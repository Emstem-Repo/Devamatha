package com.kp.cms.bo.exam;


import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentEligibilityEntryBO extends ExamGenBO {

	private int classId;
	private int studentId;
	private int eligibilityCriteriaId;
	private int isEligibile;

	private ClassUtilBO classUtilBO;
	private ExamDefinitionBO examDefinitionBO;

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	private StudentUtilBO studentUtilBO;
	private ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO;

	public ExamStudentEligibilityEntryBO() {
		super();
	}

	public ExamStudentEligibilityEntryBO(String userId) {
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

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getEligibilityCriteriaId() {
		return eligibilityCriteriaId;
	}

	public void setEligibilityCriteriaId(int eligibilityCriteriaId) {
		this.eligibilityCriteriaId = eligibilityCriteriaId;
	}

	public int getIsEligibile() {
		return isEligibile;
	}

	public void setIsEligibile(int isEligibile) {
		this.isEligibile = isEligibile;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamEligibilityCriteriaMasterBO getExamEligibilityCriteriaMasterBO() {
		return examEligibilityCriteriaMasterBO;
	}

	public void setExamEligibilityCriteriaMasterBO(
			ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO) {
		this.examEligibilityCriteriaMasterBO = examEligibilityCriteriaMasterBO;
	}

}
