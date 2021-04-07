package com.kp.cms.bo.admin;

public class GenerateMail implements java.io.Serializable{
	
	private int id;
	private String fromName;
	private String fromAddress;
	private String toAddress;
	private String subject;
	private String message;
	private String attachment;
	private String filePath;
	
	
	public GenerateMail() {
		super();
	}
	
	public GenerateMail(String fromName, String fromAddress, String toAddress,
			String subject, String message, String attachment) {
		super();
		this.fromName = fromName;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.subject = subject;
		this.message = message;
		this.attachment = attachment;
	}

	public GenerateMail(int id, String fromName, String fromAddress,
			String toAddress, String subject, String message,String attachment) {
		this.id = id;
		this.fromName = fromName;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.subject = subject;
		this.message = message;
		this.attachment=attachment;
	}
	public GenerateMail(String fromName, String fromAddress, String toAddress,
			String subject, String message, String attachment,String filePath) {
		super();
		this.fromName = fromName;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.subject = subject;
		this.message = message;
		this.attachment = attachment;
		this.filePath = filePath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}