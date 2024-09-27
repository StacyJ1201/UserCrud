package com.example.userscrud.controller;

import java.net.URI;
import java.util.List;

import com.example.userscrud.exception.DuplicateUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.userscrud.entity.Post;
import com.example.userscrud.entity.User;
import com.example.userscrud.repository.UserRepository;
import com.example.userscrud.service.PostService;
import com.example.userscrud.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	
	public UserController(UserService userService, PostService postService) {
		this.userService = userService;
		this.postService = postService;
	}
	
	
	@GetMapping("")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{email}")
	public User retrieveUser(@PathVariable String email) {
		return userService.getUser(email);
	}
	
	@DeleteMapping("/{email}")
	public void deleteUser(@PathVariable String email) {
		userService.deleteUser(email);
	}
	
	@PostMapping("")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userService.createUser(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{email}")
				.buildAndExpand(savedUser.getEmail()).toUri();
		// returning URI
		
		return ResponseEntity.created(location).build();
	}
	
	
	// To retrieve posts of User
	@GetMapping("/{email}/posts")
	public List<Post> retrievePosts(@PathVariable String email) {
		User user = userService.getUser(email);
		return user.getPosts();
	}
	
	@PostMapping("/{email}/posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable String email) {
		User postuser = userService.getUser(email);
		post.setUser(postuser);
		
		Post savedPost = postService.createPost(post);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("")
				.buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping("/test")
	public String test(){
		return "This shit is working";
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<?> deleteByName(@PathVariable String name){
		userService.deleteUserByName(name);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(DuplicateUsersException.class)
	public ResponseEntity<String> duplicateUsersException(DuplicateUsersException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	

}
