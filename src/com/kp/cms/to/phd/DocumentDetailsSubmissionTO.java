package com.kp.cms.to.phd;

import java.io.Serializable;
import java.util.Date;

public class DocumentDetailsSubmissionTO implements Serializable {

	private int id;
	private String assignDate;
	private String documentName;
	private String documentId;
	private String tempChecked1;
	private String checked1;
	private String tempChecked2;
	private String checked2;
	private String tempChecked3;
	private String checked3;
	private String tempChecked4;
	private String checked4;
	private String submittedDate;
	private String guideFeeGenerated;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getTempChecked1() {
		return tempChecked1;
	}
	public void setTempChecked1(String tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public String getTempChecked2() {
		return tempChecked2;
	}
	public void setTempChecked2(String tempChecked2) {
		this.tempChecked2 = tempChecked2;
	}
	public String getChecked2() {
		return checked2;
	}
	public void setChecked2(String checked2) {
		this.checked2 = checked2;
	}
	public String getTempChecked3() {
		return tempChecked3;
	}
	public void setTempChecked3(String tempChecked3) {
		this.tempChecked3 = tempChecked3;
	}
	public String getChecked3() {
		return checked3;
	}
	public void setChecked3(String checked3) {
		this.checked3 = checked3;
	}
	public String getTempChecked4() {
		return tempChecked4;
	}
	public void setTempChecked4(String tempChecked4) {
		this.tempChecked4 = tempChecked4;
	}
	public String getChecked4() {
		return checked4;
	}
	public void setChecked4(String checked4) {
		this.checked4 = checked4;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getGuideFeeGenerated() {
		return guideFeeGenerated;
	}
	public void setGuideFeeGenerated(String guideFeeGenerated) {
		this.guideFeeGenerated = guideFeeGenerated;
	}
	
	
}
