package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NewsEventsDetails {
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Date dateTo;
	private Date dateFrom;
	private String eventTitle;
	private String eventDescription;
	private String eventImage;
	private String iconImage;
	private String invitationMail;
	private String registrationForm;
	private String academicYear;
	private String participants;
	private String organizedBy;
	private Department deptId;
	private EmployeeStreamBO streamId;
	private Course courseId;
	private Department splCentre;
	private Boolean isRegistrationRequired;
	private Boolean isLiveTelecast;
	private Boolean isEventInvitationMail;
	private MobNewsEventsCategory categoryId;
	private String viewFor;
	private String eventWebPosition;
	private String newsWebPosition;
	private String summary;
	private String materialsPublished;
	private String eventReport;
	private Employee preApproverId;
	private Date preApprovalDate;
	private Boolean preIsApproved;
	private String preApprovalRemarks;
	private Employee postApproverId;
	private Date postApprovalDate;
	private Boolean postIsApproved;
	private String postApprovalRemarks;
	private Date displayFromDate;
	private Date displayToDate;
	private Boolean isAdminApprovePre;
	private Boolean isAdminApprovePost;
	private String postApproveStatus;
	private String preApproveStatus;
	private String newOrEvents;
	private Boolean isPostDeptEntry;
	private Set<NewsEventsResourse> newsEventsResourse = new HashSet<NewsEventsResourse>(0);
	private Set<NewsEventsParticipants> newsEventsParticipants = new HashSet<NewsEventsParticipants>(0);
	private Set<NewsEventsPhoto> newsEventsPhotos = new HashSet<NewsEventsPhoto>(0);
	private Set<NewsEventsContactDetails> newsEventsContactDetails = new HashSet<NewsEventsContactDetails>(0);
	
	
	public NewsEventsDetails(String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, Boolean isActive,
			Date dateTo, Date dateFrom, String eventTitle,
			String eventDescription, String eventImage, String iconImage,
			String invitationMail, String registrationForm,
			String academicYear, String participants, String organizedBy,
			Department deptId, EmployeeStreamBO streamId, Course courseId,
			Department splCentre, Boolean isRegistrationRequired,
			Boolean isLiveTelecast, Boolean isEventInvitationMail,
			MobNewsEventsCategory categoryId, String viewFor,
			String eventWebPosition, String newsWebPosition, String summary,
			String materialsPublished, String eventReport,
			Employee preApproverId, Date preApprovalDate,
			Boolean preIsApproved, String preApprovalRemarks,
			Employee postApproverId, Date postApprovalDate,
			Boolean postIsApproved, String postApprovalRemarks,
			Date displayFromDate, Date displayToDate, Boolean isAdminApprovePre,
			Boolean isAdminApprovePost, String postApproveStatus, String preApproveStatus, 
			String newOrEvents,Boolean isPostDeptEntry,
			Set<NewsEventsResourse> newsEventsResourse, 
			Set<NewsEventsParticipants> newsEventsParticipants, Set<NewsEventsPhoto> newsEventsPhotos,
			Set<NewsEventsContactDetails> newsEventsContactDetails) {
		super();
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.dateTo = dateTo;
		this.dateFrom = dateFrom;
		this.eventTitle = eventTitle;
		this.eventDescription = eventDescription;
		this.eventImage = eventImage;
		this.iconImage = iconImage;
		this.invitationMail = invitationMail;
		this.registrationForm = registrationForm;
		this.academicYear = academicYear;
		this.participants = participants;
		this.organizedBy = organizedBy;
		this.deptId = deptId;
		this.streamId = streamId;
		this.courseId = courseId;
		this.splCentre = splCentre;
		this.isRegistrationRequired = isRegistrationRequired;
		this.isLiveTelecast = isLiveTelecast;
		this.isEventInvitationMail = isEventInvitationMail;
		this.categoryId = categoryId;
		this.viewFor = viewFor;
		this.eventWebPosition = eventWebPosition;
		this.newsWebPosition = newsWebPosition;
		this.summary = summary;
		this.materialsPublished = materialsPublished;
		this.eventReport = eventReport;
		this.preApproverId = preApproverId;
		this.preApprovalDate = preApprovalDate;
		this.preIsApproved = preIsApproved;
		this.preApprovalRemarks = preApprovalRemarks;
		this.postApproverId = postApproverId;
		this.postApprovalDate = postApprovalDate;
		this.postIsApproved = postIsApproved;
		this.postApprovalRemarks = postApprovalRemarks;
		this.displayFromDate = displayFromDate;
		this.displayToDate = displayToDate;
		this.newsEventsResourse = newsEventsResourse;
		this.newsEventsParticipants= newsEventsParticipants;
		this.isAdminApprovePost=isAdminApprovePost;
		this.isAdminApprovePre=isAdminApprovePre;
		this.preApproveStatus=preApproveStatus;
		this.postApproveStatus=postApproveStatus;
		this.newOrEvents=newOrEvents;
		this.newsEventsContactDetails=newsEventsContactDetails;
		this.isPostDeptEntry=isPostDeptEntry;
	}

	public NewsEventsDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MobNewsEventsCategory getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(MobNewsEventsCategory categoryId) {
		this.categoryId = categoryId;
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
	
	
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
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
	public String getEventImage() {
		return eventImage;
	}
	public void setEventImage(String eventImage) {
		this.eventImage = eventImage;
	}
	public String getIconImage() {
		return iconImage;
	}
	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
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
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	public String getOrganizedBy() {
		return organizedBy;
	}
	public void setOrganizedBy(String organizedBy) {
		this.organizedBy = organizedBy;
	}
	public Department getDeptId() {
		return deptId;
	}
	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}
	public EmployeeStreamBO getStreamId() {
		return streamId;
	}
	public void setStreamId(EmployeeStreamBO streamId) {
		this.streamId = streamId;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
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
	public Set<NewsEventsResourse> getNewsEventsResourse() {
		return newsEventsResourse;
	}
	public void setNewsEventsResourse(Set<NewsEventsResourse> newsEventsResourse) {
		this.newsEventsResourse = newsEventsResourse;
	}

	public Department getSplCentre() {
		return splCentre;
	}

	public void setSplCentre(Department splCentre) {
		this.splCentre = splCentre;
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

	public Employee getPreApproverId() {
		return preApproverId;
	}

	public void setPreApproverId(Employee preApproverId) {
		this.preApproverId = preApproverId;
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

	public Employee getPostApproverId() {
		return postApproverId;
	}

	public void setPostApproverId(Employee postApproverId) {
		this.postApproverId = postApproverId;
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

	public Set<NewsEventsParticipants> getNewsEventsParticipants() {
		return newsEventsParticipants;
	}

	public void setNewsEventsParticipants(
			Set<NewsEventsParticipants> newsEventsParticipants) {
		this.newsEventsParticipants = newsEventsParticipants;
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

	public Set<NewsEventsPhoto> getNewsEventsPhotos() {
		return newsEventsPhotos;
	}

	public void setNewsEventsPhotos(Set<NewsEventsPhoto> newsEventsPhotos) {
		this.newsEventsPhotos = newsEventsPhotos;
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

	public String getPostApproveStatus() {
		return postApproveStatus;
	}

	public void setPostApproveStatus(String postApproveStatus) {
		this.postApproveStatus = postApproveStatus;
	}

	public String getPreApproveStatus() {
		return preApproveStatus;
	}

	public void setPreApproveStatus(String preApproveStatus) {
		this.preApproveStatus = preApproveStatus;
	}

	public String getNewOrEvents() {
		return newOrEvents;
	}

	public void setNewOrEvents(String newOrEvents) {
		this.newOrEvents = newOrEvents;
	}

	public Set<NewsEventsContactDetails> getNewsEventsContactDetails() {
		return newsEventsContactDetails;
	}

	public void setNewsEventsContactDetails(
			Set<NewsEventsContactDetails> newsEventsContactDetails) {
		this.newsEventsContactDetails = newsEventsContactDetails;
	}

	public Boolean getIsPostDeptEntry() {
		return isPostDeptEntry;
	}

	public void setIsPostDeptEntry(Boolean isPostDeptEntry) {
		this.isPostDeptEntry = isPostDeptEntry;
	}

	
	
	
	
	
	

}
