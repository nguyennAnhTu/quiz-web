package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.entity.QuizTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizTagRepository extends JpaRepository<QuizTag, Integer> {
  void deleteAllByQuizId(Integer quizId);

  @Query("SELECT q.tagId FROM QuizTag q WHERE q.quizId = :quizId")
  List<Integer> findTagIdsByQuizId(@Param("quizId") Integer quizId);

  @Query("SELECT distinct q.quizId FROM QuizTag q WHERE q.tagId = :tagId")
  List<Integer> findQuizIdsByTagId(@Param("tagId") Integer tagId);
}
