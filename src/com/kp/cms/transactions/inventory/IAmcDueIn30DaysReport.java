package com.kp.cms.transactions.inventory;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.InvAmc;

public interface IAmcDueIn30DaysReport {
	public List<InvAmc> getAmcDueIn30Days(Date startDate, Date endDate, int invLocationId) throws Exception;	

}
