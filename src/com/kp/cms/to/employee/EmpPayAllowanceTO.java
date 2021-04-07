package com.kp.cms.to.employee;

import java.util.Date;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;

public class EmpPayAllowanceTO {
	private int id;
	private String allowanceValue;
	private boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private PayScaleBO payscale;
	private Employee employee;
	private EmpAllowance empAllowance;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAllowanceValue() {
		return allowanceValue;
	}
	public void setAllowanceValue(String allowanceValue) {
		this.allowanceValue = allowanceValue;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public PayScaleBO getPayscale() {
		return payscale;
	}
	public void setPayscale(PayScaleBO payscale) {
		this.payscale = payscale;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public EmpAllowance getEmpAllowance() {
		return empAllowance;
	}
	public void setEmpAllowance(EmpAllowance empAllowance) {
		this.empAllowance = empAllowance;
	}

}
