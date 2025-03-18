package com.ptit.a2.movie_theater_managent.service.base;

public interface BaseRedisService {
  void hashSet(String key, String field, Object value);

  Object get(String key);

  Object hashGet(String key, String field);

  void delete(String key);

  void delete(String key, String field);
}
