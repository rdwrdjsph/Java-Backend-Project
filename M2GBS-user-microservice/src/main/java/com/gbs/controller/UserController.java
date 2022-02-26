package com.gbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbs.entity.User;
import com.gbs.entity.UsersResponse;
import com.gbs.repository.UserRepository;


@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	
	//GETTING THE USERS IN THE DATABASE
	@GetMapping("/validate")
	public UsersResponse loginValidation() {
		List<User> usersList = userRepository.findAll();
		UsersResponse usersResponse = new UsersResponse();
		usersResponse.setUsers(usersList);
		return usersResponse;	
	}
	
	@GetMapping("/user")
	public List<User> getAllUser(){
		List<User> userList = userRepository.findAll();
		return userList;
	}
	
	
	//UPDATING THE TOTAL ACCOUNT BALANCE OF SENDER
	@PutMapping("/updateFromAccountBalance/{userName}")
	public User updateFromAccountBalance(@PathVariable(value = "userName") String userName,@RequestBody User userFromClient) {	
		User existingUser = userRepository.findById(userName).get();
		float oldTotal = existingUser.getTotalBalance();
		float amountClient = userFromClient.getTotalBalance();
		float newTotal = oldTotal - amountClient;
		existingUser.setTotalBalance(newTotal);
		User updatedUser = userRepository.save(existingUser);

		return updatedUser;
	}
	
	//UPDATING THE TOTAL BALANCE OF RECEIVER
	@PutMapping("/updateToAccountBalance/{userName}")
		public User updateToAccountBalance(@PathVariable(value = "userName") String userName,@RequestBody User userFromClient) {
		User existingUser = userRepository.findById(userName).get();
		float oldTotal = existingUser.getTotalBalance();
		float amountClient = userFromClient.getTotalBalance();
		float newTotal = oldTotal + amountClient;
		existingUser.setTotalBalance(newTotal);
		User updatedUser = userRepository.save(existingUser);

		return updatedUser;
	}
}

