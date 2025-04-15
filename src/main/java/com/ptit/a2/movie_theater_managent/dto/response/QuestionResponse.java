package com.ptit.a2.movie_theater_managent.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionResponse {
  private Integer id;
  private String content;
  private String mediaLink;
  private String funFact;
  private Integer quizId;
  private Integer time;
  private Integer questionOrder;
  private List<AnswerResponse> answer;

  public static QuestionResponse of(Integer id, String content, String mediaLink, String funFact, Integer quizId, Integer time, Integer order) {
    return new QuestionResponse(id, content, mediaLink, funFact, quizId, time, order);
  }

  private QuestionResponse(Integer id, String content, String mediaLink, String funFact, Integer quizId, Integer time, Integer questionOrder) {
    this.id = id;
    this.content = content;
    this.mediaLink = mediaLink;
    this.funFact = funFact;
    this.quizId = quizId;
    this.time = time;
    this.questionOrder = questionOrder;
  }
}
