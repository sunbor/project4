package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dto.QuestionDisplay;
import com.revature.model.Question;
import com.revature.util.ConnectionUtil;
import com.revature.util.Exceptions;

public class QuestionDaoImpl implements QuestionDao {

	//logger
	private static Logger logger = LogManager.getLogger(QuestionDaoImpl.class);
	
	//magic strings
	public static final String NEW_QUESTION = "INSERT INTO questions (question_id, question)"
												+ " VALUES (question_id_seq.nextval, ?)";
	public static final String QUESTION_ID = "SELECT question_id_seq.currval FROM dual";
	public static final String ALL_QUESTIONS = "SELECT * FROM questions";
	public static final String QUESTION_BY_QUIZ = "SELECT q.question_id, question "
			+ "FROM questions q LEFT JOIN quiz_questions z "
			+ "ON (q.question_id = z.question_id) WHERE z.quiz_id = ?";
	
	//instance
	private static QuestionDao instance;
	
	//instance getter
	public static QuestionDao getInstance() {
		if(instance == null) {
			instance = new QuestionDaoImpl();
		}
		return instance;
	}
	
	@Override
	//returns id of question added
	public int newQuestion(Question newQuestion) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(NEW_QUESTION);
			
			int index = 0;
			ps.setString(++index, newQuestion.getQuestion());
			
			ps.executeUpdate();
			
			//get id of added question
			PreparedStatement ps2 = conn.prepareStatement(QUESTION_ID);
			
			ResultSet rs = ps2.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("currval");
			}
			else {
				logger.warn("failed to get question id");
				return 0;
			}
			
		}
		catch(SQLException e) {
			logger.warn("failed to add new question to database");
			Exceptions.logSQLException(e);
			return 0;
		}
	}

	@Override
	public List<QuestionDisplay> getAllQuestions() {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ALL_QUESTIONS);
			
			ResultSet rs = ps.executeQuery();
			
			List<QuestionDisplay> questionList = new ArrayList<QuestionDisplay>();
			
			while(rs.next()) {
				questionList.add(new QuestionDisplay(
						rs.getInt("question_id"),
						rs.getString("question"),
						null
						));
			}
			
			return questionList;
		}
		catch(SQLException e) {
			logger.warn("failed to get questions from database");
			Exceptions.logSQLException(e);
			return null;
		}
	}

	@Override
	public List<QuestionDisplay> getQuestionByQuiz(int quizId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(QUESTION_BY_QUIZ);
			
			int index = 0;
			ps.setInt(++index, quizId);
			
			ResultSet rs = ps.executeQuery();
			
			List<QuestionDisplay> questionList = new ArrayList<QuestionDisplay>();
			
			while(rs.next()) {
				questionList.add(new QuestionDisplay(
						rs.getInt("question_id"),
						rs.getString("question"),
						null
						));
			}
			
			return questionList;
		}
		catch(SQLException e) {
			logger.warn("failed to get questions by quiz number from database");
			Exceptions.logSQLException(e);
			return null;
		}
	}

}
