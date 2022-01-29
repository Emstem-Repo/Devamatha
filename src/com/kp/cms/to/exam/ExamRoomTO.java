package com.kp.cms.to.exam;

import java.util.ArrayList;
import java.util.Map;

public class ExamRoomTO {
	
private int examNameId;
private String examName;
private String date;
private String time;
private int roomCapacity;
private int alloted;
private int available;
private int roomNo;
private String comments;

private Map<Integer, String> classesList;
private String[] subjectIds;



public int getExamNameId() {
	return examNameId;
}
public void setExamNameId(int examNameId) {
	this.examNameId = examNameId;
}
public String getExamName() {
	return examName;
}
public void setExamName(String examName) {
	this.examName = examName;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getRoomCapacity() {
	return roomCapacity;
}
public void setRoomCapacity(int roomCapacity) {
	this.roomCapacity = roomCapacity;
}
public int getAlloted() {
	return alloted;
}
public void setAlloted(int alloted) {
	this.alloted = alloted;
}
public int getAvailable() {
	return available;
}
public void setAvailable(int available) {
	this.available = available;
}
public int getRoomNo() {
	return roomNo;
}
public void setRoomNo(int roomNo) {
	this.roomNo = roomNo;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public Map<Integer, String> getClassesList() {
	return classesList;
}
public void setClassesList(Map<Integer, String> classesList) {
	this.classesList = classesList;
}
public void setSubjectIds(String[] subjectIds) {
	this.subjectIds = subjectIds;
}
public String[] getSubjectIds() {
	return subjectIds;
}


}
