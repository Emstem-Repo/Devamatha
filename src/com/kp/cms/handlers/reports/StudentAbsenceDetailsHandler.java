package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.reports.StudentAbsenceDetailsForm;
import com.kp.cms.helpers.reports.StudentAbsenceDetailsHelper;
import com.kp.cms.to.reports.StudentAbsenceDetailsTO;
import com.kp.cms.transactions.reports.IStudentAbsenceDetailsTransaction;
import com.kp.cms.transactionsimpl.reports.StudentAbsenceDetailsTransactionImpl;

public class StudentAbsenceDetailsHandler {
	
private static final Log log = LogFactory.getLog(StudentAbsenceDetailsHandler.class);
	
	private static volatile StudentAbsenceDetailsHandler studentAbsenceDetailsHandler = null;
	
	private StudentAbsenceDetailsHandler(){
	}
	/**
	 * This method returns the single instance of this StudentAbsenceDetailsHandler.
	 * @return
	 */
	public static StudentAbsenceDetailsHandler getInstance() {
      if(studentAbsenceDetailsHandler == null) {
    	  studentAbsenceDetailsHandler = new StudentAbsenceDetailsHandler();
      }
      return studentAbsenceDetailsHandler;
	}
	
	/**
	 * Method to fetch the Student Absence Details
	 * @param studentAbsenceDetailsForm
	 * @return studentAbsenceDetails list 
	 * @throws Exception
	 */
	public List<StudentAbsenceDetailsTO> getStudentAbsenceDetails(
			StudentAbsenceDetailsForm studentAbsenceDetailsForm) throws Exception {
		log.info("Entered getStudentAbsenceDetails");
		
		IStudentAbsenceDetailsTransaction studentAbsenceDetails = new StudentAbsenceDetailsTransactionImpl();
		
		StudentAbsenceDetailsHelper studentAbsenceDetailsHelper = new StudentAbsenceDetailsHelper();
		
		List<AttendanceStudent> studentAbsenceDetailsList = studentAbsenceDetails.getStudentAbsenceDetails(studentAbsenceDetailsHelper.getStudentAbsenceDetails(studentAbsenceDetailsForm));
		
		List<StudentAbsenceDetailsTO> studentAbsenceDetailsTOList = studentAbsenceDetailsHelper.convertBoToTo(studentAbsenceDetailsList);
		
		log.info("Exit getStudentAbsenceDetails");
		
		return studentAbsenceDetailsTOList;
	}
}