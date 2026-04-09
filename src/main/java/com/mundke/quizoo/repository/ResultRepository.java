package com.mundke.quizoo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundke.quizoo.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
	Optional<Result> findByUserIdAndQuizId(Long userId, Long quizId);
	List<Result> findByUserUsername(String username);
	List<Result> findByQuizId(Long quizId);
	void deleteByQuizId(Long quizId);
}