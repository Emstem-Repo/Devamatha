package com.kp.cms.bo.exam;

/**
 * Mar 5, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamSubjecRuleSettingsMultipleEvaluatorBO extends ExamGenBO{

	private int subjectRuleSettingsId;
	private Integer evaluatorId;
	private Integer noOfEvaluations;
	private String typeOfEvaluation;
	private String isTheoryPractical;
	private EmployeeUtilBO employeeUtilBO;
	private ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO;
	public ExamSubjecRuleSettingsMultipleEvaluatorBO(int subjectRuleSettingsId,
			int evaluatorId, int noOfEvaluations, String typeOfEvaluation,
			String isTheoryPractical,boolean isActive) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.evaluatorId = evaluatorId;
		this.noOfEvaluations = noOfEvaluations;
		this.typeOfEvaluation = typeOfEvaluation;
		this.isTheoryPractical = isTheoryPractical;
		this.isActive = isActive;
	}
	public ExamSubjecRuleSettingsMultipleEvaluatorBO(int subjectRuleSettingsId,
			int evaluatorId, int noOfEvaluations, String typeOfEvaluation,
			String isTheoryPractical) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.evaluatorId = evaluatorId;
		this.noOfEvaluations = noOfEvaluations;
		this.typeOfEvaluation = typeOfEvaluation;
		this.isTheoryPractical = isTheoryPractical;
	}
	public ExamSubjecRuleSettingsMultipleEvaluatorBO() {
		super();
	}
	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}
	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}

	public Integer getEvaluatorId() {
		return evaluatorId;
	}
	public void setEvaluatorId(Integer evaluatorId) {
		this.evaluatorId = evaluatorId;
	}
	public Integer getNoOfEvaluations() {
		return noOfEvaluations;
	}
	public void setNoOfEvaluations(Integer noOfEvaluations) {
		this.noOfEvaluations = noOfEvaluations;
	}

	public String getTypeOfEvaluation() {
		return typeOfEvaluation;
	}
	public void setTypeOfEvaluation(String typeOfEvaluation) {
		this.typeOfEvaluation = typeOfEvaluation;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public EmployeeUtilBO getEmployeeUtilBO() {
		return employeeUtilBO;
	}

	public void setEmployeeUtilBO(EmployeeUtilBO employeeUtilBO) {
		this.employeeUtilBO = employeeUtilBO;
	}
	public ExamSubjectRuleSettingsBO getExamSubjectRuleSettingsBO() {
		return examSubjectRuleSettingsBO;
	}

	public void setExamSubjectRuleSettingsBO(
			ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO) {
		this.examSubjectRuleSettingsBO = examSubjectRuleSettingsBO;
	}

}
