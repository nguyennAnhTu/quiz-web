package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.facade.QuestionFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/questions")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionFacadeService questionFacadeService;

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseGeneral<QuestionResponse> create(
        @RequestBody QuestionRequest request
  ) {
    log.info("===start create question");

    return ResponseGeneral.ofCreated(
          SUCCESS,
          questionFacadeService.create(request)
    );
  }

  @GetMapping("/{id}")
  public ResponseGeneral<QuestionResponse> find(@PathVariable Integer id) {
    log.info("===start find question");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          questionFacadeService.find(id)
    );
  }
}
