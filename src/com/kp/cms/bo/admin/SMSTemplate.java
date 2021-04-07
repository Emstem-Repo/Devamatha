package com.kp.cms.bo.admin;

import java.util.Date;

public class SMSTemplate implements java.io.Serializable {


	private int id;
	private ProgramType programType;
	private Program program;
	private Course course;
	private String templateName;
	private String templateDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public SMSTemplate() {
	}

	public SMSTemplate(int id, ProgramType programType) {
		this.id = id;
		this.programType = programType;
	}

	public SMSTemplate(int id, ProgramType programType, Program program,
			Course course, String templateName, String templateDescription,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate) {
		this.id = id;
		this.programType = programType;
		this.program = program;
		this.course = course;
		this.templateName = templateName;
		this.templateDescription = templateDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProgramType getProgramType() {
		return this.programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateDescription() {
		return this.templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}