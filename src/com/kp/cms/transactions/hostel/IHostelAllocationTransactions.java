package com.kp.cms.transactions.hostel;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.to.hostel.HlRoomFloorTO;
public interface IHostelAllocationTransactions {
	
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public List<Object> getApplicantHostelDetailsList(String searchQuery) throws Exception;
	public List<HlRoomFloorTO> getFloorsByHostel(int hostelId) throws Exception;
	public String saveAllocationDetails(HlRoomTransaction hlRoomTransaction)throws Exception ;
	public int getNumberOfOccupants(int roomId) throws Exception;
	public List<Object> getCurrentOccupantsCount(int roomId) throws Exception;
	public Date getReservationDate(Integer applicationformId) throws Exception;
	public String updateApplicationFormWithTransactionSet(HlApplicationForm hlApplicationForm)throws Exception;
	public HlApplicationForm getApplicationDetailsToUpdate(int applId) throws Exception ;
	public List<Object> getAllocatedBedList(int roomNoId) throws Exception;

}
