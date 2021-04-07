package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class EmpBlogTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String url;
	private String numberOfComments;
	private String subject;
	private String language;
	private String monthYear;
	
	private String approverComment;
	private String doi;
	private Date approvedDate;
	private String entryCreatedate;
	
	private String approverId;
	private String employeeId;
	private String empResPubMasterId;
	private Boolean isBlog;
	private Boolean isActive;
	private Boolean isApproved;
	private String entryApprovedDate;
	private Boolean isRejected;
	private String rejectReason;
	private Date rejectDate;
	private String academicYear;
	private String entryRejectedDate;
	private String autoApprove;
	private String guestId;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNumberOfComments() {
		return numberOfComments;
	}
	public void setNumberOfComments(String numberOfComments) {
		this.numberOfComments = numberOfComments;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsBlog() {
		return isBlog;
	}
	public void setIsBlog(Boolean isBlog) {
		this.isBlog = isBlog;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
