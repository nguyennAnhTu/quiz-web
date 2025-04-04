package com.ptit.a2.movie_theater_managent.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ptit.a2.movie_theater_managent.annotation.ValidationEmail;
import com.ptit.a2.movie_theater_managent.annotation.ValidationPassword;
import com.ptit.a2.movie_theater_managent.annotation.ValidationUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class
AuthRegisterRequest {
  @ValidationEmail
  private String email;

  @ValidationPassword
  private String password;

  @ValidationUsername
  private String username;

  private Boolean isAdmin;
}
