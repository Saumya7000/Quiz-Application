package com.quizserver.quizserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizserver.quizserver.entities.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

	
}

