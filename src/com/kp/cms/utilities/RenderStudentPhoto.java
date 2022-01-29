package com.kp.cms.utilities;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.reports.ScoreSheetTO;

public class RenderStudentPhoto extends TagSupport {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RenderStudentPhoto.class);
	private String applicationNO;
	private String dob;
	
	
	public String getApplicationNO() {
		return applicationNO;
	}
	public void setApplicationNO(String applicationNO) {
		this.applicationNO = applicationNO;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	
	public int doStartTag() {
		
			 try {
//				 ServletResponse response= pageContext.getResponse();
//				 response.setContentType("image/jpeg");
//				ServletOutputStream out = response.getOutputStream();
				 JspWriter out= pageContext.getOut();
				 HttpSession session= pageContext.getSession();
				 if(session!=null && session.getAttribute("SelectedCandidates")!=null){
					 List<ScoreSheetTO> selectedList=(List<ScoreSheetTO>)session.getAttribute("SelectedCandidates");
					 if(selectedList!=null){
						 Iterator<ScoreSheetTO> scoreItr=selectedList.iterator();
						 while (scoreItr.hasNext()) {
							ScoreSheetTO scoreSheetTO = (ScoreSheetTO) scoreItr.next();
							if(scoreSheetTO.getApplicationNo()!=null && scoreSheetTO.getApplicationNo().equalsIgnoreCase(applicationNO)
									&& scoreSheetTO.getDateOfBirth()!=null && scoreSheetTO.getDateOfBirth().equalsIgnoreCase(dob)){
								byte[] photobytes=scoreSheetTO.getPhotoBytes();
								if(out!=null)
									pageContext.getResponse().getOutputStream().write(photobytes);
							}
						}
					 }
				 }
		         
		  } catch (Exception e) {
			log.error("Error occured at RenderStudentPhoto" + e.getMessage());	 
	    }
		 return SKIP_BODY;
	}
	/**
	 * doEndTag is called by the JSP container when the tag is closed
	 */
		public int doEndTag(){		  
		return EVAL_PAGE;
		}

}
