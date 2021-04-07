package com.kp.cms.bo.exam;

import java.math.BigDecimal;


@SuppressWarnings("serial")
public class ExamRevaluationDetailsBO extends ExamGenBO {

	private int examRevaluationId;
	private int subjectId;
	private BigDecimal previousTheoryMarks;
	private BigDecimal previousPracticalMarks;
	private BigDecimal currentTheoryMarks;
	private BigDecimal currentPracticalMarks;

	private ExamRevaluationBO examRevaluationBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamRevaluationDetailsBO() {
		super();
	}

	public ExamRevaluationDetailsBO(int examRevaluationId, int subjectId,
			BigDecimal previousTheoryMarks, BigDecimal previousPracticalMarks,
			BigDecimal currentTheoryMarks, BigDecimal currentPracticalMarks) {
		super();
		this.examRevaluationId = examRevaluationId;
		this.subjectId = subjectId;
		this.previousTheoryMarks = previousTheoryMarks;
		this.previousPracticalMarks = previousPracticalMarks;
		this.currentTheoryMarks = currentTheoryMarks;
		this.currentPracticalMarks = currentPracticalMarks;
	}

	
	public ExamRevaluationDetailsBO(Integer examRevaluationId, Integer subjectId,
			String userId) {
		this.examRevaluationId = examRevaluationId;
		this.subjectId = subjectId;
	}

	public int getExamRevaluationId() {
		return examRevaluationId;
	}

	public void setExamRevaluationId(int examRevaluationId) {
		this.examRevaluationId = examRevaluationId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public BigDecimal getPreviousTheoryMarks() {
		return previousTheoryMarks;
	}

	public void setPreviousTheoryMarks(BigDecimal previousTheoryMarks) {
		this.previousTheoryMarks = previousTheoryMarks;
	}

	public BigDecimal getPreviousPracticalMarks() {
		return previousPracticalMarks;
	}

	public void setPreviousPracticalMarks(BigDecimal previousPracticalMarks) {
		this.previousPracticalMarks = previousPracticalMarks;
	}

	public BigDecimal getCurrentTheoryMarks() {
		return currentTheoryMarks;
	}

	public void setCurrentTheoryMarks(BigDecimal currentTheoryMarks) {
		this.currentTheoryMarks = currentTheoryMarks;
	}

	public BigDecimal getCurrentPracticalMarks() {
		return currentPracticalMarks;
	}

	public void setCurrentPracticalMarks(BigDecimal currentPracticalMarks) {
		this.currentPracticalMarks = currentPracticalMarks;
	}

	public ExamRevaluationBO getExamRevaluationBO() {
		return examRevaluationBO;
	}

	public void setExamRevaluationBO(ExamRevaluationBO examRevaluationBO) {
		this.examRevaluationBO = examRevaluationBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

}
