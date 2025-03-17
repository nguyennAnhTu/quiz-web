package com.ptit.a2.movie_theater_managent.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.MATCHER_ADMIN_API;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.MATCHER_USER_API;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//  private final JwtAuthenticationFilter jwtAuthenticationFilter;
//  private final UnAuthenticationCustomHandler unAuthenticationCustomHandler;
//  private final UnAuthorizationCustomHandler unAuthorizationCustomHandler;


  @Bean
  public SecurityFilterChain securityFilterChainUsersAPI(HttpSecurity httpSecurity) throws Exception {
    sharedSecurityConfiguration(httpSecurity);
    httpSecurity
//          .securityMatcher(CMSConstants.AuthConstant.MATCHER_USER_API)
          .authorizeHttpRequests(auth -> {
            auth.requestMatchers(MATCHER_USER_API).permitAll();
            auth.anyRequest().authenticated();
          });
//          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//          .exceptionHandling(exception -> exception
//                .authenticationEntryPoint(unAuthenticationCustomHandler)
//                .accessDeniedHandler(unAuthorizationCustomHandler));
    return httpSecurity.build();
  }

  private void sharedSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
          .csrf(AbstractHttpConfigurer::disable)
          .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
          .sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
          });
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOriginPattern("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    configuration.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


  @Bean
  public CorsFilter corsFilter() {
    return new CorsFilter(corsConfigurationSource());
  }
}
