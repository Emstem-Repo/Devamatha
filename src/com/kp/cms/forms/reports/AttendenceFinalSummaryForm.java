package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class AttendenceFinalSummaryForm extends BaseActionForm {

	private String academicYear;

	private String[] attendanceType;

	private String startDate;

	private String endDate;

	private Boolean leaveType;
	
	private Map<Integer, String> classMap = new HashMap<Integer, String>();
	
	private String[] classesName;
	

	public String[] getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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

	public Boolean getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Boolean leaveType) {
		this.leaveType = leaveType;
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

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {

		this.academicYear = null;
		this.attendanceType = null;
		this.startDate = null;
		this.endDate = null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setSemister(null);
		super.setActivityId(null);
		this.setLeaveType(null);
		this.classesName = null;
		this.classMap = null;
	}

}
