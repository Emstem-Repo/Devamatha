package com.kp.cms.to.employee;

import java.io.Serializable;

public class EmployeeSearchTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int empId;
	private String empName;
	private String empCode;
	private String departmentName;
	private String jobTitle;
	private String jobStatus;
    private String contactMobileExt;

    public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getContactMobileExt() {
		return contactMobileExt;
	}
	public void setContactMobileExt(String contactMobileExt) {
		this.contactMobileExt = contactMobileExt;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
    
    	
}