package com.technical.test.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	private String countryOfResidence;

	private String phoneNumber;

	private String gender;

	public User(String name, Date birthDate, String countryOfResidence, String phoneNumber, String gender) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.countryOfResidence = countryOfResidence;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}

}
