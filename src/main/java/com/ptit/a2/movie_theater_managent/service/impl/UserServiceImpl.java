package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.ChangePasswordRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.UserUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.authentication.EmailExistedException;
import com.ptit.a2.movie_theater_managent.exception.authentication.PasswordIncorrectException;
import com.ptit.a2.movie_theater_managent.exception.authentication.UserNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.film.BadRequestException;
import com.ptit.a2.movie_theater_managent.repository.UserRepository;
import com.ptit.a2.movie_theater_managent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BLANK;
import static com.ptit.a2.movie_theater_managent.utils.PasswordEncoderUtils.getPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;

  @Override
  @Transactional
  public AuthRegisterResponse create(AuthRegisterRequest request) {
    log.info("(create user) request: {}", request);

    this.checkEmailExists(request.getEmail());
    User user = User.of(
          request.getEmail(),
          getPasswordEncoder().encode(request.getPassword()),
          request.getName(),
          request.getDateOfBirth(),
          request.getPhoneNumber(),
          request.getIsAdmin()
    );
    repository.save(user);

    return AuthRegisterResponse.of(
          user.getId(),
          user.getEmail(),
          user.getName(),
          user.getDateOfBirth(),
          user.getPhoneNumber(),
          user.getIsAdmin()
    );
  }

  @Override
  public User findByEmail(String email) {
    log.info("(findByEmail) request: {}", email);

    return repository.findByEmail(email)
          .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserResponse update(Integer id, UserUpdateRequest request) {
    log.info("(update) id:{}, request:{}", id, request);

    final User user = this.find(id);
    this.updateField(user, request);

    return this.toDTO(repository.save(user));
  }

  @Override
  public UserResponse detail(Integer id) {
    log.info("(detail) id:{}", id);

    return this.toDTO(this.find(id));
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) id:{}", id);

    final User user = this.find(id);
    repository.delete(user);
  }

  @Override
  public PageResponse<UserResponse> list(String keyword, int page, int size, boolean isAll) {
    log.info("(list) keyword:{}, page:{}, size:{}, isAll:{}", keyword, page, size, isAll);

    if (isAll) {
      List<UserResponse> responses = repository.listAll(
            keyword == null ? BLANK : keyword
      );

      return PageResponse.of(responses, responses.size());
    } else {
      Page<UserResponse> responses = repository.list(
            keyword == null ? BLANK : keyword
            , PageRequest.of(page, size));

      return PageResponse.of(responses.getContent(), (int) responses.getTotalElements());
    }

  }

  @Override
  public void changePassword(Integer id, ChangePasswordRequest request) {
    log.info("(changePassword) id:{}", id);

    final User user = this.find(id);
    // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
    if (!request.newPassword().equals(request.confirmPassword())) {
      throw new BadRequestException();
    }

    // Kiểm tra mật khẩu hiện tại có đúng không
    if (!getPasswordEncoder().matches(request.currentPassword(), user.getPassword())) {
      throw new PasswordIncorrectException();
    }

    // Mã hóa mật khẩu mới và cập nhật
    user.setPassword(getPasswordEncoder().encode(request.newPassword()));

    // Lưu thay đổi vào database
    repository.save(user);
  }


  private User find(Integer id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  private void updateField(User user, UserUpdateRequest request) {
    user.setName(request.getName());
    user.setDateOfBirth(request.getDateOfBirth());
    user.setIsAdmin(request.getIsAdmin());
    user.setPhoneNumber(request.getPhoneNumber());
  }

  private UserResponse toDTO(User user) {
    return UserResponse.of(
          user.getId(),
          user.getEmail(),
          user.getName(),
          user.getDateOfBirth(),
          user.getPhoneNumber(),
          user.getIsAdmin()
    );
  }

  private void checkEmailExists(String email) {
    if (Boolean.TRUE.equals(repository.existsByEmail(email))) {
      throw new EmailExistedException();
    }
  }
}
