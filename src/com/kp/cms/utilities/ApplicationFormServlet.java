package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApplicationFormServlet
 */
public class ApplicationFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		dispatcher=request.getRequestDispatcher("./jsp/applicationform/requestReciverForApplication.jsp");
		dispatcher.forward(request, response);
		return;
	}

}
