package com.kp.cms.actions.exam;

import java.util.List;
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
import com.kp.cms.forms.exam.ExamSpecializationMasterForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.exam.ExamSpecializationHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationMasterAction extends BaseDispatchAction {
	ExamSpecializationHandler handler = new ExamSpecializationHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	/**
	 * To Get the table values
	 */

	public ActionForward initSpecializationMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSpecializationMasterForm objform = (ExamSpecializationMasterForm) form;
		objform.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objform.setCourseList(courseList);

		//objform.setCourseList(handler.getCourseList());
		// setCourses(SMForm, request);
		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	public ActionForward addSMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSpecializationMasterForm objform = (ExamSpecializationMasterForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String name = objform.getSpecializationName();
		boolean splChar = splCharValidation(name, "\\,\\.\\-\\_\\&\\s");
		if (errors.isEmpty()) {
			try {
				if (!splChar) {
					handler.addSMaster(objform.getSpecializationName().trim(),
							Integer.parseInt(objform.getCourseId()), objform
									.getUserId());

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.specialization.addsuccess",
							objform.getSpecializationName());

					messages.add("messages", message);
					saveMessages(request, messages);
					objform.setCourseId("");
					objform.clearPage();
				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.specialization.splChar"));
					saveErrors(request, errors);
				}
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.specialization.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.specialization.reactivate", e1
								.getID()));
				saveErrors(request, errors);
			}

		}
		objform.setCourseId(objform.getCourseId());
		objform.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objform.setCourseList(courseList);
	//	objform.setCourseList(handler.getCourseList());

		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	// modify assignment type

	public ActionForward updateSMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSpecializationMasterForm objform = (ExamSpecializationMasterForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String name = objform.getSpecializationName();
		boolean splChar = splCharValidation(name, "\\,\\.\\-\\_\\&\\s");

		if (errors.isEmpty()) {
			try {
				if (!splChar) {
					handler.updateSMaster(objform.getId(), objform
							.getSpecializationName().trim(), Integer
							.parseInt(objform.getCourseId()), objform
							.getUserId());
					objform.clearPage();
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.specialization.updated", objform
									.getSpecializationName());
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.setCourseId("");
				} else {
					request.setAttribute("examSMOperation", "edit");
					request.setAttribute("Update", "Update");
					errors.add("error", new ActionError(
							"knowledgepro.exam.specialization.splChar"));
					saveErrors(request, errors);
				}
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.specialization.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.specialization.reactivate", e1
								.getID()));
				saveErrors(request, errors);
			}
		} else {
			request.setAttribute("examSMOperation", "edit");
		}
		// request.setAttribute("Update", "Update");
		objform.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objform.setCourseList(courseList);
		//objform.setCourseList(handler.getCourseList());
		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	public ActionForward deleteSPLM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSpecializationMasterForm objForm = (ExamSpecializationMasterForm) form;
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		setUserId(request, objForm);
		handler.deleteSpelizaction(objForm.getId(), objForm.getUserId());
		objForm.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objForm.setCourseList(courseList);
	//	objForm.setCourseList(handler.getCourseList());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.specialization.delte");
		messages.add("messages", message);
		saveMessages(request, messages);
		// errors.add("error", new ActionError(
		// "knowledgepro.exam.specialization.delte"));
		// saveErrors(request, errors);
		objForm.clearPage();
		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	public ActionForward reActivateSMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSpecializationMasterForm objform = (ExamSpecializationMasterForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		handler.reactivateSpecialization(objform.getId(), objform.getUserId());
		objform.clearPage();

		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.specialization.reativeSuccess");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objform.setCourseList(courseList);
		//objform.setCourseList(handler.getCourseList());
		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	public ActionForward editSMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSpecializationMasterForm objform = (ExamSpecializationMasterForm) form;
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		request.setAttribute("examSMOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_SPECILIZACTION);
	}

	private void setRequestToList(ExamSpecializationMasterForm objform,
			HttpServletRequest request) throws Exception {
		objform.setListOfSpecialization(handler.getSpecializationType());
		List<KeyValueTO> courseList = CourseHandler.getInstance().getCoursesKey();
		objform.setCourseList(courseList);
		//objform.setCourseList(handler.getCourseList());
	}

	private boolean splCharValidation(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

}
