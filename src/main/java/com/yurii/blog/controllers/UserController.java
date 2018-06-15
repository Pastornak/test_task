package com.yurii.blog.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yurii.blog.entity.*;
import com.yurii.blog.repositories.*;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
	
	@GetMapping("/users/age/{age}")
    public List<User> getAllUsersWithAgeBigger(@PathVariable int age) {
        return userRepository.findAll().stream().filter(user -> user.getAge() > age).collect(Collectors.toList());
    }
	
	@GetMapping("/users/biggest_poster")
    public Set<String> getAllUsersWithMoreThan3Articles() {
		return userRepository.findAll().stream().filter(user -> user.getArticles().size() >= 3).map(User::getName).collect(Collectors.toSet());
    }

    @PostMapping("/users")
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, User requestUser) {
    	return userRepository.findById(id).map(user -> {
    		user.setName(requestUser.getName());
    		user.setAge(requestUser.getAge());
    		return userRepository.save(user);
    	}).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
        	userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
}
