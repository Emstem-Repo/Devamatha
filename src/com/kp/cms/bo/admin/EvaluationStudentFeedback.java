package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.studentfeedback.ExitEvaluationStudentFeedbackAnswer;


public class EvaluationStudentFeedback implements Serializable{
	private int id;
	private Student student;
	private Classes classes;
	private EvaluationStudentFeedbackSession facultyEvaluationSession;
	private Boolean cancel;
	private Set<EvaluationStudentFeedbackFaculty> feedbackFaculty=new HashSet<EvaluationStudentFeedbackFaculty>(0);
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isExitEval;
	private String exitEvalRemarks;
	private Set<ExitEvaluationStudentFeedbackAnswer> exitEvalFeedbackAnswer=new HashSet<ExitEvaluationStudentFeedbackAnswer>(0);
	
	private Set<EvaluationStudentSubjectWiseFeedbackAnswer> feedbackSubjectWiseAnswer = new HashSet<EvaluationStudentSubjectWiseFeedbackAnswer>(0);
	
	

	public EvaluationStudentFeedback() {
		super();
	}
	
	public EvaluationStudentFeedback(int id, Student student, Classes classes,
			EvaluationStudentFeedbackSession facultyEvaluationSession,
			Boolean cancel,
			Set<EvaluationStudentFeedbackFaculty> feedbackFaculty,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.student = student;
		this.classes = classes;
		this.facultyEvaluationSession = facultyEvaluationSession;
		this.cancel = cancel;
		this.feedbackFaculty = feedbackFaculty;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.feedbackSubjectWiseAnswer = feedbackSubjectWiseAnswer;
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

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public EvaluationStudentFeedbackSession getFacultyEvaluationSession() {
		return facultyEvaluationSession;
	}

	public void setFacultyEvaluationSession(
			EvaluationStudentFeedbackSession facultyEvaluationSession) {
		this.facultyEvaluationSession = facultyEvaluationSession;
	}

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
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

	public Set<EvaluationStudentFeedbackFaculty> getFeedbackFaculty() {
		return feedbackFaculty;
	}

	public void setFeedbackFaculty(
			Set<EvaluationStudentFeedbackFaculty> feedbackFaculty) {
		this.feedbackFaculty = feedbackFaculty;
	}

	public Boolean getIsExitEval() {
		return isExitEval;
	}

	public void setIsExitEval(Boolean isExitEval) {
		this.isExitEval = isExitEval;
	}

	public Set<ExitEvaluationStudentFeedbackAnswer> getExitEvalFeedbackAnswer() {
		return exitEvalFeedbackAnswer;
	}

	public void setExitEvalFeedbackAnswer(
			Set<ExitEvaluationStudentFeedbackAnswer> exitEvalFeedbackAnswer) {
		this.exitEvalFeedbackAnswer = exitEvalFeedbackAnswer;
	}

	public String getExitEvalRemarks() {
		return exitEvalRemarks;
	}

	public void setExitEvalRemarks(String exitEvalRemarks) {
		this.exitEvalRemarks = exitEvalRemarks;
	}
	public Set<EvaluationStudentSubjectWiseFeedbackAnswer> getFeedbackSubjectWiseAnswer() {
		return feedbackSubjectWiseAnswer;
	}

	public void setFeedbackSubjectWiseAnswer(
			Set<EvaluationStudentSubjectWiseFeedbackAnswer> feedbackSubjectWiseAnswer) {
		this.feedbackSubjectWiseAnswer = feedbackSubjectWiseAnswer;
	}
	
	
}
