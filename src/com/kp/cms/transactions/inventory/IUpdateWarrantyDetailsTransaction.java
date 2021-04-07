package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.bo.admin.InvStockReceipt;

public interface IUpdateWarrantyDetailsTransaction {
	public List<InvStockReceipt> getStockReceiptItems(String orderNo) throws Exception;
	public boolean addWarrantyDetails(List<InvAmc> amcBOList) throws Exception;
}
