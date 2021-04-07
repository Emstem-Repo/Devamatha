package com.kp.cms.to.usermanagement;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kp.cms.to.attendance.PeriodTO;

public class StudentAttendanceTO implements Serializable,Comparable<StudentAttendanceTO> {
	
	private String date;
	private String day;
	private List<PeriodTO> periodList;
	private List<Integer> toPosList;
	private Date attendanceDate;
	private int hoursHeldByDay;
	private int subjectHoursHeld;
	private int presentHoursAtt;
	private int leaveHoursAtt;
	private int coLeaveHoursAtt;
	private int attTypeId;
	private int subjectId;
	private int theoryAssMarks;
	private int practicalAssMarks;
	//raghu
	private String teacherName;
	private String subjectName;
	private String periodName;
	private String className;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<PeriodTO> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<PeriodTO> periodList) {
		this.periodList = periodList;
	}
	public List<Integer> getToPosList() {
		return toPosList;
	}
	public void setToPosList(List<Integer> toPosList) {
		this.toPosList = toPosList;
	}
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public int getHoursHeldByDay() {
		return hoursHeldByDay;
	}
	public void setHoursHeldByDay(int hoursHeldByDay) {
		this.hoursHeldByDay = hoursHeldByDay;
	}
	public int getSubjectHoursHeld() {
		return subjectHoursHeld;
	}
	public void setSubjectHoursHeld(int subjectHoursHeld) {
		this.subjectHoursHeld = subjectHoursHeld;
	}
	public int getPresentHoursAtt() {
		return presentHoursAtt;
	}
	public void setPresentHoursAtt(int presentHoursAtt) {
		this.presentHoursAtt = presentHoursAtt;
	}
	public int getLeaveHoursAtt() {
		return leaveHoursAtt;
	}
	public void setLeaveHoursAtt(int leaveHoursAtt) {
		this.leaveHoursAtt = leaveHoursAtt;
	}
	public int getCoLeaveHoursAtt() {
		return coLeaveHoursAtt;
	}
	public void setCoLeaveHoursAtt(int coLeaveHoursAtt) {
		this.coLeaveHoursAtt = coLeaveHoursAtt;
	}
	public int getAttTypeId() {
		return attTypeId;
	}
	public void setAttTypeId(int attTypeId) {
		this.attTypeId = attTypeId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getTheoryAssMarks() {
		return theoryAssMarks;
	}
	public void setTheoryAssMarks(int theoryAssMarks) {
		this.theoryAssMarks = theoryAssMarks;
	}
	public int getPracticalAssMarks() {
		return practicalAssMarks;
	}
	public void setPracticalAssMarks(int practicalAssMarks) {
		this.practicalAssMarks = practicalAssMarks;
	}
	@Override
	public int compareTo(StudentAttendanceTO arg0) {
		if(arg0!=null && this!=null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Date startTime;
			try {
				startTime = dateFormat.parse(this.getDate());
				Date startTime1 = dateFormat.parse(arg0.getDate());
				if(startTime.compareTo(startTime1) == 0)
					return 0;
				else if(startTime.compareTo(startTime1) < 0)
					return -1;
				else if(startTime.compareTo(startTime1) > 0)
					return 1;
			} catch (ParseException e) {
			}
		}	
		return 0;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
}