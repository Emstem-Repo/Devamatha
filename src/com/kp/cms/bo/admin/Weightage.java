package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Weightage generated by hbm2java
 */
public class Weightage implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Program program;
	private Course course;
	private String name;
	private String description;
	private Integer year;
	private Date createdDate;
	private Date lastModifiedDate;
	private BigDecimal educationWeightage;
	private BigDecimal interviewWeightage;
	private BigDecimal prerequisiteWeightage;
	private Set<WeightageDefinition> weightageDefinitions = new HashSet<WeightageDefinition>(
			0);
	private Set<InterviewProgramCourse> interviewProgramCourses = new HashSet<InterviewProgramCourse>(
			0);
	private Set<DocChecklist> docChecklists = new HashSet<DocChecklist>(0);

	public Weightage() {
	}

	public Weightage(int id) {
		this.id = id;
	}

	public Weightage(int id, String createdBy,
			String modifiedBy, Program program, Course course,
			String name, String description, Date createdDate,Integer year,BigDecimal prerequisiteWeightage,
			Date lastModifiedDate, BigDecimal educationWeightage,
			BigDecimal interviewWeightage,
			Set<WeightageDefinition> weightageDefinitions,
			Set<InterviewProgramCourse> interviewProgramCourses,
			Set<DocChecklist> docChecklists) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.program = program;
		this.course = course;
		this.name = name;
		this.year = year;
		this.description = description;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.educationWeightage = educationWeightage;
		this.interviewWeightage = interviewWeightage;
		this.weightageDefinitions = weightageDefinitions;
		this.prerequisiteWeightage = prerequisiteWeightage;
		this.interviewProgramCourses = interviewProgramCourses;
		this.docChecklists = docChecklists;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}
	
	public BigDecimal getEducationWeightage() {
		return this.educationWeightage;
	}

	public void setEducationWeightage(BigDecimal educationWeightage) {
		this.educationWeightage = educationWeightage;
	}

	public BigDecimal getInterviewWeightage() {
		return this.interviewWeightage;
	}

	public void setInterviewWeightage(BigDecimal interviewWeightage) {
		this.interviewWeightage = interviewWeightage;
	}
	
	public BigDecimal getPrerequisiteWeightage() {
		return this.prerequisiteWeightage;
	}

	public void setPrerequisiteWeightage(BigDecimal prerequisiteWeightage) {
		this.prerequisiteWeightage = prerequisiteWeightage;
	}


	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<WeightageDefinition> getWeightageDefinitions() {
		return this.weightageDefinitions;
	}

	public void setWeightageDefinitions(
			Set<WeightageDefinition> weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
	}
	
	public Set<InterviewProgramCourse> getInterviewProgramCourses() {
		return this.interviewProgramCourses;
	}

	public void setInterviewProgramCourses(
			Set<InterviewProgramCourse> interviewProgramCourses) {
		this.interviewProgramCourses = interviewProgramCourses;
	}

	public Set<DocChecklist> getDocChecklists() {
		return this.docChecklists;
	}

	public void setDocChecklists(Set<DocChecklist> docChecklists) {
		this.docChecklists = docChecklists;
	}
	
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}