package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionCreateRequest;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;

public interface QuizSessionService {
  QuizSessionResponse create(QuizSessionCreateRequest request);

  void save(QuizSession quizSession);

  QuizSession findById(Integer id);

  QuizSession findBySessionCode(String sessionCode);

  QuizSessionResponse update(Integer id, QuizSessionUpdateRequest request);

  QuizSessionResponse detail(Integer id);

  void delete(Integer id);

  PageResponse<QuizSessionResponse> list(String keyword, int page, int size, boolean isAll);
}
