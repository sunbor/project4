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
import com.revature.dao.QuizDao;
import com.revature.dao.QuizDaoImpl;
import com.revature.dto.AnswerForm;
import com.revature.dto.NewQuizForm;
import com.revature.dto.QuizAssignment;
import com.revature.dto.UserQuiz;
import com.revature.model.Quiz;
import com.revature.util.Json;

public class QuizHandler {
	
	private static Logger logger = LogManager.getLogger(QuizHandler.class);
	private static final QuestionDao questionDao = QuestionDaoImpl.getInstance();
	private static final QuizDao quizDao = QuizDaoImpl.getInstance();
	private static final AnswerDao answerDao = AnswerDaoImpl.getInstance();
	
	//make new quiz and add questions to join table
	public static void handleNewQuiz(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get name and question ids
			NewQuizForm newQuizForm = (NewQuizForm) Json.read(req.getInputStream(), NewQuizForm.class);
			logger.trace("new quiz parameters: " + newQuizForm);
			
			//make quiz
			//the object name and method have the same name oops
			int quizId = quizDao.newQuiz(newQuizForm.getTitle());
			
			//add questions to quiz
			boolean updateSuccess = quizDao.addQuestion(quizId, newQuizForm.getQuestionIds());
			
			resp.getOutputStream().write(Json.write(updateSuccess));
		}
		catch(IOException e){
			logger.warn("failed to write question to database");
			e.printStackTrace();
		}
	}
	
	//get all quizzes
	public static void handleGetQuizzes(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get all quizzes
			List<Quiz> quizList = quizDao.getAllQuizzes();
			
			//exit if not found
			if(quizList == null) {
				logger.info("quizzes not found");
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
						
			//send list of questions to front
			resp.getOutputStream().write(Json.write(quizList));
		}
		catch(IOException e) {
			logger.warn("failed to get list of all quizzes");
			e.printStackTrace();
		}
	}
	
	//assign quizzes to users
	public static void handleAssignQuizzes(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get arrays
			QuizAssignment assignments = (QuizAssignment) Json.read(req.getInputStream(), QuizAssignment.class);
			
			//send arrays to dao
			boolean assignmentSuccess = quizDao.assignQuizzes(assignments.getUserIds(), assignments.getQuizIds());
			
			//respond with results
			resp.getOutputStream().write(Json.write(assignmentSuccess));
		}
		catch(IOException e){
			logger.warn("failed to assign quizzes to users");
			e.printStackTrace();
		}
	}
	
	//get quiz by user
	public static void handleGetQuizByUser(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get user id from front
			//int userId = (int) Json.read(req.getInputStream(), int.class);
			int userId = Integer.parseInt(req.getParameter("id"));
			logger.trace("user id parameter: " + userId);
			
			//get all quizzes
			List<UserQuiz> quizList = quizDao.quizByUser(userId);
			
			//exit if not found
			if(quizList == null) {
				logger.info("quizzes not found");
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
						
			//send list of questions to front
			resp.getOutputStream().write(Json.write(quizList));
		}
		catch(IOException e) {
			logger.warn("failed to get list of all quizzes");
			e.printStackTrace();
		}
	}
	
	public static void handleScoreQuiz(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get answer form
			AnswerForm answers = (AnswerForm) Json.read(req.getInputStream(), AnswerForm.class);
			
			//get number of correct answers
			int numCorrect = answerDao.scoreQuiz(answers.getAnswers(), answers.getQuestions(), answers.getSize());
			
			//calculate score
			double score = ((double) numCorrect/ (double) answers.getSize()) * 100.00;
			
			//update score
			boolean updateSuccess = quizDao.updateScore(score, answers.getQuizId());
			
			//respond with results
			resp.getOutputStream().write(Json.write(updateSuccess));
		}
		catch(IOException e) {
			logger.warn("failed to score quiz");
			e.printStackTrace();
		}
	}
}
