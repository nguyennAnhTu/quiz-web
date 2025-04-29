package com.ptit.a2.movie_theater_managent.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationDto {
  private NotificationType type;
  private Object data;


  public enum NotificationType {
    RELOAD_USER,
    NEXT_QUESTION,
    CHANGE_STATUS_ROOM,
    LEADERBOARD
  }
}
