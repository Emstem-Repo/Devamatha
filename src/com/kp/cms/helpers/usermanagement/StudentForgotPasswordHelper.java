package com.kp.cms.helpers.usermanagement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.usermanagement.StudentForgotPasswordForm;
import com.kp.cms.utilities.CommonUtil;

public class StudentForgotPasswordHelper {
	private static final Log log = LogFactory.getLog(StudentForgotPasswordHelper.class);
	/**
	 * Singleton object of StudentForgotPasswordHelper
	 */
	private static volatile StudentForgotPasswordHelper studentForgotPasswordHelper = null;
	private StudentForgotPasswordHelper() {
		
	}
	/**
	 * return singleton object of StudentForgotPasswordHelper.
	 * @return
	 */
	public static StudentForgotPasswordHelper getInstance() {
		if (studentForgotPasswordHelper == null) {
			studentForgotPasswordHelper = new StudentForgotPasswordHelper();
		}
		return studentForgotPasswordHelper;
	}
	/**
	 * @param studentForgotPasswordForm
	 * @return
	 * @throws Exception
	 */
	public String checkValidDataQuery(StudentForgotPasswordForm studentForgotPasswordForm) throws Exception {
		String query="from StudentLogin s where s.isActive=1 and s.student.isActive=1 " +
				"and s.student.admAppln.isCancelled=0 and s.userName='"+studentForgotPasswordForm.getRegisterNo()
				+"' and s.student.admAppln.personalData.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(studentForgotPasswordForm.getDob())+"'";
		return query;
	}
	public String checkValidUserQuery(StudentForgotPasswordForm studentForgotPasswordForm) {
		String query="from Users u where u.isActive=1 and u.employee.fingerPrintId='" +studentForgotPasswordForm.getEmployeeid()+"'"+		
		" and u.employee <> null and u.employee.dob='"+CommonUtil.ConvertStringToSQLDate(studentForgotPasswordForm.getDob())+"'";
		return query;
	}
}
