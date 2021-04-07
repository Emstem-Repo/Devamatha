package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MotherTongue generated by hbm2java
 */
public class MotherTongue implements java.io.Serializable {

	private int id;
	private String name;
	private boolean isActive;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Set<PersonalData> personalDatas = new HashSet<PersonalData>(0);
	

	public MotherTongue() {
	}

	public MotherTongue(int id) {
		this.id = id;
	}

	public MotherTongue(int id, String name, boolean isActive,
			Set<PersonalData> personalDatas) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.personalDatas = personalDatas;
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

	
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<PersonalData> getPersonalDatas() {
		return this.personalDatas;
	}

	public void setPersonalDatas(Set<PersonalData> personalDatas) {
		this.personalDatas = personalDatas;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}