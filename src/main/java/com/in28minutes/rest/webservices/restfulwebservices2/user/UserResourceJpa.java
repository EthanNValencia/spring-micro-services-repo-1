package com.in28minutes.rest.webservices.restfulwebservices2.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.in28minutes.rest.webservices.restfulwebservices2.user.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices2.user.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserResourceJpa {

	private UserRepository userRepository;
	private PostRepository postRepository;

	public UserResourceJpa(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@GetMapping(path = "/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> getUserById(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<User> postNewUser(@Valid @RequestBody User user) {
		System.out.println(user);
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> getPostsForUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		return user.get().getPosts();
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createNewPostForUser(@Valid @RequestBody Post post, @PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " was not found.");
		}
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path = "/jpa/users/{userId}/posts/{postId}")
	public Post getPost(@PathVariable Integer userId, @PathVariable Integer postId) {
		Optional<User> user = userRepository.findById(userId);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + userId + " was not found.");
		}
		Optional<Post> post = user.get().getPosts().stream().filter(p -> p.getId() == postId).findFirst();
		if(post==null) {
			throw new PostNotFoundException("Post with id:" + postId + " was not found.");
		}
		return post.get();
	}
}
