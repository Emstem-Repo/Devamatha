package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.bo.admin.EmpAttribute;

public class AppraisalsTO implements Serializable{
	private int detailsId;
	private String attributeName;
	private int attributeValue;
	private Boolean isEmployee;
	private String recomand;
	private int departmentId;
	private String departmentName;
	private String createdBy;
	private Date attributeCreatedDate;
	private Date lastModifiedDate;
	private int empAppraiseId;
	private int attributeId;
	private int appraisalId;
	private String employeeName;
	private Set<EmpAppraisalDetails> empAppraisalDetails;
	private Date appraiseCreatedDate;
	public int getAppraisalId() {
		return appraisalId;
	}
	public void setAppraisalId(int appraisalId) {
		this.appraisalId = appraisalId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public int getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(int attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	public String getRecomand() {
		return recomand;
	}
	public void setRecomand(String recomand) {
		this.recomand = recomand;
	}
	
	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}
	public int getDetailsId() {
		return detailsId;
	}
	
	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	public Boolean getIsEmployee() {
		return isEmployee;
	}
	public void setAttributeCreatedDate(Date attributeCreatedDate) {
		this.attributeCreatedDate = attributeCreatedDate;
	}
	public Date getAttributeCreatedDate() {
		return attributeCreatedDate;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setEmpAppraiseId(int empAppraiseId) {
		this.empAppraiseId = empAppraiseId;
	}
	public int getEmpAppraiseId() {
		return empAppraiseId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public int getAttributeId() {
		return attributeId;
	}
	public void setEmpAppraisalDetails(Set<EmpAppraisalDetails> empAppraisalDetails) {
		this.empAppraisalDetails = empAppraisalDetails;
	}
	public Set<EmpAppraisalDetails> getEmpAppraisalDetails() {
		return empAppraisalDetails;
	}
	public void setAppraiseCreatedDate(Date appraiseCreatedDate) {
		this.appraiseCreatedDate = appraiseCreatedDate;
	}
	public Date getAppraiseCreatedDate() {
		return appraiseCreatedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	

}
