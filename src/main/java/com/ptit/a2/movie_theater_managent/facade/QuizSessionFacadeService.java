package com.ptit.a2.movie_theater_managent.facade;

public interface QuizSessionFacadeService {
  void joinQuiz(Integer quizSessionId);

  void startQuiz(Integer quizSessionId);

  void pauseQuiz(Integer quizSessionId);

  void endQuiz(Integer quizSessionId, String reason);
}
