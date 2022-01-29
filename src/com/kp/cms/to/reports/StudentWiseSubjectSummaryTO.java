package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class StudentWiseSubjectSummaryTO implements Serializable,Comparable<StudentWiseSubjectSummaryTO>{

	private String subjectName;

	private int conductedClasses;

	private int classesPresent;

	private int classesAbsent;

	private int leaveApproved;

	private float percentageWithLeave;

	private float percentageWithoutLeave;
	
	private String newLine = "\n";
	
	private String subjectID;
	
	private String attendanceTypeID;
	
	private String attendanceID;
	
	private String attendanceTypeName;
	private String activityName;
	private String activityID;
	
	private String studentId;
	private List<AttendanceTypeTO> attendanceTypeList;
	
	//Satish Patruni
	private int cocurricularLeave;
	
	private float percentageWithCocurricularLeave;
	
	private float totalAttPercentage;

	private int order;
	
	private boolean flag;
   

	private Boolean isAdditionalSubject;
	
	private List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
	
	
	public Boolean getIsAdditionalSubject() {
		return isAdditionalSubject;
	}

	public void setIsAdditionalSubject(Boolean isAdditionalSubject) {
		this.isAdditionalSubject = isAdditionalSubject;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getConductedClasses() {
		return conductedClasses;
	}

	public void setConductedClasses(int conductedClasses) {
		this.conductedClasses = conductedClasses;
	}

	public int getClassesPresent() {
		return classesPresent;
	}

	public void setClassesPresent(int classesPresent) {
		this.classesPresent = classesPresent;
	}

	public int getClassesAbsent() {
		return classesAbsent;
	}

	public void setClassesAbsent(int classesAbsent) {
		this.classesAbsent = classesAbsent;
	}

	public int getLeaveApproved() {
		return leaveApproved;
	}

	public void setLeaveApproved(int leaveApproved) {
		this.leaveApproved = leaveApproved;
	}

	public float getPercentageWithLeave() {
		return percentageWithLeave;
	}

	public void setPercentageWithLeave(float percentageWithLeave) {
		this.percentageWithLeave = percentageWithLeave;
	}

	public float getPercentageWithoutLeave() {
		return percentageWithoutLeave;
	}

	public void setPercentageWithoutLeave(float percentageWithoutLeave) {
		this.percentageWithoutLeave = percentageWithoutLeave;
	}

	public String getNewLine() {
		return newLine;
	}

	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public String getAttendanceTypeID() {
		return attendanceTypeID;
	}

	public String getAttendanceTypeName() {
		return attendanceTypeName;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public void setAttendanceTypeID(String attendanceTypeID) {
		this.attendanceTypeID = attendanceTypeID;
	}

	public void setAttendanceTypeName(String attendanceTypeName) {
		this.attendanceTypeName = attendanceTypeName;
	}

	public String getAttendanceID() {
		return attendanceID;
	}

	public void setAttendanceID(String attendanceID) {
		this.attendanceID = attendanceID;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}

	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}


	public int getCocurricularLeave() {
		return cocurricularLeave;
	}

	public void setCocurricularLeave(int cocurricularLeave) {
		this.cocurricularLeave = cocurricularLeave;
	}

	public float getPercentageWithCocurricularLeave() {
		return percentageWithCocurricularLeave;
	}

	public void setPercentageWithCocurricularLeave(
			float percentageWithCocurricularLeave) {
		this.percentageWithCocurricularLeave = percentageWithCocurricularLeave;
	}

	public float getTotalAttPercentage() {
		return totalAttPercentage;
	}

	public void setTotalAttPercentage(float totalAttPercentage) {
		this.totalAttPercentage = totalAttPercentage;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<ExamMarksEntryDetailsTO> getExamMarksEntryDetailsTOList() {
		return examMarksEntryDetailsTOList;
	}

	public void setExamMarksEntryDetailsTOList(
			List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList) {
		this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
	}

	@Override
	public int compareTo(StudentWiseSubjectSummaryTO arg0) {
		if(arg0!=null ){
			if (arg0.getOrder() != 0 && this.getOrder()!=0){
				if(this.getOrder() >  arg0.getOrder())
					return 1;
				else if(this.getOrder() <  arg0.getOrder()){
					return -1;
				}else
					return 0;
		    }
		}
		return 0;
	}
	
}
