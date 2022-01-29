package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentEligibilityCheckBO extends ExamGenBO {

	private int classId;
	private int examId;
	private int studentId;
	private int examTypeId;
	private int isCourseFeesPaid;
	private int isExamFeesPaid;
	private int isAttendance;
	private String remarks;
	private int isExamEligibile;

	private ClassUtilBO classUtilBO;
	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ExamTypeUtilBO examTypeUtilBO;

	public ExamStudentEligibilityCheckBO() {
		super();
	}

	public ExamStudentEligibilityCheckBO(int classId, int examId,
			int studentId, int examTypeId, int isCourseFeesPaid,
			int isExamFeesPaid, int isAttendance, String remarks,
			int isExamEligibile) {
		super();
		this.classId = classId;
		this.examId = examId;
		this.studentId = studentId;
		this.examTypeId = examTypeId;
		this.isCourseFeesPaid = isCourseFeesPaid;
		this.isExamFeesPaid = isExamFeesPaid;
		this.isAttendance = isAttendance;
		this.remarks = remarks;
		this.isExamEligibile = isExamEligibile;
	}

	public ExamStudentEligibilityCheckBO(String userId) {
		super();
		this.createdBy = userId;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getIsExamEligibile() {
		return isExamEligibile;
	}

	public void setIsExamEligibile(int isExamEligibile) {
		this.isExamEligibile = isExamEligibile;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setIsExamFeesPaid(int isExamFeesPaid) {
		this.isExamFeesPaid = isExamFeesPaid;
	}

	public int getIsExamFeesPaid() {
		return isExamFeesPaid;
	}

	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}

	public int getExamTypeId() {
		return examTypeId;
	}

	public void setIsAttendance(int isAttendance) {
		this.isAttendance = isAttendance;
	}

	public int getIsAttendance() {
		return isAttendance;
	}

	public void setIsCourseFeesPaid(int isCourseFeesPaid) {
		this.isCourseFeesPaid = isCourseFeesPaid;
	}

	public int getIsCourseFeesPaid() {
		return isCourseFeesPaid;
	}

	public void setExamTypeUtilBO(ExamTypeUtilBO examTypeUtilBO) {
		this.examTypeUtilBO = examTypeUtilBO;
	}

	public ExamTypeUtilBO getExamTypeUtilBO() {
		return examTypeUtilBO;
	}

}
