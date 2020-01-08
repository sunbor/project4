package com.revature.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Exceptions {
	private static Logger logger = LogManager.getLogger(Exceptions.class);

	private Exceptions() {
		
	}
	
	public static void logSQLException(SQLException e) {
		logger.warn("SQL Message: {}", e.getMessage());
		logger.warn("Error Code: {}", e.getErrorCode());
		logger.warn("SQL State: {}", e.getSQLState());
		logger.warn("Stack Trace: ", e);
	}
	
	//write, pojo to json
	public static void logJsonMarshallException(IOException e, Class<?> clazz) {
		logger.warn("failed to marshall object of type {}", clazz.getName());
		logger.warn("stack trace: ", e);
	}
	
	//read, json to pojo
	public static void logJsonUnmarshallException(IOException e, Class<?> clazz) {
		logger.warn("failed to unmarshall object of type {}", clazz.getName());
		logger.warn("stack trace: ", e);
	}
	
	//for if salting algorithm isnt found
	public static void logNoSuchAlgorithmException(NoSuchAlgorithmException e, Class<?> clazz, String badAlg) {
		logger.warn("Algorithm: " + badAlg + "not found", clazz.getName());
		logger.warn("Stack Trace: ", e);
	}
	
	//not sure actually
	public static void logInvalidKeySpecException(InvalidKeySpecException e, Class<?> clazz) {
		logger.warn("Invalid key spec shhhh careful");
		logger.warn("Stack Trace: ", e);
	}
}
