package com.kp.cms.bo.admin;

import java.util.Date;

public class BulkSMStoEmployeeMobileMessaging {
	private int id;
	private String destinationNumber;
	private String messageBody;
	private Integer messagePriority;
	private String senderNumber;
	private String senderName;
	private Date messageEnqueueDate;
	private String messageStatus;
	private Integer retryCount;
	private String gatewayError;
	private String gatewayGuid;
	private Boolean isMessageSent;
	private Date smsDate;
	private Integer userId;
	private Integer rollid;
	
	public BulkSMStoEmployeeMobileMessaging()
	{
		super();
	}
	public BulkSMStoEmployeeMobileMessaging(int id,String destinationNumber,String messageBody,Integer messagePriority,String senderNumber
			,String senderName,Date messageEnqueueDate,String messageStatus,Integer retryCount,String gatewayError,String gatewayGuid,
			Boolean isMessageSent,Integer userId,Integer rollid, Date smsDate)
	{
		this.id =  id;
		this.destinationNumber = destinationNumber;
		this.messageBody = messageBody;
		this.messagePriority = messagePriority;
		this.senderNumber = senderNumber;
		this.senderName = senderName;
		this.messageEnqueueDate = messageEnqueueDate;
		this.messageStatus = messageStatus;
		this.retryCount = retryCount;
		this.gatewayError = gatewayError;
		this.gatewayGuid = gatewayGuid;
		this.isMessageSent = isMessageSent;
		this.userId = userId;
		this.rollid = rollid;
		this.smsDate = smsDate;
		
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
	public Integer getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}
	public String getGatewayError() {
		return gatewayError;
	}
	public void setGatewayError(String gatewayError) {
		this.gatewayError = gatewayError;
	}
	public String getGatewayGuid() {
		return gatewayGuid;
	}
	public void setGatewayGuid(String gatewayGuid) {
		this.gatewayGuid = gatewayGuid;
	}
	public Boolean getIsMessageSent() {
		return isMessageSent;
	}
	public void setIsMessageSent(Boolean isMessageSent) {
		this.isMessageSent = isMessageSent;
	}
	public Date getSmsDate() {
		return smsDate;
	}
	public void setSmsDate(Date smsDate) {
		this.smsDate = smsDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRollid() {
		return rollid;
	}
	public void setRollid(Integer rollid) {
		this.rollid = rollid;
	}
	
}
