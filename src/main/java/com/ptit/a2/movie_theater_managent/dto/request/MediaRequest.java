package com.ptit.a2.movie_theater_managent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class MediaRequest {
  private String mediaLink;
  private Float zoom;
  private Float offsetX;
  private Float offsetY;
}
