package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.service.TokenRedisService;
import com.ptit.a2.movie_theater_managent.service.base.impl.BaseRedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.RedisConstant.ACCESS_TOKEN_KEY;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.RedisConstant.REFRESH_TOKEN_KEY;

@Slf4j
@Service
public class TokenRedisServiceImpl extends BaseRedisServiceImpl implements TokenRedisService {
  public TokenRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    super(redisTemplate);
  }

  @Override
  public void remove(String userId) {
    log.info("(remove)userId: {}", userId);

    this.delete(REFRESH_TOKEN_KEY, userId);
    this.delete(ACCESS_TOKEN_KEY, userId);
  }
}
