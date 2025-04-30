package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.*;
import com.ptit.a2.movie_theater_managent.entity.Question;
import com.ptit.a2.movie_theater_managent.entity.Quiz;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuizFacadeServiceImpl implements QuizFacadeService {
  private final QuizService quizService;
  private final TagService tagService;
  private final QuizTagService quizTagService;
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final UserService userService;
  private final MediaService mediaService;

  @Override
  @Transactional
  public QuizResponse create(QuizRequest request) {
    log.info("===start create quiz, request={}, {}, {}, {}, {}", request.getName(), request.getDescription(), request.getMedia(), request.getTagIds(), request.getModifier());

    Integer mediaId = null;
    MediaResponse mediaResponse = null;
    if (request.getMedia() != null) {
      mediaResponse = mediaService.create(request.getMedia());
      mediaId = mediaResponse.getId();
    }

    QuizResponse quizResponse = quizService.create(request, mediaId);
    quizResponse.setMedia(mediaResponse);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    quizResponse.setTagIds(request.getTagIds());

    return quizResponse;
  }

  @Override
  public QuizResponse find(Integer id) {
    log.info("===start find quiz");

    Quiz quiz = quizService.find(id);

    QuizResponse quizResponse = this.toDTO(quiz);
    if (quiz.getMediaId() != null) {
      quizResponse.setMedia(mediaService.find(quiz.getMediaId()));
    }

    List<Question> questions = questionService.findByQuizId(id);

    //lay ra question response
    List<QuestionResponse> questionResponses =
          questions.stream().map(this::toDTO).toList();

    //lay ra list media id tu questions
    List<Integer> mediaIds = questions.stream().map(Question::getMediaId).toList();
    Map<Integer, MediaResponse> mediaMap = mediaService.findAllByIds(mediaIds)
          .stream().collect(Collectors.toMap(MediaResponse::getId, m -> m));

    //ghep cac media response vao question response
    for (int index=0; index<questions.size(); index++) {
      Integer mediaId = questions.get(index).getMediaId();
      if (mediaId != null) {
        questionResponses.get(index).setMedia(mediaMap.get(mediaId));
      }
    }

    quizResponse.setQuestions(questionResponses);
    quizResponse.setTagIds(quizTagService.getTagIds(id));
    for (QuestionResponse questionResponse : quizResponse.getQuestions()) {
      questionResponse.setAnswer(answerService.findByQuestionId(questionResponse.getId()));
    }

    return quizResponse;
  }

  @Override
  @Transactional
  public QuizResponse update(Integer id, QuizRequest request) {
    log.info("===start update quiz");

    Quiz quiz = quizService.find(id);
    MediaResponse mediaResponse = new MediaResponse();
    if (request.getMedia() != null) {
      if (quiz.getMediaId() != null) {
        mediaResponse = mediaService.update(quiz.getMediaId(), request.getMedia());
      } else {
        mediaResponse = mediaService.create(request.getMedia());
      }
      quiz.setMediaId(mediaResponse.getId());
    }
    quiz.setName(request.getName());
    quiz.setDescription(request.getDescription());
    quiz.setModifier(request.getModifier());

    QuizResponse quizUpdated = this.toDTO(quizService.update(quiz));

    quizTagService.delete(id);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quiz.getId(), tagId);
    }

    quizUpdated.setTagIds(request.getTagIds());
    quizUpdated.setMedia(mediaResponse);

    return quizUpdated;
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    log.info("===start delete quiz");

    List<Question> questions = questionService.findByQuizId(id);
    List<Integer> questionIds = questions.stream().map(Question::getId).toList();

    //xoa answer
    for (Integer questionId : questionIds) {
      answerService.deleteByQuestionId(questionId);
    }

    //lay mediaId truoc khi xoa quiz, do co fk tu quiz toi media
    Integer mediaId = quizService.findMediaId(id);

    questionService.deleteByQuizId(id);
    //xoa media cua question
    for (Question question : questions) {
      if (question.getMediaId() != null) {
        mediaService.delete(question.getMediaId());
      }
    }

    quizTagService.delete(id);
    quizService.delete(id);

    //xoa media
    if (mediaId != null) {
      mediaService.delete(mediaId);
    }
  }

  @Override
  public List<QuizDTO> list(Integer tagId) {
    log.info("===start list quiz tagId: {}", tagId);

    List<Integer> quizIds = quizTagService.getQuizIds(tagId);
    if (quizIds.isEmpty()) {
      return Collections.emptyList();
    }

    List<Quiz> quizzes = quizService.findByIdIn(quizIds);

    return this.getQuizDTOS(quizzes);
  }

  @Override
  public List<QuizDTO> findByKeyword(String keyword, String sortBy, String order) {
    List<Quiz> quizzes = quizService.findByKeyword(keyword, sortBy, order);

    return this.getQuizDTOS(quizzes);
  }

  private List<QuizDTO> getQuizDTOS(List<Quiz> quizzes) {
    List<QuizDTO> quizDTOS = new ArrayList<>();
    for (Quiz quiz : quizzes) {
      QuizDTO quizDTO = this.toDto(quiz);
      quizDTO.setCreatedBy(userService.get(quiz.getCreatedBy()));
      if (quiz.getMediaId() != null) {
        quizDTO.setMedia(mediaService.find(quiz.getMediaId()));
      }

      quizDTOS.add(quizDTO);
    }

    return quizDTOS;
  }

  private QuizResponse toDTO(Quiz quiz) {
    return QuizResponse.of(
          quiz.getId(),
          quiz.getName(),
          quiz.getDescription(),
          null,
          quiz.getModifier(),
          quiz.getRating(),
          quiz.getCreatedBy(),
          null,
          null
    );
  }

  private QuestionResponse toDTO(Question question) {
    return QuestionResponse.of(
          question.getId(),
          question.getContent(),
          null,
          question.getFunFact(),
          question.getQuizId(),
          question.getTime(),
          question.getQuestionOrder()
    );
  }

  private QuizDTO toDto(Quiz quiz) {
    return QuizDTO.of(
          quiz.getId(),
          quiz.getName(),
          null,
          null,
          quiz.getRating(),
          quiz.getCreatedAt()
    );
  }
}
