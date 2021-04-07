package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.to.hostel.VRequisitionsTO;

public interface IRequisitionTransaction {

	List<Object[]> getRequisitionDetails(String dynamicQuery) throws Exception;

	List<HlApplicationForm> getRequisitionDetailsToShow(String dynamicQuery)throws Exception;

	boolean markAsApprove(List<VRequisitionsTO> list1);

	boolean updateStatus(int hlId, int rid, int hid,String status)throws Exception;


}
