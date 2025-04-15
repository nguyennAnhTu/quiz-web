package com.ptit.a2.movie_theater_managent.service;

import java.util.List;

public interface QuizTagService {
  void create(Integer quizId, Integer tagId);

  void delete(Integer quizId);

  List<Integer> getTagIds(Integer quizId);
}
