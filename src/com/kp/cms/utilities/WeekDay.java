package com.kp.cms.utilities;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.StringTokenizer;

public class WeekDay extends TagSupport {
	private static final Log log = LogFactory.getLog(WeekDay.class);
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		StringTokenizer dateToken=new StringTokenizer(date,"-");
		Date weekdate=null;
		if(dateToken.hasMoreElements()){
			int year=Integer.parseInt((String)dateToken.nextElement());
			int month=Integer.parseInt((String)dateToken.nextElement())-1;
			int date=Integer.parseInt((String)dateToken.nextElement());
			weekdate=new GregorianCalendar(year,month,date).getTime();
		}
		SimpleDateFormat f = new SimpleDateFormat("EEEE");
		JspWriter out=pageContext.getOut();
		try {
			out.print("(<h5>"+f.format(weekdate)+"</h5>)");
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			log.error("Error occured at RenderYearList" + e.getMessage());
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

}
