package com.kp.cms.bo.applicationform;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Religion;

public class ProfessorPersonalData implements Serializable{
	
	private int id;
	private Date createdDate; 
	private String firstName;

	private String maritalStatus;
	private Religion religionId;
	private Caste casteId;
	private Department departmentId;
	private Integer age;
	private Date dateOfBirth;
	private String emailId;
	private String mobileNo;
	private Set<ProfessorEducationDetailsBO> professerEducationDetails = new HashSet<ProfessorEducationDetailsBO>(0);
	private String diocese;
	private String address;
	private String category;
	private Boolean isPhoto;

	private byte[] document;
	private String fileName;
	





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Religion getReligionId() {
		return religionId;
	}
	public void setReligionId(Religion religionId) {
		this.religionId = religionId;
	}
	public Caste getCasteId() {
		return casteId;
	}
	public void setCasteId(Caste casteId) {
		this.casteId = casteId;
	}
	public Department getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Set<ProfessorEducationDetailsBO> getProfesserEducationDetails() {
		return professerEducationDetails;
	}
	public void setProfesserEducationDetails(
			Set<ProfessorEducationDetailsBO> professerEducationDetails) {
		this.professerEducationDetails = professerEducationDetails;
	}
	

	public String getDiocese() {
		return diocese;
	}
	public void setDiocese(String diocese) {
		this.diocese = diocese;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Boolean getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(Boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public byte[] getDocument() {
		return document;
	}
	public void setDocument(byte[] document) {
		this.document = document;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
