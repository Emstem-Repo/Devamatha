package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;

public class RenderSchemeOrCourseList extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RenderSchemeOrCourseList.class);
	
	
	public int doStartTag() {
			InputStream propStream=RenderSchemeOrCourseList.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();
			 try {
			prop.load(propStream);
			int schemeMaxRange=Integer.parseInt(prop.getProperty(CMSConstants.SCHEME_MAXRANGE_KEY));
			int schemeMinRange=Integer.parseInt(prop.getProperty(CMSConstants.SCHEME_MINRANGE_KEY));
	        JspWriter out = pageContext.getOut();
		       for(;schemeMinRange<=schemeMaxRange;schemeMinRange++)
		      {
		    	  out.println("<option value="+schemeMinRange+">"+schemeMinRange+"</option>"); 
		      }
		    
		  } catch (IOException io) {
			  log.error("Unable to read properties file --- In RenderSchemeOrCourseList");
		}catch (Exception e) {
			log.error("Error occured at RenderSchemeOrCourseList");		 
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
