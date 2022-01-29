package com.kp.cms.bo.admin;

// Generated May 18, 2009 3:06:44 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * AdmissionNumber generated by hbm2java
 */
public class AdmissionNumber implements java.io.Serializable {

	private int id;
	private ProgramType programType;
	private Program program;
	private Course course;
	private String admissionNumberFrom;
	private String admissionNumberCurrent;
	private String admissionNumberTo;
	private Date year;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public AdmissionNumber() {
	}

	public AdmissionNumber(int id) {
		this.id = id;
	}

	public AdmissionNumber(int id, ProgramType programType, Program program,
			Course course, String admissionNumberFrom,
			String admissionNumberCurrent, String admissionNumberTo, Date year,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.programType = programType;
		this.program = program;
		this.course = course;
		this.admissionNumberFrom = admissionNumberFrom;
		this.admissionNumberCurrent = admissionNumberCurrent;
		this.admissionNumberTo = admissionNumberTo;
		this.year = year;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
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

	public String getAdmissionNumberFrom() {
		return this.admissionNumberFrom;
	}

	public void setAdmissionNumberFrom(String admissionNumberFrom) {
		this.admissionNumberFrom = admissionNumberFrom;
	}

	public String getAdmissionNumberCurrent() {
		return this.admissionNumberCurrent;
	}

	public void setAdmissionNumberCurrent(String admissionNumberCurrent) {
		this.admissionNumberCurrent = admissionNumberCurrent;
	}

	public String getAdmissionNumberTo() {
		return this.admissionNumberTo;
	}

	public void setAdmissionNumberTo(String admissionNumberTo) {
		this.admissionNumberTo = admissionNumberTo;
	}

	public Date getYear() {
		return this.year;
	}

	public void setYear(Date year) {
		this.year = year;
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

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
