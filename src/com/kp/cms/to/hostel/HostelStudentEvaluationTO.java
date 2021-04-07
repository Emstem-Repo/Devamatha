package com.kp.cms.to.hostel;

import java.util.List;
import java.util.Map;

public class HostelStudentEvaluationTO {
	
	private String studentName;
	private String course;
	private String registerNo;
	private String roomNo;
	private String attendancePercentage;
	private String maxMarks;
	private String marksObtained;
	private String totalPercentageObtained;
	private String result;
	private String disciplinaryType;
	private List<DisciplinaryDetailsTO> disciplinaryDetails;// for heading
	private List<StudentDisciplinaryTypeTO> studentDisciplinaryTypes;// for listing for the student
	private Map<Integer,Integer> studentDisciplinaryMap;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	public String getAttendancePercentage() {
		return attendancePercentage;
	}
	public void setAttendancePercentage(String attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getTotalPercentageObtained() {
		return totalPercentageObtained;
	}
	public void setTotalPercentageObtained(String totalPercentageObtained) {
		this.totalPercentageObtained = totalPercentageObtained;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getDisciplinaryType() {
		return disciplinaryType;
	}
	public void setDisciplinaryType(String disciplinaryType) {
		this.disciplinaryType = disciplinaryType;
	}
	public List<DisciplinaryDetailsTO> getDisciplinaryDetails() {
		return disciplinaryDetails;
	}
	public void setDisciplinaryDetails(
			List<DisciplinaryDetailsTO> disciplinaryDetails) {
		this.disciplinaryDetails = disciplinaryDetails;
	}
	public List<StudentDisciplinaryTypeTO> getStudentDisciplinaryTypes() {
		return studentDisciplinaryTypes;
	}
	public void setStudentDisciplinaryTypes(
			List<StudentDisciplinaryTypeTO> studentDisciplinaryTypes) {
		this.studentDisciplinaryTypes = studentDisciplinaryTypes;
	}
	public Map<Integer, Integer> getStudentDisciplinaryMap() {
		return studentDisciplinaryMap;
	}
	public void setStudentDisciplinaryMap(
			Map<Integer, Integer> studentDisciplinaryMap) {
		this.studentDisciplinaryMap = studentDisciplinaryMap;
	}	

}
