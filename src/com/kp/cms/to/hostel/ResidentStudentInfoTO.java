package com.kp.cms.to.hostel;

import java.util.List;

public class ResidentStudentInfoTO {
	private String hostelType;
	private String roomType;
	private String applNo;
	private String studentName;
	private List<DisciplinaryTypeTO> disciplinaryToList;
	private List<LeaveTypeTo> leaveList;
	private List<HlAttendanceTO> list;
	private int courseId;
	private int year;
	private int studentId;
	private int hostelApplnId;
	private String applNo1;
	public String getApplNo1() {
		return applNo1;
	}
	public void setApplNo1(String applNo1) {
		this.applNo1 = applNo1;
	}
	public int getHostelApplnId() {
		return hostelApplnId;
	}
	public void setHostelApplnId(int hostelApplnId) {
		this.hostelApplnId = hostelApplnId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public List<HlAttendanceTO> getList() {
		return list;
	}
	public void setList(List<HlAttendanceTO> list) {
		this.list = list;
	}
	public List<DisciplinaryTypeTO> getDisciplinaryToList() {
		return disciplinaryToList;
	}
	public void setDisciplinaryToList(List<DisciplinaryTypeTO> disciplinaryToList) {
		this.disciplinaryToList = disciplinaryToList;
	}
	public String getHostelType() {
		return hostelType;
	}
	public void setHostelType(String hostelType) {
		this.hostelType = hostelType;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public List<LeaveTypeTo> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(List<LeaveTypeTo> leaveList) {
		this.leaveList = leaveList;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
