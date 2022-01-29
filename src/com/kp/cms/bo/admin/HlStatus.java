package com.kp.cms.bo.admin;

// Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HlStatus generated by hbm2java
 */
public class HlStatus implements java.io.Serializable {

	private int id;
	private String name;
	private String statusType;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<HlComplaint> hlComplaints = new HashSet<HlComplaint>(0);
	private Set<HlLeave> hlLeaves = new HashSet<HlLeave>(0);
	private Set<HlApplicationForm> hlApplicationForms = new HashSet<HlApplicationForm>(
			0);
	private Set<HlRoomTransaction> hlRoomTransactions = new HashSet<HlRoomTransaction>(
			0);

	public HlStatus() {
	}

	public HlStatus(int id) {
		this.id = id;
	}

	public HlStatus(int id, String name, String statusType, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Set<HlComplaint> hlComplaints,
			Set<HlLeave> hlLeaves, Set<HlApplicationForm> hlApplicationForms,
			Set<HlRoomTransaction> hlRoomTransactions) {
		this.id = id;
		this.name = name;
		this.statusType = statusType;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.hlComplaints = hlComplaints;
		this.hlLeaves = hlLeaves;
		this.hlApplicationForms = hlApplicationForms;
		this.hlRoomTransactions = hlRoomTransactions;
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

	public String getStatusType() {
		return this.statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
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

	public Set<HlComplaint> getHlComplaints() {
		return this.hlComplaints;
	}

	public void setHlComplaints(Set<HlComplaint> hlComplaints) {
		this.hlComplaints = hlComplaints;
	}

	public Set<HlLeave> getHlLeaves() {
		return this.hlLeaves;
	}

	public void setHlLeaves(Set<HlLeave> hlLeaves) {
		this.hlLeaves = hlLeaves;
	}

	public Set<HlApplicationForm> getHlApplicationForms() {
		return this.hlApplicationForms;
	}

	public void setHlApplicationForms(Set<HlApplicationForm> hlApplicationForms) {
		this.hlApplicationForms = hlApplicationForms;
	}

	public Set<HlRoomTransaction> getHlRoomTransactions() {
		return this.hlRoomTransactions;
	}

	public void setHlRoomTransactions(Set<HlRoomTransaction> hlRoomTransactions) {
		this.hlRoomTransactions = hlRoomTransactions;
	}

}
