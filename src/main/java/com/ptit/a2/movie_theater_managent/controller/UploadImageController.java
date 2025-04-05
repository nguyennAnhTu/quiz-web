package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/images")
@Slf4j
@RequiredArgsConstructor
public class UploadImageController {
  private final UploadImageService service;

  @PostMapping
  public ResponseGeneral<String> upload(
        @RequestPart MultipartFile file
  ) {
    log.info("===start upload");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          service.upload(file)
    );
  }
}
