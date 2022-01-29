package com.kp.cms.actions.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.fee.FeeDivisionEntryForm;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.to.fee.FeeDivisionTO;

/**
 * A dispatch action manages add, edit, delete action of the Fee division entry.
 * @author
 * 
 */
@SuppressWarnings("deprecation")

public class FeeDivisionAction extends BaseDispatchAction {

	/**
	 * Initializes fee division entry screen.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeDivisionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FeeDivisionEntryForm feeDivisionEntryForm = (FeeDivisionEntryForm) form;
		setDatatoForm(feeDivisionEntryForm);
		request.setAttribute("submitbutton", "Add");
		setUserId(request, feeDivisionEntryForm); //setting userId for updating last changed details
		feeDivisionEntryForm.setMethod("addFeeDivisionEntry");
		return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
	}

	/**
	 * Performs delete fee division entry action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFeeDivisionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FeeDivisionEntryForm feeDivisionEntryForm = (FeeDivisionEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		try {
			FeeDivisionHandler.getInstance().deleteFeeDivisionEntry(
					Integer.parseInt(feeDivisionEntryForm.getDivisionId()));
			setDatatoForm(feeDivisionEntryForm);
			message = new ActionMessage(
					"knowledgepro.admission.division.name.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		} catch (Exception exception) {

			return addExceptionMessage(mapping, feeDivisionEntryForm, messages,
					exception);
		}

		return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
	}

	/**
	 * Performs edit fee division entry action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFeeDivisionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FeeDivisionEntryForm feeDivisionEntryForm = (FeeDivisionEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = feeDivisionEntryForm.validate(mapping, request);
		ActionMessage message = null;
		if (errors.isEmpty()) {
			try {

				if (!feeDivisionEntryForm.getDivisionNameOriginal().equals(
						feeDivisionEntryForm.getDivisionName())) {
					setUserId(request, feeDivisionEntryForm); //setting userId for updating last changed details
					FeeDivisionHandler.getInstance().updateFeeDivisionEntry(
							Integer.valueOf(feeDivisionEntryForm
									.getDivisionId()),
							feeDivisionEntryForm.getDivisionName(),
							feeDivisionEntryForm.getDivisionNameOriginal(), feeDivisionEntryForm.getUserId());
				}

				setDatatoForm(feeDivisionEntryForm);

				message = new ActionMessage(
						"knowledgepro.admission.division.name.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				request.setAttribute("submitbutton", "Add");
				request.setAttribute("method", "addFeeDivisionEntry");
				feeDivisionEntryForm.clearFields();
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.division.name.exists"));
				saveErrors(request, errors);
				request.setAttribute("admOperation", "editFeeDivisionEntry");

				setDatatoForm(feeDivisionEntryForm);
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (ReActivateException reActivateException) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.admission.division.name.alreadyexist.reactivate"));
				saveErrors(request, errors);
				request.setAttribute("admOperation", "editFeeDivisionEntry");
				setDatatoForm(feeDivisionEntryForm);
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, feeDivisionEntryForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			setDatatoForm(feeDivisionEntryForm);
			return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);

		}

	}

	/**
	 * Performs add fee division entry action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFeeDivisionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		FeeDivisionEntryForm feeDivisionEntryForm = (FeeDivisionEntryForm) form;
		errors = feeDivisionEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, feeDivisionEntryForm); //setting userId for updating last changed details
				FeeDivisionHandler.getInstance().addFeeDivision(
						feeDivisionEntryForm);
				setDatatoForm(feeDivisionEntryForm);
				message = new ActionMessage(
						"knowledgepro.admission.division.name.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				feeDivisionEntryForm.clearFields();
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.division.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(feeDivisionEntryForm);
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (ReActivateException reActivateException) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.admission.division.name.alreadyexist.reactivate"));
				saveErrors(request, errors);

				setDatatoForm(feeDivisionEntryForm);
				return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, feeDivisionEntryForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			setDatatoForm(feeDivisionEntryForm);
			return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
		}

	}

	/**
	 * Performs reactivate fee division entry action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateFeeDivisionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FeeDivisionEntryForm feeDivisionEntryForm = (FeeDivisionEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		try {

			FeeDivisionHandler.getInstance().reActivateFeeDivision(
					feeDivisionEntryForm.getDivisionName());
			setDatatoForm(feeDivisionEntryForm);
			feeDivisionEntryForm.clearFields();
			request.setAttribute("submitbutton", "Add");
			request.setAttribute("method", "addFeeDivisionEntry");
			message = new ActionMessage(
					"knowledgepro.admission.division.name.reactivatesuccessty");
			messages.add("messages", message);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.FEE_DIVISION_ENTRY);
		} catch (Exception exception) {

			return addExceptionMessage(mapping, feeDivisionEntryForm, messages,
					exception);
		}

	}

	/**
	 * Maps to the exception/error page.
	 * @param mapping
	 * @param feeDivisionEntryForm
	 * @param messages
	 * @param exception
	 * @return
	 * @throws Exception
	 */
	private ActionForward addExceptionMessage(ActionMapping mapping,
			FeeDivisionEntryForm feeDivisionEntryForm, ActionMessages messages,
			Exception exception) throws Exception {
		if (exception instanceof BusinessException) {
			String msgKey = super.handleBusinessException(exception);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			feeDivisionEntryForm.setErrorMessage(msg);
			feeDivisionEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw exception;
		}
	}

	/**
	 * Sets required data to the request scope.
	 * @param feeDivisionEntryForm
	 * @throws Exception
	 */
	private void setDatatoForm(FeeDivisionEntryForm feeDivisionEntryForm)
			throws Exception {
		List<FeeDivisionTO> feeDivisionTo = FeeDivisionHandler.getInstance()
				.getFeeDivisionList();
		feeDivisionEntryForm.setFeeDivisionToList(feeDivisionTo);

	}

}
