package com.kp.cms.bo.admin;

import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;

@SuppressWarnings("serial")
public class TimeAllocationBo extends ExamGenBO {

	private int academicYearId;
	private int academicYear;
	private Subject preferredSubjectId;
	private Employee teachingStaffId;
	private ClassSchemewise classId;
	private Integer subjectType;
	private Batch batchId;
	private Period periodId;
	private ExamRoomMasterBO roomId;
	private int day;

	public int getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(int academicYearId) {
		this.academicYearId = academicYearId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public Subject getPreferredSubjectId() {
		return preferredSubjectId;
	}

	public void setPreferredSubjectId(Subject preferredSubjectId) {
		this.preferredSubjectId = preferredSubjectId;
	}

	public Employee getTeachingStaffId() {
		return teachingStaffId;
	}

	public void setTeachingStaffId(Employee teachingStaffId) {
		this.teachingStaffId = teachingStaffId;
	}

	public ClassSchemewise getClassId() {
		return classId;
	}

	public void setClassId(ClassSchemewise classId) {
		this.classId = classId;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Batch getBatchId() {
		return batchId;
	}

	public void setBatchId(Batch batchId) {
		this.batchId = batchId;
	}

	public Period getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Period periodId) {
		this.periodId = periodId;
	}

	public ExamRoomMasterBO getRoomId() {
		return roomId;
	}

	public void setRoomId(ExamRoomMasterBO roomId) {
		this.roomId = roomId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
