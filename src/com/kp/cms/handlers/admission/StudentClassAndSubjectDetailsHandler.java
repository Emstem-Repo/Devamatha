package com.kp.cms.handlers.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentClassAndSubjectDetailsForm;
import com.kp.cms.helpers.admission.StudentClassAndSubjectDetailsHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IStudentClassAndSubjectDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.StudentClassAndSubjectDetailsTransactionImpl;

public class StudentClassAndSubjectDetailsHandler {
	/**
	 * Singleton object of StudentClassAndSubjectDetailsHandler
	 */
	private static volatile StudentClassAndSubjectDetailsHandler studentClassAndSubjectDetailsHandler = null;
	private static final Log log = LogFactory.getLog(StudentClassAndSubjectDetailsHandler.class);
	private StudentClassAndSubjectDetailsHandler() {
		
	}
	/**
	 * return singleton object of StudentClassAndSubjectDetailsHandler.
	 * @return
	 */
	public static StudentClassAndSubjectDetailsHandler getInstance() {
		if (studentClassAndSubjectDetailsHandler == null) {
			studentClassAndSubjectDetailsHandler = new StudentClassAndSubjectDetailsHandler();
		}
		return studentClassAndSubjectDetailsHandler;
	}
	/**
	 * To get the classes and subjects of the student
	 * @param stuForm
	 * @return
	 * @throws Exception
	 */
	public StudentTO getStudentClassandSubjects(StudentClassAndSubjectDetailsForm stuForm) throws Exception {
		// TODO Auto-generated method stub
		IStudentClassAndSubjectDetailsTransaction txn = StudentClassAndSubjectDetailsTransactionImpl.getInstance();
		Student stuBo= txn.getStudentBO(stuForm);
		StudentTO stuTO= StudentClassAndSubjectDetailsHelper.getInstance().convertBOsToTOs(stuBo);
		return stuTO;
	}
	
}
