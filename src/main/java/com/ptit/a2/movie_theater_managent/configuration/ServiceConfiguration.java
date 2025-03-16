package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.repository.FilmGenreRepository;
import com.ptit.a2.movie_theater_managent.repository.FilmRepository;
import com.ptit.a2.movie_theater_managent.repository.GenreRepository;
import com.ptit.a2.movie_theater_managent.service.FilmGenreService;
import com.ptit.a2.movie_theater_managent.service.FilmService;
import com.ptit.a2.movie_theater_managent.service.GenreService;
import com.ptit.a2.movie_theater_managent.service.MessageService;
import com.ptit.a2.movie_theater_managent.service.impl.FilmGenreServiceImpl;
import com.ptit.a2.movie_theater_managent.service.impl.FilmServiceImpl;
import com.ptit.a2.movie_theater_managent.service.impl.GenreServiceImpl;
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
}
