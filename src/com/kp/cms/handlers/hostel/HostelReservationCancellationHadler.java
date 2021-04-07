package com.kp.cms.handlers.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelReservationcancellationForm;
import com.kp.cms.helpers.hostel.HostelReservationCancellationHelper;
import com.kp.cms.helpers.hostel.RequisitionStatusHelper;
import com.kp.cms.to.hostel.VReqStatusTo;
import com.kp.cms.transactionsimpl.hostel.HostelReservationCancellationImpl;

public class HostelReservationCancellationHadler {
	private static volatile HostelReservationCancellationHadler hostelReservationCancellationHadler = null;

	private HostelReservationCancellationHadler() {
	}

	public static HostelReservationCancellationHadler getInstance() {
		if (hostelReservationCancellationHadler == null) {
			hostelReservationCancellationHadler = new HostelReservationCancellationHadler();
		}
		return hostelReservationCancellationHadler;
	}
	HostelReservationCancellationImpl hostelReservationCancellationImpl= new HostelReservationCancellationImpl();
	/**
	 * @param hostelReservationcancellationForm
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	public boolean cancelReservation(HostelReservationcancellationForm hostelReservationcancellationForm,HttpServletRequest request) throws ApplicationException {
		boolean iscancelled = hostelReservationCancellationImpl.cancelReservation(hostelReservationcancellationForm);
		return iscancelled;
	}

	/**
	 * @param hostelReservationcancellationForm
	 * @return
	 * @throws Exception
	 */
	public HlApplicationForm getHlApplicationFormByRequistionNo(HostelReservationcancellationForm hostelReservationcancellationForm) throws Exception{
		String query="from HlApplicationForm h where h.isActive=1 and h.hlStatus.id=8 and h.requisitionNo="+hostelReservationcancellationForm.getReqNo();
		return hostelReservationCancellationImpl.getHlApplicationFormByRequistionNo(query);
	}
	
	public HlRoomTransaction getNewHlRoomTransactionAfterCancellation(HlRoomTransaction hlRoomTransaction)
	{
		return HostelReservationCancellationHelper.getInstance().getNewHlRoomTransactionAfterCancellation(hlRoomTransaction);
	}
}
