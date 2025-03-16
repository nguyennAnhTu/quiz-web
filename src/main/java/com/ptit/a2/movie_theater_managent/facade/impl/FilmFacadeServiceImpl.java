package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.facade.FilmFacadeService;
import com.ptit.a2.movie_theater_managent.service.FilmGenreService;
import com.ptit.a2.movie_theater_managent.service.FilmService;
import com.ptit.a2.movie_theater_managent.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmFacadeServiceImpl implements FilmFacadeService {
  private final FilmService filmService;
  private final GenreService genreService;
  private final FilmGenreService filmGenreService;
}
