package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamFooterAgreementForm;
import com.kp.cms.handlers.exam.ExamFooterAgreementHandler;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamFooterAgreementAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamFooterAgreementHandler handler = new ExamFooterAgreementHandler();

	// Initilization of action
	public ActionForward initExamFooterAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamFooterAgreementForm objform = (ExamFooterAgreementForm) form;
		objform.setMainList(handler.init());
		objform.setProgramTypeList(handler.getProgramTypeList());

		return mapping.findForward(CMSConstants.EXAM_FOOTER_AGREEMENT);
	}

	// On SAVE
	public ActionForward createFooterAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamFooterAgreementForm objform = (ExamFooterAgreementForm) form;
		String templateString = request
				.getParameter(CMSConstants.EDITOR_DEFAULT);
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		if (errors.isEmpty()) {
			errors = validate(templateString, objform.getName(), objform
					.getDate());
		}
		saveErrors(request, errors);
		setUserId(request, objform);

		try {
			if (errors.isEmpty()) {

				handler.add(objform.getName(), objform.getTypeId(), objform
						.getSelectedProgramType(), templateString, objform
						.getDate(), objform.getUserId(), objform.getHalTcktOrMarksCard(),objform.getAcademicYear());
				objform.setProgramName(handler
						.getProgramNameByProgramId(Integer.parseInt(objform
								.getSelectedProgramType())));
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.footerAggreement.added");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.name.exists"));
			saveErrors(request, errors);
		} catch (Exception e) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.addFailure"));
			saveErrors(request, errors);
		} finally {
			objform.setMainList(handler.init());
			objform.setProgramTypeList(handler.getProgramTypeList());

		}

		return mapping.findForward(CMSConstants.EXAM_FOOTER_AGREEMENT);
	}

	// On EDIT
	public ActionForward updateFooterAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamFooterAgreementForm objform = (ExamFooterAgreementForm) form;
		String templateString = request
				.getParameter(CMSConstants.EDITOR_DEFAULT);
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		if (errors.isEmpty()) {
			errors = validate(templateString, objform.getName(), objform
					.getDate());
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		try {
			if (errors.isEmpty()) {
				handler.update(objform.getId(), objform.getName(), objform
						.getTypeId(), objform.getSelectedProgramType(),
						templateString, objform.getDate(), objform.getUserId(), objform.getHalTcktOrMarksCard(),objform.getAcademicYear() );
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.footerAggreement.updated");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			} else {
				objform = handler.getUpdatableForm(objform);
				request.setAttribute("operation", "edit");
				request.setAttribute("Update", "Update");

			}

		} catch (DuplicateException e1) {
			errors.clear();
			messages.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.name.exists"));
			saveErrors(request, errors);
			objform = handler.getUpdatableForm(objform);
			request.setAttribute("operation", "edit");
			request.setAttribute("Update", "Update");

		} catch (Exception e) {
			errors.clear();
			messages.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.updateFailure"));
			saveErrors(request, errors);
			objform = handler.getUpdatableForm(objform);
			request.setAttribute("operation", "edit");
			request.setAttribute("Update", "Update");
		} finally {
			objform.setMainList(handler.init());
			objform.setProgramTypeList(handler.getProgramTypeList());
		}
		return mapping.findForward(CMSConstants.EXAM_FOOTER_AGREEMENT);
	}

	// On DELETE
	public ActionForward deleteFooterAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamFooterAgreementForm objform = (ExamFooterAgreementForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);

		try {
			handler.delete(objform.getId(), objform.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.footerAggreement.delete");
			messages.add("messages", message);
			saveMessages(request, messages);
		} catch (Exception e) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.deleteFailure"));
			saveErrors(request, errors);
		} finally {
			objform.setMainList(handler.init());
			objform.setProgramTypeList(handler.getProgramTypeList());
		}
		return mapping.findForward(CMSConstants.EXAM_FOOTER_AGREEMENT);
	}

	// Updatable Form
	public ActionForward editFooterAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamFooterAgreementForm objform = (ExamFooterAgreementForm) form;
		errors.clear();
		messages.clear();

		objform = handler.getUpdatableForm(objform);
		objform.setMainList(handler.init());
		objform.setProgramTypeList(handler.getProgramTypeList());
		request.setAttribute("operation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_FOOTER_AGREEMENT);
	}

	// DATE & TEMPLATE validation
	private ActionErrors validate(String templateString, String name,
			String date) {
		if (templateString == null || templateString.length() == 0) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}

		if (splCharValidation(name)) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.footerAggreement.name.splChar"));
		}
		if (date != null && !date.isEmpty() && date.length() != 0) {
			if (!validatePastDate(date)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.footerAggreement.date.currDate"));
			}
		}
		return errors;

	}

	// Validate Special Character
	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\_ \\- \\& \\s]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	private boolean validatePastDate(String dateString) {

		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, "dd/MM/yyyy", "MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();

		if (date.compareTo(origdate) < 0)
			return false;
		else
			return true;

	}

}
