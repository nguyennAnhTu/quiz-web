package com.ptit.a2.movie_theater_managent.entity;

import com.ptit.a2.movie_theater_managent.entity.base.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Table(name = "questions")
public class Question extends AuditEntity {
  private String content;
  private String mediaLink;
  private String funFact;
  private Integer quizId;
  private Integer questionOrder;
  private Integer time;
}
