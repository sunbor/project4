package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dto.UserQuiz;
import com.revature.model.Quiz;
import com.revature.util.ConnectionUtil;
import com.revature.util.Exceptions;

public class QuizDaoImpl implements QuizDao {

	//logger
	private static Logger logger = LogManager.getLogger(QuizDaoImpl.class);
	
	//magic strings
	private static final String NEW_QUIZ = "INSERT INTO quizzes (quiz_id, title)"
											+ " VALUES (quiz_id_seq.nextval, ?)";
	public static final String QUIZ_ID = "SELECT quiz_id_seq.currval FROM dual";
	private static final String ADD_QUESTIONS = "INSERT INTO quiz_questions (quiz_id, question_id)"
											+ " VALUES (?, ?)";
	private static final String ALL_QUIZZES = "SELECT * FROM quizzes";
	private static final String ASSIGN_QUIZZES = "INSERT INTO quiz_assignment (user_id, quiz_id, score)"
											+ " VALUES (?, ?, -1000000)";
	private static final String QUIZ_BY_USER = "SELECT q.quiz_id, title, score FROM quizzes q "
			+ "LEFT JOIN quiz_assignment a ON (q.quiz_id = a.quiz_id) WHERE a.user_id = ?";
	private static final String UPDATE_SCORE = "UPDATE quiz_assignment SET score = ? WHERE quiz_id = ?";

	
	//instance
	private static QuizDao instance;
	
	//instance getter
	public static QuizDao getInstance() {
		if(instance == null) {
			instance = new QuizDaoImpl();
		}
		return instance;
	}
	
	@Override
	public int newQuiz(String title) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(NEW_QUIZ);
			
			int index = 0;
			ps.setString(++index, title);
			
			if(ps.executeUpdate() != 1) {
				logger.warn("add quiz failed");
				return 0;
			}
			
			//get id of quiz
			PreparedStatement ps2 = conn.prepareStatement(QUIZ_ID);
			
			ResultSet rs = ps2.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("currval");
			}
			else {
				logger.warn("failed to get quiz id");
				return 0;
			}
		}
		catch(SQLException e){
			Exceptions.logSQLException(e);
			return 0;
		}
	}
	
	public boolean addQuestion(int quizId, int[] questionId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ADD_QUESTIONS);
			
			int index = 0;
			//set id of quiz in statement
			ps.setInt(++index, quizId);
			
			//changing second parameter only
			index++;
			for(int id : questionId) {
				//update with each id
				ps.setInt(index, id);
				if(ps.executeUpdate() != 1) {
					//if update fails
					logger.warn("add question failed");
					return false;
				}
			}
			
			return true;
		}
		catch(SQLException e) {
			logger.warn("failed to add question to quiz");
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ALL_QUIZZES);
			
			ResultSet rs = ps.executeQuery();
			
			List<Quiz> quizList = new ArrayList<Quiz>();
			
			while(rs.next()) {
				quizList.add(new Quiz(
						rs.getInt("quiz_id"),
						rs.getString("title")
						));
			}
			
			return quizList;
		}
		catch(SQLException e) {
			logger.warn("failed to get list of quizzes from database");
			Exceptions.logSQLException(e);
			return null;
		}
	}

	@Override
	public boolean assignQuizzes(int[] userIds, int[] quizIds) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			boolean assignSuccess = true;
			
			PreparedStatement ps = conn.prepareStatement(ASSIGN_QUIZZES);
			for(int i=0; i<userIds.length; i++) {
				ps.setInt(1, userIds[i]);
				for(int j=0; j<quizIds.length; j++) {
					ps.setInt(2, quizIds[j]);
					if(ps.executeUpdate() != 1) {
						assignSuccess = false;
						logger.warn("failed to assign quiz " + quizIds[j] +
									" to user " + userIds[i]);
					}
				}
			}
			return assignSuccess;
		}
		catch(SQLException e) {
			logger.warn("failed to add quiz assignments to database");
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public List<UserQuiz> quizByUser(int userId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(QUIZ_BY_USER);
			
			int index = 0;
			ps.setInt(++index, userId);
			
			ResultSet rs = ps.executeQuery();
			
			List<UserQuiz> quizList = new ArrayList<UserQuiz>();
			
			while(rs.next()) {
				quizList.add(new UserQuiz(
						rs.getInt("quiz_id"),
						rs.getString("title"),
						rs.getDouble("score")
						));
			}
			
			return quizList;
		}
		catch(SQLException e) {
			logger.warn("failed to get list of quizzes for the user from database");
			Exceptions.logSQLException(e);
			return null;
		}
	}

	@Override
	public boolean updateScore(double score, int quizId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(UPDATE_SCORE);
			
			int index=0;
			
			ps.setDouble(++index, score);
			ps.setInt(++index, quizId);
			
			return ps.executeUpdate() == 1;
		}
		catch(SQLException e) {
			logger.warn("failed to update score");
			Exceptions.logSQLException(e);
			return false;
		}
	}

}
