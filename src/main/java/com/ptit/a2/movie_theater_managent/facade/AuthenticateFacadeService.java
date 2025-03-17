package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;

public interface AuthenticateFacadeService {
  AuthRegisterResponse register(AuthRegisterRequest request);
}
