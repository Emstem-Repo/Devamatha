package com.kp.cms.bo.exam;

import java.io.Serializable;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class ConsolidateMarksCardNoGen implements Serializable {
	private int id;
	private Classes classId;
	private Student studentId;
	private ExamDefinition examId;
	private String marksCardNo;
	private Boolean isDuplicate;
	private Boolean isConsolidate;
	private String year;
	private Course courseId;
	private String marksCardNo1;
	
	public ConsolidateMarksCardNoGen(){}
	
	
	
	public ConsolidateMarksCardNoGen(int id, Classes classId,
			Student studentId, ExamDefinition examId, String marksCardNo,
			Boolean isDuplicate, Boolean isConsolidate, String year,
			Course courseId, String marksCardNo1) {
		super();
		this.id = id;
		this.classId = classId;
		this.studentId = studentId;
		this.examId = examId;
		this.marksCardNo = marksCardNo;
		this.isDuplicate = isDuplicate;
		this.isConsolidate = isConsolidate;
		this.year = year;
		this.courseId = courseId;
		this.marksCardNo1 = marksCardNo1;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Classes getClassId() {
		return classId;
	}
	public void setClassId(Classes classId) {
		this.classId = classId;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public ExamDefinition getExamId() {
		return examId;
	}
	public void setExamId(ExamDefinition examId) {
		this.examId = examId;
	}
	public String getMarksCardNo() {
		return marksCardNo;
	}
	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
	}
	public Boolean getIsDuplicate() {
		return isDuplicate;
	}
	public void setIsDuplicate(Boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	
	public Boolean getIsConsolidate() {
		return isConsolidate;
	}
	public void setIsConsolidate(Boolean isConsolidate) {
		this.isConsolidate = isConsolidate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMarksCardNo1() {
		return marksCardNo1;
	}
	public void setMarksCardNo1(String marksCardNo1) {
		this.marksCardNo1 = marksCardNo1;
	}
	
	
	
}
