package com.revature.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AnswerDao;
import com.revature.dao.AnswerDaoImpl;
import com.revature.dao.QuestionDao;
import com.revature.dao.QuestionDaoImpl;
import com.revature.dao.QuizDao;
import com.revature.dao.QuizDaoImpl;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;

public class TestDriver {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		logger.trace("hello");
		
		//testUserDao();
		//testQuestionList();
		//testAnswerList();
		//testAssignQuiz();
		testScoreQuiz();
	}
	
	private static void testUserDao() {
		UserDao testUser = UserDaoImpl.getInstance();
		
		logger.trace(testUser.Login("admin1", "pass"));
	}
	
	private static void testQuestionList() {
		QuestionDao testQuestions = QuestionDaoImpl.getInstance();
		
		logger.trace(testQuestions.getAllQuestions());
	}
	
	private static void testAnswerList() {
		AnswerDao testAnswers = AnswerDaoImpl.getInstance();
		
		logger.trace(testAnswers.getAnswerByQuestion(22));
	}
	private static void testAssignQuiz() {
		QuizDao testAssign = QuizDaoImpl.getInstance();
		int[] a1 = {1, 2, 3};
		int[] a2 = {4, 5, 6};
		testAssign.assignQuizzes(a1, a2);
	}
	private static void testScoreQuiz() {
		AnswerDao testScore = AnswerDaoImpl.getInstance();
		int[] answers = {3, 5};
		int[] questions = {1, 2};
		logger.trace(testScore.scoreQuiz(answers, questions , 2));
	}
}
