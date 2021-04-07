package com.kp.cms.actions.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.CashPurchaseForm;
import com.kp.cms.handlers.inventory.CashPurchaseHandler;
import com.kp.cms.handlers.inventory.InventoryRequestHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CashPurchaseAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(CashPurchaseAction.class);

	/**
	 * Method to set the required data to the form to display in cashPurchase.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCashPurchase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initCashPurchase CashPurchaseAction");
		CashPurchaseForm cashPurchaseForm = (CashPurchaseForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			cashPurchaseForm.clear();
			cashPurchaseForm.setTransferItemList(null);
			getInventoryLocationList(cashPurchaseForm);
			setUserId(request, cashPurchaseForm);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			cashPurchaseForm.setErrorMessage(msg);
			cashPurchaseForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Exit initCashPurchase CashPurchaseAction");		
		return mapping.findForward(CMSConstants.CASH_PURCHASE);
	}
	
	/**
	 * Method to set all active Inventory Locations to the form
	 * @param inventoryRequestForm
	 * @throws Exception
	 */
	public void getInventoryLocationList(CashPurchaseForm cashPurchaseForm) throws Exception{
		List<SingleFieldMasterTO> inventoryLocationList = InventoryRequestHandler.getInstance().getInventoryLocation();
		if( inventoryLocationList != null ){
			cashPurchaseForm.setInventoryLocationList(inventoryLocationList);
		}
		List<VendorTO> vendorList = VendorHandler.getInstance()
		.getVendorDetails();
		cashPurchaseForm.setVendorList(vendorList);
	}
	
	/**
	 * Method to add the cash Purchase
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCashPurchase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered addCashPurchase CashPurchaseAction");
		CashPurchaseForm cashPurchaseForm = (CashPurchaseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Boolean isValidDate = true;
		try{
			if(cashPurchaseForm.getDate()!= null && !cashPurchaseForm.getDate().trim().isEmpty()){
				isValidDate = CommonUtil.isValidDate(cashPurchaseForm.getDate());
			}
			//date validation
			if(!isValidDate){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CASH_PURCHASE);		
			}	
			if(cashPurchaseForm.getTransferItemList() == null || cashPurchaseForm.getTransferItemList().isEmpty()){
				errors = cashPurchaseForm.validate(mapping, request);
			}
			if(errors.isEmpty()){
				if(cashPurchaseForm.getQuantity()!= null && !cashPurchaseForm.getQuantity().isEmpty() && Integer.parseInt(cashPurchaseForm.getQuantity()) <= 0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_CASHPURCHASE_QUANTITY));
				}
			}
			if(!errors.isEmpty()){
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.CASH_PURCHASE);
			}
			boolean itemAdded = false;
			itemAdded = CashPurchaseHandler.getInstance().getItemAdded(cashPurchaseForm);
			
			if (itemAdded) {
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_CASHPURCHASE_ADDSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				cashPurchaseForm.clear();
				cashPurchaseForm.setTransferItemList(null);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDFAILURE));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error occured in addCashPurchase of CashPurchaseAction", e);
			String msg = super.handleApplicationException(e);
			cashPurchaseForm.setErrorMessage(msg);
			cashPurchaseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("Exit addCashPurchase CashPurchaseAction");		
		return mapping.findForward(CMSConstants.CASH_PURCHASE);
	}
	
	/**
	 * Used to prepare the itemList (Along with quantity) based on the selection
	 */
	public ActionForward prepareItemListForTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into prepareItemListForTransfer CashPurchaseAction");	
		CashPurchaseForm cashPurchaseForm = (CashPurchaseForm) form;
		 ActionErrors errors = cashPurchaseForm.validate(mapping, request);
		
		try{
			if(errors.isEmpty()){
				if(cashPurchaseForm.getQuantity()!= null  && !cashPurchaseForm.getQuantity().isEmpty() && Integer.parseInt(cashPurchaseForm.getQuantity()) <= 0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_CASHPURCHASE_QUANTITY));
				}
			}	
			if(errors.isEmpty()){
				//Used to not allow duplicate items to transfer
				List<ItemTO> transferItemList = cashPurchaseForm.getTransferItemList();
				if(transferItemList == null || transferItemList.isEmpty()){
					transferItemList = new ArrayList<ItemTO>();
				}
				if (!transferItemList.contains(cashPurchaseForm.getItemName())) {
					ItemTO itemTO = new ItemTO();
					itemTO.setName(cashPurchaseForm.getItemName().trim());
					itemTO.setQuantityIssued(cashPurchaseForm.getQuantity().trim());
					itemTO.setPurchaseCost(new BigDecimal(cashPurchaseForm.getPurchasePrice().trim()));
					String name=cashPurchaseForm.getName();
					if(name!=null && !name.isEmpty() && !name.equalsIgnoreCase("other")){
						itemTO.setVendorName(name);
					}else{
						itemTO.setVendorName(cashPurchaseForm.getVendorName());
					}
					itemTO.setVendorId(cashPurchaseForm.getVendorId());
					itemTO.setInvLocationId(cashPurchaseForm.getInvLocationId());
					itemTO.setPurchaseDate(cashPurchaseForm.getDate());
					if(cashPurchaseForm.getComments()!=null && !cashPurchaseForm.getComments().isEmpty()){
						itemTO.setDescription(cashPurchaseForm.getComments().trim());
					}
					transferItemList.add(itemTO);
					cashPurchaseForm.setTransferItemList(transferItemList);
					cashPurchaseForm.setItemName(null);
					cashPurchaseForm.setQuantity(null);
					cashPurchaseForm.setPurchasePrice(null);
					cashPurchaseForm.setVendorName(null);
					cashPurchaseForm.setComments(null);
					cashPurchaseForm.setDate(null);
					cashPurchaseForm.setInvLocationId(null);
					cashPurchaseForm.setVendorId(null);
				} else {
					errors.add(	CMSConstants.ERROR,new ActionError(CMSConstants.STOCK_TRANSFER_ITEM_DUPLICATE));
				}
			}else{
				addErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error occured in prepareItemListForTransfer of CashPurchaseAction", e);
			String msg = super.handleApplicationException(e);
			cashPurchaseForm.setErrorMessage(msg);
			cashPurchaseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into prepareItemListForTransfer CashPurchaseAction");
		return mapping.findForward(CMSConstants.CASH_PURCHASE);
	}
	
	/**
	 * Used to delete an item for the transfer item list
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteItem CashPurchaseAction");
		CashPurchaseForm cashPurchaseForm = (CashPurchaseForm) form;
		ActionMessages message = new ActionMessages();
		try{
			boolean isDeleted = false;
			String itemName= cashPurchaseForm.getItemName();
				Iterator<ItemTO> iterator = cashPurchaseForm.getTransferItemList().iterator();
				while (iterator.hasNext()) {
					ItemTO itemTO = (ItemTO) iterator.next();
					if(itemTO.getName().equals(itemName)){
						iterator.remove();
						isDeleted = true;
					}
				}
				if(isDeleted){
					cashPurchaseForm.clear();
					message.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STOCK_TRANSFER_ITEM_DELETE_SUCCESS));
					saveMessages(request, message);
				}
		}
		catch (Exception e) {
			log.error("Error occured in deleteItem of CashPurchaseAction", e);
			String msg = super.handleApplicationException(e);
			cashPurchaseForm.setErrorMessage(msg);
			cashPurchaseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving deleteItem CashPurchaseAction");
		return mapping.findForward(CMSConstants.CASH_PURCHASE);
	}
}