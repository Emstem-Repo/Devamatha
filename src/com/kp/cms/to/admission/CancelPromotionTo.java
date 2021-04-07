package com.kp.cms.to.admission;

import java.util.List;

import com.kp.cms.to.attendance.SubjectGroupDetailsTo;

public class CancelPromotionTo {
 
 private int id;
 private String registerNo;
 private String studentName;
 private String classes;
 private List<SubjectGroupDetailsTo> subjectGroupTo;
 private int studentId;
 private int admapplnId;
 private int semesterNo;
 private int classId;
 private int classSchemwiseId;
 private int subGrpId;
 private int examStuClassId;
 private int examSubGrpId;
 public int getExamStuClassId() {
	return examStuClassId;
}
public void setExamStuClassId(int examStuClassId) {
	this.examStuClassId = examStuClassId;
}
public int getExamSubGrpId() {
	return examSubGrpId;
}
public void setExamSubGrpId(int examSubGrpId) {
	this.examSubGrpId = examSubGrpId;
}
public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public void setSubjectGroupTo(List<SubjectGroupDetailsTo> subjectGroupTo) {
		this.subjectGroupTo = subjectGroupTo;
	}
	public List<SubjectGroupDetailsTo> getSubjectGroupTo() {
		return subjectGroupTo;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setAdmapplnId(int admapplnId) {
		this.admapplnId = admapplnId;
	}
	public int getAdmapplnId() {
		return admapplnId;
	}
	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}
	public int getSemesterNo() {
		return semesterNo;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassSchemwiseId(int classSchemwiseId) {
		this.classSchemwiseId = classSchemwiseId;
	}
	public int getClassSchemwiseId() {
		return classSchemwiseId;
	}
	public void setSubGrpId(int subGrpId) {
		this.subGrpId = subGrpId;
	}
	public int getSubGrpId() {
		return subGrpId;
	}
}
