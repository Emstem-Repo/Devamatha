package com.kp.cms.to.hostel;

import java.util.List;

public class RequisitionsTo {
	private String studentName;
	
	private String hostelName;
	private String requisitionNol;
	private String fullName;
	private String dateofBirth;
	private String mobileNo;
	private String applNoStaffID;
	private String program;
	private String religion;
	private String email;
	private String preferredHostel;
	private String preferredRoomType;
	private String paddressLine1;
	private String paddressLine2;
	private String paddressLine3;
	private String country;
	private String state;
	private String city;
	private byte[] photoBytes;
	private String zip;
	private String phone;
	private String gaddressLine1;
	private String gaddressLine2;
	private String gaddressLine3;
	private String gcountry;
	private String gstate;
	private String gcity;
	private String gzip;
	private String gphone;
	private String requisitionStatus;
	private String forhostel;
	private String roomType;
	private String status;
	private Integer hostelId;
	private Integer roomTypeId;
	//  private List<HostelTO> hostelList;
	
	
	public String getHostelName() {
		return hostelName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public byte[] getPhotoBytes() {
		return photoBytes;
	}
	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getRequisitionNol() {
		return requisitionNol;
	}
	public void setRequisitionNol(String requisitionNol) {
		this.requisitionNol = requisitionNol;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getApplNoStaffID() {
		return applNoStaffID;
	}
	public void setApplNoStaffID(String applNoStaffID) {
		this.applNoStaffID = applNoStaffID;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPreferredHostel() {
		return preferredHostel;
	}
	public void setPreferredHostel(String preferredHostel) {
		this.preferredHostel = preferredHostel;
	}
	public String getPreferredRoomType() {
		return preferredRoomType;
	}
	public void setPreferredRoomType(String preferredRoomType) {
		this.preferredRoomType = preferredRoomType;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getGcountry() {
		return gcountry;
	}
	public void setGcountry(String gcountry) {
		this.gcountry = gcountry;
	}
	public String getGstate() {
		return gstate;
	}
	public void setGstate(String gstate) {
		this.gstate = gstate;
	}
	public String getGcity() {
		return gcity;
	}
	public void setGcity(String gcity) {
		this.gcity = gcity;
	}
	public String getGzip() {
		return gzip;
	}
	public void setGzip(String gzip) {
		this.gzip = gzip;
	}
	public String getGphone() {
		return gphone;
	}
	public void setGphone(String gphone) {
		this.gphone = gphone;
	}
	public String getRequisitionStatus() {
		return requisitionStatus;
	}
	public void setRequisitionStatus(String requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}
	public String getForhostel() {
		return forhostel;
	}
	public void setForhostel(String forhostel) {
		this.forhostel = forhostel;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getPaddressLine1() {
		return paddressLine1;
	}
	public void setPaddressLine1(String paddressLine1) {
		this.paddressLine1 = paddressLine1;
	}
	public String getPaddressLine2() {
		return paddressLine2;
	}
	public void setPaddressLine2(String paddressLine2) {
		this.paddressLine2 = paddressLine2;
	}
	public String getPaddressLine3() {
		return paddressLine3;
	}
	public void setPaddressLine3(String paddressLine3) {
		this.paddressLine3 = paddressLine3;
	}
	public String getGaddressLine1() {
		return gaddressLine1;
	}
	public void setGaddressLine1(String gaddressLine1) {
		this.gaddressLine1 = gaddressLine1;
	}
	public String getGaddressLine2() {
		return gaddressLine2;
	}
	public void setGaddressLine2(String gaddressLine2) {
		this.gaddressLine2 = gaddressLine2;
	}
	public String getGaddressLine3() {
		return gaddressLine3;
	}
	public void setGaddressLine3(String gaddressLine3) {
		this.gaddressLine3 = gaddressLine3;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getHostelId() {
		return hostelId;
	}
	public void setHostelId(Integer hostelId) {
		this.hostelId = hostelId;
	}
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
	
	
}
