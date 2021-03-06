package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class StudentBulkAttendanceSMS implements Serializable {
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
	private Integer classId;
	private Date attendanceDate;
	
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
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	

}
