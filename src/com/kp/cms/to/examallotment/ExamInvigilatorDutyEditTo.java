package com.kp.cms.to.examallotment;

import java.util.Map;

public class ExamInvigilatorDutyEditTo {
private int id;
private String date;
private String session;
private String examinerType;
private String facultyId;
private String roomNo;
private Map<Integer,String> facultyMap;
private Map<Integer,String> roomNoMap;
private Map<String,String> examinerTypeMap;
private Map<Integer,String> sessionMap;
private String checked;
private String flag;
private String hiddenDate;
private String hiddenSession;
private String hiddenExaminerType;
private String hiddenFacultyId;
private String hiddenRoomNo;
private String tempChecked;


public String getTempChecked() {
	return tempChecked;
}
public void setTempChecked(String tempChecked) {
	this.tempChecked = tempChecked;
}
public String getHiddenDate() {
	return hiddenDate;
}
public void setHiddenDate(String hiddenDate) {
	this.hiddenDate = hiddenDate;
}
public String getHiddenSession() {
	return hiddenSession;
}
public void setHiddenSession(String hiddenSession) {
	this.hiddenSession = hiddenSession;
}
public String getHiddenExaminerType() {
	return hiddenExaminerType;
}
public void setHiddenExaminerType(String hiddenExaminerType) {
	this.hiddenExaminerType = hiddenExaminerType;
}
public String getHiddenFacultyId() {
	return hiddenFacultyId;
}
public void setHiddenFacultyId(String hiddenFacultyId) {
	this.hiddenFacultyId = hiddenFacultyId;
}
public String getHiddenRoomNo() {
	return hiddenRoomNo;
}
public void setHiddenRoomNo(String hiddenRoomNo) {
	this.hiddenRoomNo = hiddenRoomNo;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
public String getChecked() {
	return checked;
}
public void setChecked(String checked) {
	this.checked = checked;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getSession() {
	return session;
}
public void setSession(String session) {
	this.session = session;
}
public String getExaminerType() {
	return examinerType;
}
public void setExaminerType(String examinerType) {
	this.examinerType = examinerType;
}
public String getFacultyId() {
	return facultyId;
}
public void setFacultyId(String facultyId) {
	this.facultyId = facultyId;
}
public String getRoomNo() {
	return roomNo;
}
public void setRoomNo(String roomNo) {
	this.roomNo = roomNo;
}
public Map<Integer, String> getFacultyMap() {
	return facultyMap;
}
public void setFacultyMap(Map<Integer, String> facultyMap) {
	this.facultyMap = facultyMap;
}
public Map<Integer, String> getRoomNoMap() {
	return roomNoMap;
}
public void setRoomNoMap(Map<Integer, String> roomNoMap) {
	this.roomNoMap = roomNoMap;
}
public Map<String, String> getExaminerTypeMap() {
	return examinerTypeMap;
}
public void setExaminerTypeMap(Map<String, String> examinerTypeMap) {
	this.examinerTypeMap = examinerTypeMap;
}
public Map<Integer, String> getSessionMap() {
	return sessionMap;
}
public void setSessionMap(Map<Integer, String> sessionMap) {
	this.sessionMap = sessionMap;
}




}
