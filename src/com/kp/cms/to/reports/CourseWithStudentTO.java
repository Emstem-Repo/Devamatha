package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.List;

public class CourseWithStudentTO implements Serializable{
	
	private String courseName;
	private List<SecondLanguageReportTO> students;
	
	public String getCourseName() {
		return courseName;
	}
	public List<SecondLanguageReportTO> getStudents() {
		return students;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setStudents(List<SecondLanguageReportTO> students) {
		this.students = students;
	}

}
