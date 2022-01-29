package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.ListOfOccupancyRegisterTO;

public class ListOfOccupancyRegisterForm extends BaseActionForm{

	private static final long serialVersionUID = 1L;

	private String method;
	private String floorNo;
	private String roomNo;
	private String hostelName;
	private List<ListOfOccupancyRegisterTO> listOfOccupancyRegisterList;
	
	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	

	public List<ListOfOccupancyRegisterTO> getListOfOccupancyRegisterList() {
		return listOfOccupancyRegisterList;
	}

	public void setListOfOccupancyRegisterList(
			List<ListOfOccupancyRegisterTO> listOfOccupancyRegisterList) {
		this.listOfOccupancyRegisterList = listOfOccupancyRegisterList;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		String formName=request.getParameter(CMSConstants.FORMNAME);
		ActionErrors errors=null;
		errors=super.validate(mapping, request,formName);
		return errors;
	}

}
