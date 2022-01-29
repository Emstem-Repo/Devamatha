package com.kp.cms.bo.exam;


@SuppressWarnings("serial")
public class ExamSecuredMarksVerificationDetailsBO extends ExamGenBO {

	private int examSecuredMarkVerificationId;
	private int studentId;
	private String theoryMarks;
	private String practicalMarks;
	private int isMistake;
	private int isRetest;

	private ExamSecuredMarkVerificationBO examSecuredMarkVerificationBO;
	private StudentUtilBO studentUtilBO;

	public ExamSecuredMarksVerificationDetailsBO() {
		super();
	}

	public ExamSecuredMarksVerificationDetailsBO(
			int examSecuredMarkVerificationId, int studentId,
			String theoryMarks, String practicalMarks, int isMistake,
			int isRetest) {
		super();
		this.examSecuredMarkVerificationId = examSecuredMarkVerificationId;
		this.studentId = studentId;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.isMistake = isMistake;
		this.isRetest = isRetest;
	}

	public int getExamSecuredMarkVerificationId() {
		return examSecuredMarkVerificationId;
	}

	public void setExamSecuredMarkVerificationId(
			int examSecuredMarkVerificationId) {
		this.examSecuredMarkVerificationId = examSecuredMarkVerificationId;
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

	public int getIsMistake() {
		return isMistake;
	}

	public void setIsMistake(int isMistake) {
		this.isMistake = isMistake;
	}

	public int getIsRetest() {
		return isRetest;
	}

	public void setIsRetest(int isRetest) {
		this.isRetest = isRetest;
	}

	public ExamSecuredMarkVerificationBO getExamSecuredMarkVerificationBO() {
		return examSecuredMarkVerificationBO;
	}

	public void setExamSecuredMarkVerificationBO(
			ExamSecuredMarkVerificationBO examSecuredMarkVerificationBO) {
		this.examSecuredMarkVerificationBO = examSecuredMarkVerificationBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	

}
