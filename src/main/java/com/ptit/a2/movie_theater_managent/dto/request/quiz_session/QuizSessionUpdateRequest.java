package com.ptit.a2.movie_theater_managent.dto.request.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuizSessionUpdateRequest(
      Integer quizId,
      String sessionCode,
      String status,
      Integer currentQuestionId,
      @Min(value = 1, message = "Duration must be greater than 0") Long duration
) {}