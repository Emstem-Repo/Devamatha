package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;

public class RenderMinToMaxYearList extends TagSupport {
	public static final Log log=LogFactory.getLog(RenderMinToMaxYearList.class);
	InputStream iStream=RenderMinToMaxYearList.class.getResourceAsStream("/resources/application.properties");
	Properties yearPop=new Properties();
	JspWriter out=null;
	
	public int doStartTag(){
		log.info("start of RenderMinToMaxYearList class in doStartTag");
		try {
			out=pageContext.getOut();
			yearPop.load(iStream);
			int minYear=Integer.parseInt(yearPop.getProperty(CMSConstants.YEAR_MINRANGE_KEY));
			int maxYear=Integer.parseInt(yearPop.getProperty(CMSConstants.YEAR_MAXRANGE_KEY));
			for(;minYear<=maxYear;){
				out.println("<option value="+minYear+" selected='selected'>"+minYear+"</option>");
				minYear++;				
			}
		} catch (Exception e) {			
			log.error("Error Occurred in RenderMinToMaxYearList class");
		}
		log.info("end of RenderMinToMaxYearList class in doStartTag");
		return SKIP_BODY;
	}
	
	public int doEndTag(){		
		return EVAL_PAGE;
	}
}
