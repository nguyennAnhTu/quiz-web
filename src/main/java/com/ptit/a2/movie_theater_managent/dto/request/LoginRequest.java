package com.ptit.a2.movie_theater_managent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
  private String email;
  private String password;
}
