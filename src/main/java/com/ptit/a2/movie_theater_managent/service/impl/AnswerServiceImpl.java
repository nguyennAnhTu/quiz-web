package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AnswerResponse;
import com.ptit.a2.movie_theater_managent.entity.Answer;
import com.ptit.a2.movie_theater_managent.repository.AnswerRepository;
import com.ptit.a2.movie_theater_managent.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
  private final AnswerRepository repository;

  @Override
  public AnswerResponse create(AnswerRequest request, Integer questionId) {
    log.info("create answer request: {}", request);

    Answer answer = Answer.of(request.getContent(), request.getIsCorrect(), questionId);
    return this.toDTO(repository.save(answer));
  }

  private AnswerResponse toDTO(Answer answer) {
    return AnswerResponse.of(
          answer.getId(),
          answer.getContent(),
          answer.getIsCorrect(),
          answer.getQuestionId()
    );
  }
}
