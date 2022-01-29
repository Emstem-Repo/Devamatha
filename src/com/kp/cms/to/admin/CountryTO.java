package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountryTO implements Serializable{
      
	private int id;
	private String name;
	private String isActive;
	private String created;
	private String modified;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private Set states = new HashSet(0);
	private List<StateTO> stateList;
	private String cDate;
	private String lDate;

	
	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set getStates() {
		return this.states;
	}

	public void setStates(Set states) {
		this.states = states;
	}

	public List<StateTO> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateTO> stateList) {
		this.stateList = stateList;
	}
}
