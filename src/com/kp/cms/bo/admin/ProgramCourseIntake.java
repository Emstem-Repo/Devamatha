package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * ProgramCourseIntake generated by hbm2java
 */
public class ProgramCourseIntake implements java.io.Serializable {

	private int id;
	private ProgramType programType;
	private String createdBy;;
	private String modifiedBy;
	private Category category;
	private Program program;
	private MeritSet meritSet;
	private Course course;
	private Integer maxIntake;
	private Date createdDate;
	private Date lastModifiedDate;

	public ProgramCourseIntake() {
	}

	public ProgramCourseIntake(int id) {
		this.id = id;
	}

	public ProgramCourseIntake(int id, ProgramType programType,
			String createdBy, String modifiedBy,
			Category category, Program program, MeritSet meritSet,
			Course course, Integer maxIntake, Date createdDate,
			Date lastModifiedDate) {
		this.id = id;
		this.programType = programType;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.category = category;
		this.program = program;
		this.meritSet = meritSet;
		this.course = course;
		this.maxIntake = maxIntake;
		this.createdDate = createdDate;
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

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public MeritSet getMeritSet() {
		return this.meritSet;
	}

	public void setMeritSet(MeritSet meritSet) {
		this.meritSet = meritSet;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getMaxIntake() {
		return this.maxIntake;
	}

	public void setMaxIntake(Integer maxIntake) {
		this.maxIntake = maxIntake;
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

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}