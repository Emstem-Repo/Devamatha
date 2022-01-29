package com.kp.cms.bo.exam;

import java.util.Date;

public class ExamStudentRejoinBO extends ExamGenBO {

	private int studentId;
	private Integer classId;
	private String newRegisterNo;
	private String newRollNo;
	private Date rejoinDate;
	private String remarks;
	private String batch;
	private ClassUtilBO classUtilBO;
	private StudentUtilBO studentUtilBO;

	public ExamStudentRejoinBO() {
		super();
	}

	public ExamStudentRejoinBO(int studentId, Integer classId,
			String newRegisterNo, String newRollNo, Date rejoinDate,
			String remarks, String batch, String userId) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.newRegisterNo = newRegisterNo;
		this.newRollNo = newRollNo;
		this.rejoinDate = rejoinDate;
		this.remarks = remarks;
		this.batch = batch;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getNewRegisterNo() {
		return newRegisterNo;
	}

	public void setNewRegisterNo(String newRegisterNo) {
		this.newRegisterNo = newRegisterNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNewRollNo() {
		return newRollNo;
	}

	public void setNewRollNo(String newRollNo) {
		this.newRollNo = newRollNo;
	}

	public Date getRejoinDate() {
		return rejoinDate;
	}

	public void setRejoinDate(Date rejoinDate) {
		this.rejoinDate = rejoinDate;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBatch() {
		return batch;
	}

}
