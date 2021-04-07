package com.kp.cms.forms.usermanagement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ChangePasswordForm extends BaseActionForm{
	private String existingPwd;
	private String newPwd;
	private String reTypeNewPwd;
	private String tempUname;
	private String dob;
	private String email;
	private boolean tempPassword;
	
	public String getExistingPwd() {
		return existingPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public String getReTypeNewPwd() {
		return reTypeNewPwd;
	}
	public void setExistingPwd(String existingPwd) {
		this.existingPwd = existingPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public void setReTypeNewPwd(String reTypeNewPwd) {
		this.reTypeNewPwd = reTypeNewPwd;
	}
	public String getTempUname() {
		return tempUname;
	}
	public void setTempUname(String tempUname) {
		this.tempUname = tempUname;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the tempPassword
	 */
	public boolean isTempPassword() {
		return tempPassword;
	}
	/**
	 * @param tempPassword the tempPassword to set
	 */
	public void setTempPassword(boolean tempPassword) {
		this.tempPassword = tempPassword;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		existingPwd = null;
		newPwd = null;
		reTypeNewPwd = null;
		dob = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
