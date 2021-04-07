package com.kp.cms.to.hostel;

import java.io.Serializable;

public class HostelReAdmissionTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String studentName;
	private String registerNo;
	private String studentClassName;
	private String appliedRoomType;
	private String changeRoomType;
	private String checked;
	private String tempChecked;
	private int studentId;
	private int hostelOnlineAppId;
	private int appliedRoomTypeId;
	private String applicationNo;
	private String studentApplicationNo;
	private int hostelId;
	
	
	
	
	
	
	public int getHostelId() {
		return hostelId;
	}
	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}
	public String getStudentApplicationNo() {
		return studentApplicationNo;
	}
	public void setStudentApplicationNo(String studentApplicationNo) {
		this.studentApplicationNo = studentApplicationNo;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public int getAppliedRoomTypeId() {
		return appliedRoomTypeId;
	}
	public void setAppliedRoomTypeId(int appliedRoomTypeId) {
		this.appliedRoomTypeId = appliedRoomTypeId;
	}
	public int getHostelOnlineAppId() {
		return hostelOnlineAppId;
	}
	public void setHostelOnlineAppId(int hostelOnlineAppId) {
		this.hostelOnlineAppId = hostelOnlineAppId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentClassName() {
		return studentClassName;
	}
	public void setStudentClassName(String studentClassName) {
		this.studentClassName = studentClassName;
	}
	public String getAppliedRoomType() {
		return appliedRoomType;
	}
	public void setAppliedRoomType(String appliedRoomType) {
		this.appliedRoomType = appliedRoomType;
	}
	public String getChangeRoomType() {
		return changeRoomType;
	}
	public void setChangeRoomType(String changeRoomType) {
		this.changeRoomType = changeRoomType;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	

}
