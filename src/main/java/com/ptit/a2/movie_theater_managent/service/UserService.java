package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.ChangePasswordRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.UserUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserDTO;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;

import java.util.List;

import java.util.Optional;

public interface UserService {
  AuthRegisterResponse create(AuthRegisterRequest request);

  User findByEmail(String email);

  User getById(Integer userId);

  UserResponse update(Integer id, UserUpdateRequest request);

  UserResponse detail(Integer id);

  void delete(Integer id);

  PageResponse<UserResponse> list(String keyword, int page, int size, boolean isAll);

  void changePassword(Integer id, ChangePasswordRequest request);

  List<UserResponse> getUsersByIds(List<Integer> userIds);
  void createInactiveUser(AuthRegisterRequest request, Integer mediaId);

  void activeUser(String email);

  void deleteInactiveUser(String email);

  UserDTO get(Integer id);

  void findUserByEmail(String email);

  Optional<User> find(String email);

  void updateInformation(Integer id, String password, String username);
}
