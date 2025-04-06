package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
  QuestionResponse create(QuestionRequest request, Integer quizId);

  List<QuestionResponse> findByQuizId(Integer quizId);

  void deleteByQuizId(Integer quizId);
}
