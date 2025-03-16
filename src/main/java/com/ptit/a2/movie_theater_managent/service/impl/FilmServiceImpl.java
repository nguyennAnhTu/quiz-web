package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.repository.FilmRepository;
import com.ptit.a2.movie_theater_managent.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
  private final FilmRepository repository;

}
