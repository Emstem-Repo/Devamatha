package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class InterviewResultUtilBO implements Serializable{
	private Integer id;
	private Integer interviewProgramCourseId;
	private Integer interviewStatusId;
	private String comments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Integer admApplnId;
	private Integer selectedPreference;
	private Boolean isActive;
	private BigDecimal weightageAdjustedMarks;
	private Integer interviewSubRoundsId;

	private Set<InterviewResultDetailUtilBO> interviewResultDetailUtilBOSet;

	public void setAll(InterviewResultUtilBO interviewResultInput, String userID) {
		this.admApplnId = interviewResultInput.getAdmApplnId();
		this.comments = interviewResultInput.getComments();
		this.interviewProgramCourseId = interviewResultInput
				.getInterviewProgramCourseId();
		this.interviewStatusId = interviewResultInput.getInterviewStatusId();
		this.interviewSubRoundsId = interviewResultInput
				.getInterviewSubRoundsId();
		this.lastModifiedDate = new Date();
		this.modifiedBy = userID;
		this.selectedPreference = interviewResultInput.getSelectedPreference();
		this.weightageAdjustedMarks = interviewResultInput
				.getWeightageAdjustedMarks();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInterviewProgramCourseId() {
		return interviewProgramCourseId;
	}

	public void setInterviewProgramCourseId(Integer interviewProgramCourseId) {
		this.interviewProgramCourseId = interviewProgramCourseId;
	}

	public Integer getInterviewStatusId() {
		return interviewStatusId;
	}

	public void setInterviewStatusId(Integer interviewStatusId) {
		this.interviewStatusId = interviewStatusId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Integer getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(Integer admApplnId) {
		this.admApplnId = admApplnId;
	}

	public Integer getSelectedPreference() {
		return selectedPreference;
	}

	public void setSelectedPreference(Integer selectedPreference) {
		this.selectedPreference = selectedPreference;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

	public Integer getInterviewSubRoundsId() {
		return interviewSubRoundsId;
	}

	public void setInterviewSubRoundsId(Integer interviewSubRoundsId) {
		this.interviewSubRoundsId = interviewSubRoundsId;
	}

	public Set<InterviewResultDetailUtilBO> getInterviewResultDetailUtilBOSet() {
		return interviewResultDetailUtilBOSet;
	}

	public void setInterviewResultDetailUtilBOSet(
			Set<InterviewResultDetailUtilBO> interviewResultDetailUtilBOSet) {
		this.interviewResultDetailUtilBOSet = interviewResultDetailUtilBOSet;
	}
}
