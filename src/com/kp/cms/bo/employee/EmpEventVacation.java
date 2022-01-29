package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;

public class EmpEventVacation implements Serializable{
	private int id;
	private String type;
	private Set<EmpEventVacationDepartment> empEventVacationDepartment;
	private Date toDate;
	private Date fromDate;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean teachingStaff;
	private EmployeeStreamBO stream;
	/**
	 * 
	 */
	public EmpEventVacation() {
		
	}
	

	/**
	 * @param id
	 * @param type
	 * @param empEventVacationDepartment
	 * @param toDate
	 * @param fromDate
	 * @param description
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param lastModifiedDate
	 * @param isActive
	 */
	public EmpEventVacation(int id, String type, Set<EmpEventVacationDepartment> empEventVacationDepartment,
			Date toDate, Date fromDate, String description, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Boolean teachingStaff, EmployeeStreamBO stream) {
		this.id = id;
		this.type = type;
		this.empEventVacationDepartment=empEventVacationDepartment;
		this.toDate = toDate;
		this.fromDate = fromDate;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.teachingStaff = teachingStaff;
		this.stream = stream;
	}
	
	
	public int getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public Set<EmpEventVacationDepartment> getEmpEventVacationDepartment() {
		return empEventVacationDepartment;
	}

	public Date getToDate() {
		return toDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public String getDescription() {
		return description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setEmpEventVacationDepartment(
			Set<EmpEventVacationDepartment> empEventVacationDepartment) {
		this.empEventVacationDepartment = empEventVacationDepartment;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public Boolean getTeachingStaff() {
		return teachingStaff;
	}


	public void setTeachingStaff(Boolean teachingStaff) {
		this.teachingStaff = teachingStaff;
	}


	public EmployeeStreamBO getStream() {
		return stream;
	}


	public void setStream(EmployeeStreamBO stream) {
		this.stream = stream;
	}


	


	
}
