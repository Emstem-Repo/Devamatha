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

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvIssueItem;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.bo.admin.InvRequestItem;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.IssueMaterialForm;
import com.kp.cms.to.inventory.IssueMaterialTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IIssueMaterialTransaction;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactionsimpl.inventory.IssueMaterialTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class IssueMaterialHelper {
	private static final Log log = LogFactory.getLog(IssueMaterialHelper.class);
	public static volatile IssueMaterialHelper issueMaterialHelper = null;

	private IssueMaterialHelper(){	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static IssueMaterialHelper getInstance() {
		if (issueMaterialHelper == null) {
			issueMaterialHelper = new IssueMaterialHelper();
		}
		return issueMaterialHelper;
	}
	
	/**
	 * Converts request BO to TO
	 */
	public void copyInvReqestBOToTO(InvRequest invRequest, IssueMaterialForm materialForm,ActionErrors errors)throws Exception{
		log.info("Entering into copyInvReqestBOToTO IssueMaterialHelper");
			IssueMaterialTO materialTO = new IssueMaterialTO();
			List<ItemTO> itemList = new ArrayList<ItemTO>();
			ItemTO itemTO = null;
			String requestedBy = "";
			String department = "";
			
			if(invRequest.getRequestedBy().trim()!=null){
				requestedBy = invRequest.getRequestedBy();
				materialTO.setRaisedBy(invRequest.getRequestedBy());
			}
			//Get the department of who have requested
			if(!StringUtils.isEmpty(requestedBy)){
				IIssueMaterialTransaction transaction = new IssueMaterialTransactionImpl();
				department = transaction.getDepartmentByUserName(requestedBy);
			}
			materialForm.setDepartment("("+department+")");
			if(invRequest.getRequestDate()!=null){
				materialTO.setRaisedOn(CommonUtil.getStringDate(invRequest.getRequestDate()));
			}
			if(invRequest.getStatus().trim()!=null){
				materialTO.setStatus(invRequest.getStatus().trim());
			}
			if(invRequest.getInvLocation()!=null){
				materialTO.setInvLocationId(String.valueOf(invRequest.getInvLocation().getId()));
				materialTO.setInventoryName(invRequest.getInvLocation().getName().trim());
			}
			materialTO.setInvRequestId(String.valueOf(invRequest.getId()));
			
			IStockTransferTransaction transaction = new StockTransferTransactionImpl();
			//Gets the stock items for the Inventory
			List<InvItemStock> inventoryItemStockList = null;
			if(materialTO.getInvLocationId()!=null){
				inventoryItemStockList = transaction.getItemStockOnInventory(Integer.valueOf(materialTO.getInvLocationId()));
			}
			//Used to create stock map.
			Map<String, ItemStockTO> inventoryItemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(inventoryItemStockList);
			
			double totalAvailable=0;
			
			if(invRequest.getInvRequestItems()!=null && !invRequest.getInvRequestItems().isEmpty()){
				Iterator<InvRequestItem> iterator = invRequest.getInvRequestItems().iterator();
				while (iterator.hasNext()) {
					InvRequestItem invRequestItem = iterator.next();
					itemTO = new ItemTO();
					if(invRequestItem.getInvItem()!=null){
						itemTO.setId(String.valueOf(invRequestItem.getInvItem().getId()));
						StringBuffer buffer = new StringBuffer();
						if(invRequestItem.getInvItem().getName().trim()!=null){
							 buffer.append(invRequestItem.getInvItem().getName().trim());
						}
						if(invRequestItem.getInvItem().getCode().trim()!=null){
							buffer.append("("+invRequestItem.getInvItem().getCode()+")");
						}
						itemTO.setNameWithCode(buffer.toString());
						//Used to display available quantity for the particular item
						String availableQuantity = "0.0";
						if(inventoryItemStockMap!=null && inventoryItemStockMap.containsKey(String.valueOf(invRequestItem.getInvItem().getId()))){
							ItemStockTO itemStockTO = inventoryItemStockMap.get(String.valueOf(invRequestItem.getInvItem().getId()));
							if(itemStockTO != null && itemStockTO.getAvailableStock()!=null){
								availableQuantity = itemStockTO.getAvailableStock();
								totalAvailable=totalAvailable+Double.parseDouble(itemStockTO.getAvailableStock());
							}				
						}
						itemTO.setAvailableQuantity(availableQuantity);
//						if(availableQuantity.equals("0.0")){
//							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
//						}
					}
					if(invRequestItem.getQuantity()!=null){
						itemTO.setRequestedQuantity(String.valueOf(invRequestItem.getQuantity()));
					}					
					itemList.add(itemTO);
				}
			}
			if(totalAvailable==0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.inventory.available.quantity.issued"));
		    }
			materialForm.setItemList(itemList);
			materialForm.setMaterialTO(materialTO);
			log.info("Leaving into copyInvReqestBOToTO IssueMaterialHelper");
	}
	
	/**
	 * Used to prepare the Issue and Issue Item BOS
	 */
	public InvIssue prepareMaterialBOSForIssue(IssueMaterialForm materialForm)throws Exception{
		log.info("Entering into prepareMaterialBOSForIssue IssueMaterialHelper");
		IssueMaterialTO materialTO = materialForm.getMaterialTO();
		List<ItemTO>itemList = materialForm.getItemList();		
		InvIssue inIssue = new InvIssue();
		InvRequest request = new InvRequest();
		InvLocation location = new InvLocation();
		Set<InvIssueItem> issueItemSet= new HashSet<InvIssueItem>();
		InvIssueItem issueItem = null;
		InvItem invItem = null;
				
			if(materialTO.getInvRequestId()!=null){
				request.setId(Integer.valueOf(materialTO.getInvRequestId().trim()));
			}
			inIssue.setInvRequest(request);
			if(materialTO.getInvLocationId()!=null){
				location.setId(Integer.valueOf(materialTO.getInvLocationId().trim()));
			}
			inIssue.setInvLocation(location);
			inIssue.setIssueDate(new Date());
			inIssue.setCreatedBy(materialForm.getUserId());
			inIssue.setModifiedBy(materialForm.getUserId());
			inIssue.setCreatedDate(new Date());
			inIssue.setLastModifiedDate(new Date());
			inIssue.setIsActive(true);
			if(itemList!=null && !itemList.isEmpty()){
				Iterator<ItemTO> iterator = itemList.iterator();
				while (iterator.hasNext()) {
					ItemTO itemTO = iterator.next();
					if(itemTO.getQuantityIssued()!=null && !itemTO.getQuantityIssued().isEmpty()){
						issueItem = new InvIssueItem();
						invItem = new InvItem();
						invItem.setId(Integer.valueOf(itemTO.getId().trim()));
						issueItem.setInvItem(invItem);					
						issueItem.setQuantity(new BigDecimal(itemTO.getQuantityIssued()));
						issueItem.setCreatedBy(materialForm.getUserId());
						issueItem.setModifiedBy(materialForm.getUserId());
						issueItem.setCreatedDate(new Date());
						issueItem.setLastModifiedDate(new Date());
						issueItem.setIsActive(true);
						issueItemSet.add(issueItem);
					}
				}
				inIssue.setInvIssueItems(issueItemSet);
			}
		log.info("Leaving into prepareMaterialBOSForIssue IssueMaterialHelper");
		return inIssue;
	}
	
	/**
	 * Used to create ItemStock BO
	 * In order to update ItemStock
	 * 
	 */
	public Map<String, ItemStockTO> updateItemStock(Map<String, ItemStockTO> itemStockMap, IssueMaterialForm materialForm, ActionErrors errors)throws Exception{
		log.info("Entering into populateItemStock IssueMaterialHelper");
		//gets the itemList from form
		List<InvTx> invTXList = new ArrayList<InvTx>();
		InvTx invTx = null;
		InvLocation invLocation = null;
		InvItem invItem = null;
		
		List<ItemTO> itemList = materialForm.getItemList();
		double availableStock = 0.0;
		String newStock = "";
		double issuedStock = 0.0;
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> itemIterator = itemList.iterator();
			while (itemIterator.hasNext()) {
				ItemTO itemTO = itemIterator.next();
				//Checks if the stock is available for the item then update with the new quantity issued items
				if(itemStockMap.containsKey(itemTO.getId())){
					ItemStockTO stockTO = itemStockMap.get(itemTO.getId());
					availableStock = Double.valueOf(stockTO.getAvailableStock()).doubleValue();
					if(!StringUtils.isEmpty(itemTO.getQuantityIssued())){
						issuedStock = Double.valueOf(itemTO.getQuantityIssued()).doubleValue();					
						if(issuedStock <= availableStock){
							newStock = String.valueOf(availableStock - issuedStock);
							stockTO.setAvailableStock(newStock);
		
							//Used for InvTX Transaction
							invTx = new InvTx();
							invItem = new InvItem();
							invItem.setId(Integer.valueOf(itemTO.getId()));
							invTx.setInvItem(invItem);
							invLocation = new InvLocation();
							invLocation.setId(stockTO.getInvLocationTO().getId());
							invTx.setInvLocation(invLocation);
							invTx.setTxType(CMSConstants.ISSUE_TX_TYPE);
							invTx.setTxDate(new Date());
							invTx.setQuantity(new BigDecimal(issuedStock));
							invTx.setOpeningBalance(new BigDecimal(availableStock));
							invTx.setClosingBalance(new BigDecimal(newStock));
							invTx.setCreatedBy(materialForm.getUserId().trim());
							invTx.setModifiedBy(materialForm.getUserId().trim());
							invTx.setCreatedDate(new Date());
							invTx.setLastModifiedDate(new Date());
							invTx.setIsActive(true);
							invTXList.add(invTx);				
						}
						else{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_AVAILABLE, itemTO.getNameWithCode(), availableStock));
						}
					}					
				}
				//Otherwise append the appropriate the error message
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE,itemTO.getNameWithCode()));
				}
			}
		}
		materialForm.setInvTxList(invTXList);
		log.info("Leaving into populateItemStock IssueMaterialHelper");
		return itemStockMap;
	}
	/**
	 * Converts update Item stock To to BO
	 */
	public List<InvItemStock>covertupdateItemStockTOToBO(Map<String, ItemStockTO> stockMap, IssueMaterialForm materialForm)throws Exception{
		log.info("Entering into covertupdateItemStockTOToBO IssueMaterialHelper");
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
					invItemStock.setModifiedBy(materialForm.getUserId());
					invItemStock.setLastModifiedDate(new Date());
					updateStockList.add(invItemStock);
				}
			}
		}
		log.info("Leaving into covertupdateItemStockTOToBO IssueMaterialHelper");
		return updateStockList;
	}
	
	/**
	 * Used to update prevoius issued items for the requestID
	 */
	public InvIssue updatePreviousIssuedItems(InvIssue issue, IssueMaterialForm materialForm,
		ActionErrors errors)throws Exception{
		log.info("Entering into updatePreviousIssuedItems IssueMaterialHelper");
		InvIssueItem invIssueItem = null;
		InvItem invItem = null;
		List<ItemTO> itemList = materialForm.getItemList();
		Map<Integer, InvIssueItem> previousIssuedItemMap = generateItemMapForOldIssuedItems(issue);
		double requestedQuantity=0.0;
		double previousIssuedQuantity = 0.0;
		double nowIssuedQuantity = 0.0;
		double totalQuantity = 0.0;
		
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> itr = itemList.iterator();
			while (itr.hasNext()) {
				ItemTO itemTO = (ItemTO) itr.next();
				//Works for already issued items. updating the quantity
				if(previousIssuedItemMap != null && previousIssuedItemMap.containsKey(Integer.valueOf(itemTO.getId()))){
					
					invIssueItem = previousIssuedItemMap.get(Integer.valueOf(itemTO.getId()));
						//Calculates the previous issued quantity and now new issued quantity
						//And validates the total quantity
						if(!StringUtils.isEmpty(itemTO.getQuantityIssued())){
							nowIssuedQuantity= Double.valueOf(itemTO.getQuantityIssued()).doubleValue();							
							requestedQuantity = Double.valueOf(itemTO.getRequestedQuantity()).doubleValue();
							if(invIssueItem.getQuantity()!=null){
								previousIssuedQuantity = Double.valueOf(String.valueOf(invIssueItem.getQuantity())).doubleValue();
							}					
							totalQuantity = previousIssuedQuantity + nowIssuedQuantity ;							
							if(totalQuantity <= requestedQuantity){
								invIssueItem.setModifiedBy(materialForm.getUserId());
								invIssueItem.setLastModifiedDate(new Date());
								invIssueItem.setQuantity(new BigDecimal(totalQuantity));
								previousIssuedItemMap.put(Integer.valueOf(itemTO.getId()), invIssueItem);
							}
							else{
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_ISSUE_ITEM_QUANTITY_EXCEEDS, 
								itemTO.getNameWithCode(), itemTO.getRequestedQuantity(), previousIssuedQuantity));
							}
						}
				}
				//Works for newly issued items
				else{
					invIssueItem = new InvIssueItem();
					invItem = new InvItem();
					invItem.setId(Integer.valueOf(itemTO.getId()));
					invIssueItem.setInvItem(invItem);
					if(itemTO.getQuantityIssued()!=null){
						invIssueItem.setQuantity(new BigDecimal(itemTO.getQuantityIssued()));
					}
					invIssueItem.setIsActive(true);
					invIssueItem.setCreatedDate(new Date());
					invIssueItem.setLastModifiedDate(new Date());
					invIssueItem.setCreatedBy(materialForm.getUserId());
					invIssueItem.setModifiedBy(materialForm.getUserId());
					
					previousIssuedItemMap.put(Integer.valueOf(itemTO.getId()), invIssueItem);
				}
			}
		}
		//Prepare the final issue Bo and issuedItemBOSet for update
		issue.setInvIssueItems(null);
		Set<InvIssueItem> updatedIssuedItemSet = new HashSet<InvIssueItem>();
		if(previousIssuedItemMap!=null && !previousIssuedItemMap.isEmpty()){
			updatedIssuedItemSet.addAll(previousIssuedItemMap.values());
		}
		issue.setInvIssueItems(updatedIssuedItemSet);
		issue.setModifiedBy(materialForm.getUserId());
		issue.setLastModifiedDate(new Date());
		log.info("Leaving into updatePreviousIssuedItems IssueMaterialHelper");
		return issue;
	}
	
	/**
	 * Generates item map for the old issued items
	 */
	public static Map<Integer, InvIssueItem> generateItemMapForOldIssuedItems(InvIssue issue)throws Exception{
		log.info("Entering into generateItemMapForOldIssuedItems IssueMaterialHelper");
		Map<Integer, InvIssueItem> itemMap = new HashMap<Integer, InvIssueItem>();		
		if(issue.getInvIssueItems()!=null && !issue.getInvIssueItems().isEmpty()){
			Iterator<InvIssueItem> iterator = issue.getInvIssueItems().iterator();
			while (iterator.hasNext()) {
				InvIssueItem invIssueItem = iterator.next();
				if(invIssueItem.getInvItem()!=null){
					itemMap.put(invIssueItem.getInvItem().getId(), invIssueItem);
				}
			}
		}
		log.info("Leaving into generateItemMapForOldIssuedItems IssueMaterialHelper");
		return itemMap;
	}
}
