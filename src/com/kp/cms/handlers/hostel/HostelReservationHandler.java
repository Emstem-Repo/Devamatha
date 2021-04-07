package com.kp.cms.handlers.hostel;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelReservationForm;
import com.kp.cms.helpers.hostel.HostelReservationHelper;
import com.kp.cms.to.hostel.HostelReservationTO;
import com.kp.cms.transactions.hostel.IHostelReservationTransaction;
import com.kp.cms.transactions.hostel.IRoomTypeTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelReservationTxnImpl;
import com.kp.cms.transactionsimpl.hostel.RoomTypeTransactionImpl;

public class HostelReservationHandler {

	private static volatile HostelReservationHandler hostelReservationHandler = null;

	private HostelReservationHandler() {
	}

	/**
	 * This method returns the single instance of this HostelReservationHandler.
	 * 
	 * @return
	 */
	public static HostelReservationHandler getInstance() {
		if (hostelReservationHandler == null) {
			hostelReservationHandler = new HostelReservationHandler();
		}
		return hostelReservationHandler;
	}
	
	/**
	 * 
	 * @param hostelReservationForm
	 * @return
	 * @throws Exception
	 */
	public HostelReservationTO getApplicantHostelDetails(HostelReservationForm hostelReservationForm) throws Exception{
	
		IHostelReservationTransaction hostelReservationTransaction = new HostelReservationTxnImpl();
		HostelReservationHelper hostelReservationHelper = new HostelReservationHelper();
		HlApplicationForm applicantHostelDetails = hostelReservationTransaction.getApplicantHostelDetailsList(hostelReservationHelper.getSearchCriteria(hostelReservationForm));  
		HostelReservationTO applicantHostelDetailsTO = hostelReservationHelper.convertBOtoTO(applicantHostelDetails);
		
		return applicantHostelDetailsTO; 
	}
	
	/**
	 * @param hostelReservationForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveReservationDetails(HostelReservationForm hostelReservationForm)throws Exception{
		
		IHostelReservationTransaction hostelReservationTransaction = new HostelReservationTxnImpl();
		HostelReservationHelper hostelReservationHelper = new HostelReservationHelper();
		
		int cnt=hostelReservationTransaction.findCurrentReservationCount(hostelReservationForm);
		int currentOccupantCount=hostelReservationTransaction.findCurrentOccupantsCount(hostelReservationForm);
		return hostelReservationTransaction.saveReservationDetails(hostelReservationHelper.saveReservationDetails(hostelReservationForm,cnt,currentOccupantCount),hostelReservationForm);
	}
	/**
	 * @param reservationForm
	 * @param request
	 * @throws Exception
	 */
	public void getDetailByRoomTypeId(HostelReservationForm reservationForm, HttpServletRequest request)throws Exception{
		IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		int roomTypeId = Integer.parseInt(reservationForm.getRoomTypeId());
		HlRoomType roomType = transaction.getDetailByRoomTypeId(roomTypeId);
		if(roomType!= null){
			reservationForm.setMaxNoOfOccupants(roomType.getNoOfOccupants());
		}
	}
	
public boolean canReservationBeDone(HostelReservationForm hostelReservationForm)throws Exception{
		
		IHostelReservationTransaction hostelReservationTransaction = new HostelReservationTxnImpl();
		int currentReservationCount=hostelReservationTransaction.findCurrentReservationCount(hostelReservationForm);
		int currentOccupantCount=hostelReservationTransaction.findCurrentOccupantsCount(hostelReservationForm);
		int maxNumberOfOccupants=hostelReservationTransaction.getMaxNumberOfOccupantsPerRoom(hostelReservationForm);
		if((currentOccupantCount+currentReservationCount)<maxNumberOfOccupants)
			return true;
		else
			return false;
	}
	
public int getBillNumber()throws Exception{
	
	IHostelReservationTransaction hostelReservationTransaction = new HostelReservationTxnImpl();
	int maxBillNumber=hostelReservationTransaction.getMaxBillNumber();
	return maxBillNumber;
}
}
