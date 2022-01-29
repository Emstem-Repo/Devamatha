package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EvaluationStudentFeedbackFaculty implements Serializable{
	private int id;
	private Users faculty;
	private Subject subject;
	private EvaluationStudentFeedback evaStuFeedback;
	private Set<EvaluationStudentFeedbackAnswer> feedbackAnswer = new HashSet<EvaluationStudentFeedbackAnswer>(0);
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String remarks;
	/* newly added */
	private String additionalRemarks;
	private Batch batch;
	/*  --------- */
	public EvaluationStudentFeedbackFaculty() {
		super();
	}
	
	public EvaluationStudentFeedbackFaculty(int id, Users faculty,
			Subject subject, EvaluationStudentFeedback evaStuFeedback,
			Set<EvaluationStudentFeedbackAnswer> feedbackAnswer,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,Batch batch) {
		super();
		this.id = id;
		this.faculty = faculty;
		this.subject = subject;
		this.evaStuFeedback = evaStuFeedback;
		this.feedbackAnswer = feedbackAnswer;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.batch = batch;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public EvaluationStudentFeedback getEvaStuFeedback() {
		return evaStuFeedback;
	}
	public void setEvaStuFeedback(EvaluationStudentFeedback evaStuFeedback) {
		this.evaStuFeedback = evaStuFeedback;
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

	public Set<EvaluationStudentFeedbackAnswer> getFeedbackAnswer() {
		return feedbackAnswer;
	}

	public void setFeedbackAnswer(
			Set<EvaluationStudentFeedbackAnswer> feedbackAnswer) {
		this.feedbackAnswer = feedbackAnswer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Users getFaculty() {
		return faculty;
	}

	public void setFaculty(Users faculty) {
		this.faculty = faculty;
	}

	public String getAdditionalRemarks() {
		return additionalRemarks;
	}

	public void setAdditionalRemarks(String additionalRemarks) {
		this.additionalRemarks = additionalRemarks;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	
}
