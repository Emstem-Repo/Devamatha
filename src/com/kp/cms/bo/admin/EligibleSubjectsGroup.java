package com.kp.cms.bo.admin;

// Generated May 18, 2009 3:06:44 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EligibleSubjectsGroup generated by hbm2java
 */
public class EligibleSubjectsGroup implements java.io.Serializable {

	private int id;
	private String name;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Set<EligibilitySubjectGroupSubjects> eligibilitySubjectGroupSubjectses = new HashSet<EligibilitySubjectGroupSubjects>(
			0);

	public EligibleSubjectsGroup() {
	}

	public EligibleSubjectsGroup(int id) {
		this.id = id;
	}

	public EligibleSubjectsGroup(
			int id,
			String name,
			String description,
			String createdBy,
			Date createdDate,
			String modifiedBy,
			Date lastModifiedDate,
			Set<EligibilitySubjectGroupSubjects> eligibilitySubjectGroupSubjectses) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.eligibilitySubjectGroupSubjectses = eligibilitySubjectGroupSubjectses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<EligibilitySubjectGroupSubjects> getEligibilitySubjectGroupSubjectses() {
		return this.eligibilitySubjectGroupSubjectses;
	}

	public void setEligibilitySubjectGroupSubjectses(
			Set<EligibilitySubjectGroupSubjects> eligibilitySubjectGroupSubjectses) {
		this.eligibilitySubjectGroupSubjectses = eligibilitySubjectGroupSubjectses;
	}

}