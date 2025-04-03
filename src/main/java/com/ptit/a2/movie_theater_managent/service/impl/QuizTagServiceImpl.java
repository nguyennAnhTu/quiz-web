package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.entity.QuizTag;
import com.ptit.a2.movie_theater_managent.repository.QuizTagRepository;
import com.ptit.a2.movie_theater_managent.service.QuizTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class QuizTagServiceImpl implements QuizTagService {
  private final QuizTagRepository repository;

  @Override
  public void create(Integer quizId, Integer tagId) {
    log.info("create quiz tag");

    repository.save(QuizTag.of(quizId, tagId));
  }
}
