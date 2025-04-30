package com.ptit.a2.movie_theater_managent.entity;

import com.ptit.a2.movie_theater_managent.entity.base.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "medias")
public class Media extends AuditEntity {
  private String mediaLink;
  private Float zoom;

  @Column(name = "offset_x")
  private Float offsetX;

  @Column(name = "offset_y")
  private Float offsetY;
}
