package com.ptit.a2.movie_theater_managent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_session_answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSessionAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "session_id")
  private Integer sessionId;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "question_id")
  private Integer questionId;

  @Column(name = "answer_id")
  private Integer answerId;

  @Column(name = "is_correct")
  private Boolean isCorrect;

  @Column(name = "submitted_at")
  private Long submittedAt;
}
