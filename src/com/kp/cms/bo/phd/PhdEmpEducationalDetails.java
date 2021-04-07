package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.State;

public class PhdEmpEducationalDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private PhdEmployee employee;
	private QualificationLevelBO phdQualificationLevel;
	private String degree;
	private String universityName;
	private State stateId;
	private int yearOfpassing;
	private String percentage;
	private String attempts;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public PhdEmpEducationalDetails(){
		
	}
	/**
	 * @param id
	 * @param course
	 * @param specialization
	 * @param grade
	 * @param yearOfCompletion
	 * @param institute
	 * @param active
	 * @param empQualificationLevel
	 * @param empOnlineResume
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public PhdEmpEducationalDetails(int id,PhdEmployee employee,QualificationLevelBO phdQualificationLevel,String degree,String universityName,State stateId,int yearOfpassing
	,String percentage,String attempts,Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date modifiedDate) {
		super();
		this.id = id;
		this.employee = employee;
		this.phdQualificationLevel = phdQualificationLevel;
		this.degree = degree;
		this.universityName = universityName;
		this.stateId = stateId;
		this.yearOfpassing = yearOfpassing;
		this.percentage = percentage;
		this.attempts = attempts;
		this.percentage = percentage;
		this.percentage = percentage;
		this.isActive = isActive;
		
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PhdEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(PhdEmployee employee) {
		this.employee = employee;
	}
	public QualificationLevelBO getPhdQualificationLevel() {
		return phdQualificationLevel;
	}
	public void setPhdQualificationLevel(QualificationLevelBO phdQualificationLevel) {
		this.phdQualificationLevel = phdQualificationLevel;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public State getStateId() {
		return stateId;
	}
	public void setStateId(State stateId) {
		this.stateId = stateId;
	}
	public int getYearOfpassing() {
		return yearOfpassing;
	}
	public void setYearOfpassing(int yearOfpassing) {
		this.yearOfpassing = yearOfpassing;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
