package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamStudentMarksCorrectionAllstudentsTO implements Serializable {

	private Integer marksEntryId;// needed
	private Integer detailId;// needed, same as id of exammarkdetails
	private int studentId; // needed
	private String regNo;
	private String rolNo;
	private String studentName;
	private String theoryMarks;
	private String practicalMarks;
	private boolean mistake;
	private boolean retest;
	private String comments;
	private String isTheoryPractical;
	private boolean oldMarks;
	private String previousEvaluatorTheoryMarks;
	private String previousEvaluatorPracticalMarks;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public ExamStudentMarksCorrectionAllstudentsTO() {
		super();
	}

	public Integer getMarksEntryId() {
		return marksEntryId;
	}

	public void setMarksEntryId(Integer marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRolNo() {
		return rolNo;
	}

	public void setRolNo(String rolNo) {
		this.rolNo = rolNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public String getTheoryMarks() {
		return theoryMarks;
	}

	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public String getPracticalMarks() {
		return practicalMarks;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getMistake() {
		return mistake;
	}

	public void setMistake(boolean mistake) {
		this.mistake = mistake;
	}

	public boolean getRetest() {
		return retest;
	}

	public void setRetest(boolean retest) {
		this.retest = retest;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setOldMarks(boolean oldMarks) {
		this.oldMarks = oldMarks;
	}

	public boolean isOldMarks() {
		return oldMarks;
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

	
}
