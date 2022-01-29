package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSecuredMarksEntryTO implements Serializable {
	private String marksEntryId;
	private String detailId;
	private String registerNo;
	private String rollNo;
	private String theoryMarks;
	private String practicalMarks;
	private String previousEvaluatorMarks;
	private String studentName;
	private String isTheoryPractical;
	private int examId;
	private int subjectId;
	private String evaluatorType;
	private String answerScriptId;
	private int studentId;
	private boolean isSecured;
	private boolean mistake;
	private boolean retest;
	private Integer examMarksEntryId;
	private int serialNumber;
	private String errorType;
	private Integer maxMarks;
	private String registerNoError;
	private String marksError;
	private String percentageDifference;

	public ExamSecuredMarksEntryTO() {
		super();
	}

	public ExamSecuredMarksEntryTO(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMarksEntryId() {
		return marksEntryId;
	}

	public void setMarksEntryId(String marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(String evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	public String getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(String answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public boolean isSecured() {
		return isSecured;
	}

	public void setSecured(boolean isSecured) {
		this.isSecured = isSecured;
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

	public Integer getExamMarksEntryId() {
		return examMarksEntryId;
	}

	public void setExamMarksEntryId(Integer examMarksEntryId) {
		this.examMarksEntryId = examMarksEntryId;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
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

	public void setPreviousEvaluatorMarks(String previousEvaluatorMarks) {
		this.previousEvaluatorMarks = previousEvaluatorMarks;
	}

	public String getPreviousEvaluatorMarks() {
		return previousEvaluatorMarks;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}

	public Integer getMaxMarks() {
		return maxMarks;
	}

	public void setRegisterNoError(String registerNoError) {
		this.registerNoError = registerNoError;
	}

	public String getRegisterNoError() {
		return registerNoError;
	}

	public void setMarksError(String marksError) {
		this.marksError = marksError;
	}

	public String getMarksError() {
		return marksError;
	}

	public void setPercentageDifference(String percentageDifference) {
		this.percentageDifference = percentageDifference;
	}

	public String getPercentageDifference() {
		return percentageDifference;
	}

}
