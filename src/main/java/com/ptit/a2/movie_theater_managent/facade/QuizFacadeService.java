package com.ptit.a2.movie_theater_managent.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuizFacadeService {
  void create(
        String requestString,
        MultipartFile quizImage,
        List<MultipartFile> questionImages
  ) throws JsonProcessingException;

  QuizResponse find(Integer id);
}
