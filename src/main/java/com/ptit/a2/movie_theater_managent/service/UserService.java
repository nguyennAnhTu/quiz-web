package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;

public interface UserService {
  AuthRegisterResponse create(AuthRegisterRequest request);
}
