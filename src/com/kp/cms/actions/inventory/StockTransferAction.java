package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockTransferForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.helpers.inventory.StockTransferHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class StockTransferAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(StockTransferAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return initilizes stock transfer home page
	 * @throws Exception
	 */
	public ActionForward initStockTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initStockTransfer StockTransferAction");	
		StockTransferForm transferForm = (StockTransferForm)form;
		try {
			setAllInventoryLocationToForm(transferForm);
			StockTransferHandler.getInstance().setTransferNoToForm(transferForm);
			transferForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initStockTransferItemSelection of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			transferForm.setErrorMessage(msg);
			transferForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initStockTransfer StockTransferAction");	
		return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns Stock transfer item selection page
	 * @throws Exception
	 */
	public ActionForward initStockTransferItemSelection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initStockTransferItemSelection StockTransferAction");
		StockTransferForm transferForm = (StockTransferForm)form;
		 ActionErrors errors = transferForm.validate(mapping, request);
		try {
			if (transferForm.getTransferDate().trim() != null && !StringUtils.isEmpty(transferForm.getTransferDate().trim())) {
				errors = validateTransferDate(errors, transferForm.getTransferDate().trim());
			}
			if(transferForm.getFromInventoryId().trim()!=null && !StringUtils.isEmpty(transferForm.getFromInventoryId().trim())
			&& transferForm.getToInventoryId().trim()!=null && !StringUtils.isEmpty(transferForm.getToInventoryId().trim())){
				errors = validateFromTOInventory(errors, transferForm.getFromInventoryId().trim(), transferForm.getToInventoryId().trim());
			}
			if(transferForm.getTransferNo().trim()==null || StringUtils.isEmpty(transferForm.getTransferNo().trim())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_NO_NOT_FOUND));
			}
			if(transferForm.getRemarks()!= null && transferForm.getRemarks().length() > 200){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.tc.remarks"));
			}
			if(errors.isEmpty()){
				setAllItemsToForm(transferForm);
				return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER_ITEM);
			}
		} catch (Exception e) {
			log.error("Error occured in initStockTransferItemSelection of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			transferForm.setErrorMessage(msg);
			transferForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initStockTransferItemSelection StockTransferAction");
		setAllInventoryLocationToForm(transferForm);
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER);
	}
	
	/**
	 * Used to validate the transfer date
	 * Validates the date format and as well as for future date
	 */
	
	public ActionErrors validateTransferDate(ActionErrors errors, String transferDate)throws Exception{
		log.info("entering into validateTransferDate StockTransferAction");
		boolean isValidDateFormat;
		isValidDateFormat = CommonUtil.isValidDate(transferDate);
		if(!isValidDateFormat){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));		
		}
		else if(isValidDateFormat){
			String formattedString=CommonUtil.ConvertStringToDateFormat(transferDate, CMSConstants.DEST_DATE,CMSConstants.DEST_DATE_MM_DD_YYYY);
			Date date = new Date(formattedString);
			Date curdate = new Date();
			boolean result;
			if (date.compareTo(curdate) == 1){
				result = false;
			}
			else{
				result = true;
			}
			if(!result){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_STOCK_TRANSFER_FUTURE_DATE));
			}
		}
		log.info("Leaving into validateTransferDate StockTransferAction");
		return errors;
	}
	
	/**
	 * Used to save stock transfer informations
	 */
	public ActionForward submitStockTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitStockTransfer StockTransferAction");
		StockTransferForm transferForm = (StockTransferForm)form;
		ActionMessages message = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors = validateItemList(transferForm, errors);		
		try {
			if (errors.isEmpty()) {
				setUserId(request, transferForm);
				boolean isTransferred;
				isTransferred = StockTransferHandler.getInstance().submitStockTransfer(transferForm, errors);
				//If no error
				if(isTransferred && errors.isEmpty()){
					message.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.STOCK_TRANSFER_SUCCESS,transferForm.getFromInventoryName().trim(),
					transferForm.getToInventoryName().trim()));
					StockTransferHandler.getInstance().setTransferNoToForm(transferForm);
					addMessages(request, message);
					transferForm.clear();
					return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER);
				}				
			}
		} catch (Exception e) {
			log.error("Error occured in submitStockTransfer of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			transferForm.setErrorMessage(msg);
			transferForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into submitStockTransfer StockTransferAction");
		return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER_ITEM);
	}
	
	/**
	 * Used to get all inventories
	 */
	public void setAllInventoryLocationToForm(StockTransferForm transferForm)throws Exception {
		log.info("entering into setAllHostelsToForm RoomType Action");
		List<SingleFieldMasterTO> inventoryList = StockTransferHandler.getInstance().getAllInventoryLocation();
		transferForm.setInventoryList(inventoryList);
		log.info("Leaving into setAllHostelsToForm RoomType Action");
	}
	
	/**
	 * Used to validate From and To Inventory Selections
	 */
	public ActionErrors validateFromTOInventory(ActionErrors errors, String fromInventory, String toInventory)throws Exception{
		log.info("entering into validateFromTOInventory StockTransferAction");	
		if(fromInventory.equals(toInventory)){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_BOTH_INVENTORY_EQUAL));
		}
		log.info("Leaving into validateFromTOInventory StockTransferAction");	
		return errors;
	}
	
	/**
	 * used to get all items from backend
	 * And also creates a Map to keep the item informations needed to show in UI as Item List
	 */
	
	public void setAllItemsToForm(StockTransferForm transferForm)throws Exception{
		log.info("entering into setAllItemsToForm StockTransferAction");	
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		//Gets the stock items for From Inventroy
		List<InvItemStock> fromInventoryItemStockList = transaction.getItemStockOnInventory(Integer.valueOf(transferForm.getFromInventoryId().trim()));
		//Used to create stock map.
		Map<String, ItemStockTO> fromInventoryItemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(fromInventoryItemStockList);
	
		Map<String, ItemTO> itemMap = new HashMap<String, ItemTO>();
		if(itemList!=null && !itemList.isEmpty()){
			
			Iterator<ItemTO> ItemIterator = itemList.iterator();
			while (ItemIterator.hasNext()) {
				ItemTO itemTO = ItemIterator.next();
				String availableStock = "";
				String itemLabel = "";
				String issueUomName = "";
				String nameWithCode = "";
				//Check the item in stock and append with available quantity and issued uom name
				if(fromInventoryItemStockMap!=null && !fromInventoryItemStockMap.isEmpty() 
					&& fromInventoryItemStockMap.containsKey(itemTO.getId())){
					ItemStockTO itemStockTO = fromInventoryItemStockMap.get(itemTO.getId());
					if(itemStockTO.getAvailableStock()!= null){
						availableStock = itemStockTO.getAvailableStock();
					}
					if(itemTO.getNameWithCode()!=null){
						nameWithCode =  itemTO.getNameWithCode();
					}
					if(itemTO.getIssueUomName()!=null){
						issueUomName = itemTO.getIssueUomName();
					}
					itemLabel = nameWithCode+ " " + ":"+ " " + availableStock + " " + issueUomName;
					itemTO.setItemLabel(itemLabel);
				}
				else{
					if(itemTO.getNameWithCode()!=null){
						nameWithCode =  itemTO.getNameWithCode();
					}
					availableStock = "0.00";
					if(itemTO.getIssueUomName()!=null){
						issueUomName = itemTO.getIssueUomName();
					}
					itemLabel = nameWithCode+ " " + ":"+ " " + availableStock + " " + issueUomName;
					itemTO.setItemLabel(itemLabel);
				}
				itemMap.put(itemTO.getId(), itemTO);
			}
		}
		transferForm.setItemList(itemList);
		transferForm.setItemMap(itemMap);
		log.info("Leaving into setAllItemsToForm StockTransferAction");	
	}
	
	/**
	 * Used to prepare the itemList (Along with quantity) based on the selection
	 */

	public ActionForward prepareItemListForTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into prepareItemListForTransfer StockTransferAction");	
		StockTransferForm transferForm = (StockTransferForm)form;
		 ActionErrors errors = transferForm.validate(mapping, request);
		try{
			setAllItemsToForm(transferForm);
			if(errors.isEmpty()){
				Map<String, ItemTO> itemMap = transferForm.getItemMap();
				//Used to not allow duplicate items to transfer
				Set<String> itemIdSet = transferForm.getItemIdSet();				
				if(itemIdSet==null || itemIdSet.isEmpty()){
					itemIdSet = new HashSet<String>();
				}				
				List<ItemTO> transferItemList = transferForm.getTransferItemList();
				if(transferItemList == null || transferItemList.isEmpty()){
					transferItemList = new ArrayList<ItemTO>();
				}					
				String itemId = transferForm.getItemId();
					if(!itemIdSet.contains(itemId)){
						ItemTO itemTO = new ItemTO();
						itemTO.setId(itemId);
						itemTO.setQuantityIssued(transferForm.getQuantityIssued());
						//Get the item name, code and UOM name from the item map
						if(itemMap != null && !itemMap.isEmpty() && itemMap.containsKey(itemId)){
							ItemTO oldItemTO = itemMap.get(itemId);	
							if(oldItemTO.getNameWithCode()!=null){
								itemTO.setNameWithCode(oldItemTO.getNameWithCode());
							}							
							if(oldItemTO.getIssueUomName()!=null){
								itemTO.setIssueUomName(oldItemTO.getIssueUomName());
							}
						}
						itemIdSet.add(itemId);
						transferItemList.add(itemTO);
						transferForm.setTransferItemList(transferItemList);
						transferForm.setItemIdSet(itemIdSet);
						transferForm.setQuantityIssued(null);
						transferForm.setItemId(null);
						transferForm.setSearchItem(null);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_DUPLICATE));
					}
			}
		}
		catch (Exception e) {
			log.error("Error occured in prepareItemListForTransfer of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			transferForm.setErrorMessage(msg);
			transferForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into prepareItemListForTransfer StockTransferAction");
		return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER_ITEM);
	}
	
	/**
	 * Used to delete an item for the transfer item list
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteItem StockTransferAction");
		StockTransferForm transferForm = (StockTransferForm)form;
		ActionMessages message = new ActionMessages();
		try{
			boolean isDeleted = false;
			String itemId= transferForm.getItemId();
				Iterator<ItemTO> iterator = transferForm.getTransferItemList().iterator();
				while (iterator.hasNext()) {
					ItemTO itemTO = (ItemTO) iterator.next();
					if(itemTO.getId().equals(itemId)){
						iterator.remove();
						//Also delete from the set which is used for duplicate check of item lists
						transferForm.getItemIdSet().remove(itemId);
						isDeleted = true;
					}
				}
				//If delete is success
				if(isDeleted){
					message.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STOCK_TRANSFER_ITEM_DELETE_SUCCESS));
					saveMessages(request, message);
				}
			setAllItemsToForm(transferForm);
		}
		catch (Exception e) {
			log.error("Error occured in deleteItem of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			transferForm.setErrorMessage(msg);
			transferForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into deleteItem StockTransferAction");
		return mapping.findForward(CMSConstants.INVENTORY_INIT_STOCK_TRANSFER_ITEM);
	}
	
	/**
	 * Used to validate the item List when submit button is clicked
	 * If no item selected. throw error message
	 */
	public ActionErrors validateItemList(StockTransferForm transferForm, ActionErrors errors)throws Exception{
		log.info("Entering into validateItemList StockTransferAction");
		if(transferForm.getTransferItemList()==null || transferForm.getTransferItemList().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_EMPTY));
		}		
		log.info("Leaving into validateItemList StockTransferAction");
		return errors;
	}
}
