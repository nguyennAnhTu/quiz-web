package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.entity.Question;
import com.ptit.a2.movie_theater_managent.exception.quiz.QuizNotFoundException;
import com.ptit.a2.movie_theater_managent.facade.QuestionFacadeService;
import com.ptit.a2.movie_theater_managent.service.AnswerService;
import com.ptit.a2.movie_theater_managent.service.MediaService;
import com.ptit.a2.movie_theater_managent.service.QuestionService;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionFacadeServiceImpl implements QuestionFacadeService {
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final QuizService quizService;
  private final MediaService mediaService;

  @Override
  @Transactional
  public QuestionResponse create(QuestionRequest request) {
    log.info("===start create question request: {}", request);

    if (!quizService.exist(request.getQuizId())) {
      throw new QuizNotFoundException();
    }

    Integer mediaId = null;
    MediaResponse mediaResponse = null;
    if (request.getMedia() != null) {
      mediaResponse = mediaService.create(request.getMedia());
      mediaId = mediaResponse.getId();
    }

    QuestionResponse questionResponse = questionService.create(request, mediaId);
    questionResponse.setMedia(mediaResponse);

    for (AnswerRequest answerRequest : request.getAnswers()) {
      answerService.create(answerRequest, questionResponse.getId());
    }

    return questionResponse;
  }

  @Override
  public QuestionResponse find(Integer id) {
    log.info("===start find question request: {}", id);

    Question question = questionService.find(id);
    QuestionResponse questionResponse = this.toDTO(question);
    if (question.getMediaId() != null) {
      questionResponse.setMedia(this.mediaService.find(question.getMediaId()));
    }

    questionResponse.setAnswer(
          answerService.findByQuestionId(questionResponse.getId())
    );

    return questionResponse;
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) id:{}", id);

    answerService.deleteByQuestionId(id);
    questionService.delete(id);

  }

  @Override
  @Transactional
  public QuestionResponse update(Integer id, QuestionRequest questionRequest) {
    if (!quizService.exist(questionRequest.getQuizId())) {
      throw new QuizNotFoundException();
    }

    QuestionResponse questionResponse = questionService.update(id,questionRequest);
    answerService.deleteByQuestionId(id);
    for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
      answerService.create(answerRequest, questionResponse.getId());
    }

    return questionResponse;
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
}
