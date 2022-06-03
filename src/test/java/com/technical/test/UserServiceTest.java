package com.technical.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.technical.test.dao.UserRepository;
import com.technical.test.dto.UserDto;
import com.technical.test.entities.User;
import com.technical.test.exception.InvalidUserException;
import com.technical.test.exception.UserNotFoundException;
import com.technical.test.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	private UserService underTest;
	
	@BeforeEach
	 void setup() {
		underTest = new UserService(userRepository);
	}
	
	
	
	@Test
	void canSaveUser() throws Exception {
		LocalDate date1 = LocalDate.of(1996, 9, 11);
		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		UserDto userRequest = new UserDto("mohamed", date, "france", "+3312457896", "male");
		User user = new User("mohamed", date,
				"france", "+3312457896", "male");
		underTest.saveUser(userRequest);
		
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(userRepository).save(userArgumentCaptor.capture());
		
		User capturedUser = userArgumentCaptor.getValue();
		
		assertThat(capturedUser).isEqualTo(user);
	
	}
	
	@Test
	void canNotSaveUserNationalityError() throws Exception {
		LocalDate date1 = LocalDate.of(1996, 9, 11);
		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		UserDto userRequest = new UserDto("mohamed", date, "italy", "+3312457896", "male");
		assertThatThrownBy(() -> underTest.saveUser(userRequest))
			.isInstanceOf(InvalidUserException.class)
			.hasMessageContaining("user nationality not valid");
		
	}
	
	@Test
	void canNotSaveUserAgeError() throws Exception {
		LocalDate date1 = LocalDate.of(2009, 9, 11);
		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		UserDto userRequest = new UserDto("mohamed", date, "france", "+3312457896", "male");
		assertThatThrownBy(() -> underTest.saveUser(userRequest))
			.isInstanceOf(InvalidUserException.class)
			.hasMessageContaining("age  not valid");
		
	}
	
	@Test
	void getUser() throws Exception{
		LocalDate date1 = LocalDate.of(1996, 9, 11);
		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		User user = new User( "mohamed", date,
				"france", "+3312457896", "male");
		when(userRepository.findByUserId(Mockito.anyLong())).thenReturn(user);
		
		assertThat(underTest.getUser(2L)).isEqualTo(user);
		
	}
	
	@Test
	void canNotGetUser() throws Exception{
		when(userRepository.findByUserId(Mockito.anyLong())).thenReturn(null);
		
		assertThatThrownBy(() -> underTest.getUser(2L))
		.isInstanceOf(UserNotFoundException.class)
		.hasMessageContaining("user not found with id : 2");
		
	}
	

}
