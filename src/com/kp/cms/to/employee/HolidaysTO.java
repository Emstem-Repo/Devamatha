package com.kp.cms.to.employee;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;

public class HolidaysTO {
	
	private int id;
	private int empTypeId;
	private String  empTypeName;
	private int academicYear;
	private String startDate;
	private String endDate;
	private String holiday;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private int datesId[];
	private Set<Integer> datesIdSet;
	private String date;
	private List<HolidaysDatesTO> holidaysDatesTOList;
	private String[] datesArray;
	
	public int getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(int empTypeId) {
		this.empTypeId = empTypeId;
	}
	public String getEmpTypeName() {
		return empTypeName;
	}
	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int[] getDatesId() {
		return datesId;
	}
	public void setDatesId(int[] datesId) {
		this.datesId = datesId;
	}
	public Set<Integer> getDatesIdSet() {
		return datesIdSet;
	}
	public void setDatesIdSet(Set<Integer> datesIdSet) {
		this.datesIdSet = datesIdSet;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<HolidaysDatesTO> getHolidaysDatesTOList() {
		return holidaysDatesTOList;
	}
	public void setHolidaysDatesTOList(List<HolidaysDatesTO> holidaysDatesTOList) {
		this.holidaysDatesTOList = holidaysDatesTOList;
	}
	public String[] getDatesArray() {
		return datesArray;
	}
	public void setDatesArray(String[] datesArray) {
		this.datesArray = datesArray;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
