package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.GenreRequest;
import com.ptit.a2.movie_theater_managent.dto.response.GenreResponse;

public interface GenreService {
  GenreResponse create(GenreRequest request);
}
