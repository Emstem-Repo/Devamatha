package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.studentfeedback.RoomMasterForm;
import com.kp.cms.to.studentfeedback.BlockBoTo;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class RoomMasterHelpers
{

	private static final Log log=LogFactory.getLog(RoomMasterHelpers.class);
	public static volatile RoomMasterHelpers roomMasterHelpers = null;
   

    public static RoomMasterHelpers getInstance()
    {
        if(roomMasterHelpers == null)
        {
        	roomMasterHelpers = new RoomMasterHelpers();
            return roomMasterHelpers;
        } else
        {
            return roomMasterHelpers;
        }
    }

    public List<BlockBoTo> convertBosToTOs(List<EmployeeWorkLocationBO> locationBos)
    {
        List<BlockBoTo> locationList = new ArrayList<BlockBoTo>();
        if(locationBos != null)
        {
        	Iterator <EmployeeWorkLocationBO> iterator=locationBos.iterator();
			while(iterator.hasNext())
			{
				EmployeeWorkLocationBO locationBo = (EmployeeWorkLocationBO)iterator.next();
				BlockBoTo locationTo = new BlockBoTo();
				locationTo.setEmpLocationId(locationBo.getId());
				locationTo.setEmpLocationName(locationBo.getName());
				locationList.add(locationTo);
            }

        }
        return locationList;
    }
    /**
     * @param blockList
     * @return
     */
    public List<RoomMasterTo> convertBoToTos(List<RoomMaster> blockList)
    {
        List<RoomMasterTo> blockToList= new ArrayList<RoomMasterTo>();
        if(blockList != null)
        {
        	Iterator<RoomMaster> itr=blockList.iterator();
    		while (itr.hasNext()) {
    			RoomMaster roomMaster = (RoomMaster)itr.next();
    			RoomMasterTo roomMasterTo= new RoomMasterTo();
    			
    			roomMasterTo.setId(roomMaster.getId());
    			if(roomMaster.getBlockId().getLocationId()!=null && roomMaster.getBlockId().getLocationId().getId()>0)
    			roomMasterTo.setLocationId(String.valueOf(roomMaster.getBlockId().getLocationId().getId()));
    			if(roomMaster.getBlockId().getLocationId().getName()!=null && !roomMaster.getBlockId().getLocationId().getName().isEmpty())
    			roomMasterTo.setLocationName(roomMaster.getBlockId().getLocationId().getName());
    			if(roomMaster.getBlockId().getBlockName()!=null && !roomMaster.getBlockId().getBlockName().isEmpty())
    			roomMasterTo.setBlockName(roomMaster.getBlockId().getBlockName());
    			if(roomMaster.getBlockId()!=null && roomMaster.getBlockId().getId()>0)
    			roomMasterTo.setBlockId(Integer.toString(roomMaster.getBlockId().getId()));
    			if(roomMaster.getFloor()!=null && !roomMaster.getFloor().isEmpty())
    			roomMasterTo.setFloor(roomMaster.getFloor());
    			if(roomMaster.getFloorName()!=null && !roomMaster.getFloorName().isEmpty())
    			roomMasterTo.setFloorName(roomMaster.getFloorName());
    			if(roomMaster.getRoomNo()!=null && !roomMaster.getRoomNo().isEmpty())
    			roomMasterTo.setRoomNo(roomMaster.getRoomNo());
    			if(roomMaster.getTotalCapacity()!=null && !roomMaster.getTotalCapacity().isEmpty())
    			roomMasterTo.setTotalCapacity(roomMaster.getTotalCapacity());
    			if(roomMaster.getEndSemCapacity()!=null && !roomMaster.getEndSemCapacity().isEmpty())
    			roomMasterTo.setEndSemCapacity(roomMaster.getEndSemCapacity());
    			if(roomMaster.getEndSemTotalColumn()!=null && !roomMaster.getEndSemTotalColumn().isEmpty())
    			roomMasterTo.setEndSemTotalColumn(roomMaster.getEndSemTotalColumn());
    			if(roomMaster.getMidSemCapacity()!=null && !roomMaster.getMidSemCapacity().isEmpty())
    			roomMasterTo.setMidSemCapacity(roomMaster.getMidSemCapacity());
    			if(roomMaster.getMidSemTotalColumn()!=null && !roomMaster.getMidSemTotalColumn().isEmpty())
    			roomMasterTo.setMidSemTotalColumn(roomMaster.getMidSemTotalColumn());
    			blockToList.add(roomMasterTo);
            }

        }
        return blockToList;
    }
    /**
     * @param roomMasterForm
     * @return
     */
    public RoomMaster convertFormToBos(RoomMasterForm roomMasterForm)
    {
    	RoomMaster roomMaster = new RoomMaster();
    	Set<RoomEndMidSemRows> endMidSemRows=new HashSet<RoomEndMidSemRows>();
    	
    	if(roomMasterForm.getRoomId()!=null && !roomMasterForm.getRoomId().isEmpty()){
    		roomMaster.setId(Integer.parseInt(roomMasterForm.getRoomId()));
    	}
    	BlockBo type = new BlockBo();
        type.setId(Integer.parseInt(roomMasterForm.getBlockId()));
        roomMaster.setBlockId(type);
        roomMaster.setFloor(roomMasterForm.getFloor());
        roomMaster.setFloorName(roomMasterForm.getFloorName());
        roomMaster.setRoomNo(roomMasterForm.getRoomNo());
        roomMaster.setTotalCapacity(roomMasterForm.getTotalCapacity());
        roomMaster.setEndSemCapacity(roomMasterForm.getEndSemCapacity());
        roomMaster.setEndSemTotalColumn(roomMasterForm.getEndSemTotalColumn());
        roomMaster.setEndSemSeatInDesk(roomMasterForm.getEndSemSeatInDesk());
        roomMaster.setMidSemCapacity(roomMasterForm.getMidSemCapacity());
        roomMaster.setMidSemTotalColumn(roomMasterForm.getMidSemTotalColumn());
        roomMaster.setMidSemSeatInDesk(roomMasterForm.getMidSemSeatInDesk());
        
        if(roomMasterForm.getEndSemList()!=null && !roomMasterForm.getEndSemList().isEmpty()){
        	Iterator<RoomEndMidSemRowsTo> itr=roomMasterForm.getEndSemList().iterator();
        	while (itr.hasNext()) {
        		RoomEndMidSemRowsTo roomMasterTo = (RoomEndMidSemRowsTo) itr.next();
				RoomEndMidSemRows roomMasterBo=new RoomEndMidSemRows();
				if(roomMasterTo.getEndColumnNumber()!=null && roomMasterTo.getEndColumnNumber()>0){
					
					if(roomMasterTo.getId()>0){
						roomMasterBo.setId(roomMasterTo.getId());
					}
					if(roomMasterTo.getEndSemNoOfRows()!=null && !roomMasterTo.getEndSemNoOfRows().isEmpty()){
						roomMasterBo.setNoOfRows(roomMasterTo.getEndSemNoOfRows());
					}
					if(roomMasterTo.getEndColumnNumber()!=null && roomMasterTo.getEndColumnNumber()>0){
						roomMasterBo.setColumnNumber(Integer.toString(roomMasterTo.getEndColumnNumber()));
					}
					if(roomMasterTo.getEndSemSeatInDesk()!=null && !roomMasterTo.getEndSemSeatInDesk().isEmpty()){
						roomMasterBo.setNoOfSetInDesk(Integer.parseInt(roomMasterTo.getEndSemSeatInDesk()));
					}
					roomMasterBo.setEndMidSem("E");
					roomMasterBo.setIsActive(true);
					roomMasterBo.setCreatedBy(roomMasterForm.getUserId());
					roomMasterBo.setCreatedDate(new Date());
					roomMasterBo.setModifiedBy(roomMasterForm.getUserId());
					roomMasterBo.setLastModifiedDate(new Date());
					endMidSemRows.add(roomMasterBo);
				}
			}
        	
        }
        if(roomMasterForm.getMidSemList()!=null && !roomMasterForm.getMidSemList().isEmpty()){
        	Iterator<RoomEndMidSemRowsTo> itr=roomMasterForm.getMidSemList().iterator();
        	while (itr.hasNext()) {
        		RoomEndMidSemRowsTo roomMasterTo = (RoomEndMidSemRowsTo) itr.next();
				RoomEndMidSemRows roomMasterBo=new RoomEndMidSemRows();
				if(roomMasterTo.getMidColumnNumber()!=null && roomMasterTo.getMidColumnNumber()>0){
					
					if(roomMasterTo.getId()>0){
						roomMasterBo.setId(roomMasterTo.getId());
					}
					if(roomMasterTo.getMidSemNoOfRows()!=null && !roomMasterTo.getMidSemNoOfRows().isEmpty()){
						roomMasterBo.setNoOfRows(roomMasterTo.getMidSemNoOfRows());
					}
					if(roomMasterTo.getMidColumnNumber()!=null && roomMasterTo.getMidColumnNumber()>0){
						roomMasterBo.setColumnNumber(Integer.toString(roomMasterTo.getMidColumnNumber()));
					}
					if(roomMasterTo.getMidSemSeatInDesk()!=null && !roomMasterTo.getMidSemSeatInDesk().isEmpty()){
						roomMasterBo.setNoOfSetInDesk(Integer.parseInt(roomMasterTo.getMidSemSeatInDesk()));
					}
					roomMasterBo.setEndMidSem("M");
					roomMasterBo.setIsActive(true);
					roomMasterBo.setCreatedBy(roomMasterForm.getUserId());
					roomMasterBo.setCreatedDate(new Date());
					roomMasterBo.setModifiedBy(roomMasterForm.getUserId());
					roomMasterBo.setLastModifiedDate(new Date());
					endMidSemRows.add(roomMasterBo);
				}
			}
        	
        }
        roomMaster.setEndMidSemRows(endMidSemRows);
        
        roomMaster.setCreatedBy(roomMasterForm.getUserId());
        roomMaster.setCreatedDate(new Date());
        roomMaster.setLastModifiedDate(new Date());
        roomMaster.setModifiedBy(roomMasterForm.getUserId());
        roomMaster.setIsActive(Boolean.valueOf(true));
        return roomMaster;
    }
    /**
     * @param roomMasterForm
     * @param roomMaster
     */
    public void setDataBoToForm(RoomMasterForm roomMasterForm, RoomMaster roomMaster)
    {
        if(roomMaster != null)
        {
        	roomMasterForm.setRoomId(Integer.toString(roomMaster.getId()));
            roomMasterForm.setLocationId(String.valueOf(roomMaster.getBlockId().getLocationId().getId()));
            roomMasterForm.setBlockId(String.valueOf(roomMaster.getBlockId().getId()));
            roomMasterForm.setFloor(roomMaster.getFloor());
            if(roomMaster.getFloorName()!=null && !roomMaster.getFloorName().isEmpty())
            roomMasterForm.setFloorName(roomMaster.getFloorName());
            roomMasterForm.setRoomNo(roomMaster.getRoomNo());
            if(roomMaster.getTotalCapacity()!=null && !roomMaster.getTotalCapacity().isEmpty())
            roomMasterForm.setTotalCapacity(roomMaster.getTotalCapacity());
            if(roomMaster.getEndSemCapacity()!=null && !roomMaster.getEndSemCapacity().isEmpty())
            roomMasterForm.setEndSemCapacity(roomMaster.getEndSemCapacity());
            if(roomMaster.getEndSemTotalColumn()!=null && !roomMaster.getEndSemTotalColumn().isEmpty())
            roomMasterForm.setEndSemTotalColumn(roomMaster.getEndSemTotalColumn());
            if(roomMaster.getEndSemSeatInDesk()!=null && !roomMaster.getEndSemSeatInDesk().isEmpty())
            roomMasterForm.setEndSemSeatInDesk(roomMaster.getEndSemSeatInDesk());
            if(roomMaster.getMidSemCapacity()!=null && !roomMaster.getMidSemCapacity().isEmpty())
            roomMasterForm.setMidSemCapacity(roomMaster.getMidSemCapacity());
            if(roomMaster.getMidSemTotalColumn()!=null && !roomMaster.getMidSemTotalColumn().isEmpty())
            roomMasterForm.setMidSemTotalColumn(roomMaster.getMidSemTotalColumn());
            roomMasterForm.setMidSemSeatInDesk(roomMaster.getMidSemSeatInDesk());
            
            if(roomMaster.getEndMidSemRows()!=null && !roomMaster.getEndMidSemRows().isEmpty()){
            	Iterator<RoomEndMidSemRows> itr=roomMaster.getEndMidSemRows().iterator();
            	ArrayList<RoomEndMidSemRowsTo> endList=new ArrayList<RoomEndMidSemRowsTo>();
            	ArrayList<RoomEndMidSemRowsTo> midList=new ArrayList<RoomEndMidSemRowsTo>();
            	while (itr.hasNext()) {
					RoomEndMidSemRows roomEndMidSemRows = (RoomEndMidSemRows) itr.next();
					if(roomEndMidSemRows.getEndMidSem().equalsIgnoreCase("E")){
						   RoomEndMidSemRowsTo endSemRowsTo=new RoomEndMidSemRowsTo();
						   endSemRowsTo.setId(roomEndMidSemRows.getId());
						   if(roomEndMidSemRows.getColumnNumber()!=null && !roomEndMidSemRows.getColumnNumber().isEmpty())
						   endSemRowsTo.setEndColumnNumber(Integer.parseInt(roomEndMidSemRows.getColumnNumber()));
						   if(roomEndMidSemRows.getNoOfRows()!=null && !roomEndMidSemRows.getNoOfRows().isEmpty())
						   endSemRowsTo.setEndSemNoOfRows(roomEndMidSemRows.getNoOfRows());
						   if(roomEndMidSemRows.getNoOfSetInDesk()!=null && roomEndMidSemRows.getNoOfSetInDesk()>0)
							endSemRowsTo.setEndSemSeatInDesk(Integer.toString(roomEndMidSemRows.getNoOfSetInDesk()));
						   if(roomEndMidSemRows.getEndMidSem()!=null && !roomEndMidSemRows.getEndMidSem().isEmpty())
						   endSemRowsTo.setEndMidSem(roomEndMidSemRows.getEndMidSem());
						   endList.add(endSemRowsTo);
					 }
					if(roomEndMidSemRows.getEndMidSem().equalsIgnoreCase("M")){
						   RoomEndMidSemRowsTo midSemRowsTo=new RoomEndMidSemRowsTo();
						   midSemRowsTo.setId(roomEndMidSemRows.getId());
						   if(roomEndMidSemRows.getColumnNumber()!=null && !roomEndMidSemRows.getColumnNumber().isEmpty())
						   midSemRowsTo.setMidColumnNumber(Integer.parseInt(roomEndMidSemRows.getColumnNumber()));
						   if(roomEndMidSemRows.getNoOfRows()!=null && !roomEndMidSemRows.getNoOfRows().isEmpty())
						   midSemRowsTo.setMidSemNoOfRows(roomEndMidSemRows.getNoOfRows());
						   if(roomEndMidSemRows.getNoOfSetInDesk()!=null && roomEndMidSemRows.getNoOfSetInDesk()>0)
						   midSemRowsTo.setMidSemSeatInDesk(Integer.toString(roomEndMidSemRows.getNoOfSetInDesk()));
						   if(roomEndMidSemRows.getEndMidSem()!=null && !roomEndMidSemRows.getEndMidSem().isEmpty())
						   midSemRowsTo.setEndMidSem(roomEndMidSemRows.getEndMidSem());
						   midList.add(midSemRowsTo);
					}
				}
            	Collections.sort(endList);
            	Collections.sort(midList);
            	roomMasterForm.setEndSemList(endList);
            	roomMasterForm.setMidSemList(midList);
            }
        }
    }
}
