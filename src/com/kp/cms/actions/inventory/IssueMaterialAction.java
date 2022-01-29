package com.kp.cms.actions.inventory;

import java.util.Iterator;

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
import com.kp.cms.forms.inventory.IssueMaterialForm;
import com.kp.cms.handlers.inventory.IssueMaterialHandler;
import com.kp.cms.to.inventory.ItemTO;

@SuppressWarnings("deprecation")
public class IssueMaterialAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(IssueMaterialAction.class);
/**
 * Used to intialize issuematerial home page
 */
public ActionForward initIssuematerial(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("entering into initIssuematerial IssueMaterialAction");	
	IssueMaterialForm materialForm = (IssueMaterialForm)form;
	materialForm.clear();
	log.info("Leaving into initIssuematerial IssueMaterialAction");	
	return mapping.findForward(CMSConstants.INVENTORY_INIT_ISSUE_MATERIAL);
	}

/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return Get the list items to issue
 * @throws Exception
 */
	public ActionForward getIssueItemDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into getIssueItemDetails IssueMaterialAction");	
		IssueMaterialForm materialForm = (IssueMaterialForm)form;
		 ActionErrors errors = materialForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				IssueMaterialHandler.getInstance().setItemDetailsToForm(materialForm, errors);
				if(errors.isEmpty()){
					setUserId(request, materialForm);
					return mapping.findForward(CMSConstants.INVENTORY_ISSUE_DETAILS);
				}
			}
		} catch (Exception e) {
			log.error("Error occured in initStockTransferItemSelection of StockTransferAction", e);
			String msg = super.handleApplicationException(e);
			materialForm.setErrorMessage(msg);
			materialForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("entering into getIssueItemDetails IssueMaterialAction");	
		return mapping.findForward(CMSConstants.INVENTORY_INIT_ISSUE_MATERIAL);
	}
	
	/**
	 * Used to finally issuing the material 
	 */

	public ActionForward submitIssueItemDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitIssueItemDetails IssueMaterialAction");	
		IssueMaterialForm materialForm = (IssueMaterialForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages message = new ActionMessages();		
		try {
			if(isCancelled(request)){
				IssueMaterialHandler.getInstance().setItemDetailsToForm(materialForm, errors);
				return mapping.findForward(CMSConstants.INVENTORY_ISSUE_DETAILS);
			}
			errors = validateItemList(materialForm, errors);
			if (errors.isEmpty()) {
				boolean isIssued = IssueMaterialHandler.getInstance().submitIssueMaterials(materialForm, errors);
				if(isIssued){
					message.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.INVENTORY_ISSUE_SUCCESS,materialForm.getMaterialTO().getInventoryName()));
					saveMessages(request, message);
					materialForm.clear();
					return mapping.findForward(CMSConstants.INVENTORY_INIT_ISSUE_MATERIAL);
				}
			}
		} catch (Exception e) {
			log.error("Error occured in submitIssueItemDetails of IssueMaterialAction", e);
			String msg = super.handleApplicationException(e);
			materialForm.setErrorMessage(msg);
			materialForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into submitIssueItemDetails IssueMaterialAction");	
		return mapping.findForward(CMSConstants.INVENTORY_ISSUE_DETAILS);
	}
	/**
	 * Used to validate the item List when submit button is clicked
	 * If no item issued. throw error message
	 */
	public ActionErrors validateItemList(IssueMaterialForm materialForm, ActionErrors errors)throws Exception{
		log.info("Entering into validateItemList IssueMaterialAction");
		if(materialForm.getItemList()!=null || !materialForm.getItemList().isEmpty()){
			int count =0;
			double requestedQuantity = 0.0;
			double issuedQuantity = 0.0;
			
			Iterator<ItemTO> iterator = materialForm.getItemList().iterator();
			while (iterator.hasNext()) {
				ItemTO itemTO = iterator.next();
				if(itemTO.getQuantityIssued()!=null && !StringUtils.isEmpty(itemTO.getQuantityIssued()) &&
				!StringUtils.isNumeric(itemTO.getQuantityIssued())){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_ISSUE_QUANTITY_INTEGER));
				}
				if(itemTO.getQuantityIssued()==null || StringUtils.isEmpty(itemTO.getQuantityIssued())){
					++count;
				}
				if(itemTO.getRequestedQuantity()!=null && !StringUtils.isEmpty(itemTO.getRequestedQuantity())&&
				itemTO.getQuantityIssued()!=null && !StringUtils.isEmpty(itemTO.getQuantityIssued()) && 
				StringUtils.isNumeric(itemTO.getQuantityIssued())){		
					requestedQuantity = Double.valueOf(itemTO.getRequestedQuantity()).doubleValue();
					issuedQuantity = Double.valueOf(itemTO.getQuantityIssued()).doubleValue();
					if(issuedQuantity > requestedQuantity){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_ISSUE_QUANTITY_GREATER));
					}
				}
			}
			if(materialForm.getItemList().size()==count){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_ISSUE_QUANTITY_SELECT_ANY));
			}
		}		
		log.info("Leaving into validateItemList IssueMaterialAction");
		return errors;
	}
}
