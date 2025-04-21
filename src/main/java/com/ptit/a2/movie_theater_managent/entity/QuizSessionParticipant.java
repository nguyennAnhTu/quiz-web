package com.ptit.a2.movie_theater_managent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_session_participants")
public class QuizSessionParticipant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "session_id")
  private Integer sessionId;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "join_time")
  private Long joinTime;

  @Column(name = "score")
  private Integer score;

}
