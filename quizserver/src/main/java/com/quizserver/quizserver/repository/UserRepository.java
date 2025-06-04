package com.quizserver.quizserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizserver.quizserver.UserRole;
import com.quizserver.quizserver.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByRole(UserRole role);
	
	User findFirstByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
