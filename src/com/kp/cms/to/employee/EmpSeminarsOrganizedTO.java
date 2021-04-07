package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class EmpSeminarsOrganizedTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nameConferencesSeminar;
	private String nameOrganisers;
	private String resoursePerson;
	private String place;
	private String sponsors;
	private String language;
	private String dateMonthYear;

	
	private String approverComment;
	private String doi;
	private Date approvedDate;
	private String entryCreatedate;
	
	private String approverId;
	private String employeeId;
	private String empResPubMasterId;
	private Boolean isActive;
	private Boolean isSeminarOrganized;
	private Boolean isApproved;
	private String entryApprovedDate;
	private Boolean isRejected;
	private String rejectReason;
	private Date rejectDate;
	private String academicYear;
	private String entryRejectedDate;
	private String autoApprove;
	private String guestId;
	
	public String getNameConferencesSeminar() {
		return nameConferencesSeminar;
	}
	public void setNameConferencesSeminar(String nameConferencesSeminar) {
		this.nameConferencesSeminar = nameConferencesSeminar;
	}
	public String getNameOrganisers() {
		return nameOrganisers;
	}
	public void setNameOrganisers(String nameOrganisers) {
		this.nameOrganisers = nameOrganisers;
	}
	public String getResoursePerson() {
		return resoursePerson;
	}
	public void setResoursePerson(String resoursePerson) {
		this.resoursePerson = resoursePerson;
	}
	
	public String getSponsors() {
		return sponsors;
	}
	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	
	public String getEntryCreatedate() {
		return entryCreatedate;
	}
	public void setEntryCreatedate(String entryCreatedate) {
		this.entryCreatedate = entryCreatedate;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpResPubMasterId() {
		return empResPubMasterId;
	}
	public void setEmpResPubMasterId(String empResPubMasterId) {
		this.empResPubMasterId = empResPubMasterId;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateMonthYear() {
		return dateMonthYear;
	}
	public void setDateMonthYear(String dateMonthYear) {
		this.dateMonthYear = dateMonthYear;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsSeminarOrganized() {
		return isSeminarOrganized;
	}
	public void setIsSeminarOrganized(Boolean isSeminarOrganized) {
		this.isSeminarOrganized = isSeminarOrganized;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getEntryApprovedDate() {
		return entryApprovedDate;
	}
	public void setEntryApprovedDate(String entryApprovedDate) {
		this.entryApprovedDate = entryApprovedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public Boolean getIsRejected() {
		return isRejected;
	}
	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public Date getRejectDate() {
		return rejectDate;
	}
	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getEntryRejectedDate() {
		return entryRejectedDate;
	}
	public void setEntryRejectedDate(String entryRejectedDate) {
		this.entryRejectedDate = entryRejectedDate;
	}
	public String getAutoApprove() {
		return autoApprove;
	}
	public void setAutoApprove(String autoApprove) {
		this.autoApprove = autoApprove;
	}
	public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	
	

}
