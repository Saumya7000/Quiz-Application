package com.quizserver.quizserver.Service.User;

import org.springframework.stereotype.Service;

import com.quizserver.quizserver.entities.User;

@Service
public interface UserService {
	User createUser(User user);
	Boolean hasUserWithEmail(String email);
	
	User login(User user);
}
