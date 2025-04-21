package com.ptit.a2.movie_theater_managent.entity;

import com.ptit.a2.movie_theater_managent.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiz_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSession  extends AuditEntity {
  @Column(name = "quiz_id")
  private Integer quizId;

  @Column(name = "session_code", length = 50)
  private String sessionCode;

  @Column(name = "status", length = 255)
  private Status status;

  @Column(name = "current_question_id")
  private Integer currentQuestionId;

  @Column(name = "start_time")
  private Long startTime;

  @Column(name = "end_time")
  private Long endTime;
  public enum Status {
    WAITING,    // Đang chờ
    STARTED,    // Đang diễn ra
    PAUSED,     // Tạm dừng
    ENDED       // Kết thúc
  }
}