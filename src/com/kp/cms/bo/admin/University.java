package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * University generated by hbm2java
 */
public class University implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private DocType docType;
	
	private Set<College> colleges = new HashSet<College>(0);
	private Set<WeightageDefinition> weightageDefinitions = new HashSet<WeightageDefinition>(
			0);
	private Set<EdnQualification> ednQualifications = new HashSet<EdnQualification>(
			0);
	private Set<FeeCriteria> feeCriteriasUni = new HashSet<FeeCriteria>(
			0);
	public University() {
	}

	public University(int id) {
		this.id = id;
	}

	public University(int id, String createdBy,
			String modifiedBy, String name, Date createdDate,
			Date lastModifiedDate, Set<College> colleges,
			Set<WeightageDefinition> weightageDefinitions,
			Set<EdnQualification> ednQualifications, DocType docType, Set<FeeCriteria> feeCriteriasUni) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.colleges = colleges;
		this.weightageDefinitions = weightageDefinitions;
		this.ednQualifications = ednQualifications;
		this.docType = docType;
		this.feeCriteriasUni = feeCriteriasUni;
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

	public Set<College> getColleges() {
		return this.colleges;
	}

	public void setColleges(Set<College> colleges) {
		this.colleges = colleges;
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
	public DocType getDocType() {
		return this.docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public Set<FeeCriteria> getFeeCriteriasUni() {
		return feeCriteriasUni;
	}

	public void setFeeCriteriasUni(Set<FeeCriteria> feeCriteriasUni) {
		this.feeCriteriasUni = feeCriteriasUni;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}