package com.in28minutes.rest.webservices.restfulwebservices2.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	private UserDAOService userDAOService;

	public UserResource(UserDAOService userDAOService) {
		this.userDAOService = userDAOService;
	}

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return userDAOService.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public User getUserById(@PathVariable Integer id) {
		User user = userDAOService.findById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		return user;
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<User> postNewUser(@RequestBody User user) {
		System.out.println(user);
		User savedUser = userDAOService.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/users/{id}")
	public User deleteUserById(@PathVariable Integer id) {
		User user = userDAOService.deleteById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		return user;
	}
}
