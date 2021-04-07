package com.kp.cms.bo.phd;

import java.util.Date;

import com.kp.cms.bo.admin.Student;

public class PhdMailBo implements java.io.Serializable{
	
	private int id;
	private Student studentId;
	private DocumentDetailsBO documentId;
	private Date sentDate;
	private Date documentDate;
	private String mailType;
	private String createdBy;
	private Date createdDate;
	
	public PhdMailBo() {
		super();
	}
	
	public PhdMailBo(int id, Student studentId,DocumentDetailsBO documentId,Date sentDate,Date documentDate,
			String mailType,String createdBy,Date createdDate)
	{
		super();
		this.id = id;
		this.studentId=studentId;
		this.documentId=documentId;
		this.sentDate=sentDate;
		this.documentDate=documentDate;
		this.mailType=mailType;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
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

	public DocumentDetailsBO getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentDetailsBO documentId) {
		this.documentId = documentId;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
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

	
}
