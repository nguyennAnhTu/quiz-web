package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.entity.User;

public interface UserService {
  AuthRegisterResponse create(AuthRegisterRequest request);

  User findByEmail(String email);
}
