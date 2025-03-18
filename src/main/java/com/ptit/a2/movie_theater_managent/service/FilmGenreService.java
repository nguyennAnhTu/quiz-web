package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.FilmGenreRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmGenreResponse;

public interface FilmGenreService {
  FilmGenreResponse create(FilmGenreRequest request);
}
