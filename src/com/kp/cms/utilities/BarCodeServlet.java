package com.kp.cms.utilities;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class LogoServlet
 */
public class BarCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BarCodeServlet() {
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
		HttpSession session= request.getSession(false);
		int count=1;
		if(session!=null){
			byte[] photobytes=null;
			String countParameter=request.getParameter("count");
			if(countParameter!=null && !countParameter.isEmpty() && StringUtils.isNumeric(countParameter))
				count=Integer.parseInt(countParameter);
				
			if(session.getAttribute("barCodeBytes")!=null && count==1)
				photobytes=(byte[])session.getAttribute("barCodeBytes");
			
			if(photobytes!=null){
				OutputStream os = response.getOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(os);
				response.setContentType("image/jpeg");
				bos.write(photobytes);
				bos.flush();
				bos.close();
//				response.getOutputStream().write(photobytes);  
			}
		}
	}

}
