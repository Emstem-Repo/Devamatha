package com.kp.cms.bo.admin;

// Generated Feb 11, 2009 12:21:20 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Prerequisite generated by hbm2java
 */
public class Prerequisite implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private boolean isActive;
	private Date lastModifiedDate;
	private Set<CoursePrerequisite> coursePrerequisites = new HashSet<CoursePrerequisite>(
			0);
	private Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks = new HashSet<CandidatePrerequisiteMarks>(
			0);
	
	public Prerequisite() {
	}

	public Prerequisite(int id) {
		this.id = id;
	}

	public Prerequisite(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate, Set<CoursePrerequisite> coursePrerequisites,Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.coursePrerequisites = coursePrerequisites;
		this.candidatePrerequisiteMarks = candidatePrerequisiteMarks;
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

	public Set<CoursePrerequisite> getCoursePrerequisites() {
		return this.coursePrerequisites;
	}

	public void setCoursePrerequisites(
			Set<CoursePrerequisite> coursePrerequisites) {
		this.coursePrerequisites = coursePrerequisites;
	}

	public Set<CandidatePrerequisiteMarks> getCandidatePrerequisiteMarks() {
		return candidatePrerequisiteMarks;
	}

	public void setCandidatePrerequisiteMarks(
			Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks) {
		this.candidatePrerequisiteMarks = candidatePrerequisiteMarks;
	}
	

}
