package com.technical.test.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.technical.test.entities.DateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotEmpty(message = "user name shouldn't be empty")
	private String name;

	@NotNull(message = "birth Date shouldn't be empty")
	@JsonDeserialize(using = DateDeserializer.class)
	private Date birthDate;

	@NotEmpty(message = "country of residence shouldn't be empty")
	private String countryOfResidence;

	private String phoneNumber;

	private String gender;

}
