package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvTx;

/**
 * Txn. Interface for Stocks Receipt
 *
 */
public interface IStocksReceiptTransaction {

	int updateFinalStockReceipt(InvStockReceipt finalOrder, List<InvItemStock> itemStockList)throws Exception;

	public List<String> getAllItemNosInAMC() throws Exception;

	void updateTransactionDetails(InvTx txn)throws Exception;

	List<InvTx> getItemTransactionsOnDate(String txDate, int locationId,String endDate)throws Exception;

	List<InvTx> getItemTransactionsOnDetail(String selDt, int txnId, int selLoc)throws Exception;

	double getAlreadyRecievedQty(int itemId, int purchaseOrderId) throws Exception;

	List getStockdetails(String date, int locationId) throws Exception;
	
}
