package com.revature.dao;

import java.util.List;

import com.revature.dto.UserQuiz;
import com.revature.model.Quiz;

public interface QuizDao {
	
	int newQuiz(String title);
	
	boolean addQuestion(int quizId, int[] questionId);
	
	List<Quiz> getAllQuizzes();
	
	boolean assignQuizzes(int[] userIds, int[] quizIds);
	
	List<UserQuiz> quizByUser(int userId);
	
	boolean updateScore(double score, int quizId);

}
