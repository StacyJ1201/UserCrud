package com.example.userscrud.service;

import java.util.List;

import com.example.userscrud.exception.DuplicateUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userscrud.entity.User;
import com.example.userscrud.exception.UserNotFoundException;
import com.example.userscrud.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUser(String email) {
		User user = userRepository.findByEmailAddress(email);
		if(user == null) {
			throw new UserNotFoundException("User with email : "+email+" doesn't exist.");
		}
		
		return user;
	}

	@Override
	public List<User> getUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public void deleteUser(String email) {
		User user = userRepository.findByEmailAddress(email);
		userRepository.delete(user);
	}

	@Override
	public void deleteUserByName(String name) {
		List<User> users = getUserByName(name);
		if(users.size() > 1){
			throw new DuplicateUsersException("You have more than one user with that name");
		} else if(users.size() == 1) {
			userRepository.deleteByName(name);
		} else {
			throw new UserNotFoundException("There are no users with that name");
		}
	}

}
