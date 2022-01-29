package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamAttendanceMarksForm;
import com.kp.cms.forms.exam.ExamInternalCalculationMarksForm;
import com.kp.cms.handlers.exam.ExamInternalCalMarksHandler;

public class ExamInternalCalMarksAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamInternalCalMarksHandler handler = new ExamInternalCalMarksHandler();

	// internalCalMarks
	public ActionForward initICMMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	public ActionForward addICM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		errors.clear();
		messages.clear();

		errors = objform.validate(mapping, request);
		List<Integer> listCourses = new ArrayList<Integer>();
		boolean firstRun = true;

		if (objform.getSelectedCourse() != null
				&& objform.getSelectedCourse().length != 0) {
			for (int x = 0; x < objform.getSelectedCourse().length; x++) {
				listCourses.add(Integer.parseInt(objform.getSelectedCourse()[x]));
			}
		}
		boolean check = true;
		if (objform.getStartPercentage().trim().isEmpty()) {
			check = false;
		}
		if (objform.getEndPercentage().trim().isEmpty()) {
			check = false;
		}
		if (check) {
			if ((Double.valueOf(objform.getStartPercentage().trim())
					.doubleValue() > Double.valueOf(
					objform.getEndPercentage().trim()).doubleValue())) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.icmr.percentInvalid", " Start %",
						" End %"));
			}
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		try {
			if (errors.isEmpty()) {

				handler.add(listCourses, objform.getStartPercentage().trim(),
						objform.getEndPercentage().trim(), objform.getMarks()
								.trim(), objform.getUserId(), objform
								.getTheoryPractical());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.icmr.addsuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			} else {

				String course = "";
				for (Integer courseId : listCourses) {
					course = course.concat(Integer.toString(courseId)).concat(
							",");
				}
				if (course.length() > 0) {
					course = course.substring(0, course.length() - 1);
				}
				objform.setOrgSelectedCourse(course);
				objform.setExamICMList(handler.init());
				objform
						.setListExamCourseUtilTO(handler
								.getListExamCourseUtil());
				return mapping
						.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
			}
		} catch (DuplicateException e1) {
			errors.add("error",
					new ActionError("knowledgepro.exam.icmr.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.icmr.reactivate", e1.getID()));
			saveErrors(request, errors);
		}
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	public ActionForward updateICM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		boolean check = true;
		if (objform.getStartPercentage().trim().isEmpty()) {
			check = false;
		}
		if (objform.getEndPercentage().trim().isEmpty()) {
			check = false;
		}
		if (check) {
			if ((Double.valueOf(objform.getStartPercentage().trim())
					.doubleValue() > Double.valueOf(
					objform.getEndPercentage().trim()).doubleValue())) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.icmr.percentInvalid", " Start %",
						" End %"));
			}
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		// String[] str = request.getParameterValues("selectedCourse");
		try {
			if (errors.isEmpty()) {

				// List<Integer> listCourses = new ArrayList<Integer>();
				// if (str.length != 0) {
				// listCourses = new ArrayList<Integer>();
				// for (int x = 0; x < str.length; x++) {
				// listCourses.add(Integer.parseInt(str[x]));
				// }
				// }
				List<Integer> listCourses = new ArrayList<Integer>();
				if (objform.getSelectedCourse().length != 0) {
					listCourses = new ArrayList<Integer>();
					for (int x = 0; x < objform.getSelectedCourse().length; x++) {
						listCourses.add(Integer.parseInt(objform
								.getSelectedCourse()[x]));
					}
				}

				handler.update(objform.getId(), Integer.parseInt(objform
						.getSelectedCourse()[0]), objform.getStartPercentage()
						.trim(), objform.getEndPercentage().trim(), objform
						.getMarks().trim(), objform.getUserId(), objform
						.getTheoryPractical(), listCourses);

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.icmr.updated", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();

			} else {
				request.setAttribute("examICOperation", "edit");
				request.setAttribute("Update", "Update");
			}
		} catch (DuplicateException e1) {
			errors.add("error",
					new ActionError("knowledgepro.exam.icmr.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.icmr.reactivate"));
			saveErrors(request, errors);
		}
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	public ActionForward deleteICM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		String id = request.getParameter("id");

		handler.delete(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.icmr.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		objform.clearPage();
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	public ActionForward reActivateICMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		messages.clear();
		errors.clear();
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		String id = request.getParameter("id");
		setUserId(request, objform);

		handler.reactivate(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.icmr.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.clearPage();
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	public ActionForward editICM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalCalculationMarksForm objform = (ExamInternalCalculationMarksForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		request.setAttribute("examICOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_INTERNAL_CAL_MARKS);
	}

	private ExamInternalCalculationMarksForm setRequestToList(
			ExamInternalCalculationMarksForm objform, HttpServletRequest request)
			throws Exception {
		objform.setExamICMList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return objform;

	}

	}


