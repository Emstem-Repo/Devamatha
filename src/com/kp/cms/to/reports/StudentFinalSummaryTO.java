package com.kp.cms.to.reports;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;

public class StudentFinalSummaryTO {

	private Student student;

	private AdmAppln admAppln;

	private int classesPresent;

	private int conductedClasses;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getClassesPresent() {
		return classesPresent;
	}

	public void setClassesPresent(int classesPresent) {
		this.classesPresent = classesPresent;
	}

	public int getConductedClasses() {
		return conductedClasses;
	}

	public void setConductedClasses(int conductedClasses) {
		this.conductedClasses = conductedClasses;
	}

	public AdmAppln getAdmAppln() {
		return admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

}
