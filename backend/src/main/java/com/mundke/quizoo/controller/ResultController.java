package com.mundke.quizoo.controller;

import com.mundke.quizoo.dto.QuizAttemptDTO;
import com.mundke.quizoo.model.Result;
import com.mundke.quizoo.service.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping("/submit")
    public Result submitQuiz(@RequestBody QuizAttemptDTO dto) {
        return resultService.submitQuiz(dto);
    }

    @GetMapping("/me")
    public List<Result> myResults(Authentication authentication) {
        return resultService.getResultsForUser(authentication.getName());
    }

    @GetMapping
    public List<Result> allResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/quiz/{quizId}")
    public List<Result> quizResults(@PathVariable Long quizId) {
        return resultService.getResultsForQuiz(quizId);
    }
}