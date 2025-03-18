package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.FilmGenreRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmGenreResponse;
import com.ptit.a2.movie_theater_managent.entity.FilmGenre;
import com.ptit.a2.movie_theater_managent.repository.FilmGenreRepository;
import com.ptit.a2.movie_theater_managent.service.FilmGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class FilmGenreServiceImpl implements FilmGenreService {
  private final FilmGenreRepository repository;

  @Override
  @Transactional
  public FilmGenreResponse create(FilmGenreRequest request) {
    log.info("(create film genre) request: {}", request);

    FilmGenre filmGenre = FilmGenre.of(
          request.getFilmId(), request.getGenreId()
    );
    return this.toDTO(repository.save(filmGenre));
  }

  private FilmGenreResponse toDTO(FilmGenre filmGenre) {
    return FilmGenreResponse.of(
          filmGenre.getId(),
          filmGenre.getFilmId(),
          filmGenre.getGenreId()
    );
  }
}
