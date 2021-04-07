package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;

public class ExceptionDetailsTo implements Serializable{
	
	private int id;
	private String exceptionType;
	private String staffStartDate;
	private String staffEndDate;
	private String remarks;
	private Boolean isActive;
	private String employeeName;
	private List<ExceptionDetailsDatesTO> exceptionDetailsDatesTOsList;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}
	/**
	 * @param exceptionType the exceptionType to set
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	/**
	 * @return the staffStartDate
	 */
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	public String getStaffStartDate() {
		return staffStartDate;
	}
	public void setStaffStartDate(String staffStartDate) {
		this.staffStartDate = staffStartDate;
	}
	public String getStaffEndDate() {
		return staffEndDate;
	}
	public void setStaffEndDate(String staffEndDate) {
		this.staffEndDate = staffEndDate;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public List<ExceptionDetailsDatesTO> getExceptionDetailsDatesTOsList() {
		return exceptionDetailsDatesTOsList;
	}
	public void setExceptionDetailsDatesTOsList(
			List<ExceptionDetailsDatesTO> exceptionDetailsDatesTOsList) {
		this.exceptionDetailsDatesTOsList = exceptionDetailsDatesTOsList;
	} 
	

}
