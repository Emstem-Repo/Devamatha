package com.kp.cms.transactions.hostel;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.forms.hostel.HostelReservationForm;

public interface IHostelReservationTransaction {
	
	public HlApplicationForm getApplicantHostelDetailsList(String searchQuery) throws Exception;
	
	public boolean saveReservationDetails(HlRoomTransaction hlRoomTransaction,HostelReservationForm hostelReservationForm) throws Exception;
	public int findCurrentReservationCount(HostelReservationForm hostelResForm) throws Exception;
	public int findCurrentOccupantsCount(HostelReservationForm hostelResForm) throws Exception;
	public int getMaxNumberOfOccupantsPerRoom(HostelReservationForm hostelResForm) throws Exception;
	public int getMaxBillNumber()throws Exception;
}
