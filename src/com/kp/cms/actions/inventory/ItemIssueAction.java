package com.kp.cms.actions.inventory;

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
import com.kp.cms.forms.inventory.ItemIssueForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.SalvageItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ItemIssueAction extends BaseDispatchAction{
	

		private static final Log log = LogFactory.getLog(ItemIssueAction.class);
		
		/**
		 * This method is used to display required details in jsp.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		
		public ActionForward initItemIssue(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			log.info("entering into initItemIssue in ItemIssueAction class..");	
			ItemIssueForm itemIssueForm = (ItemIssueForm)form;
			try {
				itemIssueForm.resetFields();
				itemIssueForm.setItemId(null);
				itemIssueForm.setItemMap(null);
				setInvLocationDetails(itemIssueForm);
				setItemsListtoForm(itemIssueForm);
			} catch (Exception e) {
				log.error("Error occured in initItemIssue of ItemIssueAction", e);
				String msg = super.handleApplicationException(e);
				itemIssueForm.setErrorMessage(msg);
				itemIssueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Leaving into initItemIssue in ItemIssueAction class..");	
			return mapping.findForward(CMSConstants.DISPLAY_ITEM_ISSUE);
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
		
		public ActionForward saveItemIssue(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			log.info("entering into saveItemIssue in ItemIssueAction class..");	
			ItemIssueForm itemIssueForm = (ItemIssueForm)form;
			ActionErrors errors = new ActionErrors();
			ActionMessages message = new ActionMessages();
			validateItemIssueInvLocItem(itemIssueForm, errors);
			validateItemIssue(itemIssueForm, errors);
			if(errors.isEmpty()){
				try {
					setUserId(request, itemIssueForm);
					boolean isAdded = SalvageItemHandler.getInstance().saveItemIssueDetails(itemIssueForm, errors);
					if(isAdded){
						message.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ITEM_ISSUE_SUCCESS));
						addMessages(request, message);
						itemIssueForm.resetFields();
						itemIssueForm.setItemMap(null);
						itemIssueForm.setItemId(null);
					}
					setInvLocationDetails(itemIssueForm);
				} catch (Exception e) {
					log.error("Error occured in saveItemIssue of ItemIssueAction", e);
					String msg = super.handleApplicationException(e);
					itemIssueForm.setErrorMessage(msg);
					itemIssueForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.DISPLAY_ITEM_ISSUE);
			}
			setItemsListtoForm(itemIssueForm);
			setInvLocationDetails(itemIssueForm);
			addErrors(request, errors);
			log.info("Leaving into saveItemIssue in ItemIssueAction class..");	
			return mapping.findForward(CMSConstants.DISPLAY_ITEM_ISSUE);
		}
		
		
//		/**
//		 * This method is used to add items to list and set to form.
//		 * @param mapping
//		 * @param form
//		 * @param request
//		 * @param response
//		 * @return
//		 * @throws Exception
//		 */
//		
//		public ActionForward addItemIssueToList(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response)
//				throws Exception {
//			log.info("entering into addItemIssueToList in ItemIssueAction class..");	
//			ItemIssueForm itemIssueForm = (ItemIssueForm)form;
//			ActionErrors errors = new ActionErrors();
//			validateItemIssueInvLocItem(itemIssueForm, errors);
//			errors = validateItemIssue(itemIssueForm,errors);
//			try {
//				setItemsListtoForm(itemIssueForm);
//				if(errors.isEmpty()){
//					Map<String, InvSalvageTO> itemMap = itemIssueForm.getItemMap();
//					//Used to not allow duplicate items to transfer
//					Set<String> itemIdSet = itemIssueForm.getItemIdSet();				
//					if(itemIdSet==null || itemIdSet.isEmpty()){
//						itemIdSet = new HashSet<String>();
//					}				
//					List<InvSalvageTO> transferItemList = itemIssueForm.getItemtransferList();
//					if(transferItemList == null || transferItemList.isEmpty()){
//						transferItemList = new ArrayList<InvSalvageTO>();
//					}					
//					String itemId = itemIssueForm.getItemId();
////					if(itemId != null){
//						Map<Integer,String> locationMap = itemIssueForm.getLocationMap();
//						if(!itemIdSet.contains(itemId)){
//							InvSalvageTO salvageTO = new InvSalvageTO();
//							InvLocationTO locationTO = new InvLocationTO();
//							ItemTO itemTO = new ItemTO();
//							itemTO.setId(itemId);
//							itemTO.setQuantityIssued(itemIssueForm.getQuantity());
//							if(itemMap != null && !itemMap.isEmpty() && itemMap.containsKey(itemId)){
//								salvageTO = itemMap.get(itemId);
//								ItemTO oldItemTO = salvageTO.getItemTO();
//								if(oldItemTO.getNameWithCode()!=null){
//									itemTO.setNameWithCode(oldItemTO.getNameWithCode());
//								}
//								if(oldItemTO.getIssueUomId() != 0){
//									 itemTO.setIssueUomId(oldItemTO.getIssueUomId());
//								}
//								if(oldItemTO.getPurchaseUomId() != 0){
//									 itemTO.setPurchaseUomId(oldItemTO.getPurchaseUomId());
//								}
//							}
//							salvageTO.setItemTO(itemTO);
//							
//							if(locationMap != null){
//								int locId = Integer.parseInt(itemIssueForm.getInventoryLocation());
//								locationTO.setName(locationMap.get(locId));
//							}
//							salvageTO.setInvLocationTO(locationTO);
//							salvageTO.setSalvageDate(itemIssueForm.getIssueDate());
//							salvageTO.setIssuedTo(itemIssueForm.getIssuedTO());
//							itemIdSet.add(itemId);
//							transferItemList.add(salvageTO);
//							itemIssueForm.setItemtransferList(transferItemList);
//							itemIssueForm.setItemIdSet(itemIdSet);
//							itemIssueForm.setQuantity(null);
//							itemIssueForm.setItemId(null);
//							itemIssueForm.setSearchItem(null);
//							
//						}else{
//								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_DUPLICATE));
//						}
////					}
////					else{
////						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ITEM_REQUIRE ));
////					}
//				}
//			} catch (Exception e) {
//				log.error("Error occured in addItemIssueToList of ItemIssueAction", e);
//				String msg = super.handleApplicationException(e);
//				itemIssueForm.setErrorMessage(msg);
//				itemIssueForm.setErrorStack(e.getMessage());
//				return mapping.findForward(CMSConstants.ERROR_PAGE);
//			}
//			addErrors(request, errors);
//			log.info("exit of addSalvage in ItemIssueAction class..");	
//			return mapping.findForward(CMSConstants.DISPLAY_ITEM_ISSUE);
//		}

		/**
		 * This method is used to validate item and quantity.
		 * @param salvageItemForm
		 * @param errors
		 * @return
		 */
		
		private ActionErrors validateItemIssue(ItemIssueForm itemIssueForm,
				ActionErrors errors) {
			log.info("entering into validateItemIssue in ItemIssueAction class..");
			if (errors == null)
				errors = new ActionErrors();
			if(StringUtils.isEmpty(itemIssueForm.getItemId()) && StringUtils.isEmpty(itemIssueForm.getSearchItem())){
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ITEM_REQUIRE));
			}
			if(StringUtils.isEmpty(itemIssueForm.getQuantity())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_REQUIRED));
			}
			if(!StringUtils.isNumeric(itemIssueForm.getQuantity())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_INTEGER));
			}
			log.info("exit of validateItemIssue in ItemIssueAction class..");	
			return errors;
		}
		
		/**
		 * This method is used to validate inventory location and date.
		 * @param salvageItemForm
		 * @param errors
		 */
		
		private void validateItemIssueInvLocItem(ItemIssueForm itemIssueForm,
				ActionErrors errors) {
			log.info("entering into validateItemIssueInvLocItem in ItemIssueAction class..");
			if (errors == null)
				errors = new ActionErrors();
			if(StringUtils.isEmpty(itemIssueForm.getInventoryLocation())){
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.INVENTORY_LOCATION_REQUIRED));
			}
			if(StringUtils.isEmpty(itemIssueForm.getIssueDate())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DATE_REQUIRE));
			}
			if(!StringUtils.isEmpty(itemIssueForm.getIssueDate()) && !CommonUtil.isValidDate(itemIssueForm.getIssueDate())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DATE_INVALID));
			}
			if(StringUtils.isEmpty(itemIssueForm.getIssuedTO())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ISSUED_TO_REQUIRE));
				
			}
			log.info("exit of validateItemIssueInvLocItem in ItemIssueAction class..");	
		}
		
