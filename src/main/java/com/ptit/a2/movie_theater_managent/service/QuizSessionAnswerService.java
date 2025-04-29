package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSessionAnswer;

public interface QuizSessionAnswerService {
  QuizSessionAnswerResponse create(QuizSessionAnswerRequest request);

  void save(QuizSessionAnswer answer);
} 