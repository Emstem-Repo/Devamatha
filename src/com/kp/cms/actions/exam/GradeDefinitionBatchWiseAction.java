package com.kp.cms.actions.exam;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamGradeDefinitionForm;
import com.kp.cms.forms.exam.GradeDefinitionBatchWiseForm;
import com.kp.cms.handlers.exam.GradeDefinitionBatchWiseHandler;
import com.kp.cms.to.exam.GradeDefinitionBatchWiseTO;
import com.kp.cms.utilities.HibernateUtil;

public class GradeDefinitionBatchWiseAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	GradeDefinitionBatchWiseHandler handler = new GradeDefinitionBatchWiseHandler();

	public ActionForward initGradeDefinitionBatchWise(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		objform.setListCourseName(handler.getListExamCourseUtil());
		objform.setListExamCourseGroup(handler.init());
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
	}

	public ActionForward addGDB(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		 ActionErrors errors = objform.validate(mapping, request);
	//	errors.clear();
	//	messages.clear();
		setUserId(request, objform);
		String[] str = request.getParameterValues("courseName");
		String year=objform.getFromBatch();
		HttpSession session=request.getSession();
		session.setAttribute("YEAR", objform.getFromBatch());
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
				objform.setListGradeDefinition(handler.add(listCourses,objform));
				objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
				objform.setCourseIds(courseIds.toString().trim());
				return mapping
						.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
			}else{
				saveErrors(request, errors);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinitionbatch.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinitionbatch.reactivate", e1
									.getID()));
			saveErrors(request, errors);
		}
		objform.setListCourseName(handler.getListExamCourseUtil());
		objform.setListExamCourseGroup(handler.init());
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
	}

	public ActionForward addGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		errors.clear();
		messages.clear();
		HttpSession session=request.getSession();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String year=session.getAttribute("YEAR").toString();
		objform.setFromBatch(year);
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
				if ((Double.valueOf(objform.getStartPercentage().trim()).doubleValue() > Double.valueOf(objform.getEndPercentage().trim()).doubleValue())) 
				{

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" Start %", " End %");
					errors.add("messages", message);
					saveMessages(request, errors);

					objform.setListGradeDefinition(handler.add(listCourses,objform));
					objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
					return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
				} else {

					handler.addGDAdd(listCourses,objform.getFromBatch(), objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint(),
							objform.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinitionbatch.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					objform.setListCourseName(handler.getListExamCourseUtil());
					objform.setListExamCourseGroup(handler.init());
					return mapping
							.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
				}
			} else {

				objform.setListGradeDefinition(handler.select(listCourses,objform));
				objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
				return mapping
						.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinitionbatch.exists"));
			saveErrors(request, errors);
			objform.setListGradeDefinition(handler.select(listCourses,objform));
			objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
			return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinitionbatch.reactivate", e1.getID()));
			saveErrors(request, errors);
			objform.setListGradeDefinition(handler.select(listCourses,objform));
			objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
			return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
		}

	}

	public ActionForward updateGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		HttpSession session=request.getSession();
		String year=session.getAttribute("YEAR").toString();
		objform.setFromBatch(year);
		try {
			if(isCancelled(request)){
				String mode = "Edit";
             	objform = handler.getUpdatableForm(objform, mode);
             	setRequestToList(objform, request, objform.getOrgCourseid(),year);
				request.setAttribute("examGDOperation", "edit");
				
				//request.setAttribute("Update", "Update");
				return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
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
					objform.setListGradeDefinition(handler.add(listCourses,objform));
					objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
					request.setAttribute("examGDOperation", "edit");
					return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);

				} else {
					handler.update(objform.getId(), Integer.parseInt(objform
							.getCourseIds()),objform.getFromBatch(), objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade().trim(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint().trim(),
							objform.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinitionbatch.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					ArrayList<Integer> listCourses = new ArrayList<Integer>();
					listCourses.add(Integer.parseInt(objform.getCourseIds()));
					objform.setListGradeDefinition(handler.select(listCourses,objform));
					objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
					//objform.setListExamCourseGroup(handler.init());
					//objform.setListCourseName(handler.getListExamCourseUtil());
					return mapping
							.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
				}
			} else {
				ArrayList<Integer> listCourses = new ArrayList<Integer>();
				listCourses.add(Integer.parseInt(objform.getCourseIds()));
				objform.setListGradeDefinition(handler.select(listCourses,objform));
				objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
				request.setAttribute("examGDOperation", "edit");
				return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinitionbatch.exists"));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setListGradeDefinition(handler.select(listCourses,objform));
			objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
			request.setAttribute("examGDOperation", "edit");
			return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinitionbatch.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setListGradeDefinition(handler.select(listCourses,objform));
			objform.setListExamCourseGroup(handler.getListExamCourseYear(listCourses,year));
			return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
		}

	}

	public ActionForward deleteGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete_gradeDef(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinitionbatch.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
	}

	public ActionForward deleteGCDB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");

		handler.delete_courseId(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinitionbatch.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
	}

	public ActionForward reActivateGDAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		messages.clear();
		errors.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		setUserId(request, objform);

		handler.reactivate(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinitionbatch.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH);
	}

	public ActionForward editGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
	   HttpSession session=request.getSession();
		errors.clear();
		messages.clear();
		String mode = "Edit";
		String year=session.getAttribute("YEAR").toString();
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request, objform.getOrgCourseid(),year);
		request.setAttribute("examGDOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
	}

	private void setRequestToList(GradeDefinitionBatchWiseForm objform,HttpServletRequest request, int courseID, String year) throws Exception  {

		objform.setListExamCourseGroup(handler.getListExamCourses(courseID,year));
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(courseID);
		objform.setListGradeDefinition(handler.select(listcourseId,objform));
	}

	public ActionForward editGCDB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeDefinitionBatchWiseForm objform = (GradeDefinitionBatchWiseForm) form;
		String id = request.getParameter("id");
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(Integer.parseInt(id));
		objform.setListGradeDefinition(handler.select(listcourseId,objform));
		HttpSession session=request.getSession();
		session.setAttribute("YEAR", objform.getFromBatch());
		String year=objform.getFromBatch();
	   	objform.setListExamCourseGroup(handler.getListExamCourses(Integer.parseInt(id),year));
		objform.setCourseIds(id);
		return mapping.findForward(CMSConstants.EXAM_GRADE_DEFINITION_FROMBATCH_ADD);
	}

}
