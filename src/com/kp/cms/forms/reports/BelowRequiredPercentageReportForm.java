package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.SubjectSummaryTO;

public class BelowRequiredPercentageReportForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String semister;
	private String academicYear;
	private String[] attendanceType;
	private String startDate;
	private String endDate;
	private String courseName;	
	private Boolean leaveType;
	private String requiredPercentage;
	private List<SubjectSummaryTO> subjectLSummaryist;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();	
	private String[] classesName;
	private String[] subjects;
	
	
	public String[] getSubjects() {
		return subjects;
	}
	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
	public String getRequiredPercentage() {
		return requiredPercentage;
	}
	public void setRequiredPercentage(String requiredPercentage) {
		this.requiredPercentage = requiredPercentage;
	}
	public String getSemister() {
		return semister;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public String[] getAttendanceType() {
		return attendanceType;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	public Boolean getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Boolean leaveType) {
		this.leaveType = leaveType;
	}
	
	public List<SubjectSummaryTO> getSubjectLSummaryist() {
		return subjectLSummaryist;
	}
	public void setSubjectLSummaryist(List<SubjectSummaryTO> subjectLSummaryist) {
		this.subjectLSummaryist = subjectLSummaryist;
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
		
		this.semister = null;
		this.academicYear = null;
		this.attendanceType = null;		
		this.startDate = null;
		this.endDate = null;
		this.courseName = null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);	
		this.requiredPercentage = null;
		this.leaveType = null;
		this.classesName = null;
		this.setActivityId(null);
		this.subjects = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
