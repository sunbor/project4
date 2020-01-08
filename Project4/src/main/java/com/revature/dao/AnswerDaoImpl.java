package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.Answer;
import com.revature.util.ConnectionUtil;
import com.revature.util.Exceptions;

public class AnswerDaoImpl implements AnswerDao {
	
	private static Logger logger = LogManager.getLogger(AnswerDaoImpl.class);

	public static final String NEW_ANSWER = "INSERT INTO answers (answer_id, answer, correct, question)"
											+ " VALUES (answer_id_seq.nextval, ?, ?, ?)";
	public static final String ANSWER_BY_QUESTION = "SELECT * FROM answers WHERE question=?";
	public static final String CORRECT_ANSWERS = "SELECT correct FROM answers WHERE question = ? AND answer_id = ?";
	
	private static AnswerDao instance;
	
	public static AnswerDao getInstance() {
		if(instance == null) {
			instance = new AnswerDaoImpl();
		}
		return instance;
	}
	
	
	@Override
	public boolean newAnswer(Answer newAnswer) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(NEW_ANSWER);
			
			int index = 0;
			ps.setString(++index, newAnswer.getAnswer());
			ps.setInt(++index, newAnswer.getCorrect());
			ps.setInt(++index, newAnswer.getQuestion());
			
			return ps.executeUpdate() == 1;
			
		}
		catch(SQLException e) {
			logger.warn("failed to write new answer to database");
			Exceptions.logSQLException(e);
			return false;
		}
	}


	@Override
	public List<Answer> getAnswerByQuestion(int questionId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ANSWER_BY_QUESTION);
			
			int index = 0;
			ps.setInt(++index, questionId);
			
			ResultSet rs = ps.executeQuery();
			
			List<Answer> answerList = new ArrayList<Answer>();
			
			while(rs.next()) {
				answerList.add(new Answer(
						rs.getInt("answer_id"),
						rs.getString("answer"),
						rs.getInt("correct"),
						rs.getInt("question")
						));
			}
			return answerList;
		}
		catch(SQLException e) {
			logger.warn("failed to get answer by question whatever that means");
			Exceptions.logSQLException(e);
			return null;
		}
	}


	@Override
	public List<Answer> getAnswerHideByQuestion(int questionId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ANSWER_BY_QUESTION);
			
			int index = 0;
			ps.setInt(++index, questionId);
			
			ResultSet rs = ps.executeQuery();
			
			List<Answer> answerList = new ArrayList<Answer>();
			
			while(rs.next()) {
				answerList.add(new Answer(
						rs.getInt("answer_id"),
						rs.getString("answer"),
						666,
						rs.getInt("question")
						));
			}
			return answerList;
		}
		catch(SQLException e) {
			logger.warn("failed to get cheap lying answer by question");
			Exceptions.logSQLException(e);
			return null;
		}
	}


	@Override
	public int scoreQuiz(int[] answers, int[] questions, int size) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(CORRECT_ANSWERS);
			ResultSet rs;
			int score = 0;
			
			for(int i=0; i<size; i++) {
				ps.setInt(1, questions[i]);
				ps.setInt(2, answers[i]);
				rs = ps.executeQuery();
				if(rs.next()) {
					if(rs.getInt("correct") == 1) {
						score++;
					}
				}
			}
			
			return score;
		}
		catch(SQLException e) {
			logger.warn("failed to grade the quiz");
			Exceptions.logSQLException(e);
			return 0;
		}
	}


}
