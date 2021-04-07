package com.kp.cms.actions.admin;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.admin.CertificatePurposeForm;
import com.kp.cms.handlers.admin.CertificatePurposeHandler;
import com.kp.cms.to.admin.CertificatePurposeTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertificatePurposeAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(CertificatePurposeAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		CertificatePurposeForm certificatePurposeForm=(CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		certificatePurposeForm.reset(mapping, request);
		try{
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setRequestedDateToForm(certificatePurposeForm,request);
			
		}catch (Exception e) {
			log.error("error in initCertificatePurpose...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificatePurposeForm.setErrorMessage(msg);
				certificatePurposeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
	}

	/**
	 * @param certificatePurposeForm
	 * @param request 
	 * @throws Exception
	 */
	private void setRequestedDateToForm(CertificatePurposeForm certificatePurposeForm, HttpServletRequest request) throws Exception {
		log.debug("inside setRequestedDateToForm: Action");
		List<CertificatePurposeTO> certificatePurpose= CertificatePurposeHandler.getInstance().getPurposeFields();
		certificatePurposeForm.setPurposeList(certificatePurpose);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificatePurposeForm certificatePurposeForm = (CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=certificatePurposeForm.validate(mapping, request);
		boolean isAdded = false;
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(certificatePurposeForm,request);
				//if space is entered then no need to save
				if(certificatePurposeForm.getPurposeName().trim().isEmpty()){
					certificatePurposeForm.setPurposeName(null);
				}
				return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
			}
			/*boolean isSpecial=nameValidate(certificatePurposeForm.getName().trim()); //validation checking for special characters
			if(isSpecial)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}*/
			
			isAdded=CertificatePurposeHandler.getInstance().addCertificatePurpose(certificatePurposeForm,"Add");
			setRequestedDateToForm(certificatePurposeForm,request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.name.exists"));
			saveErrors(request, errors);
			setRequestedDateToForm(certificatePurposeForm,request);
			return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setRequestedDateToForm(certificatePurposeForm,request);
			return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
		} catch (Exception e) {
			log.error("error in final submit of purpose page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificatePurposeForm.setErrorMessage(msg);
				certificatePurposeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		if(isAdded){
			ActionMessage message = new ActionError("knowledgepro.admin.Purpose.addsuccess",certificatePurposeForm.getPurposeName());
			messages.add("messages", message);
			saveMessages(request, messages);
			certificatePurposeForm.reset(mapping, request);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.addfailure",certificatePurposeForm.getPurposeName()));
			saveErrors(request, errors);
		}
		
		log.debug("Leaving purpose Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
	}

	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher m = p.matcher(name);
        result = m.find();
        return result;

	}
	
	
	public ActionForward editCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		CertificatePurposeForm certificatePurposeForm = (CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		ActionMessages messages = new ActionMessages();
		try {
			CertificatePurposeHandler.getInstance().editCertificatePurpose(certificatePurposeForm);
				request.setAttribute("purpose", "edit");
			
		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			certificatePurposeForm.setErrorMessage(msg);
			certificatePurposeForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		setRequestedDateToForm(certificatePurposeForm,request);
		log.info("end of editMenus method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
		}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificatePurposeForm certificatePurposeForm = (CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=certificatePurposeForm.validate(mapping, request);
		boolean isUpdate = false;
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(certificatePurposeForm,request);
				//if space is entered then no need to save
				if(certificatePurposeForm.getPurposeName().trim().isEmpty()){
					certificatePurposeForm.setPurposeName(null);
				}
				request.setAttribute("purpose", "edit");
				return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
			}
			//boolean isSpecial=nameValidate(certificatePurposeForm.getName().trim()); //validation checking for special characters
			//if(isSpecial)
			//{
			//	errors.add("error", new ActionError("knowledgepro.admin.special"));
			//}
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(certificatePurposeForm,request);
				//if space is entered then no need to save
				if(certificatePurposeForm.getPurposeName().trim().isEmpty()){
					certificatePurposeForm.setPurposeName(null);
				}
				request.setAttribute("purpose", "edit");
				return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
			}
			isUpdate=CertificatePurposeHandler.getInstance().addCertificatePurpose(certificatePurposeForm,"Edit");
			setRequestedDateToForm(certificatePurposeForm,request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.name.exists"));
			saveErrors(request, errors);
			setRequestedDateToForm(certificatePurposeForm,request);
			request.setAttribute("purpose", "edit");
			return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setRequestedDateToForm(certificatePurposeForm,request);
			request.setAttribute("purpose", "edit");
			return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
		} catch (Exception e) {
			log.error("error in update admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("purpose", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificatePurposeForm.setErrorMessage(msg);
				certificatePurposeForm.setErrorStack(e.getMessage());
				request.setAttribute("purpose", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isUpdate){
			ActionMessage actionMessage = new ActionMessage("knowledgepro.admin.Purpose.updatesuccess",certificatePurposeForm.getPurposeName());
			messages.add("messages", actionMessage);
			saveMessages(request, messages);
			certificatePurposeForm.reset(mapping, request);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Purpose.updatefailure",certificatePurposeForm.getPurposeName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving updateCertificatePurpose Action");
		request.setAttribute("purpose", "add");
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificatePurposeForm certificatePurposeForm = (CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isDeleted=false;
		try{
			if(certificatePurposeForm.getId()!=0){
				int purposeId=certificatePurposeForm.getId();
				isDeleted=CertificatePurposeHandler.getInstance().deleteCertificatePurpose(purposeId,false,certificatePurposeForm);
			}
		}catch (Exception e) {
			log.error("error in delete CertificatePurpose page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificatePurposeForm.setErrorMessage(msg);
				certificatePurposeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setRequestedDateToForm(certificatePurposeForm,request);
		if(isDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.admin.Purpose.deletesuccess",certificatePurposeForm.getPurposeName());
			messages.add("messages",message);
			saveMessages(request, messages);
			certificatePurposeForm.reset(mapping, request);
		}else{
			errors.add("error",new ActionError("knowledgepro.admin.Purpose.deletefailure",certificatePurposeForm.getPurposeName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving deleteCertificatePurpose Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateCertificatePurpose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificatePurposeForm certificatePurposeForm = (CertificatePurposeForm)form;
		setUserId(request, certificatePurposeForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivate = false;
		try{
			if(certificatePurposeForm.getDupId()!=0){
				int purposeId = certificatePurposeForm.getDupId();
				isActivate=CertificatePurposeHandler.getInstance().deleteCertificatePurpose(purposeId, true, certificatePurposeForm);
			}
		}catch (Exception e) {
			log.error("error in delete CertificatePurpose page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificatePurposeForm.setErrorMessage(msg);
				certificatePurposeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setRequestedDateToForm(certificatePurposeForm,request);
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.admin.Purpose.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
		}
		else{
			errors.add("error", new ActionError("Knowledgepro.admin.Purpose.activate.failure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving activateCertificatePurpose Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_PURPOSE);
	}
}
