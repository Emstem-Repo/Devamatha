package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.FacilityTO;

public class HostelStudentCheckOutForm extends BaseActionForm {
	
	private int id;
	private String regNo;
	private String studentName;
	private String hostelName;
	private String roomType;
	private String blockName;
	private String unitName;
	private String floorNo;
	private String roomNo;
	private String bedNo;
	private String checkInDate;
	List<FacilityTO> facilityList;
	private String date;
	private String checked1;
	private String checked2;
	private String checkOutRemarks;
	private String editMode;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public String getEditMode() {
		return editMode;
	}

	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}

	public String getCheckOutRemarks() {
		return checkOutRemarks;
	}

	public void setCheckOutRemarks(String checkOutRemarks) {
		this.checkOutRemarks = checkOutRemarks;
	}

	public String getChecked1() {
		return checked1;
	}

	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}

	public String getChecked2() {
		return checked2;
	}

	public void setChecked2(String checked2) {
		this.checked2 = checked2;
	}

	public List<FacilityTO> getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(List<FacilityTO> facilityList) {
		this.facilityList = facilityList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public void reset() {
		this.regNo=null;
		this.checked1=null;
		this.checked2=null;
		this.date=null;
		this.checkOutRemarks=null;
		this.editMode=null;
		
	}
	
	public void clearAll() {
		this.studentName=null;
		this.hostelName=null;
		this.roomType=null;
		this.blockName=null;
		this.roomNo=null;
		this.unitName=null;
		this.floorNo=null;
		this.bedNo=null;
		this.checkInDate=null;
		this.facilityList=null;
		
		
	}
	

}
