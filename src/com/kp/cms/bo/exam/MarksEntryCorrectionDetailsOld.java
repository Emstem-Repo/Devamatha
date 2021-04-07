package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Subject;

public class MarksEntryCorrectionDetailsOld implements Serializable {
	
	private int id;
	private MarksEntry marksEntry;
	private Subject subject;
	private String theoryMarks;
	private String practicalMarks;

	// For Secured Marks Entry Screen
	private String previousEvaluatorTheoryMarks;
	private String previousEvaluatorPracticalMarks;
	private Boolean isMistake;
	private Boolean isRetest;
	private String comments;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private StudentOverallInternalMarkDetails studentOverAllMark;
	private StudentFinalMarkDetails studentFinalMark;
	
	
	public MarksEntryCorrectionDetailsOld() {
		super();
	}

	public MarksEntryCorrectionDetailsOld(int id, MarksEntry marksEntry,
			Subject subject, String theoryMarks, String practicalMarks,
			String previousEvaluatorTheoryMarks,
			String previousEvaluatorPracticalMarks, Boolean isMistake,
			Boolean isRetest, String comments, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			StudentOverallInternalMarkDetails studentOverAllMark,
			StudentFinalMarkDetails studentFinalMark) {
		super();
		this.id = id;
		this.marksEntry = marksEntry;
		this.subject = subject;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
		this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
		this.isMistake = isMistake;
		this.isRetest = isRetest;
		this.comments = comments;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.studentOverAllMark = studentOverAllMark;
		this.studentFinalMark = studentFinalMark;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MarksEntry getMarksEntry() {
		return marksEntry;
	}
	public void setMarksEntry(MarksEntry marksEntry) {
		this.marksEntry = marksEntry;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public String getTheoryMarks() {
		return theoryMarks;
	}
	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}
	public String getPracticalMarks() {
		return practicalMarks;
	}
	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}
	public String getPreviousEvaluatorTheoryMarks() {
		return previousEvaluatorTheoryMarks;
	}
	public void setPreviousEvaluatorTheoryMarks(String previousEvaluatorTheoryMarks) {
		this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
	}
	public String getPreviousEvaluatorPracticalMarks() {
		return previousEvaluatorPracticalMarks;
	}
	public void setPreviousEvaluatorPracticalMarks(
			String previousEvaluatorPracticalMarks) {
		this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
	}
	public Boolean getIsMistake() {
		return isMistake;
	}
	public void setIsMistake(Boolean isMistake) {
		this.isMistake = isMistake;
	}
	public Boolean getIsRetest() {
		return isRetest;
	}
	public void setIsRetest(Boolean isRetest) {
		this.isRetest = isRetest;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public StudentOverallInternalMarkDetails getStudentOverAllMark() {
		return studentOverAllMark;
	}

	public void setStudentOverAllMark(
			StudentOverallInternalMarkDetails studentOverAllMark) {
		this.studentOverAllMark = studentOverAllMark;
	}

	public StudentFinalMarkDetails getStudentFinalMark() {
		return studentFinalMark;
	}

	public void setStudentFinalMark(StudentFinalMarkDetails studentFinalMark) {
		this.studentFinalMark = studentFinalMark;
	}
	
}
