package com.ptit.a2.movie_theater_managent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QuizDTO {
  private Integer id;
  private String name;
  private String mediaLink;
  private Integer createdBy;
  private Double rating;
  private Long createdAt;
  private List<TagResponse> tags;

  private QuizDTO (Integer id, String name, String mediaLink, Integer createdBy, Double rating, Long createdAt) {
    this.id = id;
    this.name = name;
    this.mediaLink = mediaLink;
    this.createdBy = createdBy;
    this.rating = rating;
    this.createdAt = createdAt;
  }

  public static QuizDTO of(Integer id, String name, String mediaLink, Integer createdBy, Double rating, Long createdAt) {
    return new QuizDTO(id, name, mediaLink, createdBy, rating, createdAt);
  }
}

