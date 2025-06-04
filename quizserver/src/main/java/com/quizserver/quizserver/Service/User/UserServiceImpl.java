package com.quizserver.quizserver.Service.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizserver.quizserver.UserRole;
import com.quizserver.quizserver.entities.User;
import com.quizserver.quizserver.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	private void createAdminUser()
	{
		User optionalUser = userRepository.findByRole(UserRole.ADMIN);
		if(optionalUser == null)
		{
			User user = new User();
			user.setName("Admin");
			user.setEmail("admin@gmail.com");
			user.setRole(UserRole.ADMIN);
			user.setPassword("admin");
			
			userRepository.save(user);
		}
	}
	public Boolean hasUserWithEmail(String email)
	{
		return userRepository.findFirstByEmail(email) != null;
	}
	
	public User createUser(User user)
	{
		user.setRole(UserRole.USER);
		return userRepository.save(user);
	}
	
	public User login(User user)
	{
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		if(optionalUser.isPresent() && user.getPassword().equals(optionalUser.get().getPassword()))
		{
			return optionalUser.get();
		}
		return null;
	}
}
