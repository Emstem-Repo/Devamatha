package com.kp.cms.to.reports;

import java.util.List;

public class ClasswithStudentTO {
	private String className;
	private List<ListofRollRegNoReportTO> students;
	private List<ClassStudentListTO> studentList;
	
	public String getClassName() {
		return className;
	}
	public List<ListofRollRegNoReportTO> getStudents() {
		return students;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setStudents(List<ListofRollRegNoReportTO> students) {
		this.students = students;
	}
	public List<ClassStudentListTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<ClassStudentListTO> studentList) {
		this.studentList = studentList;
	}
	
}
