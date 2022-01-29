package com.kp.cms.to.exam;

public class UploadMarksEntryTO 
{
	private String examId;
	private String studentId;
	private String subjectId;
	private String subjectType;
	private String marks;
	private Integer evaluatorId;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public Integer getEvaluatorId() {
		return evaluatorId;
	}
	public void setEvaluatorId(Integer evaluatorId) {
		this.evaluatorId = evaluatorId;
	}
}
