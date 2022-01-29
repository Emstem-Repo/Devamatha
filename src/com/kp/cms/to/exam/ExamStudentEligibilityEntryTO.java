package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamStudentEligibilityEntryTO implements Serializable,Comparable<ExamStudentEligibilityEntryTO> {

	private int id;
	private int studentId;
	private int examId;
	private int eligibilityCriteriaId;
	private int classId;
	private String rollNo;
	private String regNO;
	private String studentName;
	private String eligibilityCheck;

	public ExamStudentEligibilityEntryTO(int studentId, int classId,
			int eligibilityCriteriaId, String eligibilityCheck) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.eligibilityCriteriaId = eligibilityCriteriaId;
		this.eligibilityCheck = eligibilityCheck;
	}

	public ExamStudentEligibilityEntryTO(Integer studentId, String rollNo,
			String regNO, String firstName, String lastName,
			Integer eligibilityCheck, Integer eligibilityId, Integer classId,
			Integer examId, Integer eligibilityCriteriaId) {
		super();
		//this.setStudentId(id);
		this.rollNo = rollNo;
		this.regNO = regNO;
		this.studentName = firstName + " " + lastName;
		this.eligibilityCheck = eligibilityCheck == 0 ? "off" : "on";
		this.id = eligibilityId;
		this.classId = classId;
		this.examId = examId;
		this.eligibilityCriteriaId = eligibilityCriteriaId;
	}

	public ExamStudentEligibilityEntryTO() {
		super();
	}

	public ExamStudentEligibilityEntryTO(Integer studentId, String rollNo,
			String regNO, String firstName, String lastName,
			Integer eligibilityCheck, Integer classId) {
		super();
		this.setStudentId(studentId);
		this.rollNo = rollNo;
		this.regNO = regNO;
		this.studentName = firstName + " " + lastName;
		this.eligibilityCheck = eligibilityCheck == 0 ? "off" : "on";
		this.classId = classId;
	}

	public boolean isEligibilityChecked() {
		if ("on".equalsIgnoreCase(eligibilityCheck)) {
			return true;
		}
		return false;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegNO() {
		return regNO;
	}

	public void setRegNO(String regNO) {
		this.regNO = regNO;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEligibilityCheck() {
		return eligibilityCheck;
	}

	public void setEligibilityCheck(String eligibilityCheck) {
		this.eligibilityCheck = eligibilityCheck;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getClassId() {
		return classId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public void setEligibilityCriteriaId(int eligibilityCriteriaId) {
		this.eligibilityCriteriaId = eligibilityCriteriaId;
	}

	public int getEligibilityCriteriaId() {
		return eligibilityCriteriaId;
	}

	@Override
	public int compareTo(ExamStudentEligibilityEntryTO arg0) {
		if(arg0!=null && this!=null && arg0.getStudentName()!=null
				 && this.getStudentName()!=null){
			return this.getStudentName().compareTo(arg0.getStudentName());
		}else
		return 0;
	}

}
