package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.repository.QuizRepository;
import com.ptit.a2.movie_theater_managent.repository.UserRepository;
import com.ptit.a2.movie_theater_managent.service.*;
import com.ptit.a2.movie_theater_managent.service.impl.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
  @Bean
  public MessageService messageService(MessageSource messageSource) {
    return new MessageServiceImpl(messageSource);
  }

  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserServiceImpl(userRepository);
  }

  @Bean
  public QuizService quizService(QuizRepository quizRepository) {
    return new QuizServiceImpl(quizRepository);
  }
}
