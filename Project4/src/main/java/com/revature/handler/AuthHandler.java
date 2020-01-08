package com.revature.handler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.dto.LoginForm;
import com.revature.dto.QuestionDisplay;
import com.revature.dto.RegisterForm;
import com.revature.dto.UserInfo;
import com.revature.model.User;
import com.revature.util.AuthUtil;
import com.revature.util.Exceptions;
import com.revature.util.Json;

public class AuthHandler {
	
	private static final UserDao userDao = UserDaoImpl.getInstance();
	private static final Logger logger = LogManager.getLogger(AuthHandler.class);
	
	public static void handleLogin(HttpServletRequest req, HttpServletResponse resp) {
		try {
//			LoginForm loginForm = (LoginForm) Json.read(req.getInputStream(), LoginForm.class);
//			logger.trace("credentials read from json: " + loginForm);
//			
//			UserInfo logUser = userDao.Login(loginForm.getUsername(), loginForm.getPassword());
//			logger.trace("user from dao: " + logUser);
//			
//			resp.getOutputStream().write(Json.write(logUser));
			LoginForm cred = (LoginForm) Json.read(req.getInputStream(), LoginForm.class);
			logger.trace("provided credentials: " + cred);
			
			//get user from username
			User logUser = userDao.getByUsername(cred.getUsername());
			logger.trace("user with matching username " + logUser);
			
			//if username not found, set status code and return
			if(logUser == null) {
				logger.info("user not found");
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			else {
				//verify
				if(AuthUtil.verifyPassword(cred.getPassword(), logUser.getKey(), logUser.getSalt())) {
					resp.setContentType(Json.CONTENT_TYPE);
					//store user id in cookie
					Cookie cookie = new Cookie("currentUser", Integer.toString(logUser.getId()));
					cookie.setPath("/Project4/api");
					resp.addCookie(cookie);
					
					//format as data transfer object
					UserInfo respUser = new UserInfo(logUser.getId(), logUser.getUsername(), logUser.getRole(), logUser.getGroup());
					
					resp.getOutputStream().write(Json.write(respUser));
					return;
				}
				else {
					logger.info("incorrect password");
					resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
			
		}
		catch(IOException e) {
			logger.warn("some other login error i dont know");
			e.printStackTrace();
		}
	}
	
	public static void handleRegister(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get info from front end
			RegisterForm regForm = (RegisterForm) Json.read(req.getInputStream(), RegisterForm.class);
			
			//set secure key and salt
			Optional<String> salt = AuthUtil.generateSalt();
			Optional<String> key = AuthUtil.hashPassword(regForm.getPassword(), salt.get());
			
			//make user object
			User newUser = new User(-1, regForm.getUsername(), key.get(), salt.get(), regForm.getRole(), regForm.getGroup());
					
			//clear provided password
			regForm = null;
			
			//check if username is already taken
			//set status to 409 if so
			if(userDao.getByUsername(newUser.getUsername()) == null) {
				//save user to database
				// TODO: give notification if this fails somehow
				userDao.createUser(newUser);
			}
			else {
				//set status code
				resp.setStatus(HttpServletResponse.SC_CONFLICT);
				return;
			}
		}
		catch(IOException e) {
			Exceptions.logJsonUnmarshallException(e, AuthHandler.class);
		}
		
	}
	
	public static void handleGetQuizTakers(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get all quiz takers
			List<UserInfo> userList = userDao.getQuizTakers();
			
			//exit if not found
			if(userList == null) {
				logger.info("users not found");
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
						
			//send list of questions to front
			resp.getOutputStream().write(Json.write(userList));
		}
		catch(IOException e) {
			logger.warn("failed to get list of all quiz takers");
			e.printStackTrace();
		}
	}

}
