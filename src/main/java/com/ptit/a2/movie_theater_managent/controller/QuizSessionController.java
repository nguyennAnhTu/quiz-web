package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionCreateRequest;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse;

import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;
import com.ptit.a2.movie_theater_managent.facade.QuizSessionFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/quiz-sessions")
@Slf4j
@RequiredArgsConstructor
public class QuizSessionController {
  private final QuizSessionService quizSessionService;
  private final QuizSessionFacadeService quizSessionFacadeService;

  // Tạo mới quiz session
  @PostMapping
  public ResponseGeneral<QuizSessionResponse> createQuizSession(
        @RequestBody @Valid QuizSessionCreateRequest request
  ) {
    log.info("Start createQuizSession");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionService.create(request)
    );
  }

  // Lấy thông tin chi tiết quiz session
  @GetMapping("/{id}")
  public ResponseGeneral<QuizSessionResponse> getQuizSessionDetail(
        @PathVariable("id") Integer id
  ) {
    log.info("Start getQuizSessionDetail");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionService.detail(id)
    );
  }

  @GetMapping("/detail")
  public ResponseGeneral<QuizSessionResponse> getQuizSessionDetailByCode(
        @RequestParam("code") String code
  ) {
    log.info("Start getQuizSessionDetail");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionService.detail(code)
    );
  }

  // Cập nhật quiz session
  @PutMapping("/{id}")
  public ResponseGeneral<QuizSessionResponse> updateQuizSession(
        @PathVariable("id") Integer id,
        @RequestBody @Valid QuizSessionUpdateRequest request
  ) {
    log.info("Start updateQuizSession");
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionService.update(id, request)
    );
  }

  // Xóa quiz session
  @DeleteMapping("/{id}")
  public ResponseGeneral<Void> deleteQuizSession(
        @PathVariable("id") Integer id
  ) {
    log.info("Start deleteQuizSession");
    quizSessionService.delete(id);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Lấy danh sách quiz sessions
  @GetMapping
  public ResponseGeneral<PageResponse<QuizSessionResponse>> listQuizSessions(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "false") boolean isAll
  ) {
    log.info("Start listQuizSessions - keyword: {}, page: {}, size: {}, isAll: {}",
          keyword, page, size, isAll);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionService.list(keyword, page, size, isAll)
    );
  }

  @PostMapping("/join")
  public ResponseGeneral<String> joinQuiz(
        @RequestParam("session_code") String sessionCode
  ) {
    log.info("Start joinQuiz - sessionCode: {}", sessionCode);

    quizSessionFacadeService.joinQuiz(sessionCode);
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          "User joined quiz session successfully"
    );
  }

  @PostMapping("/{quizSessionId}/start")
  public ResponseGeneral<Void> startQuiz(
        @PathVariable("quizSessionId") Integer quizSessionId
  ) {
    log.info("Start startQuiz - quizSessionId: {}", quizSessionId);

    quizSessionFacadeService.startQuiz(quizSessionId);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Tạm dừng quiz session
  @PostMapping("/{quizSessionId}/pause")
  public ResponseGeneral<Void> pauseQuiz(
        @PathVariable("quizSessionId") Integer quizSessionId
  ) {
    log.info("Start pauseQuiz - quizSessionId: {}", quizSessionId);

    quizSessionFacadeService.pauseQuiz(quizSessionId);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Kết thúc quiz session
  @PostMapping("/{quizSessionId}/end")
  public ResponseGeneral<Void> endQuiz(
        @PathVariable("quizSessionId") Integer quizSessionId
  ) {
    log.info("Start endQuiz - quizSessionId: {}", quizSessionId);

    quizSessionFacadeService.endQuiz(quizSessionId);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  // Chuyển sang câu hỏi tiếp theo
  @PostMapping("/{quizSessionId}/next-question")
  public ResponseGeneral<Void> nextQuestion(
        @PathVariable("quizSessionId") Integer quizSessionId,
        @RequestParam("current_question_id") Integer currentQuestionId
  ) {
    log.info("Start nextQuestion - quizSessionId: {}, currentQuestionId: {}", quizSessionId, currentQuestionId);

    quizSessionFacadeService.nextQuestion(quizSessionId, currentQuestionId);
    return ResponseGeneral.ofSuccess(
          SUCCESS
    );
  }

  @PostMapping("/{quizSessionId}/out")
  public ResponseGeneral<String> outQuiz(
        @PathVariable("quizSessionId") Integer quizSessionId
  ) {
    log.info("Start outQuiz - quizSessionId: {}", quizSessionId);

    quizSessionFacadeService.outQuiz(quizSessionId);
    return ResponseGeneral.ofSuccess(
          SUCCESS,
          "User out quiz session successfully"
    );
  }

  @PostMapping("/{quizSessionId}/submit-answer")
  public ResponseGeneral<QuizSessionAnswerResponse> submitAnswer(
        @PathVariable("quizSessionId") Integer quizSessionId,
        @RequestBody @Valid QuizSessionAnswerRequest request
  ) {
    log.info("Start submitAnswer - quizSessionId: {}, request: {}", quizSessionId, request);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          quizSessionFacadeService.submitAnswer(quizSessionId, request)
    );
  }
}