package com.ptit.a2.movie_theater_managent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Error {
  private String code;
  private Object detail;
}
