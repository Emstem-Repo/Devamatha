package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.MobNewsEventsCategory;

public class NewsEventsDetailsTO implements Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String dateTo;
	private String dateFrom;
	private String eventTitle;
	private String eventDescription;
	private byte[] eventImage;
	private byte[] iconImage;
	private String category;
	private int categoryId;
	private String participants;
	private String organisedBy;
	private String invitationMail;
	private String registrationForm;
	private String academicYear;
	private String deptId;
	private String streamId;
	private String courseId;
	private String splCentre;
	private Boolean isRegistrationRequired;
	private Boolean isLiveTelecast;
	private Boolean isEventInvitationMail;
	private String viewFor;
	private String eventWebPosition;
	private String newsWebPosition;
	private String summary;
	private String materialsPublished;
	private String eventReport;
	private String preApproverId;
	private Date preApprovalDate;
	private Boolean preIsApproved;
	private String preApprovalRemarks;
	private String postApproverId;
	private Date postApprovalDate;
	private Boolean postIsApproved;
	private String postApprovalRemarks;
	private Date displayFromDate;
	private Date displayToDate;
	private Boolean isAdminApprovePre;
	private	Boolean isAdminApprovePost;
	private String postApproveDecision; 
	private String preApproveDecision;
	private String newsOrEvents;
	private String departmentName;
	private String courseName;
	private String splCentreName;
	private String streamName;
	private String orgBy;
	private String status;
	private String isEditable;
	private Boolean isPostDeptEntry;

	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public byte[] getEventImage() {
		return eventImage;
	}
	public void setEventImage(byte[] eventImage) {
		this.eventImage = eventImage;
	}
	public byte[] getIconImage() {
		return iconImage;
	}
	public void setIconImage(byte[] iconImage) {
		this.iconImage = iconImage;
	}
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	public String getOrganisedBy() {
		return organisedBy;
	}
	public void setOrganisedBy(String organisedBy) {
		this.organisedBy = organisedBy;
	}
	public String getInvitationMail() {
		return invitationMail;
	}
	public void setInvitationMail(String invitationMail) {
		this.invitationMail = invitationMail;
	}
	public String getRegistrationForm() {
		return registrationForm;
	}
	public void setRegistrationForm(String registrationForm) {
		this.registrationForm = registrationForm;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public Boolean getIsRegistrationRequired() {
		return isRegistrationRequired;
	}
	public void setIsRegistrationRequired(Boolean isRegistrationRequired) {
		this.isRegistrationRequired = isRegistrationRequired;
	}
	public Boolean getIsLiveTelecast() {
		return isLiveTelecast;
	}
	public void setIsLiveTelecast(Boolean isLiveTelecast) {
		this.isLiveTelecast = isLiveTelecast;
	}
	public Boolean getIsEventInvitationMail() {
		return isEventInvitationMail;
	}
	public void setIsEventInvitationMail(Boolean isEventInvitationMail) {
		this.isEventInvitationMail = isEventInvitationMail;
	}
	public String getViewFor() {
		return viewFor;
	}
	public void setViewFor(String viewFor) {
		this.viewFor = viewFor;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMaterialsPublished() {
		return materialsPublished;
	}
	public void setMaterialsPublished(String materialsPublished) {
		this.materialsPublished = materialsPublished;
	}
	public String getEventReport() {
		return eventReport;
	}
	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}
	public Date getPreApprovalDate() {
		return preApprovalDate;
	}
	public void setPreApprovalDate(Date preApprovalDate) {
		this.preApprovalDate = preApprovalDate;
	}
	public Boolean getPreIsApproved() {
		return preIsApproved;
	}
	public void setPreIsApproved(Boolean preIsApproved) {
		this.preIsApproved = preIsApproved;
	}
	public String getPreApprovalRemarks() {
		return preApprovalRemarks;
	}
	public void setPreApprovalRemarks(String preApprovalRemarks) {
		this.preApprovalRemarks = preApprovalRemarks;
	}
	public Date getPostApprovalDate() {
		return postApprovalDate;
	}
	public void setPostApprovalDate(Date postApprovalDate) {
		this.postApprovalDate = postApprovalDate;
	}
	public Boolean getPostIsApproved() {
		return postIsApproved;
	}
	public void setPostIsApproved(Boolean postIsApproved) {
		this.postIsApproved = postIsApproved;
	}
	public String getPostApprovalRemarks() {
		return postApprovalRemarks;
	}
	public void setPostApprovalRemarks(String postApprovalRemarks) {
		this.postApprovalRemarks = postApprovalRemarks;
	}
	public Date getDisplayFromDate() {
		return displayFromDate;
	}
	public void setDisplayFromDate(Date displayFromDate) {
		this.displayFromDate = displayFromDate;
	}
	public Date getDisplayToDate() {
		return displayToDate;
	}
	public void setDisplayToDate(Date displayToDate) {
		this.displayToDate = displayToDate;
	}
	public String getEventWebPosition() {
		return eventWebPosition;
	}
	public void setEventWebPosition(String eventWebPosition) {
		this.eventWebPosition = eventWebPosition;
	}
	public String getNewsWebPosition() {
		return newsWebPosition;
	}
	public void setNewsWebPosition(String newsWebPosition) {
		this.newsWebPosition = newsWebPosition;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSplCentre() {
		return splCentre;
	}
	public void setSplCentre(String splCentre) {
		this.splCentre = splCentre;
	}
	public String getPreApproverId() {
		return preApproverId;
	}
	public void setPreApproverId(String preApproverId) {
		this.preApproverId = preApproverId;
	}
	public String getPostApproverId() {
		return postApproverId;
	}
	public void setPostApproverId(String postApproverId) {
		this.postApproverId = postApproverId;
	}
	public Boolean getIsAdminApprovePre() {
		return isAdminApprovePre;
	}
	public void setIsAdminApprovePre(Boolean isAdminApprovePre) {
		this.isAdminApprovePre = isAdminApprovePre;
	}
	public Boolean getIsAdminApprovePost() {
		return isAdminApprovePost;
	}
	public void setIsAdminApprovePost(Boolean isAdminApprovePost) {
		this.isAdminApprovePost = isAdminApprovePost;
	}
	public String getPostApproveDecision() {
		return postApproveDecision;
	}
	public void setPostApproveDecision(String postApproveDecision) {
		this.postApproveDecision = postApproveDecision;
	}
	public String getPreApproveDecision() {
		return preApproveDecision;
	}
	public void setPreApproveDecision(String preApproveDecision) {
		this.preApproveDecision = preApproveDecision;
	}
	public String getNewsOrEvents() {
		return newsOrEvents;
	}
	public void setNewsOrEvents(String newsOrEvents) {
		this.newsOrEvents = newsOrEvents;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSplCentreName() {
		return splCentreName;
	}
	public void setSplCentreName(String splCentreName) {
		this.splCentreName = splCentreName;
	}
	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	public String getOrgBy() {
		return orgBy;
	}
	public void setOrgBy(String orgBy) {
		this.orgBy = orgBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	public Boolean getIsPostDeptEntry() {
		return isPostDeptEntry;
	}
	public void setIsPostDeptEntry(Boolean isPostDeptEntry) {
		this.isPostDeptEntry = isPostDeptEntry;
	}
	
	
	 
	
	
	
	
	
	
	
	
	
}
