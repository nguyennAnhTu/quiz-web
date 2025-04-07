package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;

public interface QuizService {
  QuizResponse create(QuizRequest request);

  QuizResponse find(Integer id);

  QuizResponse update(Integer id, QuizRequest request);

  void delete(Integer id);

  boolean exist(Integer id);
}
