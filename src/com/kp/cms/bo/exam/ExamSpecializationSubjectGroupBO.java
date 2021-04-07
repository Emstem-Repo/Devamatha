package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamSpecializationSubjectGroupBO extends ExamGenBO {

	private int courseId;
	private int academicYear;
	private int schemeId;
	private int specializationId;
	private int subjectGroupId;

	private ExamCourseUtilBO examCourseUtilBO;
	private ExamSpecializationBO examSpecializationBO;
	private SubjectGroupUtilBO subjectGroupUtilBO;

	public ExamSpecializationSubjectGroupBO() {
		super();
	}

	public ExamSpecializationSubjectGroupBO(int academicYear, int courseId,
			int schemeId, int specializationId, int subjectGroupId,
			String userId) {
		super();
		this.academicYear = academicYear;
		this.courseId = courseId;
		this.schemeId = schemeId;
		this.specializationId = specializationId;
		this.subjectGroupId = subjectGroupId;
		this.createdBy = userId;
		this.createdDate = new Date();
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public int getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public int getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(int specializationId) {
		this.specializationId = specializationId;
	}

	public int getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public ExamSpecializationBO getExamSpecializationBO() {
		return examSpecializationBO;
	}

	public void setExamSpecializationBO(
			ExamSpecializationBO examSpecializationBO) {
		this.examSpecializationBO = examSpecializationBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

}
