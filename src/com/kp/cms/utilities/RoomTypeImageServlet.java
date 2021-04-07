package com.kp.cms.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kp.cms.to.hostel.RoomTypeImageTO;

public class RoomTypeImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomTypeImageServlet() {
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
			List<RoomTypeImageTO> imageList = (List<RoomTypeImageTO>) session.getAttribute("imageList");		
			int countid=Integer.parseInt(request.getParameter("countID"));
			if(imageList!=null && !imageList.isEmpty()){
				Iterator<RoomTypeImageTO> iterator = imageList.iterator();
				while (iterator.hasNext()) {
					RoomTypeImageTO roomTypeImageTO = iterator.next();
					if(countid!=0 && roomTypeImageTO.getCountId()==countid){
					byte[] photobytes = roomTypeImageTO.getImage();
					response.getOutputStream().write(photobytes);
					}
				}
			}			
		}
	}
}
