package com.example.userscrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.userscrud.entity.User;
import com.example.userscrud.service.UserService;

@RestController
@RequestMapping(path="/users", params="version=2", produces="application/json")
public class UserControllerV2 {

@Autowired
private UserService userService;
	

	@GetMapping("")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}



}
