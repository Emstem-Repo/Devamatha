package com.kp.cms.to.phd;

import java.io.Serializable;

public class PhdGuideRemunerationDetailsTO implements Serializable{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String guideRemunerationId;
	private String documentId;
	private String amount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuideRemunerationId() {
		return guideRemunerationId;
	}
	public void setGuideRemunerationId(String guideRemunerationId) {
		this.guideRemunerationId = guideRemunerationId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
		
}
