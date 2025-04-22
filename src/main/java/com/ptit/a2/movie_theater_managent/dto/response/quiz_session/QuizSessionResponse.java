package com.ptit.a2.movie_theater_managent.dto.response.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuizSessionResponse(
      Integer id,
      Integer quizId,
      String sessionCode,
      QuizSession.Status status,
      Integer currentQuestionId,
      Long startTime,
      Long duration,
      Integer createdBy,
      Long createdAt,
      Integer lastUpdatedBy,
      Long lastUpdatedAt
) {
  public static QuizSessionResponse of(
        Integer id,
        Integer quizId,
        String sessionCode,
        QuizSession.Status status,
        Integer currentQuestionId,
        Long startTime,
        Long duration,
        Integer createdBy,
        Long createdAt,
        Integer lastUpdatedBy,
        Long lastUpdatedAt
  ) {
    return new QuizSessionResponse(
          id, quizId, sessionCode, status, currentQuestionId,
          startTime, duration, createdBy, createdAt, lastUpdatedBy, lastUpdatedAt
    );
  }
}