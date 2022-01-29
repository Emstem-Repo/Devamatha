package com.kp.cms.handlers.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.hostel.HlRoomTypeFees;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.helpers.admin.CertificateDetailsHelpers;
import com.kp.cms.helpers.fee.FeeAdditionalHelper;
import com.kp.cms.helpers.hostel.RoomTypeHelper;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.RoomTypeFeesTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.transactions.hostel.IRoomTypeTransaction;
import com.kp.cms.transactionsimpl.hostel.RoomTypeTransactionImpl;

public class RoomTypeHandler {
private static final Log log = LogFactory.getLog(RoomTypeHandler.class);
	
	public static volatile RoomTypeHandler roomTypeHandler = null;

	private RoomTypeHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static RoomTypeHandler getInstance() {
		if (roomTypeHandler == null) {
			roomTypeHandler = new RoomTypeHandler();
		}
		return roomTypeHandler;
	}
	
	/**
	 * Used to get all the facilities
	 */
	
	public List<FacilityTO> getAllFacility()throws Exception{
		log.info("Entering into getAllFacility of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		List<HlFacility> facilityBOList = transaction.getAllFacility();
		log.info("Leaving into getAllFacility of RoomTypeHandler");
		return RoomTypeHelper.getInstance().copyFacilityBOToTO(facilityBOList);
	}
	
	/**
	 * Used for inserting room type
	 */
	public boolean submitRoomType(RoomTypeForm roomTypeForm)throws Exception{
		log.info("Entering into submitRoomType of RoomTypeHandler");
		HlRoomType hlRoomType = RoomTypeHelper.getInstance().prepareRoomTypeBOForSave(roomTypeForm);
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		log.info("Leaving into submitRoomType of RoomTypeHandler");
		return transaction.submitRoomType(hlRoomType);
	}
	/**
	 * Used to get all the facilities
	 */	
	public List<RoomTypeTO> getAllRoomType()throws Exception{
		log.info("Entering into getAllRoomType of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		List<HlRoomType> roomTypeBOList = transaction.getAllRoomType();
		log.info("Leaving into getAllRoomType of RoomTypeHandler");
		return RoomTypeHelper.getInstance().copyRoomTypeBOToTO(roomTypeBOList);
	}	
	/**
	 * Used to delete roomtype
	 * Makes Isactive false
	 */
	public boolean deleteRoomType(int roomTypeId, String userId)throws Exception{
		log.info("Entering into deleteRoomType of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		log.info("Leaving into deleteRoomType of RoomTypeHandler");
		return transaction.deleteRoomType(roomTypeId, userId);
	}
	/**
	 * Used to check duplicate on hostel id and name
	 */
	public HlRoomType getRoomTypeOnHostelName(int hostelId, String roomType)throws Exception{
		log.info("Entering into getRoomTypeOnHostelName of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		log.info("Leaving into getRoomTypeOnHostelName of RoomTypeHandler");
		return transaction.getRoomTypeOnHostelName(hostelId, roomType);
	}
	
	/**
	 * Used to reactivate roomtype
	 */
	public boolean reactivateRoomType(int hostelId, String roomType,String userId)throws Exception{
		log.info("Entering into reactivateRoomType of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		log.info("Leaving into reactivateRoomType of RoomTypeHandler");
		return transaction.reactivateRoomType(hostelId, roomType, userId);
	}	
	/**
	 * Works when edit button is clicked
	 */
	public void getDetailByRoomTypeId(RoomTypeForm typeForm, HttpServletRequest request)throws Exception{
		log.info("Entering into getDetailByRoomTypeId of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		int roomTypeId = Integer.parseInt(typeForm.getRoomTypeId());
		HlRoomType roomType = transaction.getDetailByRoomTypeId(roomTypeId);
		RoomTypeHelper.getInstance().setRoomTypeDetailsToForm(roomType, typeForm, request);
		log.info("Leaving into getDetailByRoomTypeId of RoomTypeHandler");
	}
	
	/**
	 * Used when updating room type
	 */
	public boolean updateRoomType(RoomTypeForm roomTypeForm)throws Exception{
		log.info("Entering into updateRoomType of RoomTypeHandler");
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		HlRoomType roomType = RoomTypeHelper.getInstance().populateTOToBOWhileUpdate(roomTypeForm);
		log.info("Leaving into updateRoomType of RoomTypeHandler");
		return transaction.updateRoomType(roomType);
	}
	
	/**
	 * Used to delete roomType image in update mode
	 */
	public boolean  deleteRoomTypeImage(int roomTypeImageId)throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		return transaction.deleteRoomTypeImage(roomTypeImageId);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<FeeGroupTO> getAssignFeeForRoom() throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		List<FeeGroup> feeGroupBo=transaction.getAssignFeeForRoom();
		List<FeeGroupTO> feeAssignmentsTOs = RoomTypeHelper.getInstance().copyFeeBosToTos(feeGroupBo);
		return feeAssignmentsTOs;
	}

	public boolean addAssignFees(RoomTypeForm typeForm) throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		boolean isAdded=false;
    	List<HlRoomTypeFees> roomFees = RoomTypeHelper.getInstance().convertFormToBos(typeForm);
    	if(roomFees!=null && !roomFees.isEmpty())
        isAdded = transaction.addSynopsisDefense(roomFees);
        return isAdded;
    }

	public List<RoomTypeFeesTO> setAssignRoomFees(RoomTypeForm typeForm) throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		List<HlRoomTypeFees> roomFeesBo=transaction.setAssignRoomFees(typeForm);
		 List<RoomTypeFeesTO>  roomTypeFeesTO=RoomTypeHelper.getInstance().convertFeeBosToTos(roomFeesBo);
		return null;
	}
	
	
	public  List<FeeGroupTO> getAssignFees(RoomTypeForm typeForm) throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		 List<FeeGroup> fee=transaction.getFee();
		 Map<Integer,Integer> feeList = transaction.getAssignFee(typeForm);
		 typeForm.setOldCountt(feeList.size());
		 List<FeeGroupTO> feeListsNew = RoomTypeHelper.getInstance().setAssignFee(fee,feeList);
	     return feeListsNew;
	}
}
