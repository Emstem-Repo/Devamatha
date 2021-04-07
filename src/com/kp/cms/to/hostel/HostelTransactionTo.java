package com.kp.cms.to.hostel;

public class HostelTransactionTo {
   private String registerNo;
   private String roomNo;
   private int hlRoomId;
   private String studentName;
   private int studentId;
   private String courseName;
   private int courseId;

public int getHlRoomId() {
	return hlRoomId;
}
public void setHlRoomId(int hlRoomId) {
	this.hlRoomId = hlRoomId;
}
public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public int getCourseId() {
	return courseId;
}
public void setCourseId(int courseId) {
	this.courseId = courseId;
}
public String getRoomNo() {
	return roomNo;
}
public void setRoomNo(String roomNo) {
	this.roomNo = roomNo;
}
public String getRegisterNo() {
	return registerNo;
}
public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
}
}
