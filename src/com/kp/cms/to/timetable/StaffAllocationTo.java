package com.kp.cms.to.timetable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StaffAllocationTo extends TimeTableModuleTo implements
		Serializable {

	private String teachingStaff;
	private int teachingStaffId;
	private int academicYr;
	private String className;
	private String subjectPreference;
	private String academicYear;

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getTeachingStaff() {
		return teachingStaff;
	}

	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}

	public int getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(int academicYr) {
		this.academicYr = academicYr;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubjectPreference() {
		return subjectPreference;
	}

	public void setSubjectPreference(String subjectPreference) {
		this.subjectPreference = subjectPreference;
	}

	public int getTeachingStaffId() {
		return teachingStaffId;
	}

	public void setTeachingStaffId(int teachingStaffId) {
		this.teachingStaffId = teachingStaffId;
	}

}
