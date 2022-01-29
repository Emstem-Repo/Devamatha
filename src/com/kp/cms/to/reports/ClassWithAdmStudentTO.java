package com.kp.cms.to.reports;

import java.util.List;

public class ClassWithAdmStudentTO {
	private String className;
	private List<AdmittedStudentsReportsTO> students;
	public String getClassName() {
		return className;
	}
	public List<AdmittedStudentsReportsTO> getStudents() {
		return students;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setStudents(List<AdmittedStudentsReportsTO> students) {
		this.students = students;
	}
}
