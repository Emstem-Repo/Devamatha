package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.CourseWithStudentTO;
import com.kp.cms.to.reports.SecondLanguageReportTO;

public class SecondLanguageForm extends BaseActionForm{
	private String feeStatus;
	private String secondLanguage;
	private String searchBy;
	private List<SecondLanguageReportTO> totalStudentList;
	private List<CourseWithStudentTO> studentList;
	private String organizationName;
	private String programTypeName;
	private String method;
	public List<SecondLanguageReportTO> getTotalStudentList() {
		return totalStudentList;
	}
	public void setTotalStudentList(List<SecondLanguageReportTO> totalStudentList) {
		this.totalStudentList = totalStudentList;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}
	
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public List<CourseWithStudentTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<CourseWithStudentTO> studentList) {
		this.studentList = studentList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void clear(){
		this.setProgramTypeId(null);
		this.setProgramId(null);
		this.setCourseId(null);
		this.setSemister(null);
		this.secondLanguage=null;
		this.feeStatus=null;
		this.searchBy=null;
		super.setYear(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
