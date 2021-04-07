package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class ExamStudentAttendanceMarksCjcBO {
	private int id;
	private String attendancePercentage;
	private String attendanceMarks;
	private Student student;
	private Classes classes;
	private Subject subject;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	

	public ExamStudentAttendanceMarksCjcBO(int id, int classId, int subjectId,
			int studentId, String attendancePercentage,
			String attendanceMarks, Student student, Classes classes,
			Subject subject, String createdBy, Date createdDate, 
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id= id;
		this.attendancePercentage = attendancePercentage;
		this.attendanceMarks = attendanceMarks;
		this.student = student;
		this.classes = classes;
		this.subject = subject;
		this.createdBy = createdBy;
		this.lastModifiedDate = lastModifiedDate;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}


	public ExamStudentAttendanceMarksCjcBO() {
		super();
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Classes getClasses() {
		return classes;
	}


	public void setClasses(Classes classes) {
		this.classes = classes;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getAttendanceMarks() {
		return attendanceMarks;
	}


	public void setAttendanceMarks(String attendanceMarks) {
		this.attendanceMarks = attendanceMarks;
	}


	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getAttendancePercentage() {
		return attendancePercentage;
	}


	public void setAttendancePercentage(String attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	

	

}
