package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.helper.UserNotFoundException;
import com.exam.helper.UserFoundException;
import com.exam.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//creating User
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		user.setProfile("default.png");
		
		//encoding Password with BCryptPasswordEncoder
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		
		
		Set<UserRole> roles=new HashSet<>();
		Role role=new Role();
//		//for normal User
		role.setRoleId(45L);
		role.setRoleName("Normal User");
		
//		//for admin 
//		role.setRoleId(50L); 
//		role.setRoleName("Admin User");
		
		UserRole userRole=new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		roles.add(userRole);
		return this.userService.createUser(user, roles);
		
	}
	
	@GetMapping("/{username}")
	public User getUserByUsername(@PathVariable("username") String username) {
		return this.userService.getUserByUsername(username);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUserById(@PathVariable("userId") Long userId) {
		this.userService.deleteUserById(userId);
		return "User with User ID: "+ userId + " is deleted Succesfully";
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?>exceptionHandler(UserNotFoundException ex){
		return new ResponseEntity<>(ex,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<?> handleUserFoundException(UserFoundException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
