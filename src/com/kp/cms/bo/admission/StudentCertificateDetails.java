package com.kp.cms.bo.admission;

import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Student;

public class StudentCertificateDetails implements java.io.Serializable {
	
	private int id;
	private Student studentId;
	private String certificateNo;
	private Date printedDate;
	private String type;
	
	
	public StudentCertificateDetails(){
		
	}
	
	public StudentCertificateDetails(int id, Student studentId, String certificateNo, Date printedDate, String type){
		super();
		this.id = id;
		this.studentId=studentId;
		this.certificateNo=certificateNo;
		this.printedDate=printedDate;
		this.type=type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public Date getPrintedDate() {
		return printedDate;
	}
	public void setPrintedDate(Date printedDate) {
		this.printedDate = printedDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
}
