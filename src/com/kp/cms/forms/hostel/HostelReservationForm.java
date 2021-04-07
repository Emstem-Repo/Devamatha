package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelReservationTO;
import com.kp.cms.to.hostel.RoomTypeTO;

public class HostelReservationForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationNo;
	private String registerNo;
	private String staffID;
	private HostelReservationTO applicantHostelDetails;
	private String reservationDate;
	private List<RoomTypeTO> roomTypesList;
	private String roomTypeId;
	private String remarks;
	private String applicationId;
	private int maxNoOfOccupants;
	private String print;
	private String roomTypeName;
	private String isVeg;
	private String requisitionNo;
	private String floorNo;
	private String hlAppFormId;
	private int billNo;
	private String isStaff;
	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public String getHlAppFormId() {
		return hlAppFormId;
	}

	public void setHlAppFormId(String hlAppFormId) {
		this.hlAppFormId = hlAppFormId;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public HostelReservationTO getApplicantHostelDetails() {
		return applicantHostelDetails;
	}

	public void setApplicantHostelDetails(
			HostelReservationTO applicantHostelDetails) {
		this.applicantHostelDetails = applicantHostelDetails;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public List<RoomTypeTO> getRoomTypesList() {
		return roomTypesList;
	}

	public void setRoomTypesList(List<RoomTypeTO> roomTypesList) {
		this.roomTypesList = roomTypesList;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getMaxNoOfOccupants() {
		return maxNoOfOccupants;
	}

	public void setMaxNoOfOccupants(int maxNoOfOccupants) {
		this.maxNoOfOccupants = maxNoOfOccupants;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(String isVeg) {
		this.isVeg = isVeg;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		super.setHostelId(null);
		this.requisitionNo=null;
		this.floorNo=null;
		super.setRoomId(null);
	}

	public void resetSecondPageFields() {
		reservationDate = null;
		roomTypeId = null;
		remarks = null;
		isVeg = "false";
	}

	public String getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}


	
}