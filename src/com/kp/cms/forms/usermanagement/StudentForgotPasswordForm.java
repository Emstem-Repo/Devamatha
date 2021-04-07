package com.kp.cms.forms.usermanagement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.BaseActionForm;

public class StudentForgotPasswordForm extends BaseActionForm {
	
	private String registerNo;
	private String dob;
	private StudentLogin studentLogin;
	private String userName;
	private String employeeid;
	private Users user;
	
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public StudentLogin getStudentLogin() {
		return studentLogin;
	}
	public void setStudentLogin(StudentLogin studentLogin) {
		this.studentLogin = studentLogin;
	}
	/**
	 * 
	 */
	public void resetFields(){
		this.registerNo=null;
		this.dob=null;
		this.studentLogin=null;
		this.userName=null;
		this.user=null;
		this.employeeid=null;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	
	
}
