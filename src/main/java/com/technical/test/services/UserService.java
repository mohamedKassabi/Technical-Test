package com.technical.test.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technical.test.aop.TrackExecutionTime;
import com.technical.test.dao.UserRepository;
import com.technical.test.dto.UserDto;
import com.technical.test.entities.User;
import com.technical.test.exception.InvalidUserException;
import com.technical.test.exception.UserNotFoundException;

@Service
public class UserService {

	private static final String USER_NOT_FOUND_WITH_ID = "user not found with id : ";
	private static final String AGE_NOT_VALID = "age  not valid";
	private static final String USER_NATIONALITY_NOT_VALID = "user nationality not valid";
	private static final String FRANCE = "France";
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	// method to save user
	
	@TrackExecutionTime
	public User saveUser(UserDto userRequest) throws InvalidUserException {

		LocalDate localDate = Instant.ofEpochMilli(userRequest.getBirthDate().getTime()).atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate today = LocalDate.now();
		Period period = Period.between(localDate, today);
		if (!FRANCE.equalsIgnoreCase(userRequest.getCountryOfResidence())) {
			throw new InvalidUserException(USER_NATIONALITY_NOT_VALID);
		} else if (period.getYears() < 18) {
			throw new InvalidUserException(AGE_NOT_VALID);
		}

		User user = new User(userRequest.getName(), userRequest.getBirthDate(), userRequest.getCountryOfResidence(),
				userRequest.getPhoneNumber(), userRequest.getGender());

		return userRepository.save(user);

	}

	// method to get User by Id
	@TrackExecutionTime
	public User getUser(Long id) throws UserNotFoundException {
		User user = userRepository.findByUserId(id);
		if (user == null) {
			throw new UserNotFoundException(USER_NOT_FOUND_WITH_ID + id);
		}

		return user;

	}

}
