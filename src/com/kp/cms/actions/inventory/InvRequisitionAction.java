package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.InvRequisitionForm;
import com.kp.cms.handlers.inventory.InvRequisitionHandler;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvRequestTO;
import com.kp.cms.to.inventory.ItemTO;

public class InvRequisitionAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(InvRequisitionAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInvRequisition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	log.info("entered initInvRequisition..");
	InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form; 
	setAllInventoryLocationToForm(invRequisitionForm);
	setAllItemsToForm(invRequisitionForm);
	invRequisitionForm.resetFields();
	log.info("exit initInvRequisition..");
		return mapping.findForward("initInvRequisition");
}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInvRequisition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	log.info("entered submitInvRequisition..");
	InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
	 ActionMessages errors = invRequisitionForm.validate(mapping, request);
	if (errors.isEmpty()) {
		HttpSession session = request.getSession();
		try {
			String userName = ""; 
			userName = (String)session.getAttribute("username");
			if(userName!=null){
				invRequisitionForm.setUserName(userName);
			}

			setUserId(request,invRequisitionForm);	
			InvRequisitionHandler.getInstance().addRequisition(invRequisitionForm);

		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}
	} else {
		addErrors(request, errors);
		setAllInventoryLocationToForm(invRequisitionForm);
		setAllItemsToForm(invRequisitionForm);

		return mapping.findForward("initInvRequisition");
	}

	
	log.info("exit submitInvRequisition..");
		return mapping.findForward("submitInvRequisition");
}

	/**
	 * Used to prepare the itemList (Along with quantity) based on the selection
	 */

	public ActionForward prepareItemListForTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into prepareItemListForTransfer InvRequisitionAction");	
		InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
		 ActionErrors errors = invRequisitionForm.validate(mapping, request);
		try{
			setAllItemsToForm(invRequisitionForm);
			if(errors.isEmpty()){
				Map<String, ItemTO> itemMap = invRequisitionForm.getItemMap();
				//Used to not allow duplicate items to transfer
				Set<String> itemIdSet = invRequisitionForm.getItemIdSet();				
				if(itemIdSet==null || itemIdSet.isEmpty()){
					itemIdSet = new HashSet<String>();
				}				
				List<ItemTO> transferItemList = invRequisitionForm.getTransferItemList();
				if(transferItemList == null || transferItemList.isEmpty()){
					transferItemList = new ArrayList<ItemTO>();
				}					
				String itemId = invRequisitionForm.getItemId();
					if(!itemIdSet.contains(itemId)){
						ItemTO itemTO = new ItemTO();
						itemTO.setId(itemId);
						itemTO.setQuantityIssued(invRequisitionForm.getQuantityIssued());
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
						invRequisitionForm.setTransferItemList(transferItemList);
						invRequisitionForm.setItemIdSet(itemIdSet);
						invRequisitionForm.setQuantityIssued(null);
						invRequisitionForm.setItemId(null);
						invRequisitionForm.setSearchItem(null);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.inventory.stock.transfer.item.duplicate"));
					}
			}
		}
		catch (Exception e) {
			log.error("Error occured in prepareItemListForTransfer of InvRequisitionAction", e);
			String msg = super.handleApplicationException(e);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into prepareItemListForTransfer InvRequisitionAction");
		return mapping.findForward("initInvRequisition");
	}
	
	/**
	 * Used to delete an item for the transfer item list
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteItem InvRequisitionAction");
		InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
		ActionMessages message = new ActionMessages();
		try{
			boolean isDeleted = false;
			String itemId= invRequisitionForm.getItemId();
				Iterator<ItemTO> iterator = invRequisitionForm.getTransferItemList().iterator();
				while (iterator.hasNext()) {
					ItemTO itemTO = (ItemTO) iterator.next();
					if(itemTO.getId().equals(itemId)){
						iterator.remove();
						invRequisitionForm.getItemIdSet().remove(itemId);
						isDeleted = true;
					}
				}
				if(isDeleted){
					message.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.inventory.stock.transfer.item.delete.success"));
					saveMessages(request, message);
				}
			setAllItemsToForm(invRequisitionForm);
		}
		catch (Exception e) {
			log.error("Error occured in deleteItem of InvRequisitionAction", e);
			String msg = super.handleApplicationException(e);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into deleteItem InvRequisitionAction");
		return mapping.findForward("initInvRequisition");
	}
	
	/**
	 * Used to get all inventories
	 */
	public void setAllInventoryLocationToForm(InvRequisitionForm invRequisitionForm)throws Exception {
		log.info("entering into setAllInventoryLocationToForm");
		List<SingleFieldMasterTO> inventoryList = StockTransferHandler.getInstance().getAllInventoryLocation();
		invRequisitionForm.setInventoryList(inventoryList);
		log.info("Leaving into setAllInventoryLocationToForm");
	}
	/**
	 * used to get all items 
	 */
	
	public void setAllItemsToForm(InvRequisitionForm invRequisitionForm)throws Exception{
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
		invRequisitionForm.setItemList(itemList);
		Map<String, ItemTO> itemMap = new HashMap<String, ItemTO>();
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> ItemIterator = itemList.iterator();
			while (ItemIterator.hasNext()) {
				ItemTO itemTO = ItemIterator.next();
				itemMap.put(itemTO.getId(), itemTO);
			}
		}
		invRequisitionForm.setItemMap(itemMap);

	}

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInvRequisitionApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initInvRequisitionApproval..");
		InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		try {
			List<InvRequestTO> invReqList = InvRequisitionHandler.getInstance().getRequestedItems();
			if(invReqList!=null && !invReqList.isEmpty()){			
				invRequisitionForm.setRequistionApprovalList(invReqList);
			}else{
				invRequisitionForm.setRequistionApprovalList(null);
				message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}
		log.info("exit initInvRequisition..");
		return mapping.findForward("initInvRequisitionApproval");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInvRequisitionApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitInvRequisitionApproval..");
		InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		 ActionMessages errors = invRequisitionForm.validate(mapping, request);
		boolean isUpdated = false;
		if (errors.isEmpty()) {
			try {
				setUserId(request,invRequisitionForm);	
				isUpdated = InvRequisitionHandler.getInstance().updateRequisition(invRequisitionForm);
				if(isUpdated){
					message = new ActionMessage("knowledgePro.admission.record.edited");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			} catch (ApplicationException ae) {
				String msg = super.handleApplicationException(ae);
				invRequisitionForm.setErrorMessage(msg);
				invRequisitionForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception e) {
				throw e;
			}
		} else {
			addErrors(request, errors);
			List<InvRequestTO> invReqList = InvRequisitionHandler.getInstance().getRequestedItems();
			if(invReqList!=null && !invReqList.isEmpty()){			
				invRequisitionForm.setRequistionApprovalList(invReqList);
			}
			return mapping.findForward("initInvRequisitionApproval");
		}


		log.info("exit submitInvRequisitionApproval..");
		return mapping.findForward("initInvRequisitionApproval");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered getDetails..");
		InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
		try {
			setUserId(request,invRequisitionForm);	
			InvRequestTO invRequestTO = InvRequisitionHandler.getInstance().getDetails(invRequisitionForm);
			if(invRequestTO!=null){
				invRequisitionForm.setInvRequestTO(invRequestTO);
			}	
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}


		log.info("exit getDetails..");
		return mapping.findForward("getDetails");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInvRequisitionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	log.info("entered initInvRequisitionReport..");
	InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form; 
	HttpSession session = request.getSession();
	setAllInventoryLocationToForm(invRequisitionForm);	
	session.removeAttribute("requisitionList");
	invRequisitionForm.resetFields();
	log.info("exit initInvRequisitionReport..");
		return mapping.findForward("initInvRequisitionReport");
}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInvRequisitionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	log.info("entered submitInvRequisitionReport..");
	InvRequisitionForm invRequisitionForm = (InvRequisitionForm)form;
	ActionMessages messages = new ActionMessages();
	ActionMessage message = null;
	
	 ActionMessages errors = invRequisitionForm.validate(mapping, request);
	List<InvRequestTO> invRequestTOList;
	if (errors.isEmpty()) {
		HttpSession session = request.getSession();
		try {
			
			setUserId(request,invRequisitionForm);	
			invRequestTOList = InvRequisitionHandler.getInstance().getInvRequestByLocation(invRequisitionForm);
			
			if(invRequestTOList!=null && !invRequestTOList.isEmpty()){
				session.setAttribute("requisitionList",invRequestTOList);
			}else{
				message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			invRequisitionForm.setErrorMessage(msg);
			invRequisitionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}
	} else {
		addErrors(request, errors);
		setAllInventoryLocationToForm(invRequisitionForm);
		setAllItemsToForm(invRequisitionForm);

		return mapping.findForward("initInvRequisitionReport");
	}

	
	log.info("exit submitInvRequisition..");
		return mapping.findForward("submitInvRequisitionReport");
}

}
