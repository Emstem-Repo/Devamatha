package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;

public class AdmBioDataAcademicYear extends TagSupport{
	private static final Log log = LogFactory.getLog(RenderYearList.class);
	private String normalYear="false";
	
	public String getNormalYear() {
		return normalYear;
	}
	public void setNormalYear(String normalYear) {
		this.normalYear = normalYear;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public int doStartTag() {
			InputStream propStream=RenderYearList.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();
			 try {
				 final Calendar cal = new GregorianCalendar();
				// final int year = cal.get(Calendar.YEAR);   	 
			prop.load(propStream);
			int yearMaxRange=2025;
			int yearMinRange=1980;
	        JspWriter out = pageContext.getOut();
		       for(;yearMinRange<=yearMaxRange;yearMinRange++)
		      {
		    		   if(this.normalYear.equalsIgnoreCase("false"))
		    		   out.println("<option value="+yearMinRange+">"+yearMinRange+"-"+(yearMinRange+1)+"</option>");
		    		   else if(this.normalYear.equalsIgnoreCase("true"))
		    			   out.println("<option value="+yearMinRange+">"+yearMinRange+"</option>");
		    	   }
		  } catch (Exception e) {
			  log.error("Error occured at RenderYearList" + e.getMessage());
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
