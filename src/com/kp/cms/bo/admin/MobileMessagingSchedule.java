package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class MobileMessagingSchedule implements Serializable {
	private int id;
	private String destinationNumber;
	private String messageBody;
	private String sendTime;
	private Integer messagePriority;
	private String senderNumber;
	private String senderName;
	private Date messageEnqueueDate;
	private String messageStatus;
	private String gatewayError;
	private Boolean isMessageSent;
	private Date sendingDate;
	
	public MobileMessagingSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MobileMessagingSchedule(int id, String destinationNumber,
			String messageBody, String sendTime, Integer messagePriority,
			String senderNumber, String senderName, Date messageEnqueueDate,
			String messageStatus, String gatewayError, Boolean isMessageSent, Date sendingDate) {
		super();
		this.id = id;
		this.destinationNumber = destinationNumber;
		this.messageBody = messageBody;
		this.sendTime = sendTime;
		this.messagePriority = messagePriority;
		this.senderNumber = senderNumber;
		this.senderName = senderName;
		this.messageEnqueueDate = messageEnqueueDate;
		this.messageStatus = messageStatus;
		this.gatewayError = gatewayError;
		this.isMessageSent = isMessageSent;
		this.sendingDate = sendingDate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDestinationNumber() {
		return destinationNumber;
	}


	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}


	public String getMessageBody() {
		return messageBody;
	}


	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}


	public String getSendTime() {
		return sendTime;
	}


	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


	public Integer getMessagePriority() {
		return messagePriority;
	}


	public void setMessagePriority(Integer messagePriority) {
		this.messagePriority = messagePriority;
	}


	public String getSenderNumber() {
		return senderNumber;
	}


	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public Date getMessageEnqueueDate() {
		return messageEnqueueDate;
	}


	public void setMessageEnqueueDate(Date messageEnqueueDate) {
		this.messageEnqueueDate = messageEnqueueDate;
	}


	public String getMessageStatus() {
		return messageStatus;
	}


	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}


	public String getGatewayError() {
		return gatewayError;
	}


	public void setGatewayError(String gatewayError) {
		this.gatewayError = gatewayError;
	}


	public Boolean getIsMessageSent() {
		return isMessageSent;
	}


	public void setIsMessageSent(Boolean isMessageSent) {
		this.isMessageSent = isMessageSent;
	}
	public Date getSendingDate() {
		return sendingDate;
	}
	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	
	
	
}
