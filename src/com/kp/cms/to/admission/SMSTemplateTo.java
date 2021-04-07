package com.kp.cms.to.admission;

import java.io.Serializable;

public class SMSTemplateTo implements Serializable{


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
	

}
