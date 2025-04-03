package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.AnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AnswerResponse;

public interface AnswerService {
  AnswerResponse create(AnswerRequest request, Integer questionId);
}
