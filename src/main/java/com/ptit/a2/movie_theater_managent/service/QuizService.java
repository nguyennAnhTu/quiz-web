package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.entity.Quiz;

import java.util.List;

public interface QuizService {
  Quiz create(QuizRequest request, Integer mediaId);

  Quiz find(Integer id);

  Quiz update(Quiz quiz);

  void delete(Integer id);

  boolean exist(Integer id);

  Integer findMediaId(Integer id);

  List<Quiz> findByIdIn(List<Integer> ids);

  List<Quiz> findByCreatedBy(Integer modifier);

  List<Quiz> findByKeyword(String keyword, String sortBy, String order);
}
