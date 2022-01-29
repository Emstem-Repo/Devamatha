package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.forms.reports.StudentForUniversityForm;
import com.kp.cms.helpers.reports.StudentForUniversityHelper;
import com.kp.cms.to.reports.CourseWithStudentUniversityTO;
import com.kp.cms.to.reports.StudentForUniversityTO;
import com.kp.cms.transactions.reports.IStudentForUniversityTransaction;
import com.kp.cms.transactionsimpl.reports.StudentForUniversityTransactionImpl;

public class StudentForUniversityHandler {
	/**
	 * Singleton object of ScoreSheetHandler
	 */
	private static volatile StudentForUniversityHandler studentForUniversityHandler = null;
	private static final Log log = LogFactory.getLog(StudentForUniversityHandler.class);
	private StudentForUniversityHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static StudentForUniversityHandler getInstance() {
		if (studentForUniversityHandler == null) {
			studentForUniversityHandler = new StudentForUniversityHandler();
		}
		return studentForUniversityHandler;
	}
	/**
	 * Gets all the offline Details
	 */
	public List<CourseWithStudentUniversityTO> getAllStudentsForUniversity(StudentForUniversityForm studentForUniversityForm)throws Exception
	{
		log.info("Entering into StudentForUniversityHandler of getAllStudentsForUniversity");
		IStudentForUniversityTransaction transaction=new StudentForUniversityTransactionImpl();
		String searchCriteria=StudentForUniversityHelper.getInstance().getSearchQuery(studentForUniversityForm);
		List studentBOList=transaction.getAllStudentForUniversity(searchCriteria);
		List<Integer> detainedStudentList=transaction.getDetainedOrDiscontinuedStudents();
		log.info("Leaving into StudentForUniversityHandler of getAllStudentsForUniversity");
		return StudentForUniversityHelper.getInstance().convertBotoTo(studentBOList,studentForUniversityForm,detainedStudentList);
	}
}
