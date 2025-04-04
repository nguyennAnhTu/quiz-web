package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
  List<Answer> findAllByQuestionId(Integer questionId);
}
