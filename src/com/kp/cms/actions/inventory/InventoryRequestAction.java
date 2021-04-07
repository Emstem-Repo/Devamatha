package com.kp.cms.actions.inventory;

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
import com.kp.cms.forms.inventory.InventoryRequestForm;
import com.kp.cms.handlers.inventory.InventoryRequestHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InventoryRequestTO;

@SuppressWarnings("deprecation")
public class InventoryRequestAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(ItemAction.class);
	
	/**
	 * Method to set the required data to the form to display in inventoryRequest.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInventoryLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered getInventoryLocation InventoryRequestAction");
		InventoryRequestForm inventoryRequestForm = (InventoryRequestForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			inventoryRequestForm.clear();
			getInventoryLocationList(inventoryRequestForm);
			setUserId(request, inventoryRequestForm);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			inventoryRequestForm.setErrorMessage(msg);
			inventoryRequestForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Exit getInventoryLocation InventoryRequestAction");		
		return mapping.findForward(CMSConstants.INVENTORY_REQUEST);
	}
	
	/**
	 * Method to set all active Inventory Locations to the form
	 * @param inventoryRequestForm
	 * @throws Exception
	 */
	public void getInventoryLocationList(InventoryRequestForm inventoryRequestForm) throws Exception{
		List<SingleFieldMasterTO> inventoryLocationList = InventoryRequestHandler.getInstance().getInventoryLocation();
		if( inventoryLocationList != null ){
			inventoryRequestForm.setInventoryLocationList(inventoryLocationList);
		}
	}
	
	/**
	 * Method to set the inventory request details to the form to display in inventoryRequest.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward inventoryRequestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InventoryRequestForm inventoryRequestForm = (InventoryRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		try{
			ActionErrors errors = inventoryRequestForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			
			int invLocationId = Integer.parseInt(inventoryRequestForm.getInvLocationId());
			
			List<InventoryRequestTO> inventoryRequestList = InventoryRequestHandler.getInstance().getInventoryRequestList(invLocationId);
			
			if( inventoryRequestList.size()==0){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INVENTORY_REQUEST);
			}else{
				inventoryRequestForm.setInventoryRequestList(inventoryRequestList);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INVENTORY_REQUEST);
		}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			inventoryRequestForm.setErrorMessage(msg);
			inventoryRequestForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit inventoryRequestDetails");
		return mapping.findForward(CMSConstants.INVENTORY_REQUEST_RESULT);
	}
}