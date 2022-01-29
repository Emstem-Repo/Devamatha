package com.kp.cms.to.admin;

import java.util.Date;

public class PfGratuityNomineesTO {
	private String id;
	private String nameAdressNominee;
	private String memberRelationship;
	private String dobMember;
	private String share;
	private String nameAdressGuardian;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameAdressNominee() {
		return nameAdressNominee;
	}
	public void setNameAdressNominee(String nameAdressNominee) {
		this.nameAdressNominee = nameAdressNominee;
	}
	public String getMemberRelationship() {
		return memberRelationship;
	}
	public void setMemberRelationship(String memberRelationship) {
		this.memberRelationship = memberRelationship;
	}
	public String getDobMember() {
		return dobMember;
	}
	public void setDobMember(String dobMember) {
		this.dobMember = dobMember;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getNameAdressGuardian() {
		return nameAdressGuardian;
	}
	public void setNameAdressGuardian(String nameAdressGuardian) {
		this.nameAdressGuardian = nameAdressGuardian;
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
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
