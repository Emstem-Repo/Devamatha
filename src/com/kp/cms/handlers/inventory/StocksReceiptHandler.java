package com.kp.cms.handlers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.helpers.inventory.StocksReceiptHelper;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactions.inventory.IStocksReceiptTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StocksReceiptTransactionImpl;

/**
 * Handler for Stocks Receipt Action
 *
 */
public class StocksReceiptHandler {
	private static final Log log = LogFactory.getLog(StocksReceiptHandler.class);
	
	public static volatile StocksReceiptHandler self=null;
	public static StocksReceiptHandler getInstance(){
		if(self==null){
			self= new StocksReceiptHandler();
		}
		return self;
	}
	private StocksReceiptHandler(){
		
	}
	
	/**
	 * @param returnForm.getPurchaseOrderNo()
	 * @return
	 * @throws Exception
	 */
	public InvPurchaseOrderTO getPurchaseOrderDetails(StockReceiptForm returnForm) throws Exception {
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
		InvPurchaseOrderTO orderTO=StocksReceiptHelper.getInstance().convertPurchaseOrderBoToTO(orderBO,returnForm);
		return orderTO;
	}
	
	/**
	 * @param returnForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveStockReceipts(StockReceiptForm returnForm) throws Exception {
		InvStockReceipt finalOrder=StocksReceiptHelper.getInstance().prepareFinalStockReceipt(returnForm);
		List<InvItemStock> itemStockList =new ArrayList<InvItemStock>();
		if(returnForm.getInventoryId()!=null && !StringUtils.isEmpty(returnForm.getInventoryId()) && StringUtils.isNumeric(returnForm.getInventoryId())){
			IStockTransferTransaction transaction = new StockTransferTransactionImpl();
			//Gets the stock items based on the inventory
			itemStockList = transaction.getItemStockOnInventory(Integer.parseInt(returnForm.getInventoryId().trim()));
		}
		// update stocks in inventory
		if(finalOrder!=null && itemStockList!=null){
			updateStockValues(finalOrder,itemStockList,returnForm);
		}
		
		boolean result=false;
		int id=0;
		if(finalOrder!=null){
			//save it
			IStocksReceiptTransaction txn= new StocksReceiptTransactionImpl();
			id=txn.updateFinalStockReceipt(finalOrder,itemStockList);
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
	
	/**
	 * Used to get All inventory Locations
	 */
	public List<InvLocationTO>getAllInventoryLocation()throws Exception{
		log.info("entering into getAllInventoryLocation StockTransferHandler");
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<InvLocation> invLocation = singleFieldMasterTransaction.getInventoryLocations();
		log.info("Leaving into getAllInventoryLocation StockTransferHandler");
		return StocksReceiptHelper.getInstance().copyInventoryLocationBosToTos(invLocation);
	}
	
