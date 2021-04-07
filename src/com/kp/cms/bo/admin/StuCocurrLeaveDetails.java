package com.kp.cms.bo.admin;

// Generated Aug 31, 2009 5:57:07 PM by Hibernate Tools 3.2.0.b9

/**
 * StuCocurrLeaveDetails generated by hbm2java
 */
public class StuCocurrLeaveDetails implements java.io.Serializable {

	private int id;
	private Student student;
	private StuCocurrLeave stuCocurrLeave;

	public StuCocurrLeaveDetails() {
	}

	public StuCocurrLeaveDetails(int id) {
		this.id = id;
	}

	public StuCocurrLeaveDetails(int id, Student student,
			StuCocurrLeave stuCocurrLeave) {
		this.id = id;
		this.student = student;
		this.stuCocurrLeave = stuCocurrLeave;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StuCocurrLeave getStuCocurrLeave() {
		return this.stuCocurrLeave;
	}

	public void setStuCocurrLeave(StuCocurrLeave stuCocurrLeave) {
		this.stuCocurrLeave = stuCocurrLeave;
	}

}