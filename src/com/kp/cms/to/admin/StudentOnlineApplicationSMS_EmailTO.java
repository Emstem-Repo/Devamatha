package com.kp.cms.to.admin;

public class StudentOnlineApplicationSMS_EmailTO {
	private int id;
	private int studentId;
	private String emailSubject;
	private String emailMessage;
	private String sendingDate;
	private String emailToAddress;
	private String smsMessageBody;
	private String mobileNumber;
	private int applnNo;
	private int count ;
	private String displayMsg;
	private String flag="false";
//	setter & getter methods.
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
	public String getSendingDate() {
		return sendingDate;
	}
	public void setSendingDate(String sendingDate) {
		this.sendingDate = sendingDate;
	}
	public String getEmailToAddress() {
		return emailToAddress;
	}
	public void setEmailToAddress(String emailToAddress) {
		this.emailToAddress = emailToAddress;
	}
	public String getSmsMessageBody() {
		return smsMessageBody;
	}
	public void setSmsMessageBody(String smsMessageBody) {
		this.smsMessageBody = smsMessageBody;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}
	public int getApplnNo() {
		return applnNo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDisplayMsg() {
		return displayMsg;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
