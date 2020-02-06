package com.lokesh.SpringBoot.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJpaController {

	@Autowired
	private UserDAO service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Optional<User> getUser(@PathVariable int id)
	{
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent())
		{
			throw new UserNotFoundException("id ---"+id);
		}
		return user;
	}
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@RequestBody User user)
	{
		User savedUser=userRepository.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getAllPost(@PathVariable Integer id)
	{
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent())
		{
			throw new UserNotFoundException("id ---"+id);
		}
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id, @RequestBody Post post)
	{
		Optional<User> userOptional=userRepository.findById(id);
		if(!userOptional.isPresent())
		{
			throw new UserNotFoundException("id ---"+id);
		}
		
		User user=userOptional.get();
		post.setUser(user);		
		postRepository.save(post);
		
		User savedUser=userRepository.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}
