package com.kp.cms.to.applicationform;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Religion;

public class ProfessorPersonalDataTO {
	
	private int id;
	private String name;
	private String maritalStatus;
	private String mobileNo;
	private String religionId;
	private String casteId;
	private String emailId;
	private String departmentId;
	private Integer age;
	private Date dateOfBirth;
	private FormFile editDocument;
	private Boolean isPhoto;
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getReligionId() {
		return religionId;
	}
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	public String getCasteId() {
		return casteId;
	}
	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
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
	
	public FormFile getEditDocument() {
		return editDocument;
	}
	public void setEditDocument(FormFile editDocument) {
		this.editDocument = editDocument;
	}
	public Boolean getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(Boolean isPhoto) {
		this.isPhoto = isPhoto;
	}

}
