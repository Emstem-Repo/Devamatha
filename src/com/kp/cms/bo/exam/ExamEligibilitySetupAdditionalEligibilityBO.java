package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamEligibilitySetupAdditionalEligibilityBO extends ExamGenBO {

	private int eligibilitySetupId;
	private int additionalEligibilityCriteriaId;

	private ExamEligibilitySetupBO examEligibilitySetupBO;
	private ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO;

	public ExamEligibilitySetupAdditionalEligibilityBO() {
		super();
	}

	public ExamEligibilitySetupAdditionalEligibilityBO(int eligibilitySetupId,
			int additionalEligibilityCriteriaId, String userID) {
		super();
		this.additionalEligibilityCriteriaId = additionalEligibilityCriteriaId;
		this.eligibilitySetupId = eligibilitySetupId;
		this.createdBy = userID;
		this.createdDate = new Date();
		this.modifiedBy = userID;
		this.lastModifiedDate = new Date();
	}

	public int getEligibilitySetupId() {
		return eligibilitySetupId;
	}

	public void setEligibilitySetupId(int eligibilitySetupId) {
		this.eligibilitySetupId = eligibilitySetupId;
	}

	public int getAdditionalEligibilityCriteriaId() {
		return additionalEligibilityCriteriaId;
	}

	public void setAdditionalEligibilityCriteriaId(
			int additionalEligibilityCriteriaId) {
		this.additionalEligibilityCriteriaId = additionalEligibilityCriteriaId;
	}

	public ExamEligibilitySetupBO getExamEligibilitySetupBO() {
		return examEligibilitySetupBO;
	}

	public void setExamEligibilitySetupBO(
			ExamEligibilitySetupBO examEligibilitySetupBO) {
		this.examEligibilitySetupBO = examEligibilitySetupBO;
	}

	public void setExamEligibilityCriteriaMasterBO(
			ExamEligibilityCriteriaMasterBO examEligibilityCriteriaMasterBO) {
		this.examEligibilityCriteriaMasterBO = examEligibilityCriteriaMasterBO;
	}

	public ExamEligibilityCriteriaMasterBO getExamEligibilityCriteriaMasterBO() {
		return examEligibilityCriteriaMasterBO;
	}

}
