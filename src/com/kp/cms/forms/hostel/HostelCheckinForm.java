package com.kp.cms.forms.hostel;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelCheckinForm extends BaseActionForm {

	private static final long serialVersionUID = 1L;
	private String transactionId;
	private String hostelName;
	private String appNo;
	private String regNo;
	private String staffId;
	private String rollNo;
	List<HostelTO> hostelList;
	private String applicantName;
	private String roomId;
	private String roomName;
	private String roomType;
	private String roomTypeId;
	private String bedNo;
	private String txnDate;
	private String fingerPrintId;
	private String comments;
	private String remarks;
	private String fecilitName;
	//private int hlAppFormId;
	private int formId;
	private int statusId;
	private String statusType;
	private String isStaff;
	List<HlRoomTypeFacilityTo> fecilityList;
	List<HostelCheckinTo> allotedList;
	//List<HostelCheckinTo> hostelCheckinToList;
	//private HostelCheckinTo hto;
	private String allotedDate;
	
	public String getFecilitName() {
		return fecilitName;
	}

	public void setFecilitName(String fecilitName) {
		this.fecilitName = fecilitName;
	}

	public List<HlRoomTypeFacilityTo> getFecilityList() {
		return fecilityList;
	}

	public void setFecilityList(List<HlRoomTypeFacilityTo> fecilityList) {
		this.fecilityList = fecilityList;
	}

	// List<HlCheckinCheckoutFacility> hlCheckinCheckoutFacilities;

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

	public List<HostelTO> getHostelList() {
		return hostelList;
	}

	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
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

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getFingerPrintId() {
		return fingerPrintId;
	}

	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	/*public int getHlAppFormId() {
		return hlAppFormId;
	}

	public void setHlAppFormId(int hlAppFormId) {
		this.hlAppFormId = hlAppFormId;
	}*/

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}
	

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getAllotedDate() {
		return allotedDate;
	}

	public void setAllotedDate(String allotedDate) {
		this.allotedDate = allotedDate;
	}

	public void clearMyFields() {
		this.regNo = null;
		this.staffId = null;
		this.appNo = null;
		this.rollNo = null;
		super.setHostelId(null);
		this.txnDate=null;
		this.fingerPrintId=null;
		this.comments=null;
		this.remarks=null;	
		this.allotedDate=null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public List<HostelCheckinTo> getAllotedList() {
		return allotedList;
	}

	public void setAllotedList(List<HostelCheckinTo> allotedList) {
		this.allotedList = allotedList;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	

}
