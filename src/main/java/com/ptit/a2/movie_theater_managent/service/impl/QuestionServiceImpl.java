package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.entity.Question;
import com.ptit.a2.movie_theater_managent.repository.QuestionRepository;
import com.ptit.a2.movie_theater_managent.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository repository;

  @Override
  public QuestionResponse create(QuestionRequest request, Integer quizId) {
    log.info("create question request: {}", request);

    Question question = Question.of(
          request.getContent(),
          request.getMediaLink(),
          request.getFunFact(),
          quizId,
          request.getTime()
    );

    return this.toDTO(repository.save(question));
  }

  @Override
  public List<QuestionResponse> findByQuizId(Integer quizId) {
    List<Question> questions = repository.findAllByQuizId(quizId);

    return questions.stream().map(this::toDTO).toList();
  }

  @Override
  public void deleteByQuizId(Integer quizId) {
    log.info("delete question request: {}", quizId);

    repository.deleteAllByQuizId(quizId);
  }

  private QuestionResponse toDTO(Question question) {
    return QuestionResponse.of(
          question.getId(),
          question.getContent(),
          question.getMediaLink(),
          question.getFunFact(),
          question.getQuizId(),
          question.getTime()
    );
  }

}
