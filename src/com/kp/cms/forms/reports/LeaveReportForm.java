package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.LeaveReportTO;

public class LeaveReportForm extends BaseActionForm {
	
	private String method;
	private String semister;
	private String academicYear;
	private String startDate;
	private String endDate;
	private String courseName;	
	private List<LeaveReportTO> leaveReportList;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();
	private String[] classesName;

	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<LeaveReportTO> getLeaveReportList() {
		return leaveReportList;
	}
	public void setLeaveReportList(List<LeaveReportTO> leaveReportList) {
		this.leaveReportList = leaveReportList;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String[] getClassesName() {
		return classesName;
	}
	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}
	public void resetFields() {
		this.startDate = null;
		this.endDate = null;
		this.classesName = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}