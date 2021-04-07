package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.admin.SubjectTO;

public class HallTicketTo implements Serializable,Comparable<HallTicketTo> {
	private String className ;
	private String registerNo;
	private String studentName;
	private List<SubjectTO> subList;
	private String roomAlloted;
	private String blockNO;
	private String floorNo;
	private String doNotDisplay;
	private String studentPhoto;
	
	private String endTime;
	private String dateofBirth;
	private String semesterNo;
	private String semesterExt;
	private String examName;
	private String courseName;
	private String month;
	private String year;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public List<SubjectTO> getSubList() {
		return subList;
	}
	public void setSubList(List<SubjectTO> subList) {
		this.subList = subList;
	}
	@Override
	public int compareTo(HallTicketTo arg0) {

		if(arg0!=null && this!=null && arg0.getRegisterNo()!=null
				 && this.getRegisterNo()!=null){
				return this.getRegisterNo().compareTo(arg0.getRegisterNo());
		}else
		return 0;
	
	}
	public String getRoomAlloted() {
		return roomAlloted;
	}
	public void setRoomAlloted(String roomAlloted) {
		this.roomAlloted = roomAlloted;
	}
	public String getBlockNO() {
		return blockNO;
	}
	public void setBlockNO(String blockNO) {
		this.blockNO = blockNO;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getDoNotDisplay() {
		return doNotDisplay;
	}
	public void setDoNotDisplay(String doNotDisplay) {
		this.doNotDisplay = doNotDisplay;
	}
	public String getStudentPhoto() {
		return studentPhoto;
	}
	public void setStudentPhoto(String studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getSemesterExt() {
		return semesterExt;
	}
	public void setSemesterExt(String semesterExt) {
		this.semesterExt = semesterExt;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
