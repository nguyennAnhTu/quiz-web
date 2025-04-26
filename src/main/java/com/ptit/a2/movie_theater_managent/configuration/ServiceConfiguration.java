package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.repository.*;
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
  public UserService userService(UserRepository userRepository, MediaService mediaService) {
    return new UserServiceImpl(userRepository, mediaService);
  }

  @Bean
  public QuizService quizService(QuizRepository quizRepository) {
    return new QuizServiceImpl(quizRepository);
  }

  @Bean
  public TagService tagService(TagRepository tagRepository) {
    return new TagServiceImpl(tagRepository);
  }

  @Bean
  public QuizTagService quizTagService(QuizTagRepository quizTagRepository) {
    return new QuizTagServiceImpl(quizTagRepository);
  }

  @Bean
  public QuestionService questionService(QuestionRepository questionRepository) {
    return new QuestionServiceImpl(questionRepository);
  }

  @Bean
  public AnswerService answerService(AnswerRepository answerRepository) {
    return new AnswerServiceImpl(answerRepository);
  }
}
