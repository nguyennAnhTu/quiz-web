package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.UserRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.authentication.EmailExistedException;
import com.ptit.a2.movie_theater_managent.repository.UserRepository;
import com.ptit.a2.movie_theater_managent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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

  private User toEntity(UserRequest request) {
    return User.of(
          request.getEmail(),
          getPasswordEncoder().encode(request.getPassword()),
          request.getName(),
          request.getDateOfBirth(),
          request.getPhoneNumber(),
          request.getIsAdmin()
    );
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
    if(Boolean.TRUE.equals(repository.existsByEmail(email))) {
      throw new EmailExistedException();
    }
  }
}
