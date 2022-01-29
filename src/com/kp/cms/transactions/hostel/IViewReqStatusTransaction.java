package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;

public interface IViewReqStatusTransaction {
	List<HlApplicationForm> getRequisitionStatusDetails(String dynamicQuery) throws Exception;

	String getRequisitionisEmployee(String query)throws Exception;

	String getRequisitionisFeePaid(String feequery)throws Exception;
}
