package com.kp.cms.bo.admin;

import com.kp.cms.bo.exam.ExamGenBO;

@SuppressWarnings("serial")
public class DurationAllocationBo extends ExamGenBO {

	private int academicYearId;
	private int academicYear;
	private Course courseId;
	private Subject subjectId;
	private CourseScheme courseSchemeId;
	private String minimumLectureHours;
	private String maximumLectureHours;

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

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public Subject getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Subject subjectId) {
		this.subjectId = subjectId;
	}

	public CourseScheme getCourseSchemeId() {
		return courseSchemeId;
	}

	public void setCourseSchemeId(CourseScheme courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}

	public String getMinimumLectureHours() {
		return minimumLectureHours;
	}

	public void setMinimumLectureHours(String minimumLectureHours) {
		this.minimumLectureHours = minimumLectureHours;
	}

	public String getMaximumLectureHours() {
		return maximumLectureHours;
	}

	public void setMaximumLectureHours(String maximumLectureHours) {
		this.maximumLectureHours = maximumLectureHours;
	}

}
