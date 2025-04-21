package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionParticipantRepository extends JpaRepository<QuizSessionParticipant, Integer> {
  boolean existsBySessionIdAndUserId(Integer sessionId, Integer userId);

}
