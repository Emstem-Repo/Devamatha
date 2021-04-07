package com.kp.cms.actions.exam;

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
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamSubjectSectionForm;
import com.kp.cms.handlers.exam.ExamSubjectSectionHandler;
import com.kp.cms.helpers.exam.ExamSubjectSectionHelper;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSubjectSectionMasterAction extends BaseDispatchAction {
	ExamSubjectSectionHandler handler = new ExamSubjectSectionHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initSubjectSectionMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectSectionForm objSubjectSectionForm = (ExamSubjectSectionForm) form;
		objSubjectSectionForm.setListOfSubjectSection(handler
				.getSubjectSectionType());
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	public ActionForward addSubjectSectionMasterEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectSectionForm objSSForm = (ExamSubjectSectionForm) form;
		errors.clear();
		messages.clear();
		// errors = objSSForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objSSForm);
		String name = objSSForm.getName();
		boolean splChar = splCharValidation(name, "\\,\\.\\-\\_\\&\\s");
		// errors.isEmpty()
		if (objSSForm.getName().length() != 0) {
			try {

				int isInitialise = 0;
				if (objSSForm.getIsinitialise().equalsIgnoreCase("on")) {
					isInitialise = 1;
				}
				//if (!splChar) {
					handler.addSSMaster(objSSForm.getName(), isInitialise,
							objSSForm.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.subjectSection.addsuccess",
							objSSForm.getName());
					messages.add("messages", message);
					saveMessages(request, messages);
					objSSForm.clearPage();
				//} else {
				//	errors.add("error", new ActionError(
					//		"knowledgepro.exam.specialization.splChar"));
					//saveErrors(request, errors);
				//}
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.subjectSection.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.subjectSection.reactivate"));
				saveErrors(request, errors);
			}

		} else {
			errors.add("error", new ActionError(
					"knowledgepro.exam.subjectSection.sectReqiured"));
			saveErrors(request, errors);
		}
		objSSForm.setListOfSubjectSection(handler.getSubjectSectionType());
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	public ActionForward updateSubjectSectionMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectSectionForm objSSForm = (ExamSubjectSectionForm) form;
		errors.clear();
		messages.clear();
		// errors = objSSForm.validate(mapping, request);
		setUserId(request, objSSForm);
		// String name = request.getParameter("name");
		String nameU = objSSForm.getName();
		boolean splChar = splCharValidation(nameU, "\\,\\.\\-\\_\\&\\s");
		if (objSSForm.getName().length() != 0) {
			try {
				int isInitialise = 0;
				if (objSSForm.getIsinitialise().equalsIgnoreCase("on")) {
					isInitialise = 1;
				}
			//	if (!splChar) {
					handler.updateSubjectSelection(objSSForm.getId(), objSSForm
							.getName().trim(), isInitialise, objSSForm
							.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.subjectSection.updated",
							objSSForm.getName());
					messages.add("messages", message);
					saveMessages(request, messages);
					objSSForm.clearPage();
				//} else {
					//request.setAttribute("examSSMOperation", "edit");
					//errors.add("error", new ActionError(
					//		"knowledgepro.exam.specialization.splChar"));
					//saveErrors(request, errors);
				//}
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.subjectSection.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.specialization.reactivate"));
				saveErrors(request, errors);
			}
		} else {
			request.setAttribute("examSSMOperation", "edit");
			request.setAttribute("Update", "Update");
			errors.add("error", new ActionError(
					"knowledgepro.exam.specialization.requiered"));
			saveErrors(request, errors);
		}
		objSSForm.setListOfSubjectSection(handler.getSubjectSectionType());
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	public ActionForward deleteSSM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectSectionForm objSSForm = (ExamSubjectSectionForm) form;
		errors.clear();
		messages.clear();
		errors = objSSForm.validate(mapping, request);
		setUserId(request, objSSForm);
		String id = request.getParameter("id");
		handler.deleteSubjectSection(Integer.parseInt(id), objSSForm
				.getUserId());

		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.specialization.delte");
		messages.add("messages", message);
		saveMessages(request, messages);
		// errors.add("error", new ActionError(
		// "knowledgepro.exam.subjectSection.delte"));
		// saveErrors(request, errors);
		objSSForm.setListOfSubjectSection(handler.getSubjectSectionType());
		objSSForm.clearPage();
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	public ActionForward reActivateSSMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectSectionForm objSSForm = (ExamSubjectSectionForm) form;
		errors.clear();
		messages.clear();
		errors = objSSForm.validate(mapping, request);
		setUserId(request, objSSForm);
		String name = request.getParameter("name");
		setUserId(request, objSSForm);

		handler.updateReactivateSSMaster(name, objSSForm.getUserId());

		objSSForm.clearPage();
		errors.clear();
		messages.clear();
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.spelization.addsuccess", name);
		messages.add("messages", message);
		saveMessages(request, messages);
		objSSForm.setListOfSubjectSection(handler.getSubjectSectionType());
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	public ActionForward editSSM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectSectionForm objform = (ExamSubjectSectionForm) form;
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		request.setAttribute("examSSMOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_SUBJECT_SELECTION);
	}

	private ExamSubjectSectionForm setRequestToList(
			ExamSubjectSectionForm objform, HttpServletRequest request)
			throws Exception {
		objform.setListOfSubjectSection(handler.getSubjectSectionType());
		return objform;

	}

	private boolean splCharValidation(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
}
