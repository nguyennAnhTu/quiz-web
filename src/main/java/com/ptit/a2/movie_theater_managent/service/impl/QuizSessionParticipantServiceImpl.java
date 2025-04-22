package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;
import com.ptit.a2.movie_theater_managent.repository.QuizSessionParticipantRepository;
import com.ptit.a2.movie_theater_managent.service.QuizSessionParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizSessionParticipantServiceImpl implements QuizSessionParticipantService {
  private final QuizSessionParticipantRepository repository;

  @Override
  @Transactional
  public void save(QuizSessionParticipant participant) {
    log.info("(save) participant: {}", participant);
    repository.save(participant);
  }

  @Override
  public boolean existsBySessionIdAndUserId(Integer sessionId, Integer userId) {
    log.info("(existsBySessionIdAndUserId) sessionId: {}, userId: {}", sessionId, userId);
    return repository.existsBySessionIdAndUserId(sessionId, userId);
  }

  @Override
  public List<QuizSessionParticipant> findBySessionId(Integer quizSessionId) {
    return repository.findBySessionId(quizSessionId);
  }
}