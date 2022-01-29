package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSecuredMarksVerificationTO implements Serializable{

	private Integer marksEntryId;
	private Integer detailId;
	private String regNo;
	private String rollNo;
	private String marks;
	private String theoryMarks;
	private String practicalMarks;
	private String studentName;
	private String isTheoryPractical;
	private int examId;
	private int subjectId;
	private Integer evaluatorType;
	private Integer answerScriptId;
	private int studentId;
	private boolean isSecured;
	private boolean mistake;
	private boolean retest;
	private Integer examMarksEntryId;
	private String middleName;
	private String lastName;
	private String packetNo;
	private String classId;
	
	
	

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ExamSecuredMarksVerificationTO() {
		super();
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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

	public Integer getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(Integer evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	public Integer getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(Integer answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public boolean getSecured() {
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

	public void setMarksEntryId(Integer marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public Integer getMarksEntryId() {
		return marksEntryId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setExamMarksEntryId(Integer examMarksEntryId) {
		this.examMarksEntryId = examMarksEntryId;
	}

	public Integer getExamMarksEntryId() {
		return examMarksEntryId;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMarks() {
		return marks;
	}

	public String getPacketNo() {
		return packetNo;
	}

	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	

}
