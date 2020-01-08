package com.revature.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {
	
	//for readability/maintainability
	public static final String CONTENT_TYPE = "application/json";
	private static final ObjectMapper mapper = new ObjectMapper();
	
	//optional, easier to read
	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	//restrict instantiation
	private Json() {
		
	}
	
	//write just one object
	public static byte[] write(Object o) {
		try {
			return mapper.writeValueAsBytes(o);
		}
		catch (IOException e){
			Exceptions.logJsonMarshallException(e, o.getClass());
			return null;
		}
	}
	
	//read into an object
	public static Object read(InputStream is, Class<?> clazz) {
		try {
			return mapper.readValue(is, clazz);
		}
		catch(IOException e) {
			Exceptions.logJsonUnmarshallException(e, clazz);
			return null;
		}
	}
	
	//read json string into json node
	public static JsonNode readString(String str, Class<?> clazz) {
		try {
			return mapper.readValue(str, JsonNode.class);
		}
		catch(IOException e) {
			Exceptions.logJsonUnmarshallException(e, clazz);
			return null;
		}
	}

}
