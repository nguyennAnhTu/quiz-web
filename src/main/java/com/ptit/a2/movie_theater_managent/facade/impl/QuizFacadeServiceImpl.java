package com.ptit.a2.movie_theater_managent.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.entity.Quiz;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.cloudinary.CloudinaryHelper.uploadAndGetFileUrl;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuizFacadeServiceImpl implements QuizFacadeService {
  private final QuizService quizService;
  private final TagService tagService;
  private final QuizTagService quizTagService;
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final UserService userService;

  @Override
  @Transactional
  public QuizResponse create(QuizRequest request) {
    log.info("===start create quiz");

    QuizResponse quizResponse = quizService.create(request);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    return quizResponse;
  }

  @Override
  public QuizResponse find(Integer id) {
    log.info("===start find quiz");

    return quizService.find(id);
  }

  @Override
  @Transactional
  public QuizResponse update(Integer id, QuizRequest request) {
    log.info("===start update quiz");

    quizTagService.delete(id);
    QuizResponse quizResponse = quizService.update(id, request);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }

    return quizResponse;
  }
}
