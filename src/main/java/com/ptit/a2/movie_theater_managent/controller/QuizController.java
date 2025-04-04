package com.ptit.a2.movie_theater_managent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.CreateQuizRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ptit.a2.movie_theater_managent.cloudinary.CloudinaryHelper.uploadAndGetFileUrl;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/quizzes")
@Slf4j
@RequiredArgsConstructor
public class QuizController {
  private final QuizFacadeService quizFacadeService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<Void> create(
        @RequestPart(value = "request") String requestString,
        @RequestPart(value = "quizImage", required = false) MultipartFile quizImage,
        @RequestPart(value = "questionImages", required = false) List<MultipartFile> questionImages
  ) throws JsonProcessingException {
    log.info("===start create quiz");

    quizFacadeService.create(requestString, quizImage, questionImages);
    return ResponseGeneral.ofCreated(
          "Tạo quiz thành công"
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
}
