package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dto.UserInfo;
import com.revature.model.Quiz;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.Exceptions;

public class UserDaoImpl implements UserDao {
	
	private static Logger logger = LogManager.getLogger(UserDaoImpl.class);

	// TODO get rid of select all
	//private static final String LOGIN_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";
	private static final String LOGIN_QUERY = "SELECT user_id, username, role, quiz_group"
											+ " FROM users WHERE username = ? AND password = ?";
	private static final String GET_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
	private static final int UNUSED_INT = -1;
	private static final String UNUSED_STRING = "nope";
	private static final String NEW_USER = "INSERT INTO users (user_id, username, key, salt, role, quiz_group)"
											+ " VALUES (users_id_seq.nextval, ?, ?, ?, ?, ?)";
	//role = 2 refers to non managers
	private static final String ALL_QUIZ_TAKERS = "SELECT user_id, username, role, quiz_group"
											+ " FROM users WHERE role = 2";
	
	
	private static UserDao instance;
	// Lazy
	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDaoImpl();
		} 
		return instance;
	}
	
	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo Login(String username, String password) {
		try(Connection conn = ConnectionUtil.getConnection()){
			//make prepared statement
			PreparedStatement ps = conn.prepareStatement(LOGIN_QUERY);
			
			int index = 0;
			ps.setString(++index, username);
			ps.setString(++index, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return fromResultSet(rs);
			}
			else {
				logger.warn("user not found");
				return null;
			}
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
	}
	
	public UserInfo fromResultSet(ResultSet rs) throws SQLException {
		return new UserInfo(
				rs.getInt("user_id"),
				rs.getString("username"),
				rs.getInt("role"),
				rs.getInt("quiz_group"));
	}

	@Override
	public User getByUsername(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(GET_BY_USERNAME);
			
			int index = 0;
			ps.setString(++index, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("key"),
						rs.getString("salt"),
						rs.getInt("role"),
						rs.getInt("quiz_group")
						);
			}
			else {
				logger.warn("user not found");
				return null;
			}
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
		
	}

	@Override
	public boolean createUser(User newUser) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(NEW_USER);
			
			int index = 0;
			ps.setString(++index, newUser.getUsername());
			ps.setString(++index, newUser.getKey());
			ps.setString(++index, newUser.getSalt());
			ps.setInt(++index, newUser.getRole());
			ps.setInt(++index, newUser.getGroup());
			
			return ps.executeUpdate() == 1;
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public List<UserInfo> getQuizTakers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(ALL_QUIZ_TAKERS);
			
			ResultSet rs = ps.executeQuery();
			
			List<UserInfo> userList = new ArrayList<UserInfo>();
			
			while(rs.next()) {
				userList.add(new UserInfo(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getInt("role"),
						rs.getInt("quiz_group")
						));
			}
			
			return userList;
		}
		catch(SQLException e) {
			logger.warn("failed to get list of quiz takers");
			Exceptions.logSQLException(e);
			return null;
		}
	}

}
