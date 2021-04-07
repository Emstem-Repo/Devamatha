package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvStockTransfer;
import com.kp.cms.bo.admin.InvStockTransferItem;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockTransferForm;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class StockTransferHelper {
	private static final Log log = LogFactory.getLog(StockTransferHandler.class);
	public static volatile StockTransferHelper stockTransferHelper = null;

	private StockTransferHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static StockTransferHelper getInstance() {
		if (stockTransferHelper == null) {
			stockTransferHelper = new StockTransferHelper();
		}
		return stockTransferHelper;
	}
	
	/**
	 * Used to prepare the BO object from TO while stock transfer
	 * Also validates the stock availability
	 * Based on stock availability prepares the BO
	 */
	public InvStockTransfer copyTOToBOForTransfer(StockTransferForm transferForm, 
		ActionErrors errors)throws Exception{
		log.info("entering into copyTOToBOForTransfer StockTransferHelper");
		//Get the stock for both From inventory and To inventory
		Map<String, ItemStockTO> fromInventoryitemStockMap = transferForm.getFromInventoryItemStockMap();
		Map<String, ItemStockTO> toInventoryitemStockMap = transferForm.getToInventoryItemStockMap();
		
		List<InvTx> invTXList = new ArrayList<InvTx>();
		InvTx invTx = null;
		InvLocation invLocation = null;
		
		double stockPresent = 0.0;
		double transferQuantity = 0.0;
		InvStockTransfer transfer = new InvStockTransfer();
		InvLocation invLocationFrom = new InvLocation();
		InvLocation invLocationTO = new InvLocation();
		Set<InvStockTransferItem> invStockTransferItemSet = new HashSet<InvStockTransferItem>();
		InvStockTransferItem transferItem = null;
		InvItem invItem = null;
		
		invLocationFrom.setId(Integer.valueOf(transferForm.getFromInventoryId().trim()));
		transfer.setInvLocationByInvLocationFromId(invLocationFrom);
		invLocationTO.setId(Integer.valueOf(transferForm.getToInventoryId().trim()));
		transfer.setInvLocationByInvLocationToId(invLocationTO);
		
		String prefix=transferForm.getPrefix();
		String maxorderNo=transferForm.getTransferNo();
		 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
		 if(tempMax!=null && StringUtils.isNumeric(tempMax))
		 {
			int maxOrder=Integer.parseInt(tempMax);
			transfer.setTransferNo(maxOrder);
		 }
		transfer.setTransferDate(CommonUtil.ConvertStringToSQLDate(transferForm.getTransferDate()));
		if(transferForm.getRemarks().trim()!=null && !StringUtils.isEmpty(transferForm.getRemarks().trim())){
			transfer.setRemarks(transferForm.getRemarks().trim());
		}
		transfer.setIsActive(true);
		transfer.setCreatedBy(transferForm.getUserId().trim());
		transfer.setModifiedBy(transferForm.getUserId().trim());
		transfer.setCreatedDate(new Date());
		transfer.setLastModifiedDate(new Date());
		//Prepare the child object as a set
		Iterator<ItemTO> iterator= transferForm.getTransferItemList().iterator();
		while (iterator.hasNext()) {
			ItemTO itemTO = iterator.next();
			//If item is available in stock list then go for transfer
			if(fromInventoryitemStockMap != null && !fromInventoryitemStockMap.isEmpty() &&
					fromInventoryitemStockMap.containsKey(itemTO.getId())){
					ItemStockTO itemStockTO = fromInventoryitemStockMap.get(itemTO.getId());
					
					String availableStock = "";
					if(itemStockTO.getAvailableStock()!=null){
						availableStock = itemStockTO.getAvailableStock();
					}				
					stockPresent = Double.valueOf(itemStockTO.getAvailableStock()).doubleValue();
					transferQuantity = Double.valueOf(itemTO.getQuantityIssued()).doubleValue();
					//If selected quantity is less or equal to stock quantity then go gor transfer
						if(stockPresent >= transferQuantity){
							//Add for transfer
							transferItem = new InvStockTransferItem();
							invItem = new InvItem();
							invItem.setId(Integer.valueOf(itemTO.getId()));
							transferItem.setInvItem(invItem);
							
							transferItem.setQuantity(new BigDecimal(itemTO.getQuantityIssued().trim()));
							
							transferItem.setCreatedBy(transferForm.getUserId().trim());
							transferItem.setModifiedBy(transferForm.getUserId().trim());
							transferItem.setIsActive(true);
							transferItem.setCreatedDate(new Date());
							transferItem.setLastModifiedDate(new Date());
							invStockTransferItemSet.add(transferItem);
							
							//Deduct the stock from the availability
							double newStock = 0.0;
							newStock = stockPresent - transferQuantity;
							itemStockTO.setAvailableStock(String.valueOf(newStock));
							
							//Used for InvTX Transaction
							invTx = new InvTx();
							invTx.setInvItem(invItem);
							invLocation = new InvLocation();
							invLocation.setId(itemStockTO.getInvLocationTO().getId());
							invTx.setInvLocation(invLocation);
							invTx.setTxType(CMSConstants.STOCKTRANSFER_ISSUE_TX_TYPE);
							invTx.setTxDate(new Date());
							invTx.setQuantity(new BigDecimal(transferQuantity));
							invTx.setOpeningBalance(new BigDecimal(stockPresent));
							invTx.setClosingBalance(new BigDecimal(newStock));
							invTx.setCreatedBy(transferForm.getUserId().trim());
							invTx.setModifiedBy(transferForm.getUserId().trim());
							invTx.setCreatedDate(new Date());
							invTx.setLastModifiedDate(new Date());
							invTx.setIsActive(true);
							invTXList.add(invTx);
						}
					//Otherwise append the appropriate error message
						else{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_AVAILABLE, itemTO.getNameWithCode(), availableStock));
						}
				}
			//Otherwise append the appropriate the error message
			if(!fromInventoryitemStockMap.containsKey(itemTO.getId())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE,itemTO.getNameWithCode()));
			}
			if(fromInventoryitemStockMap != null  && !fromInventoryitemStockMap.isEmpty() && 
				toInventoryitemStockMap!= null && toInventoryitemStockMap.containsKey(itemTO.getId())
				&& fromInventoryitemStockMap.containsKey(itemTO.getId())){
				ItemStockTO itemStockTO = toInventoryitemStockMap.get(itemTO.getId());
				stockPresent = Double.valueOf(itemStockTO.getAvailableStock()).doubleValue();
				transferQuantity = Double.valueOf(itemTO.getQuantityIssued()).doubleValue();
				//Add the stock to the TO Inventory
				double newStock = 0.0;
				newStock = stockPresent + transferQuantity;
				itemStockTO.setAvailableStock(String.valueOf(newStock));
				//Used for InvTX Transaction
				invTx = new InvTx();
				invTx.setInvItem(invItem);
				invLocation = new InvLocation();
				invLocation.setId(itemStockTO.getInvLocationTO().getId());
				invTx.setInvLocation(invLocation);
				invTx.setTxType(CMSConstants.STOCKTRANSFER_RECEIVE_TX_TYPE);
				invTx.setTxDate(new Date());
				invTx.setQuantity(new BigDecimal(transferQuantity));
				invTx.setOpeningBalance(new BigDecimal(stockPresent));
				invTx.setClosingBalance(new BigDecimal(newStock));
				invTx.setCreatedBy(transferForm.getUserId().trim());
				invTx.setModifiedBy(transferForm.getUserId().trim());
				invTx.setCreatedDate(new Date());
				invTx.setLastModifiedDate(new Date());
				invTx.setIsActive(true);
				invTXList.add(invTx);
			}
			if(fromInventoryitemStockMap!=null && !fromInventoryitemStockMap.isEmpty() 
				&& !toInventoryitemStockMap.containsKey(itemTO.getId()) && 
				fromInventoryitemStockMap.containsKey(itemTO.getId())){
				ItemStockTO itemStockTO = new ItemStockTO();
				ItemTO newItemTO = new ItemTO();
				newItemTO.setId(itemTO.getId());
				itemStockTO.setItemTO(newItemTO);
				SingleFieldMasterTO locationTO = new SingleFieldMasterTO();
				locationTO.setId(Integer.valueOf(transferForm.getToInventoryId()));
				itemStockTO.setInvLocationTO(locationTO);
				itemStockTO.setAvailableStock(itemTO.getQuantityIssued());
				itemStockTO.setCreatedDate(new Date());
				itemStockTO.setCreatedBy(transferForm.getUserId());
				toInventoryitemStockMap.put(itemTO.getId(), itemStockTO);
				
				//Used for InvTX Transaction
				
				invTx = new InvTx();
				invTx.setInvItem(invItem);
				invLocation = new InvLocation();
				invLocation.setId(itemStockTO.getInvLocationTO().getId());
				invTx.setInvLocation(invLocation);
				invTx.setTxType(CMSConstants.STOCKTRANSFER_RECEIVE_TX_TYPE);
				invTx.setTxDate(new Date());
				invTx.setQuantity(new BigDecimal(itemTO.getQuantityIssued()));
				invTx.setOpeningBalance(new BigDecimal(0));
				invTx.setClosingBalance(new BigDecimal(itemTO.getQuantityIssued()));
				invTx.setCreatedBy(transferForm.getUserId().trim());
				invTx.setModifiedBy(transferForm.getUserId().trim());
				invTx.setCreatedDate(new Date());
				invTx.setLastModifiedDate(new Date());
				invTx.setIsActive(true);
				invTXList.add(invTx);
			}
		}
		transfer.setInvStockTransferItems(invStockTransferItemSet);
		
		List<ItemStockTO> updatedItemStockList = new ArrayList<ItemStockTO>();
		updatedItemStockList.addAll(fromInventoryitemStockMap.values());
		updatedItemStockList.addAll(toInventoryitemStockMap.values());
		
		transferForm.setUpdatedItemStockList(updatedItemStockList);
		transferForm.setInvTxList(invTXList);
		
		log.info("Leaving into copyTOToBOForTransfer StockTransferHelper");
		return transfer;
	}
	
	/**
	 * Used to create item stock Map
	 * Creates the map from the stock list retrieved based on the inventory
	 * Keep the item code as key and rest are values.
	 */
	public Map<String, ItemStockTO> createStockMapOnInventoyCode(List<InvItemStock> itemStockList)throws Exception{
		log.info("entering into createStockMapOnInventoyCode StockTransferHelper");
		Map<String, ItemStockTO> itemStockMap = new HashMap<String, ItemStockTO>();
		ItemStockTO itemStockTO = null;
		SingleFieldMasterTO invLocationTO = null;
		ItemTO itemTO = null;		
		if(itemStockList!=null && !itemStockList.isEmpty()){
			Iterator<InvItemStock> stockIterator = itemStockList.iterator();
			while (stockIterator.hasNext()) {
				InvItemStock invItemStock = stockIterator.next();
				itemStockTO = new ItemStockTO();
				itemStockTO.setId(invItemStock.getId());
				if(invItemStock.getInvLocation()!=null){
					invLocationTO = new SingleFieldMasterTO();
					invLocationTO.setId(invItemStock.getInvLocation().getId());
					itemStockTO.setInvLocationTO(invLocationTO);
				}
				if(invItemStock.getAvailableStock()!=null){
					itemStockTO.setAvailableStock(String.valueOf(invItemStock.getAvailableStock()));
				}
				if(invItemStock.getInvItem()!=null){
					itemTO = new ItemTO();
					itemTO.setId(String.valueOf(invItemStock.getInvItem().getId()));
					itemStockTO.setItemTO(itemTO);
					itemStockMap.put(String.valueOf(invItemStock.getInvItem().getId()), itemStockTO);
				}
			}
		}
		log.info("Leaving into createStockMapOnInventoyCode StockTransferHelper");
		return itemStockMap;
	}
	
	/**
	 * Used to generate the updated stock item List after the stock transfer
	 * Generates the item stock BO from the TO
	 */
	public List<InvItemStock>updatedStockAfterTransfer(StockTransferForm transferForm)throws Exception{
		log.info("entering into updatedStockAfterTransfer StockTransferHelper");
		List<InvItemStock> updatedStockList = new ArrayList<InvItemStock>();
		InvItemStock itemStock = null;
		InvItem invItem = null;
		InvLocation invLocation = null;
			
			if(transferForm.getUpdatedItemStockList()!=null && !transferForm.getUpdatedItemStockList().isEmpty()){
				Iterator<ItemStockTO> iterator = transferForm.getUpdatedItemStockList().iterator();
				while (iterator.hasNext()) {
					ItemStockTO itemStockTO = iterator.next();
					itemStock = new InvItemStock();
					invItem = new InvItem();
					invLocation = new InvLocation();
					if(itemStockTO.getId()!=0){
						itemStock.setId(itemStockTO.getId());
					}
					if(itemStockTO.getAvailableStock()!=null && !itemStockTO.getAvailableStock().isEmpty()){
						itemStock.setAvailableStock(new BigDecimal(itemStockTO.getAvailableStock()));
					}
					if(itemStockTO.getItemTO()!=null){
						invItem.setId(Integer.valueOf(itemStockTO.getItemTO().getId()));
					}
					if(itemStockTO.getInvLocationTO()!=null){
						invLocation.setId(itemStockTO.getInvLocationTO().getId());
					}
					itemStock.setInvItem(invItem);
					itemStock.setInvLocation(invLocation);
					itemStock.setModifiedBy(transferForm.getUserId());
					itemStock.setLastModifiedDate(new Date());
					itemStock.setIsActive(true);
					if(itemStockTO.getCreatedBy()!=null && !StringUtils.isEmpty(itemStockTO.getCreatedBy())){
						itemStock.setCreatedBy(itemStockTO.getCreatedBy());
					}
					if(itemStockTO.getCreatedDate()!=null){
						itemStock.setCreatedDate(new Date());
					}
					updatedStockList.add(itemStock);
				}
			}
		log.info("Leaving into updatedStockAfterTransfer StockTransferHelper");
		return updatedStockList;
	}
}
