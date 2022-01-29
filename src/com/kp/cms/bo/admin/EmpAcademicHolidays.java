package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EmpAcademicHolidays {
	
	private int id;
	private EmployeeTypeBO  employeeTypeBO;
	private AcademicYear academicYearBO;
	private int academicYear;
	private Date startDate;
	private Date endDate;
	private String holiday;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<EmpAcademicHolidaysDates> empAcademicHolidaysDates = new HashSet<EmpAcademicHolidaysDates>(0);
	
	public EmpAcademicHolidays() {
	}

	public EmpAcademicHolidays(int id) {
		this.id = id;
	}

	public EmpAcademicHolidays(int id, AcademicYear academicYearBO, int academicYear,
			Date startDate, Date endDate, String holiday,String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,EmployeeTypeBO  employeeTypeBO, 
			Set<EmpAcademicHolidaysDates> empAcademicHolidaysDates) {
		this.id = id;
		this.academicYearBO = academicYearBO;
		this.academicYear = academicYear;
		this.startDate = startDate;
		this.endDate = endDate;
		this.holiday = holiday;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.empAcademicHolidaysDates = empAcademicHolidaysDates;
		this.employeeTypeBO = employeeTypeBO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AcademicYear getAcademicYearBO() {
		return academicYearBO;
	}

	public void setAcademicYearBO(AcademicYear academicYearBO) {
		this.academicYearBO = academicYearBO;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
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

	public Set<EmpAcademicHolidaysDates> getEmpAcademicHolidaysDates() {
		return empAcademicHolidaysDates;
	}

	public void setEmpAcademicHolidaysDates(
			Set<EmpAcademicHolidaysDates> empAcademicHolidaysDates) {
		this.empAcademicHolidaysDates = empAcademicHolidaysDates;
	}

	public EmployeeTypeBO getEmployeeTypeBO() {
		return employeeTypeBO;
	}

	public void setEmployeeTypeBO(EmployeeTypeBO employeeTypeBO) {
		this.employeeTypeBO = employeeTypeBO;
	}
}
