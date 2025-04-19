package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.dto.response.QuizDTO;
import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

  @Override
  @Transactional
  public QuizResponse create(QuizRequest request) {
    log.info("===start create quiz, request={}, {}, {}, {}, {}", request.getName(), request.getDescription(), request.getMediaLink(), request.getTagIds(), request.getModifier());

    QuizResponse quizResponse = quizService.create(request);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    quizResponse.setTagIds(request.getTagIds());

    return quizResponse;
  }

  @Override
  public QuizResponse find(Integer id) {
    log.info("===start find quiz");

    QuizResponse quizResponse = quizService.find(id);
    quizResponse.setQuestions(questionService.findByQuizId(id));
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

    QuizResponse quizResponse = quizService.update(id, request);

    quizTagService.delete(id);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    quizResponse.setTagIds(request.getTagIds());

    return quizResponse;
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    log.info("===start delete quiz");

    List<Integer> questionIds =
          questionService.findByQuizId(id).
                stream().map(QuestionResponse::getId).toList();

    for (Integer questionId : questionIds) {
      answerService.deleteByQuestionId(questionId);
    }

    questionService.deleteByQuizId(id);
    quizTagService.delete(id);
    quizService.delete(id);
  }

  @Override
  public List<QuizProjection> list(Integer tagId, Integer page, Integer size) {
    log.info("===start list quiz tagId: {}", tagId);

    List<Integer> quizIds = quizTagService.getQuizIds(tagId);
    if (quizIds.isEmpty()) {
      return null;
    }

    return quizService.findByIdIn(quizIds);
  }
}
