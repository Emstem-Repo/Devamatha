package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Mar 4, 2010 Created By 9Elements Team
 */
public class ExamSubjectRuleSettingsAssignmentBO extends ExamGenBO {

	private int subjectRuleSettingsId;
	private Integer assignmentTypeId;
	private BigDecimal minimumMark;
	private BigDecimal maximumMark;
	private String isTheoryPractical;
	private int isActiveInt;

	private ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO;
	private ExamAssignmentTypeMasterBO examAssignmentTypeMasterBO;

	public ExamSubjectRuleSettingsAssignmentBO() {
		super();
	}

	public ExamSubjectRuleSettingsAssignmentBO(int subjectRuleSettingsId,
			Integer assignmentTypeId, BigDecimal minimumMark,
			BigDecimal maximumMark, String isTheoryPractical, boolean isActive) {
		super();
		
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.assignmentTypeId = assignmentTypeId;
		this.minimumMark = minimumMark;
		this.maximumMark = maximumMark;
		this.isTheoryPractical = isTheoryPractical;
		this.isActive = isActive;
	}
	public ExamSubjectRuleSettingsAssignmentBO(int subjectRuleSettingsId,
			Integer assignmentTypeId, BigDecimal minimumMark,
			BigDecimal maximumMark, String isTheoryPractical) {
		super();
		
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.assignmentTypeId = assignmentTypeId;
		this.minimumMark = minimumMark;
		this.maximumMark = maximumMark;
		this.isTheoryPractical = isTheoryPractical;
	}

	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}

	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}

	public int getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public BigDecimal getMinimumMark() {
		return minimumMark;
	}

	public void setMinimumMark(BigDecimal minimumMark) {
		this.minimumMark = minimumMark;
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

	public ExamAssignmentTypeMasterBO getExamAssignmentTypeMasterBO() {
		return examAssignmentTypeMasterBO;
	}

	public void setExamAssignmentTypeMasterBO(
			ExamAssignmentTypeMasterBO examAssignmentTypeMasterBO) {
		this.examAssignmentTypeMasterBO = examAssignmentTypeMasterBO;
	}

	public void setIsActiveInt(int isActiveInt) {
		this.isActiveInt = isActiveInt;
	}

	public int getIsActiveInt() {
		return isActiveInt;
	}

}
