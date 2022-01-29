package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awl.merchanttoolkit.dto.ResMsgDTO;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;
import com.kp.cms.constants.CMSConstants;

/**
 * Servlet implementation class PGIResponseServlet
 */
public class PGIResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PGIResponseServlet.class);
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*//String responseMsg=request.getParameter("msg");
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
		request.setAttribute("Error", Error);
		request.setAttribute("PG_TYPE",PG_TYPE);
		request.setAttribute("bank_ref_num", bank_ref_num);
		request.setAttribute("payuMoneyId",payuMoneyId);
		if(additionalCharges!=null){
			request.setAttribute("additionalCharges",additionalCharges);
		}else{
			request.setAttribute("additionalCharges","0");
		}
		
		log.error("msg recieved from PGI:"+hash);*/
		//request.setAttribute("responseMsg", responseMsg);
		String merchantResponse = request.getParameter("merchantResponse");
		System.out.println("Inside response Servelt");
		AWLMEAPI objAWLMEAPI = new AWLMEAPI();
		ResMsgDTO objResMsgDTO = null;
		try {
			objResMsgDTO = objAWLMEAPI.parseTrnResMsg(merchantResponse, CMSConstants.PGI_WLS_ENCRIPTION_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(objResMsgDTO!=null){
		System.out.println("Inside response Servelt objResMsgDTO Not null");
		request.setAttribute("txnRefNo",objResMsgDTO.getPgMeTrnRefNo() );
		request.setAttribute("txnAmt", objResMsgDTO.getTrnAmt());
		request.setAttribute("candidateRefNo", objResMsgDTO.getOrderId());
		request.setAttribute("status",  objResMsgDTO.getStatusCode());
		request.setAttribute("bank_ref_num",objResMsgDTO.getRrn());
		request.setAttribute("statusDes", objResMsgDTO.getStatusDesc());
		request.setAttribute("authzCode", objResMsgDTO.getAuthZCode());
		request.setAttribute("responceCode", objResMsgDTO.getResponseCode());
		request.setAttribute("txnDate", objResMsgDTO.getTrnReqDate());
		
		System.out.println("txnRefNo"+objResMsgDTO.getPgMeTrnRefNo() );
		System.out.println("txnAmt"+objResMsgDTO.getTrnAmt());
		System.out.println("candidateRefNo"+objResMsgDTO.getOrderId());
		System.out.println("status" +objResMsgDTO.getStatusCode());
		System.out.println("bank_ref_num"+objResMsgDTO.getRrn());
		System.out.println("statusDes"+ objResMsgDTO.getStatusDesc());
		System.out.println("authzCode"+objResMsgDTO.getAuthZCode());
		System.out.println("responceCode"+ objResMsgDTO.getResponseCode());
		System.out.println("txnDate"+objResMsgDTO.getTrnReqDate());
		}else{
			System.out.println("Inside response Servlet-> objResMsgDTO is null");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/pgiRepsonseReceiver.jsp");
		dispatcher.forward(request, response);
	}

}
