package com.ptit.a2.movie_theater_managent.dto.request.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public record QuizSessionCreateRequest(@NotNull(message = "Quiz ID is required") Integer quizId,
                                       @NotBlank(message = "Session code is required") String sessionCode,
                                       @NotNull(message = "Status is required") String status,
                                       Integer currentQuestionId,
                                       Date startTime,
                                       Date endTime) {
}
