package com.kp.cms.to.reports;

import java.util.List;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.to.admission.InterviewSubroundsTO;

public class ScoreSheetTO {
	private String applicationId;
	private String applicationNo;
	private String applicantName;
	private String gender;
	private String dateOfBirth;
	private String time;
	private String date;
	private String name;
	private byte[] photoBytes;
	private int count;
	private List<Grade> gradeList;
	private int studentId;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private List<InterviewSubroundsTO> interviewSubRoundsTOList;
	
	public List<InterviewSubroundsTO> getInterviewSubRoundsTOList() {
		return interviewSubRoundsTOList;
	}
	public void setInterviewSubRoundsTOList(
			List<InterviewSubroundsTO> interviewSubRoundsTOList) {
		this.interviewSubRoundsTOList = interviewSubRoundsTOList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public byte[] getPhotoBytes() {
		return photoBytes;
	}
	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}
	public List<Grade> getGradeList() {
		return gradeList;
	}
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
	
}
