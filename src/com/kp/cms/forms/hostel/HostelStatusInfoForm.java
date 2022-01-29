package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlFloorTo;
import com.kp.cms.to.hostel.HostelStatusInfoTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelStatusInfoForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostelName;
	private String roomStatusOnDate;
	private String roomType[];	
	private String method;
	private List<HostelStatusInfoTO> hostelRoomStatusInfoDetailsList;
	private List<HostelStatusInfoTO> roomTOList;
	private List<HlFloorTo> floorList;
	private Map<Integer, String> unitMap;
	private Map<Integer, String> blockMap; 
	private Map<Integer, String> hostelMap;
	private Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> hlAdmissionMap;
	private String year;
	
	public List<HostelStatusInfoTO> getRoomTOList() {
		return roomTOList;
	}
	public void setRoomTOList(List<HostelStatusInfoTO> roomTOList) {
		this.roomTOList = roomTOList;
	}
	public String[] getRoomType() {
		return roomType;
	}
	public void setRoomType(String[] roomType) {
		this.roomType = roomType;
	}
	
	public List<HostelStatusInfoTO> getHostelRoomStatusInfoDetailsList() {
		return hostelRoomStatusInfoDetailsList;
	}
	public void setHostelRoomStatusInfoDetailsList(
			List<HostelStatusInfoTO> hostelRoomStatusInfoDetailsList) {
		this.hostelRoomStatusInfoDetailsList = hostelRoomStatusInfoDetailsList;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	private List<HostelTO> hostelList;
	
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}

	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getRoomStatusOnDate() {
		return roomStatusOnDate;
	}
	public void setRoomStatusOnDate(String roomStatusOnDate) {
		this.roomStatusOnDate = roomStatusOnDate;
	}
	
	public List<HlFloorTo> getFloorList() {
		return floorList;
	}
	public void setFloorList(List<HlFloorTo> floorList) {
		this.floorList = floorList;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}	
	
	public void clearFormFields() {
		super.setFloorNo(null);
		super.setHostelId(null);
		this.roomStatusOnDate=null;
		this.roomType=null;
		this.hlAdmissionMap = null;
		this.blockMap = null;
		this.unitMap = null;
		this.year = null;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> getHlAdmissionMap() {
		return hlAdmissionMap;
	}
	public void setHlAdmissionMap(
			Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> hlAdmissionMap) {
		this.hlAdmissionMap = hlAdmissionMap;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
