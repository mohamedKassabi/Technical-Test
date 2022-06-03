package com.technical.test.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technical.test.dto.UserDto;
import com.technical.test.entities.User;
import com.technical.test.exception.InvalidUserException;
import com.technical.test.exception.UserNotFoundException;
import com.technical.test.services.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	// endpoint to register a user
	@ApiOperation(value = "allows to register a user")
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody @Valid UserDto userRequest) throws InvalidUserException {
		return new ResponseEntity<User>(userService.saveUser(userRequest), HttpStatus.CREATED);
	}

	// endpoint to displays the details of a registred user
	@ApiOperation(value = "displays the details of a registered user")
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
		return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
	}
}
