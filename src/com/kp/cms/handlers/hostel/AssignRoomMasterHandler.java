package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.helpers.hostel.AssignRoomMasterHelper;
import com.kp.cms.helpers.usermanagement.MenusHelper;
import com.kp.cms.to.hostel.AssignRoomMasterTo;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.transactions.hostel.IAssignRoomTransactions;
import com.kp.cms.transactionsimpl.hostel.AssignRoomTransactionImpl;

public class AssignRoomMasterHandler {
	private static final Log log = LogFactory.getLog(AssignRoomMasterHandler.class);
	private static volatile AssignRoomMasterHandler assignRoomMasterHandler = null;

	public static AssignRoomMasterHandler getInstance() {
		if (assignRoomMasterHandler == null) {
			assignRoomMasterHandler = new AssignRoomMasterHandler();
		}
		return assignRoomMasterHandler;
	}
	
	/*
	save method
	*/

	public boolean saveRoomDetails(AssignRoomMasterForm roomForm) throws DuplicateException, Exception {
		IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
		boolean isAdded;
		List<HlRoom> roomList = AssignRoomMasterHelper.getInstance().populateRoomDetails(roomForm);
		roomForm.setFormValueDupl(false);
		isAdded = irTransactions.addRooms(roomList);
		return isAdded;
	}

	public AssignRoomMasterTo getRoomTo(AssignRoomMasterForm roomForm) throws Exception
	{
		IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
		List<HlRoom> roomList=irTransactions.getRoomsByHostelAndFloor(Integer.parseInt(roomForm.getHostelId()),Integer.parseInt(roomForm.getFloorNo()),roomForm.getBlock(),roomForm.getUnit());
		AssignRoomMasterTo roomMasterTo=AssignRoomMasterHelper.getInstance().getRoomMasterTo(roomList);
		if(roomMasterTo!=null)
		{
			roomMasterTo.setRoomNames(MenusHelper.getInstance().splitString(roomMasterTo.getRoomNames()));
		}
		return roomMasterTo;
	}

	public List<HlRoomTO> getRoomList(AssignRoomMasterForm roomForm) throws Exception{
		IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
		List<HlRoom> roomList=irTransactions.getRoomsByHostelAndFloor(Integer.parseInt(roomForm.getSearchedHostelId()),Integer.parseInt(roomForm.getSearchedFloorNumber()),roomForm.getBlockId(),roomForm.getUnitId());
		List<HlRoomTO>roomToList=AssignRoomMasterHelper.getInstance().getRoomToList(roomList,roomForm);
		return roomToList;
	}

	public boolean deleteRoom(String roomNo) throws Exception{
		IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
		boolean isDeleted=irTransactions.deleteRoom(Integer.parseInt(roomNo));
		return isDeleted;
	}
	
	public boolean updateRoomDetails(AssignRoomMasterForm roomForm) throws DuplicateException, Exception {
		IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
		boolean isUpdated;
		List<HlRoom> roomList = AssignRoomMasterHelper.getInstance().populateRoomDetailsForUpdate(roomForm);
		isUpdated = irTransactions.updateRooms(roomList);
		return isUpdated;
	}
}
