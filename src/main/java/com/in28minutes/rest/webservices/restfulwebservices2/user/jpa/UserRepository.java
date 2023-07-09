package com.in28minutes.rest.webservices.restfulwebservices2.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservices2.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
