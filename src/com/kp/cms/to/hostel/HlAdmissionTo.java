package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.Date;

public class HlAdmissionTo implements Serializable,Comparable<HlAdmissionTo>{
	
	private int id;
	private String studentId;
	private String studentName;
	private String courseId;
	private String courseName;
	private String hostelId;
	private String hostelName;
	private String roomTypeId;
	private String roomTypeName;
	private String regNo;
	private String applNo;
	private String year;
	private Date admittedDate;
	private String roomId;
	private String roomName;
	private String bedId;
	private String bedNo;
	private String hlApplicationNo;
	private String blockId;
	private String unitId;
	private String floorId;
	private String biometricId;
	private String isCheckIn;
	private Date checkInDate;
	private String studentClass;
	private String status;
	private boolean isPhoto;
	private String morningPunch;
	private String eveningPunch;
	private String hostelDate;
	private String hostelSession;
	private String holidayType;
	private Date checkOutDate;
	private Boolean isFacilityVerified;
	private Boolean isCheckOut;
	private String tempChecked;
	private String dummySelected;
	private boolean checkingStudent;
	private String program;
	private String dateOfBirth;
	private String religion;
	private String mobNO;
	private String parentAdd1;
	private String parentAdd2;
	private String parentAdd3;
	private String parentAdd4;
	private String parentMobNO;
	private String parentTelNO;
	private String currentAdd1;
	private String currentAdd2;
	private String currentAdd3;
	private String currentAdd4;
	private String currentMobNO;
	private String currentTelNO;
	private String email;
	
	public String getParentAdd4() {
		return parentAdd4;
	}
	public void setParentAdd4(String parentAdd4) {
		this.parentAdd4 = parentAdd4;
	}
	public String getCurrentAdd4() {
		return currentAdd4;
	}
	public void setCurrentAdd4(String currentAdd4) {
		this.currentAdd4 = currentAdd4;
	}
	
	
	public String getParentAdd1() {
		return parentAdd1;
	}
	public void setParentAdd1(String parentAdd1) {
		this.parentAdd1 = parentAdd1;
	}
	public String getParentAdd2() {
		return parentAdd2;
	}
	public void setParentAdd2(String parentAdd2) {
		this.parentAdd2 = parentAdd2;
	}
	public String getParentAdd3() {
		return parentAdd3;
	}
	public void setParentAdd3(String parentAdd3) {
		this.parentAdd3 = parentAdd3;
	}
	public String getParentMobNO() {
		return parentMobNO;
	}
	public void setParentMobNO(String parentMobNO) {
		this.parentMobNO = parentMobNO;
	}
	public String getParentTelNO() {
		return parentTelNO;
	}
	public void setParentTelNO(String parentTelNO) {
		this.parentTelNO = parentTelNO;
	}
	public String getCurrentAdd1() {
		return currentAdd1;
	}
	public void setCurrentAdd1(String currentAdd1) {
		this.currentAdd1 = currentAdd1;
	}
	public String getCurrentAdd2() {
		return currentAdd2;
	}
	public void setCurrentAdd2(String currentAdd2) {
		this.currentAdd2 = currentAdd2;
	}
	public String getCurrentAdd3() {
		return currentAdd3;
	}
	public void setCurrentAdd3(String currentAdd3) {
		this.currentAdd3 = currentAdd3;
	}
	public String getCurrentMobNO() {
		return currentMobNO;
	}
	public void setCurrentMobNO(String currentMobNO) {
		this.currentMobNO = currentMobNO;
	}
	public String getCurrentTelNO() {
		return currentTelNO;
	}
	public void setCurrentTelNO(String currentTelNO) {
		this.currentTelNO = currentTelNO;
	}
	
	
	public String getMobNO() {
		return mobNO;
	}
	public void setMobNO(String mobNO) {
		this.mobNO = mobNO;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getDummySelected() {
		return dummySelected;
	}
	public void setDummySelected(String dummySelected) {
		this.dummySelected = dummySelected;
	}
	public Boolean getIsCheckOut() {
		return isCheckOut;
	}
	public void setIsCheckOut(Boolean isCheckOut) {
		this.isCheckOut = isCheckOut;
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
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	public String getHostelDate() {
		return hostelDate;
	}
	public void setHostelDate(String hostelDate) {
		this.hostelDate = hostelDate;
	}
	public String getHostelSession() {
		return hostelSession;
	}
	public void setHostelSession(String hostelSession) {
		this.hostelSession = hostelSession;
	}
	public String getMorningPunch() {
		return morningPunch;
	}
	public void setMorningPunch(String morningPunch) {
		this.morningPunch = morningPunch;
	}
	public String getEveningPunch() {
		return eveningPunch;
	}
	public void setEveningPunch(String eveningPunch) {
		this.eveningPunch = eveningPunch;
	}
	public boolean getIsPhoto() {
		return this.isPhoto;
	}
	public void setIsPhoto(boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	public String getBiometricId() {
		return biometricId;
	}
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}
	public String getIsCheckIn() {
		return isCheckIn;
	}
	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	
	
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getFloorId() {
		return floorId;
	}
	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Date getAdmittedDate() {
		return admittedDate;
	}
	public void setAdmittedDate(Date admittedDate) {
		this.admittedDate = admittedDate;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getHlApplicationNo() {
		return hlApplicationNo;
	}
	public void setHlApplicationNo(String hlApplicationNo) {
		this.hlApplicationNo = hlApplicationNo;
	}
	public boolean isCheckingStudent() {
		return checkingStudent;
	}
	public void setCheckingStudent(boolean checkingStudent) {
		this.checkingStudent = checkingStudent;
	}
	@Override
	public int compareTo(HlAdmissionTo o) {
		int i=0;
		if(this.roomName.equalsIgnoreCase(o.roomName)){
			i=this.bedNo.compareTo(o.bedNo);
		}
		return i;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
