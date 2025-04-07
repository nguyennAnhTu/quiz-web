package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;

public interface QuestionFacadeService {
  QuestionResponse create(QuestionRequest questionRequest);

  QuestionResponse find(Integer id);
}
