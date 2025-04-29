package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.QuestionRequest;
import com.ptit.a2.movie_theater_managent.dto.response.QuestionResponse;
import com.ptit.a2.movie_theater_managent.entity.Question;
import com.ptit.a2.movie_theater_managent.exception.question.QuestionNotFoundException;
import com.ptit.a2.movie_theater_managent.repository.QuestionRepository;
import com.ptit.a2.movie_theater_managent.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository repository;

  @Override
  public QuestionResponse create(QuestionRequest request, Integer mediaId) {
    log.info("create question request: {}", request);

    Question question = Question.of(
          request.getContent(),
          mediaId,
          request.getFunFact(),
          request.getQuizId(),
          request.getQuestionOrder(),
          request.getTime()
    );

    return this.toDTO(repository.save(question));
  }

  @Override
  public Question find(Integer id) {
    log.info("find question by id: {}", id);

    return this.get(id);
  }

  @Override
  public QuestionResponse update(Integer id, QuestionRequest request) {
    log.info("update question request: id={}, request={}", id, request);

    // Tìm question hiện tại theo id
    Question existingQuestion = this.get(id);

    // Cập nhật các thuộc tính từ request
    existingQuestion.setContent(request.getContent());
    //existingQuestion.setMediaLink(request.getMediaLink());
    existingQuestion.setFunFact(request.getFunFact());
    existingQuestion.setQuizId(request.getQuizId());
    existingQuestion.setQuestionOrder(request.getQuestionOrder());
    existingQuestion.setTime(request.getTime());

    // Lưu và trả về response
    return this.toDTO(repository.save(existingQuestion));
  }

  @Override
  public List<Question> findByQuizId(Integer quizId) {
    log.info("find question by quiz id: {}", quizId);

    return repository.findAllByQuizId(quizId);
  }

  @Override
  public void deleteByQuizId(Integer quizId) {
    log.info("delete question request: {}", quizId);

    repository.deleteAllByQuizId(quizId);
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) id:{}", id);

    repository.delete(this.get(id));
  }

  private QuestionResponse toDTO(Question question) {
    return QuestionResponse.of(
          question.getId(),
          question.getContent(),
          null,
          question.getFunFact(),
          question.getQuizId(),
          question.getTime(),
          question.getQuestionOrder()
    );
  }

  private Question get(Integer id) {
    return repository.findById(id).orElseThrow(QuestionNotFoundException::new);
  }
}
