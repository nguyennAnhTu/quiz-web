package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {
  QuizResponse create(QuizRequest request);

  QuizResponse find(Integer id);

  QuizResponse update(Integer id, QuizRequest request);

  void delete(Integer id);

  boolean exist(Integer id);

  List<QuizProjection> findByIdIn(List<Integer> ids);

  List<QuizProjection> findByCreatedBy(Integer modifier);

  List<QuizProjection> findByKeyword(String keyword, String sortBy, String order);
}
