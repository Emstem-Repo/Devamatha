package com.kp.cms.bo.alumini;

import java.util.Date;
import java.util.Set;

public class AlumniRegistrationDetails {

	private int id;
	private String firstName;
	private String mobileNumber;
	private String email;
	private String currentStatus;
	private String comapnyName;
	private String designation;
	private String otherJobInfo;
	private String country;
	private String city;
	private String highestQualification;
	private Date createdDate;
	private Set<AlumniEducationDetails> alumniEducationDetails;
	public AlumniRegistrationDetails() {
		super();
	}
	public AlumniRegistrationDetails(int id, String firstName,
			String mobileNumber, String email, String currentStatus,
			String comapnyName, String designation, String otherJobInfo,
			String currentLocation, String country, String city,
			String highestQualification, Date createdDate,
			Set<AlumniEducationDetails> alumniEducationDetails) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.currentStatus = currentStatus;
		this.comapnyName = comapnyName;
		this.designation = designation;
		this.otherJobInfo = otherJobInfo;
		this.country = country;
		this.city = city;
		this.highestQualification = highestQualification;
		this.createdDate = createdDate;
		this.alumniEducationDetails = alumniEducationDetails;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getComapnyName() {
		return comapnyName;
	}
	public void setComapnyName(String comapnyName) {
		this.comapnyName = comapnyName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getOtherJobInfo() {
		return otherJobInfo;
	}
	public void setOtherJobInfo(String otherJobInfo) {
		this.otherJobInfo = otherJobInfo;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Set<AlumniEducationDetails> getAlumniEducationDetails() {
		return alumniEducationDetails;
	}
	public void setAlumniEducationDetails(
			Set<AlumniEducationDetails> alumniEducationDetails) {
		this.alumniEducationDetails = alumniEducationDetails;
	}
}
