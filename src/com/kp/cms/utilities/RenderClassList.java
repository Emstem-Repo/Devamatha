package com.kp.cms.utilities;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.to.attendance.ClassesTO;

public class RenderClassList extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RenderClassList.class);
	
	public int doStartTag() {
		ClassEntryHandler classEntryHandler = ClassEntryHandler.getInstance();
	        try {
	        	Calendar calendar = Calendar.getInstance();
	    		final int currentYear = calendar.get(Calendar.YEAR);
	    		
	        	List<ClassesTO> classList=classEntryHandler.getAllClasses(currentYear);
				
				JspWriter out = pageContext.getOut();
					Iterator<ClassesTO> itr= classList.iterator();
					while (itr.hasNext()) {
						ClassesTO classesTO = (ClassesTO) itr.next();
						out.println("<option value="+classesTO.getId()+">"+classesTO.getClassName()+"</option>");
					}
			} catch (Exception e) {
				log.error("Errpr occured at RenderClassList" + e);
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
