package com.kp.cms.bo.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamCourseUtilBO extends ExamGenBO {
	/**
	 * Dec 17, 2009 Created By 9Elements Team
	 */
	private int courseID;
	private String courseName;
	private int programID;
	private ExamProgramUtilBO program;

	

	public String getPTypeProgramCourse() {
		return getProgramTypeName().concat("-").concat(getProgramName())
				.concat("-").concat(courseName);
	}

	public int getProgramID() {
		return programID;
	}

	public void setProgramID(int programID) {
		this.programID = programID;
	}

	public String getProgramCourse() {
		return getProgramName().concat("-").concat(courseName);
	}

	public String getProgramName() {
		return program.getProgramName();
	}

	public String getProgramTypeName() {
		return program.getProgramType().getProgramType();
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ExamProgramUtilBO getProgram() {
		return program;
	}

	public void setProgram(ExamProgramUtilBO program) {
		this.program = program;
	}

}
