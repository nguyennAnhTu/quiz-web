package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuizRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuizResponse;
import com.ptit.a2.movie_theater_managent.entity.Quiz;
import com.ptit.a2.movie_theater_managent.exception.quiz.QuizNotFoundException;
import com.ptit.a2.movie_theater_managent.repository.QuizRepository;
import com.ptit.a2.movie_theater_managent.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils.getCurrentUserId;

@Slf4j
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
  private final QuizRepository repository;

  @Override
  @Transactional
  public QuizResponse create(QuizRequest request) {
    log.info("(create) createQuiz request: {}", request);
      Quiz quiz = Quiz.of(
            request.getName(),
            request.getDescription(),
            request.getMediaLink(),
            request.getModifier()
      );

      repository.save(quiz);
      return this.toDTO(quiz);
  }

  @Override
  public QuizResponse find(Integer id) {
    log.info("(find) findQuiz request: {}", id);

    return this.toDTO(this.get(id));
  }

  @Override
  public QuizResponse update(Integer id, QuizRequest request) {
    log.info("(update) updateQuiz request: {}", request);

    Quiz quiz = this.get(id);
    quiz.setName(request.getName());
    quiz.setDescription(request.getDescription());
    quiz.setMediaLink(request.getMediaLink());
    quiz.setModifier(request.getModifier());

    return this.toDTO(repository.save(quiz));
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

  private QuizResponse toDTO(Quiz quiz) {
    return QuizResponse.of(
          quiz.getId(),
          quiz.getName(),
          quiz.getDescription(),
          quiz.getMediaLink(),
          quiz.getModifier(),
          quiz.getRating(),
          quiz.getCreatedBy(),
          null
    );
  }

  private Quiz get(Integer id) {
    Integer userId = getCurrentUserId();

    Quiz quiz = repository.findById(id).orElseThrow(QuizNotFoundException::new);
    if (quiz.getModifier()==0 && !Objects.equals(userId, quiz.getCreatedBy())) {
      throw new QuizNotFoundException();
    }

    return quiz;
  }
}
