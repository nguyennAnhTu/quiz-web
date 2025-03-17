package com.ptit.a2.movie_theater_managent.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FilmResponse {
  private Integer id;
  private String name;
  private String description;
  private Integer duration;
  private Integer ageLimit;
  private Date releaseDate;
  private List<String> genres;
  private String trailerUrl;

  public static FilmResponse of(Integer id, String name, String description, Integer duration, Integer ageLimit, Date releaseDate, String trailerUrl) {
    return of(id, name, description, duration, ageLimit, releaseDate, null, trailerUrl);
  }
}
