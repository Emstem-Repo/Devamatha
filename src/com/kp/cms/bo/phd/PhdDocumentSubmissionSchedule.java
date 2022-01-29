package com.kp.cms.bo.phd;

import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class PhdDocumentSubmissionSchedule implements java.io.Serializable{
	
	private int id;
	private Student studentId;
	private Course courseId;
	private DocumentDetailsBO documentId;
	private Date assignDate;
	private Boolean isReminderMail;
	private Boolean guidesFee;
	private Boolean canSubmitOnline;
	private Boolean submited;
	private Date submittedDate;
	private Date guideFeeGenerated;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public PhdDocumentSubmissionSchedule() {
		super();
	}
	
	public PhdDocumentSubmissionSchedule(int id, Student studentId,Course courseId,
			DocumentDetailsBO documentId,Date assignDate,Boolean isReminderMail,Date submittedDate,
			Date guideFeeGenerated,Boolean guidesFee,Boolean canSubmitOnline,Boolean submited,String createdBy,
			Date createdDate,String modifiedBy,Date lastModifiedDate,Boolean isActive)
	{
		super();
		this.id = id;
		this.studentId=studentId;
		this.courseId=courseId;
		this.documentId=documentId;
		this.assignDate=assignDate;
		this.isReminderMail=isReminderMail;
		this.guidesFee=guidesFee;
		this.canSubmitOnline=canSubmitOnline;
		this.submited=submited;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.submittedDate=submittedDate;
		this.guideFeeGenerated=guideFeeGenerated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public DocumentDetailsBO getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentDetailsBO documentId) {
		this.documentId = documentId;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Boolean getIsReminderMail() {
		return isReminderMail;
	}

	public void setIsReminderMail(Boolean isReminderMail) {
		this.isReminderMail = isReminderMail;
	}

	public Boolean getGuidesFee() {
		return guidesFee;
	}

	public void setGuidesFee(Boolean guidesFee) {
		this.guidesFee = guidesFee;
	}

	public Boolean getCanSubmitOnline() {
		return canSubmitOnline;
	}

	public void setCanSubmitOnline(Boolean canSubmitOnline) {
		this.canSubmitOnline = canSubmitOnline;
	}

	public Boolean getSubmited() {
		return submited;
	}

	public void setSubmited(Boolean submited) {
		this.submited = submited;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getGuideFeeGenerated() {
		return guideFeeGenerated;
	}

	public void setGuideFeeGenerated(Date guideFeeGenerated) {
		this.guideFeeGenerated = guideFeeGenerated;
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

}
