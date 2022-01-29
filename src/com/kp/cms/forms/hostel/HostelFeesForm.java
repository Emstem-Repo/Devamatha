package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelFeesTo;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelFeesForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String hostelName;
	private String roomType;
	private String total;
	private List<HostelFeesTypeTo> feeList;
	private List<HostelFeesTo> feeAllDetails;
	private List<HostelFeesTo> feeDetailedListToView;
	List<HostelTO> hostelList;
	//temp
	private String mainFee;
	private String hostelFee;
	private String deposit;
	private String[] elements;
	private int length;
	private String oldHostelId;
	private String oldRoomTypeId;
	
	public String getMainFee() {
		return mainFee;
	}
	public void setMainFee(String mainFee) {
		this.mainFee = mainFee;
	}
	public String getHostelFee() {
		return hostelFee;
	}
	public void setHostelFee(String hostelFee) {
		this.hostelFee = hostelFee;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	public List<HostelFeesTypeTo> getFeeList() {
		return feeList;
	}
	public void setFeeList(List<HostelFeesTypeTo> feeList) {
		this.feeList = feeList;
	}
	
	public List<HostelFeesTo> getFeeAllDetails() {
		return feeAllDetails;
	}
	public void setFeeAllDetails(List<HostelFeesTo> feeAllDetails) {
		this.feeAllDetails = feeAllDetails;
	}
	public String[] getElements() {
		return elements;
	}
	public void setElements(String[] elements) {
		this.elements = elements;
	}
	
	public List<HostelFeesTo> getFeeDetailedListToView() {
		return feeDetailedListToView;
	}
	public void setFeeDetailedListToView(List<HostelFeesTo> feeDetailedListToView) {
		this.feeDetailedListToView = feeDetailedListToView;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return the oldHostelId
	 */
	public String getOldHostelId() {
		return oldHostelId;
	}
	/**
	 * @param oldHostelId the oldHostelId to set
	 */
	public void setOldHostelId(String oldHostelId) {
		this.oldHostelId = oldHostelId;
	}
	/**
	 * @return the oldRoomTypeId
	 */
	public String getOldRoomTypeId() {
		return oldRoomTypeId;
	}
	/**
	 * @param oldRoomTypeId the oldRoomTypeId to set
	 */
	public void setOldRoomTypeId(String oldRoomTypeId) {
		this.oldRoomTypeId = oldRoomTypeId;
	}
	public void clearMyFields() {
		super.setHostelId(null);
		this.roomType=null;
		this.total=null;
		this.hostelName=null;
		this.oldHostelId=null;
		this.oldRoomTypeId=null;
		//this.feeList=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	
}
