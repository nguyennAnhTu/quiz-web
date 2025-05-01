package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;

public interface QuizSessionFacadeService {
  void joinQuiz(String sessionCode);

  void startQuiz(Integer quizSessionId);

  void pauseQuiz(Integer quizSessionId);

  void endQuiz(Integer quizSessionId);

  void nextQuestion(Integer quizSessionId, Integer currentQuestionId);

  void outQuiz(Integer quizSessionId);

  QuizSessionAnswerResponse submitAnswer(Integer quizSessionId, QuizSessionAnswerRequest request);
}
