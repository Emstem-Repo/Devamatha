package com.kp.cms.bo.studentLogin;

import java.util.Date;

import com.kp.cms.bo.admin.Student;

public class StudentCarPass {
	private int id;
	private String academicYear;
	private String modelOfVehicle;
	private String vehicleNumber;
	private String emergencyContactNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Student studentId;
	private int carPassNo;
	
	
	public StudentCarPass() {
	}
    

	public StudentCarPass(int id, String academicYear, String modelOfVehicle,
			String vehicleNumber, String emergencyContactNo, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Student studentId, int carPassNo) {
		super();
		this.id = id;
		this.academicYear = academicYear;
		this.modelOfVehicle = modelOfVehicle;
		this.vehicleNumber = vehicleNumber;
		this.emergencyContactNo = emergencyContactNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.studentId = studentId;
		this.carPassNo = carPassNo;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getModelOfVehicle() {
		return modelOfVehicle;
	}
	public void setModelOfVehicle(String modelOfVehicle) {
		this.modelOfVehicle = modelOfVehicle;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}







	public int getCarPassNo() {
		return carPassNo;
	}







	public void setCarPassNo(int carPassNo) {
		this.carPassNo = carPassNo;
	}
	
	
	
}
