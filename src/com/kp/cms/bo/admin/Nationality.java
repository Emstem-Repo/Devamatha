package com.kp.cms.bo.admin;

// Generated Feb 4, 2009 6:08:59 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Nationality generated by hbm2java
 */
public class Nationality implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<PersonalData> personalDatas = new HashSet<PersonalData>(0);
	private Set<WeightageDefinition> weightageDefinitionsForNationalityId = new HashSet<WeightageDefinition>(
			0);
	private Set<EmpOnlineResume> empOnlineResumes = new HashSet<EmpOnlineResume>(
			0);
	private Set<FeeCriteria> feeCriteriasNationality = new HashSet<FeeCriteria>(
			0);	
	
	public Nationality() {
	}

	public Nationality(int id) {
		this.id = id;
	}

	public Nationality(int id, String name, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<PersonalData> personalDatas,Set<WeightageDefinition> weightageDefinitionsForNationalityId,
			Set<EmpOnlineResume> empOnlineResumes, Set<FeeCriteria> feeCriteriasNationality) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.personalDatas = personalDatas;
		this.weightageDefinitionsForNationalityId = weightageDefinitionsForNationalityId;
		this.isActive = isActive;
		this.empOnlineResumes = empOnlineResumes;
		this.feeCriteriasNationality = feeCriteriasNationality;
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

	public Set<PersonalData> getPersonalDatas() {
		return this.personalDatas;
	}

	public void setPersonalDatas(Set<PersonalData> personalDatas) {
		this.personalDatas = personalDatas;
	}
	public Set<WeightageDefinition> getWeightageDefinitionsForNationalityId() {
		return this.weightageDefinitionsForNationalityId;
	}

	public void setWeightageDefinitionsForNationalityId(
			Set<WeightageDefinition> weightageDefinitionsForNationalityId) {
		this.weightageDefinitionsForNationalityId = weightageDefinitionsForNationalityId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<EmpOnlineResume> getEmpOnlineResumes() {
		return empOnlineResumes;
	}

	public void setEmpOnlineResumes(Set<EmpOnlineResume> empOnlineResumes) {
		this.empOnlineResumes = empOnlineResumes;
	}

	public Set<FeeCriteria> getFeeCriteriasNationality() {
		return feeCriteriasNationality;
	}

	public void setFeeCriteriasNationality(Set<FeeCriteria> feeCriteriasNationality) {
		this.feeCriteriasNationality = feeCriteriasNationality;
	}

	
	
}