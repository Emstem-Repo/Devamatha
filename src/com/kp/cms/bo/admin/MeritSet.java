package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MeritSet generated by hbm2java
 */
public class MeritSet implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Set<ProgramCourseIntake> programCourseIntakes = new HashSet<ProgramCourseIntake>(
			0);

	public MeritSet() {
	}

	public MeritSet(int id) {
		this.id = id;
	}

	public MeritSet(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate, boolean isActive,
			Set<ProgramCourseIntake> programCourseIntakes) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.programCourseIntakes = programCourseIntakes;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<ProgramCourseIntake> getProgramCourseIntakes() {
		return this.programCourseIntakes;
	}

	public void setProgramCourseIntakes(
			Set<ProgramCourseIntake> programCourseIntakes) {
		this.programCourseIntakes = programCourseIntakes;
	}

}
