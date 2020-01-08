package com.revature.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.QuestionDaoImpl;

public class DispatcherChain implements Dispatcher {
	
	private static Logger logger = LogManager.getLogger(DispatcherChain.class);
	
	//list of all dispatchers to iterate through
	private final List<Dispatcher> dispatchers;
	private static final DispatcherChain instance = new DispatcherChain();
	
	private DispatcherChain() {
		this.dispatchers = new ArrayList<>();
		dispatchers.add(new AuthDispatcher());
		dispatchers.add(new QuestionDispatcher());
		dispatchers.add(new AnswerDispatcher());
		dispatchers.add(new QuizDispatcher());
//		logger.trace("list of dispatchers:");
//		for(Dispatcher d : dispatchers) {
//			logger.trace(d);
//		}
	}

	public static DispatcherChain getInstance() {
		return instance;
	}
	
	@Override
	public boolean supports(HttpServletRequest req) {
		for(Dispatcher d : dispatchers) {
			if(d.supports(req)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		for(Dispatcher d : dispatchers) {
			if(d.supports(req)) {
				d.execute(req, resp);
			}
		}
		
	}

}
