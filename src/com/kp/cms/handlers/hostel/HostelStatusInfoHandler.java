package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelStatusInfoForm;
import com.kp.cms.helpers.hostel.HostelStatusInfoHelper;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlFloorTo;
import com.kp.cms.to.hostel.HostelStatusInfoTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.transactions.hostel.IHostelStatusInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelStatusInfoTransactionImpl;

public class HostelStatusInfoHandler {

	private static volatile HostelStatusInfoHandler hostelStatusInfoHandler = null;
	IHostelStatusInfoTransaction ihostelStatusInfoTransaction = new HostelStatusInfoTransactionImpl();

	public static HostelStatusInfoHandler getInstance() throws Exception {
		if (hostelStatusInfoHandler == null) {
			hostelStatusInfoHandler = new HostelStatusInfoHandler();
		}
		return hostelStatusInfoHandler;
	}

	public HostelStatusInfoHandler() {

	}

	/**
	 * @param hostelStatusInfoTO
	 * @return
	 * @throws Exception
	 */
	public List<HostelStatusInfoTO> getHostelStatusRoomInfo(
			HostelStatusInfoTO hostelStatusInfoTO) throws Exception {
		List<Object> objList=null;	
		HlRoom room=null;
		HostelStatusInfoTO hStatusInfoTO=null;
		List<HostelStatusInfoTO> hlStatusInfoTOList=new ArrayList<HostelStatusInfoTO>();
		if(ihostelStatusInfoTransaction!=null){
			List<HlRoom> roomList=(List<HlRoom>)ihostelStatusInfoTransaction.getHostelStatusInfoDetails(hostelStatusInfoTO);
			java.util.Iterator<HlRoom> objIter=roomList.iterator();
			while(objIter.hasNext()){
				hStatusInfoTO=new HostelStatusInfoTO();
				room=(HlRoom)objIter.next();
				hStatusInfoTO.setHostelId(String.valueOf(room.getHlHostel().getId()));
				hStatusInfoTO.setFloorNo(room.getFloorNo());
				hStatusInfoTO.setRoomType(room.getHlRoomType().getName());
				hlStatusInfoTOList.add(hStatusInfoTO);
			}
		}
		return hlStatusInfoTOList;
	}
	
	/**
	 * @param hostelStatusInfoTO
	 * @return
	 * @throws Exception
	 */
	public List<HostelStatusInfoTO> dipslayRoomDetails(HostelStatusInfoTO hostelStatusInfoTO) throws Exception{
		List<Object> objList=null;		
		HostelStatusInfoTO hStatusInfoTO=null;
		List<HostelStatusInfoTO> roomTOList=new ArrayList<HostelStatusInfoTO>();
		if(ihostelStatusInfoTransaction!=null){
			objList=(List<Object>)ihostelStatusInfoTransaction.dipslayRoomDetails(hostelStatusInfoTO);			
			
			java.util.Iterator<Object> objIter=objList.iterator();
			int noOfRooms=0;
			while(objIter.hasNext()){
				hStatusInfoTO=new HostelStatusInfoTO();
				Object[] obj=(Object[])objIter.next();				
				
				hStatusInfoTO.setReturnRoomTypeName(String.valueOf(obj[0]));
				hStatusInfoTO.setRoomName(String.valueOf(obj[1]));
				hStatusInfoTO.setRoomTxnDate(String.valueOf(obj[2]));
				hStatusInfoTO.setStatusType(String.valueOf(obj[3]));					
				noOfRooms++;
				//hStatusInfoTO.setNoOfRooms(noOfRooms);
				roomTOList.add(hStatusInfoTO);
			}				
		}
		return roomTOList;
	}

	/**
	 * @param hostelStatusInfoForm
	 * @return
	 * @throws Exception
	 */
	public List<HlFloorTo> getHostelStatusInfo(HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
		List<HlFloorTo> floorTos =ihostelStatusInfoTransaction.getFloorsByHostel(Integer.parseInt(hostelStatusInfoForm.getHostelId()));
		List<HlFloorTo> finalList=new ArrayList<HlFloorTo>();
		if(!floorTos.isEmpty()){
			Iterator<HlFloorTo> itr=floorTos.iterator();
			while (itr.hasNext()) {
				HlFloorTo hlFloorTo = (HlFloorTo) itr.next();
				if(hostelStatusInfoForm.getFloorNo()!=null && !hostelStatusInfoForm.getFloorNo().isEmpty()){
					if(hostelStatusInfoForm.getFloorNo().equals(hlFloorTo.getName())){
						List<RoomTypeTO> roomTypeTOs=getRoomTypeListByHostelAndFloorNo(Integer.parseInt(hostelStatusInfoForm.getHostelId()),hlFloorTo.getName(),hostelStatusInfoForm);
						hlFloorTo.setRoomTypeTOs(roomTypeTOs);
						finalList.add(hlFloorTo);
					}
				}else{
					List<RoomTypeTO> roomTypeTOs=getRoomTypeListByHostelAndFloorNo(Integer.parseInt(hostelStatusInfoForm.getHostelId()),hlFloorTo.getName(),hostelStatusInfoForm);
					hlFloorTo.setRoomTypeTOs(roomTypeTOs);
					finalList.add(hlFloorTo);
				}
			}
		}
		return finalList;
	}

	private List<RoomTypeTO> getRoomTypeListByHostelAndFloorNo(int hostelId,String floorNO,HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
		List<HlRoomType> roomType=ihostelStatusInfoTransaction.getRoomTypeList(hostelId,floorNO);
		List<RoomTypeTO> finalRoomTypeList=HostelStatusInfoHelper.getInstance().convertBOtoToList(roomType,hostelStatusInfoForm);
		List<RoomTypeTO> finalList=new ArrayList<RoomTypeTO>();
		if(finalRoomTypeList!=null && !finalRoomTypeList.isEmpty()){
			finalList=ihostelStatusInfoTransaction.getFinalRoomTypeList(hostelId,floorNO,finalRoomTypeList,hostelStatusInfoForm);
		}
		return finalList;
	}
	
	/**
	 * @author venkat
	 * @param hostelStatusInfoForm
	 * @return Map<String, Map<String, List<HlAdmissionTo>>>
	 * @throws Exception
	 */
	public Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> getHostelDetailByHostelAndYear(HostelStatusInfoForm hostelStatusInfoForm) throws Exception
	{
		Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> hlAdmissionMap=null;
		Map<String,HlAdmissionBo> admMap=ihostelStatusInfoTransaction.getStudentAdmDeatils(hostelStatusInfoForm);
		if(!admMap.isEmpty()){
			List<HlBeds> hlBedList=ihostelStatusInfoTransaction.getRoomAndBedsDetailByHostelId(hostelStatusInfoForm);
			hlAdmissionMap=HostelStatusInfoHelper.getInstance().checkHostelDetails(admMap,hlBedList);
		}
		return hlAdmissionMap;
	}
}
