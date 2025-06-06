package com.quizserver.quizserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizserver.quizserver.entities.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

	List<TestResult> findAllByUserId(Long userId);
}
