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
 * Servlet implementation class ReceiverServlet
 */
public class ReceiverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiverServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		String courseID=request.getParameter("courseID");
		if(courseID==null || StringUtils.isEmpty(courseID)){
			dispatcher=request.getRequestDispatcher("./jsp/controlreciever.jsp");
			dispatcher.forward(request, response);
			return;
		}
		request.setAttribute("encCourseID", courseID);
		//clean up session
		HttpSession session= request.getSession();
		if(session.getAttribute("admissionFormForm")!=null)
			session.removeAttribute("admissionFormForm");
		dispatcher=request.getRequestDispatcher("./jsp/decryptor.jsp");
		dispatcher.forward(request, response);
		return;
	}

}
