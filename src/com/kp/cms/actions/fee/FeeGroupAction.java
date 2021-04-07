package com.kp.cms.actions.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.kp.cms.forms.fee.FeeGroupEntryForm;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.to.fee.FeeGroupTO;

/**
 * @author manjunatha.br A dispatch action manages add, edit, delete action of
 *         the Fee group entry.
 */
@SuppressWarnings("deprecation")

public class FeeGroupAction extends BaseDispatchAction {

	private static final Logger log=Logger.getLogger(FeeGroupAction.class);
	
	/**
	 * Initializes fee group entry screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeGroupEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of initFeeGroupEntry method in FeeGroupAction class.");
		FeeGroupEntryForm feeGroupEntryForm = (FeeGroupEntryForm) form;
		//setting fee group details to form.
		setDatatoForm(feeGroupEntryForm);
		feeGroupEntryForm.clearFields();
		request.setAttribute("submitbutton", "Add");
		request.setAttribute("method", "addFeeGroupEntry");
		log.info("end of initFeeGroupEntry method in FeeGroupAction class.");
		return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
	}

	/**
	 * Performs delete fee group entry action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFeeGroupEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of deleteFeeGroupEntry method in FeeGroupAction class.");
		FeeGroupEntryForm feeGroupEntryForm = (FeeGroupEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		try {
			setUserId(request, feeGroupEntryForm);
			FeeGroupHandler.getInstance().deleteFeeGroupEntry(
					Integer.parseInt(feeGroupEntryForm.getFeeGroupId()),
					feeGroupEntryForm.getUserId());
			setDatatoForm(feeGroupEntryForm);
			//if delete success.
			message = new ActionMessage(
					"knowledgepro.admission.feegroup.name.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		} catch (Exception exception) {

			return addExceptionMessage(mapping, feeGroupEntryForm, messages,
					exception);
		}
		log.info("end of deleteFeeGroupEntry method in FeeGroupAction class.");
		return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
	}

	/**
	 * Performs edit fee group entry action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFeeGroupEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of editFeeGroupEntry method in FeeGroupAction class.");
		FeeGroupEntryForm feeGroupEntryForm = (FeeGroupEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = feeGroupEntryForm.validate(mapping, request);
		ActionMessage message = null;
		if (errors.isEmpty()) {
			setUserId(request, feeGroupEntryForm);
			try {
				if (!feeGroupEntryForm.getFeeGroupNameOriginal().equals(
						feeGroupEntryForm.getFeeGroupName())
						|| !feeGroupEntryForm.getOriginalOptional()
								.equalsIgnoreCase(
										feeGroupEntryForm.getOptional())) {

					FeeGroupHandler.getInstance().updateFeeGroupEntry(
							Integer.valueOf(feeGroupEntryForm.getFeeGroupId()),
							feeGroupEntryForm.getFeeGroupName(),
							feeGroupEntryForm.getOptional(),
							feeGroupEntryForm.getFeeGroupNameOriginal(),
							feeGroupEntryForm.getUserId());
				}
				//setting fee group details to form.
				setDatatoForm(feeGroupEntryForm);
				//if update is success.
				message = new ActionMessage(
						"knowledgepro.admission.feegroup.name.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				request.setAttribute("submitbutton", "Add");
				request.setAttribute("method", "addFeeGroupEntry");
				feeGroupEntryForm.clearFields();
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.feegroup.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(feeGroupEntryForm);
				request.setAttribute("admOperation", "editFeeGroupEntry");
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (ReActivateException reActivateException) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.admission.feegroup.name.alreadyexist.reactivate"));
				saveErrors(request, errors);
				//setting fee group details to form.
				setDatatoForm(feeGroupEntryForm);
				request.setAttribute("admOperation", "editFeeGroupEntry");
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, feeGroupEntryForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			setDatatoForm(feeGroupEntryForm);
			log.info("end of editFeeGroupEntry method in FeeGroupAction class.");
			return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
		}
	}

	/**
	 * Performs add fee group entry action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFeeGroupEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of addFeeGroupEntry method in FeeGroupAction class.");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		FeeGroupEntryForm feeGroupEntryForm = (FeeGroupEntryForm) form;
		errors = feeGroupEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, feeGroupEntryForm);
				FeeGroupHandler.getInstance().addFeeGroup(feeGroupEntryForm);
				//setting user id to form.
				setDatatoForm(feeGroupEntryForm);
				//if adding is success.
				message = new ActionMessage(
						"knowledgepro.admission.feegroup.name.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				feeGroupEntryForm.clearFields();
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.feegroup.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(feeGroupEntryForm);
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (ReActivateException reActivateException) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.admission.feegroup.name.alreadyexist.reactivate"));
				saveErrors(request, errors);

				setDatatoForm(feeGroupEntryForm);
				return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, feeGroupEntryForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			setDatatoForm(feeGroupEntryForm);
			log.info("end of addFeeGroupEntry method in FeeGroupAction class.");
			return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
		}

	}

	/**
	 * Performs reactivate fee group entry action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateFeeGroupEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of reactivateFeeGroupEntry method in FeeGroupAction class.");
		FeeGroupEntryForm feeGroupEntryForm = (FeeGroupEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		try {
			setUserId(request, feeGroupEntryForm);
			FeeGroupHandler.getInstance().reActivateFeeGroup(
					feeGroupEntryForm.getFeeGroupName(),
					feeGroupEntryForm.getUserId());
			setDatatoForm(feeGroupEntryForm);
			feeGroupEntryForm.clearFields();
			request.setAttribute("submitbutton", "Add");
			request.setAttribute("method", "addFeeGroupEntry");
			message = new ActionMessage(
					"knowledgepro.admission.feegroup.name.reactivatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			log.info("end of reactivateFeeGroupEntry method in FeeGroupAction class.");
			return mapping.findForward(CMSConstants.FEE_GROUP_ENTRY);
		} catch (Exception exception) {

			return addExceptionMessage(mapping, feeGroupEntryForm, messages,
					exception);
		}

	}

	/**
	 * Maps to exception/error page
	 * 
	 * @param mapping
	 * @param feeGroupEntryForm
	 * @param messages
	 * @param exception
	 * @return
	 * @throws Exception
	 */
	private ActionForward addExceptionMessage(ActionMapping mapping,
			FeeGroupEntryForm feeGroupEntryForm, ActionMessages messages,
			Exception exception) throws Exception {
		if (exception instanceof BusinessException) {
			String msgKey = super.handleBusinessException(exception);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			feeGroupEntryForm.setErrorMessage(msg);
			feeGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw exception;
		}
	}

	/**
	 * Sets required data to the request scope.
	 * 
	 * @param feeGroupEntryForm
	 * @throws Exception
	 */
	private void setDatatoForm(FeeGroupEntryForm feeGroupEntryForm)
			throws Exception {
		log.info("call of setDatatoForm method in FeeGroupAction class.");
		//used to get the data from Fee group table and setting to list of type FeeGroupTO.
		List<FeeGroupTO> feeGroupTo = FeeGroupHandler.getInstance()
				.getFeeGroups();
		//setting list to form.
		feeGroupEntryForm.setFeeGroupToList(feeGroupTo);
		log.info("end of setDatatoForm method in FeeGroupAction class.");
	}
}