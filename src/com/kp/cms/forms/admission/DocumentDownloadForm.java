package com.kp.cms.forms.admission;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class DocumentDownloadForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile document;
	private String documentId;	

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public FormFile getDocument() {
		return document;
	}

	public void setDocument(FormFile document) {
		this.document = document;
	}
}
