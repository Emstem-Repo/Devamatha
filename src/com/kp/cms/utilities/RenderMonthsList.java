package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RenderMonthsList extends TagSupport {

	private static final Log log = LogFactory.getLog(RenderYear.class);
	private String normalMonth = "false";

	public String getNormalMonth() {
		return normalMonth;
	}

	public void setNormalMonth(String normalMonth) {
		this.normalMonth = normalMonth;
	}

	public int doStartTag() {

		JspWriter out = pageContext.getOut();

		try {

			if (this.normalMonth.equalsIgnoreCase("true")) {
				out.println("<option value=00>JAN </option>");
				out.println("<option value=01>FEB</option>");
				out.println("<option value=02>MAR </option>");
				out.println("<option value=03>APR </option>");
				out.println("<option value=04>MAY </option>");
				out.println("<option value=05>JUN </option>");
				out.println("<option value=06>JUL </option>");
				out.println("<option value=07>AUG </option>");
				out.println("<option value=08>SEP </option>");
				out.println("<option value=09>OCT </option>");
				out.println("<option value=10>NOV </option>");
				out.println("<option value=11>DEC </option>");
			} else {

				out.println("<option value=00>January </option>");
				out.println("<option value=01>February </option>");
				out.println("<option value=02>March </option>");
				out.println("<option value=03>April </option>");
				out.println("<option value=04>May </option>");
				out.println("<option value=05>June </option>");
				out.println("<option value=06>July </option>");
				out.println("<option value=07>August </option>");
				out.println("<option value=08>September </option>");
				out.println("<option value=09>October </option>");
				out.println("<option value=10>November </option>");
				out.println("<option value=11>December </option>");
			}
		} catch (IOException e) {
			log.error("Error occured at RenderMonth" + e.getMessage());
		}
		return 0;

	}

	/**
	 * doEndTag is called by the JSP container when the tag is closed
	 */
	public int doEndTag() {

		return EVAL_PAGE;
	}

}
