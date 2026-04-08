package com.mundke.quizoo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mundke.quizoo.model.Quiz;
import com.mundke.quizoo.repository.QuizRepository;
import com.mundke.quizoo.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz createQuiz(Quiz quiz) {
        quiz.setCreatedAt(LocalDateTime.now());
        bindRelationships(quiz);
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Long id, Quiz quiz) {
        Quiz existingQuiz = quizRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));

        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setDescription(quiz.getDescription());
        existingQuiz.setQuestions(quiz.getQuestions());
        bindRelationships(existingQuiz);

        return quizRepository.save(existingQuiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("Quiz not found");
        }
        quizRepository.deleteById(id);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    private void bindRelationships(Quiz quiz) {
        if (quiz.getQuestions() == null) {
            return;
        }

        quiz.getQuestions().forEach(question -> {
            question.setQuiz(quiz);

            if (question.getOptions() != null) {
                question.getOptions().forEach(option -> option.setQuestion(question));
            }
        });
    }
}