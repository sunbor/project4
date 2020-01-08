package com.revature.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AnswerDao;
import com.revature.dao.AnswerDaoImpl;
import com.revature.model.Answer;
import com.revature.util.Json;

public class AnswerHandler {

	private static final AnswerDao answerDao = AnswerDaoImpl.getInstance();
	private static Logger logger = LogManager.getLogger(AnswerHandler.class);
	
	public static void handleNewAnswer(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get the stuff from the thing
			Answer[] answerArray = (Answer[]) Json.read(req.getInputStream(), Answer[].class);
			//List<Answer> answerArray = (List<Answer>) Json.read(req.getInputStream(), ArrayList<Answer>.class); doesnt work
			
			
			
			boolean allSuccess = true;
			
			//for each answer
			for(Answer anAnswer : answerArray) {
				logger.trace("answers: " + anAnswer);
				//add to database
				//only check if false
				//dont reset if true
				if(answerDao.newAnswer(anAnswer) == false) {
					allSuccess = false;
				}
			}
			
			logger.trace("did all answers write successfully? " + allSuccess);
			resp.getOutputStream().write(Json.write(allSuccess));
		}
		catch(IOException e) {
			logger.warn("failed to write answers to database");
			e.printStackTrace();
		}
	}
}
