package com.kp.cms.bo.exam;

import java.math.BigDecimal;

/**
 * Mar 5, 2010 Created By 9Elements Team
 */
public class ExamSubjectRuleSettingsMultipleAnsScriptBO extends ExamGenBO {

	private int subjectRuleSettingsId;
	private Integer multipleAnswerScriptId;
	private BigDecimal value;
	private String isTheoryPractical;

	private ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;

	public ExamSubjectRuleSettingsMultipleAnsScriptBO() {
		super();
	}

	public ExamSubjectRuleSettingsMultipleAnsScriptBO(
			int subjectRuleSettingsId, Integer multipleAnswerScriptId,
			BigDecimal value, String isTheoryPractical,boolean isActive) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.multipleAnswerScriptId = multipleAnswerScriptId;
		this.value = value;
		this.isTheoryPractical = isTheoryPractical;
		this.isActive = isActive;
	}
	
	public ExamSubjectRuleSettingsMultipleAnsScriptBO(
			int subjectRuleSettingsId, Integer multipleAnswerScriptId,
			BigDecimal value, String isTheoryPractical) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.multipleAnswerScriptId = multipleAnswerScriptId;
		this.value = value;
		this.isTheoryPractical = isTheoryPractical;
	}

	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}

	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}

	public Integer getMultipleAnswerScriptId() {
		return multipleAnswerScriptId;
	}

	public void setMultipleAnswerScriptId(Integer multipleAnswerScriptId) {
		this.multipleAnswerScriptId = multipleAnswerScriptId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public ExamMultipleAnswerScriptMasterBO getExamMultipleAnswerScriptMasterBO() {
		return examMultipleAnswerScriptMasterBO;
	}

	public void setExamMultipleAnswerScriptMasterBO(
			ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO) {
		this.examMultipleAnswerScriptMasterBO = examMultipleAnswerScriptMasterBO;
	}

}
