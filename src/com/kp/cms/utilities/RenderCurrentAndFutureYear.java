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


public class RenderCurrentAndFutureYear extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RenderCurrentAndFutureYear.class);
	
	public int doStartTag() {
			InputStream propStream=RenderYear.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();
			 try {
			prop.load(propStream);
			Calendar cal = new GregorianCalendar();
			int currentYear = cal.get(Calendar.YEAR);
			int year = currentYear - 1;   	 
			int yearMaxRange= Integer.parseInt(prop.getProperty(CMSConstants.FUTUREYEAR_MAXRANGE_KEY));	
			int yearMinRange=year;
			JspWriter out = pageContext.getOut();
		       for(;yearMinRange<=yearMaxRange;yearMinRange++)
		      {	    
		    	   if( yearMinRange == currentYear){
		    		   out.println("<option value="+yearMinRange+" selected='selected'>"+yearMinRange+"-"+(yearMinRange+1)+"</option>");
		    	   }
		    	   else
		    	   {
		    		   out.println("<option value="+yearMinRange+">"+yearMinRange+"-"+(yearMinRange+1)+"</option>");
		    	   }
		      }		    
		  } catch (Exception e) {
			log.error("Error occured at RenderYear" + e.getMessage());	 
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
