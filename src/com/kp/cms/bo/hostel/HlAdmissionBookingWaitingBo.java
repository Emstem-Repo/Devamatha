package com.kp.cms.bo.hostel;


import java.util.Date;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.Student;

public class HlAdmissionBookingWaitingBo implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Student studentId;
	private HlHostel hostelId;
	private HlRoomType roomTypeId;
	private String academicYear;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Date admittedDate;
	private String applicationNo;
	private Date dateOfBooking;
	private int waitingListPriorityNo;
	private Date intimatedDate;
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
	public HlHostel getHostelId() {
		return hostelId;
	}
	public void setHostelId(HlHostel hostelId) {
		this.hostelId = hostelId;
	}
	public HlRoomType getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(HlRoomType roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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
	public Date getAdmittedDate() {
		return admittedDate;
	}
	public void setAdmittedDate(Date admittedDate) {
		this.admittedDate = admittedDate;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public Date getDateOfBooking() {
		return dateOfBooking;
	}
	public void setDateOfBooking(Date dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}
	public int getWaitingListPriorityNo() {
		return waitingListPriorityNo;
	}
	public void setWaitingListPriorityNo(int waitingListPriorityNo) {
		this.waitingListPriorityNo = waitingListPriorityNo;
	}
	public Date getIntimatedDate() {
		return intimatedDate;
	}
	public void setIntimatedDate(Date intimatedDate) {
		this.intimatedDate = intimatedDate;
	}
	
	
	
	

}
