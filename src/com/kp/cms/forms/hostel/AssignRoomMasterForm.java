package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.AssignRoomMasterTo;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;

public class AssignRoomMasterForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String floorNo;
	private String noOfRooms;
	private List<HlRoomTO> roomList;
	private String roomTypeId;
	private String hostelName;
	private List<RoomTypeTO> roomTypeList;
	private String roomNo;
	private Boolean formValueDupl;
	private AssignRoomMasterTo roomMaster;
	private String searchedFloorNumber;
	private String searchedHostelId;
	private String searchedHostelName;
	private String createdBy;
	private String createdDate;
	private Map<Integer, String> unitMap;
	private Map<Integer, String> blockMap;
	private Map<Integer, String> floorMap;
	private String unit;
	private String block;
	private String unitId;
	private String blockId;
	private List<HostelTO> hostelList = new ArrayList<HostelTO>();
	private String unitName;
	private String blockName;
	private String unitNameForEdit;
	private String blockNameForEdit;
	private Map<Integer, Integer> roomTypeMap;
	
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public List<HlRoomTO> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<HlRoomTO> roomList) {
		this.roomList = roomList;
	}
	public int getId() {
		return id;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public String getNoOfRooms() {
		return noOfRooms;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public void setNoOfRooms(String noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	
	public List<RoomTypeTO> getRoomTypeList() {
		return roomTypeList;
	}
	public void setRoomTypeList(List<RoomTypeTO> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	public Boolean getFormValueDupl() {
		return formValueDupl;
	}
	public void setFormValueDupl(Boolean formValueDupl) {
		this.formValueDupl = formValueDupl;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public AssignRoomMasterTo getRoomMaster() {
		return roomMaster;
	}
	public void setRoomMaster(AssignRoomMasterTo roomMaster) {
		this.roomMaster = roomMaster;
	}
	public String getSearchedFloorNumber() {
		return searchedFloorNumber;
	}
	public void setSearchedFloorNumber(String searchedFloorNumber) {
		this.searchedFloorNumber = searchedFloorNumber;
	}
	public String getSearchedHostelId() {
		return searchedHostelId;
	}
	public void setSearchedHostelId(String searchedHostelId) {
		this.searchedHostelId = searchedHostelId;
	}
	public String getSearchedHostelName() {
		return searchedHostelName;
	}
	public void setSearchedHostelName(String searchedHostelName) {
		this.searchedHostelName = searchedHostelName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
	public Map<Integer, String> getFloorMap() {
		return floorMap;
	}
	public void setFloorMap(Map<Integer, String> floorMap) {
		this.floorMap = floorMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public Map<Integer, Integer> getRoomTypeMap() {
		return roomTypeMap;
	}
	public void setRoomTypeMap(Map<Integer, Integer> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getUnitNameForEdit() {
		return unitNameForEdit;
	}
	public void setUnitNameForEdit(String unitNameForEdit) {
		this.unitNameForEdit = unitNameForEdit;
	}
	public String getBlockNameForEdit() {
		return blockNameForEdit;
	}
	public void setBlockNameForEdit(String blockNameForEdit) {
		this.blockNameForEdit = blockNameForEdit;
	}
	
	
	
}
