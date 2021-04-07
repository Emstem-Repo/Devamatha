package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.to.hostel.AssignRoomMasterTo;
import com.kp.cms.to.hostel.HlBedsTO;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.utilities.CommonUtil;

public class AssignRoomMasterHelper {
	private static final Log log = LogFactory.getLog(AssignRoomMasterHelper.class);
	private static volatile AssignRoomMasterHelper assignRoomMasterHelper = null;

	public static AssignRoomMasterHelper getInstance() {
		if (assignRoomMasterHelper == null) {
			assignRoomMasterHelper = new AssignRoomMasterHelper();
		}
		return assignRoomMasterHelper;
	}
	/**
	 * copy form values to BO
	 * @param roomForm
	 * @return
	 * @throws Exception
	 */
	public List<HlRoom> populateRoomDetails(AssignRoomMasterForm roomForm) throws Exception {
		log.debug("AssignRoomMasterHelper");
		List<HlRoom> roomBoList = new ArrayList<HlRoom>();
		Iterator<HlRoomTO> tcIter = roomForm.getRoomList().iterator(); 
		HlRoomTO hlRoomTo;
		HlHostel hlHostel = new HlHostel();
		HlBlocks blocks = new HlBlocks();
		HlUnits units = new HlUnits();
		if(roomForm.getHostelId()!= null && !roomForm.getHostelId().trim().isEmpty()){
			hlHostel.setId(Integer.parseInt(roomForm.getHostelId()));
		}
		if(roomForm.getBlock() != null && !roomForm.getBlock().isEmpty()){
			blocks.setId(Integer.parseInt(roomForm.getBlock()));
		}
		if(roomForm.getUnit() != null && !roomForm.getUnit().isEmpty()){
			units.setId(Integer.parseInt(roomForm.getUnit()));
		}
		while(tcIter.hasNext()){
			HlRoom hlRoom = new HlRoom();
			HlRoomType hlRoomType = new HlRoomType();
			hlRoomTo = tcIter.next();
			if(hlRoomTo.getName()== null || hlRoomTo.getName().trim().isEmpty()){
				continue;
			}
			if(hlRoomTo.getRoomTypeId()!= 0){
				hlRoomType.setId(hlRoomTo.getRoomTypeId());
			}
			if(hlRoomTo.getRoomId() !=0){
				hlRoom.setId(hlRoomTo.getRoomId());
			}
			hlRoom.setHlHostel(hlHostel);
			hlRoom.setHlBlock(blocks);
			hlRoom.setHlUnit(units);
			hlRoom.setName(hlRoomTo.getName());
			hlRoom.setHlRoomType(hlRoomType);
			if(roomForm.getFloorNo()!= null && !roomForm.getFloorNo().trim().isEmpty()){
				hlRoom.setFloorNo(roomForm.getFloorNo());
			}
			if(hlRoomTo.getHlBeds() != null){
				Set<HlBeds> hlBeds = new HashSet<HlBeds>();
				Iterator<HlBedsTO> iterator = hlRoomTo.getHlBeds().iterator();
				while (iterator.hasNext()) {
					HlBedsTO to = (HlBedsTO) iterator.next();
					HlBeds bed = new HlBeds();
					if(to.getId() !=0){
						bed.setId(to.getId());
						bed.setHlRoom(hlRoom);
					}
					bed.setBedNo(to.getBedNo());
					if(to.getIsActive() != null)
						bed.setIsActive(to.getIsActive());
					else
						bed.setIsActive(true);
					bed.setCreatedDate(new Date());
					bed.setLastModifiedDate(new Date());
					bed.setCreatedBy(roomForm.getUserId());
					bed.setModifiedBy(roomForm.getUserId());
					hlBeds.add(bed);
				}
				hlRoom.setHlBeds(hlBeds);
			}
			
			hlRoom.setCreatedDate(new Date());
			hlRoom.setLastModifiedDate(new Date());
			hlRoom.setCreatedBy(roomForm.getUserId());
			hlRoom.setModifiedBy(roomForm.getUserId());
			hlRoom.setIsActive(true);
			roomBoList.add(hlRoom);
		}
		log.debug("exit AssignRoomMasterHelper");	
		return roomBoList;
		
	}
	public AssignRoomMasterTo getRoomMasterTo(List<HlRoom> roomList) {
		AssignRoomMasterTo roomMasterTo=null;
		if(roomList.size()!=0)
		{	
			roomMasterTo=new AssignRoomMasterTo();
			roomMasterTo.setHostelName(roomList.get(0).getHlHostel().getName());
			roomMasterTo.setFloorNo(roomList.get(0).getFloorNo());
			roomMasterTo.setRoomNames("");
			for(int i=0;i<roomList.size();i++)
			{
				if(i<roomList.size()-1)
					roomMasterTo.setRoomNames(roomMasterTo.getRoomNames()+roomList.get(i).getName()+",");
				else
					roomMasterTo.setRoomNames(roomMasterTo.getRoomNames()+roomList.get(i).getName());
					
			}
		}	
		return roomMasterTo;
	}
	public List<HlRoomTO> getRoomToList(List<HlRoom> roomList,AssignRoomMasterForm roomForm) 
	{
		List<HlRoomTO>roomToList=new ArrayList<HlRoomTO>();
		HlRoomTO roomTo=null;
		if(roomList.size()!=0)
		{
			roomForm.setCreatedBy(roomList.get(0).getCreatedBy());
			roomForm.setCreatedDate(CommonUtil.getStringDate(roomList.get(0).getCreatedDate()));
		}
		for(HlRoom room:roomList)
		{
			roomTo=new HlRoomTO();
			roomTo.setRoomId(room.getId());
			roomTo.setName(room.getName());
			roomTo.setRoomTypeId(room.getHlRoomType().getId());
			if(room.getHlBeds() != null){
				List<HlBedsTO> hlBeds = new ArrayList<HlBedsTO>();
				Iterator<HlBeds> iterator = room.getHlBeds().iterator();
				while (iterator.hasNext()) {
					HlBeds bo = (HlBeds) iterator.next();
					if(bo.getIsActive()){
						HlBedsTO to = new HlBedsTO();
						to.setId(bo.getId());
						to.setBedNo(bo.getBedNo());
						to.setIsActive(bo.getIsActive());
						hlBeds.add(to);
					}
				}
				Collections.sort(hlBeds);
				roomTo.setHlBeds(hlBeds);
			}
			roomToList.add(roomTo);
		}
		return roomToList;
	}
	
