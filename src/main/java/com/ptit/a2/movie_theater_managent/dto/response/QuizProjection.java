package com.ptit.a2.movie_theater_managent.dto.response;

import java.util.List;

public interface QuizProjection {
  Integer getId();
  String getName();
  String getMediaLink();
  Integer getCreatedBy();
  Double getRating();
  Long getCreatedAt();
}
