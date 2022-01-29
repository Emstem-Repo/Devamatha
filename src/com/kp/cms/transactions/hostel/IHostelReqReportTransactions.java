package com.kp.cms.transactions.hostel;

import java.util.List;
import com.kp.cms.bo.admin.HlHostel;

public interface IHostelReqReportTransactions {
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public List<Object> getHostelReqReportDetailsBo(String searchQuery) throws Exception;
	public List<Object> getHostelRoomTypeAvailabilityBo(String roomTypeQuery) throws Exception;
	

}
