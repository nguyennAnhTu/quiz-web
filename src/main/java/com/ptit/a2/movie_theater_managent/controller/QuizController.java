package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/quizzes")
@Slf4j
@RequiredArgsConstructor
public class QuizController {
  private final QuizFacadeService quizFacadeService;
  private final QuizService quizService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<QuizResponse> create(
        @RequestBody QuizRequest request
  ) {
    log.info("===start create quiz");

    return ResponseGeneral.ofCreated(
          SUCCESS,
          quizFacadeService.create(request)
    );
  }

  @GetMapping("/{id}")
  public ResponseGeneral<QuizResponse> find(
        @PathVariable Integer id
  ) {
    log.info("===start find quiz");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizFacadeService.find(id)
    );
  }

  @PutMapping("/{id}")
  public ResponseGeneral<QuizResponse> update(
        @PathVariable Integer id,
        @RequestBody QuizRequest request
  ) {
    log.info("===start update quiz");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizFacadeService.update(id, request)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseGeneral<Void> delete(
        @PathVariable Integer id
  ) {
    log.info("===start delete quiz");

    quizFacadeService.delete(id);
    return ResponseGeneral.ofSuccess(SUCCESS);
  }
}
