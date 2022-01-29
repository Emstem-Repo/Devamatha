package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.supportrequest.CategoryBo;

public class StudentSupportRequestBo implements Serializable{
	private int id;
	private CategoryBo categoryId;
	private Student studentId;
	private String description;
	private String status;
	private Date dateOfSubmission;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Users userId;
	private String remarks;
	
	public Users getUserId() {
		return userId;
	}
	public void setUserId(Users userId) {
		this.userId = userId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CategoryBo getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(CategoryBo categoryId) {
		this.categoryId = categoryId;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}
	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
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
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public StudentSupportRequestBo(){
		
	}
	public StudentSupportRequestBo(int id,CategoryBo categoryId,String status,Date dateOfSubmission,Student studentId,String description,
			Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date lastModifiedDate,Users userId,String remarks){
		this.id=id;
		this.categoryId=categoryId;
		this.dateOfSubmission=dateOfSubmission;
		this.description=description;
		this.status=status;
		this.studentId=studentId;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.userId=userId;
		this.remarks=remarks;
	}
}
