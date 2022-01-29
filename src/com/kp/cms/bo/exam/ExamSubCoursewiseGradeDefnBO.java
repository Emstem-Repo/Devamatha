package com.kp.cms.bo.exam;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ExamSubCoursewiseGradeDefnBO extends ExamGenBO {

	private int subjectId;
	private BigDecimal startPrcntgGrade;
	private BigDecimal endPrcntgGrade;
	private String grade;
	private String gradeInterpretation;
	private String resultClass;
	private BigDecimal gradePoint;
	private int courseId;
	private int academicYear;

	private SubjectUtilBO subjectUtilBO;

	public ExamSubCoursewiseGradeDefnBO() {
		super();
	}

	public ExamSubCoursewiseGradeDefnBO(int subjectId,
			BigDecimal startPrcntgGrade, BigDecimal endPrcntgGrade,
			String grade, String gradeInterpretation, String resultClass,
			BigDecimal gradePoint, int courseId,int academicYear) {
		super();
		this.subjectId = subjectId;
		this.startPrcntgGrade = startPrcntgGrade;
		this.endPrcntgGrade = endPrcntgGrade;
		this.grade = grade;
		this.gradeInterpretation = gradeInterpretation;
		this.resultClass = resultClass;
		this.gradePoint = gradePoint;
		this.courseId = courseId;
		this.academicYear=academicYear;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public BigDecimal getStartPrcntgGrade() {
		return startPrcntgGrade;
	}

	public void setStartPrcntgGrade(BigDecimal startPrcntgGrade) {
		this.startPrcntgGrade = startPrcntgGrade;
	}

	public BigDecimal getEndPrcntgGrade() {
		return endPrcntgGrade;
	}

	public void setEndPrcntgGrade(BigDecimal endPrcntgGrade) {
		this.endPrcntgGrade = endPrcntgGrade;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradeInterpretation() {
		return gradeInterpretation;
	}

	public void setGradeInterpretation(String gradeInterpretation) {
		this.gradeInterpretation = gradeInterpretation;
	}

	public String getResultClass() {
		return resultClass;
	}

	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	public BigDecimal getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(BigDecimal gradePoint) {
		this.gradePoint = gradePoint;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

}
