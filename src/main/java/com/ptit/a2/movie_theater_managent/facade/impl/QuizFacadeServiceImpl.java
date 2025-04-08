package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    log.info("===start create quiz");

    QuizResponse quizResponse = quizService.create(request);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    return quizResponse;
  }

  @Override
  public QuizResponse find(Integer id) {
    log.info("===start find quiz");

    QuizResponse quizResponse = quizService.find(id);
    quizResponse.setQuestions(questionService.findByQuizId(id));
    for (QuestionResponse questionResponse : quizResponse.getQuestions()) {
      questionResponse.setAnswerResponses(answerService.findByQuestionId(questionResponse.getId()));
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
}
