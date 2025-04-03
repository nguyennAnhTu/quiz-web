package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.TagRequest;
import com.ptit.a2.movie_theater_managent.entity.Tag;
import com.ptit.a2.movie_theater_managent.repository.TagRepository;
import com.ptit.a2.movie_theater_managent.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository repository;

  @Override
  @Transactional
  public void create(TagRequest tagRequest) {
    log.info("create tag");

    repository.save(Tag.of(tagRequest.getTitle()));
  }
}
