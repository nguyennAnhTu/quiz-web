package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.PageResponse;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionCreateRequest;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionUpdateRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;
import com.ptit.a2.movie_theater_managent.exception.authentication.UserNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.SessionCodeExistedException;
import com.ptit.a2.movie_theater_managent.repository.QuizSessionRepository;
import com.ptit.a2.movie_theater_managent.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BLANK;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuizSessionServiceImpl implements QuizSessionService {
  private final QuizSessionRepository repository;

  @Override
  @Transactional
  public QuizSessionResponse create(QuizSessionCreateRequest request) {
    log.info("(create quiz session) request: {}", request);

    this.checkSessionCodeExists(request.sessionCode());

    QuizSession quizSession = new QuizSession();
    quizSession.setQuizId(request.quizId());
    quizSession.setSessionCode(request.sessionCode());
    quizSession.setStatus(QuizSession.Status.valueOf(request.status()));
    quizSession.setCurrentQuestionId(request.currentQuestionId());

    repository.save(quizSession);

    return this.toDTO(quizSession);
  }

  @Override
  @Transactional
  public void save(QuizSession quizSession) {
    repository.save(quizSession);
  }

  @Override
  public QuizSession findById(Integer id) {
    log.info("(findById) id: {}", id);

    return repository.findById(id)
          .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public QuizSessionResponse update(Integer id, QuizSessionUpdateRequest request) {
    log.info("(update) id: {}, request: {}", id, request);

    final QuizSession quizSession = this.findById(id);
    this.updateField(quizSession, request);

    return this.toDTO(repository.save(quizSession));
  }

  @Override
  public QuizSessionResponse detail(Integer id) {
    log.info("(detail) id: {}", id);

    return this.toDTO(this.findById(id));
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) id: {}", id);

    final QuizSession quizSession = this.findById(id);
    repository.delete(quizSession);
  }

  @Override
  public PageResponse<QuizSessionResponse> list(String keyword, int page, int size, boolean isAll) {
    log.info("(list) keyword: {}, page: {}, size: {}, isAll: {}", keyword, page, size, isAll);

    if (isAll) {
      List<QuizSessionResponse> responses = repository.listAll(
            keyword == null ? BLANK : keyword
      );

      return PageResponse.of(responses, responses.size());
    } else {
      Page<QuizSessionResponse> responses = repository.list(
            keyword == null ? BLANK : keyword,
            PageRequest.of(page, size)
      );

      return PageResponse.of(responses.getContent(), (int) responses.getTotalElements());
    }
  }

  private void updateField(QuizSession quizSession, QuizSessionUpdateRequest request) {
    if (request.quizId() != null) {
      quizSession.setQuizId(request.quizId());
    }
    if (request.sessionCode() != null) {
      this.checkSessionCodeExists(request.sessionCode());
      quizSession.setSessionCode(request.sessionCode());
    }
    if (request.status() != null) {
      quizSession.setStatus(QuizSession.Status.valueOf(request.status()));
    }

    quizSession.setCurrentQuestionId(request.currentQuestionId());
  }

  private QuizSessionResponse toDTO(QuizSession quizSession) {
    return QuizSessionResponse.of(
          quizSession.getId(),
          quizSession.getQuizId(),
          quizSession.getSessionCode(),
          quizSession.getStatus(),
          quizSession.getCurrentQuestionId(),
          quizSession.getCreatedBy(),
          quizSession.getCreatedAt(),
          quizSession.getLastUpdatedBy(),
          quizSession.getLastUpdatedAt()
    );
  }

  private void checkSessionCodeExists(String sessionCode) {
    if (Boolean.TRUE.equals(repository.existsBySessionCode(sessionCode))) {
      throw new SessionCodeExistedException();
    }
  }
}