package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.StudentVehicleForm;
import com.kp.cms.helpers.reports.StudentVehicleHelper;
import com.kp.cms.to.reports.StudentVehicleTO;
import com.kp.cms.transactions.reports.IStudentVehicleTransaction;
import com.kp.cms.transactionsimpl.reports.StudentVehicleTransactionImpl;

public class StudentVehicleHandler {
	private static volatile StudentVehicleHandler studentVehicleHandler = null;
	private static final Log log = LogFactory.getLog(StudentVehicleHandler.class);
	private StudentVehicleHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static StudentVehicleHandler getInstance() {
		if (studentVehicleHandler == null) {
			studentVehicleHandler = new StudentVehicleHandler();
		}
		return studentVehicleHandler;
	}
	public List<StudentVehicleTO> getStudentVehicleDetails(
			StudentVehicleForm studentVehicleForm) throws Exception{
		StudentVehicleHelper studentVehicleHelper=StudentVehicleHelper.getInstance();
		String SearchCriteria=studentVehicleHelper.getSearchQuery(studentVehicleForm);
		IStudentVehicleTransaction iStudentVehicleTransaction=new StudentVehicleTransactionImpl();
		List studentVehicleList=iStudentVehicleTransaction.getStudentVehicleDetails(SearchCriteria);
		List<StudentVehicleTO> studentDetails=studentVehicleHelper.convertBotoTo(studentVehicleList);
	
		
		return studentDetails;
	}
}
