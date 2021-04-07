package com.kp.cms.actions.pettycash;

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
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.LastReceiptNumberForm;
import com.kp.cms.handlers.pettycash.LastrceiptNumberHandler;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.LastRceiptNumberTo;

@SuppressWarnings("deprecation")
public class LastReceiptNumberAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(LastReceiptNumberAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes LastReceiptnumber
	 * @throws Exception
	 */
	
	public ActionForward initLastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering initLastReceiptNumber ");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		
		try {
			assignListToForm(lastReceiptnumberForm);
			setFinancialYearList(lastReceiptnumberForm);
			lastReceiptnumberForm.clear();
		}
		catch (Exception e) {
			log.error("error initLastReceiptNumber");
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		log.debug("Exiting initLastReceiptNumber ");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
	
	
	public ActionForward addlastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of addlastReceiptNumber of LastReceiptNumberAction");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		 ActionErrors errors = lastReceiptnumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				setUserId(request, lastReceiptnumberForm);
				final int year = Integer.parseInt(lastReceiptnumberForm.getAcademicYear());
				PcReceiptNumber number = LastrceiptNumberHandler.getInstance().getLastReceiptNumberYear(year);
				/**
				 * Checks for the duplicate entry based on the year.
				 * If it exists in active mode then add the appropriate error message
				 */
				if (number != null) {
					if(number.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_EXISTS));					
					assignListToForm(lastReceiptnumberForm);
					setFinancialYearList(lastReceiptnumberForm);
					lastReceiptnumberForm.clear();
					saveErrors(request, errors);
					}				
				/**
				 * If it is in inactive mode then show the message to reactivate the same.
				 */
					else if(!number.getIsActive()){
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_REACTIVATE));
					assignListToForm(lastReceiptnumberForm);
					setFinancialYearList(lastReceiptnumberForm);
					saveErrors(request, errors);
					}
				}
				else{
				boolean isAdded;

				isAdded = LastrceiptNumberHandler.getInstance().addLastReceiptNumber(lastReceiptnumberForm);
				/**
				 * If add operation is success then append the success message else add the appropriate error message
				 */
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES,  new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBER_ADD_SUCCESS));
					saveMessages(request, messages);
					assignListToForm(lastReceiptnumberForm);
					setFinancialYearList(lastReceiptnumberForm);
					lastReceiptnumberForm.clear();
				}
				else {
					errors.add("error", new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_ADD_FAILED));
					assignListToForm(lastReceiptnumberForm);
					saveErrors(request, errors);
				}
				}
			}
			else{
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error in adding LastReceiptNumber");
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("End of addlastReceiptNumber of LastReceiptNumberAction");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
	public ActionForward deleteLastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of deleteLastReceiptNumber of LastReceiptNumberAction");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, lastReceiptnumberForm);
			String userId=lastReceiptnumberForm.getUserId();
			int receiptId = lastReceiptnumberForm.getId();
			boolean isDeleted;
			isDeleted = LastrceiptNumberHandler.getInstance().deleteLastReceiptNumber(receiptId, userId);
			/**
			 * If delete operation is success then add the success message.
			 * Else add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBER_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
				lastReceiptnumberForm.clear();
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
			}

		} catch (Exception e) {
			log.error("Error in deleteLastReceiptNumber");			
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Existing deleteLastReceiptNumber of LastReceiptNumberAction");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
	/**
	 * In reactivation
	 */

	public ActionForward reActivateLastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateLastReceiptNumber of LastReceiptNumberAction");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, lastReceiptnumberForm);
			String userId=lastReceiptnumberForm.getUserId();
			final int year = Integer.parseInt(lastReceiptnumberForm.getAcademicYear());
			boolean isReactivate;
			isReactivate = LastrceiptNumberHandler.getInstance().reActivateLastReceiptNumber(year, userId);
			/**
			 * If reactivation is success then add the success message.
			 * Else add the appropriate error message.
			 */
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBER_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				lastReceiptnumberForm.clear();
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
			} else {
				errors.add("error", new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating lastReceiptNumber");			
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateLastReceiptNumber of LastReceiptNumberAction");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
	
	/**
	 * 
	 *Used While editing
	 */

	public ActionForward editLastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editLastReceiptNumber of LastReceiptNumberAction");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		try {
			int receiptId = lastReceiptnumberForm.getId();
		LastRceiptNumberTo lastReceiptNumberTO = LastrceiptNumberHandler.getInstance().getLastReceiptNumberDetailsonId(receiptId);
			//Keeping the year in session scope
			if(lastReceiptNumberTO!=null){
				lastReceiptnumberForm.setReceiptNo(String.valueOf(lastReceiptNumberTO.getReceiptNo()));
				lastReceiptnumberForm.setAcademicYear(String.valueOf(lastReceiptNumberTO.getFinacialYearID()));
				int previousYear=lastReceiptNumberTO.getFinacialYearID();
				HttpSession session=request.getSession(true);
				session.setAttribute("previousYear", previousYear);
			}
			assignListToForm(lastReceiptnumberForm);
			setFinancialYearList(lastReceiptnumberForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error in editing lastReceiptNumber");
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exiting from editLastReceiptNumber of LastReceiptNumberAction");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);

	}
	
	public ActionForward updateLastReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updateLastReceiptNumber of LastReceiptNumberAction");
		LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
		 ActionErrors errors = lastReceiptnumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request)){
				int receiptId = lastReceiptnumberForm.getId();
				
				LastRceiptNumberTo lastReceiptNumberTO = LastrceiptNumberHandler.getInstance().getLastReceiptNumberDetailsonId(receiptId);
				if(lastReceiptNumberTO!=null){
					lastReceiptnumberForm.setReceiptNo(String.valueOf(lastReceiptNumberTO.getReceiptNo()));
					lastReceiptnumberForm.setAcademicYear(String.valueOf(lastReceiptNumberTO.getFinacialYearID()));
					int previousYear=lastReceiptNumberTO.getFinacialYearID();
					HttpSession session=request.getSession(true);
					session.setAttribute("previousYear", previousYear);
				}			
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
			}
			else if(errors.isEmpty()){
				setUserId(request, lastReceiptnumberForm);						
				/**
				 * Getting the previous year from session and comparing with the current year.
				 * 
				 */
					HttpSession session=request.getSession(false);
					final int prevoiusYear= (Integer)session.getAttribute("previousYear");
					final int currentYear = Integer.parseInt(lastReceiptnumberForm.getAcademicYear());
					boolean isUpdated;
				if(prevoiusYear!=currentYear){	
					PcReceiptNumber number = LastrceiptNumberHandler.getInstance().getLastReceiptNumberYear(currentYear);
				/**
				 * Checking for duplicate entry.
				 * And add the appropriate error message
				 */
						if (number != null) {
							if(number.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							assignListToForm(lastReceiptnumberForm);
							setFinancialYearList(lastReceiptnumberForm);
							
							}
							if (!number.getIsActive()) {
							errors.add("error",new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_REACTIVATE));
							saveErrors(request, errors);
							assignListToForm(lastReceiptnumberForm);
							setFinancialYearList(lastReceiptnumberForm);
							}
						}
						else{
						isUpdated = LastrceiptNumberHandler.getInstance().updateLastReceiptNumber(lastReceiptnumberForm);
						
						//If update is successful then add the success message else show the error message
						
							if(isUpdated){
								messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBER_UPDATE_SUCCESS));
								saveMessages(request, messages);
								lastReceiptnumberForm.clear();
								assignListToForm(lastReceiptnumberForm);
								setFinancialYearList(lastReceiptnumberForm);
							}
							if(!isUpdated){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_UPDATE_FAILED));
								saveErrors(request, errors);
								lastReceiptnumberForm.clear();
								assignListToForm(lastReceiptnumberForm);
								setFinancialYearList(lastReceiptnumberForm);
							}
						}
					
				}else{
					isUpdated = LastrceiptNumberHandler.getInstance().updateLastReceiptNumber(lastReceiptnumberForm);
					
					//If update is successful then add the success message else show the error message
					
					if(isUpdated){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBER_UPDATE_SUCCESS));
						saveMessages(request, messages);
						lastReceiptnumberForm.clear();
						assignListToForm(lastReceiptnumberForm);
						setFinancialYearList(lastReceiptnumberForm);	
					}
					else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTYCASH_RECEIPTNUMBER_UPDATE_FAILED));
						saveErrors(request, errors);
						lastReceiptnumberForm.clear();
						assignListToForm(lastReceiptnumberForm);
						setFinancialYearList(lastReceiptnumberForm);
					}
				}
			}
			else{
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignListToForm(lastReceiptnumberForm);
				setFinancialYearList(lastReceiptnumberForm);
			}
		} catch (Exception e) {
			log.error("Error in updating feeBillNumber");
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("exiting from updateLastReceiptNumber of LastReceiptNumberAction");
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
/*	public ActionForward addLastReceiptNumber(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

	log.debug("inside addLastReceiptNumber Action");
	LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	errors = lastReceiptnumberForm.validate(mapping, request);
	boolean isAdded = false;
	boolean isthere = false;
	setUserId(request, lastReceiptnumberForm);
	try {
		setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
		if(lastReceiptnumberForm.getLastReceiptNo()==null  || StringUtils.isEmpty(lastReceiptnumberForm.getLastReceiptNo()))
		{
			errors.add("error", new ActionError("knowledgepro.petticash.lastreceiptNumber.isrequired"));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		}
		if(lastReceiptnumberForm.getCheckValue()!=null && !StringUtils.isEmpty(lastReceiptnumberForm.getCheckValue()) && StringUtils.isNumeric(lastReceiptnumberForm.getCheckValue())
				&& Integer.parseInt(lastReceiptnumberForm.getLastReceiptNo())<= Integer.parseInt(lastReceiptnumberForm.getCheckValue())){
			errors.add("error", new ActionError("knowledgepro.petticash.lastreceiptNumber.isless"));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		}
		
		isthere = ReceiptNumberHandler.getInstance().financialyearList(lastReceiptnumberForm);
		if(isthere)
		{
			errors.add("error", new ActionError("knowledgepro.petticash.EntryForYear.exists"));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);	
		}
		if (errors != null && !errors.isEmpty()) {
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			setFinancialYearList(lastReceiptnumberForm);
			saveErrors(request, errors);
			//if any space is entered in text box then, assigning as null.
			if(lastReceiptnumberForm.getLastReceiptNo().trim().isEmpty()){       
				lastReceiptnumberForm.setLastReceiptNo(null);
			}
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		}
			//validation checking for special characters
			final boolean isSpcl=nameValidate(lastReceiptnumberForm.getLastReceiptNo().trim()); 
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			
			isAdded = ReceiptNumberHandler.getInstance().addLastReceiptNumber(lastReceiptnumberForm, "Add");
		}
	
		catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.petticash.lastreceiptNumber.exists"));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.LASTRCEIPTNUMBER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		} catch (Exception e) {
			log.error("error in update state page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				lastReceiptnumberForm.setErrorMessage(msg);
				lastReceiptnumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
	if (isAdded) {
		// success .
		ActionMessage message = new ActionMessage(
				"knowledgepro.petticash.addsuccess", lastReceiptnumberForm.getLastReceiptNo());
		messages.add("messages", message);
		setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
		setFinancialYearList(lastReceiptnumberForm);
		lastReceiptnumberForm.clear();
		saveMessages(request, messages);
		lastReceiptnumberForm.reset(mapping, request);
	} else {
		// failed
		errors.add("error", new ActionError(
				"knowledgepro.petticash.addfailure",lastReceiptnumberForm.getLastReceiptNo()));
		saveErrors(request, errors);
	}
	log.debug("Leaving addLastReceiptNumber Action");
	return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	

	public ActionForward updateLastReceiptNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
											HttpServletResponse response) throws Exception {

		log.debug("inside updateLastReceiptNumber Action");
		LastReceiptNumberForm lastReceiptNumberForm = (LastReceiptNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = lastReceiptNumberForm.validate(mapping, request);
		boolean isCurrent = false;
		boolean isUpdated = false;
	
		try {
			if(Integer.parseInt(lastReceiptNumberForm.getLastReceiptNo())<= Integer.parseInt(lastReceiptNumberForm.getCheckValue())){
				errors.add("error", new ActionError("knowledgepro.petticash.lastreceiptNumber.isless"));
				saveErrors(request, errors);
				setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
				lastReceiptNumberForm.clear();
				return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
			}
			//checking year is present
			final boolean isSpcl=nameValidate(lastReceiptNumberForm.getLastReceiptNo().trim());  //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			isCurrent = ReceiptNumberHandler.getInstance().checkYearIsCurrent(lastReceiptNumberForm);
			if(isCurrent)
			{
				errors.add("error", new ActionError("knowledgepro.petticash.financialYear.isCureent"));
				saveErrors(request, errors);
				setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
				errors.clear();
				lastReceiptNumberForm.clear();
				return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);	
			}
			
			if (errors != null && !errors.isEmpty()) {
				setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
				setFinancialYearList(lastReceiptNumberForm);
				saveErrors(request, errors);
				if(lastReceiptNumberForm.getLastReceiptNo().trim().isEmpty()){
					lastReceiptNumberForm.setLastReceiptNo(null);
				}
				request.setAttribute("receiptOperation", "edit");
				return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
			}
			
			if (errors != null && !errors.isEmpty()) {
				setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
				setFinancialYearList(lastReceiptNumberForm);
				saveErrors(request, errors);
				if(lastReceiptNumberForm.getLastReceiptNo().trim().isEmpty()){
					lastReceiptNumberForm.setLastReceiptNo(null);
				}
				request.setAttribute("receiptOperation", "edit");
				return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
			}
			
			
			isUpdated = ReceiptNumberHandler.getInstance().addLastReceiptNumber(lastReceiptNumberForm, "Edit");
			//addLastReceiptNumber  method is using for add & edit. second param is used to identify add or edit
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.petticash.lastreceiptNumber.exists"));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.LASTRCEIPTNUMBER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in update state page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				lastReceiptNumberForm.setErrorMessage(msg);
				lastReceiptNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				lastReceiptNumberForm.setErrorMessage(msg);
				lastReceiptNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		setLastReceiptNumberToRequest(request,lastReceiptNumberForm);
		
	if (isUpdated) {
		// success .
		ActionMessage message = new ActionMessage("knowledgepro.petticash.updatesuccess", lastReceiptNumberForm.getLastReceiptNo());
		messages.add("messages", message);
		saveMessages(request, messages);
		lastReceiptNumberForm.reset(mapping, request);
	} else {
		// failed
		errors.add("error", new ActionError("knowledgepro.petticash.updatefailure", lastReceiptNumberForm.getLastReceiptNo()));
		saveErrors(request, errors);
	}
		return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	
	
	
	public ActionForward deletelastReceiptNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	log.debug("inside deleteLastReceiptNumber Action");
	LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = new ActionErrors();
	boolean isDeleted = false;
	try {
		if (lastReceiptnumberForm.getReceiptNumberId() != 0) {
			int receiptNoId = lastReceiptnumberForm.getReceiptNumberId();
			isDeleted = ReceiptNumberHandler.getInstance().deleteReceiptNumber(receiptNoId, false, lastReceiptnumberForm);
		}
	} catch (Exception e) {
		log.error("error in Delete Sub Religion Action", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			lastReceiptnumberForm.setErrorMessage(msg);
			lastReceiptnumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		else {
			throw e;
		}
	}
			setLastReceiptNumberToRequest(request,lastReceiptnumberForm);
		//	setFinancialYearList(lastReceiptnumberForm);
			if (isDeleted) {
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.petticash.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				lastReceiptnumberForm.reset(mapping, request);
			} else {
				// failure error message.
				errors.add("error", new ActionError("knowledgepro.petticash.deletefailure"));
				saveErrors(request, errors);
			}
			log.debug("leaving deleteLastReceiptNumber Action");
			
			return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	
	}
	
	public ActionForward activateLastreceiptNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

	log.debug("inside activateLastreceiptNumber in Action");
	LastReceiptNumberForm lastReceiptnumberForm = (LastReceiptNumberForm) form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	boolean isActivated = false;
	return mapping.findForward(CMSConstants.LAST_RECEIPT_NUMBER_DISPLAY);
	}
	public void setLastReceiptNumberToRequest(HttpServletRequest request,LastReceiptNumberForm lastReceiptNumberForm) throws Exception {
		log.debug("inside setLastReceiptNumberToRequest in Action");
		List<ReceiptNumberTo> receiptNumberList = ReceiptNumberHandler.getInstance().getReceiptNumberDetails(lastReceiptNumberForm);
		
		request.setAttribute("receiptNumberList", receiptNumberList);
		
		lastReceiptNumberForm.setReceiptNumberList(receiptNumberList);
		
		//int maxReceiptNumber = ReceiptNumberHandler.getInstance().getMaxReceiptNumber();
		//lastReceiptNumberForm.setCheckValue(String.valueOf(maxReceiptNumber));
		log.debug("leaving setLastReceiptNumberToRequest in Action");
	}*/
	public void setFinancialYearList(LastReceiptNumberForm receiptnumberForm) throws Exception
	{
		log.info("Start of setFinancialYearList of LastReceiptNumberAction");
		List<FinancialYearTO> finanicalyearList=null;
		finanicalyearList = LastrceiptNumberHandler.getInstance().getFinancialYearList();
		receiptnumberForm.setFinancilYearList(finanicalyearList);
		log.info("Existing setFinancialYearList of LastReceiptNumberAction");
	}
	public void assignListToForm (LastReceiptNumberForm receiptnumberForm) throws Exception
	{
		log.info("Start of assignListToForm of LastReceiptNumberAction");
		List<LastRceiptNumberTo> receiptNumberList = LastrceiptNumberHandler.getInstance().getReceiptNumberDetails();
		receiptnumberForm.setReceiptNumberList(receiptNumberList);
		log.info("Existing of assignListToForm of LastReceiptNumberAction");
	}
	/*private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}*/
	

}
