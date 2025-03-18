package com.ptit.a2.movie_theater_managent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.FilmRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmResponse;
import com.ptit.a2.movie_theater_managent.facade.FilmFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
  private final FilmFacadeService filmFacadeService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<FilmResponse> create(
        @RequestPart(value = "film") String requestString,
        @RequestPart(value = "image", required = false) MultipartFile multipartFile
  ) throws JsonProcessingException {
    log.info("===start create film");

    return ResponseGeneral.of(
          HttpStatus.CREATED.value(),
          SUCCESS,
          filmFacadeService.create(requestString, multipartFile)
    );
  }
}