	/**
	 * update the new stock value in inventory location
	 * @param finalOrder
	 * @param itemStockList
	 * @param returnForm 
	 */
	private void updateStockValues(InvStockReceipt finalOrder,
			List<InvItemStock> itemStockList, StockReceiptForm returnForm) {
		Iterator<InvItemStock> stkItr=itemStockList.iterator();
		List<InvTxTO> transactions= new ArrayList<InvTxTO>();
		List<Integer> matchedItems= new ArrayList<Integer>();
		while (stkItr.hasNext()) {
			InvItemStock itmStock = (InvItemStock) stkItr.next();
			
			// if location matches
			if(itmStock.getInvLocation()!=null
					&& itmStock.getIsActive()!=null
					&& itmStock.getIsActive()
					&& itmStock.getInvLocation().getId()== finalOrder.getInvLocation().getId()
					&& finalOrder.getInvStockReceiptItems()!=null){
				Iterator<InvStockReceiptItem> itemItr= finalOrder.getInvStockReceiptItems().iterator();
				while (itemItr.hasNext()) {
					InvStockReceiptItem receiptItem = (InvStockReceiptItem) itemItr.next();
					// if item matches and received qty. entered
					if(receiptItem.getInvItem()!=null 
							&& itmStock.getInvItem()!=null
							&& receiptItem.getInvItem().getId()== itmStock.getInvItem().getId() 
							&& receiptItem.getQuantity()!=null){
						InvTxTO txnTo= new InvTxTO();
						matchedItems.add(receiptItem.getInvItem().getId());
						txnTo.setInvItem(itmStock.getInvItem());
						txnTo.setInvLocation(itmStock.getInvLocation());
						txnTo.setTxType(CMSConstants.RECEIPT_TX_TYPE);
						txnTo.setTxDate(new Date());
						double availablestock=0.0;
						double recievedqty=0.0;
						double conversion=0.0;
						if(itmStock.getAvailableStock()!=null)
						availablestock=itmStock.getAvailableStock().doubleValue();
						
						txnTo.setOpeningBalance(availablestock);
						// change to issue qty
						
						recievedqty=receiptItem.getQuantity().doubleValue();
						
						if(receiptItem.getInvItem().getConversion()!=null){
							conversion=receiptItem.getInvItem().getConversion().doubleValue();
							availablestock=availablestock+(recievedqty*conversion);
							
						}else{
							availablestock=availablestock+recievedqty;
						}
						txnTo.setClosingBalance(availablestock);
						txnTo.setQuantity(recievedqty);
						itmStock.setAvailableStock(new BigDecimal(availablestock));
						txnTo.setCreatedBy(finalOrder.getCreatedBy());
						txnTo.setCreatedDate(new Date());
						itmStock.setModifiedBy(finalOrder.getCreatedBy());
						itmStock.setLastModifiedDate(new Date());
						transactions.add(txnTo);
					}
				}
			}
			
			
			
		}
		
		// add new items
		if(finalOrder.getInvStockReceiptItems()!=null){
			if(itemStockList==null)
			{
				itemStockList= new ArrayList<InvItemStock>();
			}
			Iterator<InvStockReceiptItem> itemItr2= finalOrder.getInvStockReceiptItems().iterator();
			while (itemItr2.hasNext()) {
				InvStockReceiptItem receiptItem = (InvStockReceiptItem) itemItr2.next();
				if(!matchedItems.contains(receiptItem.getInvItem().getId())){
					InvItemStock itemStock = new InvItemStock();
					InvTxTO txnTo= new InvTxTO();
					itemStock.setInvItem(receiptItem.getInvItem());
					itemStock.setInvLocation(finalOrder.getInvLocation());
					txnTo.setInvItem(receiptItem.getInvItem());
					txnTo.setInvLocation(finalOrder.getInvLocation());
					txnTo.setTxType(CMSConstants.RECEIPT_TX_TYPE);
					txnTo.setTxDate(new Date());
					
					double stock=0.0;
					if(receiptItem.getQuantity()!=null){
						if(receiptItem.getInvItem().getConversion()!=null)
						{
							stock=receiptItem.getQuantity().doubleValue()*receiptItem.getInvItem().getConversion().doubleValue();
						}else{
							stock=receiptItem.getQuantity().doubleValue();
						}
					}
					itemStock.setAvailableStock(new BigDecimal(stock));
					txnTo.setOpeningBalance(stock);
					txnTo.setClosingBalance(stock);
					txnTo.setQuantity(stock);
					itemStock.setIsActive(true);
					itemStock.setCreatedBy(finalOrder.getCreatedBy());
					itemStock.setCreatedDate(finalOrder.getCreatedDate());
					txnTo.setCreatedBy(finalOrder.getCreatedBy());
					txnTo.setCreatedDate(new Date());
					itemStockList.add(itemStock);
					transactions.add(txnTo);
				}
			}
		}
		
		returnForm.setTransactions(transactions);
	}
	/**
	 * returns all present item no.s in inv_amc table
	 * @return
	 */
	public List<String> getItemNosInAMC() throws Exception {
		IStocksReceiptTransaction txn= new StocksReceiptTransactionImpl();
		return txn.getAllItemNosInAMC();
	}
}
