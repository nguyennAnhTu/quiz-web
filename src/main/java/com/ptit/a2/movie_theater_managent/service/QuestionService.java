package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.entity.Question;

import java.util.List;

public interface QuestionService {
  QuestionResponse create(QuestionRequest request, Integer mediaId);

  Question find(Integer id);

  Question update(Integer id, QuestionRequest request);

  List<Question> findByQuizId(Integer quizId);

  void deleteByQuizId(Integer quizId);

  void delete(Integer id);
}
