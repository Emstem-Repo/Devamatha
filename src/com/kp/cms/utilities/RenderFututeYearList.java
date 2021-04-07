package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;


public class RenderFututeYearList extends TagSupport{
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
	private static final Log log = LogFactory.getLog(RenderFututeYearList.class);
	
	
	public int doStartTag() {
			InputStream propStream=RenderFututeYearList.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();
			 try {
				 Calendar cal = new GregorianCalendar();
				 int year = cal.get(Calendar.YEAR);   	 
			prop.load(propStream);
			int yearMaxRange=Integer.parseInt(prop.getProperty(CMSConstants.FUTUREYEAR_MAXRANGE_KEY));
			int yearMinRange=Integer.parseInt(prop.getProperty(CMSConstants.FUTUREYEAR_MINRANGE_KEY));
	        JspWriter out = pageContext.getOut();
		       for(;yearMinRange<=yearMaxRange;yearMinRange++)
		      {
		    	   if(year == yearMinRange){
		    		   if(this.normalYear.equalsIgnoreCase("false"))
		    		   out.println("<option value="+yearMinRange+" selected='selected'>"+yearMinRange+"-"+(yearMinRange+1)+"</option>");
		    		   else if(this.normalYear.equalsIgnoreCase("true"))
			    		   out.println("<option value="+yearMinRange+" selected='selected'>"+yearMinRange+"</option>");
		    	   }else{
		    		   if(this.normalYear.equalsIgnoreCase("false"))
		    		   out.println("<option value="+yearMinRange+">"+yearMinRange+"-"+(yearMinRange+1)+"</option>");
		    		   else if(this.normalYear.equalsIgnoreCase("true"))
		    			   out.println("<option value="+yearMinRange+">"+yearMinRange+"</option>");
		    	   }
		      }
		    
		  } catch (IOException io) {
			  log.error("Error occured at RenderFututeYearList");
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
