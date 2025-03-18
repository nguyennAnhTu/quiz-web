package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.repository.FilmGenreRepository;
import com.ptit.a2.movie_theater_managent.repository.FilmRepository;
import com.ptit.a2.movie_theater_managent.repository.GenreRepository;
import com.ptit.a2.movie_theater_managent.repository.UserRepository;
import com.ptit.a2.movie_theater_managent.service.*;
import com.ptit.a2.movie_theater_managent.service.impl.*;
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

  @Bean
  public FilmService filmService(FilmRepository filmRepository) {
    return new FilmServiceImpl(filmRepository);
  }

  @Bean
  public GenreService genreService(GenreRepository genreRepository) {
    return new GenreServiceImpl(genreRepository);
  }

  @Bean
  public FilmGenreService filmGenreService(FilmGenreRepository filmGenreRepository) {
    return new FilmGenreServiceImpl(filmGenreRepository);
  }

  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserServiceImpl(userRepository);
  }
}
