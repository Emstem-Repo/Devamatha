package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class EmpArticlInPeriodicalsTO implements Serializable{
	private int id;
	private String title;
	private String authorName;
	private String dateMonthYear;
	private String namePeriodical;
	private String language;
	private String volumeNumber;
	private String issueNumber;
	private String pagesFrom;
	private String pagesTo;
	private String isbn;
	
	private String approverComment;
	private String doi;
	private Date approvedDate;
	private String entryCreatedate;
	private String approverId;
	private String employeeId;
	private String guestId;
	private String empResPubMasterId;
	private Boolean isArticleInPeriodicals;
	private Boolean isActive;
	private Boolean isApproved;
	private String entryApprovedDate;
	private Boolean isRejected;
	private String rejectReason;
	private Date rejectDate;
	private String academicYear;
	private String type;
	private String otherTextArticle;
	private String entryRejectedDate;
	private String autoApprove;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public String getNamePeriodical() {
		return namePeriodical;
	}
	public void setNamePeriodical(String namePeriodical) {
		this.namePeriodical = namePeriodical;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getVolumeNumber() {
		return volumeNumber;
	}
	public void setVolumeNumber(String volumeNumber) {
		this.volumeNumber = volumeNumber;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getPagesFrom() {
		return pagesFrom;
	}
	public void setPagesFrom(String pagesFrom) {
		this.pagesFrom = pagesFrom;
	}
	public String getPagesTo() {
		return pagesTo;
	}
	public void setPagesTo(String pagesTo) {
		this.pagesTo = pagesTo;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
	public String getDateMonthYear() {
		return dateMonthYear;
	}
	public void setDateMonthYear(String dateMonthYear) {
		this.dateMonthYear = dateMonthYear;
	}
	public Boolean getIsArticleInPeriodicals() {
		return isArticleInPeriodicals;
	}
	public void setIsArticleInPeriodicals(Boolean isArticleInPeriodicals) {
		this.isArticleInPeriodicals = isArticleInPeriodicals;
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
	public String getOtherTextArticle() {
		return otherTextArticle;
	}
	public void setOtherTextArticle(String otherTextArticle) {
		this.otherTextArticle = otherTextArticle;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
