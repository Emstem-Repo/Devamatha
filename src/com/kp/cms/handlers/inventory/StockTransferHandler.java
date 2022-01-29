package com.kp.cms.handlers.inventory;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvStockTransfer;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockTransferForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.inventory.StockTransferHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;

@SuppressWarnings("deprecation")
public class StockTransferHandler {
	private static final Log log = LogFactory.getLog(StockTransferHandler.class);
	public static volatile StockTransferHandler stockTransferHandler = null;

	private StockTransferHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static StockTransferHandler getInstance() {
		if (stockTransferHandler == null) {
			stockTransferHandler = new StockTransferHandler();
		}
		return stockTransferHandler;
	}
	
	/**
	 * Used to get All inventory Locations
	 */
	public List<SingleFieldMasterTO>getAllInventoryLocation()throws Exception{
		log.info("entering into getAllInventoryLocation StockTransferHandler");
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<InvLocation> invLocation = singleFieldMasterTransaction.getInventoryLocations();
		log.info("Leaving into getAllInventoryLocation StockTransferHandler");
		return SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(invLocation, "InvLocation");
	}
	/**
	 * Used to generate transfer no.
	 * Based on the configuration in counter
	 * And also autogenerated from stock transfer
	 */
	public void setTransferNoToForm(StockTransferForm transferForm)throws Exception{
		log.info("entering into setTransferNoToForm StockTransferHandler");
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		transferForm.setTransferNo(transaction.getTransferNo(transferForm));
		log.info("Leaving into setTransferNoToForm StockTransferHandler");
	}
	
	/**
	 * Used to transfer the stock
	 * Used for saving
	 * First gets the stockList for that from inventory
	 * Also validates as the transfer item list created by user
	 * If stock available and selected within the range then go for transfer
	 */
	public boolean submitStockTransfer(StockTransferForm transferForm, ActionErrors errors)throws Exception{
		log.info("entering into submitStockTransfer StockTransferHandler");
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		boolean result = false;
		InvStockTransfer stockTransfer = null;
		List<InvItemStock> newStockList = null;
		//Gets the stock items for From Inventroy
		List<InvItemStock> fromInventoryItemStockList = transaction.getItemStockOnInventory(Integer.valueOf(transferForm.getFromInventoryId().trim()));
		//Used to create stock map.
		Map<String, ItemStockTO> fromInventoryItemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(fromInventoryItemStockList);
		
		//Gets the stock items for To Inventroy
		List<InvItemStock> toInventoryItemStockList = transaction.getItemStockOnInventory(Integer.valueOf(transferForm.getToInventoryId().trim()));
		//Used to create stock map.
		Map<String, ItemStockTO> toInventoryItemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(toInventoryItemStockList);
		
		transferForm.setFromInventoryItemStockMap(fromInventoryItemStockMap);
		transferForm.setToInventoryItemStockMap(toInventoryItemStockMap);
		
		
		if(fromInventoryItemStockMap.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_EMPTY));
			result = false;
		}
		if(errors.isEmpty()){
			stockTransfer = StockTransferHelper.getInstance().copyTOToBOForTransfer(transferForm, errors);
			result = false;
			if(errors.isEmpty()){
				newStockList = StockTransferHelper.getInstance().updatedStockAfterTransfer(transferForm);
				result = true;
			}
		}
		else{
			result = false;
		}
		if(result){
			result = transaction.submitStockTransfer(stockTransfer, newStockList, transferForm.getInvTxList());
		}
		log.info("Leaving into submitStockTransfer StockTransferHandler");
		return result;
	}	
}
