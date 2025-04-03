package com.ptit.a2.movie_theater_managent.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class AuditEntity extends BaseEntity {
  @CreatedBy
  private Integer createdBy;

  @CreatedDate
  private Long createdAt;

  @LastModifiedBy
  private Integer lastUpdatedBy;

  @LastModifiedDate
  private Long lastUpdatedAt;
}
