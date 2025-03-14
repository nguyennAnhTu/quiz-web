package com.ptit.a2.movie_theater_managent.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.ENCODING_UTF_8;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.MESSAGE_SOURCE;


@Configuration
public class MessageSourceConfiguration {

  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_SOURCE);
    messageSource.setDefaultEncoding(ENCODING_UTF_8);
    return messageSource;
  }

}
