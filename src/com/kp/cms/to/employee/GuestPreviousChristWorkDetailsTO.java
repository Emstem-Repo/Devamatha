package com.kp.cms.to.employee;

import java.util.Date;
import java.util.Map;

import com.kp.cms.bo.employee.GuestFaculty;

public class GuestPreviousChristWorkDetailsTO {
	
	private int id;
	private String semester;
	private String startDate;
	private String endDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private GuestFaculty guest;
	private Boolean isActive;
	private Boolean isCurrentWorkingDates;
	private String deptId;
	private String strmId;
	private String workLocId;
	private String workHoursPerWeek;
	private String honorarium;
	private Map<String,String> deptMap;
	private Map<String,String> strmMap;
	private Map<String,String> workMap;
	public Map<String, String> getWorkMap() {
		return workMap;
	}
	public void setWorkMap(Map<String, String> workMap) {
		this.workMap = workMap;
	}
	public Map<String, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<String, String> deptMap) {
		this.deptMap = deptMap;
	}
	public Map<String, String> getStrmMap() {
		return strmMap;
	}
	public void setStrmMap(Map<String, String> strmMap) {
		this.strmMap = strmMap;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getStrmId() {
		return strmId;
	}
	public void setStrmId(String strmId) {
		this.strmId = strmId;
	}
	public String getWorkLocId() {
		return workLocId;
	}
	public void setWorkLocId(String workLocId) {
		this.workLocId = workLocId;
	}
	public String getWorkHoursPerWeek() {
		return workHoursPerWeek;
	}
	public void setWorkHoursPerWeek(String workHoursPerWeek) {
		this.workHoursPerWeek = workHoursPerWeek;
	}
	public String getHonorarium() {
		return honorarium;
	}
	public void setHonorarium(String honorarium) {
		this.honorarium = honorarium;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
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
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public Boolean getIsCurrentWorkingDates() {
		return isCurrentWorkingDates;
	}
	public void setIsCurrentWorkingDates(Boolean isCurrentWorkingDates) {
		this.isCurrentWorkingDates = isCurrentWorkingDates;
	}

}
