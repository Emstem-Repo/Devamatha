package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.EmpDependents;

public class EmpDependentsTO {
	private String id;
	private String employeeId;
	private String dependantName;
	
	private String dependentRelationship;
	private String dependantDOB;
	
	
	private String child1Name;
	private String child2Name;
	private String child1DateOfBirth;
	private String child2DateOfBirth;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	
	private boolean originalPresent;
	
	private EmpDependents originalDependent;
	private EmpDependents empDependentses;
	
	public EmpDependents getEmpDependentses() {
		return empDependentses;
	}
	public void setEmpDependentses(EmpDependents empDependentses) {
		this.empDependentses = empDependentses;
	}
	public String getDependantDOB() {
		return dependantDOB;
	}
	public void setDependantDOB(String dependantDOB) {
		this.dependantDOB = dependantDOB;
	}
	
	
	public String getDependantName() {
		return dependantName;
	}
	public void setDependantName(String dependantName) {
		this.dependantName = dependantName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
	
	public String getDependentRelationship() {
		return dependentRelationship;
	}
	public void setDependentRelationship(String dependentRelationship) {
		this.dependentRelationship = dependentRelationship;
	}
	public String getChild1Name() {
		return child1Name;
	}
	public void setChild1Name(String child1Name) {
		this.child1Name = child1Name;
	}
	public String getChild2Name() {
		return child2Name;
	}
	public void setChild2Name(String child2Name) {
		this.child2Name = child2Name;
	}
	public String getChild1DateOfBirth() {
		return child1DateOfBirth;
	}
	public void setChild1DateOfBirth(String child1DateOfBirth) {
		this.child1DateOfBirth = child1DateOfBirth;
	}
	public String getChild2DateOfBirth() {
		return child2DateOfBirth;
	}
	public void setChild2DateOfBirth(String child2DateOfBirth) {
		this.child2DateOfBirth = child2DateOfBirth;
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
	public boolean isOriginalPresent() {
		return originalPresent;
	}
	public void setOriginalPresent(boolean originalPresent) {
		this.originalPresent = originalPresent;
	}
	public EmpDependents getOriginalDependent() {
		return originalDependent;
	}
	public void setOriginalDependent(EmpDependents originalDependent) {
		this.originalDependent = originalDependent;
	}
	
	
}
