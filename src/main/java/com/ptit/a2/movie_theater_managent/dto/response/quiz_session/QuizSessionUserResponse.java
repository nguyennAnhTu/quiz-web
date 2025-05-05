package com.ptit.a2.movie_theater_managent.dto.response.quiz_session;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuizSessionUserResponse {
  private Integer id;
  private String email;
  private String username;
  private MediaResponse media;
  private Boolean isAdmin;
  private Boolean isHost;
} 