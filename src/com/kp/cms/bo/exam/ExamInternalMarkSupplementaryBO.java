package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamInternalMarkSupplementaryBO extends ExamGenBO {

	private int studentId;
	private int subjectId;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPractical;

	// many-to-one
	private StudentUtilBO studentUtilBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamInternalMarkSupplementaryBO() {
		super();
	}

	public ExamInternalMarkSupplementaryBO(int studentId, int subjectId,
			String theoryMarks, String practicalMarks,
			String isTheoryPractical, String userId) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.isTheoryPractical = isTheoryPractical;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public ExamInternalMarkSupplementaryBO(Integer studentId,
			Integer subjectId, String theoryMarks, String practicalMarks,
			String isTheoryPractical) {

		this.studentId = studentId;
		this.subjectId = subjectId;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.isTheoryPractical = isTheoryPractical;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

}
