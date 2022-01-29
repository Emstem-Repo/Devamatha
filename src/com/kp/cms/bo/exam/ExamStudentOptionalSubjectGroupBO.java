package com.kp.cms.bo.exam;

import java.util.Date;

public class ExamStudentOptionalSubjectGroupBO extends ExamGenBO {

	private int specializationOptionalSubjectId;
	private int subjectGroupId;

	private SubjectGroupUtilBO subjectGroupUtilBO;
	private ExamOptionalSubjectAssignmentStudentBO examOptionalSubjectAssignmentStudentBO;

	public ExamStudentOptionalSubjectGroupBO() {
		super();
	}

	public ExamStudentOptionalSubjectGroupBO(
			int specializationOptionalSubjectId, int subjectGroupId,
			String userId) {
		super();
		this.specializationOptionalSubjectId = specializationOptionalSubjectId;
		this.subjectGroupId = subjectGroupId;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public ExamStudentOptionalSubjectGroupBO(
			int specializationOptionalSubjectId, int subjectGroupId) {
		this.specializationOptionalSubjectId = specializationOptionalSubjectId;
		this.subjectGroupId = subjectGroupId;
	}

	public int getSpecializationOptionalSubjectId() {
		return specializationOptionalSubjectId;
	}

	public void setSpecializationOptionalSubjectId(
			int specializationOptionalSubjectId) {
		this.specializationOptionalSubjectId = specializationOptionalSubjectId;
	}

	public int getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

	public void setExamOptionalSubjectAssignmentStudentBO(
			ExamOptionalSubjectAssignmentStudentBO examOptionalSubjectAssignmentStudentBO) {
		this.examOptionalSubjectAssignmentStudentBO = examOptionalSubjectAssignmentStudentBO;
	}

	public ExamOptionalSubjectAssignmentStudentBO getExamOptionalSubjectAssignmentStudentBO() {
		return examOptionalSubjectAssignmentStudentBO;
	}

}
