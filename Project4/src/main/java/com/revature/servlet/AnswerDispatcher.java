package com.revature.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.handler.AnswerHandler;

public class AnswerDispatcher implements Dispatcher {

	AnswerDispatcher(){
		
	}
	
	@Override
	public boolean supports(HttpServletRequest req) {
		return isNewAnswer(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if(isNewAnswer(req)) {
			AnswerHandler.handleNewAnswer(req, resp);
		}
		
	}
	
	private boolean isNewAnswer(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/new-answer");
	}

}
