package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsBO extends ExamGenBO {
	private int academicYear;
	private int courseId;
	private int schemeNo;
	private int subjectId;
	private Integer selectBestOfTheoryInternal;
	private int finalTheoryInternalIsSubInternal;
	private int finalTheoryInternalIsAttendance;
	private int finalTheoryInternalIsAssignment;
	private BigDecimal finalTheoryInternalMinimumMark;
	private BigDecimal finalTheoryInternalEnteredMaxMark;
	private BigDecimal finalTheoryInternalMaximumMark;
	private Integer selectBestOfPracticalInternal;
	private int finalPracticalInternalIsSubInternal;
	private int finalPracticalInternalIsAttendance;
	private int finalPracticalInternalIsAssignment;
	private BigDecimal finalPracticalInternalMinimum_mark;
	private BigDecimal finalPracticalInternalEnteredMaxMark;
	private BigDecimal finalPracticalInternalMaximumMark;
	private int theoryEseIsRegular;
	private int theoryEseIsMultipleAnswerScript;
	private int theoryEseIsMultipleEvaluator;
	private BigDecimal theoryEseMinimumMark;
	private BigDecimal theoryEseEnteredMaxMark;
	private BigDecimal theoryEseMaximumMark;
	private BigDecimal theoryEseTheoryFinalMinimumMark;
	private BigDecimal theoryEseTheoryFinalMaximumMark;
	private int practicalEseIsRegular;
	private int practicalEseIsMultipleAnswerScript;
	private int practicalEseIsMultipleEvaluator;
	private BigDecimal practicalEseMinimumMark;
	private BigDecimal practicalEseEnteredMaxMark;
	private BigDecimal practicalEseMaximumMark;
	private BigDecimal practicalEseTheoryFinalMinimumMark;
	private BigDecimal practicalEseTheoryFinalMaximumMark;
	private int subjectFinalIsTheoryExam;
	private int subjectFinalIsPracticalExam;
	private int subjectFinalIsInternalExam;
	private int subjectFinalIsAttendance;
	private BigDecimal subjectFinalMinimum;
	private BigDecimal subjectFinalMaximum;
	private BigDecimal subjectFinalValuated;
	private BigDecimal theoryIntMinMarksTotal;
	private BigDecimal theoryIntMaxMarksTotal;
	private BigDecimal theoryIntEntryMaxMarksTotal;
	private BigDecimal practicalIntMinMarksTotal;
	private BigDecimal practicalIntMaxMarksTotal;
	private BigDecimal practicalIntEntryMaxMarksTotal;
	private int isActiveInt;
	// Many-to-one
	private ExamCourseUtilBO examCourseUtilBO;
	public ExamSubjectRuleSettingsBO(int id,int academicYear,
			BigDecimal finalPracticalInternalEnteredMaxMark,
			int finalPracticalInternalIsAssignment,
			int finalPracticalInternalIsAttendance,
			int finalPracticalInternalIsSubInternal,
			BigDecimal finalPracticalInternalMaximumMark,
			BigDecimal finalPracticalInternalMinimum_mark,
			BigDecimal finalTheoryInternalEnteredMaxMark,
			int finalTheoryInternalIsAssignment,
			int finalTheoryInternalIsAttendance,
			int finalTheoryInternalIsSubInternal,
			BigDecimal finalTheoryInternalMaximumMark,
			BigDecimal finalTheoryInternalMinimumMark,
			BigDecimal practicalEseEnteredMaxMark,
			int practicalEseIsMultipleAnswerScript,
			int practicalEseIsMultipleEvaluator, int practicalEseIsRegular,
			BigDecimal practicalEseMaximumMark,
			BigDecimal practicalEseMinimumMark,
			BigDecimal practicalEseTheoryFinalMaximumMark,
			BigDecimal practicalEseTheoryFinalMinimumMark,
			BigDecimal practicalIntEntryMaxMarksTotal,
			BigDecimal practicalIntMaxMarksTotal,
			BigDecimal practicalIntMinMarksTotal,
			Integer selectBestOfPracticalInternal, Integer selectBestOfTheoryInternal,
			int subjectFinalIsAttendance, int subjectFinalIsInternalExam,
			int subjectFinalIsPracticalExam, int subjectFinalIsTheoryExam,
			BigDecimal subjectFinalMaximum, BigDecimal subjectFinalMinimum,
			BigDecimal subjectFinalValuated,
			BigDecimal theoryEseEnteredMaxMark,
			int theoryEseIsMultipleAnswerScript,
			int theoryEseIsMultipleEvaluator, int theoryEseIsRegular,
			BigDecimal theoryEseMaximumMark, BigDecimal theoryEseMinimumMark,
			BigDecimal theoryEseTheoryFinalMaximumMark,
			BigDecimal theoryEseTheoryFinalMinimumMark,
			BigDecimal theoryIntEntryMaxMarksTotal,
			BigDecimal theoryIntMaxMarksTotal, BigDecimal theoryIntMinMarksTotal) {
		
		
		/*(int id, String name, String createdBy, Date createdDate,
				Date lastModifiedDate, String modifiedBy, boolean isActive)*/
		super();
		this.id=id;
		this.academicYear=academicYear;
		this.finalPracticalInternalEnteredMaxMark = finalPracticalInternalEnteredMaxMark;
		this.finalPracticalInternalIsAssignment = finalPracticalInternalIsAssignment;
		this.finalPracticalInternalIsAttendance = finalPracticalInternalIsAttendance;
		this.finalPracticalInternalIsSubInternal = finalPracticalInternalIsSubInternal;
		this.finalPracticalInternalMaximumMark = finalPracticalInternalMaximumMark;
		this.finalPracticalInternalMinimum_mark = finalPracticalInternalMinimum_mark;
		this.finalTheoryInternalEnteredMaxMark = finalTheoryInternalEnteredMaxMark;
		this.finalTheoryInternalIsAssignment = finalTheoryInternalIsAssignment;
		this.finalTheoryInternalIsAttendance = finalTheoryInternalIsAttendance;
		this.finalTheoryInternalIsSubInternal = finalTheoryInternalIsSubInternal;
		this.finalTheoryInternalMaximumMark = finalTheoryInternalMaximumMark;
		this.finalTheoryInternalMinimumMark = finalTheoryInternalMinimumMark;
		this.practicalEseEnteredMaxMark = practicalEseEnteredMaxMark;
		this.practicalEseIsMultipleAnswerScript = practicalEseIsMultipleAnswerScript;
		this.practicalEseIsMultipleEvaluator = practicalEseIsMultipleEvaluator;
		this.practicalEseIsRegular = practicalEseIsRegular;
		this.practicalEseMaximumMark = practicalEseMaximumMark;
		this.practicalEseMinimumMark = practicalEseMinimumMark;
		this.practicalEseTheoryFinalMaximumMark = practicalEseTheoryFinalMaximumMark;
		this.practicalEseTheoryFinalMinimumMark = practicalEseTheoryFinalMinimumMark;
		this.practicalIntEntryMaxMarksTotal = practicalIntEntryMaxMarksTotal;
		this.practicalIntMaxMarksTotal = practicalIntMaxMarksTotal;
		this.practicalIntMinMarksTotal = practicalIntMinMarksTotal;
		this.selectBestOfPracticalInternal = selectBestOfPracticalInternal;
		this.selectBestOfTheoryInternal = selectBestOfTheoryInternal;
		this.subjectFinalIsAttendance = subjectFinalIsAttendance;
		this.subjectFinalIsInternalExam = subjectFinalIsInternalExam;
		this.subjectFinalIsPracticalExam = subjectFinalIsPracticalExam;
		this.subjectFinalIsTheoryExam = subjectFinalIsTheoryExam;
		this.subjectFinalMaximum = subjectFinalMaximum;
		this.subjectFinalMinimum = subjectFinalMinimum;
		this.subjectFinalValuated = subjectFinalValuated;
		this.theoryEseEnteredMaxMark = theoryEseEnteredMaxMark;
		this.theoryEseIsMultipleAnswerScript = theoryEseIsMultipleAnswerScript;
		this.theoryEseIsMultipleEvaluator = theoryEseIsMultipleEvaluator;
		this.theoryEseIsRegular = theoryEseIsRegular;
		this.theoryEseMaximumMark = theoryEseMaximumMark;
		this.theoryEseMinimumMark = theoryEseMinimumMark;
		this.theoryEseTheoryFinalMaximumMark = theoryEseTheoryFinalMaximumMark;
		this.theoryEseTheoryFinalMinimumMark = theoryEseTheoryFinalMinimumMark;
		this.theoryIntEntryMaxMarksTotal = theoryIntEntryMaxMarksTotal;
		this.theoryIntMaxMarksTotal = theoryIntMaxMarksTotal;
		this.theoryIntMinMarksTotal = theoryIntMinMarksTotal;
	}

	private SubjectUtilBO subjectUtilBO;

	public ExamSubjectRuleSettingsBO() {
		super();
	}
	
	
	
	public ExamSubjectRuleSettingsBO(ExamSubjectRuleSettingsBO bo) {
		super();
	
		this.id=bo.getId();
		this.academicYear = bo.academicYear;
		this.courseId = bo.courseId;
		this.finalPracticalInternalEnteredMaxMark = bo.finalPracticalInternalEnteredMaxMark;
		this.finalPracticalInternalIsAssignment = bo.finalPracticalInternalIsAssignment;
		this.finalPracticalInternalIsAttendance = bo.finalPracticalInternalIsAttendance;
		this.finalPracticalInternalIsSubInternal = bo.finalPracticalInternalIsSubInternal;
		this.finalPracticalInternalMaximumMark = bo.finalPracticalInternalMaximumMark;
		this.finalPracticalInternalMinimum_mark = bo.finalPracticalInternalMinimum_mark;
		this.finalTheoryInternalEnteredMaxMark = bo.finalTheoryInternalEnteredMaxMark;
		this.finalTheoryInternalIsAssignment = bo.finalTheoryInternalIsAssignment;
		this.finalTheoryInternalIsAttendance = bo.finalTheoryInternalIsAttendance;
		this.finalTheoryInternalIsSubInternal =bo. finalTheoryInternalIsSubInternal;
		this.finalTheoryInternalMaximumMark = bo.finalTheoryInternalMaximumMark;
		this.finalTheoryInternalMinimumMark = bo.finalTheoryInternalMinimumMark;
		this.practicalEseEnteredMaxMark = bo.practicalEseEnteredMaxMark;
		this.practicalEseIsMultipleAnswerScript = bo.practicalEseIsMultipleAnswerScript;
		this.practicalEseIsMultipleEvaluator = bo.practicalEseIsMultipleEvaluator;
		this.practicalEseIsRegular = bo.practicalEseIsRegular;
		this.practicalEseMaximumMark = bo.practicalEseMaximumMark;
		this.practicalEseMinimumMark = bo.practicalEseMinimumMark;
		this.practicalEseTheoryFinalMaximumMark = bo.practicalEseTheoryFinalMaximumMark;
		this.practicalEseTheoryFinalMinimumMark = bo.practicalEseTheoryFinalMinimumMark;
		this.schemeNo = bo.schemeNo;
		this.selectBestOfPracticalInternal = bo.selectBestOfPracticalInternal;
		this.selectBestOfTheoryInternal = bo.selectBestOfTheoryInternal;
		this.subjectFinalIsAttendance = bo.subjectFinalIsAttendance;
		this.subjectFinalIsInternalExam = bo.subjectFinalIsInternalExam;
		this.subjectFinalIsPracticalExam = bo.subjectFinalIsPracticalExam;
		this.subjectFinalIsTheoryExam = bo.subjectFinalIsTheoryExam;
		this.subjectFinalMaximum =bo. subjectFinalMaximum;
		this.subjectFinalMinimum = bo.subjectFinalMinimum;
		this.subjectFinalValuated = bo.subjectFinalValuated;
		this.subjectId = bo.subjectId;
		this.theoryEseEnteredMaxMark = bo.theoryEseEnteredMaxMark;
		this.theoryEseIsMultipleAnswerScript =bo.theoryEseIsMultipleAnswerScript;
		this.theoryEseIsMultipleEvaluator = bo.theoryEseIsMultipleEvaluator;
		this.theoryEseIsRegular = bo.theoryEseIsRegular;
		this.theoryEseMaximumMark = bo.theoryEseMaximumMark;
		this.theoryEseMinimumMark = bo.theoryEseMinimumMark;
		this.theoryEseTheoryFinalMaximumMark = bo.theoryEseTheoryFinalMaximumMark;
		this.theoryEseTheoryFinalMinimumMark = bo.theoryEseTheoryFinalMinimumMark;
		this.createdBy = bo.createdBy;
		this.createdDate = bo.createdDate;
		this.lastModifiedDate = bo.lastModifiedDate;
		this.modifiedBy = bo.modifiedBy;
		//this.isActiveInt = bo.isActiveInt;
		this.isActive = bo.isActive;
		this.theoryIntMinMarksTotal = bo.theoryIntMinMarksTotal;
		this.theoryIntMaxMarksTotal = bo.theoryIntMaxMarksTotal;
		this.theoryIntEntryMaxMarksTotal = bo.theoryIntEntryMaxMarksTotal;
		this.practicalIntMinMarksTotal = bo.practicalIntMinMarksTotal;
		this.practicalIntMaxMarksTotal = bo.practicalIntMaxMarksTotal;
		this.practicalIntEntryMaxMarksTotal = bo.practicalIntEntryMaxMarksTotal;
	}
	
	
	public ExamSubjectRuleSettingsBO(int academicYear, int courseId,
			int schemeNo,boolean isActive) {
		super();
		this.academicYear = academicYear;
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.isActive = isActive;
	}

	public ExamSubjectRuleSettingsBO(int academicYear, int courseId,
			BigDecimal finalPracticalInternalEnteredMaxMark,
			int finalPracticalInternalIsAssignment,
			int finalPracticalInternalIsAttendance,
			int finalPracticalInternalIsSubInternal,
			BigDecimal finalPracticalInternalMaximumMark,
			BigDecimal finalPracticalInternalMinimum_mark,
			BigDecimal finalTheoryInternalEnteredMaxMark,
			int finalTheoryInternalIsAssignment,
			int finalTheoryInternalIsAttendance,
			int finalTheoryInternalIsSubInternal,
			BigDecimal finalTheoryInternalMaximumMark,
			BigDecimal finalTheoryInternalMinimumMark,
			BigDecimal practicalEseEnteredMaxMark,
			int practicalEseIsMultipleAnswerScript,
			int practicalEseIsMultipleEvaluator, int practicalEseIsRegular,
			BigDecimal practicalEseMaximumMark,
			BigDecimal practicalEseMinimumMark,
			BigDecimal practicalEseTheoryFinalMaximumMark,
			BigDecimal practicalEseTheoryFinalMinimumMark, int schemeNo,
			Integer selectBestOfPracticalInternal, Integer selectBestOfTheoryInternal,
			int subjectFinalIsAttendance, int subjectFinalIsInternalExam,
			int subjectFinalIsPracticalExam, int subjectFinalIsTheoryExam,
			BigDecimal subjectFinalMaximum, BigDecimal subjectFinalMinimum,
			BigDecimal subjectFinalValuated, int subjectId,
			BigDecimal theoryEseEnteredMaxMark,
			int theoryEseIsMultipleAnswerScript,
			int theoryEseIsMultipleEvaluator, int theoryEseIsRegular,
			BigDecimal theoryEseMaximumMark, BigDecimal theoryEseMinimumMark,
			BigDecimal theoryEseTheoryFinalMaximumMark,
			BigDecimal theoryEseTheoryFinalMinimumMark, String createdBy,
			Date createdDate, Date lastModifiedDate, String modifiedBy,
			boolean isActive, BigDecimal theoryIntMinMarksTotal,
			BigDecimal theoryIntMaxMarksTotal,
			BigDecimal theoryIntEntryMaxMarksTotal,
			BigDecimal practicalIntMinMarksTotal,
			BigDecimal practicalIntMaxMarksTotal,
			BigDecimal practicalIntEntryMaxMarksTotal) {
		super();
		this.academicYear = academicYear;
		this.courseId = courseId;
		this.finalPracticalInternalEnteredMaxMark = finalPracticalInternalEnteredMaxMark;
		this.finalPracticalInternalIsAssignment = finalPracticalInternalIsAssignment;
		this.finalPracticalInternalIsAttendance = finalPracticalInternalIsAttendance;
		this.finalPracticalInternalIsSubInternal = finalPracticalInternalIsSubInternal;
		this.finalPracticalInternalMaximumMark = finalPracticalInternalMaximumMark;
		this.finalPracticalInternalMinimum_mark = finalPracticalInternalMinimum_mark;
		this.finalTheoryInternalEnteredMaxMark = finalTheoryInternalEnteredMaxMark;
		this.finalTheoryInternalIsAssignment = finalTheoryInternalIsAssignment;
		this.finalTheoryInternalIsAttendance = finalTheoryInternalIsAttendance;
		this.finalTheoryInternalIsSubInternal = finalTheoryInternalIsSubInternal;
		this.finalTheoryInternalMaximumMark = finalTheoryInternalMaximumMark;
		this.finalTheoryInternalMinimumMark = finalTheoryInternalMinimumMark;
		this.practicalEseEnteredMaxMark = practicalEseEnteredMaxMark;
		this.practicalEseIsMultipleAnswerScript = practicalEseIsMultipleAnswerScript;
		this.practicalEseIsMultipleEvaluator = practicalEseIsMultipleEvaluator;
		this.practicalEseIsRegular = practicalEseIsRegular;
		this.practicalEseMaximumMark = practicalEseMaximumMark;
		this.practicalEseMinimumMark = practicalEseMinimumMark;
		this.practicalEseTheoryFinalMaximumMark = practicalEseTheoryFinalMaximumMark;
		this.practicalEseTheoryFinalMinimumMark = practicalEseTheoryFinalMinimumMark;
		this.schemeNo = schemeNo;
		this.selectBestOfPracticalInternal = selectBestOfPracticalInternal;
		this.selectBestOfTheoryInternal = selectBestOfTheoryInternal;
		this.subjectFinalIsAttendance = subjectFinalIsAttendance;
		this.subjectFinalIsInternalExam = subjectFinalIsInternalExam;
		this.subjectFinalIsPracticalExam = subjectFinalIsPracticalExam;
		this.subjectFinalIsTheoryExam = subjectFinalIsTheoryExam;
		this.subjectFinalMaximum = subjectFinalMaximum;
		this.subjectFinalMinimum = subjectFinalMinimum;
		this.subjectFinalValuated = subjectFinalValuated;
		this.subjectId = subjectId;
		this.theoryEseEnteredMaxMark = theoryEseEnteredMaxMark;
		this.theoryEseIsMultipleAnswerScript = theoryEseIsMultipleAnswerScript;
		this.theoryEseIsMultipleEvaluator = theoryEseIsMultipleEvaluator;
		this.theoryEseIsRegular = theoryEseIsRegular;
		this.theoryEseMaximumMark = theoryEseMaximumMark;
		this.theoryEseMinimumMark = theoryEseMinimumMark;
		this.theoryEseTheoryFinalMaximumMark = theoryEseTheoryFinalMaximumMark;
		this.theoryEseTheoryFinalMinimumMark = theoryEseTheoryFinalMinimumMark;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
		this.theoryIntMinMarksTotal = theoryIntMinMarksTotal;
		this.theoryIntMaxMarksTotal = theoryIntMaxMarksTotal;
		this.theoryIntEntryMaxMarksTotal = theoryIntEntryMaxMarksTotal;
		this.practicalIntMinMarksTotal = practicalIntMinMarksTotal;
		this.practicalIntMaxMarksTotal = practicalIntMaxMarksTotal;
		this.practicalIntEntryMaxMarksTotal = practicalIntEntryMaxMarksTotal;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSelectBestOfTheoryInternal() {
		return selectBestOfTheoryInternal;
	}

	public void setSelectBestOfTheoryInternal(Integer selectBestOfTheoryInternal) {
		this.selectBestOfTheoryInternal = selectBestOfTheoryInternal;
	}

	public int getFinalTheoryInternalIsSubInternal() {
		return finalTheoryInternalIsSubInternal;
	}

	public void setFinalTheoryInternalIsSubInternal(
			int finalTheoryInternalIsSubInternal) {
		this.finalTheoryInternalIsSubInternal = finalTheoryInternalIsSubInternal;
	}

	public int getFinalTheoryInternalIsAttendance() {
		return finalTheoryInternalIsAttendance;
	}

	public void setFinalTheoryInternalIsAttendance(
			int finalTheoryInternalIsAttendance) {
		this.finalTheoryInternalIsAttendance = finalTheoryInternalIsAttendance;
	}

	public int getFinalTheoryInternalIsAssignment() {
		return finalTheoryInternalIsAssignment;
	}

	public void setFinalTheoryInternalIsAssignment(
			int finalTheoryInternalIsAssignment) {
		this.finalTheoryInternalIsAssignment = finalTheoryInternalIsAssignment;
	}

	public BigDecimal getFinalTheoryInternalMinimumMark() {
		return finalTheoryInternalMinimumMark;
	}

	public void setFinalTheoryInternalMinimumMark(
			BigDecimal finalTheoryInternalMinimumMark) {
		this.finalTheoryInternalMinimumMark = finalTheoryInternalMinimumMark;
	}

	public BigDecimal getFinalTheoryInternalEnteredMaxMark() {
		return finalTheoryInternalEnteredMaxMark;
	}

	public void setFinalTheoryInternalEnteredMaxMark(
			BigDecimal finalTheoryInternalEnteredMaxMark) {
		this.finalTheoryInternalEnteredMaxMark = finalTheoryInternalEnteredMaxMark;
	}

	public BigDecimal getFinalTheoryInternalMaximumMark() {
		return finalTheoryInternalMaximumMark;
	}

	public void setFinalTheoryInternalMaximumMark(
			BigDecimal finalTheoryInternalMaximumMark) {
		this.finalTheoryInternalMaximumMark = finalTheoryInternalMaximumMark;
	}

	public Integer getSelectBestOfPracticalInternal() {
		return selectBestOfPracticalInternal;
	}

	public void setSelectBestOfPracticalInternal(
			Integer selectBestOfPracticalInternal) {
		this.selectBestOfPracticalInternal = selectBestOfPracticalInternal;
	}

	public int getFinalPracticalInternalIsSubInternal() {
		return finalPracticalInternalIsSubInternal;
	}

	public void setFinalPracticalInternalIsSubInternal(
			int finalPracticalInternalIsSubInternal) {
		this.finalPracticalInternalIsSubInternal = finalPracticalInternalIsSubInternal;
	}

	public int getFinalPracticalInternalIsAttendance() {
		return finalPracticalInternalIsAttendance;
	}

	public void setFinalPracticalInternalIsAttendance(
			int finalPracticalInternalIsAttendance) {
		this.finalPracticalInternalIsAttendance = finalPracticalInternalIsAttendance;
	}

	public int getFinalPracticalInternalIsAssignment() {
		return finalPracticalInternalIsAssignment;
	}

	public void setFinalPracticalInternalIsAssignment(
			int finalPracticalInternalIsAssignment) {
		this.finalPracticalInternalIsAssignment = finalPracticalInternalIsAssignment;
	}

	public BigDecimal getFinalPracticalInternalMinimum_mark() {
		return finalPracticalInternalMinimum_mark;
	}

	public void setFinalPracticalInternalMinimum_mark(
			BigDecimal finalPracticalInternalMinimum_mark) {
		this.finalPracticalInternalMinimum_mark = finalPracticalInternalMinimum_mark;
	}

	public BigDecimal getFinalPracticalInternalEnteredMaxMark() {
		return finalPracticalInternalEnteredMaxMark;
	}

	public void setFinalPracticalInternalEnteredMaxMark(
			BigDecimal finalPracticalInternalEnteredMaxMark) {
		this.finalPracticalInternalEnteredMaxMark = finalPracticalInternalEnteredMaxMark;
	}

	public BigDecimal getFinalPracticalInternalMaximumMark() {
		return finalPracticalInternalMaximumMark;
	}

	public void setFinalPracticalInternalMaximumMark(
			BigDecimal finalPracticalInternalMaximumMark) {
		this.finalPracticalInternalMaximumMark = finalPracticalInternalMaximumMark;
	}

	public int getTheoryEseIsRegular() {
		return theoryEseIsRegular;
	}

	public void setTheoryEseIsRegular(int theoryEseIsRegular) {
		this.theoryEseIsRegular = theoryEseIsRegular;
	}

	public int getTheoryEseIsMultipleAnswerScript() {
		return theoryEseIsMultipleAnswerScript;
	}

	public void setTheoryEseIsMultipleAnswerScript(
			int theoryEseIsMultipleAnswerScript) {
		this.theoryEseIsMultipleAnswerScript = theoryEseIsMultipleAnswerScript;
	}

	public int getTheoryEseIsMultipleEvaluator() {
		return theoryEseIsMultipleEvaluator;
	}

	public void setTheoryEseIsMultipleEvaluator(int theoryEseIsMultipleEvaluator) {
		this.theoryEseIsMultipleEvaluator = theoryEseIsMultipleEvaluator;
	}

	public BigDecimal getTheoryEseMinimumMark() {
		return theoryEseMinimumMark;
	}

	public void setTheoryEseMinimumMark(BigDecimal theoryEseMinimumMark) {
		this.theoryEseMinimumMark = theoryEseMinimumMark;
	}

	public BigDecimal getTheoryEseEnteredMaxMark() {
		return theoryEseEnteredMaxMark;
	}

	public void setTheoryEseEnteredMaxMark(BigDecimal theoryEseEnteredMaxMark) {
		this.theoryEseEnteredMaxMark = theoryEseEnteredMaxMark;
	}

	public BigDecimal getTheoryEseMaximumMark() {
		return theoryEseMaximumMark;
	}

	public void setTheoryEseMaximumMark(BigDecimal theoryEseMaximumMark) {
		this.theoryEseMaximumMark = theoryEseMaximumMark;
	}

	public BigDecimal getTheoryEseTheoryFinalMinimumMark() {
		return theoryEseTheoryFinalMinimumMark;
	}

	public void setTheoryEseTheoryFinalMinimumMark(
			BigDecimal theoryEseTheoryFinalMinimumMark) {
		this.theoryEseTheoryFinalMinimumMark = theoryEseTheoryFinalMinimumMark;
	}

	public BigDecimal getTheoryEseTheoryFinalMaximumMark() {
		return theoryEseTheoryFinalMaximumMark;
	}

	public void setTheoryEseTheoryFinalMaximumMark(
			BigDecimal theoryEseTheoryFinalMaximumMark) {
		this.theoryEseTheoryFinalMaximumMark = theoryEseTheoryFinalMaximumMark;
	}

	public int getPracticalEseIsRegular() {
		return practicalEseIsRegular;
	}

	public void setPracticalEseIsRegular(int practicalEseIsRegular) {
		this.practicalEseIsRegular = practicalEseIsRegular;
	}

	public int getPracticalEseIsMultipleAnswerScript() {
		return practicalEseIsMultipleAnswerScript;
	}

	public void setPracticalEseIsMultipleAnswerScript(
			int practicalEseIsMultipleAnswerScript) {
		this.practicalEseIsMultipleAnswerScript = practicalEseIsMultipleAnswerScript;
	}

	public int getPracticalEseIsMultipleEvaluator() {
		return practicalEseIsMultipleEvaluator;
	}

	public void setPracticalEseIsMultipleEvaluator(
			int practicalEseIsMultipleEvaluator) {
		this.practicalEseIsMultipleEvaluator = practicalEseIsMultipleEvaluator;
	}

	public BigDecimal getPracticalEseMinimumMark() {
		return practicalEseMinimumMark;
	}

	public void setPracticalEseMinimumMark(BigDecimal practicalEseMinimumMark) {
		this.practicalEseMinimumMark = practicalEseMinimumMark;
	}

	public BigDecimal getPracticalEseEnteredMaxMark() {
		return practicalEseEnteredMaxMark;
	}

	public void setPracticalEseEnteredMaxMark(
			BigDecimal practicalEseEnteredMaxMark) {
		this.practicalEseEnteredMaxMark = practicalEseEnteredMaxMark;
	}

	public BigDecimal getPracticalEseMaximumMark() {
		return practicalEseMaximumMark;
	}

	public void setPracticalEseMaximumMark(BigDecimal practicalEseMaximumMark) {
		this.practicalEseMaximumMark = practicalEseMaximumMark;
	}

	public BigDecimal getPracticalEseTheoryFinalMinimumMark() {
		return practicalEseTheoryFinalMinimumMark;
	}

	public void setPracticalEseTheoryFinalMinimumMark(
			BigDecimal practicalEseTheoryFinalMinimumMark) {
		this.practicalEseTheoryFinalMinimumMark = practicalEseTheoryFinalMinimumMark;
	}

	public BigDecimal getPracticalEseTheoryFinalMaximumMark() {
		return practicalEseTheoryFinalMaximumMark;
	}

	public void setPracticalEseTheoryFinalMaximumMark(
			BigDecimal practicalEseTheoryFinalMaximumMark) {
		this.practicalEseTheoryFinalMaximumMark = practicalEseTheoryFinalMaximumMark;
	}

	public int getSubjectFinalIsTheoryExam() {
		return subjectFinalIsTheoryExam;
	}

	public void setSubjectFinalIsTheoryExam(int subjectFinalIsTheoryExam) {
		this.subjectFinalIsTheoryExam = subjectFinalIsTheoryExam;
	}

	public int getSubjectFinalIsPracticalExam() {
		return subjectFinalIsPracticalExam;
	}

	public void setSubjectFinalIsPracticalExam(int subjectFinalIsPracticalExam) {
		this.subjectFinalIsPracticalExam = subjectFinalIsPracticalExam;
	}

	public int getSubjectFinalIsInternalExam() {
		return subjectFinalIsInternalExam;
	}

	public void setSubjectFinalIsInternalExam(int subjectFinalIsInternalExam) {
		this.subjectFinalIsInternalExam = subjectFinalIsInternalExam;
	}

	public int getSubjectFinalIsAttendance() {
		return subjectFinalIsAttendance;
	}

	public void setSubjectFinalIsAttendance(int subjectFinalIsAttendance) {
		this.subjectFinalIsAttendance = subjectFinalIsAttendance;
	}

	public BigDecimal getSubjectFinalMinimum() {
		return subjectFinalMinimum;
	}

	public void setSubjectFinalMinimum(BigDecimal subjectFinalMinimum) {
		this.subjectFinalMinimum = subjectFinalMinimum;
	}

	public BigDecimal getSubjectFinalMaximum() {
		return subjectFinalMaximum;
	}

	public void setSubjectFinalMaximum(BigDecimal subjectFinalMaximum) {
		this.subjectFinalMaximum = subjectFinalMaximum;
	}

	public BigDecimal getSubjectFinalValuated() {
		return subjectFinalValuated;
	}

	public void setSubjectFinalValuated(BigDecimal subjectFinalValuated) {
		this.subjectFinalValuated = subjectFinalValuated;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public BigDecimal getTheoryIntMinMarksTotal() {
		return theoryIntMinMarksTotal;
	}

	public void setTheoryIntMinMarksTotal(BigDecimal theoryIntMinMarksTotal) {
		this.theoryIntMinMarksTotal = theoryIntMinMarksTotal;
	}

	public BigDecimal getTheoryIntMaxMarksTotal() {
		return theoryIntMaxMarksTotal;
	}

	public void setTheoryIntMaxMarksTotal(BigDecimal theoryIntMaxMarksTotal) {
		this.theoryIntMaxMarksTotal = theoryIntMaxMarksTotal;
	}

	public BigDecimal getTheoryIntEntryMaxMarksTotal() {
		return theoryIntEntryMaxMarksTotal;
	}

	public void setTheoryIntEntryMaxMarksTotal(
			BigDecimal theoryIntEntryMaxMarksTotal) {
		this.theoryIntEntryMaxMarksTotal = theoryIntEntryMaxMarksTotal;
	}

	public BigDecimal getPracticalIntMinMarksTotal() {
		return practicalIntMinMarksTotal;
	}

	public void setPracticalIntMinMarksTotal(
			BigDecimal practicalIntMinMarksTotal) {
		this.practicalIntMinMarksTotal = practicalIntMinMarksTotal;
	}

	public BigDecimal getPracticalIntMaxMarksTotal() {
		return practicalIntMaxMarksTotal;
	}

	public void setPracticalIntMaxMarksTotal(
			BigDecimal practicalIntMaxMarksTotal) {
		this.practicalIntMaxMarksTotal = practicalIntMaxMarksTotal;
	}

	public BigDecimal getPracticalIntEntryMaxMarksTotal() {
		return practicalIntEntryMaxMarksTotal;
	}

	public void setPracticalIntEntryMaxMarksTotal(
			BigDecimal practicalIntEntryMaxMarksTotal) {
		this.practicalIntEntryMaxMarksTotal = practicalIntEntryMaxMarksTotal;
	}



	public void setIsActiveInt(int isActiveInt) {
		this.isActiveInt = isActiveInt;
	}



	public int getIsActiveInt() {
		return isActiveInt;
	}

}
