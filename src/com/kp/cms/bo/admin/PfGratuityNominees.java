package com.kp.cms.bo.admin;

// Generated Oct 12, 2009 10:03:38 AM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * EmpDependents generated by hbm2java
 */
public class PfGratuityNominees implements java.io.Serializable {

	private int id;
	private Employee employee;
	private String nameAdressNominee;
	private String memberRelationship;
	private Date dobMember;
	private String share;
	private String nameAdressGuardian;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public PfGratuityNominees() {
	}

	public PfGratuityNominees(int id) {
		this.id = id;
	}

	public PfGratuityNominees(int id, Employee employee, String nameAdressNominee,String memberRelationship,
			Date dobMember,String share,String nameAdressGuardian,
			String createdBy,Date createdDate, String modifiedBy, Date lastModifiedDate,Boolean isActive) {
		this.id = id;
		this.employee = employee;
		this.nameAdressNominee = nameAdressNominee;
		this.memberRelationship = memberRelationship;
		this.dobMember = dobMember;
		this.share = share;
		this.nameAdressGuardian = nameAdressGuardian;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Date getDobMember() {
		return dobMember;
	}

	public void setDobMember(Date dobMember) {
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean equals(Object obj) {
		PfGratuityNominees depend=(PfGratuityNominees)obj;
		boolean dob=false;
		boolean dName=false;
		boolean relation=false;
		if(this.nameAdressNominee==null && depend.getNameAdressNominee()==null)
			dName=true;
		else if(this.nameAdressNominee!=null && depend.getNameAdressNominee()!=null){
			if(this.nameAdressNominee.equalsIgnoreCase(depend.getNameAdressNominee()))
				dName=true;
		}
		if(this.memberRelationship==null && depend.getMemberRelationship()==null)
			relation=true;
		else if(this.memberRelationship!=null && depend.getMemberRelationship()!=null){
			if(this.memberRelationship.equalsIgnoreCase(depend.getMemberRelationship()))
				relation=true;
		}
		if(this.dobMember==null && depend.getDobMember()==null)
			dob=true;
		else if(this.dobMember!=null && depend.getDobMember()!=null){
			if(this.dobMember.toString().equalsIgnoreCase(depend.getDobMember().toString()))
				dob=true;
		}
		if(dob && dName && relation && this.employee.getId()==depend.getEmployee().getId()){
			return true;
		}else
			return false;
	}
    
}