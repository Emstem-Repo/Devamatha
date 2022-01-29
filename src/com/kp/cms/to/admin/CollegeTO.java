package com.kp.cms.to.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



public class CollegeTO {
	private int id;
	private UniversityTO universityTO;
	private String name;
	private Integer addressId;
	private byte[] logo;
	private Set weightageDefinitions = new HashSet(0);
	
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private String cDate;
	private String lDate;
	
	public CollegeTO(){
		
	}
	public CollegeTO(int id, UniversityTO universityTO, String name,
			Integer addressId, byte[] logo, Set weightageDefinitions,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, String isActive, String cDate, String lDate) {
		super();
		this.id = id;
		this.universityTO = universityTO;
		this.name = name;
		this.addressId = addressId;
		this.logo = (byte[])logo.clone();
		this.weightageDefinitions = weightageDefinitions;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
		this.cDate = cDate;
		this.lDate = lDate;
	}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UniversityTO getUniversityTO() {
		return universityTO;
	}
	public void setUniversityTO(UniversityTO universityTO) {
		this.universityTO = universityTO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public byte[] getLogo() {
		return (byte[])logo.clone();
	}
	public void setLogo(byte[] logo) {
		this.logo = (byte[])logo.clone();
	}
	public Set getWeightageDefinitions() {
		return weightageDefinitions;
	}
	public void setWeightageDefinitions(Set weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
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
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}