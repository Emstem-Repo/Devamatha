package com.kp.cms.utilities;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;


import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.ProgramTypeTO;


public class ProgramTypesOnlineOpenTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(RenderYear.class);
	
	
	public int doStartTag() {
		ProgramTypeHandler programtypehandler = ProgramTypeHandler
		.getInstance();
		
	        try {
	        	List<ProgramTypeTO> programtypelist=programtypehandler.getProgramTypeOnlineOpen();
				
				JspWriter out = pageContext.getOut();
					Iterator<ProgramTypeTO> itr= programtypelist.iterator();
					while (itr.hasNext()) {
						ProgramTypeTO programTypeTO = (ProgramTypeTO) itr.next();
						//if(programTypeTO.getProgramTypeId()==1)
						//out.println("<html:option value="+programTypeTO.getProgramTypeId()+" label="+programTypeTO.getProgramTypeName()+"/>");
						out.println("<option value="+programTypeTO.getProgramTypeId()+">"+programTypeTO.getProgramTypeName()+"</option>");
					}
					
				
			} catch (IOException io) {
				log.error("Error occured at RenderProgram type" + io.getMessage());	 
			}catch (Exception e) {
				log.error("Error occured at RenderProgram type" + e.getMessage());	 
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
