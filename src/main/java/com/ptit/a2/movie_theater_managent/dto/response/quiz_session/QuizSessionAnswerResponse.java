package com.ptit.a2.movie_theater_managent.dto.response.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuizSessionAnswerResponse {
  private Integer id;
  private Integer sessionId;
  private Integer userId;
  private Integer questionId;
  private Integer answerId;
  private Boolean isCorrect;
  private Long submittedAt;
  private Integer score;
} 