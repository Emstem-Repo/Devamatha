package com.kp.cms.bo.supportrequest;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Department;

public class CategoryBo implements Serializable{
	private int id;
	private String name;
	private Department departmentId;
	private String categoryFor;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String email;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public Department getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}
	public String getCategoryFor() {
		return categoryFor;
	}
	public void setCategoryFor(String categoryFor) {
		this.categoryFor = categoryFor;
	}
	public CategoryBo(){
		
	}
	public CategoryBo(int id, String name,Department departmentId,String categoryFor,String createdBy,Date createdDate,String modifiedBy,Date lastModifiedDate,Boolean isActive,String email){
		this.id=id;
		this.name=name;
		this.departmentId=departmentId;
		this.categoryFor=categoryFor;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.email=email;
	}
}
