package com.kp.cms.actions.pettycash;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.pettycash.PcAccountEntryForm;
import com.kp.cms.handlers.pettycash.PcAccountEntryHandler;
import com.kp.cms.to.pettycash.PcAccountTo;

public class PcAccountEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PcAccountEntryAction.class);
	/**
	 * Performs the get PcAccountEntryAction.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward initPcAccountEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		try{
			accountEntryForm.reset();
			setRequestedDataToForm(accountEntryForm);
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				accountEntryForm.setErrorMessage(msg);
				accountEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);
	}
	private void setRequestedDataToForm(PcAccountEntryForm accountEntryForm) throws Exception {
		List<PcAccountTo> accList=PcAccountEntryHandler.getInstance().getAccountList();
		accountEntryForm.setAccList(accList);
		
	}
	/**
	 * Performs the add PcAccountEntryAction.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward addPcAccountEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = accountEntryForm.validate(mapping, request);
		errors = validateImageSize(accountEntryForm, errors, request);	
		boolean isCasteAdded = false;
		if (errors.isEmpty()) {
			try{
			isCasteAdded = PcAccountEntryHandler.getInstance().addPcAccount(accountEntryForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.exist"));
					saveErrors(request, errors);
					setRequestedDataToForm(accountEntryForm);
					return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
				}
				if(e instanceof ReActivateException){
					String id=request.getAttribute("accountId").toString();
					errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.reactivate",id));
					saveErrors(request, errors);
					setRequestedDataToForm(accountEntryForm);
					return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				accountEntryForm.setErrorMessage(msg);
				accountEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isCasteAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.pettycash.account.entry.accountNo.add.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				accountEntryForm.reset();
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.add.failure"));
				saveErrors(request, errors);
						}
		} else {
			saveErrors(request, errors);
		}
		
		setRequestedDataToForm(accountEntryForm);	
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
	}

	/**
	 * Performs the update PcAccountEntryAction.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward updatePcAccountEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		if (isCancelled(request)) {
			String bankAccid=accountEntryForm.getAccountId();
			PcAccountTo accountTo = PcAccountEntryHandler.getInstance().getPcBankAccDetailsWithId(bankAccid);
			if (accountTo != null) {
				accountEntryForm.setAccountNo(accountTo.getAccountNo());
				accountEntryForm.setIsPhoto(accountTo.getIsPhoto());
				accountEntryForm.setAccountTo(accountTo);
				accountEntryForm.setAccountId(Integer.toString(accountTo.getId()));
			}
			setRequestedDataToForm(accountEntryForm);
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
		}
		errors = validateFormForUpdate(accountEntryForm,errors);
		errors = validateImageSize(accountEntryForm, errors, request);	
		boolean isAccountEdited = false;
		if (errors.isEmpty()) {
			try{
				setUserId(request, accountEntryForm);
				if(accountEntryForm.getAccountNo().equals(accountEntryForm.getAccountTo().getAccountNo()) && accountEntryForm.getThefile().getFileData().length<=0){
					isAccountEdited=true;
				}else{
				isAccountEdited=PcAccountEntryHandler.getInstance().updatePcAccount(accountEntryForm,request);
				}
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.exist"));
					saveErrors(request, errors);
					setRequestedDataToForm(accountEntryForm);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
				}
				if(e instanceof ReActivateException){
					String id=request.getAttribute("accountId").toString();
					errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.reactivate",id));
					saveErrors(request, errors);
					setRequestedDataToForm(accountEntryForm);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				accountEntryForm.setErrorMessage(msg);
				accountEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			setRequestedDataToForm(accountEntryForm);
			return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
		}
		
		
		if (isAccountEdited) {
			ActionMessage message = new ActionMessage("knowledgepro.pettycash.account.entry.accountNo.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			accountEntryForm.reset();
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.update.failure"));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(accountEntryForm);
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
	}

	private ActionErrors validateFormForUpdate(PcAccountEntryForm accountEntryForm,ActionErrors errors) throws Exception {
		String accountNo=accountEntryForm.getAccountNo();
		if(accountNo.equals(null) || accountNo.equals(""))
		{
			errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.required"));
		}
		if(accountEntryForm.getThefile().getFileData().length<=0 && accountEntryForm.getAccountTo().getLogo()==null )
		{
			errors.add("error", new ActionError("knowledgepro.fee.feeaccount.logo.required"));
		}
		return errors;
	}
	/**
	 * Performs the delete PcBankAccount action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deletePcAccountEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isCasteDeleted=false;
		try{
			setUserId(request, accountEntryForm);
			String bankAccid=accountEntryForm.getAccountId();
			isCasteDeleted = PcAccountEntryHandler.getInstance().deletePcBankAccNo(bankAccid,accountEntryForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			accountEntryForm.setErrorMessage(msg);
			accountEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isCasteDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.pettycash.account.entry.accountNo.delete.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			accountEntryForm.reset();
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.delete.failure"));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(accountEntryForm);
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
	}
	
	/**
	 * Performs the reActivate Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward reActivatePcBankAccNO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isCasteReActivate=false;
		try{
			String bankAccid=accountEntryForm.getAccountId();
			setUserId(request, accountEntryForm);
			isCasteReActivate = PcAccountEntryHandler.getInstance().reActivateBankAccNo(bankAccid,accountEntryForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			accountEntryForm.setErrorMessage(msg);
			accountEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isCasteReActivate) {
			ActionMessage message = new ActionMessage("knowledgepro.pettycash.account.entry.accountNo.active.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			accountEntryForm.reset();
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.pettycash.account.entry.accountNo.active.failure"));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(accountEntryForm);
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);	
	}
	/**
	 * Used to edit PCFinancialYear
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward editPCFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editPCFinancialYearDetails in PCFinancialYearEntryAction class");
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		try {
			String bankAccid=accountEntryForm.getAccountId();
			PcAccountTo accountTo = PcAccountEntryHandler.getInstance().getPcBankAccDetailsWithId(bankAccid);
			if (accountTo != null) {
				accountEntryForm.setAccountId(Integer.toString(accountTo.getId()));
				accountEntryForm.setAccountNo(accountTo.getAccountNo());
				accountEntryForm.setIsPhoto(accountTo.getIsPhoto());
				accountEntryForm.setAccountTo(accountTo);
				}
			setRequestedDataToForm(accountEntryForm);
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			accountEntryForm.setErrorMessage(msg);
			accountEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of editPCFinancialYearDetails in PCAccountEntry class");
		return mapping.findForward(CMSConstants.INIT_PCACCOUNT_ENTRY);
	}
	
	/**
	 * Validates the uploaded file size.
	 * 
	 * @param Checks for the uploaded file and it allows the user to upload maximum of 2MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validateImageSize(PcAccountEntryForm accountEntryForm,
			ActionErrors errors, HttpServletRequest request)throws Exception {
		log.info("Entering into validateImageSize of RomType Action");
		FormFile theFile = null;
		if(accountEntryForm.getThefile()!=null){
			theFile = accountEntryForm.getThefile();
		}
		InputStream propStream=PcAccountEntryAction.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error occured in validateImageSize RomType Action",e);
			 throw new ApplicationException();
		}		 
			if(theFile!=null && maXSize< theFile.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE));
				addErrors(request, errors);
			}
			log.info("Leaving into validateImageSize of RomType Action");
			return errors;
	}
	
}
