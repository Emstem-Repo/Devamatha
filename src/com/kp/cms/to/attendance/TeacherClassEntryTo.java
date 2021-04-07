package com.kp.cms.to.attendance;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeacherClassEntryTo implements Serializable {
	private Integer id;
	private Integer academicYear;
	private String year;
	private String teacherName;
	private String className;
	private String subjectName;
	private String numericCode;
	private int teacherId;
	private String departmentName;
	private String deptTeacherName;
	private String checked;
	private String tempChecked;
	private String teacherType;
		
	public TeacherClassEntryTo() {
		super();
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public String getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	
	public String getTeacherType() {
		return teacherType;
	}
	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDeptTeacherName() {
		return deptTeacherName;
	}

	public void setDeptTeacherName(String deptTeacherName) {
		this.deptTeacherName = deptTeacherName;
	}

	
	
	
	

}
