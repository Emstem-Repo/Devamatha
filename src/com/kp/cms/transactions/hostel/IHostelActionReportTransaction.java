package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.to.hostel.HostelActionReportTO;

public interface IHostelActionReportTransaction {
	public List<Object> getHostelActionReportDetails(HostelActionReportTO actionReportTO) throws Exception;
}
