package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Integer> {
  Boolean existsBySessionCode(String sessionCode);

  @Query("SELECT new com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse(" +
        "qs.id, qs.quizId, qs.sessionCode, qs.status, qs.currentQuestionId, " +
        "qs.startTime, qs.duration, qs.createdBy, qs.createdAt, qs.lastUpdatedBy, qs.lastUpdatedAt) " +
        "FROM QuizSession qs WHERE :keyword = '' OR qs.sessionCode LIKE %:keyword%")
  Page<QuizSessionResponse> list(String keyword, Pageable pageable);

  @Query("SELECT new com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse(" +
        "qs.id, qs.quizId, qs.sessionCode, qs.status, qs.currentQuestionId, " +
        "qs.startTime, qs.duration, qs.createdBy, qs.createdAt, qs.lastUpdatedBy, qs.lastUpdatedAt) " +
        "FROM QuizSession qs WHERE :keyword = '' OR qs.sessionCode LIKE %:keyword%")
  List<QuizSessionResponse> listAll(String keyword);
}