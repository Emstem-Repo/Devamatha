package com.kp.cms.bo.exam;


@SuppressWarnings("serial")
public class ExamStudentEligibilityCheckCriteriaBO extends ExamGenBO {

	private int stuEligibilityChkId;
	private int eligibilityCriteriaId;
	private int isEligibile;

	private ExamStudentEligibilityCheckBO examStudentEligibilityCheckBO;
	private ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO;

	public ExamStudentEligibilityCheckCriteriaBO() {
		super();
	}

	public ExamStudentEligibilityCheckCriteriaBO(int stuEligibilityChkId,
			int eligibilityCriteriaId, int isEligibile) {
		super();
		this.stuEligibilityChkId = stuEligibilityChkId;
		this.eligibilityCriteriaId = eligibilityCriteriaId;
		this.isEligibile = isEligibile;
	}

	public int getStuEligibilityChkId() {
		return stuEligibilityChkId;
	}

	public void setStuEligibilityChkId(int stuEligibilityChkId) {
		this.stuEligibilityChkId = stuEligibilityChkId;
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

	public ExamStudentEligibilityCheckBO getExamStudentEligibilityCheckBO() {
		return examStudentEligibilityCheckBO;
	}

	public void setExamStudentEligibilityCheckBO(
			ExamStudentEligibilityCheckBO examStudentEligibilityCheckBO) {
		this.examStudentEligibilityCheckBO = examStudentEligibilityCheckBO;
	}

	public ExamEligibilityCriteriaMasterBO getExamEligibilityCriteriaMasterBO() {
		return examEligibilityCriteriaMasterBO;
	}

	public void setExamEligibilityCriteriaMasterBO(
			ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO) {
		this.examEligibilityCriteriaMasterBO = examEligibilityCriteriaMasterBO;
	}

}
