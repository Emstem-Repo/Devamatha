package com.kp.cms.to.phd;

import java.io.Serializable;

public class DocumentDetailsTO implements Serializable {
	
	private int id;
	private String documentName;
	private int submissionOrder;
	private String guideFees;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public int getSubmissionOrder() {
		return submissionOrder;
	}
	public void setSubmissionOrder(int submissionOrder) {
		this.submissionOrder = submissionOrder;
	}
	public String getGuideFees() {
		return guideFees;
	}
	public void setGuideFees(String guideFees) {
		this.guideFees = guideFees;
	}
	
	

}
