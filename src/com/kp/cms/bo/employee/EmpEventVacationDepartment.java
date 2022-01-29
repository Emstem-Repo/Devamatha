package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Department;

public class EmpEventVacationDepartment implements Serializable{
	
	private int id;
	private EmpEventVacation empEventVacation;
	private Department department;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	public EmpEventVacationDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmpEventVacationDepartment(int id,EmpEventVacation empEventVacation,
			Department department, String createdBy, Date createdDate,
			String modifiedBy,Date lastModifiedDate ) {
		super();
		this.id = id;
		this.empEventVacation = empEventVacation;
		this.department = department;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		
	}
	public int getId() {
		return id;
	}
	public EmpEventVacation getEmpEventVacation() {
		return empEventVacation;
	}
	public Department getDepartment() {
		return department;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEmpEventVacation(EmpEventVacation empEventVacation) {
		this.empEventVacation = empEventVacation;
	}
	public void setDepartment(Department department) {
		this.department = department;
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
}
