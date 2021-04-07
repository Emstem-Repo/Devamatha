package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvSalvage;


public interface IsalvageItemTransaction {

	public int saveSalvageDetails(InvSalvage salvage, List<InvItemStock> updateStockList) throws Exception;
	public int saveItemIssueDetails(InvIssue invIssue, List<InvItemStock> updateStockList) throws Exception;
	
	public List<InvSalvage> getReportDetails(String searchCriteria) throws Exception;
}
