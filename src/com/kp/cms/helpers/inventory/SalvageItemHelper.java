package com.kp.cms.helpers.inventory;

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

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvSalvage;
import com.kp.cms.bo.admin.InvSalvageItem;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.SalvageItemForm;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class SalvageItemHelper {

		private static final Log log = LogFactory.getLog(SalvageItemHelper.class);
		public static volatile SalvageItemHelper salvageItemHelper = null;

		/**
		 * 
		 * @returns a single instance (Singleton)every time. 
		 */
		public static SalvageItemHelper getInstance() {
			if (salvageItemHelper == null) {
				salvageItemHelper = new SalvageItemHelper();
			}
			return salvageItemHelper;
		}
		
		/**
		 * This method is used to convert TO to BO.
		 * @param salvageItemForm
		 * @return
		 * @throws Exception
		 */
		
		public InvSalvage convertTOtoBOSalvage(SalvageItemForm salvageItemForm) throws Exception{
			log.info("entering of saveSalvageDetails in SalvageItemHandler class..");
			InvSalvage salvage = new InvSalvage();
			InvSalvageItem salvageItem = new InvSalvageItem();
			Set<InvSalvageItem> salvageItemSet = new HashSet<InvSalvageItem>();
			InvLocation location = new InvLocation();
			InvItem invItem = new InvItem();
			InvUom invUom = new InvUom();
			Map<String, InvSalvageTO> itemMap = salvageItemForm.getItemMap();
			if(itemMap != null && itemMap.size() != 0){
				
			InvSalvageTO salvageTO = itemMap.get(salvageItemForm.getItemId());
				
			invItem.setId(Integer.parseInt(salvageItemForm.getItemId()));
			salvageItem.setQuantity(BigDecimal.valueOf(Double.parseDouble(salvageItemForm.getQuantity().trim())));
			salvageItem.setInvItem(invItem);
			if(salvageTO.getItemTO()!= null && salvageTO.getItemTO().getIssueUomId() != 0){
				invUom.setId(salvageTO.getItemTO().getIssueUomId());
			}else{
				invUom.setId(salvageTO.getItemTO().getPurchaseUomId());
			}
				
			salvageItem.setInvUom(invUom);
			salvageItem.setIsActive(Boolean.TRUE);
			salvageItem.setCreatedBy(salvageItemForm.getUserId());
			salvageItem.setCreatedDate(new Date());
			salvageItem.setLastModifiedDate(new Date());
			salvageItem.setModifiedBy(salvageItemForm.getUserId());
			
			location.setId(Integer.parseInt(salvageItemForm.getInventoryLocation()));
			salvage.setInvLocation(location);
			salvage.setDate(CommonUtil.ConvertStringToDate(salvageItemForm.getSalvageDate()));
			salvageItemSet.add(salvageItem);

			}
			
			
//			List<InvSalvageTO> itemsList = salvageItemForm.getItemtransferList();
//			Iterator<InvSalvageTO> iterator = itemsList.iterator();
//			while (iterator.hasNext()) {
//				InvSalvageTO salvageTO = (InvSalvageTO) iterator.next();
//				invItem = new InvItem();
//				invUom = new InvUom();
//				location = new InvLocation();
//				salvageItem = new InvSalvageItem();
//				if(salvageTO.getItemTO().getIssueUomId() != 0){
//					
//					
//					invItem.setId(Integer.parseInt(salvageTO.getItemTO().getId()));
//					invItem.setName(salvageTO.getItemTO().getName());
//					salvageItem.setQuantity(BigDecimal.valueOf(Double.parseDouble(salvageTO.getItemTO().getQuantityIssued())));
//					salvageItem.setInvItem(invItem);
//					invUom.setId(salvageTO.getItemTO().getIssueUomId());
//					salvageItem.setInvUom(invUom);
//					salvageItem.setIsActive(Boolean.TRUE);
//					salvageItem.setCreatedBy(salvageItemForm.getUserId());
//					salvageItem.setCreatedDate(new Date());
//					salvageItem.setLastModifiedDate(new Date());
//					salvageItem.setModifiedBy(salvageItemForm.getUserId());
//					
//					location.setId(Integer.parseInt(salvageItemForm.getInventoryLocation()));
//					salvage.setInvLocation(location);
//					salvage.setDate(CommonUtil.ConvertStringToDate(salvageItemForm.getSalvageDate()));
//					salvageItemSet.add(salvageItem);
//				}else{
//					invItem.setId(Integer.parseInt(salvageTO.getItemTO().getId()));
//					invItem.setName(salvageTO.getItemTO().getName());
//					salvageItem.setQuantity(BigDecimal.valueOf(Double.parseDouble(salvageTO.getItemTO().getQuantityIssued())));
//					salvageItem.setInvItem(invItem);
//					invUom.setId(salvageTO.getItemTO().getPurchaseUomId());
//					salvageItem.setInvUom(invUom);
//					salvageItem.setIsActive(Boolean.TRUE);
//					salvageItem.setCreatedBy(salvageItemForm.getUserId());
//					salvageItem.setCreatedDate(new Date());
//					salvageItem.setLastModifiedDate(new Date());
//					salvageItem.setModifiedBy(salvageItemForm.getUserId());
//					
//					salvageItemSet.add(salvageItem);
//					location.setId(Integer.parseInt(salvageItemForm.getInventoryLocation()));
//					salvage.setInvLocation(location);
//					salvage.setDate(CommonUtil.ConvertStringToDate(salvageItemForm.getSalvageDate()));
//				}
//			}
			salvage.setInvSalvageItems(salvageItemSet);
			
			
			salvage.setCreatedBy(salvageItemForm.getUserId());
			salvage.setCreatedDate(new Date());
			salvage.setModifiedBy(salvageItemForm.getUserId());
			salvage.setLastModifiedDate(new Date());
			
			salvage.setIsActive(Boolean.TRUE);
			salvage.setRemarks(salvageItemForm.getRemarks());
			
			
			log.info("exit of saveSalvageDetails in SalvageItemHandler class..");
			return salvage;
		}
		
		
		
		/**
		 * Used to create ItemStock BO
		 * In order to update ItemStock
		 * 
		 */
		public Map<String, ItemStockTO> updateItemStock(Map<String, ItemStockTO> itemStockMap,SalvageItemForm salvageItemForm, ActionErrors errors)throws Exception{
			log.info("Entering into populateItemStock IssueMaterialHelper");
			//gets the itemList from form
			Map<String, InvSalvageTO> itemMap = salvageItemForm.getItemMap();
			double availableStock = 0.0;
			String newStock = "";
			double issuedStock = 0.0;
			if(itemMap!=null && !itemMap.isEmpty()){
				
					InvSalvageTO salvageTO = itemMap.get(salvageItemForm.getItemId());
					//Checks if the stock is available for the item then update with the new quantity issued items
					if(itemStockMap.containsKey(salvageTO.getItemTO().getId()) && !StringUtils.isEmpty(salvageItemForm.getQuantity())){
						ItemStockTO stockTO = itemStockMap.get(salvageTO.getItemTO().getId());
						availableStock = Double.valueOf(stockTO.getAvailableStock()).doubleValue();
						issuedStock = Double.valueOf(salvageItemForm.getQuantity()).doubleValue();					
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
		public List<InvItemStock> covertUpdateItemStockTOtoBO(Map<String, ItemStockTO> stockMap, SalvageItemForm salvageItemForm)throws Exception{
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
						invItemStock.setModifiedBy(salvageItemForm.getUserId());
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
		public void updateStockValues(InvSalvage salvage,
				List<InvItemStock> itemStockList,
				SalvageItemForm salvageItemForm) {
			Iterator<InvItemStock> stkItr=itemStockList.iterator();
			List<InvTxTO> transactions= new ArrayList<InvTxTO>();
			List<Integer> matchedItems= new ArrayList<Integer>();
			while (stkItr.hasNext()) {
				InvItemStock itmStock = (InvItemStock) stkItr.next();
				
				// if location matches
				if(itmStock.getInvLocation()!=null
						&& itmStock.getIsActive()!=null
						&& itmStock.getIsActive()
						&& itmStock.getInvLocation().getId()== salvage.getInvLocation().getId()
						&& salvage.getInvSalvageItems()!=null){
					Iterator<InvSalvageItem> itemItr= salvage.getInvSalvageItems().iterator();
					while (itemItr.hasNext()) {
						InvSalvageItem salvageItem = (InvSalvageItem) itemItr.next();
						// if item matches and received qty. entered
						if(salvageItem.getInvItem()!=null 
								&& itmStock.getInvItem()!=null
								&& salvageItem.getInvItem().getId()== itmStock.getInvItem().getId() 
								&& salvageItem.getQuantity()!=null){
							InvTxTO txnTo= new InvTxTO();
							matchedItems.add(salvageItem.getInvItem().getId());
							txnTo.setInvItem(itmStock.getInvItem());
							txnTo.setInvLocation(itmStock.getInvLocation());
							txnTo.setTxType(CMSConstants.SALVAGE_TX_TYPE);
							txnTo.setTxDate(new Date());
							double availablestock=0.0;
							double recievedqty=0.0;
							double conversion=0.0;
							if(itmStock.getAvailableStock()!=null)
							availablestock=itmStock.getAvailableStock().doubleValue();
							
							txnTo.setOpeningBalance(availablestock);
							// change to issue qty
							
							recievedqty=salvageItem.getQuantity().doubleValue();
							
							if(salvageItem.getInvItem().getConversion()!=null){
								conversion=salvageItem.getInvItem().getConversion().doubleValue();
								availablestock=availablestock-(recievedqty*conversion);
								
							}else{
								availablestock=availablestock-recievedqty;
							}
							txnTo.setClosingBalance(availablestock);
							txnTo.setQuantity(recievedqty);
							itmStock.setAvailableStock(new BigDecimal(availablestock));
							txnTo.setCreatedBy(salvageItem.getCreatedBy());
							txnTo.setCreatedDate(new Date());
							itmStock.setModifiedBy(salvageItem.getModifiedBy());
							itmStock.setLastModifiedDate(new Date());
							transactions.add(txnTo);
						}
					}
				}
				
				
				
			}
			
			// add new items
			if(salvage.getInvSalvageItems()!=null){
				if(itemStockList==null)
				{
					itemStockList= new ArrayList<InvItemStock>();
				}
				Iterator<InvSalvageItem> itemItr2= salvage.getInvSalvageItems().iterator();
				while (itemItr2.hasNext()) {
					InvSalvageItem salvageItem = (InvSalvageItem) itemItr2.next();
					if(!matchedItems.contains(salvageItem.getInvItem().getId())){
						InvItemStock itemStock = new InvItemStock();
						InvTxTO txnTo= new InvTxTO();
						itemStock.setInvItem(salvageItem.getInvItem());
						itemStock.setInvLocation(salvage.getInvLocation());
						txnTo.setInvItem(salvageItem.getInvItem());
						txnTo.setInvLocation(salvage.getInvLocation());
						txnTo.setTxType(CMSConstants.SALVAGE_TX_TYPE);
						txnTo.setTxDate(new Date());
						
						double stock=0.0;
						if(salvageItem.getQuantity()!=null){
							if(salvageItem.getInvItem().getConversion()!=null)
							{
								stock=salvageItem.getQuantity().doubleValue()*salvageItem.getInvItem().getConversion().doubleValue();
							}else{
								stock=salvageItem.getQuantity().doubleValue();
							}
						}
						itemStock.setAvailableStock(new BigDecimal(stock));
						txnTo.setOpeningBalance(stock);
						txnTo.setClosingBalance(stock);
						txnTo.setQuantity(stock);
						itemStock.setIsActive(true);
						itemStock.setCreatedBy(salvage.getCreatedBy());
						itemStock.setCreatedDate(salvage.getCreatedDate());
						txnTo.setCreatedBy(salvage.getCreatedBy());
						txnTo.setCreatedDate(new Date());
						txnTo.setModifiedBy(salvage.getModifiedBy());
						txnTo.setLastModifiedDate(new Date());
						itemStockList.add(itemStock);
						transactions.add(txnTo);
					}
				}
			}
			
			salvageItemForm.setTransactions(transactions);
		}
}