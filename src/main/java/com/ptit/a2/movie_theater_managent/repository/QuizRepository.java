package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.dto.response.QuizProjection;
import com.ptit.a2.movie_theater_managent.entity.Quiz;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

  @Query("""
    SELECT q from Quiz q
    WHERE q.createdBy=:createdBy AND (:modifier is NULL or q.modifier = :modifier)
    """)
  List<Quiz> findByCreatedBy(
        @Param("createdBy") Integer createdBy,
        @Param("modifier") Integer modifier
  );

  @Query("""
    SELECT q
    FROM Quiz q
    WHERE ((:keyword = '') OR (LOWER(q.name) LIKE LOWER(CONCAT('%', :keyword, '%'))))
      AND q.modifier = 1
    ORDER BY
      CASE WHEN :sortBy = 'createdAt' AND :order = 'desc' THEN q.createdAt END DESC,
      CASE WHEN :sortBy = 'createdAt' AND :order = 'asc' THEN q.createdAt END ASC,
      CASE WHEN :sortBy = 'rating' AND :order = 'desc' THEN q.rating END DESC,
      CASE WHEN :sortBy = 'rating' AND :order = 'asc' THEN q.rating END ASC,
      CASE WHEN :sortBy = 'name' AND :order = 'desc' THEN q.name END DESC,
      CASE WHEN :sortBy = 'name' AND :order = 'asc' THEN q.name END ASC
""")
  List<Quiz> findAllByKeyword(
        @Param("keyword") String keyword,
        @Param("sortBy") String sortBy,
        @Param("order") String order
  );

  List<Quiz> findByIdIn(List<Integer> ids);
}
