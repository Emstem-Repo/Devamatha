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
 * Servlet implementation class PGIResponseServlet
 */
public class PGIResponseServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PGIResponseServlet1.class);
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String responseMsg=request.getParameter("msg");
		String hash=request.getParameter("hash");
		String txnid=request.getParameter("txnid");
		String productinfo=request.getParameter("productinfo");
		String amount=request.getParameter("amount");
		String email=request.getParameter("email");
		String firstname=request.getParameter("firstname");
		String phone=request.getParameter("phone");
		String unmappedstatus=request.getParameter("unmappedstatus");
		String mihpayid=request.getParameter("mihpayid");
		String mode=request.getParameter("mode");
		String status=request.getParameter("status");
		String key=request.getParameter("key");
		String Error=request.getParameter("Error");
		String PG_TYPE=request.getParameter("PG_TYPE");
		String bank_ref_num=request.getParameter("bank_ref_num");
		String payuMoneyId=request.getParameter("payuMoneyId");
		String additionalCharges=request.getParameter("additionalCharges");
		
		
		
		request.setAttribute("hash", hash);
		request.setAttribute("txnid", txnid);
		request.setAttribute("productinfo", productinfo);
		request.setAttribute("amount", amount);
		request.setAttribute("email", email);
		request.setAttribute("firstname", firstname);
		request.setAttribute("phone",phone);
		request.setAttribute("unmappedstatus", unmappedstatus);
		request.setAttribute("mihpayid", mihpayid);
		request.setAttribute("mode", mode);
		request.setAttribute("status", status);
		request.setAttribute("key", key);
		//request.setAttribute("Error", Error);
		request.setAttribute("PG_TYPE",PG_TYPE);
		//request.setAttribute("bank_ref_num", bank_ref_num);
		request.setAttribute("payuMoneyId",payuMoneyId);
		if(bank_ref_num!=null){
			if(Error!=null){
			request.setAttribute("Error",Error);
			}else{
				request.setAttribute("Error","Fail");	
			}
		}else{
			request.setAttribute("Error","");
		}
		if(bank_ref_num!=null){
			request.setAttribute("bank_ref_num",bank_ref_num);
		}else{
			request.setAttribute("bank_ref_num","");
		}
		if(additionalCharges!=null){
			request.setAttribute("additionalCharges",additionalCharges);
		}else{
			request.setAttribute("additionalCharges","0");
		}
		
		System.out.println("+++++++++++++total parameters in payu request object of map+++++++++++++"+request.getParameterMap());
		System.out.println("+++++++++++++total parameters in payu request object of names+++++++++++++"+request.getParameterNames());
		
		log.error("msg recieved from PGI:"+hash);
		//request.setAttribute("responseMsg", responseMsg);
		RequestDispatcher dispatcher=request.getRequestDispatcher("./jsp/pgiRepsonseReceiver1.jsp");
		dispatcher.forward(request, response);
	}

}
