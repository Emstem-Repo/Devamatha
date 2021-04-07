package com.kp.cms.actions.attendance;

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
import com.kp.cms.forms.attendance.CopyPeriodsForm;
import com.kp.cms.handlers.attendance.CopyPeriodsHandler;
import com.kp.cms.to.attendance.ClassSchemewiseTO;

public class CopyPeriodsAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCopyPeriods(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CopyPeriodsForm copyPeriodsForm = (CopyPeriodsForm) form;
		try {
			copyPeriodsForm.clear();
			setUserId(request, copyPeriodsForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyPeriodsForm.setErrorMessage(msg);
			copyPeriodsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_COPY_PERIOD);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitCopyPeriods(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CopyPeriodsForm copyPeriodsForm = (CopyPeriodsForm) form;
		 ActionErrors errors = copyPeriodsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		validateYear(errors, copyPeriodsForm);
		setUserId(request, copyPeriodsForm);
		List<ClassSchemewiseTO> classSchemeWiseList = null;
		boolean isCopied = false;
		try {
			if (errors.isEmpty()) {
				if (copyPeriodsForm.getFromYear() != null
						&& !copyPeriodsForm.getFromYear().isEmpty()) {
					int fromYear = Integer.parseInt(copyPeriodsForm .getFromYear());
					int toYear = Integer.parseInt(copyPeriodsForm.getToYear());
					classSchemeWiseList = CopyPeriodsHandler.getInstance()
							.getClassesByYear(fromYear);
					isCopied = CopyPeriodsHandler.getInstance().savePeriods(
							classSchemeWiseList, toYear, copyPeriodsForm);
					if (isCopied) {
						// if adding is success
						ActionMessage message = new ActionMessage( CMSConstants.COPY_PERIOD_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						copyPeriodsForm.clear();
					} else {
						// if adding is failure
						ActionMessage message = new ActionMessage( CMSConstants.COPY_PERIOD_FAILURE);
						errors.add(CMSConstants.ERROR, message);
						addErrors(request, errors);
					}
				}
			} else {
				saveErrors(request, errors);
			}

		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyPeriodsForm.setErrorMessage(msg);
			copyPeriodsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_COPY_PERIOD);
	}

	/**
	 * @param errors
	 * @param copyPeriodsForm
	 */
	public void validateYear(ActionErrors errors,
			CopyPeriodsForm copyPeriodsForm) {
		if (copyPeriodsForm.getToYear() != null
				&& !copyPeriodsForm.getToYear().isEmpty()
				&& copyPeriodsForm.getFromYear() != null
				&& !copyPeriodsForm.getFromYear().isEmpty()) {
			if (Integer.parseInt(copyPeriodsForm.getToYear()) < Integer
					.parseInt(copyPeriodsForm.getFromYear())) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.validate.year"));
			}
			if (copyPeriodsForm.getToYear().equals(
					copyPeriodsForm.getFromYear())) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.validate.equalyear"));
			}
		}
	}
}
