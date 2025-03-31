package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
