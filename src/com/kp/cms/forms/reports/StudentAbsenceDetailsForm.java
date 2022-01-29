package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceTypeTO;

public class StudentAbsenceDetailsForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String academicYear;
	private String[] attendanceType;
	private String startDate;
	private String endDate;
	private String fromRegisterNo;
	private String toRegisterNo;
	private String fromRollNo;
	private String toRollNo;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();
	private List<AttendanceTypeTO> attendanceTypeList;
	private String[] classesName;
	private String[] subjects;
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String[] getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
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
	
	public String getFromRegisterNo() {
		return fromRegisterNo;
	}
	public void setFromRegisterNo(String fromRegisterNo) {
		this.fromRegisterNo = fromRegisterNo;
	}
	public String getToRegisterNo() {
		return toRegisterNo;
	}
	public void setToRegisterNo(String toRegisterNo) {
		this.toRegisterNo = toRegisterNo;
	}
	public String getFromRollNo() {
		return fromRollNo;
	}
	public void setFromRollNo(String fromRollNo) {
		this.fromRollNo = fromRollNo;
	}
	public String getToRollNo() {
		return toRollNo;
	}
	public void setToRollNo(String toRollNo) {
		this.toRollNo = toRollNo;
	}
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}
	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}
	public String[] getClassesName() {
		return classesName;
	}
	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}
	public String[] getSubjects() {
		return subjects;
	}
	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
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
		this.fromRegisterNo = null;
		this.toRegisterNo = null;
		this.fromRollNo = null;
		this.toRollNo = null;
		this.classesName = null;
		this.subjects = null;
		
	}
}