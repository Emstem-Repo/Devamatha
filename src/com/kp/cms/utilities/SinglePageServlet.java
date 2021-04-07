package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class SinglePageServlet
 */
public class SinglePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SinglePageServlet() {
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
			boolean isPresidance=false;
			if(request.getParameter("presidance")!=null && request.getParameter("presidance").equalsIgnoreCase("true")){
				isPresidance=true;
			}
			request.setAttribute("presidance",isPresidance);
			
			RequestDispatcher dispatcher=null;
			if(!isPresidance)
				dispatcher=request.getRequestDispatcher("./jsp/singlePageReceiver.jsp");
			else
				dispatcher=request.getRequestDispatcher("./jsp/presidanceSinglePageReciever.jsp");
			
			dispatcher.forward(request, response);
			return;
		
	
	}

}
