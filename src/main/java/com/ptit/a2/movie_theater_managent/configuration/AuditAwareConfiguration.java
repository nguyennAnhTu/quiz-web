package com.ptit.a2.movie_theater_managent.configuration;


import com.ptit.a2.movie_theater_managent.configuration.auditor.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditAwareConfiguration {
  @Bean
  public AuditorAware<Integer> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
