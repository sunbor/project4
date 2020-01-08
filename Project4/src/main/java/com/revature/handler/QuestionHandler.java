package com.revature.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AnswerDao;
import com.revature.dao.AnswerDaoImpl;
import com.revature.dao.QuestionDao;
import com.revature.dao.QuestionDaoImpl;
import com.revature.dto.QuestionDisplay;
import com.revature.model.Question;
import com.revature.util.Json;

public class QuestionHandler {
	
	private static Logger logger = LogManager.getLogger(QuestionHandler.class);
	private static final QuestionDao questionDao = QuestionDaoImpl.getInstance();
	private static final AnswerDao answerDao = AnswerDaoImpl.getInstance();
	
	public static void HandleNewQuestion(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get question
			Question newQ = (Question) Json.read(req.getInputStream(), Question.class);
			logger.trace("new question: " + newQ);
			
			//send to database
			//store id
			int qId = questionDao.newQuestion(newQ);
			
			//send id to front
			resp.getOutputStream().write(Json.write(qId));
		}
		catch(IOException e){
			logger.warn("failed to write question to database");
			e.printStackTrace();
		}
		
	}
	
	public static void HandleGetAllQuestions(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get all questions
			List<QuestionDisplay> questionList = questionDao.getAllQuestions();
			
			//for each question
			//get list of answers for that question
			for(QuestionDisplay question : questionList) {
				//set list of answers for each question to answers of that question
				//sorry this comment makes no sense
				question.setAnswerList(answerDao.getAnswerByQuestion(question.getId()));
			}
			
			//send list of questions to front
			resp.getOutputStream().write(Json.write(questionList));
		}
		catch(IOException e) {
			logger.warn("failed to get list of all questions");
			e.printStackTrace();
		}
	}

	public static void HandleGetQuestionByQuiz(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get quiz id from front
			int quizId = Integer.parseInt(req.getParameter("id"));
			logger.trace("quiz id parameter: " + quizId);
			
			//get all questions
			List<QuestionDisplay> questionList = questionDao.getQuestionByQuiz(quizId);
			
			//for each question
			//get list of answers for that question
			for(QuestionDisplay question : questionList) {
				//set list of answers for each question to answers of that question
				//sorry this comment makes no sense
				question.setAnswerList(answerDao.getAnswerHideByQuestion(question.getId()));
			}
			
			//send list of questions to front
			resp.getOutputStream().write(Json.write(questionList));
		}
		catch(IOException e) {
			logger.warn("failed to get list of all questions");
			e.printStackTrace();
		}
	}
}
