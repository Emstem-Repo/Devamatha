package com.kp.cms.to.exam;

public class DownloadUploadedFileTO {
	private String fileName;
	private String orgFileName;
	public String getOrgFileName() {
		return orgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
