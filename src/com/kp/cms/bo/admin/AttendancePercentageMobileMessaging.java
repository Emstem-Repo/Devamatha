package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class AttendancePercentageMobileMessaging implements Serializable{
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
	private Integer studentId;
	private Date startDate;
	private Date endDate;
	private Date attendanceDate;
	public AttendancePercentageMobileMessaging()
	{
		super();
	}
	public AttendancePercentageMobileMessaging(int id,String destinationNumber,String messageBody,Integer messagePriority
			,String senderNumber,String senderName,Date messageEnqueueDate,String messageStatus,Integer retryCount,
			String gatewayError,String gatewayGuid,Boolean isMessageSent,Integer studentId,Date startDate,Date endDate,Date attendanceDate)
	{
		this.id = id;
		this.destinationNumber = destinationNumber;
		this.messageBody = messageBody;
		this.messagePriority =  messagePriority;
		this.senderNumber =  senderNumber;
		this.senderName  = senderName;
		this.messageEnqueueDate = messageEnqueueDate;
		this.messageStatus = messageStatus;
		this.retryCount =  retryCount;
		this.gatewayError =  gatewayError;
		this.gatewayGuid = gatewayGuid;
		this.isMessageSent = isMessageSent;
		this.studentId = studentId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.attendanceDate = attendanceDate;
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
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	
}
