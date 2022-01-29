package com.kp.cms.bo.employee;
	import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;

	public class GuestPreviousChristWorkDetails implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String semester;
		private Date startDate;
		private Date endDate;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private GuestFaculty guest;
		private Boolean isActive;
		private Boolean isCurrentWorkingDates;
		private Department deptId;
		private EmployeeStreamBO strmId;
		private EmployeeWorkLocationBO worklLocId;
		private String workHoursPerWeek;
		private String honorarium;
		
		public GuestPreviousChristWorkDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public GuestPreviousChristWorkDetails(int id, String semester, 
				Date startDate, Date endDate, Boolean isActive, String createdBy,
				Date createdDate, String modifiedBy, Date lastModifiedDate,
				GuestFaculty guest,Boolean isCurrentWorkingDates) {
			super();
			this.id = id;
			this.endDate=endDate;
			this.startDate=startDate;
			this.semester=semester;
			this.isActive=isActive;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.lastModifiedDate = lastModifiedDate;
			this.guest = guest;
			this.isCurrentWorkingDates= isCurrentWorkingDates;
		}
		
		public Department getDeptId() {
			return deptId;
		}
		public void setDeptId(Department deptId) {
			this.deptId = deptId;
		}
		public EmployeeStreamBO getStrmId() {
			return strmId;
		}
		public void setStrmId(EmployeeStreamBO strmId) {
			this.strmId = strmId;
		}
		
		public EmployeeWorkLocationBO getWorklLocId() {
			return worklLocId;
		}
		public void setWorklLocId(EmployeeWorkLocationBO worklLocId) {
			this.worklLocId = worklLocId;
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
		public String getSemester() {
			return semester;
		}
		public void setSemester(String semester) {
			this.semester = semester;
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
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		public Boolean getIsCurrentWorkingDates() {
			return isCurrentWorkingDates;
		}
		public void setIsCurrentWorkingDates(Boolean isCurrentWorkingDates) {
			this.isCurrentWorkingDates = isCurrentWorkingDates;
		}
		

	}


