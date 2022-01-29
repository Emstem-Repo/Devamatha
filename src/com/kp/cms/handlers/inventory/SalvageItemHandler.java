package com.kp.cms.handlers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvIssueItem;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvSalvage;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.ItemIssueForm;
import com.kp.cms.forms.inventory.SalvageItemForm;
import com.kp.cms.forms.reports.InvSalvageReportForm;
import com.kp.cms.helpers.inventory.SalvageItemHelper;
import com.kp.cms.helpers.inventory.SalvageReportHelper;
import com.kp.cms.helpers.inventory.StockTransferHelper;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactions.inventory.IsalvageItemTransaction;
import com.kp.cms.transactionsimpl.inventory.SalvageItemTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class SalvageItemHandler {

	private static final Log log = LogFactory.getLog(SalvageItemHandler.class);
	
	public static volatile SalvageItemHandler self=null;
	public static SalvageItemHandler getInstance(){
		if(self==null){
			self= new SalvageItemHandler();
		}
		return self;
	}
	
	IsalvageItemTransaction itemTransaction = SalvageItemTransactionImpl.getInstance();
	
	/**
	 * This method is used to save the salvage details to database.
	 * @param itemIssueForm
	 * @return boolean value.
	 * Check for the stock availability
	 * @throws Exception
	 */
	
	public boolean saveSalvageItemDetails(SalvageItemForm salvageItemForm, ActionErrors errors) throws Exception {
		log.info("entering of saveItemIssueDetails in SalvageItemHandler class..");
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		InvSalvage salvage = null;
		boolean result = false;
		List<InvItemStock> updateStockList = null;
		
		//Gets the stock items based on the inventory
		List<InvItemStock> itemStockList = transaction.getItemStockOnInventory(Integer.valueOf(salvageItemForm.getInventoryLocation().trim()));		
		//Used to create stock map.
		Map<String, ItemStockTO> itemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(itemStockList);
		if(itemStockMap == null || itemStockMap.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE_IN_ITEM_STOCK));
			result = false;
		}
		
		
		if(errors.isEmpty()){
			itemStockMap = SalvageItemHelper.getInstance().updateItemStock(itemStockMap, salvageItemForm, errors);
			if(errors.isEmpty()){
				//create the issue BO
				salvage = SalvageItemHelper.getInstance().convertTOtoBOSalvage(salvageItemForm);
				if(salvage !=null && itemStockList != null){
					SalvageItemHelper.getInstance().updateStockValues(salvage,itemStockList,salvageItemForm);
				}
				//create the list of stock bo's
				updateStockList = SalvageItemHelper.getInstance().covertUpdateItemStockTOtoBO(itemStockMap, salvageItemForm);
				
			}			
		}
		
		if(salvage != null){		
			int id =  itemTransaction.saveSalvageDetails(salvage,updateStockList);
			if(id!=0){
				result=true;
				if(salvageItemForm.getTransactions()!=null && !salvageItemForm.getTransactions().isEmpty()){
					Iterator<InvTxTO> txItr=salvageItemForm.getTransactions().iterator();
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
		log.info("exit of saveItemIssueDetails in SalvageItemHandler class..");
		return result;
	}
	
	/**
	 * This method is used to save the item issue details to database.
	 * @param itemIssueForm
	 * @return boolean value.
	 * Check for the stock availability
	 * @throws Exception
	 */
	
	public boolean saveItemIssueDetails(ItemIssueForm itemIssueForm, ActionErrors errors) throws Exception {
		log.info("entering of saveItemIssueDetails in SalvageItemHandler class..");
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		InvIssue invIssue = null;
		boolean result = false;
		List<InvItemStock> updateStockList = null;
		
		//Gets the stock items based on the inventory
		List<InvItemStock> itemStockList = transaction.getItemStockOnInventory(Integer.valueOf(itemIssueForm.getInventoryLocation().trim()));		
		//Used to create stock map.
		Map<String, ItemStockTO> itemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(itemStockList);
		if(itemStockMap == null || itemStockMap.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE_IN_ITEM_STOCK));
			result = false;
		}
		
		
		if(errors.isEmpty()){
			itemStockMap = updateItemStock(itemStockMap, itemIssueForm, errors);
			if(errors.isEmpty()){
				//create the issue BO
				invIssue = convertItemTOtoBOForItemIssue(itemIssueForm);
				if(invIssue !=null && itemStockList != null){
					updateStockValues(invIssue,itemStockList,itemIssueForm);
				}
				//create the list of stock bo's
				updateStockList = covertUpdateItemStockTOtoBO(itemStockMap, itemIssueForm);
				result = true;
			}			
		}
		
		if(invIssue!= null){		
			int id =  itemTransaction.saveItemIssueDetails(invIssue,updateStockList);
			if(id != 0){
				result=true;
				if(itemIssueForm.getTransactions()!=null && !itemIssueForm.getTransactions().isEmpty()){
					Iterator<InvTxTO> txItr=itemIssueForm.getTransactions().iterator();
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
		log.info("exit of saveItemIssueDetails in SalvageItemHandler class..");
		return result;
	}
	

	/**
	 * This method is used to convert TO to BO from ItemIssueForm.
	 * @param itemIssueForm
	 * @return InvIssue BO instance
	 * @throws Exception
	 */
	
	public InvIssue convertItemTOtoBOForItemIssue(ItemIssueForm itemIssueForm) throws Exception {
		log.info("entering of saveItemIssueDetails in SalvageItemHandler class..");		
		InvIssue invIssue = new InvIssue();
		InvIssueItem invIssueItem;
		InvLocation location = new InvLocation();
		InvItem invItem = null;
		Set<InvIssueItem> itemIssueSet = new HashSet<InvIssueItem>();
			Map<String, InvSalvageTO> itemMap = itemIssueForm.getItemMap();
			invIssueItem = new InvIssueItem();
			invItem = new InvItem();
			if(itemMap != null && itemMap.size() != 0){
				
				InvSalvageTO salvageTO = itemMap.get(itemIssueForm.getItemId());
				invItem.setId(Integer.parseInt(salvageTO.getItemTO().getId()));
				invIssueItem.setQuantity(BigDecimal.valueOf(Double.parseDouble(itemIssueForm.getQuantity().trim())));
				invIssueItem.setInvItem(invItem);
				invIssueItem.setIsActive(Boolean.TRUE);
				invIssueItem.setCreatedBy(itemIssueForm.getUserId());
				invIssueItem.setCreatedDate(new Date());
				invIssueItem.setLastModifiedDate(new Date());
				invIssueItem.setModifiedBy(itemIssueForm.getUserId());
			}
				itemIssueSet.add(invIssueItem);
				
			invIssue.setInvIssueItems(itemIssueSet);
			location.setId(Integer.parseInt(itemIssueForm.getInventoryLocation()));
			invIssue.setInvLocation(location);
			invIssue.setIssueTo(itemIssueForm.getIssuedTO().trim());
			invIssue.setIssueDate(CommonUtil.ConvertStringToDate(itemIssueForm.getIssueDate()));
			invIssue.setRemarks(itemIssueForm.getRemarks());
			invIssue.setCreatedBy(itemIssueForm.getUserId());
			invIssue.setCreatedDate(new Date());
			invIssue.setModifiedBy(itemIssueForm.getUserId());
			invIssue.setLastModifiedDate(new Date());
			invIssue.setIsActive(Boolean.TRUE);
			
		return invIssue;
	}
	
	

	/**
	 * Used to create ItemStock BO
	 * In order to update ItemStock
	 * 
	 */
	public Map<String, ItemStockTO> updateItemStock(Map<String, ItemStockTO> itemStockMap,ItemIssueForm itemIssueForm, ActionErrors errors)throws Exception{
		log.info("Entering into populateItemStock IssueMaterialHelper");
		//gets the itemList from form
		Map<String, InvSalvageTO> itemMap = itemIssueForm.getItemMap();
		double availableStock = 0.0;
		String newStock = "";
		double issuedStock = 0.0;
		if(itemMap!=null && !itemMap.isEmpty()){
			
				InvSalvageTO salvageTO = itemMap.get(itemIssueForm.getItemId());
				//Checks if the stock is available for the item then update with the new quantity issued items
				if(itemStockMap.containsKey(salvageTO.getItemTO().getId()) && !StringUtils.isEmpty(itemIssueForm.getQuantity())){
					ItemStockTO stockTO = itemStockMap.get(salvageTO.getItemTO().getId());
					availableStock = Double.valueOf(stockTO.getAvailableStock()).doubleValue();
					issuedStock = Double.valueOf(itemIssueForm.getQuantity()).doubleValue();					
					if(issuedStock <= availableStock){
					newStock = String.valueOf(availableStock - issuedStock);
					stockTO.setAvailableStock(newStock);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_AVAILABLE, salvageTO.getItemTO().getNameWithCode(), availableStock));
					}
				}
				//Otherwise append the appropriate the error message
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE,salvageTO.getItemTO().getNameWithCode()));
				}
		}
	log.info("Leaving into populateItemStock IssueMaterialHelper");
		return itemStockMap;
	}
	
	
	/**
	 * Converts update Item stock To to BO
	 */
	public List<InvItemStock> covertUpdateItemStockTOtoBO(Map<String, ItemStockTO> stockMap, ItemIssueForm itemIssueForm)throws Exception{
		//Item stock map contains all the updates items along with the new items
		//Prepares the update stock BO after issue
		List<InvItemStock> updateStockList = new ArrayList<InvItemStock>();
		InvItemStock invItemStock = null;
		InvItem invItem = null;
		InvLocation invLocation = null;
		
		if(stockMap!=null && !stockMap.isEmpty()){
			List<ItemStockTO> itemStockList = new ArrayList<ItemStockTO>();
			itemStockList.addAll(stockMap.values());
			if(itemStockList!=null && !itemStockList.isEmpty()){
				Iterator<ItemStockTO> iterator = itemStockList.iterator();
				while (iterator.hasNext()) {
					ItemStockTO itemStockTO = iterator.next();
					invItemStock = new InvItemStock();
					if(itemStockTO.getId()!=0){
						invItemStock.setId(itemStockTO.getId());
					}
					invItem = new InvItem();
					if(itemStockTO.getItemTO()!=null){
						invItem.setId(Integer.valueOf(itemStockTO.getItemTO().getId()));
					}
					invItemStock.setInvItem(invItem);
					invItemStock.setIsActive(true);
					invLocation= new InvLocation();
					if(itemStockTO.getInvLocationTO()!=null){
						invLocation.setId(itemStockTO.getInvLocationTO().getId());
					}
					invItemStock.setInvLocation(invLocation);
					if(itemStockTO.getAvailableStock()!=null){
						invItemStock.setAvailableStock(new BigDecimal(itemStockTO.getAvailableStock()));
					}
					invItemStock.setModifiedBy(itemIssueForm.getUserId());
					invItemStock.setLastModifiedDate(new Date());
					updateStockList.add(invItemStock);
				}
			}
		}
		return updateStockList;
	}
	
	/**
	 * update the new stock value in inventory location
	 * @param finalOrder
	 * @param itemStockList
	 * @param returnForm 
	 */
	private void updateStockValues(InvIssue invIssue,
			List<InvItemStock> itemStockList, ItemIssueForm itemIssueForm) {
		Iterator<InvItemStock> stkItr=itemStockList.iterator();
		List<InvTxTO> transactions= new ArrayList<InvTxTO>();
		List<Integer> matchedItems= new ArrayList<Integer>();
		while (stkItr.hasNext()) {
			InvItemStock itmStock = (InvItemStock) stkItr.next();
			
			// if location matches
			if(itmStock.getInvLocation()!=null
					&& itmStock.getIsActive()!=null
					&& itmStock.getIsActive()
					&& itmStock.getInvLocation().getId()== invIssue.getInvLocation().getId()
					&& invIssue.getInvIssueItems()!=null){
				Iterator<InvIssueItem> itemItr= invIssue.getInvIssueItems().iterator();
				while (itemItr.hasNext()) {
					InvIssueItem invIssueItem = (InvIssueItem) itemItr.next();
					// if item matches and received qty. entered
					if(invIssueItem.getInvItem()!=null 
							&& itmStock.getInvItem()!=null
							&& invIssueItem.getInvItem().getId()== itmStock.getInvItem().getId() 
							&& invIssueItem.getQuantity()!=null){
						InvTxTO txnTo= new InvTxTO();
						matchedItems.add(invIssueItem.getInvItem().getId());
						txnTo.setInvItem(itmStock.getInvItem());
						txnTo.setInvLocation(itmStock.getInvLocation());
						txnTo.setTxType(CMSConstants.ISSUE_TX_TYPE);
						txnTo.setTxDate(new Date());
						double availablestock=0.0;
						double recievedqty=0.0;
						double conversion=0.0;
						if(itmStock.getAvailableStock()!=null)
						availablestock=itmStock.getAvailableStock().doubleValue();
						
						txnTo.setOpeningBalance(availablestock);
						// change to issue qty
						
						recievedqty=invIssueItem.getQuantity().doubleValue();
						
						if(invIssueItem.getInvItem().getConversion()!=null){
							conversion=invIssueItem.getInvItem().getConversion().doubleValue();
							availablestock=availablestock-(recievedqty*conversion);
							
						}else{
							availablestock=availablestock-recievedqty;
						}
						txnTo.setClosingBalance(availablestock);
						txnTo.setQuantity(recievedqty);
						itmStock.setAvailableStock(new BigDecimal(availablestock));
						txnTo.setCreatedBy(invIssueItem.getCreatedBy());
						txnTo.setCreatedDate(new Date());
						itmStock.setModifiedBy(invIssueItem.getModifiedBy());
						itmStock.setLastModifiedDate(new Date());
						transactions.add(txnTo);
					}
				}
			}
			
			
			
		}
		
		// add new items
		if(invIssue.getInvIssueItems()!=null){
			if(itemStockList==null)
			{
				itemStockList= new ArrayList<InvItemStock>();
			}
			Iterator<InvIssueItem> itemItr2= invIssue.getInvIssueItems().iterator();
			while (itemItr2.hasNext()) {
				InvIssueItem invIssueItem = (InvIssueItem) itemItr2.next();
				if(!matchedItems.contains(invIssueItem.getInvItem().getId())){
					InvItemStock itemStock = new InvItemStock();
					InvTxTO txnTo= new InvTxTO();
					itemStock.setInvItem(invIssueItem.getInvItem());
					itemStock.setInvLocation(invIssue.getInvLocation());
					txnTo.setInvItem(invIssueItem.getInvItem());
					txnTo.setInvLocation(invIssue.getInvLocation());
					txnTo.setTxType(CMSConstants.ISSUE_TX_TYPE);
					txnTo.setTxDate(new Date());
					
					double stock=0.0;
					if(invIssueItem.getQuantity()!=null){
						if(invIssueItem.getInvItem().getConversion()!=null)
						{
							stock=invIssueItem.getQuantity().doubleValue()*invIssueItem.getInvItem().getConversion().doubleValue();
						}else{
							stock=invIssueItem.getQuantity().doubleValue();
						}
					}
					itemStock.setAvailableStock(new BigDecimal(stock));
					txnTo.setOpeningBalance(stock);
					txnTo.setClosingBalance(stock);
					txnTo.setQuantity(stock);
					itemStock.setIsActive(true);
					itemStock.setCreatedBy(invIssue.getCreatedBy());
					itemStock.setCreatedDate(invIssue.getCreatedDate());
					txnTo.setCreatedBy(invIssue.getCreatedBy());
					txnTo.setCreatedDate(new Date());
					txnTo.setModifiedBy(invIssue.getModifiedBy());
					txnTo.setLastModifiedDate(new Date());
					itemStockList.add(itemStock);
					transactions.add(txnTo);
				}
			}
		}
		
		itemIssueForm.setTransactions(transactions);
	}
	
	
	
	/**
	 * This method is used to the report details.
	 * @param salvageReportForm
	 * @return list of InvSalvageTO
	 * @throws Exception
	 */
	
	public List<InvSalvageTO> getReportDetails(InvSalvageReportForm salvageReportForm) throws Exception{
		log.info("entered getLeaveReportDetails method of LeaveReportHandler class.");
		List<InvSalvage> list = itemTransaction.getReportDetails(SalvageReportHelper.getSelectionSearchCriteria(salvageReportForm));
		if(list != null){
			List<InvSalvageTO> salvageDetailsList = SalvageReportHelper.getSalvageDetails(list);
			return salvageDetailsList;
		}
		log.info("Exit of getLeaveReportDetails method of LeaveReportHandler class.");
		return new ArrayList<InvSalvageTO>();
	}
	
}