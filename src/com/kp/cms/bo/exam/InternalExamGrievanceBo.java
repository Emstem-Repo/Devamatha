package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class InternalExamGrievanceBo {
	private int id;
	private String internal1Mark;
	private String internal2Mark;
	private String seminarMark;
	private String assignmentMark;
	private String attendanceMark;
    private String comment;
	private String hodId;
	private Boolean isHodApproved;
	private Boolean isHodRejected;
	private Boolean isHodPending;
	private Boolean isControlerApproved;
	private Boolean isControlerRejected;
	private Boolean isControlerPending;
	private Date createdDate;
	private Date lastModifiedDate;
	private String grievanceSerialNo;
	private String hodRemark;
	private String controlerRemark;
	private String semNo;
	private Student student;
    private Subject subject;
    private Classes classes;
    private ExamDefinition examDefinition;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInternal1Mark() {
		return internal1Mark;
	}
	public void setInternal1Mark(String internal1Mark) {
		this.internal1Mark = internal1Mark;
	}
	public String getInternal2Mark() {
		return internal2Mark;
	}
	public void setInternal2Mark(String internal2Mark) {
		this.internal2Mark = internal2Mark;
	}
	public String getSeminarMark() {
		return seminarMark;
	}
	public void setSeminarMark(String seminarMark) {
		this.seminarMark = seminarMark;
	}
	public String getAssignmentMark() {
		return assignmentMark;
	}
	public void setAssignmentMark(String assignmentMark) {
		this.assignmentMark = assignmentMark;
	}
	public String getAttendanceMark() {
		return attendanceMark;
	}
	public void setAttendanceMark(String attendanceMark) {
		this.attendanceMark = attendanceMark;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getHodId() {
		return hodId;
	}
	public void setHodId(String hodId) {
		this.hodId = hodId;
	}
	public Boolean getIsHodApproved() {
		return isHodApproved;
	}
	public void setIsHodApproved(Boolean isHodApproved) {
		this.isHodApproved = isHodApproved;
	}
	public Boolean getIsHodRejected() {
		return isHodRejected;
	}
	public void setIsHodRejected(Boolean isHodRejected) {
		this.isHodRejected = isHodRejected;
	}
	public Boolean getIsHodPending() {
		return isHodPending;
	}
	public void setIsHodPending(Boolean isHodPending) {
		this.isHodPending = isHodPending;
	}
	public Boolean getIsControlerApproved() {
		return isControlerApproved;
	}
	public void setIsControlerApproved(Boolean isControlerApproved) {
		this.isControlerApproved = isControlerApproved;
	}
	public Boolean getIsControlerRejected() {
		return isControlerRejected;
	}
	public void setIsControlerRejected(Boolean isControlerRejected) {
		this.isControlerRejected = isControlerRejected;
	}
	public Boolean getIsControlerPending() {
		return isControlerPending;
	}
	public void setIsControlerPending(Boolean isControlerPending) {
		this.isControlerPending = isControlerPending;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getGrievanceSerialNo() {
		return grievanceSerialNo;
	}
	public void setGrievanceSerialNo(String grievanceSerialNo) {
		this.grievanceSerialNo = grievanceSerialNo;
	}
	public String getHodRemark() {
		return hodRemark;
	}
	public void setHodRemark(String hodRemark) {
		this.hodRemark = hodRemark;
	}
	public String getControlerRemark() {
		return controlerRemark;
	}
	public void setControlerRemark(String controlerRemark) {
		this.controlerRemark = controlerRemark;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public ExamDefinition getExamDefinition() {
		return examDefinition;
	}
	public void setExamDefinition(ExamDefinition examDefinition) {
		this.examDefinition = examDefinition;
	}
	

}
