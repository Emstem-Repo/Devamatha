package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceReEntryTo;

public class AttendanceReEntryForm extends BaseActionForm {
	
	private String classes;
	private String fromDate;
	private String toDate;
	private String year;
	private String regNo;
	private String appNo;
	Map<Integer,String> classMap;
	Map<Date, AttendanceReEntryTo> attendanceReEntryTos;
	private int stuId;
	List<AttendanceReEntryTo> list;
	private String studentName;
	private String range;
	
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public Map<Date, AttendanceReEntryTo> getAttendanceReEntryTos() {
		return attendanceReEntryTos;
	}
	public void setAttendanceReEntryTos(
			Map<Date, AttendanceReEntryTo> attendanceReEntryTos) {
		this.attendanceReEntryTos = attendanceReEntryTos;
	}

	public List<AttendanceReEntryTo> getList() {
		return list;
	}
	public void setList(List<AttendanceReEntryTo> list) {
		this.list = list;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	/**
	 * 
	 */
	public void clearAll(){
		this.classes = null;
		this.fromDate = null;
		this.toDate=null;
		this.regNo=null;
		this.year=null;
		this.appNo=null;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
}
