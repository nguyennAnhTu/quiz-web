package com.ptit.a2.movie_theater_managent.exception.quiz_session;

public class QuizSessionEndedException extends RuntimeException {
    public QuizSessionEndedException() {
        super("Quiz session has already ended");
    }
}