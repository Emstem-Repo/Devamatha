package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamUpdateProcessBO extends ExamGenBO {

	private String academicYear;
	private int examId;
	private int classId;
	private String joiningBatch;
	private String process;
	private int isPromotionCriteriaChecked;
	private int isExamFeePaidChecked;

	private ExamDefinitionBO examDefinitionBO;
	private ClassUtilBO classUtilBO;

	public ExamUpdateProcessBO(String academicYear, int examId, int classId,
			String joiningBatch, String process,
			int isPromotionCriteriaChecked, int isExamFeePaidChecked) {
		super();
		this.academicYear = academicYear;
		this.examId = examId;
		this.classId = classId;
		this.joiningBatch = joiningBatch;
		this.process = process;
		this.isPromotionCriteriaChecked = isPromotionCriteriaChecked;
		this.isExamFeePaidChecked = isExamFeePaidChecked;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getJoiningBatch() {
		return joiningBatch;
	}

	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public int getIsPromotionCriteriaChecked() {
		return isPromotionCriteriaChecked;
	}

	public void setIsPromotionCriteriaChecked(int isPromotionCriteriaChecked) {
		this.isPromotionCriteriaChecked = isPromotionCriteriaChecked;
	}

	public int getIsExamFeePaidChecked() {
		return isExamFeePaidChecked;
	}

	public void setIsExamFeePaidChecked(int isExamFeePaidChecked) {
		this.isExamFeePaidChecked = isExamFeePaidChecked;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

}
