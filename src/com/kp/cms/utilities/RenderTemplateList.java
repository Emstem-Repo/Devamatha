package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RenderTemplateList extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(RenderYear.class);
	private String templateName="";
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public int doStartTag() {
		try {
			Properties prop = new Properties();
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/templates.properties");
			prop.load(in);
			
			Iterator templatesItr = prop.values().iterator();
			
			JspWriter out = pageContext.getOut();
			while (templatesItr.hasNext()) {
				Object object = (Object) templatesItr.next();
				
				out.println("<option value="+object+">"+object+"</option>");
			}	
		} catch (IOException io) {
			log.error("Error occured at RenderTemplateList" + io.getMessage());	 
		}catch (Exception e) {
			log.error("Error occured at RenderTemplateList" + e.getMessage());	 
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