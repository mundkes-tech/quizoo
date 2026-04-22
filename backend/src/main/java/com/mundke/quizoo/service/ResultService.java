package com.mundke.quizoo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mundke.quizoo.dto.QuizAttemptDTO;
import com.mundke.quizoo.model.Result;

public interface ResultService {
    Result submitQuiz(QuizAttemptDTO dto);
    Result submitQuiz(Long quizId, Map<Long, Long> answers, String username);
    List<Result> getAllResults();
    List<Result> getResultsForUser(String username);
    List<Result> getResultsForQuiz(Long quizId);
    Optional<Result> getUserResultForQuiz(String username, Long quizId);
}