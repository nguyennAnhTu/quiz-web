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
@Table(name = "quizzes")
public class Quiz extends AuditEntity {
  private String name;
  private String description;
  private String mediaLink;
  private Integer modifier;
  private Double rating;

  private Quiz (String name, String description, String mediaLink, Integer modifier) {
    this.name = name;
    this.description = description;
    this.mediaLink = mediaLink;
    this.modifier = modifier;
  }

  public static Quiz of(String name, String description, String mediaLink, Integer modifier) {
    return new Quiz(name, description, mediaLink, modifier);
  }
}
