package com.revature.dao;

import java.util.List;

import com.revature.dto.QuestionDisplay;
import com.revature.model.Question;

public interface QuestionDao {
	int newQuestion(Question newQuestion);
	
	List<QuestionDisplay> getAllQuestions();
	
	List<QuestionDisplay> getQuestionByQuiz(int quizId);

}
