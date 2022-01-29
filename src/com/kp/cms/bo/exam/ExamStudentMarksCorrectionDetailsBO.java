package com.kp.cms.bo.exam;


@SuppressWarnings("serial")

public class ExamStudentMarksCorrectionDetailsBO extends ExamGenBO {

	private int studentsMarksCorrectionId;
	private int subjectId;
	private int isRetest;
	private int isMistake;
	private String theoryMarks;
	private String practicalMarks;
	private String comments;

	private SubjectUtilBO subjectUtilBO;
	private ExamStudentMarksCorrectionBO examStudentMarksCorrectionBO;
	
	
	public ExamStudentMarksCorrectionDetailsBO() {
		super();
	}

	public ExamStudentMarksCorrectionDetailsBO(int studentsMarksCorrectionId,
			int subjectId, int isRetest, int isMistake, String theoryMarks,
			String practicalMarks, String comments) {
		super();
		this.studentsMarksCorrectionId = studentsMarksCorrectionId;
		this.subjectId = subjectId;
		this.isRetest = isRetest;
		this.isMistake = isMistake;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.comments = comments;
		
	}

	public int getStudentsMarksCorrectionId() {
		return studentsMarksCorrectionId;
	}

	public void setStudentsMarksCorrectionId(int studentsMarksCorrectionId) {
		this.studentsMarksCorrectionId = studentsMarksCorrectionId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getIsRetest() {
		return isRetest;
	}

	public void setIsRetest(int isRetest) {
		this.isRetest = isRetest;
	}

	public int getIsMistake() {
		return isMistake;
	}

	public void setIsMistake(int isMistake) {
		this.isMistake = isMistake;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setExamStudentMarksCorrectionBO(
			ExamStudentMarksCorrectionBO examStudentMarksCorrectionBO) {
		this.examStudentMarksCorrectionBO = examStudentMarksCorrectionBO;
	}

	public ExamStudentMarksCorrectionBO getExamStudentMarksCorrectionBO() {
		return examStudentMarksCorrectionBO;
	}

	
	
}
