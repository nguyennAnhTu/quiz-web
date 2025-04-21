package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;

public interface QuizSessionParticipantService {
  void save(QuizSessionParticipant participant);

  boolean existsBySessionIdAndUserId(Integer sessionId, Integer userId);

}
