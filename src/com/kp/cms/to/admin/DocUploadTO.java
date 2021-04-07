package com.kp.cms.to.admin;

import org.apache.struts.upload.FormFile;

public class DocUploadTO {
	private int id;
	private DocTypeTO docTypeTO;
	private String name;
	private String applicationNo;
	private String regnNo;
	private FormFile document;
	private boolean needToProduce;
	
	public boolean isNeedToProduce() {
		return needToProduce;
	}

	public void setNeedToProduce(boolean needToProduce) {
		this.needToProduce = needToProduce;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DocTypeTO getDocTypeTO() {
		return docTypeTO;
	}

	public void setDocTypeTO(DocTypeTO docTypeTO) {
		this.docTypeTO = docTypeTO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public FormFile getDocument() {
		return document;
	}

	public void setDocument(FormFile document) {
		this.document = document;
	}
}
