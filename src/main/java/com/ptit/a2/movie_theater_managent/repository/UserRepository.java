package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  @Query("""
        SELECT new com.ptit.a2.movie_theater_managent.dto.response.UserResponse(
                u.id, u.email,u.username, u.isAdmin, null
                ) FROM User u WHERE
                :keyword = '' OR
                LOWER(u.username) ILIKE %:keyword% OR
                LOWER(u.email) ILIKE %:keyword%
        """)
  Page<UserResponse> list(@Param("keyword") String keyword, Pageable pageable);

  @Query("""
        SELECT u FROM User u WHERE
                :keyword = '' OR
                LOWER(u.username) ILIKE %:keyword% OR
                LOWER(u.email) ILIKE %:keyword%
        """)
  List<UserResponse> listAll(@Param("keyword") String keyword);

  Boolean existsByUsername(String username);

  @Query("""
        SELECT new com.ptit.a2.movie_theater_managent.dto.response.UserResponse(
                u.id, u.email, u.username, u.isAdmin, null
                ) FROM User u WHERE u.id IN :userIds
        """)
  List<UserResponse> findUsersByIds(@Param("userIds") List<Integer> userIds);
}
