package com.ptit.a2.movie_theater_managent.service.base.impl;

import com.ptit.a2.movie_theater_managent.service.base.BaseRedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedisServiceImpl implements BaseRedisService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, Object> hashOperations;

  public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void hashSet(String key, String field, Object value) {
    hashOperations.put(key, field, value);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Object hashGet(String key, String field) {
    return hashOperations.get(key, field);
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public void delete(String key, String field) {
    hashOperations.delete(key, field);
  }
}
