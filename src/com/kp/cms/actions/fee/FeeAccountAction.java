package com.kp.cms.actions.fee;

import java.util.List;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAccountForm;
import com.kp.cms.handlers.fee.FeeAccountHandler;
import com.kp.cms.to.fee.FeeAccountTO;

@SuppressWarnings("deprecation")
public class FeeAccountAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(FeeAccountAction.class);
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return To fetch the total account entries from the database
	 * @throws Exception
	 */
	
	public ActionForward initAccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		try
		{
		log.debug("Entering initStudentGroup ");
		log.debug("Leaving initStudentGroup ");
		setListToRequest(request,feeAccountForm);
		}catch (Exception e) {
			log.error("error in loading feeaccounts...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAccountForm.setErrorMessage(msg);
				feeAccountForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
	return mapping.findForward("feeaccountEntry");	
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward addFeeAccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		errors = feeAccountForm.validate(mapping, request);
		String accountname = feeAccountForm.getCode();
		String mode="Add";
		boolean isAdded=false;
		try
		{
			setUserId(request, feeAccountForm);
			if(isCancelled(request))
			{
				feeAccountForm.reset(mapping, request);
				setListToRequest(request,feeAccountForm);
				return mapping.findForward("feeaccountEntry");
			}
			if (feeAccountForm.getBankInfo() != null && feeAccountForm.getBankInfo().length() > 500) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_BANK_INFORMATION_CONTENTFAIL));
				saveErrors(request, errors);
			}
			if(errors.isEmpty())
			{
				isAdded = FeeAccountHandler.getInstance().addFeeAccount(
						feeAccountForm,mode);
				if (isAdded) 
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.feeaccount.addsuccess",
							accountname);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeAccountForm.reset(mapping, request);
				} else {
					errors.add("error", new ActionError("knowledgepro.admin.feeaccount.addfail",
							accountname));
					saveErrors(request,errors);
				}
			}else
			{
				saveErrors(request,errors);
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.feeaccount.addexist", accountname));
			saveErrors(request,errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.feeaccount.addexist.reactivate", accountname));
			saveErrors(request,errors);
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAccountForm.setErrorMessage(msg);
				feeAccountForm.setErrorStack(e.getMessage());
				
			} else {
				throw e;
			}
			
		}
		if(!errors.isEmpty())
		{
			setListToRequest(request,feeAccountForm);
			saveErrors(request,errors);
		}
		setListToRequest(request,feeAccountForm);
		return mapping.findForward("feeaccountEntry");
	}
	
	/**
	 * 
	 * @param request
	 * @param accountForm
	 * @throws Exception
	 */
	
	public void setListToRequest(HttpServletRequest request,ActionForm accountForm)throws Exception
	{
		FeeAccountForm feeAccountForm=(FeeAccountForm)accountForm;
		/*List<FeeDivisionTO> feeDivisionList = FeeDivisionHandler.getInstance()
		.getFeeDivisionList();
		request.setAttribute("feeDivisionList", feeDivisionList);*/
		List<FeeAccountTO> feeAccountList = FeeAccountHandler.getInstance()
		.getfeeAccounts();// loading subjects from database
		feeAccountForm.setFeeAccountList(feeAccountList);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward editFeeaccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		String mode="Edit";
		feeAccountForm=(FeeAccountForm)FeeAccountHandler.getInstance().getUpdatableForm(feeAccountForm,mode);
		setListToRequest(request,feeAccountForm);
		request.setAttribute("feeAccountOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward("feeaccountEntry");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward updateFeeAccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		validateMandatoryFields(feeAccountForm,errors);
//		errors = feeAccountForm.validate(mapping, request);
		String accountname = feeAccountForm.getCode();
		String mode="Update";
		boolean isUpdate=false;
		try
		{
			setUserId(request, feeAccountForm);
			if(isCancelled(request))
			{
				mode="Edit";
				feeAccountForm=(FeeAccountForm)FeeAccountHandler.getInstance().getUpdatableForm(feeAccountForm,mode);
				setListToRequest(request,feeAccountForm);
				request.setAttribute("Update", "Update");
				return mapping.findForward("feeaccountEntry");
			}
			if (feeAccountForm.getBankInfo() != null && feeAccountForm.getBankInfo().length() > 500) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_BANK_INFORMATION_CONTENTFAIL));
				saveErrors(request, errors);
			}
			if(errors.isEmpty())
			{
				isUpdate = FeeAccountHandler.getInstance().addFeeAccount(
						feeAccountForm,mode);
				if (isUpdate) {
					
					ActionMessage message = new ActionMessage("knowledgepro.admin.feeaccount.updatesuccess",
							accountname);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeAccountForm.reset(mapping, request);
				} else {
					errors.add("error", new ActionError("knowledgepro.admin.feeaccount.updatefail",
							accountname));
					saveErrors(request,errors);
				}
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.feeaccount.addexist", accountname));
			saveErrors(request,errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.feeaccount.addexist.reactivate"));
			saveErrors(request,errors);
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAccountForm.setErrorMessage(msg);
				feeAccountForm.setErrorStack(e.getMessage());
				
			} else {
				throw e;
			}
			
		}
		if(!errors.isEmpty())
		{
			setListToRequest(request,feeAccountForm);
			saveErrors(request,errors);
			request.setAttribute("feeAccountOperation", "edit");
			request.setAttribute("Update", "Update");
		}
		setListToRequest(request,feeAccountForm);
		return mapping.findForward("feeaccountEntry");
	}
	
	private ActionErrors validateMandatoryFields(FeeAccountForm feeAccountForm,
			ActionErrors errors) {
		log.info("entering into validateMandatoryFields in FeeAccountAction class..");
		if (errors == null)
			errors = new ActionErrors();
		
		if(StringUtils.isEmpty(feeAccountForm.getName())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.adimin.feeaccount.name.required"));
		}
		if(StringUtils.isEmpty(feeAccountForm.getCode())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.adimin.feeaccount.code.required"));
		}
		if(StringUtils.isEmpty(feeAccountForm.getPrintAccName())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.adimin.feeaccount.printaccountname.required"));
		}
		if(StringUtils.isEmpty(feeAccountForm.getPosition())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.fee.feeaccount.printposition.required"));
		}
		
		log.info("exit of validateMandatoryFields in FeeAccountAction class..");	
		return errors;
		
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward deleteFeeAccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		errors = feeAccountForm.validate(mapping, request);
		String mode="Delete";
		boolean isDelete=false;
		try
		{
			setUserId(request, feeAccountForm);
			isDelete = FeeAccountHandler.getInstance().addFeeAccount(
					feeAccountForm,mode);	
			if (isDelete) {
				
				ActionMessage message = new ActionMessage("knowledgepro.admin.feeaccount.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				feeAccountForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.feeaccount.deletefail"
						));
				saveErrors(request,errors);
			}
		
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAccountForm.setErrorMessage(msg);
				feeAccountForm.setErrorStack(e.getMessage());
				
			} else {
				throw e;
			}
			
		}
		setListToRequest(request,feeAccountForm);
		return mapping.findForward("feeaccountEntry");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward reactivateFeeAccount(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		errors = feeAccountForm.validate(mapping, request);
		String mode="Reactivate";
		boolean isactivate=false;
		try
		{
			setUserId(request, feeAccountForm);
			isactivate = FeeAccountHandler.getInstance().addFeeAccount(
					feeAccountForm,mode);	
			if (isactivate) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.feeaccount.activated");
				messages.add("messages", message);
				saveMessages(request, messages);
				feeAccountForm.reset(mapping, request);
			} 
		
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAccountForm.setErrorMessage(msg);
				feeAccountForm.setErrorStack(e.getMessage());
				
			} else {
				throw e;
			}
			
		}
		setListToRequest(request,feeAccountForm);
		return mapping.findForward("feeaccountEntry");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward viewFeeAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeAccountForm feeAccountForm = (FeeAccountForm)form;
		String mode="Edit";
		feeAccountForm=(FeeAccountForm)FeeAccountHandler.getInstance().getUpdatableForm(feeAccountForm,mode);
		return mapping.findForward("viewFeeaccountEntry");
	}
	
}