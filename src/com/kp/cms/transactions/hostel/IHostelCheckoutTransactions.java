package com.kp.cms.transactions.hostel;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import  java.util.List;

public interface IHostelCheckoutTransactions {
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public List<HlRoomTypeFacility> getHlFecilityDetails(int roomTypeId) throws Exception;
	public List<Object> getApplicantHostelDetailsList(String searchQuery) throws Exception; 
	public boolean saveCheckoutDetails(HlRoomTransaction hlRoomTransaction) throws Exception;
	public List<HlApplicationForm> getAppFormDetails(int hlAppFormId) throws Exception;
	public boolean updateAppFormDetails(HlApplicationForm hlApplicationForm) throws Exception;
	public List<HlCheckinCheckoutFacility> getCheckinCheckoutFacilityList(int formId,int roomTxId) throws Exception;
	public boolean saveCheckinCheckoutFecilities(HlCheckinCheckoutFacility hlCheckinCheckoutFacility) throws Exception;
	public List<Object> getDamageDetails(String damageQuery) throws Exception; 

}
