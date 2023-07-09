package com.in28minutes.rest.webservices.restfulwebservices2.user;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

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
	public EntityModel<User> getUserById(@PathVariable Integer id) {
		User user = userDAOService.findById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<User> postNewUser(@Valid @RequestBody User user) {
		System.out.println(user);
		User savedUser = userDAOService.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userDAOService.deleteById(id);
	}
}
