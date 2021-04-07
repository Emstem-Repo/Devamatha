package com.kp.cms.to.admin;

import java.io.Serializable;

import com.kp.cms.bo.admin.CertificateDetails;

public class CertificateDetailsTemplateTO implements Serializable{
	private int id;
	private String templateName;
	private String templateDescription;
	private CertificateDetails certificateId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public CertificateDetails getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(CertificateDetails certificateId) {
		this.certificateId = certificateId;
	}
	
}
