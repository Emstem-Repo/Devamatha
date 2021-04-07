package com.kp.cms.to.admin;

import java.io.Serializable;

public class InterviewTemplateTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String programTypeId;
	private String programId;
	private String courseId;
	private String programTypeName;
	private String programName;
	private String courseName;
	private String templateName;
	private String templateDescription;
	private String interviewId;
	private String interviewName;
	private String interviewSubRoundId;
	private String interviewSubRoundName;
	private String year;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}
	public String getInterviewName() {
		return interviewName;
	}
	public void setInterviewName(String interviewName) {
		this.interviewName = interviewName;
	}
	public String getInterviewSubRoundId() {
		return interviewSubRoundId;
	}
	public void setInterviewSubRoundId(String interviewSubRoundId) {
		this.interviewSubRoundId = interviewSubRoundId;
	}
	public String getInterviewSubRoundName() {
		return interviewSubRoundName;
	}
	public void setInterviewSubRoundName(String interviewSubRoundName) {
		this.interviewSubRoundName = interviewSubRoundName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
