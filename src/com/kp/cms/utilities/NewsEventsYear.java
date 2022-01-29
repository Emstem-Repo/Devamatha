package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;

public class NewsEventsYear extends TagSupport{
	private static final Log log = LogFactory.getLog(NewsEventsYear.class);
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
		InputStream propStream=NewsEventsYear.class.getResourceAsStream("/resources/application.properties");
		Properties prop= new Properties();
		 try {
		prop.load(propStream);
		AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
		List<AcademicYear> yearList = impl.getAllAcademicYear();
		int startYear=Integer.parseInt(prop.getProperty(CMSConstants.NEWEVENTS_FUTUREYEAR_MINRANGE_KEY));
		int endYear=Integer.parseInt(prop.getProperty(CMSConstants.NEWEVENTS_FUTUREYEAR_MAXRANGE_KEY));
        JspWriter out = pageContext.getOut();
        if(yearList!=null){
        Iterator<AcademicYear> iterator = yearList.iterator();
	        while (iterator.hasNext()) {
	        	AcademicYear attendanceAcademicYear = iterator.next();
				int academicYear =0;
				int currentYear=0;
				if(attendanceAcademicYear.getYear()!=null){
					academicYear = attendanceAcademicYear.getYear();
				}
				if(startYear>0 && endYear>0){
					if(academicYear>=startYear && academicYear <=endYear){
						if(attendanceAcademicYear.getIsCurrent()!=null && attendanceAcademicYear.getIsCurrent()){
							currentYear=academicYear;
							out.println("<option value="+academicYear+" selected='selected'>"+academicYear+"-"+(academicYear+1)+"</option>");
						}else{
							if(academicYear>currentYear){
							out.println("<option value="+academicYear+">"+academicYear+"-"+(academicYear+1)+"</option>");	
							}
						}
					}
				}
			}
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



