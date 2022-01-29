package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamRoomAvailability;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.ExamRoomAvailabilityForm;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class ExamRoomAvailabilityHelper {
	private static volatile ExamRoomAvailabilityHelper helper = null;
	public static ExamRoomAvailabilityHelper getInstance(){
		if(helper == null){
			helper = new ExamRoomAvailabilityHelper();
		}
		return helper;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getWorkLocationQuery()throws Exception {
		String hqlQuery = "from EmployeeWorkLocationBO empWorklocation where empWorklocation.isActive=1";
		return hqlQuery;
	}
	/**
	 * @param workLocationId
	 * @return
	 * @throws Exception
	 */
	public String getRoomAvailabilityDetailsQuery(int workLocationId)
			throws Exception {
		String hqlQuery = "from RoomMaster roomMaster where roomMaster.isActive =1  " +
							" and roomMaster.blockId.locationId=" +workLocationId;
		return hqlQuery;
	}
	/**
	 * @param roomMasterBoList
	 * @param roomIdsList 
	 * @return
	 * @throws Exception
	 */
	public List<RoomMasterTo> convertBoListToList( List<RoomMaster> roomMasterBoList, Map<Integer,Integer> roomIdsList) throws Exception {
		List<RoomMasterTo> roomMasterTosList = new ArrayList<RoomMasterTo>();
		if (roomMasterBoList != null && !roomMasterBoList.isEmpty()) {
			Iterator<RoomMaster> iterator = roomMasterBoList.iterator();
			while (iterator.hasNext()) {
				RoomMaster roomMaster = (RoomMaster) iterator.next();
				RoomMasterTo roomMasterTo = new RoomMasterTo();
				if (roomMaster.getId() > 0) {
					roomMasterTo.setId(roomMaster.getId());
				}
				if (roomMaster.getRoomNo() != null && !roomMaster.getRoomNo().isEmpty()) {
					roomMasterTo.setRoomNo(roomMaster.getRoomNo());
				}
				if (roomMaster.getBlockId() != null && roomMaster.getBlockId().getBlockName() != null
						&& !roomMaster.getBlockId().getBlockName().isEmpty()) {
					roomMasterTo.setBlockName(roomMaster.getBlockId() .getBlockName());
				}
				if (roomMaster.getFloor() != null && !roomMaster.getFloor().isEmpty()) {
					roomMasterTo.setFloor(roomMaster.getFloor());
				}
				if(roomIdsList.containsKey(roomMaster.getId())){
					roomMasterTo.setTempChecked("on");
					Integer examRoomAvailabilityId = roomIdsList.get(roomMaster.getId());
					roomMasterTo.setRoomAvailabilityId(examRoomAvailabilityId);
				}else{
					roomMasterTo.setTempChecked("off");
				}
				if(roomMaster.getMidSemCapacity()!=null && !roomMaster.getMidSemCapacity().isEmpty()){
					roomMasterTo.setMidSemCapacity(roomMaster.getMidSemCapacity());
				}
				if(roomMaster.getEndSemCapacity()!=null && !roomMaster.getEndSemCapacity().isEmpty()){
					roomMasterTo.setEndSemCapacity(roomMaster.getEndSemCapacity());
				}
					
				roomMasterTosList.add(roomMasterTo);
			}
		}
		return roomMasterTosList;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAvailability> getSelectedRoomBoList( ExamRoomAvailabilityForm objForm) throws Exception{
		List<ExamRoomAvailability> roomAvailabilitiesBoList = new ArrayList<ExamRoomAvailability>();
		List<RoomMasterTo> roomMasterToList = objForm.getRoomMasterTO();
		if(roomMasterToList!=null && !roomMasterToList.isEmpty()){
			Iterator<RoomMasterTo> iterator = roomMasterToList.iterator();
			while (iterator.hasNext()) {
				RoomMasterTo roomMasterTo = (RoomMasterTo) iterator.next();
				ExamRoomAvailability roomAvailabilityBo =null;
				RoomMaster roomMaster =null;
				if(roomMasterTo.getChecked()!=null){
					if(roomMasterTo.getChecked().equalsIgnoreCase("on")){
						roomAvailabilityBo= new ExamRoomAvailability();
						roomMaster = new RoomMaster();
						roomMaster.setId(roomMasterTo.getId());
						roomAvailabilityBo.setId(roomMasterTo.getRoomAvailabilityId());
						roomAvailabilityBo.setRoomMaster(roomMaster);
						roomAvailabilityBo.setCreatedBy(objForm.getUserId());
						roomAvailabilityBo.setLastModifiedBy(new Date());
						roomAvailabilityBo.setModifiedBy(objForm.getUserId());
						roomAvailabilityBo.setCreatedDate(new Date());
						roomAvailabilityBo.setIsActive(true);
					}
				}else if(roomMasterTo.getChecked()==null && roomMasterTo.getTempChecked().equalsIgnoreCase("on")){
						roomAvailabilityBo= new ExamRoomAvailability();
						roomMaster = new RoomMaster();
						roomMaster.setId(roomMasterTo.getId());
						roomAvailabilityBo.setId(roomMasterTo.getRoomAvailabilityId());
						roomAvailabilityBo.setRoomMaster(roomMaster);
						roomAvailabilityBo.setModifiedBy(objForm.getUserId());
						roomAvailabilityBo.setLastModifiedBy(new Date());
						roomAvailabilityBo.setIsActive(false);
				}
				if(roomAvailabilityBo!=null && !roomAvailabilityBo.toString().isEmpty()){
					roomAvailabilitiesBoList.add(roomAvailabilityBo);
				}
				
			}
		}
		if(roomAvailabilitiesBoList.size()<=0){
			throw new ApplicationException();
		}
		return roomAvailabilitiesBoList;
	}
	/**
	 * @param objForm
	 * @return
	 */
	public String getAlreadyAllotedRoomsQuery(int workLocationId) {
		String hqlQuery = "from ExamRoomAvailability roomAva where roomAva.isActive=1 " +
							" and roomAva.roomMaster.blockId.locationId="+workLocationId;
		return hqlQuery;
	}

}
