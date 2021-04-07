package com.kp.cms.bo.admin;

import com.kp.cms.bo.exam.ExamGenBO;

@SuppressWarnings("serial")
public class StaffAllocationBo extends ExamGenBO {

	private int academicYearId;
	private int academicYear;
	private ClassSchemewise classSchemeWise;
	private Subject preferredSubjectId;
	private Employee teachingStaffId;

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

	public ClassSchemewise getClassSchemeWise() {
		return classSchemeWise;
	}

	public void setClassSchemeWise(ClassSchemewise classSchemeWise) {
		this.classSchemeWise = classSchemeWise;
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

}
