package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.List;

public class AttendanceTeacherReportTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String teacherName;
	private String subjectCode;
	private String subjectName;
	private String subjectId;	
	private String className;	
	private String hourseTaken;	
	private int userId;
	private int classId;
	private List<AttendanceTeacherReportTO> inchargeTeachersList;
	private String department;
	private String batch;
	private String classesId;
	
	public String getTeacherName() {
		return teacherName;
	}
	
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getHourseTaken() {
		return hourseTaken;
	}
	public void setHourseTaken(String hourseTaken) {
		this.hourseTaken = hourseTaken;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public void setInchargeTeachersList(List<AttendanceTeacherReportTO> inchargeTeachersList) {
		this.inchargeTeachersList = inchargeTeachersList;
	}
	public List<AttendanceTeacherReportTO> getInchargeTeachersList() {
		return inchargeTeachersList;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}
	
	
}
