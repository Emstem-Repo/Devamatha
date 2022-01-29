package com.kp.cms.utilities;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.handlers.reports.AddressReportHandler;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;

public class CurrentAcademicYear
{private static volatile CurrentAcademicYear currentAcademicYear = null;
	private CurrentAcademicYear() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static CurrentAcademicYear getInstance() {
		if (currentAcademicYear == null) {
			currentAcademicYear = new CurrentAcademicYear();
		}
		return currentAcademicYear;
	}
	
	 public int getAcademicyear() throws Exception
	{
		
		 int year=0; 
		 AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
		List<AcademicYear> yearList = impl.getAllAcademicYear();
		       if(yearList!=null){
	    Iterator<AcademicYear> iterator = yearList.iterator();
	        while (iterator.hasNext()) {
	        	AcademicYear attendanceAcademicYear = iterator.next();
				if(attendanceAcademicYear.getIsCurrent()!=null && attendanceAcademicYear.getIsCurrent()){
					year=attendanceAcademicYear.getYear();
				}
			}
		       }	
	
	 return year;
	 }
	/**
	 * @return
	 * @throws Exception 
	 */
	public int getCurrentAcademicyear() throws Exception {
		 int year=0; 
		 AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
		 year = impl.getCurrentAcademicYear();
		 return year;
	}
	public int getCurrentAcademicYearforTeacher() throws Exception {
		 int year=0; 
		 AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
		 year = impl.getCurrentAcademicYearforTeacher();
		 return year;
	}
	
}
	

