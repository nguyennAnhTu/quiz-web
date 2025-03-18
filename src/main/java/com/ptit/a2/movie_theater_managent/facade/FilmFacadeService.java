package com.ptit.a2.movie_theater_managent.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ptit.a2.movie_theater_managent.dto.response.FilmResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FilmFacadeService {
  FilmResponse create(
        String filmRequestString,
        MultipartFile multipartFile
  ) throws JsonProcessingException;
}
