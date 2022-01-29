package com.kp.cms.transactions.hostel;
import java.util.List;
import com.kp.cms.bo.admin.HlApplicationForm;

import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.to.hostel.HostelCheckinTo;
public interface IHostelCheckinTransactions {	
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public List<Object> getApplicantHostelDetailsList(String searchQuery) throws Exception;
	public List<HlRoomTypeFacility> getHlFecilityDetails(int roomTypeId) throws Exception;
	public Integer saveCheckinDetails(HlRoomTransaction hlRoomTransaction) throws Exception;
	public boolean saveFecilities(HlCheckinCheckoutFacility hlCheckinCheckoutFacility) throws Exception;
	public List<HlApplicationForm >  getAppFormDetails(int hlAppFormId) throws Exception;
	public boolean updateAppFormDetails(HlApplicationForm  hlApplicationForm) throws Exception;
	public List<HlRoomTransaction> getCheckinDetailedListToView(int statusId,int roomId,int bedNo) throws Exception;
	public List<HlRoomTransaction> getCheckinDetailedListForActive(int statusId,int roomId) throws Exception;
	public boolean reActivateCheckinList(List<HlRoomTransaction> hlCheckinBoList)throws Exception;
	public boolean checkForDuplicate(String fingerPrintId, String hostelId) throws Exception;
	public boolean isCheckedin(HostelCheckinForm hostelCheckinForm) throws Exception;
}
