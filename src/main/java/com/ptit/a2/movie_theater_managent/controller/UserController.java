package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.user.ChangePasswordRequest;
import com.ptit.a2.movie_theater_managent.dto.request.user.UserUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizDTO;
import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import com.ptit.a2.movie_theater_managent.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final QuizFacadeService quizFacadeService;
  private final QuizService quizService;

  // Lấy thông tin chi tiết user
  @GetMapping("/{id}")
  public ResponseGeneral<UserResponse> getUserDetail(
        @PathVariable("id") Integer id
  ) {
    log.info("Start getUserDetail");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          userService.detail(id)
    );
  }

  // Cập nhật thông tin user
  @PutMapping("/{id}")
  public ResponseGeneral<UserResponse> updateUser(
        @PathVariable("id") Integer id,
        @RequestBody @Valid UserUpdateRequest request
  ) {
    log.info("Start updateUser");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          userService.update(id, request)
    );
  }

  // Xóa user
  @DeleteMapping("/{id}")
  public ResponseGeneral<Void> deleteUser(
        @PathVariable("id") Integer id
  ) {
    log.info("Start deleteUser");
    userService.delete(id);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Đổi mật khẩu user
  @PostMapping("/{id}/change-password")
  public ResponseGeneral<Void> changePassword(
        @PathVariable("id") Integer id,
        @RequestBody @Valid ChangePasswordRequest request
  ) {
    log.info("Start changePassword");
    userService.changePassword(id, request);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Lấy danh sách users
  @GetMapping
  public ResponseGeneral<PageResponse<UserResponse>> listUsers(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "false") boolean isAll
  ) {
    log.info("Start listUsers - keyword: {}, page: {}, size: {}, isAll: {}",
          keyword, page, size, isAll);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          userService.list(keyword, page, size, isAll)
    );
  }

  @GetMapping("/quizzes")
  public ResponseGeneral<List<QuizDTO>> listQuiz(
        @RequestParam(required = false) Integer modifier
  ) {
    log.info("Start listQuizzes");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizFacadeService.findByCreatedBy(modifier)
    );
  }
}
