package com.kp.cms.bo.admission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.employee.EmpLoan;

public class AdmSelectionSettings implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private String time;
	private String venue;
	private Course courseId;
	private String gender;
	private University universityId; 
	private String batchYear;
	private String listName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<AdmSelectionSettingsDetails> admSelectionSettingsDetails = new HashSet<AdmSelectionSettingsDetails>(0);
	
	public AdmSelectionSettings() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AdmSelectionSettings(Date date, String time, String venue,
			Course courseId, String gender, University universityId,
			String batchYear, String listName,
			Set<AdmSelectionSettingsDetails> admSelectionSettingsDetails, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.date = date;
		this.time = time;
		this.venue = venue;
		this.courseId = courseId;
		this.gender = gender;
		this.universityId = universityId;
		this.batchYear = batchYear;
		this.listName = listName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.admSelectionSettingsDetails=admSelectionSettingsDetails;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public University getUniversityId() {
		return universityId;
	}
	public void setUniversityId(University universityId) {
		this.universityId = universityId;
	}
	public String getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(String batchYear) {
		this.batchYear = batchYear;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
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

	public Set<AdmSelectionSettingsDetails> getAdmSelectionSettingsDetails() {
		return admSelectionSettingsDetails;
	}

	public void setAdmSelectionSettingsDetails(
			Set<AdmSelectionSettingsDetails> admSelectionSettingsDetails) {
		this.admSelectionSettingsDetails = admSelectionSettingsDetails;
	}
	

}
