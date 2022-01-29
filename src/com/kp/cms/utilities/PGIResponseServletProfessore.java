package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class PGIResponseServletProfessore
 */
public class PGIResponseServletProfessore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PGIResponseServletProfessore.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String hash=request.getParameter("hash_value");
		String txnid=request.getParameter("tran_id");
		String amount=request.getParameter("amount");
		String status=request.getParameter("Tran_status");

		request.setAttribute("hash", hash);
		request.setAttribute("txnid", txnid);
		request.setAttribute("amount", amount);
		request.setAttribute("status", status);
	
		System.out.println("==============hash============"+hash);
		System.out.println("==============txnid============"+txnid);
		System.out.println("==============amount============"+amount);
		System.out.println("==============status============"+status);
		System.out.println("+++++++++++++total parameters in payu request object of map+++++++++++++"+request.getParameterMap());
		System.out.println("+++++++++++++total parameters in payu request object of names+++++++++++++"+request.getParameterNames());
		
		log.error("msg recieved from PGI:"+hash);
		RequestDispatcher dispatcher=request.getRequestDispatcher("./jsp/pgiResponceReceiverProfessor.jsp");
		dispatcher.forward(request, response);
	
	}

}
