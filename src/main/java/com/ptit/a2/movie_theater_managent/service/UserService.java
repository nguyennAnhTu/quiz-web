package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.ChangePasswordRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.UserUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;

public interface UserService {
  AuthRegisterResponse create(AuthRegisterRequest request);

  User findByEmail(String email);

  User getById(Integer userId);

  UserResponse update(Integer id, UserUpdateRequest request);

  UserResponse detail(Integer id);

  void delete(Integer id);

  PageResponse<UserResponse> list(String keyword, int page, int size, boolean isAll);

  void changePassword(Integer id, ChangePasswordRequest request);
}
