package com.kp.cms.bo.smartcard;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;

public class ScLostCorrection implements java.io.Serializable {
	
	private int id;
	private Student studentId;
	private String stuId;
	private Date dateOfSubmission;
	private String cardReason;
	private Boolean isTextFileRequired;
	private String status;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String newSmartCardNum;
	private String oldSmartCardNum;
	private String remarks;
	private String reasonForRejection;
	private Boolean isEmployee;
	private Employee employeeId;
	
	
	public ScLostCorrection(){
		
	}
	
	public ScLostCorrection(int id, Student studentId, String stuId, Date dateOfSubmission, 
			String cardReason, Boolean isTextFileRequired, String status, 
			String createdBy, String modifiedBy, Date createdDate, Date lastModifiedDate, Boolean isActive, 
			String newSmartCardNum, String oldSmartCardNum, String remarks, String reasonForRejection, 
			Boolean isEmployee, Employee employeeId) {
		
		this.id = id;
		this.studentId = studentId;
		this.stuId = stuId;
		this.dateOfSubmission = dateOfSubmission;
		this.cardReason = cardReason;
		this.isTextFileRequired = isTextFileRequired;
		this.status = status;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.newSmartCardNum = newSmartCardNum;
		this.oldSmartCardNum = oldSmartCardNum;
		this.remarks = remarks;
		this.reasonForRejection = reasonForRejection;
		this.isEmployee = isEmployee;
		this.employeeId = employeeId;
	}

	public String getOldSmartCardNum() {
		return oldSmartCardNum;
	}

	public void setOldSmartCardNum(String oldSmartCardNum) {
		this.oldSmartCardNum = oldSmartCardNum;
	}

	public String getNewSmartCardNum() {
		return newSmartCardNum;
	}

	public void setNewSmartCardNum(String newSmartCardNum) {
		this.newSmartCardNum = newSmartCardNum;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public String getCardReason() {
		return cardReason;
	}

	public void setCardReason(String cardReason) {
		this.cardReason = cardReason;
	}

	public Boolean getIsTextFileRequired() {
		return isTextFileRequired;
	}

	public void setIsTextFileRequired(Boolean isTextFileRequired) {
		this.isTextFileRequired = isTextFileRequired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	public Boolean getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	
	
}
