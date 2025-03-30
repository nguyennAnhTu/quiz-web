package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/quizzes")
@Slf4j
@RequiredArgsConstructor
public class QuizController {
  private final QuizFacadeService quizFacadeService;
}
