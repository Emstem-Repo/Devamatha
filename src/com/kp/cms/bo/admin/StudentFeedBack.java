package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class StudentFeedBack implements Serializable {
	private int id;
	private Student student;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String feedback;
	private Boolean isActive;
	private Date date;
	private String email;
	private String mobileNo;
	
	
	public StudentFeedBack() {
	}
	
	
	public StudentFeedBack(int id) {
		this.id = id;
	}

	public StudentFeedBack(int id, Student student, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			String feedback, Boolean isActive, Date date,String email,String mobileNo) {
		this.id = id;
		this.student = student;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.feedback = feedback;
		this.isActive = isActive;
		this.date = date;
		this.email = email;
		this.mobileNo =mobileNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
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
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmail() {
		return email;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	
}
