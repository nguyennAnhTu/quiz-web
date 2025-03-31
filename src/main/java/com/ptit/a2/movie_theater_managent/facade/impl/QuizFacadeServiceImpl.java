package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import com.ptit.a2.movie_theater_managent.service.QuizTagService;
import com.ptit.a2.movie_theater_managent.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuizFacadeServiceImpl implements QuizFacadeService {
  private final QuizService quizService;
  private final TagService tagService;
  private final QuizTagService quizTagService;
}
