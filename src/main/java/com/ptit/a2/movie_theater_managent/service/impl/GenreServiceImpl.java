package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.GenreRequest;
import com.ptit.a2.movie_theater_managent.dto.response.GenreResponse;
import com.ptit.a2.movie_theater_managent.entity.Genre;
import com.ptit.a2.movie_theater_managent.exception.film.GenreNotFoundException;
import com.ptit.a2.movie_theater_managent.repository.GenreRepository;
import com.ptit.a2.movie_theater_managent.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
  private final GenreRepository repository;

  @Override
  @Transactional
  public GenreResponse create(GenreRequest request) {
    log.info("(create genre) request: {}", request);

    final Genre genre = this.toEntity(request);
    return this.toDTO(repository.save(genre));
  }

  @Override
  public GenreResponse find(String name) {
    log.info("(find genre) name: {}", name);

    Genre genre = repository.findByName(name).orElseThrow(GenreNotFoundException::new);

    return this.toDTO(genre);
  }

  private Genre toEntity(GenreRequest request) {
    return Genre.of(request.getName());
  }

  private GenreResponse toDTO(Genre genre) {
    return GenreResponse.of(genre.getId(), genre.getName());
  }
}
