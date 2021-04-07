package com.kp.cms.utilities.jms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * Mail data To
 * 
 */
public class MailTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fromName;
	private String fromAddress;
	private String toAddress;
	private String subject;
	private String message;
	private String attachedFile;
	private Map<String,String> messages;
	private String mailBody;
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
	public String getAttachedFile() {
		return attachedFile;
	}
	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}
	public Map<String,String> getMessages() {
		return messages;
	}
	public void setMessages(Map<String,String> messages) {
		this.messages = messages;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
}
