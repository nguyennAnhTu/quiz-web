package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
  private final GenreService genreService;
}
