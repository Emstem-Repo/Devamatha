package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class EmpConferencePresentationTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String nameTalksPresentation;
	private String nameConferencesSeminar;
	private String language;
	private String monthYear;
	private String placePublication;
	private String companyInstitution;
	private String abstractDetails;
	
	private String approverComment;
	private String doi;
	private Date approvedDate;
	private String entryCreatedate;
	
	private String approverId;
	private String employeeId;
	private String empResPubMasterId;
	private Boolean isConferencePresentation;
	private Boolean isActive;
	private Boolean isApproved;
	private String entryApprovedDate;
	private Boolean isRejected;
	private String rejectReason;
	private Date rejectDate;
	private String academicYear;
	private String type;
	private String typeOfPgm;
	private String otherTextConf;
	private String entryRejectedDate;
	private String autoApprove;
	private String guestId;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNameTalksPresentation() {
		return nameTalksPresentation;
	}
	public void setNameTalksPresentation(String nameTalksPresentation) {
		this.nameTalksPresentation = nameTalksPresentation;
	}
	public String getNameConferencesSeminar() {
		return nameConferencesSeminar;
	}
	public void setNameConferencesSeminar(String nameConferencesSeminar) {
		this.nameConferencesSeminar = nameConferencesSeminar;
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
	public String getPlacePublication() {
		return placePublication;
	}
	public void setPlacePublication(String placePublication) {
		this.placePublication = placePublication;
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
	public String getCompanyInstitution() {
		return companyInstitution;
	}
	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsConferencePresentation() {
		return isConferencePresentation;
	}
	public void setIsConferencePresentation(Boolean isConferencePresentation) {
		this.isConferencePresentation = isConferencePresentation;
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
	public String getAbstractDetails() {
		return abstractDetails;
	}
	public void setAbstractDetails(String abstractDetails) {
		this.abstractDetails = abstractDetails;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeOfPgm() {
		return typeOfPgm;
	}
	public void setTypeOfPgm(String typeOfPgm) {
		this.typeOfPgm = typeOfPgm;
	}
	public String getOtherTextConf() {
		return otherTextConf;
	}
	public void setOtherTextConf(String otherTextConf) {
		this.otherTextConf = otherTextConf;
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
