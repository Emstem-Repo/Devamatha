package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.AvailableSeatsTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;

public class AvailableSeatsForm extends BaseActionForm{
	private int id;
	Map<String,String> hostelMap;
	private boolean flag;
	private String numOfAvailableSeats;
	List<AvailableSeatsTo> availableSeatsList;
	Map<Integer,String> roomTypeMap;
	private String totalNumOfSeatsAvail;
	private String totalSeats;
	
	public String getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}
	public String getTotalNumOfSeatsAvail() {
		return totalNumOfSeatsAvail;
	}
	public void setTotalNumOfSeatsAvail(String totalNumOfSeatsAvail) {
		this.totalNumOfSeatsAvail = totalNumOfSeatsAvail;
	}
	public Map<Integer, String> getRoomTypeMap() {
		return roomTypeMap;
	}
	public void setRoomTypeMap(Map<Integer, String> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}
	public List<AvailableSeatsTo> getAvailableSeatsList() {
		return availableSeatsList;
	}
	public void setAvailableSeatsList(List<AvailableSeatsTo> availableSeatsList) {
		this.availableSeatsList = availableSeatsList;
	}
	public String getNumOfAvailableSeats() {
		return numOfAvailableSeats;
	}
	public void setNumOfAvailableSeats(String numOfAvailableSeats) {
		this.numOfAvailableSeats = numOfAvailableSeats;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}

	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	
	public void reset(){
		clear3();
		this.numOfAvailableSeats=null;
		this.flag=false;
		this.totalNumOfSeatsAvail=null;
		this.totalSeats=null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
}
