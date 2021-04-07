package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequestItem;

public interface IInventoryRequestTransaction {

	public List<InvRequestItem> getInventoryRequest(int invLocationId) throws Exception;
	
	public List<InvItemStock> getAvailableStocks(int invLocationId) throws Exception;
}
