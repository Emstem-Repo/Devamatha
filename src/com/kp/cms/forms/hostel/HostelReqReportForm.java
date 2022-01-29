package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelReqReportForm extends BaseActionForm {
	
	private String hostelName;
	private String hostelId;
	private String appliedDate;
	List<HostelTO> hostelList;
	List<HostelReqReportTo> hostelReqReportToList;
	List<HostelReqReportTo> hostelRoomTypeToList;
	
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public List<HostelReqReportTo> getHostelRoomTypeToList() {
		return hostelRoomTypeToList;
	}
	public void setHostelRoomTypeToList(List<HostelReqReportTo> hostelRoomTypeToList) {
		this.hostelRoomTypeToList = hostelRoomTypeToList;
	}
	public List<HostelReqReportTo> getHostelReqReportToList() {
		return hostelReqReportToList;
	}
	public void setHostelReqReportToList(
			List<HostelReqReportTo> hostelReqReportToList) {
		this.hostelReqReportToList = hostelReqReportToList;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	
	public void clearMyFields() {
		this.hostelName = null;
		this.appliedDate = null;
		this.hostelList = null;
		this.hostelId  = null;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
