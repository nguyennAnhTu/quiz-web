package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;

import java.util.List;

public interface QuizSessionParticipantService {
  void save(QuizSessionParticipant participant);

  boolean existsBySessionIdAndUserId(Integer sessionId, Integer userId);

  List<QuizSessionParticipant> findBySessionId(Integer quizSessionId);


}
