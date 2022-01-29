package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApplicationPrintServlet
 */
public class ApplicationPrintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ApplicationPrintServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		dispatcher=request.getRequestDispatcher("./jsp/applicationform/requestForPrintApplication.jsp");
		dispatcher.forward(request, response);
		return;
	}

}
