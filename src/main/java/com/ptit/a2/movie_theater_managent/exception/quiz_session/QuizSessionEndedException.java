package com.ptit.a2.movie_theater_managent.exception.quiz_session;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.BAD_REQUEST;

public class QuizSessionEndedException extends BaseException {
    public QuizSessionEndedException() {
        super(BAD_REQUEST, "BAD REQUEST", "Quiz session has already ended");
    }
}