package com.kp.cms.actions.exam;

import java.util.ArrayList;

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
import com.kp.cms.forms.exam.ExamSGPADefinitionForm;
import com.kp.cms.handlers.exam.ExamSGPADefinitionHandler;
import com.kp.cms.to.exam.ExamSGPADefinitionTO;

public class ExamSGPADefinitionAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamSGPADefinitionHandler handler = new ExamSGPADefinitionHandler();

	public ActionForward initSGPADefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
	}

	public ActionForward addGD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String[] str = request.getParameterValues("courseName");
		try {
			if (errors.isEmpty()) 
			{
				ArrayList<Integer> listCourses = new ArrayList<Integer>();
				StringBuilder courseIds=new StringBuilder();
				if (str.length != 0) {
					listCourses = new ArrayList<Integer>();
					for (int x = 0; x < str.length; x++) {
						listCourses.add(Integer.parseInt(str[x]));
						courseIds.append(str[x]).append(",");
					}
					courseIds.setCharAt(courseIds.length()-1, ' ');
				}
				objform.setListSGPADefinition(handler.add(listCourses));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				objform.setCourseIds(courseIds.toString().trim());
				return mapping
						.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinition.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
		}
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
	}

	public ActionForward addGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		ArrayList<Integer> listCourses = new ArrayList<Integer>();
		String tempCommaSepVal = objform.getCourseIds();
		if (tempCommaSepVal != null) {
			String[] str = tempCommaSepVal.split(",");
			for (int x = 0; x < str.length; x++) {
				try {
					listCourses.add(Integer.parseInt(str[x]));
				} catch (NumberFormatException e) {
				}
			}
		}

		try {
			if (errors.isEmpty()) {
				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" Start %", " End %");
					errors.add("messages", message);
					saveMessages(request, errors);

					objform.setListSGPADefinition(handler.add(listCourses));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					return mapping
							.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
				} else {

					handler.addGDAdd(listCourses, objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint(),
							objform.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinition.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					objform.setListExamCourseGroup(handler.init());
					objform.setListCourseName(handler.getListExamCourseUtil());
					return mapping
							.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
				}
			} else {

				objform.setListSGPADefinition(handler.select(listCourses));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				return mapping
						.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinition.exists"));
			saveErrors(request, errors);
			objform.setListSGPADefinition(handler.select(listCourses));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			objform.setListSGPADefinition(handler.select(listCourses));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
		}

	}

	public ActionForward updateGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		try {
			if(isCancelled(request)){
				String mode = "Edit";
				objform = handler.getUpdatableForm(objform, mode);
				setRequestToList(objform, request, objform.getOrgCourseid());
				request.setAttribute("examGDOperation", "edit");
				//request.setAttribute("Update", "Update");
				return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
			} else
			if (errors.isEmpty()) {
				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" Start %", " End %");
					errors.add("messages", message);
					saveMessages(request, errors);
					ArrayList<Integer> listCourses = new ArrayList<Integer>();
					listCourses.add(Integer.parseInt(objform.getCourseIds()));
					objform.setListSGPADefinition(handler.add(listCourses));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					request.setAttribute("examGDOperation", "edit");
					return mapping
							.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);

				} else {
					handler.update(objform.getId(), Integer.parseInt(objform
							.getCourseIds()), objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade().trim(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint().trim(),
							objform.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinition.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					ArrayList<Integer> listCourses = new ArrayList<Integer>();
					listCourses.add(Integer.parseInt(objform.getCourseIds()));
					objform.setListSGPADefinition(handler.select(listCourses));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					//objform.setListExamCourseGroup(handler.init());
					//objform.setListCourseName(handler.getListExamCourseUtil());
					return mapping
							.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
				}
			} else {
				ArrayList<Integer> listCourses = new ArrayList<Integer>();
				listCourses.add(Integer.parseInt(objform.getCourseIds()));
				objform.setListSGPADefinition(handler.select(listCourses));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				request.setAttribute("examGDOperation", "edit");
				return mapping
						.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinition.exists"));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setListSGPADefinition(handler.select(listCourses));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			request.setAttribute("examGDOperation", "edit");
			return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setListSGPADefinition(handler.select(listCourses));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
		}

	}

	public ActionForward deleteGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete_gradeDef(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinition.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
	}

	public ActionForward deleteGCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");

		handler.delete_courseId(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinition.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
	}

	public ActionForward reActivateGDAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		messages.clear();
		errors.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		setUserId(request, objform);

		handler.reactivate(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinition.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION);
	}

	public ActionForward editGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request, objform.getOrgCourseid());
		request.setAttribute("examGDOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
	}

	private void setRequestToList(ExamSGPADefinitionForm objform,
			HttpServletRequest request, int courseID) throws Exception  {

		objform.setListCourseName(handler.getListExamCourse(courseID));

		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(courseID);
		objform.setListSGPADefinition(handler.select(listcourseId));
	}

	public ActionForward editGCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSGPADefinitionForm objform = (ExamSGPADefinitionForm) form;
		String id = request.getParameter("id");
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(Integer.parseInt(id));
		ArrayList<ExamSGPADefinitionTO> list = handler.select(listcourseId);
		objform.setListSGPADefinition(list);
		objform.setListCourseName(handler.getListExamCourse(Integer
				.parseInt(id)));
		objform.setCourseIds(id);
		return mapping.findForward(CMSConstants.EXAM_SGPA_DEFINITION_ADD);
	}

}
