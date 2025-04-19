package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.exception.quiz.QuizNotFoundException;
import com.ptit.a2.movie_theater_managent.facade.QuestionFacadeService;
import com.ptit.a2.movie_theater_managent.service.AnswerService;
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

  @Override
  @Transactional
  public QuestionResponse create(QuestionRequest questionRequest) {
    log.info("===start create question request: {}", questionRequest);

    if (!quizService.exist(questionRequest.getQuizId())) {
      throw new QuizNotFoundException();
    }

    QuestionResponse questionResponse = questionService.create(questionRequest);

    for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
      answerService.create(answerRequest, questionResponse.getId());
    }

    return questionResponse;
  }

  @Override
  public QuestionResponse find(Integer id) {
    log.info("===start find question request: {}", id);

    QuestionResponse questionResponse = questionService.find(id);
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
}
