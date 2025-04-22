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
  private Integer quizSessionId;
  private Long timestamp;
  private String message;
  private Map<String, Object> data;

  public enum NotificationType {
    START_QUIZ,      // Quiz bắt đầu
    PAUSE_QUIZ,      // Quiz tạm dừng
    END_QUIZ,        // Quiz kết thúc
    LEADERBOARD_UPDATE // Cập nhật bảng xếp hạng
  }
}
