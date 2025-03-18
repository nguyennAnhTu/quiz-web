package com.ptit.a2.movie_theater_managent.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.a2.movie_theater_managent.dto.request.FilmGenreRequest;
import com.ptit.a2.movie_theater_managent.dto.request.FilmRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmResponse;
import com.ptit.a2.movie_theater_managent.facade.FilmFacadeService;
import com.ptit.a2.movie_theater_managent.service.FilmGenreService;
import com.ptit.a2.movie_theater_managent.service.FilmService;
import com.ptit.a2.movie_theater_managent.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmFacadeServiceImpl implements FilmFacadeService {
  private final FilmService filmService;
  private final GenreService genreService;
  private final FilmGenreService filmGenreService;

  @Override
  @Transactional
  public FilmResponse create(
        String filmRequestString,
        MultipartFile multipartFile
  ) throws JsonProcessingException {
    //log.info("(create film) request: {}", request);

    ObjectMapper objectMapper = new ObjectMapper();
    FilmRequest request = objectMapper.readValue(filmRequestString, FilmRequest.class);

    FilmResponse filmResponse = filmService.create(request, multipartFile);
    List<String> genres = request.getGenres();

    for (String genre : genres) {
      filmGenreService.create(
            FilmGenreRequest.of(
                  filmResponse.getId(),
                  genreService.find(genre).getId()
            )
      );
    }

    return filmResponse;
  }
}
