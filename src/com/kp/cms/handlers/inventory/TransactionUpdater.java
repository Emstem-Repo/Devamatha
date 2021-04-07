package com.kp.cms.handlers.inventory;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.transactions.inventory.IStocksReceiptTransaction;
import com.kp.cms.transactionsimpl.inventory.StocksReceiptTransactionImpl;

public class TransactionUpdater {
private static final Log log = LogFactory.getLog(TransactionUpdater.class);
	
	public static volatile TransactionUpdater self=null;
	public static TransactionUpdater getInstance(){
		if(self==null){
			self= new TransactionUpdater();
		}
		return self;
	}
	private TransactionUpdater(){
		
	}
	/**
	 * updates Inv_tx table
	 */
	public void updateTransactionAttributes(int refId,InvItem item,InvLocation location,
			String txType, Date txDate,double quantity,double openingbalance,
			double closingbalance,String createdby,Date createdDate)throws Exception{
		InvTx txn= new InvTx();
		txn.setClosingBalance(new BigDecimal(closingbalance));
		txn.setOpeningBalance(new BigDecimal(openingbalance));
		txn.setReferenceId(refId);
		txn.setInvItem(item);
		txn.setInvLocation(location);
		txn.setTxType(txType);
		txn.setTxDate(txDate);
		txn.setQuantity(new BigDecimal(quantity));
		txn.setIsActive(true);
		txn.setCreatedBy(createdby);
		txn.setCreatedDate(createdDate);
		
		IStocksReceiptTransaction stTxn= new StocksReceiptTransactionImpl();
		stTxn.updateTransactionDetails(txn);
	}
}
