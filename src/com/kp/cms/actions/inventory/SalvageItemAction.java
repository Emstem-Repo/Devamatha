package com.kp.cms.actions.inventory;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.SalvageItemForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.SalvageItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class SalvageItemAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(SalvageItemAction.class);
	
	/**
	 * This method is used to display required details in jsp.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initSalvageItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initSalvageItem in SalvageItemAction class..");	
		SalvageItemForm salvageItemForm = (SalvageItemForm)form;
		try {
			salvageItemForm.resetFields();
			salvageItemForm.setItemId(null);
			salvageItemForm.setItemMap(null);
			setInvLocationDetails(salvageItemForm);
			setItemsListtoForm(salvageItemForm);
		} catch (Exception e) {
			log.error("Error occured in initSalvageItem of SalvageItemAction", e);
			String msg = super.handleApplicationException(e);
			salvageItemForm.setErrorMessage(msg);
			salvageItemForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initSalvageItem in SalvageItemAction class..");	
		return mapping.findForward(CMSConstants.DISPLAY_SALVAGE);
	}

	
	/**
	 * This method is used to save salvage details to database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward saveSalvageItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into saveSalvageItem in SalvageItemAction class..");	
		SalvageItemForm salvageItemForm = (SalvageItemForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages message = new ActionMessages();
		validateSalvageInvLocItem(salvageItemForm, errors);
		validateSalvageItem(salvageItemForm, errors);
		if(errors.isEmpty()){
			try {
				setUserId(request, salvageItemForm);
				boolean isAdded = SalvageItemHandler.getInstance().saveSalvageItemDetails(salvageItemForm, errors);
				if(isAdded){
					message.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SALVAGE_SUCCESS));
					addMessages(request, message);
					salvageItemForm.resetFields();
					salvageItemForm.setItemMap(null);
					salvageItemForm.setItemId(null);
					
				}
				setInvLocationDetails(salvageItemForm);
			} catch (Exception e) {
				log.error("Error occured in saveSalvageItem of SalvageItemAction", e);
				String msg = super.handleApplicationException(e);
				salvageItemForm.setErrorMessage(msg);
				salvageItemForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.DISPLAY_SALVAGE);
		}
		setItemsListtoForm(salvageItemForm);
		setInvLocationDetails(salvageItemForm);
		addErrors(request, errors);
		log.info("Leaving into saveSalvageItem in SalvageItemAction class..");	
		return mapping.findForward(CMSConstants.DISPLAY_SALVAGE);
	}
	
//	/**
//	 * This method is used to add items to list and set to form.
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
	
//	public ActionForward addSalvageToList(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		log.info("entering into addSalvageToList in SalvageItemAction class..");	
//		SalvageItemForm salvageItemForm = (SalvageItemForm)form;
//		ActionErrors errors = new ActionErrors();
//		validateSalvageInvLocItem(salvageItemForm, errors);
//		errors = validateSalvageItem(salvageItemForm,errors);
//		try {
//			setItemsListtoForm(salvageItemForm);
//			if(errors.isEmpty()){
//				Map<String, InvSalvageTO> itemMap = salvageItemForm.getItemMap();
//				//Used to not allow duplicate items to transfer
//				Set<String> itemIdSet = salvageItemForm.getItemIdSet();				
//				if(itemIdSet==null || itemIdSet.isEmpty()){
//					itemIdSet = new HashSet<String>();
//				}				
//				List<InvSalvageTO> transferItemList = salvageItemForm.getItemtransferList();
//				if(transferItemList == null || transferItemList.isEmpty()){
//					transferItemList = new ArrayList<InvSalvageTO>();
//				}					
//				String itemId = salvageItemForm.getItemId();
////				if(itemId != null){
//				Map<Integer,String> locationMap = salvageItemForm.getLocationMap();
//					if(!itemIdSet.contains(itemId)){
//						InvSalvageTO salvageTO = new InvSalvageTO();
//						InvLocationTO locationTO = new InvLocationTO();
//						ItemTO itemTO = new ItemTO();
//						itemTO.setId(itemId);
//						itemTO.setQuantityIssued(salvageItemForm.getQuantity());
//						if(itemMap != null && !itemMap.isEmpty() && itemMap.containsKey(itemId)){
//							salvageTO = itemMap.get(itemId);
//							ItemTO oldItemTO = salvageTO.getItemTO();
//							if(oldItemTO.getNameWithCode()!=null){
//								itemTO.setNameWithCode(oldItemTO.getNameWithCode());
//							}
//							if(oldItemTO.getIssueUomId() != 0){
//								 itemTO.setIssueUomId(oldItemTO.getIssueUomId());
//							}
//							if(oldItemTO.getPurchaseUomId() != 0){
//								 itemTO.setPurchaseUomId(oldItemTO.getPurchaseUomId());
//							}
//						}
//						salvageTO.setItemTO(itemTO);
//						
//						if(locationMap != null){
//							int locId = Integer.parseInt(salvageItemForm.getInventoryLocation());
//							locationTO.setName(locationMap.get(locId));
//						}
//						salvageTO.setInvLocationTO(locationTO);
//						salvageTO.setSalvageDate(salvageItemForm.getSalvageDate());
//						itemIdSet.add(itemId);
//						transferItemList.add(salvageTO);
//						salvageItemForm.setItemtransferList(transferItemList);
//						salvageItemForm.setItemIdSet(itemIdSet);
//						salvageItemForm.setQuantity(null);
//						salvageItemForm.setItemId(null);
//						salvageItemForm.setSearchItem(null);
//						salvageItemForm.setInventoryLocation(null);
//						salvageItemForm.setSalvageDate(null);
//					}else{
//							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_DUPLICATE));
//					}
////				}
////				else{
////					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ITEM_REQUIRE ));
////				}
//			}
//		} catch (Exception e) {
//			log.error("Error occured in addSalvageToList of SalvageItemAction", e);
//			String msg = super.handleApplicationException(e);
//			salvageItemForm.setErrorMessage(msg);
//			salvageItemForm.setErrorStack(e.getMessage());
//			return mapping.findForward(CMSConstants.ERROR_PAGE);
//		}
//		addErrors(request, errors);
//		log.info("exit of addSalvageToList in SalvageItemAction class..");	
//		return mapping.findForward(CMSConstants.DISPLAY_SALVAGE);
//	}

	/**
	 * This method is used to validate item and quantity.
	 * @param salvageItemForm
	 * @param errors
	 * @return
	 */
	
	private ActionErrors validateSalvageItem(SalvageItemForm salvageItemForm,
			ActionErrors errors) {
		log.info("entering into validateSalvageItem in SalvageItemAction class..");
		if (errors == null)
			errors = new ActionErrors();
		if(StringUtils.isEmpty(salvageItemForm.getItemId()) && StringUtils.isEmpty(salvageItemForm.getSearchItem())){
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ITEM_REQUIRE));
		}
		if(StringUtils.isEmpty(salvageItemForm.getQuantity())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_REQUIRED));
		}
		if(!StringUtils.isNumeric(salvageItemForm.getQuantity())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_INTEGER));
		}
		if(salvageItemForm.getQuantity()!=null && !salvageItemForm.getQuantity().isEmpty() && StringUtils.isNumeric(salvageItemForm.getQuantity())){
			int q=Integer.parseInt(salvageItemForm.getQuantity());
			if(q==0){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.quantity.greaterthan.zero"));
			}
		}
		log.info("exit of validateSalvageItem in SalvageItemAction class..");	
		return errors;
	}
	
	/**
	 * This method is used to validate inventory location and date.
	 * @param salvageItemForm
	 * @param errors
	 */
	
	private void validateSalvageInvLocItem(SalvageItemForm salvageItemForm,
			ActionErrors errors) {
		log.info("entering into validateSalvageInvLocItem in SalvageItemAction class..");
		if (errors == null)
			errors = new ActionErrors();
		if(StringUtils.isEmpty(salvageItemForm.getInventoryLocation())){
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.INVENTORY_LOCATION_REQUIRED));
		}
		if(StringUtils.isEmpty(salvageItemForm.getSalvageDate())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DATE_REQUIRE));
		}
		if(!StringUtils.isEmpty(salvageItemForm.getSalvageDate()) && !CommonUtil.isValidDate(salvageItemForm.getSalvageDate())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DATE_INVALID));
		}
		if(StringUtils.isEmpty(salvageItemForm.getRemarks())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.REMARKS_REQUIRE));
		}
		log.info("exit of validateSalvageInvLocItem in SalvageItemAction class..");	
	}
	
