package com.kp.cms.to.exam;

/**
 * Jan 13, 2010 Created By 9Elements
 */
import java.io.Serializable;

public class ExamRevaluationApplicationTO implements Serializable {
	private int id = 0;
	private int studentId = 0;
	private String regNumber = "";
	private String rollNumber = "";
	private String studentName = "";
	private int courseId = 0;
	private String courseName = "";
	private int schemeNo;

	private int revaluationTypeId;
	private String date;
	private String subjectName;
	private String subjectCode;
	private int subjectId;
	private Boolean isSelected;
	private String amount;
	private String examName;
	private String status;
	private String newStatus;
	private String applyForType;
	private int examId;
	private int classId;
	private String className;
	private boolean revaluationReq;
	private String revType;

	public ExamRevaluationApplicationTO() {
		super();
	}

	public ExamRevaluationApplicationTO(ExamRevaluationApplicationTO input) {

		this.amount = input.getAmount();
		this.courseId = input.getCourseId();
		this.courseName = input.getCourseName();
		this.date = input.getDate();
		this.id = input.getId();
		this.isSelected = input.getIsSelected();
		this.regNumber = input.getRegNumber();
		this.revaluationTypeId = input.getRevaluationTypeId();
		this.rollNumber = input.getRollNumber();
		this.schemeNo = input.getSchemeNo();
		this.studentId = input.getStudentId();
		this.studentName = input.getStudentName();
		this.subjectCode = input.getSubjectCode();
		this.subjectId = input.getSubjectId();
		this.subjectName = input.getSubjectName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getRevaluationTypeId() {
		return revaluationTypeId;
	}

	public void setRevaluationTypeId(int revaluationTypeId) {
		this.revaluationTypeId = revaluationTypeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getApplyForType() {
		return applyForType;
	}

	public void setApplyForType(String applyForType) {
		this.applyForType = applyForType;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isRevaluationReq() {
		return revaluationReq;
	}

	public void setRevaluationReq(boolean revaluationReq) {
		this.revaluationReq = revaluationReq;
	}

	public String getRevType() {
		return revType;
	}

	public void setRevType(String revType) {
		this.revType = revType;
	}

}
