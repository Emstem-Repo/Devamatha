package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.StudentTranscriptPrintTO;

/**
 * @author dIlIp
 *
 */
public class StudentTranscriptPrintForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regNo;
	private int stuId;
	private String showSuppl;
	private String studentName;
	private String studentCourse;
	private String transcriptNo;
	private int currentNumber;
	Map<Integer, List<StudentTranscriptPrintTO>> semesterList;
	private String transcriptDate;
	private String description;
	private boolean isPG;
	private String studentAcademicYearFrom;
	private String studentAcademicYearTo;
	private String noPrefixFound;
	
	
	public void resetFields(){
		this.regNo=null;
		this.showSuppl=null;
		this.studentName=null;
		this.studentCourse=null;
		this.transcriptNo=null;
		this.transcriptDate=null;
		this.semesterList=null;
		this.description=null;
		this.studentAcademicYearFrom=null;
		this.studentAcademicYearTo=null;
		this.noPrefixFound="NO";
	}
	
	
	public String getShowSuppl() {
		return showSuppl;
	}


	public void setShowSuppl(String showSuppl) {
		this.showSuppl = showSuppl;
	}


	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentCourse() {
		return studentCourse;
	}
	public void setStudentCourse(String studentCourse) {
		this.studentCourse = studentCourse;
	}	
	public String getTranscriptNo() {
		return transcriptNo;
	}
	public void setTranscriptNo(String transcriptNo) {
		this.transcriptNo = transcriptNo;
	}
	
	public int getCurrentNumber() {
		return currentNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}
	public String getTranscriptDate() {
		return transcriptDate;
	}
	public void setTranscriptDate(String transcriptDate) {
		this.transcriptDate = transcriptDate;
	}
	public boolean isPG() {
		return isPG;
	}


	public void setPG(boolean isPG) {
		this.isPG = isPG;
	}

	public Map<Integer, List<StudentTranscriptPrintTO>> getSemesterList() {
		return semesterList;
	}


	public void setSemesterList(
			Map<Integer, List<StudentTranscriptPrintTO>> semesterList) {
		this.semesterList = semesterList;
	}


	public String getStudentAcademicYearFrom() {
		return studentAcademicYearFrom;
	}


	public void setStudentAcademicYearFrom(String studentAcademicYearFrom) {
		this.studentAcademicYearFrom = studentAcademicYearFrom;
	}


	public String getStudentAcademicYearTo() {
		return studentAcademicYearTo;
	}


	public void setStudentAcademicYearTo(String studentAcademicYearTo) {
		this.studentAcademicYearTo = studentAcademicYearTo;
	}


	public String getNoPrefixFound() {
		return noPrefixFound;
	}


	public void setNoPrefixFound(String noPrefixFound) {
		this.noPrefixFound = noPrefixFound;
	}

}
