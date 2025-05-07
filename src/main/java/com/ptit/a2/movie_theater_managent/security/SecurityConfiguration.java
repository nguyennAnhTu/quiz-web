package com.ptit.a2.movie_theater_managent.security;

import com.ptit.a2.movie_theater_managent.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
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


import java.util.Arrays;
import java.util.Collections;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.MATCHER_ADMIN_API;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.MATCHER_USER_API;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChainUsersAPI(HttpSecurity httpSecurity) throws Exception {
    sharedSecurityConfiguration(httpSecurity);
    httpSecurity
          .authorizeHttpRequests(auth -> {
            auth.requestMatchers(MATCHER_ADMIN_API).hasRole("ADMIN");
            auth.requestMatchers(MATCHER_USER_API).permitAll();
            auth.anyRequest().authenticated();
          })
//          .exceptionHandling(
//                exception -> exception.authenticationEntryPoint(new RestAuthenticationEntryPoint())
//          )
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
          //.oauth2Login(Customizer.withDefaults());
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


//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.addAllowedHeader("*");
//    configuration.addAllowedMethod("*");
//    configuration.addAllowedOriginPattern("*");
//    configuration.addAllowedOrigin("https://1343-58-187-92-82.ngrok-free.app/");
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    configuration.setAllowCredentials(true);
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }

//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.addAllowedHeader("*");
//    configuration.addAllowedMethod("*");
//    configuration.addAllowedOrigin("http://localhost:8899"); // Cho phép FE local
//    configuration.addAllowedOrigin("https://stunning-termite-ideal.ngrok-free.app"); // Domain FE ngrok
//    configuration.addAllowedOriginPattern("https://*.ngrok-free.app"); // Cho phép tất cả domain ngrok
//    configuration.setAllowCredentials(true);
//    configuration.addExposedHeader("Authorization");
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    log.info("WebSocket CORS Configuration - Allowed Origins: " + configuration.getAllowedOrigins());
//    return source;
//  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Cấu hình mạnh mẽ hơn cho CORS
//    configuration.setAllowedHeaders(Arrays.asList(
//          "Authorization", "Cache-Control", "Content-Type", "Accept",
//          "X-Requested-With", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
//          "Origin"
//    ));
    configuration.setAllowedHeaders(Arrays.asList(
          "Authorization", "Cache-Control", "Content-Type", "Accept",
          "X-Requested-With", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
          "Origin", "ngrok-skip-browser-warning", "sec-ch-ua", "sec-ch-ua-mobile", "sec-ch-ua-platform"
    ));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

    // Thay vì wildcard, cấu hình cụ thể các domain cho mobile
    configuration.setAllowedOrigins(Arrays.asList(
          "http://localhost:8899",
          "https://stunning-termite-ideal.ngrok-free.app"
    ));

    configuration.addAllowedOrigin("https://stunning-termite-ideal.ngrok-free.app");

    // Thêm pattern cho ngrok domains
    configuration.setAllowedOriginPatterns(Collections.singletonList("https://*.ngrok-free.app"));

    // Quan trọng cho credentials
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L); // Cache CORS preflight responses

    // Thêm các header quan trọng vào exposed headers
    configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    source.registerCorsConfiguration("/websocket/**", configuration);

    log.info("CORS Configuration - Allowed Origins: {}", configuration.getAllowedOrigins());
    log.info("CORS Configuration - Allowed Origin Patterns: {}", configuration.getAllowedOriginPatterns());

    return source;
  }


  @Bean
  public CorsFilter corsFilter() {
    return new CorsFilter(corsConfigurationSource());
  }
}
