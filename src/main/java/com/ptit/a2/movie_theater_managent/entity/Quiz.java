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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quizzes")
public class Quiz extends AuditEntity {
  private String title;
  private String description;
  private String mediaLink;

}
