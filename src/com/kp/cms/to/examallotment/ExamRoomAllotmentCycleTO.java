package com.kp.cms.to.examallotment;

public class ExamRoomAllotmentCycleTO {
private int id;
private String midOrEndSem;
private String cycleName;
private String sessionName;
private String schemeNo;
private String[] courseIds;
private String sessionId;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMidOrEndSem() {
	return midOrEndSem;
}
public void setMidOrEndSem(String midOrEndSem) {
	this.midOrEndSem = midOrEndSem;
}
public String getCycleName() {
	return cycleName;
}
public void setCycleName(String cycleName) {
	this.cycleName = cycleName;
}
public String getSessionName() {
	return sessionName;
}
public void setSessionName(String sessionName) {
	this.sessionName = sessionName;
}
public String getSchemeNo() {
	return schemeNo;
}
public void setSchemeNo(String schemeNo) {
	this.schemeNo = schemeNo;
}
public String[] getCourseIds() {
	return courseIds;
}
public void setCourseIds(String[] courseIds) {
	this.courseIds = courseIds;
}
public String getSessionId() {
	return sessionId;
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
}
