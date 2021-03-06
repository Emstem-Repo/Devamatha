package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * SelectionProcess generated by hbm2java
 */
public class SelectionProcess implements java.io.Serializable {

	private int id;
	private ProgramType programType;
	private String createdBy;;
	private String modifiedBy;
	private Program program;
	private Course course;
	private String meritSet;
	private Integer maximumIntake;
	private Date createdDate;
	private Date lastModifiedDate;

	public SelectionProcess() {
	}

	public SelectionProcess(int id) {
		this.id = id;
	}

	public SelectionProcess(int id, ProgramType programType,
			String createdBy, String modifiedBy,
			Program program, Course course, String meritSet,
			Integer maximumIntake, Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.programType = programType;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.program = program;
		this.course = course;
		this.meritSet = meritSet;
		this.maximumIntake = maximumIntake;
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

	public String getMeritSet() {
		return this.meritSet;
	}

	public void setMeritSet(String meritSet) {
		this.meritSet = meritSet;
	}

	public Integer getMaximumIntake() {
		return this.maximumIntake;
	}

	public void setMaximumIntake(Integer maximumIntake) {
		this.maximumIntake = maximumIntake;
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
