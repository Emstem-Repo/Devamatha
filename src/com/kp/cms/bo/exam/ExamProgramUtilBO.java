package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Set;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamProgramUtilBO implements Serializable{

	private ExamProgramTypeUtilBO programType;
	private String programName;
	private int programID;
	protected boolean isActive;
	
	private Set<ExamCourseUtilBO> courseUtilBOSet;
	private Integer academicYear;
	public Set<ExamCourseUtilBO> getCourseUtilBOSet() {
		return courseUtilBOSet;
	}

	public void setCourseUtilBOSet(Set<ExamCourseUtilBO> courseUtilBOSet) {
		this.courseUtilBOSet = courseUtilBOSet;
	}

	public ExamProgramTypeUtilBO getProgramType() {
		return programType;
	}

	public void setProgramType(ExamProgramTypeUtilBO programType) {
		this.programType = programType;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getProgramID() {
		return programID;
	}

	public void setProgramID(int programID) {
		this.programID = programID;
	}
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}
}
