package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.GenreRequest;
import com.ptit.a2.movie_theater_managent.dto.response.GenreResponse;
import com.ptit.a2.movie_theater_managent.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
  private final GenreService genreService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<GenreResponse> create(
        @RequestBody GenreRequest request
  ) {
    log.info("(start create genre): request {}", request);

    return ResponseGeneral.of(
          HttpStatus.CREATED.value(),
          SUCCESS,
          genreService.create(request)
    );
  }
}
