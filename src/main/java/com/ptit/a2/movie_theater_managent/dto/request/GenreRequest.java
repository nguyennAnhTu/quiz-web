package com.ptit.a2.movie_theater_managent.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GenreRequest {
  private String name;
}
