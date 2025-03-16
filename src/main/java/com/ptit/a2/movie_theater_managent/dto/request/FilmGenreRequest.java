package com.ptit.a2.movie_theater_managent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class FilmGenreRequest {
  private Integer filmId;
  private Integer genreId;
}
