package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlRoomFloorTO;
import com.kp.cms.to.hostel.HostelAllocationTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelAllocationForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String hostelName;
	private String appNo;
	private String regNo;
	private String staffId;
	private String rollNo;
	List<HlRoomFloorTO> floorNoList;
	List<HostelTO> hostelList;
	
	private Integer academicYr;
	private String applicantName;
	private String reservationDate;
	private String roomType;
	private String isVeg;
	private String ddNo;
	private String roomNo;
	private String bedNo;
	private List roomNoList;
	private String receiptNo;
	private String receiptDate;
	private String amount;
	private String allocationDate;
	private String isStaff;
	private List<HostelAllocationTO>reservedHostelList=new ArrayList<HostelAllocationTO>();
	
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public List<HlRoomFloorTO> getFloorNoList() {
		return floorNoList;
	}
	public void setFloorNoList(List<HlRoomFloorTO> floorNoList) {
		this.floorNoList = floorNoList;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public String getIsVeg() {
		return isVeg;
	}
	public void setIsVeg(String isVeg) {
		this.isVeg = isVeg;
	}
	public String getDdNo() {
		return ddNo;
	}
	public void setDdNo(String ddNo) {
		this.ddNo = ddNo;
	}
	
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public List getRoomNoList() {
		return roomNoList;
	}
	public void setRoomNoList(List roomNoList) {
		this.roomNoList = roomNoList;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
public void clearMyFields() {
		this.regNo=null;
		this.staffId=null;
		this.appNo=null;
		this.rollNo=null;
		this.bedNo=null;
		super.setFloorNo(null);
		super.setHostelId(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}
	public Integer getAcademicYr() {
		return academicYr;
	}
	public void setAcademicYr(Integer academicYr) {
		this.academicYr = academicYr;
	}
	public List<HostelAllocationTO> getReservedHostelList() {
		return reservedHostelList;
	}
	public void setReservedHostelList(List<HostelAllocationTO> reservedHostelList) {
		this.reservedHostelList = reservedHostelList;
	}
	public String getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}
	
	
}