//	/**
//	 * This method is used to delete the item from the list.
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	
//	public ActionForward deleteSalvageItemFromList(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		log.info("entering into deleteSalvageItemFromList in SalvageItemAction class..");	
//		SalvageItemForm salvageItemForm = (SalvageItemForm)form;
//		try {
//			ActionMessages message = new ActionMessages();
//			boolean isDeleted = false;
//			String itemId= salvageItemForm.getItemId();
//				Iterator<InvSalvageTO> iterator = salvageItemForm.getItemtransferList().iterator();
//				while (iterator.hasNext()) {
//					InvSalvageTO salvageTO = (InvSalvageTO) iterator.next();
//					if(salvageTO.getItemTO().getId().equals(itemId)){
//						iterator.remove();
//						salvageItemForm.getItemIdSet().remove(itemId);
//						isDeleted = true;
//					}
//				}
//				if(isDeleted){
//					message.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STOCK_TRANSFER_ITEM_DELETE_SUCCESS));
//					saveMessages(request, message);
//					salvageItemForm.resetFields();
//				}
//		} catch (Exception e) {
//			log.error("Error occured in deleteSalvageItemFromList in SalvageItemAction class", e);
//			String msg = super.handleApplicationException(e);
//			salvageItemForm.setErrorMessage(msg);
//			salvageItemForm.setErrorStack(e.getMessage());
//			return mapping.findForward(CMSConstants.ERROR_PAGE);
//		}
//		setItemsListtoForm(salvageItemForm);
//		log.info("exit of deleteSalvageItemFromList in SalvageItemAction class..");	
//		return mapping.findForward(CMSConstants.DISPLAY_SALVAGE);
//	}
	
	/**
	 * This method is used to save items to list.
	 * @param salvageItemForm
	 * @throws Exception
	 */
	
	public void setItemsListtoForm(SalvageItemForm salvageItemForm) throws Exception{
		log.info("entering into setItemsListtoForm in SalvageItemAction class..");
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
		salvageItemForm.setItemList(itemList);
		Map<String, InvSalvageTO> itemMap = new HashMap<String, InvSalvageTO>();
		InvSalvageTO salvageTO;
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> itemIterator = itemList.iterator();
			while (itemIterator.hasNext()) {
				salvageTO = new InvSalvageTO();
				ItemTO itemTO = itemIterator.next();
				salvageTO.setItemTO(itemTO);
				itemMap.put(itemTO.getId(), salvageTO);
			}
		}
		salvageItemForm.setItemMap(itemMap);
		log.info("exit of setItemsListtoForm in SalvageItemAction class..");
	}
	
	/**
	 * This method is used to load inventory location details.
	 * @param salvageItemForm
	 * @throws Exception
	 */
		
	public void setInvLocationDetails(SalvageItemForm salvageItemForm)throws Exception{
		log.info("entering into setInvLocationDetails in SalvageItemAction class..");
		List<SingleFieldMasterTO> invLocDetails = StockTransferHandler.getInstance().getAllInventoryLocation();
		salvageItemForm.setInventoryList(invLocDetails);
		
		log.info("exit of setInvLocationDetails in SalvageItemAction class..");
	}
	
	/**
	 * Used to validate the transfer date
	 * Validates the date format and as well as for future date
	 */
	
	public ActionErrors validateSalvageDate(ActionErrors errors, String salvageDate)throws Exception{
		log.info("entering in validateSalvageDate in SalvageItemAction class..");
		boolean isValidDateFormat;
		isValidDateFormat = CommonUtil.isValidDate(salvageDate);
		if(!isValidDateFormat){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));		
		}
		else if(isValidDateFormat){
			String formattedString=CommonUtil.ConvertStringToDateFormat(salvageDate, CMSConstants.DEST_DATE,CMSConstants.DEST_DATE_MM_DD_YYYY);
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
		log.info("Leaving into validateSalvageDate SalvageItemAction class..");
		return errors;
	}
	
	/**
	 * Used to validate the item List when submit button is clicked
	 * If no item selected. throw error message
	 */
	public ActionErrors validateItemList(SalvageItemForm salvageItemForm, ActionErrors errors)throws Exception{
		log.info("Entering into validateItemList StockTransferAction");
		if(salvageItemForm.getItemtransferList()==null || salvageItemForm.getItemtransferList().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_EMPTY));
		}		
		log.info("Leaving into validateItemList StockTransferAction");
		return errors;
	}
}