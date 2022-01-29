package com.kp.cms.forms.studentfeedback;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class RoomMasterForm extends BaseActionForm
{
	private int id;
	private String roomId;
    private String blockId;
    private String locationId;
    private String floor;
    private String floorName;
    private String roomNo;
    private String totalCapacity;
    private String endSemCapacity;
    private String endSemTotalColumn;
    private String endSemSeatInDesk;
    private String midSemCapacity;
    private String midSemTotalColumn;
    private String midSemSeatInDesk;
    private List<RoomMasterTo> roomMasterList;
    private List<RoomEndMidSemRowsTo> endSemList;
    private List<RoomEndMidSemRowsTo> midSemList;
    private Map<Integer, String> blockMap;
    private String totalEndSem;
    private String totalMidSem;

    
    public void resetClear()
    {
        id = 0;
        roomId =null;
        floor = null;
        floorName=null;
        roomNo = null;
        totalCapacity = null;
        endSemCapacity = null;
        endSemTotalColumn = null;
        endSemSeatInDesk = null;
        midSemCapacity = null;
        midSemTotalColumn = null;
        midSemSeatInDesk = null;
        midSemList=null;
        roomMasterList=null;
        endSemList=null;
        totalEndSem=null;
        totalMidSem=null;
        
    }
    
    public void resetClear1()
    {
        id = 0;
        roomId =null;
        floor = null;
        floorName=null;
        roomNo = null;
        totalCapacity = null;
        endSemCapacity = null;
        endSemTotalColumn = null;
        endSemSeatInDesk = null;
        midSemCapacity = null;
        midSemTotalColumn = null;
        midSemSeatInDesk = null;
        midSemList=null;
        roomMasterList=null;
        endSemList=null;
        blockId=null;
        locationId=null;
        totalEndSem=null;
        totalMidSem=null;
        
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(String totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getEndSemCapacity() {
		return endSemCapacity;
	}

	public void setEndSemCapacity(String endSemCapacity) {
		this.endSemCapacity = endSemCapacity;
	}

	public String getEndSemTotalColumn() {
		return endSemTotalColumn;
	}

	public void setEndSemTotalColumn(String endSemTotalColumn) {
		this.endSemTotalColumn = endSemTotalColumn;
	}

	public String getEndSemSeatInDesk() {
		return endSemSeatInDesk;
	}

	public void setEndSemSeatInDesk(String endSemSeatInDesk) {
		this.endSemSeatInDesk = endSemSeatInDesk;
	}

	public String getMidSemCapacity() {
		return midSemCapacity;
	}

	public void setMidSemCapacity(String midSemCapacity) {
		this.midSemCapacity = midSemCapacity;
	}

	public String getMidSemTotalColumn() {
		return midSemTotalColumn;
	}

	public void setMidSemTotalColumn(String midSemTotalColumn) {
		this.midSemTotalColumn = midSemTotalColumn;
	}

	public String getMidSemSeatInDesk() {
		return midSemSeatInDesk;
	}

	public void setMidSemSeatInDesk(String midSemSeatInDesk) {
		this.midSemSeatInDesk = midSemSeatInDesk;
	}

	public List<RoomEndMidSemRowsTo> getEndSemList() {
		return endSemList;
	}

	public void setEndSemList(List<RoomEndMidSemRowsTo> endSemList) {
		this.endSemList = endSemList;
	}

	public List<RoomEndMidSemRowsTo> getMidSemList() {
		return midSemList;
	}

	public void setMidSemList(List<RoomEndMidSemRowsTo> midSemList) {
		this.midSemList = midSemList;
	}

	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}

	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}

	public List<RoomMasterTo> getRoomMasterList() {
		return roomMasterList;
	}

	public void setRoomMasterList(List<RoomMasterTo> roomMasterList) {
		this.roomMasterList = roomMasterList;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getTotalEndSem() {
		return totalEndSem;
	}

	public void setTotalEndSem(String totalEndSem) {
		this.totalEndSem = totalEndSem;
	}

	public String getTotalMidSem() {
		return totalMidSem;
	}

	public void setTotalMidSem(String totalMidSem) {
		this.totalMidSem = totalMidSem;
	}

}
