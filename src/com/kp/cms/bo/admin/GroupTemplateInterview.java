package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class GroupTemplateInterview implements Serializable {
	
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
	private InterviewProgramCourse interviewProgramCourse;
	private InterviewSubRounds interviewSubRounds;
	private int year;
	
	public GroupTemplateInterview() {
		
	}
	
	public GroupTemplateInterview(int id, ProgramType programType,
			Program program, Course course, String templateName,
			String templateDescription, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate,
			InterviewProgramCourse interviewProgramCourse,InterviewSubRounds interviewSubRounds,int year) {
		super();
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
		this.interviewProgramCourse = interviewProgramCourse;
		this.interviewSubRounds=interviewSubRounds;
		this.year=year;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProgramType getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public InterviewProgramCourse getInterviewProgramCourse() {
		return interviewProgramCourse;
	}
	public void setInterviewProgramCourse(
			InterviewProgramCourse interviewProgramCourse) {
		this.interviewProgramCourse = interviewProgramCourse;
	}

	public InterviewSubRounds getInterviewSubRounds() {
		return interviewSubRounds;
	}

	public void setInterviewSubRounds(InterviewSubRounds interviewSubRounds) {
		this.interviewSubRounds = interviewSubRounds;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
