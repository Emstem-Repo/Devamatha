package com.kp.cms.to.reports;

import java.util.List;

public class CourseWithStudentUniversityTO {
	private String courseName;
	private String maxIntake;
	private List<StudentForUniversityTO> students;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<StudentForUniversityTO> getStudents() {
		return students;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}
	public void setStudents(List<StudentForUniversityTO> students) {
		this.students = students;
	}
	
}
