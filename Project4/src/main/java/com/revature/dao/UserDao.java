package com.revature.dao;

import java.util.List;

import com.revature.dto.UserInfo;
import com.revature.model.User;

public interface UserDao {
	
	User getUser();
	
	UserInfo Login(String username, String password);
	
	User getByUsername(String username);
	
	boolean createUser(User newUser);
	
	List<UserInfo> getQuizTakers();

}
