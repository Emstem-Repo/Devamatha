package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelStatusInfoForm;
import com.kp.cms.to.hostel.HlFloorTo;
import com.kp.cms.to.hostel.HostelStatusInfoTO;
import com.kp.cms.to.hostel.RoomTypeTO;

public interface IHostelStatusInfoTransaction {
	//public List<Object> getHostelStatusInfoDetails(HostelStatusInfoTO hostelStatusInfoTO) throws Exception;
	public List<HlRoom> getHostelStatusInfoDetails(HostelStatusInfoTO hostelStatusInfoTO) throws Exception;
	public List<Object> dipslayRoomDetails(HostelStatusInfoTO hostelStatusInfoTO) throws Exception;
	public List<HlFloorTo> getFloorsByHostel(int parseInt) throws Exception;
	public List<HlRoomType> getRoomTypeList(int parseInt, String name) throws Exception;
	public List<RoomTypeTO> getFinalRoomTypeList(int hostelId, String floorNO,List<RoomTypeTO> finalList,HostelStatusInfoForm hostelStatusInfoForm) throws Exception;
	
	public Map<String,HlAdmissionBo> getStudentAdmDeatils(HostelStatusInfoForm hostelStatusInfoForm)throws Exception;
	 public List<HlBeds> getRoomAndBedsDetailByHostelId(HostelStatusInfoForm hostelStatusInfoForm)throws Exception;
}
