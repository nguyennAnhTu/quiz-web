package com.ptit.a2.movie_theater_managent.repository;

import com.ptit.a2.movie_theater_managent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);
}
