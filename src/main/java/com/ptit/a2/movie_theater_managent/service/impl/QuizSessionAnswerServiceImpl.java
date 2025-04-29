package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSessionAnswer;
import com.ptit.a2.movie_theater_managent.repository.QuizSessionAnswerRepository;
import com.ptit.a2.movie_theater_managent.service.QuizSessionAnswerService;
import com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizSessionAnswerServiceImpl implements QuizSessionAnswerService {
  private final QuizSessionAnswerRepository repository;

  @Override
  @Transactional
  public QuizSessionAnswerResponse create(QuizSessionAnswerRequest request) {
    log.info("(create quiz session answer) request: {}", request);

    QuizSessionAnswer answer = new QuizSessionAnswer();
    answer.setSessionId(request.sessionId());
    answer.setUserId(request.userId());
    answer.setQuestionId(request.questionId());
    answer.setAnswerId(request.answerId());
    answer.setIsCorrect(request.isCorrect());
    answer.setScore(request.score());
    answer.setSubmittedAt(Instant.now().toEpochMilli());

    repository.save(answer);

    return toDTO(answer);
  }

  @Override
  @Transactional
  public void save(QuizSessionAnswer answer) {
    log.info("(save quiz session answer) answer: {}", answer);
    repository.save(answer);
  }

  private QuizSessionAnswerResponse toDTO(QuizSessionAnswer answer) {
    return QuizSessionAnswerResponse.of(
          answer.getId(),
          answer.getSessionId(),
          answer.getUserId(),
          answer.getQuestionId(),
          answer.getAnswerId(),
          answer.getIsCorrect(),
          answer.getSubmittedAt(),
          answer.getScore()
    );
  }
} 