package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizDTO;
import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.entity.Quiz;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.quiz.QuizNotFoundException;
import com.ptit.a2.movie_theater_managent.repository.QuizRepository;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils.getCurrentUserId;

@Slf4j
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
  private final QuizRepository repository;
  private static final List<String> VALID_SORT_FIELDS = List.of("name", "createdAt", "rating");

  @Override
  @Transactional
  public Quiz create(QuizRequest request, Integer mediaId) {
    log.info("(create) createQuiz request: {}", request);
      Quiz quiz = Quiz.of(
            request.getName(),
            request.getDescription(),
            mediaId,
            request.getModifier()
      );

      repository.save(quiz);
      return quiz;
  }

  @Override
  public Quiz find(Integer id) {
    log.info("(find) findQuiz request: {}", id);


    return this.get(id);
  }

  @Override
  public Quiz update(Quiz quiz) {
    log.info("(update) updateQuiz quiz: {}", quiz);

    return repository.save(quiz);
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) deleteQuiz request: {}", id);

    Quiz quiz = this.get(id);
    repository.delete(quiz);
  }

  @Override
  public boolean exist(Integer id) {
    return repository.existsById(id);
  }

  @Override
  public Integer findMediaId(Integer id) {
    log.info("(findMediaId) quiz id request: {}", id);

    Quiz quiz = this.get(id);
    if (quiz.getMediaId() != null) {
      return quiz.getMediaId();
    }
    return null;
  }

  @Override
  public List<Quiz> findByIdIn(List<Integer> ids) {

    return repository.findByIdIn(ids);
  }

  @Override
  public List<QuizProjection> findByCreatedBy(Integer modifier) {
    Integer userId = getCurrentUserId();
    log.info("(findByCreatedBy) findQuiz request: {}", userId);

    return repository.findByCreatedBy(userId, modifier);
  }

  @Override
  public List<Quiz> findByKeyword(String keyword, String sortBy, String order) {
    log.info("(findByKeyword) findQuiz request: {}", keyword);

    return repository.findAllByKeyword(keyword, sortBy, order);
  }

  private QuizResponse toDTO(Quiz quiz) {
    return QuizResponse.of(
          quiz.getId(),
          quiz.getName(),
          quiz.getDescription(),
          null,
          quiz.getModifier(),
          quiz.getRating(),
          null,
          null,
          null
    );
  }

  private Quiz get(Integer id) {
    Integer userId = getCurrentUserId();
    log.info("userId: {}", userId);

    Quiz quiz = repository.findById(id).orElseThrow(QuizNotFoundException::new);
    if (((userId == null) || !Objects.equals(userId, quiz.getCreatedBy())) && quiz.getModifier() != 1) {
      throw new QuizNotFoundException();
    }

    return quiz;
  }
}
