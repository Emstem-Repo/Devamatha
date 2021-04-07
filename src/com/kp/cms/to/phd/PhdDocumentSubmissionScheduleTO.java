package com.kp.cms.to.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdDocumentSubmissionScheduleTO implements Serializable {
	private int id;
	private String registerNo;
	private int studentId;
	private String feePaidDate;
	private String studentName;
    private String courseId;
    private String courseName;
	private String documentId;
	private String programTypeId;
	private String assignDate;
	private String isReminderMail;
	private String guidesFee;
	private String canSubmitOnline;
	private String submited;
	private String submittedDate;
	private String guideFeeGenerated;
	private String year;
	private String tempChecked;
	private String checked;
	private String documentName;
	private String editCheck;
	private Date documentAssiDate;
	private String guide;
	private String coGuide;
	private String printornot;
	private String status;
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getRegisterNo() {
		return registerNo;
	}



	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}



	public int getStudentId() {
		return studentId;
	}



	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}



	public String getFeePaidDate() {
		return feePaidDate;
	}



	public void setFeePaidDate(String feePaidDate) {
		this.feePaidDate = feePaidDate;
	}



	public String getStudentName() {
		return studentName;
	}



	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}



	public String getCourseId() {
		return courseId;
	}



	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}



	public String getDocumentId() {
		return documentId;
	}



	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}



	public String getProgramTypeId() {
		return programTypeId;
	}



	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getAssignDate() {
		return assignDate;
	}



	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}



	public String getIsReminderMail() {
		return isReminderMail;
	}



	public void setIsReminderMail(String isReminderMail) {
		this.isReminderMail = isReminderMail;
	}



	public String getGuidesFee() {
		return guidesFee;
	}



	public void setGuidesFee(String guidesFee) {
		this.guidesFee = guidesFee;
	}



	public String getCanSubmitOnline() {
		return canSubmitOnline;
	}



	public void setCanSubmitOnline(String canSubmitOnline) {
		this.canSubmitOnline = canSubmitOnline;
	}



	public String getSubmited() {
		return submited;
	}



	public void setSubmited(String submited) {
		this.submited = submited;
	}



	public String getSubmittedDate() {
		return submittedDate;
	}



	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getTempChecked() {
		return tempChecked;
	}



	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}



	public String getChecked() {
		return checked;
	}



	public void setChecked(String checked) {
		this.checked = checked;
	}



	public String getCourseName() {
		return courseName;
	}



	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}



	public String getDocumentName() {
		return documentName;
	}



	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}



	public String getEditCheck() {
		return editCheck;
	}



	public void setEditCheck(String editCheck) {
		this.editCheck = editCheck;
	}


	public String getGuide() {
		return guide;
	}



	public void setGuide(String guide) {
		this.guide = guide;
	}



	public String getCoGuide() {
		return coGuide;
	}



	public void setCoGuide(String coGuide) {
		this.coGuide = coGuide;
	}



	public String getGuideFeeGenerated() {
		return guideFeeGenerated;
	}



	public void setGuideFeeGenerated(String guideFeeGenerated) {
		this.guideFeeGenerated = guideFeeGenerated;
	}



	public String getPrintornot() {
		return printornot;
	}



	public void setPrintornot(String printornot) {
		this.printornot = printornot;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getDocumentAssiDate() {
		return documentAssiDate;
	}



	public void setDocumentAssiDate(Date documentAssiDate) {
		this.documentAssiDate = documentAssiDate;
	}


}
