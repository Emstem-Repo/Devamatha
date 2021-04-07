package com.kp.cms.transactions.usermanagement;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.usermanagement.ChangePasswordForm;


public interface IChangePassword {
	public boolean changePassword(ChangePasswordForm changePasswordForm) throws Exception;	
	public Users verifyUser(ChangePasswordForm changePasswordForm) throws ApplicationException;
	public boolean changeStudentPassword(ChangePasswordForm changePasswordForm) throws Exception;	
	public StudentLogin verifyStudent(ChangePasswordForm changePasswordForm) throws ApplicationException;	

	
}
