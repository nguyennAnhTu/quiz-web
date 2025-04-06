package com.ptit.a2.movie_theater_managent.service;

public interface QuizTagService {
  void create(Integer quizId, Integer tagId);

  void delete(Integer quizId);
}
