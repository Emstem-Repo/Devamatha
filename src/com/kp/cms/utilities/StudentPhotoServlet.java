package com.kp.cms.utilities;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kp.cms.to.reports.ScoreSheetTO;

/**
 * Servlet implementation class StudentPhotoServlet
 */
public class StudentPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentPhotoServlet() {
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
			ScoreSheetTO sheetTO = (ScoreSheetTO) session.getAttribute("image_"+count);
			
			

					if(count!=0 && sheetTO.getCount()==count){
					if(sheetTO.getPhotoBytes()!=null){
						byte[] photobytes = sheetTO.getPhotoBytes();
						response.getOutputStream().write(photobytes);
					}
					}
					//session.removeAttribute("image_"+count);
						
		}
	}
}