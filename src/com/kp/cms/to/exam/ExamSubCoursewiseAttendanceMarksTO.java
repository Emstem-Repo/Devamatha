package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubCoursewiseAttendanceMarksTO  implements Serializable,Comparable<ExamSubCoursewiseAttendanceMarksTO>
{
	private int id;
	private int subjectId;
	private String subjectCode;
	private String subjectName;
	private String attendanceMarks;
	private String fromPrcntgAttndnc;
	private String toPrcntgAttndnc;
	
	
	public ExamSubCoursewiseAttendanceMarksTO() {
		super();
	}
	public ExamSubCoursewiseAttendanceMarksTO(int id,
			String attendanceMarks, String fromPrcntgAttndnc,
			String toPrcntgAttndnc, String subjectCode, int subjectId,
			String subjectName) {
		super();
		this.id = id;
		this.attendanceMarks = attendanceMarks;
		this.fromPrcntgAttndnc = fromPrcntgAttndnc;
		this.toPrcntgAttndnc = toPrcntgAttndnc;
		this.subjectCode = subjectCode;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getAttendanceMarks() {
		return attendanceMarks;
	}
	public void setAttendanceMarks(String attendanceMarks) {
		this.attendanceMarks = attendanceMarks;
	}
	public String getFromPrcntgAttndnc() {
		return fromPrcntgAttndnc;
	}
	public void setFromPrcntgAttndnc(String fromPrcntgAttndnc) {
		this.fromPrcntgAttndnc = fromPrcntgAttndnc;
	}
	public String getToPrcntgAttndnc() {
		return toPrcntgAttndnc;
	}
	public void setToPrcntgAttndnc(String toPrcntgAttndnc) {
		this.toPrcntgAttndnc = toPrcntgAttndnc;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	@Override
	public int compareTo(ExamSubCoursewiseAttendanceMarksTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null 
				 && this.getSubjectName()!=null){
			
				return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}

}
