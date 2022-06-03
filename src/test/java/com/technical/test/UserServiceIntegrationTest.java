package com.technical.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.technical.test.dao.UserRepository;
import com.technical.test.entities.User;

//
@SpringBootTest(classes = { TechnicalTestApplication.class })
@WebAppConfiguration
@AutoConfigureMockMvc
public class UserServiceIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldSaveUser() throws Exception {
		String uri = "/users/save";

		String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"france\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(status().isCreated()).andReturn();

	}

	@Test
	void shouldNotSaveUser() throws Exception {
		String uri = "/users/save";

		String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	void shouldNotSaveUserWithEmptyArgument() throws Exception {
		String uri = "/users/save";

		String inputJson = "{\"name\":\"\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	void shouldNotSaveUserWithMalFormatedDate() throws Exception {
		String uri = "/users/save";

		String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-1996\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	void couldGetUserWithId() throws Exception {
		insertUser();
		String uri = "/users/1";
		mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	void couldNotGetUserWithValidId() throws Exception {
		insertUser();
		String uri = "/users/29";
		mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	void couldNotGetUserWithInvalidId() throws Exception {
		insertUser();
		String uri = "/users/m";
		mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

	}

	void insertUser() {
		User user = new User("mohamed", new Date(), "france", "+33125789201", "male");
		userRepository.save(user);
	}

}
