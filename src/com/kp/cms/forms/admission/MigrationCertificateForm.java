package com.kp.cms.forms.admission;

import com.kp.cms.forms.BaseActionForm;

public class MigrationCertificateForm extends BaseActionForm {
	
	private String regNo;
	private String migrationDate;
	private String studentName;
	private String studentCourse;
	private String studentRegNo;
	private String studentAcademicYearFrom;
	private String studentAcademicYearTo;
	private String migrationCertificateNo;
	private int stuId;
	private String showRecord;
	private int currentNumber;
	
	
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}	

	public String getMigrationDate() {
		return migrationDate;
	}

	public void setMigrationDate(String migrationDate) {
		this.migrationDate = migrationDate;
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

	public String getStudentRegNo() {
		return studentRegNo;
	}

	public void setStudentRegNo(String studentRegNo) {
		this.studentRegNo = studentRegNo;
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

	public String getMigrationCertificateNo() {
		return migrationCertificateNo;
	}

	public void setMigrationCertificateNo(String migrationCertificateNo) {
		this.migrationCertificateNo = migrationCertificateNo;
	}

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}


	public String getShowRecord() {
		return showRecord;
	}

	public void setShowRecord(String showRecord) {
		this.showRecord = showRecord;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	public void resetFields()
	{
		this.regNo=null;
		this.migrationDate=null;
		this.studentName=null;
		this.studentRegNo=null;
		this.studentCourse=null;
		this.studentAcademicYearFrom=null;
		this.studentAcademicYearTo=null;
		this.stuId=0;
		this.showRecord="NO";
		this.migrationCertificateNo=null;
		this.currentNumber=0;
	}

	public void clearFew()
	{
		this.regNo=null;
		this.showRecord="YES";
	}

	public void clearStuDetails() 
	{
		this.migrationDate=null;
		this.studentName=null;
		this.studentCourse=null;
		this.studentRegNo=null;
		this.studentAcademicYearFrom=null;
		this.studentAcademicYearTo=null;
		this.migrationCertificateNo=null;
	}

}
