package com.kp.cms.bo.admin;

// Generated Jul 22, 2009 4:54:34 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HlHostel generated by hbm2java
 */
public class HlHostel implements java.io.Serializable {

	private int id;
	private State state;
	private Country country;
	private String name;
	private String gender;
	private Integer noOfFloors;
	private byte[] termsConditions;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private Integer zipCode;
	private String phone;
	private String faxNo;
	private String email;
	private String primaryContactName;
	private String primaryContactDesignation;
	private String primaryContactPhone;
	private String primaryContactMobile;
	private String primaryContactEmail;
	private String secondaryContactName;
	private String secondaryContactDesignation;
	private String secondaryContactPhone;
	private String secondaryContactMobile;
	private String secondaryContactEmail;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private Boolean isActive;
	private String stateOthers;
	private String contentType;
	private String fileName;
	private Set<HlAttendance> hlAttendances = new HashSet<HlAttendance>(0);
	private Set<HlDisciplinaryDetails> hlDisciplinaryDetailses = new HashSet<HlDisciplinaryDetails>(
			0);
	private Set<HlVisitorInfo> hlVisitorInfos = new HashSet<HlVisitorInfo>(0);
	private Set<HlApplicationForm> hlApplicationFormsForHlApprovedHostelId = new HashSet<HlApplicationForm>(
			0);
	private Set<HlRoomType> hlRoomTypes = new HashSet<HlRoomType>(0);
	private Set<HlComplaint> hlComplaints = new HashSet<HlComplaint>(0);
	private Set<HlGroup> hlGroups = new HashSet<HlGroup>(0);
	private Set<HlDamage> hlDamages = new HashSet<HlDamage>(0);
	private Set<HlRoom> hlRooms = new HashSet<HlRoom>(0);
	private Set<HlApplicationForm> hlApplicationFormsForHlAppliedHostelId = new HashSet<HlApplicationForm>(
			0);
	private Set<HlLeave> hlLeaves = new HashSet<HlLeave>(0);
	private Set<HlFloorRoom> hlFloorRooms = new HashSet<HlFloorRoom>(0);

	public HlHostel() {
	}

	public HlHostel(int id) {
		this.id = id;
	}

