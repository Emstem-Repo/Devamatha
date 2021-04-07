package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Dec 29, 2009 Created By 9Elements
 */
public class ExamBlockUnBlockCandidatesTO implements Serializable,Comparable<ExamBlockUnBlockCandidatesTO> {

	private int id;
	private int classId;
	private String className;
	private int studentId;
	private String regNumber;
	private String rollNumber;
	private String name;
	private int examId;
	private String reason;
	private Boolean isSelected;
	private String type;
	private String status;
	private String examName;
	private String reasonOld;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(ExamBlockUnBlockCandidatesTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNumber()!=null
				 && this.getRegNumber()!=null){
				return this.getRegNumber().compareTo(arg0.getRegNumber());
		}else
		return 0;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getReasonOld() {
		return reasonOld;
	}

	public void setReasonOld(String reasonOld) {
		this.reasonOld = reasonOld;
	}
	
	
}