	public List<HlRoom> populateRoomDetailsForUpdate(AssignRoomMasterForm roomForm) throws Exception {
		log.debug("AssignRoomMasterHelper");
		List<HlRoom> roomBoList = new ArrayList<HlRoom>();
		Iterator<HlRoomTO> tcIter = roomForm.getRoomList().iterator(); 
		HlHostel hlHostel = new HlHostel();
		HlBlocks blocks = new HlBlocks();
		HlUnits units = new HlUnits();
		if(roomForm.getSearchedHostelId()!= null && !roomForm.getSearchedHostelId().trim().isEmpty()){
			hlHostel.setId(Integer.parseInt(roomForm.getSearchedHostelId()));
		}
		if(roomForm.getBlockId() != null && !roomForm.getBlockId().isEmpty()){
			blocks.setId(Integer.parseInt(roomForm.getBlockId()));
		}
		if(roomForm.getUnitId() != null && !roomForm.getUnitId().isEmpty()){
			units.setId(Integer.parseInt(roomForm.getUnitId()));
		}
		while(tcIter.hasNext()){
			HlRoom hlRoom = new HlRoom();
			HlRoomType hlRoomType = new HlRoomType();
			HlRoomTO hlRoomTo = tcIter.next();
			if(hlRoomTo.getName()== null || hlRoomTo.getName().trim().isEmpty()){
				continue;
			}
			if(hlRoomTo.getRoomTypeId()!= 0){
				hlRoomType.setId(hlRoomTo.getRoomTypeId());
			}
			hlRoom.setId(hlRoomTo.getRoomId());
			hlRoom.setHlHostel(hlHostel);
			hlRoom.setHlBlock(blocks);
			hlRoom.setHlUnit(units);
			hlRoom.setName(hlRoomTo.getName());
			hlRoom.setHlRoomType(hlRoomType);
			if(roomForm.getSearchedFloorNumber()!= null && !roomForm.getSearchedFloorNumber().trim().isEmpty()){
				hlRoom.setFloorNo(roomForm.getSearchedFloorNumber());
			}
			if(hlRoomTo.getHlBeds() != null){
				Set<HlBeds> hlBeds = new HashSet<HlBeds>();
				Iterator<HlBedsTO> iterator = hlRoomTo.getHlBeds().iterator();
				while (iterator.hasNext()) {
					HlBedsTO to = (HlBedsTO) iterator.next();
					HlBeds bed = new HlBeds();
					bed.setId(to.getId());
					bed.setHlRoom(hlRoom);
					bed.setBedNo(to.getBedNo());
					if(to.getIsActive() != null)
						bed.setIsActive(to.getIsActive());
					else
						bed.setIsActive(true);
					bed.setCreatedDate(new Date());
					bed.setLastModifiedDate(new Date());
					bed.setCreatedBy(roomForm.getUserId());
					bed.setModifiedBy(roomForm.getUserId());
					hlBeds.add(bed);
				}
				hlRoom.setHlBeds(hlBeds);
			}
			hlRoom.setCreatedDate(CommonUtil.ConvertsqlStringToDate(roomForm.getCreatedDate()));
			hlRoom.setLastModifiedDate(new Date());
			hlRoom.setCreatedBy(roomForm.getCreatedBy());
			hlRoom.setModifiedBy(roomForm.getUserId());
			hlRoom.setIsActive(true);
			roomBoList.add(hlRoom);
		}
		log.debug("exit AssignRoomMasterHelper");	
		return roomBoList;
		
	}
	
}
