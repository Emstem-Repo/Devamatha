package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class ExamRoomAllotmentDetails implements Serializable{
	private int id;
	private ExamRoomAllotment allotment;
	private Student student;
	private Classes classes;
	private Integer columnNO;
	private Integer rowNO;
	private Integer seatingPosition;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Subject subject;
	public ExamRoomAllotmentDetails(){
		
	}
	
	

	public ExamRoomAllotmentDetails(int id, ExamRoomAllotment allotment,
			Student student, Integer columnNO, Integer rowNO,
			Integer seatingPosition, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,Classes classes,Subject subject) {
		super();
		this.id = id;
		this.allotment = allotment;
		this.student = student;
		this.columnNO = columnNO;
		this.rowNO = rowNO;
		this.seatingPosition = seatingPosition;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.classes=classes;
		this.subject = subject;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamRoomAllotment getAllotment() {
		return allotment;
	}

	public void setAllotment(ExamRoomAllotment allotment) {
		this.allotment = allotment;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getColumnNO() {
		return columnNO;
	}

	public void setColumnNO(Integer columnNO) {
		this.columnNO = columnNO;
	}

	public Integer getRowNO() {
		return rowNO;
	}

	public void setRowNO(Integer rowNO) {
		this.rowNO = rowNO;
	}

	public Integer getSeatingPosition() {
		return seatingPosition;
	}

	public void setSeatingPosition(Integer seatingPosition) {
		this.seatingPosition = seatingPosition;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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



	
		
}
