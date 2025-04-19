package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;

import java.util.List;

public interface QuizFacadeService {
  QuizResponse create(QuizRequest request);

  QuizResponse find(Integer id);

  List<QuizProjection> list(Integer tagId, Integer page, Integer size);

  QuizResponse update(Integer id, QuizRequest request);

  void delete(Integer id);
}
