package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.entity.Question;
import com.ptit.a2.movie_theater_managent.repository.QuestionRepository;
import com.ptit.a2.movie_theater_managent.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository repository;

  @Override
  public QuestionResponse create(QuestionRequest request, Integer quizId) {
    log.info("create question request: {}", request);

    Question question = Question.of(
          request.getContent(),
          request.getMediaLink(),
          request.getFunFact(),
          quizId
    );

    return this.toDTO(repository.save(question));
  }

  private QuestionResponse toDTO(Question question) {
    return QuestionResponse.of(
          question.getId(),
          question.getContent(),
          question.getMediaLink(),
          question.getFunFact(),
          question.getQuizId()
    );
  }
}
