package com.kp.cms.utilities;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kp.cms.to.fee.PrintChalanTO;

/**
 * Servlet implementation class printChalanLogoServlet
 */
public class printChalanLogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public printChalanLogoServlet() {
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
		HttpSession session= request.getSession(false);
		if(session!=null){
			int count=Integer.parseInt(request.getParameter("count"));		
			PrintChalanTO chalanTO= (PrintChalanTO) session.getAttribute("image_"+count);
			
			

					if(count!=0 && chalanTO.getCount()==count){
					if(chalanTO.getLogoBytes()!=null){
						byte[] photobytes = chalanTO.getLogoBytes();
						response.getOutputStream().write(photobytes);
					}
					}
					//session.removeAttribute("image_"+count);
						
		}
	}
}	

