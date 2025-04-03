package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;

public interface QuestionService {
  QuestionResponse create(QuestionRequest request, Integer quizId);
}
