package com.kp.cms.bo.exam;

/**
 * Mar 5, 2010 Created By 9Elements Team
 */
public class ExamSubjectRuleSettingsAttendanceBO extends ExamGenBO {

	private int subjectRuleSettingsId;
	private Integer attendanceTypeId;
	private int isLeave;
	private int isCoCurricular;
	private String isTheoryPractical;
	private int isActiveInt;
	private ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO;
	private AttendanceTypeUtilBO attendanceTypeUtilBO;

	public ExamSubjectRuleSettingsAttendanceBO() {
		super();
	}

	public ExamSubjectRuleSettingsAttendanceBO(int subjectRuleSettingsId,
			Integer attendanceTypeId, int isLeave, int isCoCurricular,
			String isTheoryPractical, boolean isActive) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.attendanceTypeId = attendanceTypeId;
		this.isLeave = isLeave;
		this.isCoCurricular = isCoCurricular;
		this.isTheoryPractical = isTheoryPractical;
		this.isActive = isActive;
	}
	public ExamSubjectRuleSettingsAttendanceBO(int subjectRuleSettingsId,
			Integer attendanceTypeId, int isLeave, int isCoCurricular,
			String isTheoryPractical) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.attendanceTypeId = attendanceTypeId;
		this.isLeave = isLeave;
		this.isCoCurricular = isCoCurricular;
		this.isTheoryPractical = isTheoryPractical;
	}

	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}

	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}

	public Integer getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Integer attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public int getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}

	public int getIsCoCurricular() {
		return isCoCurricular;
	}

	public void setIsCoCurricular(int isCoCurricular) {
		this.isCoCurricular = isCoCurricular;
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

	public AttendanceTypeUtilBO getAttendanceTypeUtilBO() {
		return attendanceTypeUtilBO;
	}

	public void setAttendanceTypeUtilBO(
			AttendanceTypeUtilBO attendanceTypeUtilBO) {
		this.attendanceTypeUtilBO = attendanceTypeUtilBO;
	}

	public void setIsActiveInt(int isActiveInt) {
		this.isActiveInt = isActiveInt;
	}

	public int getIsActiveInt() {
		return isActiveInt;
	}

}
