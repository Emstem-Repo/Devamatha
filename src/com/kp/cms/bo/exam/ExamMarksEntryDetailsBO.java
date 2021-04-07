package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamMarksEntryDetailsBO extends ExamGenBO implements Comparable<ExamMarksEntryDetailsBO>{

	private Integer marksEntryId;
	private int subjectId;
	private String theoryMarks;
	private String practicalMarks;

	// For Secured Marks Entry Screen
	private String previousEvaluatorTheoryMarks;
	private String previousEvaluatorPracticalMarks;
	private Integer isMistake;
	private Integer isRetest;

	// Many-to-one
	private SubjectUtilBO subjectUtilBO;
	private ExamMarksEntryBO examMarksEntryBO;
	private String comments;

	public ExamMarksEntryDetailsBO() {
		super();
	}

	public ExamMarksEntryDetailsBO(int marksEntryId, int subjectId,
			String theoryMarks, String practicalMarks, String userId) {
		super();
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.createdBy = userId;
		this.createdDate = new Date();
	}

	public ExamMarksEntryDetailsBO(Integer marksEntryId, Integer subjectId,
			String userId) {
		super();
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.createdBy = userId;
		this.createdDate = new Date();
	}

	public ExamMarksEntryDetailsBO(Integer marksEntryId, Integer subjectId) {
		super();
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.lastModifiedDate = new Date();

	}

	public ExamMarksEntryDetailsBO(Integer marksEntryId, Integer subjectId,
			Integer mistake, Integer retest) {
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.isMistake = mistake;
		this.isRetest = retest;
	}

	
	
	public ExamMarksEntryDetailsBO(Integer marksEntryId, Integer subjectId,
			int retest, int mistake, String theory, String practical,
			String comments, String userId ) {
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.isRetest = retest;
		this.isMistake = mistake;
		this.theoryMarks = theory;
		this.practicalMarks = practical;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.comments = comments;
	}

	public ExamMarksEntryDetailsBO(Integer marksEntryId, Integer subjectId,
			int retest, int mistake, String theory, String practical,
			String comments, String userId, String previousEvaluatorTheoryMarks,
			String previousEvaluatorPracticalMarks, String modifiedBy, Date lastModifiedDate,
			String createdBy, Date createdDate) {
		this.marksEntryId = marksEntryId;
		this.subjectId = subjectId;
		this.isRetest = retest;
		this.isMistake = mistake;
		this.theoryMarks = theory;
		this.practicalMarks = practical;
		this.comments = comments;
		this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
		this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
		this.modifiedBy = userId;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = userId;
		this.createdDate = new Date();		
	}
	
	public Integer getMarksEntryId() {
		return marksEntryId;
	}

	public void setMarksEntryId(Integer marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setExamMarksEntryBO(ExamMarksEntryBO examMarksEntryBO) {
		this.examMarksEntryBO = examMarksEntryBO;
	}

	public ExamMarksEntryBO getExamMarksEntryBO() {
		return examMarksEntryBO;
	}

	public String getPreviousEvaluatorTheoryMarks() {
		return previousEvaluatorTheoryMarks;
	}

	public void setPreviousEvaluatorTheoryMarks(
			String previousEvaluatorTheoryMarks) {
		this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
	}

	public String getPreviousEvaluatorPracticalMarks() {
		return previousEvaluatorPracticalMarks;
	}

	public void setPreviousEvaluatorPracticalMarks(
			String previousEvaluatorPracticalMarks) {
		this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
	}

	public Integer getIsMistake() {
		return isMistake;
	}

	public void setIsMistake(Integer isMistake) {
		this.isMistake = isMistake;
	}

	public Integer getIsRetest() {
		return isRetest;
	}

	public void setIsRetest(Integer isRetest) {
		this.isRetest = isRetest;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	@Override
	public int compareTo(ExamMarksEntryDetailsBO arg0) {
		if(arg0!=null && this!=null && arg0.getName()!=null
				 && this.getName()!=null){
			return this.getName().compareTo(arg0.getName());
		}else
		return 0;
	}

}
