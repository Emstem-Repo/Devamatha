package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamStudentMarksCorrectionSingleStudentTO implements Serializable {

	private Integer id;
	private Integer examMasterId;
	private int subjectId;
	private String subjectName;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPractical;
	private String previousEvaluatortheoryMarks;
	private String previousEvaluatorpracticalMarks;
	private boolean mistake;
	private boolean retest;
	private String comments;
	private boolean oldMarks;
	private String correctedDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedDate;
	private String subjectCode;
	

	public ExamStudentMarksCorrectionSingleStudentTO(Integer examMasterId,
			int subjectId, boolean retest, boolean mistake, String theoryMarks,
			String practicalMarks, String comments, boolean oldMarks, String previousEvaluatortheoryMarks,
			String previousEvaluatorpracticalMarks, String createdBy, Date createdDate, String modifiedBy,
			String modifiedDate) {
		super();
		this.examMasterId = examMasterId;
		this.subjectId = subjectId;
		this.retest = retest;
		this.mistake = mistake;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.setOldMarks(oldMarks);
		this.comments = comments;
		this.previousEvaluatortheoryMarks = previousEvaluatortheoryMarks;
		this.previousEvaluatorpracticalMarks = previousEvaluatorpracticalMarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public ExamStudentMarksCorrectionSingleStudentTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExamMasterId() {
		return examMasterId;
	}

	public void setExamMasterId(Integer examMasterId) {
		this.examMasterId = examMasterId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public boolean isMistake() {
		return mistake;
	}

	public void setMistake(boolean mistake) {
		this.mistake = mistake;
	}

	public boolean isRetest() {
		return retest;
	}

	public void setRetest(boolean retest) {
		this.retest = retest;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setOldMarks(boolean oldMarks) {
		this.oldMarks = oldMarks;
	}

	public boolean isOldMarks() {
		return oldMarks;
	}

	public void setCorrectedDate(String correctedDate) {
		this.correctedDate = correctedDate;
	}

	public String getCorrectedDate() {
		return correctedDate;
	}

	public String getPreviousEvaluatortheoryMarks() {
		return previousEvaluatortheoryMarks;
	}

	public void setPreviousEvaluatortheoryMarks(String previousEvaluatortheoryMarks) {
		this.previousEvaluatortheoryMarks = previousEvaluatortheoryMarks;
	}

	public String getPreviousEvaluatorpracticalMarks() {
		return previousEvaluatorpracticalMarks;
	}

	public void setPreviousEvaluatorpracticalMarks(
			String previousEvaluatorpracticalMarks) {
		this.previousEvaluatorpracticalMarks = previousEvaluatorpracticalMarks;
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
}
