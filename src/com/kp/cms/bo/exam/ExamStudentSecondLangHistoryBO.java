package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamStudentSecondLangHistoryBO extends ExamGenBO {

	private int studentId;
	private int secondLanguageId;

	private StudentUtilBO studentUtilBO;
	private ExamSecondLanguageMasterBO examSecondLanguageMasterBO;

	public ExamStudentSecondLangHistoryBO() {
		super();
	}

	public ExamStudentSecondLangHistoryBO(int studentId, int secondLanguageId) {
		super();
		this.setStudentId(studentId);
		this.secondLanguageId = secondLanguageId;
	}

	public int getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguageId(int secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public ExamSecondLanguageMasterBO getExamSecondLanguageMasterBO() {
		return examSecondLanguageMasterBO;
	}

	public void setExamSecondLanguageMasterBO(
			ExamSecondLanguageMasterBO examSecondLanguageMasterBO) {
		this.examSecondLanguageMasterBO = examSecondLanguageMasterBO;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

}
