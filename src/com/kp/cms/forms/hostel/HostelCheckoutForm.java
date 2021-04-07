package com.kp.cms.forms.hostel;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlCheckinCheckoutFacilityTo;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelCheckoutForm extends BaseActionForm {

	private static final long serialVersionUID = 1L;
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
	private String comments;
	private String remarks;
	private String fecilitName;
	private int formId;
	private int statusId;
	private String statusType;
	private String checkoutDate;
	List<HlDamageTO> hlDamageToList;
	List<HlCheckinCheckoutFacilityTo> checkoutList;
	private List<HlRoomTypeFacilityTo> roomFacilityList;
	private HostelCheckoutTo checkoutTo;
	
	public List<HlDamageTO> getHlDamageToList() {
		return hlDamageToList;
	}
	
	public void setHlDamageToList(List<HlDamageTO> hlDamageToList) {
		this.hlDamageToList = hlDamageToList;
	}
	

	public List<HlCheckinCheckoutFacilityTo> getCheckoutList() {
		return checkoutList;
	}

	public void setCheckoutList(List<HlCheckinCheckoutFacilityTo> checkoutList) {
		this.checkoutList = checkoutList;
	}

	public String getFecilitName() {
		return fecilitName;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public void setFecilitName(String fecilitName) {
		this.fecilitName = fecilitName;
	}

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

	public int getFormId() {
		return formId;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public HostelCheckoutTo getCheckoutTo() {
		return checkoutTo;
	}

	public void setCheckoutTo(HostelCheckoutTo checkoutTo) {
		this.checkoutTo = checkoutTo;
	}

	public void clearMyFields() {
		this.regNo = null;
		this.staffId = null;
		this.appNo = null;
		this.rollNo = null;
		super.setHostelId(null);
		this.txnDate=null;
		this.comments=null;
		this.remarks=null;
		//this.checkoutList=null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public List<HlRoomTypeFacilityTo> getRoomFacilityList() {
		return roomFacilityList;
	}

	public void setRoomFacilityList(List<HlRoomTypeFacilityTo> roomFacilityList) {
		this.roomFacilityList = roomFacilityList;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

}
