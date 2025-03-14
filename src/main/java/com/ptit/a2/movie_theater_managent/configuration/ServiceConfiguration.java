package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.service.MessageService;
import com.ptit.a2.movie_theater_managent.service.impl.MessageServiceImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ServiceConfiguration {
  @Bean
  public MessageService messageService(MessageSource messageSource) {
    return new MessageServiceImpl(messageSource);
  }


}
