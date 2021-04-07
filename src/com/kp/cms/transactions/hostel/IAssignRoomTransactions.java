package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;

public interface IAssignRoomTransactions {
	public boolean addRooms(List<HlRoom> roomBOList) throws Exception;
	public Boolean isRoomNoDuplicated(int id,String floorNo) throws Exception;
	public List<HlRoom> getRoomsByHostelAndFloor(int hostelId,int floorNo, String blockId, String unitId) throws Exception;
	public boolean deleteRoom(Integer roomNo)throws Exception;	
	public boolean updateRooms(List<HlRoom> roomBOList) throws Exception;
	public Map<String, Integer> getRoomsByHostelAndFloorForDuplicateCheck(
			int hostelId, int floorNo) throws Exception;
	public boolean getRoomsByHostelAndFloorForDuplicateCheck1(AssignRoomMasterForm roomForm) throws Exception;
}
