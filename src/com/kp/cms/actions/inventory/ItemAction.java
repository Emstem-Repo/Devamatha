package com.kp.cms.actions.inventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.ajax.CommonAjaxAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.inventory.ItemForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.ItemTO;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

@SuppressWarnings("deprecation")
public class ItemAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(ItemAction.class);

	/**
	 * Method to set the required data to the form to display in itemMaster.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getItemMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered getItemMaster Action");
		ItemForm itemForm = (ItemForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			itemForm.clear();
			List<SingleFieldMasterTO> itemTypeList=ItemHandler.getInstance().getItemType();
			if(itemTypeList!=null)
				itemForm.setItemTypeList(itemTypeList);
			getItemCategoryList(itemForm);
			getUOMList(itemForm);
			getItemList(itemForm);
			setUserId(request, itemForm);
			if(request.getParameter("mainPageExists")==null){
				itemForm.setMainPage(null);
				itemForm.setSuperMainPage(null);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			itemForm.setErrorMessage(msg);
			itemForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Exit getItemMaster Action");		
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
	/**
	 * Method to set all active Item Categories to the form
	 * @param itemMasterForm
	 * @throws Exception
	 */
	public void getItemCategoryList(ItemForm itemForm) throws Exception{
		
		List<SingleFieldMasterTO> itemCategoryList = ItemHandler.getInstance().getItemCategory();
		if( itemCategoryList != null ){
			itemForm.setItemCategoryList(itemCategoryList);
		}
		itemForm.setItemSubCategoryMap(new HashMap<Integer, String>());
	}
	
	/**
	 * Method to set all active UOMs to the form
	 * @param itemMasterForm
	 * @throws Exception
	 */
	public void getUOMList(ItemForm itemForm) throws Exception{
		List<SingleFieldMasterTO> UOMList = ItemHandler.getInstance().getUOM();
		
		if( UOMList != null ){
			itemForm.setUOMList(UOMList);
		}
	}
	
	/**
	 * Method to set all active Items to the form
	 * @param itemMasterForm
	 * @throws Exception
	 */
	public void getItemList(ItemForm itemForm) throws Exception{
		List<ItemTO> itemMasterList = ItemHandler.getInstance().getItemList(0);
		
		if( itemMasterList != null ){
			itemForm.setItemList(itemMasterList);
		}
	}
	
	/**
	 * Method to add Item
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered addItem Action");
		ItemForm itemForm = (ItemForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = itemForm.validate(mapping, request);
		validateForm(errors,itemForm);
		boolean isItemAdded ;
			
		try{
			if (!errors.isEmpty()) {
				log.info("Entered addItem Action");
				saveErrors(request, errors);
				getItemList(itemForm);
				return mapping.findForward(CMSConstants.INVENTORY_ITEM);
			}
			if (itemForm.getItemDescription() != null && itemForm.getItemDescription().length() > 200) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_DESCRIPTION1_CONTENTFAIL));
				saveErrors(request, errors);
				getItemList(itemForm);
				return mapping.findForward(CMSConstants.INVENTORY_ITEM);
			}
			isItemAdded = ItemHandler.getInstance().addItem(itemForm, "Add");

			//setUserId(request, interviewDefinitionForm);
			
			if (isItemAdded) {
				if(itemForm.getSuperMainPage()!=null && !itemForm.getSuperMainPage().isEmpty()){
					session.setAttribute("newEntryName",itemForm.getItemCode().trim());
				}
				getItemList(itemForm);
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ITEM_ADDSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				itemForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_ADDFAILURE));
				saveErrors(request, errors);
			}
		} catch (DuplicateException duplicateException) {
			log.info("Entered addItem Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_ADDEXIST));
			saveErrors(request, errors);
			getItemList(itemForm);
			return mapping.findForward(CMSConstants.INVENTORY_ITEM);
		} catch (ReActivateException reActivateException) {
			log.info("Entered addItem Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_REACTIVATE));
			saveErrors(request, errors);
			getItemList(itemForm);
			return mapping.findForward(CMSConstants.INVENTORY_ITEM);
		} catch (BusinessException businessException) {
			log.info("Exception addItem");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			itemForm.setErrorMessage(msg);
			itemForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
	private void validateForm(ActionErrors errors, ItemForm itemForm) {
		if(itemForm.getItemTypeId().equals("1")){
			if(itemForm.getItemCategoryId()==null || itemForm.getItemCategoryId().trim().isEmpty() || itemForm.getItemCategoryId().equalsIgnoreCase("AddNewCategory"))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.category.required"));
			if(itemForm.getItemSubCategoryId()==null || itemForm.getItemSubCategoryId().trim().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.sub.category.required"));
		}
	}

	/**
	 * Method to update Item
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered updateItem Action");
		ItemForm itemForm = (ItemForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = itemForm.validate(mapping, request);
		validateForm(errors,itemForm);
		boolean isItemEdited;

		try {
			if (isCancelled(request)) {
				setRequiredDataToForm(itemForm, request);
				getItemList(itemForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INVENTORY_ITEM);
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				getItemList(itemForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INVENTORY_ITEM);
			}
			if (itemForm.getItemDescription() != null && itemForm.getItemDescription().length() > 200) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_DESCRIPTION1_CONTENTFAIL));
				saveErrors(request, errors);
				getItemList(itemForm);
				return mapping.findForward(CMSConstants.INVENTORY_ITEM);
			}
			isItemEdited = ItemHandler.getInstance().addItem(itemForm, "Edit");
			
			if (isItemEdited) {
				HttpSession session=request.getSession();
				if(itemForm.getSuperMainPage()!=null && !itemForm.getSuperMainPage().isEmpty()){
					session.setAttribute("newEntryName",itemForm.getItemCode().trim());
				}
				getItemList(itemForm);
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ITEM_EDITSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				itemForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_EDITFAILURE));
				saveErrors(request, errors);
			}
		}catch (DuplicateException duplicateException) {
			log.info("Entered updateItem Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_ADDEXIST));
			saveErrors(request, errors);
			getItemList(itemForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INVENTORY_ITEM);
		} catch (ReActivateException reActivateException) {
			log.info("Entered updateItem Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_REACTIVATE));
			saveErrors(request, errors);
			getItemList(itemForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INVENTORY_ITEM);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			itemForm.setErrorMessage(msg);
			itemForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit editInterviewDefinition Action");
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
	/**
	 * Method to set data to the form when the edit button is clicked
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered editItem");
		ItemForm itemForm = (ItemForm) form;
		
		setRequiredDataToForm(itemForm, request);
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
		log.info("Exit editItem");
		
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
	/**
	 * Method to change the selected item to inactive state
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered deleteItem Action");
		ItemForm itemForm = (ItemForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted;
		try {
			if (itemForm.getId() != 0) {
				int itemId = itemForm.getId();
				String uid = itemForm.getUserId();
				 
				isDeleted = ItemHandler.getInstance().deleteItem(itemId, false, uid);
			
				if (isDeleted) {
					getItemList(itemForm);
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ITEM_DELETESUCCESS, itemForm.getId());
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					itemForm.clear();
				}
			}else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_DELETEFAILURE, itemForm.getId()));
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			log.info("Exception deleteItem");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			log.info("Exception deleteItem");
			String msg = super.handleApplicationException(exception);
			itemForm.setErrorMessage(msg);
			itemForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
	/**
	 * Method to populate the selected information to the form during edit
	 * @param interviewDefinitionForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(ItemForm itemForm,
			HttpServletRequest request) throws Exception {
		int itemId = Integer.parseInt(request.getParameter("id"));
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(itemId);
		Iterator<ItemTO> iterator = itemList.iterator();
		ItemTO itemTO;

		while (iterator.hasNext()) {
			itemTO = (ItemTO) iterator.next();
			
			itemForm.setPurchaseUomId(Integer.toString(itemTO.getPurchaseUomId()));
			itemForm.setIssueUomId(Integer.toString(itemTO.getIssueUomId()));
			itemForm.setItemCategoryId(Integer.toString(itemTO.getItemCategoryId()));
			itemForm.setItemCode(itemTO.getCode());
			itemForm.setItemName(itemTO.getName());
			if(itemTO.getDescription()!=null){
				itemForm.setItemDescription(itemTO.getDescription());
			}
			/*if(itemTO.getPurchaseCost()!=null){
				itemForm.setPurchaseCost(itemTO.getPurchaseCost().toString());
			}*/
			itemForm.setWarranty(itemTO.getIsWarranty());
			if(itemTO.getConversion()!=null){
				itemForm.setConversion(itemTO.getConversion().toString());
			}
				itemForm.setItemTypeId(Integer.toString(itemTO.getItemTypeId()));
			if(itemTO.getItemSubCategoryId()>0)
				itemForm.setItemSubCategoryId(Integer.toString(itemTO.getItemSubCategoryId()));
			if(itemTO.getRemarks()!=null){
				itemForm.setRemarks(itemTO.getRemarks());
			}
			itemForm.setMinStockQuantity(itemTO.getMinStockQuantity());
			itemForm.setPurchaseCost(itemTO.getPurchaseCost().toPlainString());
			//setting to check the duplication
			itemForm.setOriginalItemCode(itemTO.getCode());
			if(itemTO.getItemCategoryId()>0 )
			itemForm.setItemSubCategoryMap(CommonAjaxHandler.getInstance().getItemSubCategory(Integer.toString(itemTO.getItemCategoryId())));
		}
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
	}
	
	/**
	 * Method to reactivate selected item
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entered activateItem");
		ItemForm itemForm = (ItemForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated;
		try {
			if (itemForm.getActivationRefId() != 0) {
				int itemId = itemForm.getActivationRefId();
				String uid = itemForm.getUserId();
				
				isActivated = ItemHandler.getInstance().deleteItem(itemId, true, uid);
				getItemList(itemForm);
				if (isActivated) {
					HttpSession session=request.getSession();
					if(itemForm.getSuperMainPage()!=null && !itemForm.getSuperMainPage().isEmpty()){
						String reactivatedCode=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(itemId, "InvItem", true, "code");
						session.setAttribute("newEntryName",reactivatedCode);
					}
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ITEM_ACTIVATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					itemForm.clear();
				}
			}
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ITEM_ADDFAILURE));
			saveErrors(request, errors);
		}
		log.debug("Exit activateItem");
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	/**
	 * Method to set the required data to the form to display in itemMaster.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToMainPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered goTOMainPage Action");
		ItemForm itemForm=(ItemForm)form;
		HttpSession session=request.getSession(false);
		if(itemForm.getAddNewType()!=null && !itemForm.getAddNewType().isEmpty() && itemForm.getAddNewType().equalsIgnoreCase("SingleFieldMasterEntry")){
			getItemCategoryList(itemForm);
			if(session.getAttribute("newEntryName")!=null){
			itemForm.setItemCategoryId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString(),"InvItemCategory"));
			if(itemForm.getItemCategoryId()!=null && !itemForm.getItemCategoryId().isEmpty())
			itemForm.setItemSubCategoryMap(CommonAjaxHandler.getInstance().getItemSubCategory(itemForm.getItemCategoryId()));
			}
			else{
				itemForm.setItemCategoryId(null);
			}
		}
		else if(itemForm.getAddNewType()!=null && !itemForm.getAddNewType().isEmpty() && itemForm.getAddNewType().equalsIgnoreCase("ItemSubCategory")){
			if(session.getAttribute("itemCategoryId")!=null){
				itemForm.setItemCategoryId(session.getAttribute("itemCategoryId").toString());
				itemForm.setItemSubCategoryMap(CommonAjaxHandler.getInstance().getItemSubCategory(session.getAttribute("itemCategoryId").toString()));
			}
			if(session.getAttribute("newEntryName")!=null && session.getAttribute("itemCategoryId")!=null){
				itemForm.setItemSubCategoryId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString()+"_"+session.getAttribute("itemCategoryId").toString(),"InvSubCategoryBo"));
			}
			else{
				itemForm.setItemSubCategoryId(null);
			}
		}
		itemForm.setMainPage(null);
		session.removeAttribute("newEntryName");
		itemForm.setAddNewType(null);
		itemForm.setDestinationMethod(null);
		log.info("Exit goTOMainPage Action");		
		return mapping.findForward(CMSConstants.INVENTORY_ITEM);
	}
	
}