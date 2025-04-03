package com.ptit.a2.movie_theater_managent.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.request.CreateQuizRequest;
import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
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

  @Override
  @Transactional
  public void create(
        String requestString,
        MultipartFile quizImage,
        List<MultipartFile> questionImages
  ) throws JsonProcessingException {
    log.info("===start create quiz");

    ObjectMapper mapper = new ObjectMapper();
    CreateQuizRequest request = mapper.readValue(requestString, CreateQuizRequest.class);

    if(quizImage != null) {
      request.setMediaLink(uploadAndGetFileUrl(quizImage));
    }

    Map<String, String> imageMappings = new HashMap<>();
    if (questionImages != null) {
      for (MultipartFile file : questionImages) {
        String savedUrl = uploadAndGetFileUrl(file);
        imageMappings.put(file.getOriginalFilename(), savedUrl);
      }
    }

    // Gán ảnh vào đúng câu hỏi dựa trên mediaLink tạm thời
    for (QuestionRequest question : request.getQuestions()) {
      if (imageMappings.containsKey(question.getMediaLink())) {
        question.setMediaLink(imageMappings.get(question.getMediaLink()));
      }
    }

    QuizResponse quizResponse = quizService.create(request);
    for (Integer tagId : request.getTagIds()) {
      quizTagService.create(quizResponse.getId(), tagId);
    }
    for (QuestionRequest questionRequest : request.getQuestions()) {
      QuestionResponse questionResponse = questionService.create(questionRequest, quizResponse.getId());
      for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
        answerService.create(answerRequest, questionResponse.getId());
      }
    }

  }
}
