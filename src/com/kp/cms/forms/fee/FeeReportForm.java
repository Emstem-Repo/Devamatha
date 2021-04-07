package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeReportTO;

public class FeeReportForm  extends BaseActionForm{
	
	private String applicationNo;
	private String regNo;
	private String academicYear;
	private String semister;
	private String status;
	private List<FeeReportTO> studentSearch;
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public List<FeeReportTO> getStudentSearch() {
		return studentSearch;
	}
	public void setStudentSearch(List<FeeReportTO> studentSearch) {
		this.studentSearch = studentSearch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.applicationNo = null;
		this.regNo = null;
		this.academicYear = null;
		this.semister = null;		
		this.status = null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);		
	}
	

}
