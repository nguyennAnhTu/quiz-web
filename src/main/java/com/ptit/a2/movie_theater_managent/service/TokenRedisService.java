package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.service.base.BaseRedisService;

public interface TokenRedisService extends BaseRedisService {
  void remove(String userId);
}
