package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvStockTransfer;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.forms.inventory.StockTransferForm;

public interface IStockTransferTransaction {	
	
	public String getTransferNo(StockTransferForm transferForm)throws Exception;
	public List<InvItemStock> getItemStockOnInventory(int invLocationId)throws Exception;
	
	//Used to submit stock transfer and update inventory present stock
	public boolean submitStockTransfer(InvStockTransfer stockTransfer, List<InvItemStock> newStockList, 
	List<InvTx> invTXList)throws Exception;
}
