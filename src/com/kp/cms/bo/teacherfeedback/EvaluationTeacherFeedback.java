package com.kp.cms.bo.teacherfeedback;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Users;

public class EvaluationTeacherFeedback implements Serializable{
	private int id;
	private Users teacher;
	private Classes classes;
	private EvaluationTeacherFeedbackSession facultyEvaluationSession;
	private Boolean cancel;
	private Set<EvaluationTeacherFeedbackStudent> feedbackStudent=new HashSet<EvaluationTeacherFeedbackStudent>(0);
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public EvaluationTeacherFeedback() {
		super();
	}
	
	public EvaluationTeacherFeedback(int id, Users teacher, Classes classes,
			EvaluationTeacherFeedbackSession facultyEvaluationSession,
			Boolean cancel,
			Set<EvaluationTeacherFeedbackStudent> feedbackStudent,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.classes = classes;
		this.facultyEvaluationSession = facultyEvaluationSession;
		this.cancel = cancel;
		this.feedbackStudent = feedbackStudent;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getTeacher() {
		return teacher;
	}

	public void setTeacher(Users teacher) {
		this.teacher = teacher;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public EvaluationTeacherFeedbackSession getFacultyEvaluationSession() {
		return facultyEvaluationSession;
	}

	public void setFacultyEvaluationSession(
			EvaluationTeacherFeedbackSession facultyEvaluationSession) {
		this.facultyEvaluationSession = facultyEvaluationSession;
	}

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public Set<EvaluationTeacherFeedbackStudent> getFeedbackStudent() {
		return feedbackStudent;
	}

	public void setFeedbackStudent(
			Set<EvaluationTeacherFeedbackStudent> feedbackStudent) {
		this.feedbackStudent = feedbackStudent;
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


	
}
