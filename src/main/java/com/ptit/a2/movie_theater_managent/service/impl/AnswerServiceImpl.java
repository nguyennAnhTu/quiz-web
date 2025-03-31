package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.repository.AnswerRepository;
import com.ptit.a2.movie_theater_managent.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
  private final AnswerRepository repository;
}
