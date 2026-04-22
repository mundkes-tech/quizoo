package com.mundke.quizoo.service.impl;

import com.mundke.quizoo.dto.QuizAttemptDTO;
import com.mundke.quizoo.model.*;
import com.mundke.quizoo.repository.*;
import com.mundke.quizoo.service.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public Result submitQuiz(QuizAttemptDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return submitQuiz(dto.getQuizId(), dto.getAnswers(), user.getUsername());
    }

    @Override
    public Result submitQuiz(Long quizId, Map<Long, Long> answers, String username) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        resultRepository.findByUserIdAndQuizId(user.getId(), quizId)
                .ifPresent(r -> {
                    throw new IllegalStateException("You have already attempted this quiz");
                });

        int score = calculateScore(quiz, answers);

        Result result = new Result();
        result.setUser(user);
        result.setQuiz(quiz);
        result.setScore(score);
        result.setAttemptedAt(LocalDateTime.now());

        return resultRepository.save(result);
    }

    @Override
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public List<Result> getResultsForUser(String username) {
        return resultRepository.findByUserUsername(username);
    }

    @Override
    public List<Result> getResultsForQuiz(Long quizId) {
        return resultRepository.findByQuizId(quizId);
    }

    @Override
    public Optional<Result> getUserResultForQuiz(String username, Long quizId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return resultRepository.findByUserIdAndQuizId(user.getId(), quizId);
    }

    private int calculateScore(Quiz quiz, Map<Long, Long> answers) {
        if (answers == null) {
            return 0;
        }

        int score = 0;

        for (Question question : quiz.getQuestions()) {
            Long selectedOptionId = answers.get(question.getId());

            if (selectedOptionId == null) continue;

            for (Option option : question.getOptions()) {
                if (option.getId().equals(selectedOptionId) && option.isCorrect()) {
                    score++;
                }
            }
        }

        return score;
    }
}