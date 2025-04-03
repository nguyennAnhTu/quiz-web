package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.TagRequest;
import com.ptit.a2.movie_theater_managent.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tags")
@Slf4j
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<Void> create(
        @RequestBody TagRequest tagRequest
  ) {
    log.info("create tag request: {}", tagRequest);

    tagService.create(tagRequest);
    return ResponseGeneral.ofCreated(
          "Tao tag thanh cong"
    );
  }
}
