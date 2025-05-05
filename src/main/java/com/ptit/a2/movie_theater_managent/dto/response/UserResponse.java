package com.ptit.a2.movie_theater_managent.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {
  private Integer id;
  private String email;
  private String username;
  private Boolean isAdmin;
  private Integer imageId;
  private MediaResponse media;

  public UserResponse(Integer id, String email, String username, Boolean isAdmin, MediaResponse media) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.isAdmin = isAdmin;
    this.media = media;
  }
}
