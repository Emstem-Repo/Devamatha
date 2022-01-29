package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;

public class HlAdmissionBo implements java.io.Serializable{
	
	private int id;
	private Student studentId;
	private Course courseId;
	private HlHostel hostelId;
	private HlRoomType roomTypeId;
	private HlRoom roomId;
	private HlBeds bedId;
	private String cancelReason;
	private String academicYear;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isCancelled;
	private Boolean isActive;
	private Date admittedDate;
	private String applicationNo;
	private String biometricId;
	private Date checkInDate;
	private Boolean isCheckedIn;
	private Boolean checkOut;
	private Date checkOutDate;
	private Boolean isFacilityVerified;
	private String regNo;
	private String checkOutRemarks;
	
	public String getCheckOutRemarks() {
		return checkOutRemarks;
	}
	public void setCheckOutRemarks(String checkOutRemarks) {
		this.checkOutRemarks = checkOutRemarks;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public Boolean getIsFacilityVerified() {
		return isFacilityVerified;
	}
	public void setIsFacilityVerified(Boolean isFacilityVerified) {
		this.isFacilityVerified = isFacilityVerified;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public Boolean getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Boolean checkOut) {
		this.checkOut = checkOut;
	}
	private Set<HlStudentFacilityAllotted> hlFacilitiesAllotted = new HashSet<HlStudentFacilityAllotted>(0);
	
	public HlAdmissionBo()
	{
		
	}
	public HlAdmissionBo(int id,Student studentId,Course courseId,HlHostel hostelId,HlRoomType roomTypeId,HlRoom roomId,HlBeds bedId,
			             String academicYear,String createdBy,Date createdDate,String cancelReason,Date admittedDate,String applicationNo,
			            String modifiedBy,Date lastModifiedDate,Boolean isCancelled,Boolean isActive, 
			            String biometricId,Date checkInDate,Boolean isCheckedIn,Set<HlStudentFacilityAllotted> hlFacilitiesAllotted)
	{
		
		this.id=id;
		this.studentId=studentId;
		this.courseId=courseId;
		this.hostelId=hostelId;
		this.roomTypeId=roomTypeId;
		this.roomId=roomId;
		this.bedId=bedId;
		this.academicYear=academicYear;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isCancelled=isCancelled;
		this.isActive=isActive;
		this.cancelReason=cancelReason;
		this.admittedDate=admittedDate;
		this.applicationNo=applicationNo;
		this.biometricId=biometricId;
		this.checkInDate=checkInDate;
		this.isCheckedIn=isCheckedIn;
		this.hlFacilitiesAllotted=hlFacilitiesAllotted;
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
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
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
	public HlRoom getRoomId() {
		return roomId;
	}
	public void setRoomId(HlRoom roomId) {
		this.roomId = roomId;
	}
	public HlBeds getBedId() {
		return bedId;
	}
	public void setBedId(HlBeds bedId) {
		this.bedId = bedId;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
	public Boolean getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
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
	public String getBiometricId() {
		return biometricId;
	}
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Boolean getIsCheckedIn() {
		return isCheckedIn;
	}
	public void setIsCheckedIn(Boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}
	public Set<HlStudentFacilityAllotted> getHlFacilitiesAllotted() {
		return hlFacilitiesAllotted;
	}
	public void setHlFacilitiesAllotted(
			Set<HlStudentFacilityAllotted> hlFacilitiesAllotted) {
		this.hlFacilitiesAllotted = hlFacilitiesAllotted;
	}
	
}
