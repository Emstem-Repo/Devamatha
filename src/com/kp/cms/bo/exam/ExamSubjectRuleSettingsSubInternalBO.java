package com.kp.cms.bo.exam;

import java.math.BigDecimal;

/**
 * Mar 6, 2010 Created By 9Elements Team
 */
public class ExamSubjectRuleSettingsSubInternalBO extends ExamGenBO {

	private int subjectRuleSettingsId;
	private Integer internalExamTypeId;
	private BigDecimal minimumMark;
	private BigDecimal enteredMaxMark;
	private BigDecimal maximumMark;
	private String isTheoryPractical;
	private int isActiveInt;

	private ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO;
	private ExamInternalExamTypeBO examInternalExamTypeBO;

	public ExamSubjectRuleSettingsSubInternalBO() {
		super();
	}

	public ExamSubjectRuleSettingsSubInternalBO(int subjectRuleSettingsId,
			Integer internalExamTypeId, BigDecimal minimumMark,
			BigDecimal enteredMaxMark, BigDecimal maximumMark,
			String isTheoryPractical) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.internalExamTypeId = internalExamTypeId;
		this.minimumMark = minimumMark;
		this.enteredMaxMark = enteredMaxMark;
		this.maximumMark = maximumMark;
		this.isTheoryPractical = isTheoryPractical;
	}
    
	public ExamSubjectRuleSettingsSubInternalBO(int subjectRuleSettingsId,
			int internalExamTypeId, BigDecimal minimumMark,
			BigDecimal enteredMaxMark, BigDecimal maximumMark,
			String isTheoryPractical,boolean isActive) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.internalExamTypeId = internalExamTypeId;
		this.minimumMark = minimumMark;
		this.enteredMaxMark = enteredMaxMark;
		this.maximumMark = maximumMark;
		this.isTheoryPractical = isTheoryPractical;
		this.isActive = isActive;
		
	}

	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}

	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}

	public Integer getInternalExamTypeId() {
		return internalExamTypeId;
	}

	public void setInternalExamTypeId(Integer internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}

	public BigDecimal getMinimumMark() {
		return minimumMark;
	}

	public void setMinimumMark(BigDecimal minimumMark) {
		this.minimumMark = minimumMark;
	}

	public BigDecimal getEnteredMaxMark() {
		return enteredMaxMark;
	}

	public void setEnteredMaxMark(BigDecimal enteredMaxMark) {
		this.enteredMaxMark = enteredMaxMark;
	}

	public BigDecimal getMaximumMark() {
		return maximumMark;
	}

	public void setMaximumMark(BigDecimal maximumMark) {
		this.maximumMark = maximumMark;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public ExamSubjectRuleSettingsBO getExamSubjectRuleSettingsBO() {
		return examSubjectRuleSettingsBO;
	}

	public void setExamSubjectRuleSettingsBO(
			ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO) {
		this.examSubjectRuleSettingsBO = examSubjectRuleSettingsBO;
	}

	public ExamInternalExamTypeBO getExamInternalExamTypeBO() {
		return examInternalExamTypeBO;
	}

	public void setExamInternalExamTypeBO(
			ExamInternalExamTypeBO examInternalExamTypeBO) {
		this.examInternalExamTypeBO = examInternalExamTypeBO;
	}

	public void setIsActiveInt(int isActiveInt) {
		this.isActiveInt = isActiveInt;
	}

	public int getIsActiveInt() {
		return isActiveInt;
	}

}
