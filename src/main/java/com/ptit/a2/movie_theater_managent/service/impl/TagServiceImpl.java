package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.repository.TagRepository;
import com.ptit.a2.movie_theater_managent.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository repository;
}
