package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class EmpArticleJournalsTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	
	private String empResPubMasterId;
	private String title;
	private String authorName;
	private String language;
	private String isbn;
	private String monthYear;
	private String nameJournal;
	private String volumeNumber;
	private String issueNumber;
	private String pagesFrom;
	private String pagesTo;
	private String doi;
	private String departmentInstitution;
	private String companyInstitution;
	private String placePublication;
	private String url;
	private String impactFactor;
	private String dateSent;
	private String dateAccepted;
	private String datePublished;
	
	private String approverComment;
	private Date approvedDate;
	private String entryCreatedate;
	private String approverId;
	private String employeeId;
	private Boolean isActive;
	private Boolean isArticleJournal;
	private Boolean isApproved;
	private String entryApprovedDate;
	private Boolean isRejected;
	private String rejectReason;
	private Date rejectDate;
	private String academicYear;
	private String type;
	private String entryRejectedDate;
	private String autoApprove;
	private String guestId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getNameJournal() {
		return nameJournal;
	}
	public void setNameJournal(String nameJournal) {
		this.nameJournal = nameJournal;
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
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsArticleJournal() {
		return isArticleJournal;
	}
	public void setIsArticleJournal(Boolean isArticleJournal) {
		this.isArticleJournal = isArticleJournal;
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
	public String getDepartmentInstitution() {
		return departmentInstitution;
	}
	public void setDepartmentInstitution(String departmentInstitution) {
		this.departmentInstitution = departmentInstitution;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImpactFactor() {
		return impactFactor;
	}
	public void setImpactFactor(String impactFactor) {
		this.impactFactor = impactFactor;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public String getDateAccepted() {
		return dateAccepted;
	}
	public void setDateAccepted(String dateAccepted) {
		this.dateAccepted = dateAccepted;
	}
	public String getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}
	public String getCompanyInstitution() {
		return companyInstitution;
	}
	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}
	public String getPlacePublication() {
		return placePublication;
	}
	public void setPlacePublication(String placePublication) {
		this.placePublication = placePublication;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
