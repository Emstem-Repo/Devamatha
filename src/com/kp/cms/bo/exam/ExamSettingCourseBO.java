package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class ExamSettingCourseBO extends ExamGenBO {

	private String improvement;
	private String supplementaryForFailedSubject;

	private BigDecimal minReqAttendanceWithoutFine;
	private BigDecimal minReqAttendanceWithFine;

	private BigDecimal aggregatePass;
	private BigDecimal individualPass;
	private int courseId;

	private ExamCourseUtilBO course;
	private Set<ExamSettingCourseRevaluationBO> examSettingCourseRevaluationSet;

	public ExamSettingCourseBO() {
		super();
	}

	public ExamSettingCourseBO(int id, int courseId, String improvement,
			BigDecimal individualPass, BigDecimal aggregatePass,
			BigDecimal minReqAttendanceWithFine,
			BigDecimal minReqAttendanceWithoutFine,
			String supplementaryForFailedSubject, String userId) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.aggregatePass = aggregatePass;
		this.individualPass = individualPass;
		this.minReqAttendanceWithFine = minReqAttendanceWithFine;
		this.minReqAttendanceWithoutFine = minReqAttendanceWithoutFine;
		this.improvement = improvement;
		this.supplementaryForFailedSubject = supplementaryForFailedSubject;

		this.createdBy = userId;
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		this.modifiedBy = userId;
		this.isActive = true;
	}

	public ExamSettingCourseBO(int id, String improvement,
			BigDecimal individualPass, BigDecimal aggregatePass,
			BigDecimal minReqAttendanceWithFine,
			BigDecimal minReqAttendanceWithoutFine,
			String supplementaryForFailedSubject, String userId) {
		super();
		this.id = id;
		this.aggregatePass = aggregatePass;
		this.individualPass = individualPass;
		this.minReqAttendanceWithFine = minReqAttendanceWithFine;
		this.minReqAttendanceWithoutFine = minReqAttendanceWithoutFine;
		this.improvement = improvement;
		this.supplementaryForFailedSubject = supplementaryForFailedSubject;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		this.modifiedBy = userId;
		this.isActive = true;
	}

	public Set<ExamSettingCourseRevaluationBO> getExamSettingCourseRevaluationSet() {
		return examSettingCourseRevaluationSet;
	}

	public void setExamSettingCourseRevaluationSet(
			Set<ExamSettingCourseRevaluationBO> examSettingCourseRevaluationSet) {
		this.examSettingCourseRevaluationSet = examSettingCourseRevaluationSet;
	}

	public String getImprovement() {
		return improvement;
	}

	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}

	public String getSupplementaryForFailedSubject() {
		return supplementaryForFailedSubject;
	}

	public void setSupplementaryForFailedSubject(
			String supplementaryForFailedSubject) {
		this.supplementaryForFailedSubject = supplementaryForFailedSubject;
	}

	public int getCourseId() {
		return courseId;
	}

	public BigDecimal getMinReqAttendanceWithoutFine() {
		return minReqAttendanceWithoutFine;
	}

	public void setMinReqAttendanceWithoutFine(
			BigDecimal minReqAttendanceWithoutFine) {
		this.minReqAttendanceWithoutFine = minReqAttendanceWithoutFine;
	}

	public BigDecimal getMinReqAttendanceWithFine() {
		return minReqAttendanceWithFine;
	}

	public void setMinReqAttendanceWithFine(BigDecimal minReqAttendanceWithFine) {
		this.minReqAttendanceWithFine = minReqAttendanceWithFine;
	}

	public BigDecimal getAggregatePass() {
		return aggregatePass;
	}

	public void setAggregatePass(BigDecimal aggregatePass) {
		this.aggregatePass = aggregatePass;
	}

	public BigDecimal getIndividualPass() {
		return individualPass;
	}

	public void setIndividualPass(BigDecimal individualPass) {
		this.individualPass = individualPass;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public ExamCourseUtilBO getCourse() {
		return course;
	}

	public void setCourse(ExamCourseUtilBO course) {
		this.course = course;
	}
}
