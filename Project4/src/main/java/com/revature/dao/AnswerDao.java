package com.revature.dao;

import java.util.List;

import com.revature.model.Answer;

public interface AnswerDao {
	boolean newAnswer(Answer newAnswer);
	
	List<Answer> getAnswerByQuestion(int questionId);
	
	List<Answer> getAnswerHideByQuestion(int questionId);
	
	int scoreQuiz(int[] answers, int[] questions, int size);

}
