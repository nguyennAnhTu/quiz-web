package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.repository.FilmGenreRepository;
import com.ptit.a2.movie_theater_managent.service.FilmGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FilmGenreServiceImpl implements FilmGenreService {
  private final FilmGenreRepository repository;

}
