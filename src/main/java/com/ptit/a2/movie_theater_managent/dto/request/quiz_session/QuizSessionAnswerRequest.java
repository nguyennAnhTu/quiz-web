package com.ptit.a2.movie_theater_managent.dto.request.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuizSessionAnswerRequest(
      @NotNull(message = "Session ID is required") Integer sessionId,
      @NotNull(message = "User ID is required") Integer userId,
      @NotNull(message = "Question ID is required") Integer questionId,
      @NotNull(message = "Answer ID is required") Integer answerId,
      @NotNull(message = "Is Correct is required") Boolean isCorrect,
      @NotNull(message = "Score is required") Integer score
) {} 