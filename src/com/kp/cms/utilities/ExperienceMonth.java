package com.kp.cms.utilities;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExperienceMonth extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ExperienceMonth.class);
	private int expLimit;
	private boolean year;
	private boolean month;
	private boolean lack;
	private boolean thousands;

	public int getExpLimit() {
		return expLimit;
	}

	public void setExpLimit(int expLimit) {
		this.expLimit = expLimit;
	}

	public int doStartTag() {
		try {

			JspWriter out = pageContext.getOut();
			if (isMonth()) {
				for (int i = 0; i <=getExpLimit(); i++) {
					out.println("<option value=" + i + ">" + i + "</option>");
				}
			} else if(isYear()) {
				for (int i = 0; i <=getExpLimit(); i++) {
					out.println("<option value=" + i + ">" + i + "</option>");
				}
			}else if(isLack()){
				for (int i = 0; i <=getExpLimit(); i++) {
					out.println("<option value=" + i + ">" + i + "</option>");
				}
			}else if(isThousands()){
				for (int i = 0; i <=getExpLimit(); i=i+5) {
					out.println("<option value=" + i + ">" + i + "</option>");
				}	
			}
		} catch (Exception e) {
			log.error("Error occured at ExperienceYear" + e.getMessage());
		}
		return SKIP_BODY;
	}

	/**
	 * doEndTag is called by the JSP container when the tag is closed
	 */
	public int doEndTag() {
		return EVAL_PAGE;
	}

	public boolean isYear() {
		return year;
	}

	public void setYear(boolean year) {
		this.year = year;
	}

	public boolean isMonth() {
		return month;
	}

	public void setMonth(boolean month) {
		this.month = month;
	}

	public void setLack(boolean lack) {
		this.lack = lack;
	}

	public boolean isLack() {
		return lack;
	}

	public void setThousands(boolean thousands) {
		this.thousands = thousands;
	}

	public boolean isThousands() {
		return thousands;
	}

}
