package com.kp.cms.handlers.inventory;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.PurchaseReturnForm;
import com.kp.cms.helpers.inventory.PurchaseReturnHelper;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;

/**
 * handler class for Purchase return action
 *
 */
public class PurchaseReturnHandler {
	private static final Log log = LogFactory.getLog(PurchaseReturnHandler.class);
	
	public static volatile PurchaseReturnHandler self=null;
	public static PurchaseReturnHandler getInstance(){
		if(self==null){
			self= new PurchaseReturnHandler();
		}
		return self;
	}
	private PurchaseReturnHandler(){
		
	}
	/**
	 * @param returnForm.getPurchaseOrderNo()
	 * @return
	 * @throws Exception
	 */
	public InvPurchaseOrderTO getPurchaseOrderDetails(PurchaseReturnForm returnForm) throws Exception {
		IPurchaseReturnTransaction txn= new PurchaseReturnTransactionImpl();
		InvPurchaseOrder orderBO=null;
		
		String prefix=QuotationReportHandler.getInstance().getQuotationPrefix(CMSConstants.PURCHASE_ORDER_COUNTER);
		String maxorderNo=returnForm.getPurchaseOrderNo();
		
		String tempMax="";
		if(prefix!=null && !prefix.isEmpty() && maxorderNo.startsWith(prefix) )
		tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
		
		if(tempMax!=null && !tempMax.isEmpty() && StringUtils.isNumeric(tempMax))
		orderBO=txn.getPurchaseorderDetails(tempMax);
		
		if(orderBO==null)
			return null;
		InvPurchaseOrderTO orderTO=PurchaseReturnHelper.getInstance().convertPurchaseOrderBoToTO(orderBO,returnForm);
		return orderTO;
	}
	/**
	 * @param returnForm
	 * @return
	 * @throws Exception
	 */
	public boolean savePurchaseReturns(PurchaseReturnForm returnForm) throws Exception {
		InvPurchaseReturn finalOrder=PurchaseReturnHelper.getInstance().prepareFinalPurchaseReturn(returnForm);
		// get updated item stock after return
		Set<InvItemStock> itemStocks=PurchaseReturnHelper.getInstance().prepareFinalItemStocks(returnForm);
		boolean result=false;
		int id=0;
		if(finalOrder!=null){
			//save it
			IPurchaseReturnTransaction txn= new PurchaseReturnTransactionImpl();
			id=txn.placeFinalPurchaseReturn(finalOrder,itemStocks);
			if(id!=0 ){
				result=true;
				if(returnForm.getTransactions()!=null && !returnForm.getTransactions().isEmpty()){
					Iterator<InvTxTO> txItr=returnForm.getTransactions().iterator();
					while (txItr.hasNext()) {
						InvTxTO invTxTO = (InvTxTO) txItr.next();
						TransactionUpdater.getInstance().updateTransactionAttributes(
								id, invTxTO.getInvItem(), invTxTO.getInvLocation(),
								invTxTO.getTxType(), invTxTO.getTxDate(), invTxTO.getQuantity(),
								invTxTO.getOpeningBalance(), invTxTO.getClosingBalance(),
								invTxTO.getCreatedBy(),invTxTO.getCreatedDate());
					}
				}
			}
		}
		return result;
	}
}
