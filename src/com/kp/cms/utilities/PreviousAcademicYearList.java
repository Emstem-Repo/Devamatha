package com.kp.cms.utilities;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;

public class PreviousAcademicYearList extends TagSupport{
	private static final Log log = LogFactory.getLog(PreviousAcademicYearList.class);
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
			
		try {
			AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
			List<AcademicYear> yearList = impl.getAllAcademicYear();
			
	        JspWriter out = pageContext.getOut();
	        if(yearList!=null){
	        Iterator<AcademicYear> iterator = yearList.iterator();
	        
		        while (iterator.hasNext()) {
		        	AcademicYear attendanceAcademicYear = iterator.next();
					int academicYear = 0;
					if(attendanceAcademicYear.getYear()!=null){
						academicYear = attendanceAcademicYear.getYear();
					}
					if((!attendanceAcademicYear.getIsCurrent())
							&& (attendanceAcademicYear.getIsCurrentForAdmission()==null || !attendanceAcademicYear.getIsCurrentForAdmission())){
						out.println("<option value="+academicYear+">"+academicYear+"-"+(academicYear+1)+"</option>");
					}
					
				}
		        
	        }
	    
		  } catch (Exception e) {
			  log.error("Error occured at PreviousAcademicYearList" + e.getMessage());
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
