package com.kp.cms.bo.admin;

// Generated Apr 22, 2009 7:09:12 PM by Hibernate Tools 3.2.0.b9

import java.util.HashSet;
import java.util.Set;

/**
 * ClassSchemewise generated by hbm2java
 */
public class ClassSchemewise implements java.io.Serializable {

	private int id;
	private Classes classes;
	private CurriculumSchemeDuration curriculumSchemeDuration;
	private Set<Period> periods = new HashSet<Period>(0);
	private Set<Student> students = new HashSet<Student>(0);
	private Set<AttendanceClass> attendanceClasses = new HashSet<AttendanceClass>(
			0);
	private Set<Batch> batchs = new HashSet<Batch>(0);
	private Set<StuCocurrLeave> stuCocurrLeaves = new HashSet<StuCocurrLeave>(0);
	public ClassSchemewise() {
	}

	public ClassSchemewise(int id) {
		this.id = id;
	}

	public ClassSchemewise(int id, Classes classes,
			CurriculumSchemeDuration curriculumSchemeDuration,
			Set<Period> periods, Set<Student> students,
			Set<AttendanceClass> attendanceClasses, Set<Batch> batchs,Set<StuCocurrLeave> stuCocurrLeaves) {
		this.id = id;
		this.classes = classes;
		this.curriculumSchemeDuration = curriculumSchemeDuration;
		this.periods = periods;
		this.students = students;
		this.attendanceClasses = attendanceClasses;
		this.batchs = batchs;
		this.stuCocurrLeaves = stuCocurrLeaves;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Classes getClasses() {
		return this.classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public CurriculumSchemeDuration getCurriculumSchemeDuration() {
		return this.curriculumSchemeDuration;
	}

	public void setCurriculumSchemeDuration(
			CurriculumSchemeDuration curriculumSchemeDuration) {
		this.curriculumSchemeDuration = curriculumSchemeDuration;
	}

	public Set<Period> getPeriods() {
		return this.periods;
	}

	public void setPeriods(Set<Period> periods) {
		this.periods = periods;
	}

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<AttendanceClass> getAttendanceClasses() {
		return this.attendanceClasses;
	}

	public void setAttendanceClasses(Set<AttendanceClass> attendanceClasses) {
		this.attendanceClasses = attendanceClasses;
	}

	public Set<Batch> getBatchs() {
		return this.batchs;
	}

	public void setBatchs(Set<Batch> batchs) {
		this.batchs = batchs;
	}
	public Set<StuCocurrLeave> getStuCocurrLeaves() {
		return this.stuCocurrLeaves;
	}

	public void setStuCocurrLeaves(Set<StuCocurrLeave> stuCocurrLeaves) {
		this.stuCocurrLeaves = stuCocurrLeaves;
	}

}
