package com.kp.cms.actions.inventory;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.ListofNonWarrantyItemsHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;

@SuppressWarnings("deprecation")
public class ListofNonWarrantyItemsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ListofNonWarrantyItemsAction.class);
	
	/**
	 * initialize report method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNonWarrantyItemReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm)form;
		ActionMessages messages = new ActionMessages();
		try {		
			baseActionForm.setItemCategoryId(null);
			getItemCategoryList(request);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			baseActionForm.setErrorMessage(msg);
			baseActionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}				
		log.debug("inside initWarrantyItemReport");
		return mapping.findForward(CMSConstants.WARRANTY_ITEMS_REPORT_SEARCH);

	}
		
		
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of warranty item details based on the search criteria
	 * @throws Exception
	 */
	public ActionForward submitNonWarrantyListReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside submitWarrantyListReport");
		BaseActionForm bForm = (BaseActionForm) form;
		
		 ActionErrors errors = bForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WARRANTY_ITEMS_REPORT_SEARCH);
			}
			
			setWarrantyItemDetailsRequest(request, bForm);
		} catch (Exception e) {
			log.error("error in getDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				bForm.setErrorMessage(msg);
				bForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		
		log.debug("leaving submitWarrantyListReport");
		return mapping.findForward(CMSConstants.WARRANTY_ITEMS_REPORT_SUBMIT);
	}	
		

	/**
	 * 
	 * @param request
	 *            This method sets the itemList to Request
	 * @throws Exception
	 */
	public void setWarrantyItemDetailsRequest(HttpServletRequest request,BaseActionForm form) throws Exception {
		log.debug("inside setWarrantyItemDetailsRequest");
		HttpSession session = request.getSession();
		int itemCatgId = 0;
		if(form.getItemCategoryId()!= null && !form.getItemCategoryId().trim().isEmpty()){
			itemCatgId = Integer.parseInt(form.getItemCategoryId());
		}
		List<ItemTO> itemList = ListofNonWarrantyItemsHandler.getInstance().getNonWarrantyDetails(itemCatgId);
		session.setAttribute("itemList", itemList);
	}

	/**
	 * Method to set all active Item Categories to the request
	 * @param request
	 * @throws Exception
	 */
	public void getItemCategoryList(HttpServletRequest request) throws Exception{
		List<SingleFieldMasterTO> itemCategoryList = ItemHandler.getInstance().getItemCategory();
		if( itemCategoryList != null ){
			request.setAttribute("itemCategoryList", itemCategoryList);
		}
	}
	
}
