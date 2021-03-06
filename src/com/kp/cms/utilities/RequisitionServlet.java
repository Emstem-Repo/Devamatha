package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.kp.cms.to.hostel.RequisitionsTo;

/**
 * Servlet implementation class RequisitionServlet
 */
public class RequisitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequisitionServlet() {
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
			RequisitionsTo requisitionsTo= (RequisitionsTo) session.getAttribute("image");
					if(requisitionsTo.getPhotoBytes()!=null){
						byte[] photobytes = requisitionsTo.getPhotoBytes();
						response.getOutputStream().write(photobytes);
					}
					session.removeAttribute("image");
		}
	}
}