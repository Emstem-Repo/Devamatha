package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * College generated by hbm2java
 */
public class College implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private University university;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<WeightageDefinition> weightageDefinitions = new HashSet<WeightageDefinition>(
			0);
	private Set<EdnQualification> ednQualifications = new HashSet<EdnQualification>(
			0);
	private Set<FeeCriteria> feeCriterias = new HashSet<FeeCriteria>(
			0);
	public College() {
	}

	public College(int id) {
		this.id = id;
	}

	public College(int id, String createdBy,
			String modifiedBy, University university, String name,
			Date createdDate, Date lastModifiedDate,
			Set<WeightageDefinition> weightageDefinitions,
			Set<EdnQualification> ednQualifications, Set<FeeCriteria> feeCriterias) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.university = university;
		this.name = name;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.weightageDefinitions = weightageDefinitions;
		this.ednQualifications = ednQualifications;
		this.feeCriterias = feeCriterias;
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

	public University getUniversity() {
		return this.university;
	}

	public void setUniversity(University university) {
		this.university = university;
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

	public Set<WeightageDefinition> getWeightageDefinitions() {
		return this.weightageDefinitions;
	}

	public void setWeightageDefinitions(
			Set<WeightageDefinition> weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
	}
	
	public Set<EdnQualification> getEdnQualifications() {
		return ednQualifications;
	}

	public void setEdnQualifications(Set<EdnQualification> ednQualifications) {
		this.ednQualifications = ednQualifications;
	}
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<FeeCriteria> getFeeCriterias() {
		return feeCriterias;
	}

	public void setFeeCriterias(Set<FeeCriteria> feeCriterias) {
		this.feeCriterias = feeCriterias;
	}
	
}
