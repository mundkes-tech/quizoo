package com.mundke.quizoo.repository;

import com.mundke.quizoo.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}