	public HlHostel(int id, State state, Country country, String name,
			String gender, Integer noOfFloors,
			byte[] termsConditions, String addressLine1, String addressLine2,
			String city, Integer zipCode, String phone, String faxNo,
			String email, String primaryContactName,
			String primaryContactDesignation, String primaryContactPhone,
			String primaryContactMobile, String primaryContactEmail,
			String secondaryContactName, String secondaryContactDesignation,
			String secondaryContactPhone, String secondaryContactMobile,
			String secondaryContactEmail, String createdBy, Date createdDate,
			String modifiedBy, String lastModifiedDate, Boolean isActive,String stateOthers,
			String fileName, String contentType,
			Set<HlAttendance> hlAttendances,
			Set<HlDisciplinaryDetails> hlDisciplinaryDetailses,
			Set<HlVisitorInfo> hlVisitorInfos,
			Set<HlApplicationForm> hlApplicationFormsForHlApprovedHostelId,
			Set<HlRoomType> hlRoomTypes, Set<HlComplaint> hlComplaints,
			Set<HlGroup> hlGroups, Set<HlDamage> hlDamages,
			Set<HlRoom> hlRooms,
			Set<HlApplicationForm> hlApplicationFormsForHlAppliedHostelId,
			Set<HlLeave> hlLeaves, Set<HlFloorRoom> hlFloorRooms) {
		this.id = id;
		this.state = state;
		this.country = country;
		this.name = name;
		this.gender = gender;
		this.noOfFloors = noOfFloors;
		this.termsConditions = termsConditions;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.zipCode = zipCode;
		this.phone = phone;
		this.faxNo = faxNo;
		this.email = email;
		this.primaryContactName = primaryContactName;
		this.primaryContactDesignation = primaryContactDesignation;
		this.primaryContactPhone = primaryContactPhone;
		this.primaryContactMobile = primaryContactMobile;
		this.primaryContactEmail = primaryContactEmail;
		this.secondaryContactName = secondaryContactName;
		this.secondaryContactDesignation = secondaryContactDesignation;
		this.secondaryContactPhone = secondaryContactPhone;
		this.secondaryContactMobile = secondaryContactMobile;
		this.secondaryContactEmail = secondaryContactEmail;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.hlAttendances = hlAttendances;
		this.hlDisciplinaryDetailses = hlDisciplinaryDetailses;
		this.hlVisitorInfos = hlVisitorInfos;
		this.hlApplicationFormsForHlApprovedHostelId = hlApplicationFormsForHlApprovedHostelId;
		this.hlRoomTypes = hlRoomTypes;
		this.hlComplaints = hlComplaints;
		this.hlGroups = hlGroups;
		this.hlDamages = hlDamages;
		this.hlRooms = hlRooms;
		this.hlApplicationFormsForHlAppliedHostelId = hlApplicationFormsForHlAppliedHostelId;
		this.hlLeaves = hlLeaves;
		this.hlFloorRooms = hlFloorRooms;
		this.stateOthers = stateOthers;
		this.fileName = fileName;
		this.contentType = contentType;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getNoOfFloors() {
		return this.noOfFloors;
	}

	public void setNoOfFloors(Integer noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	public byte[] getTermsConditions() {
		return this.termsConditions;
	}

	public void setTermsConditions(byte[] termsConditions) {
		this.termsConditions = termsConditions;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrimaryContactName() {
		return this.primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactDesignation() {
		return this.primaryContactDesignation;
	}

	public void setPrimaryContactDesignation(String primaryContactDesignation) {
		this.primaryContactDesignation = primaryContactDesignation;
	}

	public String getPrimaryContactPhone() {
		return this.primaryContactPhone;
	}

	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}

	public String getPrimaryContactMobile() {
		return this.primaryContactMobile;
	}

	public void setPrimaryContactMobile(String primaryContactMobile) {
		this.primaryContactMobile = primaryContactMobile;
	}

	public String getPrimaryContactEmail() {
		return this.primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getSecondaryContactName() {
		return this.secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactDesignation() {
		return this.secondaryContactDesignation;
	}

	public void setSecondaryContactDesignation(
			String secondaryContactDesignation) {
		this.secondaryContactDesignation = secondaryContactDesignation;
	}

	public String getSecondaryContactPhone() {
		return this.secondaryContactPhone;
	}

	public void setSecondaryContactPhone(String secondaryContactPhone) {
		this.secondaryContactPhone = secondaryContactPhone;
	}

	public String getSecondaryContactMobile() {
		return this.secondaryContactMobile;
	}

	public void setSecondaryContactMobile(String secondaryContactMobile) {
		this.secondaryContactMobile = secondaryContactMobile;
	}

	public String getSecondaryContactEmail() {
		return this.secondaryContactEmail;
	}

	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = secondaryContactEmail;
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

	public String getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<HlAttendance> getHlAttendances() {
		return this.hlAttendances;
	}

	public void setHlAttendances(Set<HlAttendance> hlAttendances) {
		this.hlAttendances = hlAttendances;
	}

	public Set<HlDisciplinaryDetails> getHlDisciplinaryDetailses() {
		return this.hlDisciplinaryDetailses;
	}

	public void setHlDisciplinaryDetailses(
			Set<HlDisciplinaryDetails> hlDisciplinaryDetailses) {
		this.hlDisciplinaryDetailses = hlDisciplinaryDetailses;
	}

	public Set<HlVisitorInfo> getHlVisitorInfos() {
		return this.hlVisitorInfos;
	}

	public void setHlVisitorInfos(Set<HlVisitorInfo> hlVisitorInfos) {
		this.hlVisitorInfos = hlVisitorInfos;
	}

	public Set<HlApplicationForm> getHlApplicationFormsForHlApprovedHostelId() {
		return this.hlApplicationFormsForHlApprovedHostelId;
	}

	public void setHlApplicationFormsForHlApprovedHostelId(
			Set<HlApplicationForm> hlApplicationFormsForHlApprovedHostelId) {
		this.hlApplicationFormsForHlApprovedHostelId = hlApplicationFormsForHlApprovedHostelId;
	}

	public Set<HlRoomType> getHlRoomTypes() {
		return this.hlRoomTypes;
	}

	public void setHlRoomTypes(Set<HlRoomType> hlRoomTypes) {
		this.hlRoomTypes = hlRoomTypes;
	}

	public Set<HlComplaint> getHlComplaints() {
		return this.hlComplaints;
	}

	public void setHlComplaints(Set<HlComplaint> hlComplaints) {
		this.hlComplaints = hlComplaints;
	}

	public Set<HlGroup> getHlGroups() {
		return this.hlGroups;
	}

	public void setHlGroups(Set<HlGroup> hlGroups) {
		this.hlGroups = hlGroups;
	}

	public Set<HlDamage> getHlDamages() {
		return this.hlDamages;
	}

	public void setHlDamages(Set<HlDamage> hlDamages) {
		this.hlDamages = hlDamages;
	}

	public Set<HlRoom> getHlRooms() {
		return this.hlRooms;
	}

	public void setHlRooms(Set<HlRoom> hlRooms) {
		this.hlRooms = hlRooms;
	}

	public Set<HlApplicationForm> getHlApplicationFormsForHlAppliedHostelId() {
		return this.hlApplicationFormsForHlAppliedHostelId;
	}

	public void setHlApplicationFormsForHlAppliedHostelId(
			Set<HlApplicationForm> hlApplicationFormsForHlAppliedHostelId) {
		this.hlApplicationFormsForHlAppliedHostelId = hlApplicationFormsForHlAppliedHostelId;
	}

	public Set<HlLeave> getHlLeaves() {
		return this.hlLeaves;
	}

	public void setHlLeaves(Set<HlLeave> hlLeaves) {
		this.hlLeaves = hlLeaves;
	}

	public Set<HlFloorRoom> getHlFloorRooms() {
		return this.hlFloorRooms;
	}

	public void setHlFloorRooms(Set<HlFloorRoom> hlFloorRooms) {
		this.hlFloorRooms = hlFloorRooms;
	}

	public String getStateOthers() {
		return stateOthers;
	}

	public void setStateOthers(String stateOthers) {
		this.stateOthers = stateOthers;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}