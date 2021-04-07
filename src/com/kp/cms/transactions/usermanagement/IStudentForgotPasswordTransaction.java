package com.kp.cms.transactions.usermanagement;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.usermanagement.StudentForgotPasswordForm;

public interface IStudentForgotPasswordTransaction {

	StudentLogin checkValidData(String query) throws Exception;

	StudentLogin changePassword(StudentForgotPasswordForm studentForgotPasswordForm, String encpass, String iserverPassword) throws Exception;

	Users checkValidUser(String query) throws Exception;

}
