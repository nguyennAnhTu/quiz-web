package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;
import com.ptit.a2.movie_theater_managent.service.QuizSessionAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/quiz-session-answers")
@Slf4j
@RequiredArgsConstructor
public class QuizSessionAnswerController {
  private final QuizSessionAnswerService quizSessionAnswerService;

  @PostMapping
  public ResponseGeneral<QuizSessionAnswerResponse> createQuizSessionAnswer(
        @RequestBody @Valid QuizSessionAnswerRequest request
  ) {
    log.info("Start createQuizSessionAnswer");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionAnswerService.create(request)
    );
  }
} 