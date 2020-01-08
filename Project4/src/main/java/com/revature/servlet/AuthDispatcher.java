package com.revature.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.handler.AuthHandler;

public class AuthDispatcher implements Dispatcher {

	AuthDispatcher(){
		
	}
	
	@Override
	public boolean supports(HttpServletRequest req) {
		return isLogin(req) || isRegister(req) || isGetQuizTakers(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if(isLogin(req)) {
			AuthHandler.handleLogin(req, resp);
		}
		else if(isRegister(req)) {
			AuthHandler.handleRegister(req, resp);
		}
		else if(isGetQuizTakers(req)) {
			AuthHandler.handleGetQuizTakers(req, resp);
		}
		
	}
	
	private boolean isLogin(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/login");
	}
	private boolean isRegister(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/register");
	}
	private boolean isGetQuizTakers(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Project4/api/quiz-takers");
	}
}
