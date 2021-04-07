package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo;

public class ViewMyAttendanceLeaveForm extends BaseActionForm{
	private int id;
	private String attendanceLeaveDate;
	private String method;
	private String printPage;
	private String teacherName;
	private Map<String,ViewMyAttendanceLeaveTo> attendanceLeaveMap;
	private List<ViewMyAttendanceLeaveTo> leaveTo;
	private Map<Integer,String> teachersMap;
	private String teachers;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttendanceLeaveDate() {
		return attendanceLeaveDate;
	}
	public void setAttendanceLeaveDate(String attendanceLeaveDate) {
		this.attendanceLeaveDate = attendanceLeaveDate;
	}
	public Map<String, ViewMyAttendanceLeaveTo> getAttendanceLeaveMap() {
		return attendanceLeaveMap;
	}
	public void setAttendanceLeaveMap(
			Map<String, ViewMyAttendanceLeaveTo> attendanceLeaveMap) {
		this.attendanceLeaveMap = attendanceLeaveMap;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public List<ViewMyAttendanceLeaveTo> getLeaveTo() {
		return leaveTo;
	}
	public void setLeaveTo(List<ViewMyAttendanceLeaveTo> leaveTo) {
		this.leaveTo = leaveTo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeachers() {
		return teachers;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
}
