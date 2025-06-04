package com.quizserver.quizserver.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizserver.quizserver.Service.User.UserService;
import com.quizserver.quizserver.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody User user) {
	    if (userService.hasUserWithEmail(user.getEmail())) {
	        return new ResponseEntity<>("User already Exists", HttpStatus.NOT_ACCEPTABLE);
	    }

	    User createdUser = userService.createUser(user);
	    if (createdUser == null) {
	        return new ResponseEntity<>("User not created, come again later", HttpStatus.NOT_ACCEPTABLE);
	            
	    }

	    return new ResponseEntity<>(createdUser, HttpStatus.OK);  // This will return user object in JSON
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user)
	{
		User dbUser = userService.login(user);
		if(dbUser == null)
		{
			return new ResponseEntity<>("Wrong Credentials", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(dbUser, HttpStatus.OK);
	}

}
