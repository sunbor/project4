package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispatcherServlet extends HttpServlet {

	//autogenerated
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = LogManager.getLogger(getClass());
	private final Dispatcher dispatcherChain = DispatcherChain.getInstance();
	
	public DispatcherServlet() {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(dispatcherChain.supports(req)) {
			dispatcherChain.execute(req, resp);
		}
		else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			logger.warn("servlet not found");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("{} request coming to {}", req.getMethod(), req.getRequestURI());
		if(dispatcherChain.supports(req)) {
			dispatcherChain.execute(req, resp);
		}
		else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			logger.warn("servlet not found");
			return;
		}
	}
}