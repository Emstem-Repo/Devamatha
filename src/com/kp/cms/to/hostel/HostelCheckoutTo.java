package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.List;

public class HostelCheckoutTo implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String id;
	private int applicationId;
	private int staffId;
	private int hlAppFormId;
	private String applicantName;
	private String roomId;
	private String roomName;
	private int statusId;
	private String statusType;
	private String roomType;
	private String roomTypeId;
	private String hostelId;
	private String hostelName;
	private String bedNo;
	private String txnDate;
	List<HlCheckinCheckoutFacilityTo> checkoutList;
	
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	private Integer currentOccupantsCount;
	
	private Integer currentReservationCount;
	private List<HlRoomTypeFacilityTo> roomFacilityList;
	
	public  int getStatusId() {
		return statusId;
	}
	public void setStatusId( int statusId) {
		this.statusId = statusId;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public int getHlAppFormId() {
		return hlAppFormId;
	}
	public void setHlAppFormId(int hlAppFormId) {
		this.hlAppFormId = hlAppFormId;
	}
	
	public List<HlRoomTypeFacilityTo> getRoomFacilityList() {
		return roomFacilityList;
	}
	public void setRoomFacilityList(List<HlRoomTypeFacilityTo> roomFacilityList) {
		this.roomFacilityList = roomFacilityList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public Integer getCurrentOccupantsCount() {
		return currentOccupantsCount;
	}
	public void setCurrentOccupantsCount(Integer currentOccupantsCount) {
		this.currentOccupantsCount = currentOccupantsCount;
	}
	public Integer getCurrentReservationCount() {
		return currentReservationCount;
	}
	public void setCurrentReservationCount(Integer currentReservationCount) {
		this.currentReservationCount = currentReservationCount;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
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
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
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
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public List<HlCheckinCheckoutFacilityTo> getCheckoutList() {
		return checkoutList;
	}
	public void setCheckoutList(List<HlCheckinCheckoutFacilityTo> checkoutList) {
		this.checkoutList = checkoutList;
	}
}
