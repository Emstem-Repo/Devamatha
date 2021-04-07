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
import com.kp.cms.handlers.exam.ExamAttendanceMarksHandler;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;

public class ExamAttendanceMarksAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamAttendanceMarksHandler handler = new ExamAttendanceMarksHandler();

	public ActionForward initAttMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
		objform.setExamAttList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	public ActionForward addAttMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		boolean check = true;
		
		if(objform.getFromPercentage()!=null && !objform.getFromPercentage().isEmpty() && objform.getToPercentage()!=null && !objform.getToPercentage().isEmpty())
		{
			if ((Double.valueOf(objform.getFromPercentage().trim())
					.doubleValue() > Double.valueOf(
					objform.getToPercentage().trim()).doubleValue())) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.attendanceMarks.percInvalid",
						" From %", " To %"));
			}
		}
		saveErrors(request, errors);
		setUserId(request, objform);

		String[] str = request.getParameterValues("selectedCourse");

		try {

			if (errors.isEmpty()) {

				List<Integer> listCourses = new ArrayList<Integer>();
				if (str.length != 0) {
					listCourses = new ArrayList<Integer>();
					for (int x = 0; x < str.length; x++) {
						listCourses.add(Integer.parseInt(str[x]));
					}
				}
				handler.add(listCourses, objform.getFromPercentage().trim(),
						objform.getToPercentage().trim(), objform.getMarks()
								.trim(), objform.getTheoryPractical(), objform
								.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.attendanceMarks.addsuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();

			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.attendanceMarks.exists"));
			saveErrors(request, errors);
		} 
		objform.setExamAttList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	public ActionForward updateAttMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		boolean check = true;
		try {
			if(isCancelled(request)){
				String mode = "Edit";
				objform = handler.getUpdatableForm(objform, mode);
				setRequestToList(objform, request);
				request.setAttribute("examAttOperation", "edit");
				//request.setAttribute("Update", "Update");
				return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
			}else
		if(objform.getFromPercentage()!=null && !objform.getFromPercentage().isEmpty() && objform.getToPercentage()!=null && !objform.getToPercentage().isEmpty())
		{
			if ((Double.valueOf(objform.getFromPercentage().trim())
					.doubleValue() > Double.valueOf(
					objform.getToPercentage().trim()).doubleValue())) {
				errors.add("error", new ActionError("knowledgepro.exam.attendanceMarks.percInvalid"," From %", " To %"));
			}
		}
		
		saveErrors(request, errors);
		setUserId(request, objform);
		
			if (errors.isEmpty()) {

				handler.update(objform.getId(), Integer.parseInt(objform
						.getSelectedCourse()), objform.getFromPercentage()
						.trim(), objform.getToPercentage().trim(), objform
						.getMarks().trim(), objform.getTheoryPractical(),
						objform.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.attendanceMarks.updated", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			} else {
				request.setAttribute("examAttOperation", "edit");
				request.setAttribute("Update", "Update");
				objform.setExamAttList(handler.init());
				objform
						.setListExamCourseUtilTO(handler
								.getListExamCourseUtil());
				return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.attendanceMarks.exists"));
			saveErrors(request, errors);
			request.setAttribute("examAttOperation", "edit");
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
							"knowledgepro.exam.attendanceMarks.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			request.setAttribute("examAttOperation", "edit");
		}
		
		objform.setExamAttList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	public ActionForward deleteAttMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
		errors.clear();
		messages.clear();
		// errors = objform.validate(mapping, request);
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.deleteAttMarks(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.attendanceMarks.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setExamAttList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	public ActionForward reActivateAttMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
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
		objform.setExamAttList(handler.init());
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	public ActionForward editAttendenceMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAttendanceMarksForm objform = (ExamAttendanceMarksForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		request.setAttribute("examAttOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_ATTENDENCE_MARKS);
	}

	private ExamAttendanceMarksForm setRequestToList(
			ExamAttendanceMarksForm objform, HttpServletRequest request)
			throws Exception {
		objform.setListExamCourseUtilTO(handler.getListExamCourseUtil());
		request.setAttribute("examCourseUtilTOList", objform
				.getListExamCourseUtilTO());
		objform.setExamAttList(handler.init());
		return objform;
	}

}
