package com.kp.cms.bo.exam;

/**
 * Dec 28, 2009 Created By 9Elements Team
 */
import java.util.Date;

@SuppressWarnings("serial")
public class ExamRejoinBO extends ExamGenBO {

	private int studentId;
	private int classId;
	private String oldRegisterNo;
	private String oldRollNo;
	private String newRegisterNo;
	private String remarks;
	private String newRollNo;
	private Date rejoinDate;

	private ClassUtilBO classUtilBO;
	private StudentUtilBO studentUtilBO;

	public ExamRejoinBO() {
		super();
	}

	public ExamRejoinBO(int classId, String newRegisterNo, String newRollNo,
			String oldRegisterNo, String oldRollNo, Date rejoinDate,
			String remarks, int studentId, String userId ) {
		super();
		this.classId = classId;
		this.newRegisterNo = newRegisterNo;
		this.newRollNo = newRollNo;
		this.oldRegisterNo = oldRegisterNo;
		this.oldRollNo = oldRollNo;
		this.rejoinDate = rejoinDate;
		this.remarks = remarks;
		this.studentId = studentId;
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

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getOldRegisterNo() {
		return oldRegisterNo;
	}

	public void setOldRegisterNo(String oldRegisterNo) {
		this.oldRegisterNo = oldRegisterNo;
	}

	public String getOldRollNo() {
		return oldRollNo;
	}

	public void setOldRollNo(String oldRollNo) {
		this.oldRollNo = oldRollNo;
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

}
