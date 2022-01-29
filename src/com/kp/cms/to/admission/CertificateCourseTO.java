package com.kp.cms.to.admission;

import java.io.Serializable;

import com.kp.cms.bo.admin.Users;

public class CertificateCourseTO implements Serializable,Comparable<CertificateCourseTO> {
	private String courseName;
	private String programTypeName;
	private String programName;
	private String isOptional;
	private int id;
	private String courseCheck;
	private String stream;
	private String subjectName;
	private String maxIntake;
	private Users users;
	private String startTime;
	private String endTime;
	private String venue;
	private String teacherName;
	private int year;
	private String courseNameWithSemType;
	private String extracurricular;
	private String description;
	private String groupMaxIntake;
	private boolean display=false;
	private boolean tempChecked;
	private boolean checkBoxDisplay;
	private String semType;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getIsOptional() {
		return isOptional;
	}
	public void setIsOptional(String isOptional) {
		this.isOptional = isOptional;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseCheck() {
		return courseCheck;
	}
	public void setCourseCheck(String courseCheck) {
		this.courseCheck = courseCheck;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCourseNameWithSemType() {
		return courseNameWithSemType;
	}
	public void setCourseNameWithSemType(String courseNameWithSemType) {
		this.courseNameWithSemType = courseNameWithSemType;
	}
	public String getExtracurricular() {
		return extracurricular;
	}
	public void setExtracurricular(String extracurricular) {
		this.extracurricular = extracurricular;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroupMaxIntake() {
		return groupMaxIntake;
	}
	public void setGroupMaxIntake(String groupMaxIntake) {
		this.groupMaxIntake = groupMaxIntake;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public boolean isCheckBoxDisplay() {
		return checkBoxDisplay;
	}
	public void setCheckBoxDisplay(boolean checkBoxDisplay) {
		this.checkBoxDisplay = checkBoxDisplay;
	}
	
	
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	@Override
	public int compareTo(CertificateCourseTO to1) {
		if (to1 != null) {
			if(to1.getCourseName() == null) {
				to1.setCourseName("");
			}
			if(this.getCourseName() == null) {
				this.setCourseName("");
			}
			return this.getCourseName().toUpperCase().compareTo(to1.getCourseName().toUpperCase());
		}
		return 0;
	}
	
}