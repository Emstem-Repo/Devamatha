package com.kp.cms.utilities.jms;

import java.util.Date;

public class SMS_Message {
	public static final String DEST_NO = "MobileNo";
	public static final String MESSAGE = "Message";
	public static final String SENDER_NUMBER = "CDMAHeader";
	public static final String SENDER_NAME = "SenderID";
	public static final int MESSAGE_SUCCESS = 0;
	public static final int MESSAGE_FAIL = 1;
	public static final int MESSAGE_RETRY = 2;
	//added by chandra
	public static final String TO_NO = "to";
	public static final String NEWMESSAGE_NO = "message";

	String destination_number; // MobileNo
	String message_body; // Message
	String message_priority;
	String sender_number; // CDMA Header
	String sender_name; // SenderID
	private Date messageEnqueueDate;

	// Internal
	int message_id;
	int message_status;

	// From sms gateway
	String sms_guid;
	String sms_gateway_response;

	public Date sendingDate;
	private String sendTime;

	//raghu
	private Integer studentId;
	private Date attendanceDate;
	private Date startDate;
	private Date endDate;
	private Integer classId;
	private boolean isStudent;
	
	/**
	 * @return the message_id
	 */
	public int getMessage_id() {
		return message_id;
	}

	/**
	 * @param messageId
	 *            the message_id to set
	 */
	public void setMessage_id(int messageId) {
		message_id = messageId;
	}

	/**
	 * @return the message_status
	 */
	public int getMessage_status() {
		return message_status;
	}

	/**
	 * @param messageStatus
	 *            the message_status to set
	 */
	public void setMessage_status(int messageStatus) {
		message_status = messageStatus;
	}

	/**
	 * @return the sms_guid
	 */
	public String getSms_guid() {
		return sms_guid;
	}

	/**
	 * @param smsGuid
	 *            the sms_guid to set
	 */
	public void setSms_guid(String smsGuid) {
		sms_guid = smsGuid;
	}

	/**
	 * @return the sms_gateway_response
	 */
	public String getSms_gateway_response() {
		return sms_gateway_response;
	}

	/**
	 * @param smsGatewayResponse
	 *            the sms_gateway_response to set
	 */
	public void setSms_gateway_response(String smsGatewayResponse) {
		sms_gateway_response = smsGatewayResponse;
	}

	/**
	 * @return the destination_number
	 */
	public String getDestination_number() {
		return destination_number;
	}

	/**
	 * @param destinationNumber
	 *            the destination_number to set
	 */
	public void setDestination_number(String destinationNumber) {
		destination_number = destinationNumber;
	}

	/**
	 * @return the message_body
	 */
	public String getMessage_body() {
		return message_body;
	}

	/**
	 * @param messageBody
	 *            the message_body to set
	 */
	public void setMessage_body(String messageBody) {
		message_body = messageBody;
	}

	/**
	 * @return the message_priority
	 */
	public String getMessage_priority() {
		return message_priority;
	}

	/**
	 * @param messagePriority
	 *            the message_priority to set
	 */
	public void setMessage_priority(String messagePriority) {
		message_priority = messagePriority;
	}

	/**
	 * @return the sender_number
	 */
	public String getSender_number() {
		return sender_number;
	}

	/**
	 * @param senderNumber
	 *            the sender_number to set
	 */
	public void setSender_number(String senderNumber) {
		sender_number = senderNumber;
	}

	/**
	 * @return the sender_name
	 */
	public String getSender_name() {
		return sender_name;
	}

	/**
	 * @param senderName
	 *            the sender_name to set
	 */
	public void setSender_name(String senderName) {
		sender_name = senderName;
	}

	public Date getMessageEnqueueDate() {
		return messageEnqueueDate;
	}

	public void setMessageEnqueueDate(Date messageEnqueueDate) {
		this.messageEnqueueDate = messageEnqueueDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SMS_Message [destination_number=" + destination_number
				+ ", message_body=" + message_body + ", message_id="
				+ message_id + ", message_priority=" + message_priority
				+ ", message_status=" + message_status + ", sender_name="
				+ sender_name + ", sender_number=" + sender_number
				+ ", sms_gateway_response=" + sms_gateway_response
				+ ", sms_guid=" + sms_guid + "]";
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
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

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	
	
}
