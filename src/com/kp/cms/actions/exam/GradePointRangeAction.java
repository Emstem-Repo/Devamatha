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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;

import com.kp.cms.forms.exam.GradePointRangeForm;
import com.kp.cms.handlers.admin.AcademicYearHandler;
import com.kp.cms.handlers.exam.GradePointRangeHandler;

import com.kp.cms.to.exam.GradePointRangeTO;

public class GradePointRangeAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	GradePointRangeHandler handler = new GradePointRangeHandler();

	public ActionForward initGradeDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		//objform.setListExamCourseGroup(handler.init());
		objform.setCourseListForGradeDefinition(handler.initNew());
		objform.setListCourseName(handler.getListExamCourseUtil());
		objform.setAppliedYearList(AcademicYearHandler.getInstance().getAcademicYearDetails());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINTRANGE);
	}

	public ActionForward addGD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
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
				objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
				//objform.setCourseListForGradeDefinition(handler.add(listCourses));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				objform.setCourseIds(courseIds.toString().trim());
				return mapping
						.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradePointDefinition.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradePointDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
		}
		objform.setListExamCourseGroup(handler.init());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINTRANGE);
	}

	public ActionForward addGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
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

					objform.setCourseListForGradeDefinition(handler.add(listCourses));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					return mapping
							.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
				} else {

					handler.addGDAdd(listCourses, objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint(),
							objform.getUserId(),objform);
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradePointDefinition.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					ArrayList<Integer> listCourseId = new ArrayList<Integer>();
					String[] courseIds = new String[50];
					if(objform.getCourseIds().contains(",")){
						courseIds = objform.getCourseIds().split(",");
						for(int j=0; j<courseIds.length;j++){
							listCourseId.add(Integer.parseInt(courseIds[j]));
						}
					}
					else
						listCourseId.add(Integer.parseInt(objform.getCourseIds()));
					//objform.setListExamCourseGroup(handler.init());
					//objform.setCourseListForGradeDefinition(handler.initNew());
					
					ArrayList<GradePointRangeTO> list = handler.select(listCourseId,objform.getAppliedYear());
					objform.setCourseListForGradeDefinition(list);
					//objform.setListCourseName(handler.getListExamCourseUtil());
					//objform.setListCourseName(handler.getListExamCourse(Integer
							//.parseInt(objform.getCourseIds())));
					objform.setListCourseName(handler
							.getListExamCourse(listCourseId));
					return mapping
							.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
				}
			} else {

				objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				return mapping
						.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradePointDefinition.exists"));
			saveErrors(request, errors);
			objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradePointDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			request.setAttribute("id",new Integer(objform.getId()).toString());
			request.getSession().setAttribute("id1",new Integer(objform.getId()).toString());
			objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
		}

	}

	public ActionForward updateGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
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
				return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
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
					objform.setCourseListForGradeDefinition(handler.add(listCourses));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					request.setAttribute("examGDOperation", "edit");
					return mapping
							.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);

				} else {
					handler.update(objform.getId(), Integer.parseInt(objform
							.getCourseIds()), objform.getStartPercentage().trim(),
							objform.getEndPercentage().trim(), objform.getGrade().trim(),
							objform.getInterpretation().trim(), objform
									.getResultClass().trim(), objform.getGradePoint().trim(),
							objform.getUserId(),objform);
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradePointDefinition.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					ArrayList<Integer> listCourses = new ArrayList<Integer>();
					listCourses.add(Integer.parseInt(objform.getCourseIds()));
					objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					//objform.setListExamCourseGroup(handler.init());
					//objform.setListCourseName(handler.getListExamCourseUtil());
					return mapping
							.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
				}
			} else {
				ArrayList<Integer> listCourses = new ArrayList<Integer>();
				listCourses.add(Integer.parseInt(objform.getCourseIds()));
				objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
				request.setAttribute("examGDOperation", "edit");
				return mapping
						.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradePointDefinition.exists"));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			request.setAttribute("examGDOperation", "edit");
			return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradePointDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			listCourses.add(Integer.parseInt(objform.getCourseIds()));
			objform.setCourseListForGradeDefinition(handler.select(listCourses,objform.getAppliedYear()));
			objform.setListCourseName(handler.getListExamCourse(listCourses));
			return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
		}

	}

	public ActionForward deleteGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete_gradeDef(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradePointDefinition.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		//objform.setListExamCourseGroup(handler.init());
		ArrayList<Integer> courseIdList = new ArrayList<Integer>();
		courseIdList.add(Integer.parseInt(objform.getCourseIds()));
		ArrayList<GradePointRangeTO> list = handler.select(courseIdList,objform.getAppliedYear());
		objform.setCourseListForGradeDefinition(list);
		
		objform.setListCourseName(handler.getListExamCourse(Integer
				.parseInt(objform.getCourseIds())));
		//objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINTRANGE_ADD);
	}

	public ActionForward deleteGCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");

		handler.delete_courseId(Integer.parseInt(objform.getCourseIds()), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradePointDefinition.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		//objform.setListExamCourseGroup(handler.init());
		objform.setCourseListForGradeDefinition(handler.initNew());
		objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINTRANGE);
	}

	public ActionForward reActivateGDAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		messages.clear();
		errors.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		String id1 = request.getSession().getAttribute("id1").toString();
		//id=(new Integer(objform.getId()).toString());
		setUserId(request, objform);

		handler.reactivate(Integer.parseInt(id1), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradePointDefinition.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		//objform.setListExamCourseGroup(handler.init());
		ArrayList<Integer> courseIdList = new ArrayList<Integer>();
		courseIdList.add(Integer.parseInt(objform.getCourseIds()));
		
		ArrayList<GradePointRangeTO> list = handler.select(courseIdList,objform.getAppliedYear());
		objform.setCourseListForGradeDefinition(list);
		objform.setListCourseName(handler.getListExamCourse(Integer
				.parseInt(objform.getCourseIds())));
		//objform.setListCourseName(handler.getListExamCourseUtil());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINTRANGE);
	}

	public ActionForward editGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";
		HttpSession session = request.getSession(true);
		objform = handler.getUpdatableForm(objform, mode);
		setRequestToList(objform, request,Integer.parseInt(objform.getCourseIds()));
		request.setAttribute("examGDOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
	}

	private void setRequestToList(GradePointRangeForm objform,
			HttpServletRequest request, int courseID) throws Exception  {

		objform.setListCourseName(handler.getListExamCourse(courseID));

		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(courseID);
		objform.setCourseListForGradeDefinition(handler.select(listcourseId,objform.getAppliedYear()));
;
	}

	public ActionForward editGCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradePointRangeForm objform = (GradePointRangeForm) form;
		//String id = request.getParameter("id");
		HttpSession session = request.getSession(true);
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(Integer.parseInt(objform.getCourseIds()));
		session.setAttribute("origCourseId", objform.getCourseIds());
		ArrayList<GradePointRangeTO> list = handler.select(listcourseId,objform.getAppliedYear());
		objform.setCourseListForGradeDefinition(list);
		objform.setListCourseName(handler.getListExamCourse(Integer
				.parseInt(objform.getCourseIds())));
		objform.setCourseIds(objform.getCourseIds());
		return mapping.findForward(CMSConstants.EXAM_GRADEPOINT_ADD);
	}

}
