package com.kp.cms.transactions.inventory;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.InvCashPurchaseItem;

public interface ICashPurchaseReportTran {
	public List<InvCashPurchaseItem> getCashPurchaseItems(Date startDate, Date endDate, int invLocationId) throws Exception;
}
