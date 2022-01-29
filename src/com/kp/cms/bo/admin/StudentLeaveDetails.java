package com.kp.cms.bo.admin;

/**
 * AttendanceStudent generated by hbm2java
 */
public class StudentLeaveDetails implements java.io.Serializable {

	private int id;
	private Student student;
	private StudentLeave studentLeave;
	

	public StudentLeaveDetails() {
	}

	public StudentLeaveDetails(int id) {
		this.id = id;
	}

	public StudentLeaveDetails(int id, Student student, StudentLeave studentLeave) {
		this.id = id;
		this.student = student;
		this.studentLeave = studentLeave;
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

	/**
	 * @return the studentLeave
	 */
	public StudentLeave getStudentLeave() {
		return studentLeave;
	}

	/**
	 * @param studentLeave the studentLeave to set
	 */
	public void setStudentLeave(StudentLeave studentLeave) {
		this.studentLeave = studentLeave;
	}
	
}
