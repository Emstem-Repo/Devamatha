package com.kp.cms.forms.admission;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class CertificateCourseCopyForm extends BaseActionForm {
	
	private String fromAcademicYear;
	private String toAcademicYear;
	private String fromSemType;
	private String toSemType;
	
	
	
	public String getFromAcademicYear() {
		return fromAcademicYear;
	}
	public void setFromAcademicYear(String fromAcademicYear) {
		this.fromAcademicYear = fromAcademicYear;
	}
	public String getToAcademicYear() {
		return toAcademicYear;
	}
	public void setToAcademicYear(String toAcademicYear) {
		this.toAcademicYear = toAcademicYear;
	}
	public String getFromSemType() {
		return fromSemType;
	}
	public void setFromSemType(String fromSemType) {
		this.fromSemType = fromSemType;
	}
	public String getToSemType() {
		return toSemType;
	}
	public void setToSemType(String toSemType) {
		this.toSemType = toSemType;
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fromAcademicYear=null;
		toAcademicYear=null;
		fromSemType=null;
		toSemType=null;
	}
	

}
