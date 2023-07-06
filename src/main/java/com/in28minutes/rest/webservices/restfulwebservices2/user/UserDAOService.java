package com.in28minutes.rest.webservices.restfulwebservices2.user;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

	public UserDAOService() {
		usersList = new ArrayList<User>();
		usersList.add(new User(arrayIdCounter++, "Natalia", LocalDate.now().minusYears(33)));
		usersList.add(new User(arrayIdCounter++, "Ethan", LocalDate.now().minusYears(31)));
		usersList.add(new User(arrayIdCounter++, "Tim", LocalDate.now().minusYears(26)));
	}

	private Integer arrayIdCounter = 0;

	private List<User> usersList;

	public List<User> findAll() {
		return usersList;
	}

	public User findById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		User user = usersList.stream().filter(predicate).findFirst().orElse(null); // If data isn't found it will return null.
		return user;
	}

	public User addUser(User user) {
		user.setId(arrayIdCounter++);
		usersList.add(user);
		return user;
	}

}
