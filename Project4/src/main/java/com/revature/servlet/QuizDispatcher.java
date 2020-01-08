package com.revature.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.handler.QuizHandler;

public class QuizDispatcher implements Dispatcher {

	@Override
	public boolean supports(HttpServletRequest req) {
		return isNewQuiz(req) || isGetAllQuizzes(req) || 
				isAssignQuizzes(req) || isGetQuizzesByUser(req) ||
				isScoreQuizzes(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if(isNewQuiz(req)) {
			QuizHandler.handleNewQuiz(req, resp);
		}
		else if(isGetAllQuizzes(req)) {
			QuizHandler.handleGetQuizzes(req, resp);
		}
		else if(isAssignQuizzes(req)) {
			QuizHandler.handleAssignQuizzes(req, resp);
		}
		else if(isGetQuizzesByUser(req)) {
			QuizHandler.handleGetQuizByUser(req, resp);
		}
		else if(isScoreQuizzes(req)) {
			QuizHandler.handleScoreQuiz(req, resp);
			
		}
		
	}
	
	private boolean isNewQuiz(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/new-quiz");
	}
	private boolean isGetAllQuizzes(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Project4/api/get-quizzes");
	}
	private boolean isAssignQuizzes(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/post-assignments");
	}
	private boolean isGetQuizzesByUser(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Project4/api/get-quiz-by-user");
	}
	private boolean isScoreQuizzes(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Project4/api/post-quiz-answers");
	}


}
