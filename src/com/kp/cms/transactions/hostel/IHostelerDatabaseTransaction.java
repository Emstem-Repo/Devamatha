package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;

public interface IHostelerDatabaseTransaction {
	public List<HlRoomTransaction> submitHostelerDatabase(String seachCriteria)throws Exception;

	public List<HlApplicationForm> getRequisitionDetailsToShow(String dynamicQuery)throws Exception;

}
