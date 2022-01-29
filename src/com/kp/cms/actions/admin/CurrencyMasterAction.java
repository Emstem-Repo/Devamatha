package com.kp.cms.actions.admin;

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
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CurrencyMasterForm;
import com.kp.cms.handlers.admin.CurrencyMasterHandler;
import com.kp.cms.to.admin.CurrencyMasterTO;

@SuppressWarnings("deprecation")
public class CurrencyMasterAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(CurrencyMasterAction.class);
	
	/**
	 * This method performs the retrieving of currency details in CurrencyMasterAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward initCurrencyMasterEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Currency Master");
		try {
			currencyMasterForm.reset(mapping, request);
			//getting all currency details.
			List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
			currencyMasterForm.setCurrencyMasterList(currencyList);
		}  catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			currencyMasterForm.setErrorMessage(msgKey);
			currencyMasterForm.setErrorStack(businessException.getMessage());
			log.info("businessException in initCurrencyMaster in CurrencyMasterAction class. ");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			log.info("exception in initCurrencyMaster in CurrencyMasterAction class. ");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}

	/**
	 * This method performs the adding of currency details in CurrencyMasterAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward addCurrencyMasterEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = currencyMasterForm.validate(mapping, request);

		boolean isCurrencyAdded = false;
		String currencyName="";
		String currencyShortName = "";
		try
		{
		if (errors.isEmpty()) {
			setUserId(request, currencyMasterForm);
			if(currencyMasterForm.getCurrencyName()!=null && !currencyMasterForm.getCurrencyName().isEmpty()){
				currencyName = currencyMasterForm.getCurrencyName().trim();
				currencyShortName = currencyMasterForm.getCurrencyShortName().trim();
			}else{
				return mapping.findForward(CMSConstants.LOGIN_PAGE);
			}
			Currency currency =CurrencyMasterHandler.getInstance().isCurrencyNameExist(currencyName, 0);
			Currency currency1 =CurrencyMasterHandler.getInstance().isCurrencyShortNameExist(currencyShortName, 0);
			if(currency!=null && currency.getIsActive()){
				errors.add(CMSConstants.CURRENCY_NAME_EXIST, new ActionError(CMSConstants.CURRENCY_NAME_EXIST));
				saveErrors(request, errors);
			}else if(currency1!=null && currency1.getIsActive()){
				errors.add(CMSConstants.CURRENCY_SHORT_NAME_EXIST, new ActionError(CMSConstants.CURRENCY_SHORT_NAME_EXIST));
				saveErrors(request, errors);
			}
			else if(currency!=null && !currency.getIsActive()){
				errors.add(CMSConstants.CURRENCY_NAME_REACTIVATE, new ActionError(CMSConstants.CURRENCY_NAME_REACTIVATE));
				saveErrors(request, errors);			
			}else{
				isCurrencyAdded = CurrencyMasterHandler.getInstance().addCurrencyMaster(currencyMasterForm);
		
			if (isCurrencyAdded) {
				ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_ADD_SUCCESS);// Adding success message.
				messages.add("messages", message);
				saveMessages(request, messages);
				currencyMasterForm.reset(mapping, request);	
			}else{
				errors.add(CMSConstants.CURRENCY_ADD_FAILURE, new ActionError(CMSConstants.CURRENCY_ADD_FAILURE));// Adding failure message
			}
			}
		} else {
			saveErrors(request, errors);
			}
		
		}catch (BusinessException businessException) {
			log.info("businessException in addCurrencyMasterEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			log.info("exception in addCurrencyMasterEntry");
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//getting all currency details.
		List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
		currencyMasterForm.setCurrencyMasterList(currencyList);
		return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}


	/**
	 * This method performs the editing one record of currency details in CurrencyMasterAction class.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward editCurrencyMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = currencyMasterForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		CurrencyMasterTO currencyMasterTO =CurrencyMasterHandler.getInstance().editCurrencyMaster(currencyMasterForm.getCurrencyMasterId());
	    		currencyMasterForm.setCurrencyName(currencyMasterTO.getName().trim());
	    		currencyMasterForm.setCurrencyShortName(currencyMasterTO.getCurrencyCode().trim());
	    		currencyMasterForm.setCurrencyMasterId(currencyMasterTO.getId());
	    		request.setAttribute("currencyOperation","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
	    			session.setAttribute("CurrencyName",currencyMasterTO.getName());
	    			session.setAttribute("currencyShortName",currencyMasterTO.getCurrencyCode());
	    		}
	    	}
	    	else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("businessException in editCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			log.info("Exception in editCurrencyMaster");
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.	
	    List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
	    currencyMasterForm.setCurrencyMasterList(currencyList);
	    
		log.info("end of editCurrencyMaster method in CurrencyMasterAction class.");
		return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}
	
	/**
     * This method performs the updating of one record of currency details in CurrencyMasterAction.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward updateCurrencyMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = currencyMasterForm.validate(mapping, request);
	    try{
	    	//if reset is clicked when validation fails.
	    		if(isCancelled(request)){
	    			CurrencyMasterTO currencyMasterTO =CurrencyMasterHandler.getInstance().editCurrencyMaster(currencyMasterForm.getCurrencyMasterId());
		    		currencyMasterForm.setCurrencyName(currencyMasterTO.getName().trim());
		    		currencyMasterForm.setCurrencyShortName(currencyMasterTO.getCurrencyCode().trim());
		    		currencyMasterForm.setCurrencyMasterId(currencyMasterTO.getId());
		    		request.setAttribute("currencyOperation","edit");
		    		 List<CurrencyMasterTO> currencyMasterList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
	    		    currencyMasterForm.setCurrencyMasterList(currencyMasterList);	
	    			return mapping.findForward(CMSConstants.Currency_Master_Entry);
	    		}
	    		if(errors.isEmpty()){
		    		setUserId(request, currencyMasterForm);
		    		HttpSession session=request.getSession(false);
		    		String cname = session.getAttribute("CurrencyName").toString();
		    		String csname = session.getAttribute("currencyShortName").toString();
		    		String currencyName = currencyMasterForm.getCurrencyName().trim();
		    		String currencyShortName = currencyMasterForm.getCurrencyShortName().trim();
		    		if(!cname.equalsIgnoreCase(currencyName) || !csname.equalsIgnoreCase(currencyShortName)){
		    			Currency currency = CurrencyMasterHandler.getInstance().isCurrencyNameExist(currencyName, currencyMasterForm.getCurrencyMasterId());
		    			Currency currency1 = CurrencyMasterHandler.getInstance().isCurrencyShortNameExist(currencyShortName, currencyMasterForm.getCurrencyMasterId());
		    		if(currency!=null && currency.getIsActive() && currency.getName().equalsIgnoreCase(currencyName)){
						errors.add(CMSConstants.CURRENCY_NAME_EXIST, new ActionError(CMSConstants.CURRENCY_NAME_EXIST));
						request.setAttribute("currencyOperation","edit");
						saveErrors(request, errors);
		    		}else if(currency1!=null && currency1.getIsActive() && currency1.getCurrencyCode().equalsIgnoreCase(currencyShortName)){
						errors.add(CMSConstants.CURRENCY_SHORT_NAME_EXIST, new ActionError(CMSConstants.CURRENCY_SHORT_NAME_EXIST));
						request.setAttribute("currencyOperation","edit");
						saveErrors(request, errors);
					}else if(currency!=null && !currency.getIsActive()){
						errors.add(CMSConstants.CURRENCY_NAME_REACTIVATE, new ActionError(CMSConstants.CURRENCY_NAME_REACTIVATE));
						saveErrors(request, errors);			
						request.setAttribute("currencyOperation","edit");
					}else if(currency1!=null && currency1.getIsActive() && currency!=null && currency.getName().equalsIgnoreCase(currencyName) 
							&& currency1.getCurrencyCode().equals(currencyShortName) && currency1.getCurrencyCode().equals(csname)){
		    			errors.add(CMSConstants.CURRENCY_SHORT_NAME_EXIST, new ActionError(CMSConstants.CURRENCY_SHORT_NAME_EXIST));
						request.setAttribute("currencyOperation","edit");
						saveErrors(request, errors);
	    		}else{
					boolean isUpdated =CurrencyMasterHandler.getInstance().updateCurrencyMaster(currencyMasterForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("CurrencyName");
		    			session.removeAttribute("currencyShortName");
		    			ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						currencyMasterForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						currencyMasterForm.reset(mapping, request);
		    		}
		    	}
	    		}else{
		    		boolean isUpdated =CurrencyMasterHandler.getInstance().updateCurrencyMaster(currencyMasterForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("CurrencyName");
		    			session.removeAttribute("currencyShortName");
		    			ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						currencyMasterForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						currencyMasterForm.reset(mapping, request);
		    		}
		    	}
	    	}else{
	    		//errors are present
				errors.add(messages);
				request.setAttribute("currencyOperation","edit");
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("businessException in updateCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			log.info("Exception in updateCurrencyMaster");
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
	    currencyMasterForm.setCurrencyMasterList(currencyList);	
		return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}
	
	/**
     * This method performs the deleting one record of currency details in CurrencyMasterAction class.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward deleteCurrencyMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = currencyMasterForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, currencyMasterForm);
	    		boolean isDelete=CurrencyMasterHandler.getInstance().deleteCurrencyMaster(currencyMasterForm.getCurrencyMasterId(),currencyMasterForm.getUserId());
				if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					currencyMasterForm.reset(mapping, request);
				}if(!isDelete){
					//if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_DELETE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					currencyMasterForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("businessException in deleteCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			log.info("Exception in deleteCurrencyMaster");
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
	    currencyMasterForm.setCurrencyMasterList(currencyList);	
	    
		 return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}
	
	/**
	 * This method performs reactivation of currency details in CurrencyMasterAction class.
	 * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
	 */
	
	public ActionForward reActivateCurrencyMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CurrencyMasterForm currencyMasterForm = (CurrencyMasterForm)form; 
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = currencyMasterForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		setUserId(request, currencyMasterForm);
	    		//errors are empty
	    		boolean isActivated = CurrencyMasterHandler.getInstance().reActivateCurrencyMaster(currencyMasterForm.getCurrencyName(),currencyMasterForm.getUserId());
	    		if(isActivated){
	    			//if reactivation is success.
		    		ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_REACTIVATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					currencyMasterForm.reset(mapping, request);
	    		}else{
	    			//if reactivation is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.CURRENCY_REACTIVATE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					currencyMasterForm.reset(mapping, request);
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("businessException in reActivateCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			log.info("Exception in reActivateCurrencyMaster");
			String msg = super.handleApplicationException(exception);
			currencyMasterForm.setErrorMessage(msg);
			currencyMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
	    currencyMasterForm.setCurrencyMasterList(currencyList);
	   
	    return mapping.findForward(CMSConstants.Currency_Master_Entry);
	}
}