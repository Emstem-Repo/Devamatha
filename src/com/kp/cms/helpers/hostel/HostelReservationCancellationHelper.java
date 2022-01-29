package com.kp.cms.helpers.hostel;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlRoomTransaction;

public class HostelReservationCancellationHelper 
{
	private static Log log = LogFactory.getLog(HostelReservationCancellationHelper.class);
	
	private static volatile HostelReservationCancellationHelper reservationCancellationHelper = null;
	
	private HostelReservationCancellationHelper() {
	}
	
	public static HostelReservationCancellationHelper getInstance() {
		if (reservationCancellationHelper == null) {
			reservationCancellationHelper = new HostelReservationCancellationHelper();
		}
		return reservationCancellationHelper;
	}
	
	public HlRoomTransaction getNewHlRoomTransactionAfterCancellation(HlRoomTransaction hlRoomTransaction)
	{
		HlRoomTransaction  hlRoomTransactionNew=new HlRoomTransaction();
		hlRoomTransactionNew.setHlStatus(hlRoomTransaction.getHlStatus());
		hlRoomTransactionNew.setHlApplicationForm(hlRoomTransaction.getHlApplicationForm());
		hlRoomTransactionNew.setAdmAppln(hlRoomTransaction.getAdmAppln());
		hlRoomTransactionNew.setEmployee(hlRoomTransaction.getEmployee());
		hlRoomTransactionNew.setTxnDate(new Date());
		hlRoomTransactionNew.setCurrentOccupantsCount(hlRoomTransaction.getCurrentOccupantsCount());
		hlRoomTransactionNew.setBedNo(hlRoomTransaction.getBedNo());
		hlRoomTransactionNew.setCreatedBy(hlRoomTransaction.getCreatedBy());
		hlRoomTransactionNew.setModifiedBy(hlRoomTransaction.getModifiedBy());
		hlRoomTransactionNew.setLastModifiedDate(new Date());
		hlRoomTransactionNew.setIsActive(hlRoomTransaction.getIsActive());
		hlRoomTransactionNew.setComments(hlRoomTransaction.getComments());
		hlRoomTransactionNew.setRemarks(hlRoomTransaction.getRemarks());
		hlRoomTransactionNew.setCurrentReservationCount(hlRoomTransaction.getCurrentReservationCount()-1);
		hlRoomTransactionNew.setHlRoom(hlRoomTransaction.getHlRoom());
		hlRoomTransactionNew.setCreatedDate(hlRoomTransaction.getCreatedDate());
		return hlRoomTransactionNew;
	}
}
