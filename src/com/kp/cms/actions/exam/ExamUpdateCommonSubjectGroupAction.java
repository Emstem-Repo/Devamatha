package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import com.kp.cms.forms.exam.ExamUpdateCommonSubjectGroupForm;
import com.kp.cms.handlers.exam.ExamUpdateCommonSubjectGroupHandler;
import com.kp.cms.utilities.CommonUtil;

public class ExamUpdateCommonSubjectGroupAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamUpdateCommonSubjectGroupHandler handler = new ExamUpdateCommonSubjectGroupHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamUpdateCommonSubjectGroup(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUpdateCommonSubjectGroupForm objform = (ExamUpdateCommonSubjectGroupForm) form;
		// setClasses(objform,request);
		if(objform.getAcademicYear()==null){
		String str = CommonUtil.getTodayDate();
			str = str.substring(6, str.length());
			Integer year = Integer.parseInt(str);
			HashMap<Integer, String> map = handler.getClassByYear(year);
			objform.setClassSelected(map);
			request.setAttribute("operation", "val");
		}
		return mapping
				.findForward(CMSConstants.EXAM_UPDATE_COMMON_SUBJECT_GROUP);
	}

	private void setClasses(ExamUpdateCommonSubjectGroupForm objform,
			HttpServletRequest request) {
		
		String str = CommonUtil.getTodayDate();
		str = str.substring(6, str.length());
		Integer year = Integer.parseInt(str);

		HashMap<Integer, String> map = handler.getClassByYear(year);
		objform.setClassSelected(map);
		request.setAttribute("operation", "val");
	}

	public ActionForward getStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ExamUpdateCommonSubjectGroupForm objform = (ExamUpdateCommonSubjectGroupForm) form;

		setUserId(request, objform);
		objform.getAcademicYear();
		ArrayList<Integer> list = new ArrayList<Integer>();
		errors.clear();
		messages.clear();
		saveErrors(request, errors);

		String[] ids = request.getParameterValues("selectedClasses");

		if (errors.isEmpty() && ids != null && ids.length > 0) {
			for (String s : ids) {
				if (s != null)
					list.add(Integer.parseInt(s));
			}
			String academicYear1 = request.getParameter("academicYear");

			int academicYear = 0;
			if (academicYear1 == null) {
				Date d = new Date();
				academicYear = (d.getYear() + 1900);
			}
			else if (academicYear1 != null)
				academicYear = Integer.parseInt(academicYear1);
			handler.update(academicYear, list);
			ActionMessage message = new ActionMessage("knowledgepro.exam.attendanceMarks.updated");
			messages.add("messages",message);
			saveMessages(request,messages);
			academicYear = 0;
			setClasses(objform, request);
			objform.setSelectedClasses("");
			return mapping
					.findForward(CMSConstants.EXAM_UPDATE_COMMON_SUBJECT_GROUP);

		} else {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamMarksEntry.Students.classes.req"));
			saveErrors(request, errors);
			setClasses(objform, request);
		
			return mapping
					.findForward(CMSConstants.EXAM_UPDATE_COMMON_SUBJECT_GROUP);

		}

	}
}
