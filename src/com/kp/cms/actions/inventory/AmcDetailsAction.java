package com.kp.cms.actions.inventory;

import java.util.Date;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.AmcDetailsForm;
import com.kp.cms.handlers.inventory.AmcDetailsHandler;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AmcDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AmcDetailsAction.class);
	
	/**
	 * setting item category list to request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         AMC_DETAILS_ENTRY
	 * @throws Exception
	 */
	public ActionForward initAmcDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initAmcDetails ");
		AmcDetailsForm acmAmcDetailsForm = (AmcDetailsForm) form;
		acmAmcDetailsForm.setItemCategoryId(null);
		acmAmcDetailsForm.setItemNo(null);
		acmAmcDetailsForm.setAmcList(null);
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			getItemCategoryList(request);
			setVendorDetailsToForm(acmAmcDetailsForm);
			setUserId(request, acmAmcDetailsForm);
		} catch (Exception e) {
			log.error("error initAmcDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				acmAmcDetailsForm.setErrorMessage(msg);
				acmAmcDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initAmcDetails ");
	
		return mapping.findForward(CMSConstants.AMC_DETAILS_ENTRY);
	}
	
	/** setting amc list to request
	 * forward to the second page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return SUBMIT_AMC_DETAILS_ENTRY
	 * @throws Exception
	 */
	public ActionForward submitAmcDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside submitAmcDetails");
		AmcDetailsForm amcDetailsForm = (AmcDetailsForm) form;
		
		 ActionErrors errors = amcDetailsForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				getItemCategoryList(request);
				return mapping.findForward(CMSConstants.AMC_DETAILS_ENTRY);
			}
			
			setAmcListRequest(request, amcDetailsForm);
			if(amcDetailsForm.getAmcList() == null || amcDetailsForm.getAmcList().size() <= 0){
				getItemCategoryList(request);
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.AMC_DETAILS_ENTRY);				
			}
		} catch (Exception e) {
			log.error("error in submitAmcDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				amcDetailsForm.setErrorMessage(msg);
				amcDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving submitAmcDetails");
		return mapping.findForward(CMSConstants.SUBMIT_AMC_DETAILS_ENTRY);	
	}
	
	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return updateAmcDetails
	 * @throws Exception
	 */
	public ActionForward updateAmcDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside updateAmcDetails");
		AmcDetailsForm amcDetailsForm = (AmcDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		InvAmcTO inAmcTO;
		Boolean isAdded;
		try {
			//empty checking
			Iterator<InvAmcTO> amcIter = amcDetailsForm.getAmcList().iterator();
			Boolean itemFound = false;
			boolean isValidStartDate = true;
			boolean isValidEndDate = true;
			
			while (amcIter.hasNext()){
				inAmcTO = amcIter.next();
				
				if(inAmcTO.getNewWarrantyStartDate()!= null && !inAmcTO.getNewWarrantyStartDate().trim().isEmpty()){
					isValidStartDate = CommonUtil.isValidDate(inAmcTO.getNewWarrantyStartDate());
				}
				if(inAmcTO.getNewWarrantyEndDate()!= null && !inAmcTO.getNewWarrantyEndDate().trim().isEmpty()){
					isValidEndDate = CommonUtil.isValidDate(inAmcTO.getNewWarrantyEndDate());
				}
				//date validation
				if(!isValidStartDate || !isValidEndDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SUBMIT_AMC_DETAILS_ENTRY);		
				}					
				
				if(inAmcTO.getNewWarrantyStartDate()!= null && !inAmcTO.getNewWarrantyStartDate().trim().isEmpty()
					&& inAmcTO.getNewWarrantyEndDate()!= null && !inAmcTO.getNewWarrantyEndDate().trim().isEmpty()
					&& inAmcTO.getVendorId() > 0 &&  inAmcTO.getAmount()!= null && !inAmcTO.getAmount().trim().isEmpty()){
					itemFound = true;
//					break;
				}						
			}			
			if(!itemFound){
				errors.add("error", new ActionError("knowledgepro.inventory.amc.details.entry.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_AMC_DETAILS_ENTRY);
			}
			if(checkForEmpty(amcDetailsForm)){
				errors.add("error", new ActionError("knowledgepro.inventory.amc.details.entry.mandatory.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_AMC_DETAILS_ENTRY);
			}
			//date validation
			Date startDate = null;
			Date endDate = null;
			amcIter = amcDetailsForm.getAmcList().iterator();
			while (amcIter.hasNext()){
				inAmcTO = amcIter.next();
				if(inAmcTO.getNewWarrantyStartDate()!= null && !inAmcTO.getNewWarrantyStartDate().trim().isEmpty()){
					startDate = CommonUtil.ConvertStringToDate(inAmcTO.getNewWarrantyStartDate());
				}
				if(inAmcTO.getNewWarrantyEndDate()!= null && !inAmcTO.getNewWarrantyEndDate().trim().isEmpty()){
					endDate = CommonUtil.ConvertStringToDate(inAmcTO.getNewWarrantyEndDate());
				}
				
				if(startDate!= null && endDate!= null){	
					if(startDate.compareTo(endDate) == 1){
						errors.add("error", new ActionError("knowledgepro.inventory.amc.details.start.date.cannot.greater", inAmcTO.getItemNo()));
						break;
					}
				}
						
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_AMC_DETAILS_ENTRY);
			}
			
			isAdded = AmcDetailsHandler.getInstance().saveAmcDetails(amcDetailsForm);
			getItemCategoryList(request);
		
		} catch (Exception e) {
			log.error("error in updateAmcDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				amcDetailsForm.setErrorMessage(msg);
				amcDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.inventory.amc.details.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			amcDetailsForm.setAmcList(null);
			amcDetailsForm.setItemCategoryId(null);
			amcDetailsForm.setItemNo(null);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.amc.details.update.failure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving updateAmcDetails");
		return mapping.findForward(CMSConstants.AMC_DETAILS_ENTRY);
	}
	
	/**
	 * 
	 * @param request
	 *            This method sets the amcList to Request
	 * @throws Exception
	 */
	public void setAmcListRequest(HttpServletRequest request, AmcDetailsForm amcDetailsForm) throws Exception {
		log.debug("inside setAmcListRequest");
		String itemNo = "";
		if(amcDetailsForm.getItemNo()!= null && !amcDetailsForm.getItemNo().trim().isEmpty()){
			itemNo = amcDetailsForm.getItemNo();
		}
		List<InvAmcTO> amcList = AmcDetailsHandler.getInstance().getAmcDetails(Integer.parseInt(amcDetailsForm.getItemCategoryId()), itemNo);
		amcDetailsForm.setAmcList(amcList);
	}
	
	/**
	 * Method to set all active Item Categories to request
	 * @param request
	 * @throws Exception
	 */
	public void getItemCategoryList(HttpServletRequest request) throws Exception{
		List<SingleFieldMasterTO> itemCategoryList = ItemHandler.getInstance().getItemCategory();
		if( itemCategoryList != null ){
			request.setAttribute("itemCategoryList", itemCategoryList);
		}
	}		
	
	/**
	 * 
	 * @param request
	 *            This method sets the amcList to Request
	 * @throws Exception
	 */
	public void setHistoryDetailsRequest(HttpServletRequest request, AmcDetailsForm amcDetailsForm) throws Exception {
		log.debug("inside setAmcListRequest");
		String itemNo = "";
		if(amcDetailsForm.getSelectedItemNo()!= null && !amcDetailsForm.getSelectedItemNo().trim().isEmpty()){
			itemNo = amcDetailsForm.getSelectedItemNo();
		}
		List<InvAmcTO> amcHistoryList = AmcDetailsHandler.getInstance().getAmcHistoryDetails(Integer.parseInt(amcDetailsForm.getItemCategoryId()), itemNo);
		request.setAttribute("amcHistoryList", amcHistoryList);
	}
	
	/**
	 * view method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering viewHistory ");
		AmcDetailsForm amcDetailsForm = (AmcDetailsForm) form;
		try {
			setHistoryDetailsRequest(request, amcDetailsForm);
		} catch (Exception e) {
			log.error("error in viewHistory...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				amcDetailsForm.setErrorMessage(msg);
				amcDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving viewHistory ");
		return mapping.findForward(CMSConstants.VIEW_AMC_HISTORY);
	}

	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void setVendorDetailsToForm(AmcDetailsForm amcForm) throws Exception{
		log.info("start setVendorDetailsToRequest");
		List<VendorTO> vendorList = VendorHandler.getInstance().getVendorDetails();
		amcForm.setVendorList(vendorList);
		log.info("exit setVendorDetailsToRequest");
	}
	
	/**
	 * 
	 * @param amcDetailsForm
	 * @return
	 */
	private boolean checkForEmpty(AmcDetailsForm amcDetailsForm){
		log.debug("inside checkForEmpty");
		Iterator<InvAmcTO> amcIter = amcDetailsForm.getAmcList().iterator();
		Boolean itemFound = false;
		InvAmcTO inAmcTO;
		
		while (amcIter.hasNext()){
			inAmcTO = amcIter.next();
			
			if(inAmcTO.getNewWarrantyStartDate()!= null && !inAmcTO.getNewWarrantyStartDate().trim().isEmpty()
			&& inAmcTO.getNewWarrantyEndDate()!= null && !inAmcTO.getNewWarrantyEndDate().trim().isEmpty()
			&& inAmcTO.getVendorId() > 0 &&  inAmcTO.getAmount()!= null && !inAmcTO.getAmount().trim().isEmpty()){
				continue;
			}
			if(inAmcTO.getNewWarrantyStartDate()== null || inAmcTO.getNewWarrantyStartDate().trim().isEmpty()
				&& inAmcTO.getNewWarrantyEndDate() == null || inAmcTO.getNewWarrantyEndDate().trim().isEmpty()
				&& inAmcTO.getVendorId() <= 0 &&  inAmcTO.getAmount() == null || inAmcTO.getAmount().trim().isEmpty()){
				itemFound = false;
			}
			else
			{
				itemFound = true;
				break;
			}
		}	
		log.debug("exit checkForEmpty");
		return itemFound;
	}
	
}
