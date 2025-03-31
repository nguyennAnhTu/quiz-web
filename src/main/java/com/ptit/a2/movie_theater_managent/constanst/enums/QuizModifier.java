package com.ptit.a2.movie_theater_managent.constanst.enums;

public enum QuizModifier {
  PRIVATE(-1),
  PUBLISH(1);

  private final int value;

  QuizModifier(int value) {
    this.value = value;
  }
}
