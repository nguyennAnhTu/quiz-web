package com.ptit.a2.movie_theater_managent.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BASE_PACKAGE_REPO;


@Configuration
@EnableJpaRepositories(
      basePackages = BASE_PACKAGE_REPO
)
public class JpaRepositoryConfiguration {
}
