package com.mundke.quizoo.service;

import java.util.List;

import com.mundke.quizoo.model.Quiz;

public interface QuizService {
    Quiz createQuiz(Quiz quiz);
    Quiz updateQuiz(Long id, Quiz quiz);
    void deleteQuiz(Long id);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
}