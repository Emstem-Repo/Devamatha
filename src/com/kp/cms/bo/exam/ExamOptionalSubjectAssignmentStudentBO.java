package com.kp.cms.bo.exam;
/**
 * Feb 18, 2010 Created By 9Elements
 */
import java.util.Date;
import java.util.Set;

public class ExamOptionalSubjectAssignmentStudentBO extends ExamGenBO {

	private int studentId;
	private int specializationId;

	// many-to-one
	private StudentUtilBO studentUtilBO;
	private ExamSpecializationBO examSpecializationBO;
	
	private Set<ExamStudentOptionalSubjectGroupBO> examStudentOptionalSubjectGroupBOSet;

	public ExamOptionalSubjectAssignmentStudentBO() {
		super();
	}

	public ExamOptionalSubjectAssignmentStudentBO(int studentId,
			int specializationId, String userId) {
		super();
		this.studentId = studentId;
		this.specializationId = specializationId;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(int specializationId) {
		this.specializationId = specializationId;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamSpecializationBO getExamSpecializationBO() {
		return examSpecializationBO;
	}

	public void setExamSpecializationBO(
			ExamSpecializationBO examSpecializationBO) {
		this.examSpecializationBO = examSpecializationBO;
	}

	public void setExamStudentOptionalSubjectGroupBOSet(
			Set<ExamStudentOptionalSubjectGroupBO> examStudentOptionalSubjectGroupBOSet) {
		this.examStudentOptionalSubjectGroupBOSet = examStudentOptionalSubjectGroupBOSet;
	}

	public Set<ExamStudentOptionalSubjectGroupBO> getExamStudentOptionalSubjectGroupBOSet() {
		return examStudentOptionalSubjectGroupBOSet;
	}

}
