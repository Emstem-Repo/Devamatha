package com.kp.cms.to.sap;

public class ExamRegistrationDetailsTo {
	private int id;
	private int sessionId;
	private int venueId;
	private int priorityNo;
	private int capacity;
	private String isHideSession;
	private String sessionName;
	private String examDate;
	private String venueName;
	private String studentName;
	private String invigilatorName;
	private String regNO;
	private String studentMobileNo;
	private String invigilatorMobileNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	
	public int getPriorityNo() {
		return priorityNo;
	}
	public void setPriorityNo(int priorityNo) {
		this.priorityNo = priorityNo;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getIsHideSession() {
		return isHideSession;
	}
	public void setIsHideSession(String isHideSession) {
		this.isHideSession = isHideSession;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getInvigilatorName() {
		return invigilatorName;
	}
	public void setInvigilatorName(String invigilatorName) {
		this.invigilatorName = invigilatorName;
	}
	public String getRegNO() {
		return regNO;
	}
	public void setRegNO(String regNO) {
		this.regNO = regNO;
	}
	public String getStudentMobileNo() {
		return studentMobileNo;
	}
	public void setStudentMobileNo(String studentMobileNo) {
		this.studentMobileNo = studentMobileNo;
	}
	public String getInvigilatorMobileNo() {
		return invigilatorMobileNo;
	}
	public void setInvigilatorMobileNo(String invigilatorMobileNo) {
		this.invigilatorMobileNo = invigilatorMobileNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
}
