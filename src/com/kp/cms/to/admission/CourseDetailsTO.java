package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class CourseDetailsTO implements Serializable,Comparable<CourseDetailsTO>{
	private String className;
	private String subject;
	private float attendence;
	private float marks;
	private List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
	private int order;
	private float absent;
	private String attendanceTypeID;
	private String attendanceID;
	private String subjectID;
	private String studentId;
	private boolean flag;
	private float cocurricularLeave;
	
	public CourseDetailsTO() {
		super();
	}
	public CourseDetailsTO(String className, String subject, float attendence,
			float marks,List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList, int order) {
		super();
		this.className = className;
		this.subject = subject;
		this.attendence = attendence;
		this.marks = marks;
		this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
		this.order = order;
	}
	public CourseDetailsTO(String className, String subject, float attendence,
			float marks,List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList, int order,float absent, 
			String attendanceTypeID,String attendanceID,String subjectID,String studentId) {
		super();
		this.className = className;
		this.subject = subject;
		this.attendence = attendence;
		this.marks = marks;
		this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
		this.order = order;
		this.absent=absent;
		this.attendanceID=attendanceID;
		this.attendanceTypeID=attendanceTypeID;
		this.subjectID=subjectID;
		this.studentId=studentId;
	}
	public CourseDetailsTO(String className, String subject, float attendence,
			float marks,List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList, int order,float absent, 
			String attendanceTypeID,String attendanceID,String subjectID,String studentId,boolean flag,float cocurricularLeave) {
		super();
		this.className = className;
		this.subject = subject;
		this.attendence = attendence;
		this.marks = marks;
		this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
		this.order = order;
		this.absent=absent;
		this.attendanceID=attendanceID;
		this.attendanceTypeID=attendanceTypeID;
		this.subjectID=subjectID;
		this.studentId=studentId;
		this.flag=flag;
		this.cocurricularLeave=cocurricularLeave;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public float getAttendence() {
		return attendence;
	}
	public void setAttendence(float attendence) {
		this.attendence = attendence;
	}
	public float getMarks() {
		return marks;
	}
	public void setMarks(float marks) {
		this.marks = marks;
	}
	public List<ExamMarksEntryDetailsTO> getExamMarksEntryDetailsTOList() {
		return examMarksEntryDetailsTOList;
	}
	public void setExamMarksEntryDetailsTOList(
			List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList) {
		this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public float getAbsent() {
		return absent;
	}
	public void setAbsent(float absent) {
		this.absent = absent;
	}
	public String getAttendanceTypeID() {
		return attendanceTypeID;
	}
	public void setAttendanceTypeID(String attendanceTypeID) {
		this.attendanceTypeID = attendanceTypeID;
	}
	public String getAttendanceID() {
		return attendanceID;
	}
	public void setAttendanceID(String attendanceID) {
		this.attendanceID = attendanceID;
	}
	public String getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public float getCocurricularLeave() {
		return cocurricularLeave;
	}
	public void setCocurricularLeave(float cocurricularLeave) {
		this.cocurricularLeave = cocurricularLeave;
	}
	@Override
	public int compareTo(CourseDetailsTO arg0) {
		if(arg0!=null && this!=null ){
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