//		/**
//		 * This method is used to delete the item from the list.
//		 * @param mapping
//		 * @param form
//		 * @param request
//		 * @param response
//		 * @return
//		 * @throws Exception
//		 */
//		
//		public ActionForward deleteItemIssueFromList(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response)
//				throws Exception {
//			log.info("entering into deleteItemIssueFromList in ItemIssueAction class..");	
//			ItemIssueForm itemIssueForm = (ItemIssueForm)form;
//			try {
//				ActionMessages message = new ActionMessages();
//				boolean isDeleted = false;
//				String itemId= itemIssueForm.getItemId();
//				Iterator<InvSalvageTO> iterator = itemIssueForm.getItemtransferList().iterator();
//				while (iterator.hasNext()) {
//					InvSalvageTO salvageTO = (InvSalvageTO) iterator.next();
//					if(salvageTO.getItemTO().getId().equals(itemId)){
//							iterator.remove();
//							itemIssueForm.getItemIdSet().remove(itemId);
//							isDeleted = true;
//						}
//					}
//					if(isDeleted){
//						message.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STOCK_TRANSFER_ITEM_DELETE_SUCCESS));
//						saveMessages(request, message);
//					}
//			} catch (Exception e) {
//				log.error("Error occured in deleteItemIssueFromList in ItemIssueAction class", e);
//				String msg = super.handleApplicationException(e);
//				itemIssueForm.setErrorMessage(msg);
//				itemIssueForm.setErrorStack(e.getMessage());
//				return mapping.findForward(CMSConstants.ERROR_PAGE);
//			}
//			setItemsListtoForm(itemIssueForm);
//			log.info("exit of deleteItemIssueFromList in ItemIssueAction class..");	
//			return mapping.findForward(CMSConstants.DISPLAY_ITEM_ISSUE);
//		}
		
		/**
		 * This method is used to save items to list.
		 * @param salvageItemForm
		 * @throws Exception
		 */
		
		public void setItemsListtoForm(ItemIssueForm itemIssueForm) throws Exception{
			log.info("entering into setItemsListtoForm in ItemIssueAction class..");
			List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
			itemIssueForm.setItemList(itemList);
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
			itemIssueForm.setItemMap(itemMap);
			log.info("exit of setItemsListtoForm in ItemIssueAction class..");
		}

		/**
		 * This method is used to load inventory location details.
		 * @param salvageItemForm
		 * @throws Exception
		 */
			
		public void setInvLocationDetails(ItemIssueForm itemIssueForm)throws Exception{
			log.info("entering into setInvLocationDetails in ItemIssueAction class..");
			List<SingleFieldMasterTO> invLocDetails = StockTransferHandler.getInstance().getAllInventoryLocation();
			itemIssueForm.setInventoryList(invLocDetails);
		
			log.info("exit of setInvLocationDetails in ItemIssueAction class..");
		}
		
		/**
		 * Used to validate the item List when submit button is clicked
		 * If no item selected. throw error message
		 */
		public ActionErrors validateItemList(ItemIssueForm itemIssueForm, ActionErrors errors)throws Exception{
			log.info("Entering into validateItemList ItemIssueAction class..");
			if(itemIssueForm.getItemtransferList()==null || itemIssueForm.getItemtransferList().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_EMPTY));
			}		
			log.info("Leaving into validateItemList ItemIssueAction class..");
			return errors;
		}

}