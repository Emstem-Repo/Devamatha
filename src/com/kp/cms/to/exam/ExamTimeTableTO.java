package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ExamTimeTableTO implements Serializable{
	private int id;
	private Integer examId;
	private String examType;
	private String batch;
	private String program;
	private Integer courseId;
	private String course;
	private Integer schemeId;
	private String scheme;
	private Integer schemeNo;
	private Integer academicyear;
	private String joiningBatch;
	
	
	private String status;
	private ArrayList<ExamSubjectTimeTableTO> listSubjects;
	
	public ExamTimeTableTO() {
		super();
	}

	public ExamTimeTableTO(int id, String examType, String batch,
			String program, String course, String scheme, String status,String joiningBatch) {
		super();
		this.id = id;
		this.examType = examType;
		this.batch = batch;
		this.program = program;
		this.course = course;
		this.scheme = scheme;
		this.status = status;
		this.joiningBatch=joiningBatch;
	}

	public ExamTimeTableTO(int id, String examType, String batch,
			String program, String course, String scheme, Integer courseId,
			Integer schemeId, String status,
			ArrayList<ExamSubjectTimeTableTO> listSubjects,String joiningBatch) {
		super();
		this.id = id;
		this.examType = examType;
		this.batch = batch;
		this.program = program;
		this.course = course;
		this.scheme = scheme;
		this.courseId = courseId;
		this.schemeId = schemeId;
		this.status = status;
		this.listSubjects = listSubjects;
		this.joiningBatch=joiningBatch;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setListSubjects(ArrayList<ExamSubjectTimeTableTO> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public ArrayList<ExamSubjectTimeTableTO> getListSubjects() {
		return listSubjects;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}

	public Integer getSchemeNo() {
		return schemeNo;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setAcademicyear(Integer academicyear) {
		this.academicyear = academicyear;
	}

	public Integer getAcademicyear() {
		return academicyear;
	}

	public String getJoiningBatch() {
		return joiningBatch;
	}

	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}

}
