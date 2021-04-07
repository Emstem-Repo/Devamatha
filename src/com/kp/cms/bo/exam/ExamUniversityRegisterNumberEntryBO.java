package com.kp.cms.bo.exam;

/**
 * February 6, 2009 Created By 9Elements Team
 */
import java.util.Date;

public class ExamUniversityRegisterNumberEntryBO extends ExamGenBO {

	private String academicYear;
	private int courseId;
	private int schemeNo;
	private int studentId;
	private Integer secondLanguageId;
	private String registerNo;

	private ExamCourseUtilBO examCourseUtilBO;
	private StudentUtilBO studentUtilBO;
	private ExamSecondLanguageMasterBO examSecondLanguageMasterBO;

	public ExamUniversityRegisterNumberEntryBO() {
		super();
	}

	public ExamUniversityRegisterNumberEntryBO(String academicYear,
			int studentId, int schemeNo,int courseId,Integer secondLanguageId, String registerNo,
			String userId) {
		super();
		this.academicYear = academicYear;
		this.studentId = studentId;
		this.schemeNo=schemeNo;
		this.courseId=courseId;
		this.secondLanguageId = secondLanguageId;
		this.registerNo = registerNo;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public ExamUniversityRegisterNumberEntryBO(String academicYear,
			int studentId, String registerNo, String userId) {
		super();
		this.academicYear = academicYear;
		this.studentId = studentId;
		this.registerNo = registerNo;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Integer getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguageId(Integer secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamSecondLanguageMasterBO getExamSecondLanguageMasterBO() {
		return examSecondLanguageMasterBO;
	}

	public void setExamSecondLanguageMasterBO(
			ExamSecondLanguageMasterBO examSecondLanguageMasterBO) {
		this.examSecondLanguageMasterBO = examSecondLanguageMasterBO;
	}

}
