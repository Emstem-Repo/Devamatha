package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ExportDataSearchTO;

public class ExportDataSearchForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String method;
	private String academicYear;
	private String[] courseNames;
	private List<ExportDataSearchTO> candidateList;
	private String fileDownload;
	private String validTill;
	private String applicationNo;
	private String regNo;
	private String rollNo;
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public String[] getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(String[] courseNames) {
		this.courseNames = courseNames;
	}
	public List<ExportDataSearchTO> getCandidateList() {
		return candidateList;
	}
	public void setCandidateList(List<ExportDataSearchTO> candidateList) {
		this.candidateList = candidateList;
	}
	public String getFileDownload() {
		return fileDownload;
	}
	public void setFileDownload(String fileDownload) {
		this.fileDownload = fileDownload;
	}
	public String getValidTill() {
		return validTill;
	}
	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}
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
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setSemister(null);
		this.courseNames = null;
		this.validTill = null;
		this.applicationNo=null;
		this.regNo=null;
		this.rollNo=null;
	}
	
}