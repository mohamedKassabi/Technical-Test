package com.technical.test.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.technical.test.exception.InvalidDateException;
import com.technical.test.exception.InvalidUserException;
import com.technical.test.exception.UserNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	private static final String INVALID_REQUEST_PARAMETER = "Invalid request parameter";
	private static final String ERROR_MESSAGE = "errorMessage";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});

		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserNotFoundException.class)
	public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();

		errorMap.put(ERROR_MESSAGE, ex.getMessage());

		return errorMap;
	}

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(InvalidFormatException.class)
//	public Map<String, String> handleHttpMessageNotReadableException(InvalidFormatException ex) {
//		Map<String, String> errorMap = new HashMap<>();
//		errorMap.put("errorMessage", "invalid date");
//
//		return errorMap;
//
//	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidUserException.class)
	public Map<String, String> handleInvalidUserException(InvalidUserException ex) {
		Map<String, String> errorMap = new HashMap<>();

		errorMap.put(ERROR_MESSAGE, ex.getMessage());

		return errorMap;

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDateException.class)
	public Map<String, String> handleInvalidDateException(InvalidDateException ex) {
		Map<String, String> errorMap = new HashMap<>();

		errorMap.put(ERROR_MESSAGE, ex.getMessage());

		return errorMap;

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Map<String, String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		Map<String, String> errorMap = new HashMap<>();

		errorMap.put(ERROR_MESSAGE, INVALID_REQUEST_PARAMETER);

		return errorMap;

	}

}
