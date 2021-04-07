package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Subject;

public class MarksEntryDetailsOldDetained implements Serializable{
	
	private int id;
	private MarksEntryOldDetained marksEntry;
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
	private Boolean isTheorySecured;
	private Boolean isPracticalSecured;
	
	public MarksEntryDetailsOldDetained() {
		super();
	}
	
	public MarksEntryDetailsOldDetained(int id, MarksEntryOldDetained marksEntry, Subject subject,
			String theoryMarks, String practicalMarks,
			String previousEvaluatorTheoryMarks,
			String previousEvaluatorPracticalMarks, Boolean isMistake,
			Boolean isRetest, String comments, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			Boolean isTheorySecured, Boolean isPracticalSecured) {
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
		this.isTheorySecured = isTheorySecured;
		this.isPracticalSecured = isPracticalSecured;
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



	public Boolean getIsTheorySecured() {
		return isTheorySecured;
	}



	public void setIsTheorySecured(Boolean isTheorySecured) {
		this.isTheorySecured = isTheorySecured;
	}



	public Boolean getIsPracticalSecured() {
		return isPracticalSecured;
	}



	public void setIsPracticalSecured(Boolean isPracticalSecured) {
		this.isPracticalSecured = isPracticalSecured;
	}

	public MarksEntryOldDetained getMarksEntry() {
		return marksEntry;
	}

	public void setMarksEntry(MarksEntryOldDetained marksEntry) {
		this.marksEntry = marksEntry;
	}
	
	
}
