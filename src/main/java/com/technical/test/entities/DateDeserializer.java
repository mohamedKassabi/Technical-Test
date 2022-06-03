package com.technical.test.entities;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.technical.test.exception.InvalidDateException;

//
public class DateDeserializer extends StdDeserializer<Date> {

	private static final String INVALID_DATE_FORMAT = "Invalid Date Format";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2255201459726888324L;

	public DateDeserializer() {
		super(Date.class);
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		String value = p.readValueAs(String.class);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			throw new InvalidDateException(INVALID_DATE_FORMAT);
		}
	}

}
