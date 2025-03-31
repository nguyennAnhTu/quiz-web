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
@Table(name = "answers")
public class Answer extends AuditEntity {
  private String content;
  private Boolean isCorrect;
  private Integer questionId;
}
