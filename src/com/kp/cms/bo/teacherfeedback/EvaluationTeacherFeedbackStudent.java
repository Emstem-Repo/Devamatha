package com.kp.cms.bo.teacherfeedback;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class EvaluationTeacherFeedbackStudent implements Serializable{
	private int id;
	private Student student;
	private Subject subject;
	private EvaluationTeacherFeedback evaTeacherFeedback;
	private Set<EvaluationTeacherFeedbackAnswer> feedbackAnswer = new HashSet<EvaluationTeacherFeedbackAnswer>(0);
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
	public EvaluationTeacherFeedbackStudent() {
		super();
	}
	
	public EvaluationTeacherFeedbackStudent(int id, Student student,
			Subject subject, EvaluationTeacherFeedback evaTeacherFeedback,
			Set<EvaluationTeacherFeedbackAnswer> feedbackAnswer,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,Batch batch) {
		super();
		this.id = id;
		this.student = student;
		this.subject = subject;
		this.evaTeacherFeedback = evaTeacherFeedback;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public EvaluationTeacherFeedback getEvaTeacherFeedback() {
		return evaTeacherFeedback;
	}

	public void setEvaTeacherFeedback(EvaluationTeacherFeedback evaTeacherFeedback) {
		this.evaTeacherFeedback = evaTeacherFeedback;
	}

	public Set<EvaluationTeacherFeedbackAnswer> getFeedbackAnswer() {
		return feedbackAnswer;
	}

	public void setFeedbackAnswer(
			Set<EvaluationTeacherFeedbackAnswer> feedbackAnswer) {
		this.feedbackAnswer = feedbackAnswer;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
