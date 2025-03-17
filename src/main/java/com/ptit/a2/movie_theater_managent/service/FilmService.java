package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.FilmRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmResponse;

public interface FilmService {
  FilmResponse create(FilmRequest request);
}
