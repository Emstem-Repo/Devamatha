package com.kp.cms.forms.reports;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class CancelledAdmissionsForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String academicYear;
	private String fromDate;
	private String toDate;
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.toDate=null;
		this.fromDate=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}
