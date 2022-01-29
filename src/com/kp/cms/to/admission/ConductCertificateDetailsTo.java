package com.kp.cms.to.admission;

import java.util.List;

public class ConductCertificateDetailsTo {
	
	private String courseName;
	private String dateOfApplication;
	private String classOfLeaving;
	private String characterId;
	private String classId;
	private String academicYear;
	private String academicDuration;
	private boolean isAided;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public String getClassOfLeaving() {
		return classOfLeaving;
	}
	public void setClassOfLeaving(String classOfLeaving) {
		this.classOfLeaving = classOfLeaving;
	}
	public String getCharacterId() {
		return characterId;
	}
	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAcademicDuration() {
		return academicDuration;
	}
	public void setAcademicDuration(String academicDuration) {
		this.academicDuration = academicDuration;
	}
	public boolean isAided() {
		return isAided;
	}
	public void setAided(boolean isAided) {
		this.isAided = isAided;
	}

}
