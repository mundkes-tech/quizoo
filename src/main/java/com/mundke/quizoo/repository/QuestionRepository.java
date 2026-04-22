package com.mundke.quizoo.repository;

import com.mundke.quizoo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}