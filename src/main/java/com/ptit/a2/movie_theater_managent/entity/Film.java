package com.ptit.a2.movie_theater_managent.entity;

import com.ptit.a2.movie_theater_managent.entity.base.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Table(name = "films")
public class Film extends AuditEntity {
  private String name;
  private String description;
  private Integer duration;
  private Integer ageLimit;
  private Date releaseDate;
  private String thumbnailUrl;
  private String trailerUrl;
}
