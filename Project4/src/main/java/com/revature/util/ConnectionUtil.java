package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
	private static Logger logger = LogManager.getLogger(ConnectionUtil.class);
	private static Properties props = getProperties();
	
	//magic strings
	private static final String URL = props.getProperty("jdbc.url");
	private static final String USERNAME = props.getProperty("jdbc.username");
	private static final String PASSWORD = props.getProperty("jdbc.password");
	
	// Fail Fast
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.error("Failed to load JDBC Driver: {}", e);
			System.exit(1);
		}
	}
	
	//singleton
	private ConnectionUtil() {
		
	}
	
	//make connection
	public static Connection getConnection() {
		try {
			logger.debug(URL);
			logger.debug(USERNAME);
			logger.debug(PASSWORD);
			logger.debug(DriverManager.getConnection(URL, USERNAME, PASSWORD));
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
	}
	
	
	//get properties from application.properties file
	private static Properties getProperties() {
		try {
			//properties object
			Properties props = new Properties();
			
			//get values from file
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			return props;
		}
		catch (IOException | NullPointerException e) {
			logger.error("cant find properties from application.properties file");
			logger.error("Stack trace: ", e);
			throw new RuntimeException("cant find connection properties");
		}
	}
}
