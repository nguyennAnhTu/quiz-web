package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.CreateQuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;

public interface QuizService {
  QuizResponse create(CreateQuizRequest request);

  QuizResponse find(Integer id);
}
