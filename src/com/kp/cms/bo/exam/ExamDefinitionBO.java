package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

public class ExamDefinitionBO extends ExamGenBO {

	private int academicYear;
	private String examCode;
	private int examTypeID;
	private String month;
	private int year;
	private Integer examForJoiningBatch;
	private Integer maxNoFailedSub;
	private boolean isCurrent;
	private Integer internalExamTypeId;
	private boolean delIsActive;

	// many to 1
	private ExamInternalExamTypeBO internalExamTypeBO;
	private ExamTypeUtilBO examTypeUtilBO;
	private Integer regularExamId;

	// 1-to-many
	private Set<ExamExamCourseSchemeDetailsBO> examExamCourseSchemeDetailsBOSet;
	// 1-to-many
	private Set<ExamDefinitionProgramBO> examDefinitionProgramBOSet;

	public ExamDefinitionBO() {
		super();
	}

	public ExamDefinitionBO(int id, String examName, String examCode,
			int academicYear, int examTypeId, String month, int year,
			Integer joiningBatch, Integer maxFailedSub, boolean isCurrent,
			int internalTypeId, String userId, boolean isActive, Integer regularExamId) {
		super();
		this.id = id;
		this.name = examName;
		this.academicYear = academicYear;
		this.examCode = examCode;
		this.examForJoiningBatch = joiningBatch;
		this.examTypeID = examTypeId;
		this.isCurrent = isCurrent;
		this.maxNoFailedSub = maxFailedSub;
		this.month = month;
		this.year = year;
		if (internalTypeId == -1) {
			this.internalExamTypeId = null;
		} else {
			this.internalExamTypeId = internalTypeId;
		}
		this.createdBy = userId;
		this.modifiedBy = userId;
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		this.isActive = isActive;
		this.delIsActive=true;
		this.regularExamId = regularExamId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public int getExamTypeID() {
		return examTypeID;
	}

	public void setExamTypeID(int examTypeID) {
		this.examTypeID = examTypeID;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Integer getExamForJoiningBatch() {
		return examForJoiningBatch;
	}

	public void setExamForJoiningBatch(Integer examForJoiningBatch) {
		this.examForJoiningBatch = examForJoiningBatch;
	}

	public Integer getMaxNoFailedSub() {
		return maxNoFailedSub;
	}

	public void setMaxNoFailedSub(Integer maxNoFailedSub) {
		this.maxNoFailedSub = maxNoFailedSub;
	}

	public Integer getInternalExamTypeId() {
		return internalExamTypeId;
	}

	public void setInternalExamTypeId(Integer internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}

	public ExamInternalExamTypeBO getInternalExamTypeBO() {
		return internalExamTypeBO;
	}

	public void setInternalExamTypeBO(ExamInternalExamTypeBO internalExamTypeBO) {
		this.internalExamTypeBO = internalExamTypeBO;
	}

	public ExamTypeUtilBO getExamTypeUtilBO() {
		return examTypeUtilBO;
	}

	public void setExamTypeUtilBO(ExamTypeUtilBO examTypeUtilBO) {
		this.examTypeUtilBO = examTypeUtilBO;
	}

	public void setIsCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public boolean getIsCurrent() {
		return isCurrent;
	}

	public void setExamExamCourseSchemeDetailsBOSet(
			Set<ExamExamCourseSchemeDetailsBO> examExamCourseSchemeDetailsBOSet) {
		this.examExamCourseSchemeDetailsBOSet = examExamCourseSchemeDetailsBOSet;
	}

	public Set<ExamExamCourseSchemeDetailsBO> getExamExamCourseSchemeDetailsBOSet() {
		return examExamCourseSchemeDetailsBOSet;
	}

	public void setExamDefinitionProgramBOSet(
			Set<ExamDefinitionProgramBO> examDefinitionProgramBOSet) {
		this.examDefinitionProgramBOSet = examDefinitionProgramBOSet;
	}

	public Set<ExamDefinitionProgramBO> getExamDefinitionProgramBOSet() {
		return examDefinitionProgramBOSet;
	}

	public void setDelIsActive(boolean delIsActive) {
		this.delIsActive = delIsActive;
	}

	public boolean isDelIsActive() {
		return delIsActive;
	}

	public Integer getRegularExamId() {
		return regularExamId;
	}

	public void setRegularExamId(Integer regularExamId) {
		this.regularExamId = regularExamId;
	}

}
