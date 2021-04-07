package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kp.cms.to.admin.PeriodTONew;
import com.kp.cms.to.admin.SubjectTO;

public class CocurricularAttendnaceEntryTo implements Serializable , Comparable<CocurricularAttendnaceEntryTo>{
	private String attendanceDate;
	private List<PeriodTONew> periodToList;
	private String cocurricularLeavetypeId;
	private String classSchemewiseId;
	private String subjectId;
	private String periodId;
	private String studentId;
	private String year;
	private String attendanceStudentId;
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public List<PeriodTONew> getPeriodToList() {
		return periodToList;
	}
	public void setPeriodToList(List<PeriodTONew> periodToList) {
		this.periodToList = periodToList;
	}
	public String getCocurricularLeavetypeId() {
		return cocurricularLeavetypeId;
	}
	public void setCocurricularLeavetypeId(String cocurricularLeavetypeId) {
		this.cocurricularLeavetypeId = cocurricularLeavetypeId;
	}
	
	public String getClassSchemewiseId() {
		return classSchemewiseId;
	}
	public void setClassSchemewiseId(String classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getAttendanceStudentId() {
		return attendanceStudentId;
	}
	public void setAttendanceStudentId(String attendanceStudentId) {
		this.attendanceStudentId = attendanceStudentId;
	}
	@Override
	public int compareTo(CocurricularAttendnaceEntryTo arg0) {
		if(arg0!=null ){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Date startTime;
			try {
				startTime = dateFormat.parse(this.getAttendanceDate());
				Date startTime1 = dateFormat.parse(arg0.getAttendanceDate());
				if(startTime.compareTo(startTime1) > 0)
					return 1;
				else if(startTime.compareTo(startTime1) < 0){
					return -1;
				}else
					return 0;
			} catch (ParseException e) {
			}
		}	
		return 0;
	}

}
