package com.in28minutes.rest.webservices.restfulwebservices2.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservices2.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
