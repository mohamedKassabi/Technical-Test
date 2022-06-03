package com.technical.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.test.dao.UserRepository;
import com.technical.test.dto.UserDto;
import com.technical.test.entities.User;
//
@SpringBootTest(classes = {TechnicalTestApplication.class})
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

//		LocalDate date1 = LocalDate.of(1996, 9, 11);
//		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
//		UserDto userRequest = new UserDto("mohamed", date, "france", "+3312457896", "male");
//		String inputJson = mapToJson(userRequest);
		String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"france\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";
		
		 mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(status().isCreated()).andReturn();
		
	}
		
		@Test
		void shouldNotSaveUser() throws Exception {
			String uri = "/users/save";
//			LocalDate date1 = LocalDate.of(1900, 9, 11);
//			Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
//			UserDto userRequest = new UserDto("mohamed", date, "italy", "+3312457896", "male");
//			String inputJson = mapToJson(userRequest);
			
			String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";
			
			
			mockMvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
					.andExpect(status().isBadRequest()).andReturn();
			
		
	}
		
		@Test
		void shouldNotSaveUserWithEmptyArgument() throws Exception {
			String uri = "/users/save";
//			LocalDate date1 = LocalDate.of(1900, 9, 11);
//			Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
//			UserDto userRequest = new UserDto("", date, "italy", "+3312457896", "male");
//			String inputJson = mapToJson(userRequest);
			
			String inputJson = "{\"name\":\"\",\"birthDate\":\"1996-01-01\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";
			
			
			
			mockMvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
					.andExpect(status().isBadRequest()).andReturn();
			
		
	}
		
		@Test
		void shouldNotSaveUserWithMalFormatedDate() throws Exception {
			String uri = "/users/save";
//			Date date = new Date();
//			UserDto userRequest = new UserDto("mohamed", date, "france", "+3312457896", "male");
//			String inputJson = mapToJson(userRequest);
			
			String inputJson = "{\"name\":\"mohamed\",\"birthDate\":\"1996-01-1996\",\"countryOfResidence\":\"italy\",\"phoneNumber\":\"+3312457896\",\"gender\":\"male\"}";
			
			
			mockMvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
					.andExpect(status().isBadRequest()).andReturn();
			
		
	}
		
		
		@Test
		void couldGetUserWithId() throws Exception {
			insertUser();
			String uri = "/users/1";
			mockMvc.perform(
					MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			
		}
		
		@Test
		void couldNotGetUserWithValidId() throws Exception {
			insertUser();
			String uri = "/users/29";
			mockMvc.perform(
					MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
			
		}
		
		@Test
		void couldNotGetUserWithInvalidId() throws Exception {
			insertUser();
			String uri = "/users/m";
			mockMvc.perform(
					MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
			
		}
	
	
		void insertUser() {
			User user = new User( "mohamed", new Date(), "france", "+33125789201", "male");
			userRepository.save(user);
		}

		String mapToJson(Object obj) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		}

		<T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, clazz);
		}
	
	

}
