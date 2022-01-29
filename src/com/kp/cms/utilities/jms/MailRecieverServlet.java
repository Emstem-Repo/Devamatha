package com.kp.cms.utilities.jms;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * At server startup, starts message listener
 * 
 * JMS UTILITIES
 * 
 */

/**
 * Servlet implementation class MailRecieverServlet
 */
public class MailRecieverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailRecieverServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}
	@Override
	public void init() throws ServletException {
		// inits the reciever scheduler
		RecieverScheduler scheduler= new RecieverScheduler();
		scheduler.setName("MailListner");
		scheduler.start();
	}
	
}
