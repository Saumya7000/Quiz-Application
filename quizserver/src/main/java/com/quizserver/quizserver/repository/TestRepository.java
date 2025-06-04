package com.quizserver.quizserver.repository;

import org.springframework.stereotype.Repository;

import com.quizserver.quizserver.entities.Test;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface TestRepository extends JpaRepository<Test, Long> {

}
