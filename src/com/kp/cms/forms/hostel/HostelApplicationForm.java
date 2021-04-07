package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlApplicationFeeTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;

public class HostelApplicationForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String method;
	private List<HostelTO> hostelList;
	private String hostelId;
	private List<RoomTypeTO> roomTypeNameList;
	private String clinicalRemarks;
	private String sicknessRelatedInfo;
	private boolean accepted;
	private String roomTypeId;
	private String roomTypeCheck;
	private String maxRequisitionNo;
	private String templateDescription;
	private HostelTO hostelTO;
	private List<RoomTypeWithAmountTO> roomTypeWithAmountList;
	private Map<Integer,String> roomTypeMap;
	

	
	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getMaxRequisitionNo() {
		return maxRequisitionNo;
	}

	public void setMaxRequisitionNo(String maxRequisitionNo) {
		this.maxRequisitionNo = maxRequisitionNo;
	}

	public String getRoomTypeCheck() {
		return roomTypeCheck;
	}

	public void setRoomTypeCheck(String roomTypeCheck) {
		this.roomTypeCheck = roomTypeCheck;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<HostelTO> getHostelList() {
		return hostelList;
	}

	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<RoomTypeTO> getRoomTypeNameList() {
		return roomTypeNameList;
	}

	public void setRoomTypeNameList(List<RoomTypeTO> roomTypeNameList) {
		this.roomTypeNameList = roomTypeNameList;
	}

	public String getClinicalRemarks() {
		return clinicalRemarks;
	}

	public String getSicknessRelatedInfo() {
		return sicknessRelatedInfo;
	}

	public void setClinicalRemarks(String clinicalRemarks) {
		this.clinicalRemarks = clinicalRemarks;
	}

	public void setSicknessRelatedInfo(String sicknessRelatedInfo) {
		this.sicknessRelatedInfo = sicknessRelatedInfo;
	}

	public HostelTO getHostelTO() {
		return hostelTO;
	}

	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	
	public List<RoomTypeWithAmountTO> getRoomTypeWithAmountList() {
		return roomTypeWithAmountList;
	}

	public void setRoomTypeWithAmountList(
			List<RoomTypeWithAmountTO> roomTypeWithAmountList) {
		this.roomTypeWithAmountList = roomTypeWithAmountList;
	}

	public void clear(){
		this.hostelId = null;
		this.roomTypeCheck = null;
		this.roomTypeId = null;
		this.roomTypeNameList = null;
		this.clinicalRemarks = null;
		this.sicknessRelatedInfo = null;
	}
}
