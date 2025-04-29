package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.ChangePasswordRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.UserUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserDTO;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.authentication.EmailExistedException;
import com.ptit.a2.movie_theater_managent.exception.authentication.PasswordIncorrectException;
import com.ptit.a2.movie_theater_managent.exception.authentication.UserNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.authentication.UsernameExistedException;
import com.ptit.a2.movie_theater_managent.exception.film.BadRequestException;
import com.ptit.a2.movie_theater_managent.repository.UserRepository;
import com.ptit.a2.movie_theater_managent.service.MediaService;
import com.ptit.a2.movie_theater_managent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BLANK;
import static com.ptit.a2.movie_theater_managent.utils.PasswordEncoderUtils.getPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final MediaService mediaService;

  @Override
  @Transactional
  public AuthRegisterResponse create(AuthRegisterRequest request) {
    log.info("(create user) request: {}", request);

    this.checkEmailExists(request.getEmail());
    this.checkUsernameExists(request.getUsername());
    User user = User.of(
          request.getEmail(),
          getPasswordEncoder().encode(request.getPassword()),
          request.getUsername(),
          request.getIsAdmin()
    );
    repository.save(user);

    return AuthRegisterResponse.of(
          user.getEmail(),
          user.getUsername()
    );
  }

  @Override
  public User findByEmail(String email) {
    log.info("(findByEmail) request: {}", email);

    User user = repository.findByEmail(email).orElseThrow(UserNotFoundException::new);

    if (Boolean.TRUE.equals(!user.getIsActive())) {
      throw new UserNotFoundException();
    }

    return user;
  }

  @Override
  public void findUserByEmail(String email) {
    log.info("(findUserByEmail), email: {}", email);

    User user = repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public Optional<User> find(String email) {
    log.info("(find), email: {}", email);

    return repository.findByEmail(email);
  }

  @Override
  public User getById(Integer userId) {
    return repository.findById(userId).orElseThrow(UserNotFoundException::new);
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

  @Override
  public List<UserResponse> getUsersByIds(List<Integer> userIds) {
    log.info("(getUsersByIds) userIds: {}", userIds);
    return repository.findUsersByIds(userIds);
  }
  @Override
  @Transactional
  public void createInactiveUser(AuthRegisterRequest request, Integer mediaId) {
    log.info("(createInactiveUser) request: {}", request);

    this.checkEmailExists(request.getEmail());
    this.checkUsernameExists(request.getUsername());

    User user = User.of(
          request.getEmail(),
          getPasswordEncoder().encode(request.getPassword()),
          request.getUsername(),
          request.getIsAdmin()
    );

    user.setMediaId(mediaId);

    repository.save(user);
  }

  @Override
  public void activeUser(String email) {
    log.info("(activeUser) email: {}", email);

    User user = repository.findByEmail(email).orElseThrow(UserNotFoundException::new);

    if (Boolean.TRUE.equals(user.getIsActive())) {
      throw new EmailExistedException();
    }

    user.setIsActive(true);
    repository.save(user);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteInactiveUser(String email) {
    log.info("(deleteInactiveUser) email: {}", email);

    User user = repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    Integer mediaId = user.getMediaId();

    repository.delete(user);
    mediaService.delete(mediaId);
  }

  @Override
  public UserDTO get(Integer id) {
    User user = repository.findById(id).orElseThrow(UserNotFoundException::new);

    return UserDTO.of(
          user.getId(),
          user.getUsername()
    );
  }

  @Override
  public void updateInformation(Integer id, String password, String username) {
    log.info("(updatePassword) id:{}, password: {}", id, password);

    User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
    this.checkUsernameExists(username);
    user.setPassword(getPasswordEncoder().encode(password));
    user.setUsername(username);
    repository.save(user);
  }

  private User find(Integer id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  private void updateField(User user, UserUpdateRequest request) {
    user.setUsername(request.getUsername());
  }

  private UserResponse toDTO(User user) {
    return UserResponse.of(
          user.getId(),
          user.getEmail(),
          user.getUsername(),
          user.getIsAdmin()
    );
  }

  private void checkEmailExists(String email) {
    if (Boolean.TRUE.equals(repository.existsByEmail(email))) {
      throw new EmailExistedException();
    }
  }

  private void checkUsernameExists(String username) {
    if (Boolean.TRUE.equals(repository.existsByUsername(username))) {
      throw new UsernameExistedException();
    }
  }
}
