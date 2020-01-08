package com.revature.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.handler.QuestionHandler;

public class QuestionDispatcher implements Dispatcher {

	QuestionDispatcher(){
		
	}
	
	@Override
	public boolean supports(HttpServletRequest req) {
		return isNewQuestion(req) || isGetAllQuestions(req) || isGetQuestionByQuiz(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if(isNewQuestion(req)) {
			QuestionHandler.HandleNewQuestion(req, resp);
		}
		else if(isGetAllQuestions(req)) {
			QuestionHandler.HandleGetAllQuestions(req, resp);
		}
		else if(isGetQuestionByQuiz(req)) {
			QuestionHandler.HandleGetQuestionByQuiz(req, resp);
		}
	}
	
	private boolean isNewQuestion(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/new-question");
	}
	
	private boolean isGetAllQuestions(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Project4/api/get-question");
	}
	
	private boolean isGetQuestionByQuiz(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Project4/api/get-question-by-quiz");
	}

}
