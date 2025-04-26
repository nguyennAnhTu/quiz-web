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
@Table(name = "users")
public class User extends AuditEntity {
  private String email;
  private String password;
  private String username;
  private Integer mediaId;
  private Boolean isAdmin;
  private Boolean isActive = false;

  public static User of(String email, String password, String username, Boolean isAdmin) {
    return new User(email, password, username, isAdmin);
  }

  private User(String email, String password, String username, Boolean isAdmin) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.isAdmin = isAdmin;
  }
}
