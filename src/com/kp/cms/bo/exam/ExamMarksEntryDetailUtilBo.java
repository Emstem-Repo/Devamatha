package com.kp.cms.bo.exam;
@SuppressWarnings("serial")
public class ExamMarksEntryDetailUtilBo extends ExamGenBO
{
	private String theoryMarks;
	private String practicalMarks;
	private String previousEvaluatorTheoryMarks;
	private String previousEvaluatorPracticalMarks;
	private Integer isMistake;
	private Integer isRetest;
	private SubjectUtilBO subjectUtilBO;
	private ExamMarksEntryUtilBO examMarksEntryBO;
	private String comments;
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
	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}
	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}
	public ExamMarksEntryUtilBO getExamMarksEntryBO() {
		return examMarksEntryBO;
	}
	public void setExamMarksEntryBO(ExamMarksEntryUtilBO examMarksEntryBO) {
		this.examMarksEntryBO = examMarksEntryBO;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
