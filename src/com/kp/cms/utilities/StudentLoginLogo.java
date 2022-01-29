package com.kp.cms.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.NewsEventsTO;

/**
 * Servlet implementation class StudentLoginLogo
 */
public class StudentLoginLogo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(StudentLoginLogo.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		try{
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				byte[] photo = organisation.getTopbar();
				String imageURL = config.getServletContext().getRealPath("/")+"images/Logos/";
				CMSConstants.LOGO_URL = "images/Logos/"+organisation.getLogoName();
				CMSConstants.COLOUR_LOGO_URL = "images/Logos/"+organisation.getTopbarName();
				CMSConstants.STUDENT_CHANGE_PASSWORD = organisation.getChangePassword().toString();
				CMSConstants.LOGIN_LOGO=organisation.getLogo();
				if(organisation.getOpenHonoursCourseLink() != null){
					CMSConstants.OPEN_HONOURSCOURS_LINK=organisation.getOpenHonoursCourseLink();
				}
				if(organisation.getConvocationRegistration()!=null){
					CMSConstants.CONVOCATION_REGISTRATION=organisation.getConvocationRegistration();
				}
				BufferedImage imag=ImageIO.read(new ByteArrayInputStream(photo));
			    File f = new File(imageURL);
				ImageIO.write(imag, "jpg",new File(f,organisation.getTopbarName()));
				if(organisation.getLogo() != null){
					imag = ImageIO.read(new ByteArrayInputStream(organisation.getLogo()));
					f = new File(imageURL);
					ImageIO.write(imag, "jpg",new File(f,organisation.getLogoName()));
				}
			}
			StringBuffer buffer = new StringBuffer();
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents(CMSConstants.LOGIN_STUDENT);
			Iterator<NewsEventsTO> iterator = newsEventsList.iterator();
			while (iterator.hasNext()) {
				NewsEventsTO eventsTO = (NewsEventsTO ) iterator.next();
				buffer.append(eventsTO.getName()).append("<br></br>").toString();
			}
			CMSConstants.NEWS_DESCRIPTION =buffer.toString();
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}
