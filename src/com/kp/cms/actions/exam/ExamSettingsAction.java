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
import com.kp.cms.forms.exam.ExamSettingsForm;
import com.kp.cms.handlers.exam.ExamSettingsHandler;
@SuppressWarnings("deprecation")
public class ExamSettingsAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamSettingsHandler handler = new ExamSettingsHandler();

	public ActionForward initExamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		objform.setListExamSetting(handler.init());
		
		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	
	public ActionForward addExamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = getValidation(objform.getAbsentCodeMarkEntry(), objform
				.getNotProcedCodeMarkEntry(),objform.getWithHeldCodeMarkEntry(), request);
		saveErrors(request, errors);
		setUserId(request, objform);
		try {

			if (errors.isEmpty()) {
				handler.add(objform.getAbsentCodeMarkEntry().trim(), objform
						.getNotProcedCodeMarkEntry().trim(), objform.getWithHeldCodeMarkEntry().trim(),objform
						.getSecuredMarkEntryBy().trim(), objform
						.getMaxAllwdDiffPrcntgMultiEvaluator().trim(), objform
						.getGradePointForFail().trim(), objform
						.getGradeForFail().trim(), objform.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examSettings.addsuccess", objform
								.getAbsentCodeMarkEntry());
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examSettings.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examSettings.reactivate", e1.getID()));
			saveErrors(request, errors);
		} 
		objform.setListExamSetting(handler.init());
		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	public ActionForward updatEexamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = getValidation(objform.getAbsentCodeMarkEntry(), objform
				.getNotProcedCodeMarkEntry(),objform.getWithHeldCodeMarkEntry(), request);
		saveErrors(request, errors);
		setUserId(request, objform);
		try {
			if (errors.isEmpty()) {
				handler.update(objform.getId(), objform
						.getAbsentCodeMarkEntry().trim(), objform
						.getNotProcedCodeMarkEntry().trim(), objform.getWithHeldCodeMarkEntry().trim(),objform
						.getSecuredMarkEntryBy().trim(), objform
						.getMaxAllwdDiffPrcntgMultiEvaluator().trim(), objform
						.getGradePointForFail().trim(), objform
						.getGradeForFail().trim(), objform.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examSettings.updated", "");
				messages.add("messages", message);
				saveMessages(request, messages);

				objform.clearPage();
			} else {
				request.setAttribute("examSettingOperation", "edit");
				request.setAttribute("Update", "Update");
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examSettings.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examSettings.reactivate"));
			saveErrors(request, errors);
		}
		objform.setListExamSetting(handler.init());

		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	public ActionForward deleteExamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.examSettings.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamSetting(handler.init());
		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	public ActionForward reActivateExamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		messages.clear();
		errors.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		setUserId(request, objform);
		handler.reactivate(Integer.parseInt(id), objform.getUserId());

		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.examSettings.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.clearPage();
		objform.setListExamSetting(handler.init());
		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	public ActionForward editExamSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingsForm objform = (ExamSettingsForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";

		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		request.setAttribute("examSettingOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_SETTINGS);
	}

	private ExamSettingsForm setRequestToList(ExamSettingsForm objform,
			HttpServletRequest request) throws Exception {
		objform.setListExamSetting(handler.init());
		return objform;
	}

	private ActionErrors getValidation(String absentCodeMarkEntry,
			String notProcedCodeMarkEntry,String withHeldCodeMarkEntry, HttpServletRequest request) {
		
		if (splCharValidate(absentCodeMarkEntry.trim(), "\\_\\&\\-")) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examSettings.special"));
		}
		
		if (splCharValidate(notProcedCodeMarkEntry.trim(), "\\_\\&\\-")) {
			if(errors.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(
				"knowledgepro.exam.examSettings.special"));
			}
			
		}
		if (splCharValidate(withHeldCodeMarkEntry.trim(), "\\_\\&\\-")) {
			if(errors.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(
				"knowledgepro.exam.examSettings.special"));
			}
			
		}

		saveErrors(request, errors);
		return errors;

	}

	private boolean splCharValidate(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

}
