package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamEligibilitySetupBO extends ExamGenBO {

	private int classId;
	private int examTypeId;
	private int isNoEligibilityChecked;
	private int isExamFeesChecked;
	private int isAttendanceChecked;
	private int isCourseFeesChecked;

	public ClassUtilBO classUtilBO;
	public ExamTypeUtilBO examTypeUtilBO;
	private Set<ExamEligibilitySetupAdditionalEligibilityBO> examEligibilitySetupAdditionalEligibilityBOset;

	public ExamEligibilitySetupBO() {
		super();
	}

	public ExamEligibilitySetupBO(int classId, int examTypeId,
			int isNoEligibilityChecked, int isExamFeesChecked,
			int isAttendanceChecked, int isCourseFeesChecked, String userID) {
		super();
		this.classId = classId;
		this.examTypeId = examTypeId;
		this.isNoEligibilityChecked = isNoEligibilityChecked;
		this.isExamFeesChecked = isExamFeesChecked;
		this.isAttendanceChecked = isAttendanceChecked;
		this.isCourseFeesChecked = isCourseFeesChecked;
		this.createdBy = userID;
		this.createdDate = new Date();
		this.modifiedBy = userID;
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}

	public int getIsNoEligibilityChecked() {
		return isNoEligibilityChecked;
	}

	public void setIsNoEligibilityChecked(int isNoEligibilityChecked) {
		this.isNoEligibilityChecked = isNoEligibilityChecked;
	}

	public int getIsExamFeesChecked() {
		return isExamFeesChecked;
	}

	public void setIsExamFeesChecked(int isExamFeesChecked) {
		this.isExamFeesChecked = isExamFeesChecked;
	}

	public int getIsAttendanceChecked() {
		return isAttendanceChecked;
	}

	public void setIsAttendanceChecked(int isAttendanceChecked) {
		this.isAttendanceChecked = isAttendanceChecked;
	}

	public int getIsCourseFeesChecked() {
		return isCourseFeesChecked;
	}

	public void setIsCourseFeesChecked(int isCourseFeesChecked) {
		this.isCourseFeesChecked = isCourseFeesChecked;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public ExamTypeUtilBO getExamTypeUtilBO() {
		return examTypeUtilBO;
	}

	public void setExamTypeUtilBO(ExamTypeUtilBO examTypeUtilBO) {
		this.examTypeUtilBO = examTypeUtilBO;
	}

	public Set<ExamEligibilitySetupAdditionalEligibilityBO> getExamEligibilitySetupAdditionalEligibilityBOset() {
		return examEligibilitySetupAdditionalEligibilityBOset;
	}

	public void setExamEligibilitySetupAdditionalEligibilityBOset(
			Set<ExamEligibilitySetupAdditionalEligibilityBO> examEligibilitySetupAdditionalEligibilityBOset) {
		this.examEligibilitySetupAdditionalEligibilityBOset = examEligibilitySetupAdditionalEligibilityBOset;
	}

}
