package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

public class ExamValuationStatusTO implements Serializable , Comparable<ExamValuationStatusTO>{
	
	private int id;
	private String examName;
	private int examId;
	private String subjectCode;
	private String subjectName;
	private String employeeName;
	private String otherEmployee;
	private String issueDate;
	private String valuationCompleted;
	private String verificationCompleted;
	private String courseName;
	private int totalStudent;
	private int totalEntered;
	private int totalVerified;
	private int subjectId;
	private List<ExamValuationStatusTO> statusTOs;
	private List<ExamValidationDetailsTO> evaluatorDetails;
	private String evaluatorTypeId;
	private String courseCode;
	private List<ExamValuationStatusTO> examValuationList;
	private String isTheory;
	private String theoryMultiple;
	private int courseId;
	private String valuationProcess;
	private String overallProcess;
	private String tempvaluationProcess;
	private String tempoverallProcess;
	private String newLine = "\n";
	private int slNo;
	private boolean isCertificateCourse;
	private String misMatchFound;
	private int termNumber;
	private String programType;
	private int totalAbsent;
	private String internalSubject;
	private String examStratDate;
	private String issueForValuationDate;
	private String valuationLastDate;
	private int classId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getOtherEmployee() {
		return otherEmployee;
	}
	public void setOtherEmployee(String otherEmployee) {
		this.otherEmployee = otherEmployee;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getValuationCompleted() {
		return valuationCompleted;
	}
	public void setValuationCompleted(String valuationCompleted) {
		this.valuationCompleted = valuationCompleted;
	}
	public String getVerificationCompleted() {
		return verificationCompleted;
	}
	public void setVerificationCompleted(String verificationCompleted) {
		this.verificationCompleted = verificationCompleted;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}
	public int getTotalEntered() {
		return totalEntered;
	}
	public void setTotalEntered(int totalEntered) {
		this.totalEntered = totalEntered;
	}
	public int getTotalVerified() {
		return totalVerified;
	}
	public void setTotalVerified(int totalVerified) {
		this.totalVerified = totalVerified;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public List<ExamValuationStatusTO> getStatusTOs() {
		return statusTOs;
	}
	public void setStatusTOs(List<ExamValuationStatusTO> statusTOs) {
		this.statusTOs = statusTOs;
	}
	public List<ExamValidationDetailsTO> getEvaluatorDetails() {
		return evaluatorDetails;
	}
	public void setEvaluatorDetails(List<ExamValidationDetailsTO> evaluatorDetails) {
		this.evaluatorDetails = evaluatorDetails;
	}
	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}
	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public List<ExamValuationStatusTO> getExamValuationList() {
		return examValuationList;
	}
	public void setExamValuationList(List<ExamValuationStatusTO> examValuationList) {
		this.examValuationList = examValuationList;
	}
	public String getIsTheory() {
		return isTheory;
	}
	public void setIsTheory(String isTheory) {
		this.isTheory = isTheory;
	}
	public String getTheoryMultiple() {
		return theoryMultiple;
	}
	public void setTheoryMultiple(String theoryMultiple) {
		this.theoryMultiple = theoryMultiple;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getValuationProcess() {
		return valuationProcess;
	}
	public void setValuationProcess(String valuationProcess) {
		this.valuationProcess = valuationProcess;
	}
	public String getOverallProcess() {
		return overallProcess;
	}
	public void setOverallProcess(String overallProcess) {
		this.overallProcess = overallProcess;
	}
	public String getTempvaluationProcess() {
		return tempvaluationProcess;
	}
	public void setTempvaluationProcess(String tempvaluationProcess) {
		this.tempvaluationProcess = tempvaluationProcess;
	}
	public String getTempoverallProcess() {
		return tempoverallProcess;
	}
	public void setTempoverallProcess(String tempoverallProcess) {
		this.tempoverallProcess = tempoverallProcess;
	}
	public String getNewLine() {
		return newLine;
	}
	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public boolean isCertificateCourse() {
		return isCertificateCourse;
	}
	public void setCertificateCourse(boolean isCertificateCourse) {
		this.isCertificateCourse = isCertificateCourse;
	}
	public String getMisMatchFound() {
		return misMatchFound;
	}
	public void setMisMatchFound(String misMatchFound) {
		this.misMatchFound = misMatchFound;
	}
	public int getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}
	@Override
	public int compareTo(ExamValuationStatusTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getTermNumber() <  arg0.getTermNumber())
				return -1;
			else if(this.getTermNumber() >  arg0.getTermNumber()){
				return 1;
			}else
				return 0;
		}
		return 0;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public int getTotalAbsent() {
		return totalAbsent;
	}
	public void setTotalAbsent(int totalAbsent) {
		this.totalAbsent = totalAbsent;
	}
	public String getInternalSubject() {
		return internalSubject;
	}
	public void setInternalSubject(String internalSubject) {
		this.internalSubject = internalSubject;
	}
	public String getExamStratDate() {
		return examStratDate;
	}
	public void setExamStratDate(String examStratDate) {
		this.examStratDate = examStratDate;
	}
	public String getIssueForValuationDate() {
		return issueForValuationDate;
	}
	public void setIssueForValuationDate(String issueForValuationDate) {
		this.issueForValuationDate = issueForValuationDate;
	}
	public String getValuationLastDate() {
		return valuationLastDate;
	}
	public void setValuationLastDate(String valuationLastDate) {
		this.valuationLastDate = valuationLastDate;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
}