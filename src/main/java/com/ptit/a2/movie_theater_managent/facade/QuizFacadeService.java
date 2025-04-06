package com.ptit.a2.movie_theater_managent.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuizFacadeService {
  QuizResponse create(QuizRequest request);

  QuizResponse find(Integer id);

  QuizResponse update(Integer id, QuizRequest request);

  void delete(Integer id);
}
