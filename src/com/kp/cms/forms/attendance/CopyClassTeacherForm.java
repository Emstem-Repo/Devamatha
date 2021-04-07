package com.kp.cms.forms.attendance;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

public class CopyClassTeacherForm extends BaseActionForm{
	
	private String toAcademicYear;
	private String fromAcademicYear;
	private String semType;
	private int id;
	public String getToAcademicYear() {
		return toAcademicYear;
	}
	public void setToAcademicYear(String toAcademicYear) {
		this.toAcademicYear = toAcademicYear;
	}
	public String getFromAcademicYear() {
		return fromAcademicYear;
	}
	public void setFromAcademicYear(String fromAcademicYear) {
		this.fromAcademicYear = fromAcademicYear;
	}
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		semType=null;
		this.setToAcademicYear(null);
		this.setFromAcademicYear(null);
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
