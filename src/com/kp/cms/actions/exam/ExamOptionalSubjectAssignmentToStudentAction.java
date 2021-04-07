package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.forms.exam.ExamOptionalSubjectAssignmentToStudentForm;
import com.kp.cms.handlers.exam.ExamOptionalSubjectAssignmentToStudentHandler;
import com.kp.cms.to.exam.ExamOptAssSubTypeTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamOptionalSubjectAssignmentToStudentAction extends
		BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamOptionalSubjectAssignmentToStudentHandler handler = new ExamOptionalSubjectAssignmentToStudentHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamOptionalSubjectAssignmentToStudent(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamOptionalSubjectAssignmentToStudentForm objform = (ExamOptionalSubjectAssignmentToStudentForm) form;

		String str = CommonUtil.getTodayDate();
		str = str.substring(6, str.length());
		Integer year = Integer.parseInt(str);
		objform.setAcademicYear(str);
		HashMap<Integer, String> map = handler.getClassByYear(year);
		objform.setClassSelected(map);
		request.setAttribute("operation", "val");

		return mapping
				.findForward(CMSConstants.EXAM_OPTIONAL_SUBJECT_ASSIGNMENT_TO_STUDENT);

	}

	private void setClasses(ExamOptionalSubjectAssignmentToStudentForm objform,
			HttpServletRequest request) {

		String str = objform.getAcademicYear();

		Integer year = Integer.parseInt(str);

		HashMap<Integer, String> map = handler.getClassByYear(year);
		objform.setClassSelected(map);
		request.setAttribute("operation", "val");

		objform.setAcademicYear(objform.getAcademicYear());

	}

	public ActionForward getStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamOptionalSubjectAssignmentToStudentForm objform = (ExamOptionalSubjectAssignmentToStudentForm) form;
		// errors.clear();
		messages.clear();
		try {
			errors = objform.validate(mapping, request);
			setUserId(request, objform);
			ArrayList<Integer> listOfClass = new ArrayList<Integer>();
			saveErrors(request, errors);
			if (errors.isEmpty()) {
				String ids[] = objform.getSelectedClasses();
				for (String classId : ids) {
					if (classId != null && classId.trim().length() > 0)
						listOfClass.add(Integer.parseInt(classId));
				}
				ArrayList<ExamOptAssSubTypeTO> list = null;
				if (listOfClass != null && listOfClass.size() > 0) {
					list = handler.getStudentList(listOfClass, objform);
				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.classesRequired"));
					saveErrors(request, errors);
				}
				if (errors.isEmpty()) {
					listOfClass.clear();
					objform.setList(list);
					if (list.size() != 0) {
						objform.setListSize(list.size());
					} else {
						messages.add("message", new ActionMessage(
								"knowledgepro.norecords"));
						saveMessages(request, messages);
						objform.setSelectedClasses(null);
						objform.setAcademicYear(null);
						return initExamOptionalSubjectAssignmentToStudent(mapping, objform, request, response);
					}
					// SET VALUES to form
					int academicYear = Integer.parseInt(objform.getAcademicYear());
					String year = Integer.toString(academicYear).concat("-")
							.concat(Integer.toString(academicYear + 1));
					objform.setAcademicYear(year);
					objform.setClassName(handler.getClassNameByIds(objform
							.getSelectedClasses()));
					objform.setSelectedClasses(null);
					return mapping
							.findForward(CMSConstants.EXAM_OPTIONAL_SUBJECT_ASSIGNMENT_TO_STUDENT_RESULT);
				} else {
					setClasses(objform, request);
				}

			} else
				setClasses(objform, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping
				.findForward(CMSConstants.EXAM_OPTIONAL_SUBJECT_ASSIGNMENT_TO_STUDENT);

	}

	public ActionForward Apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ExamOptionalSubjectAssignmentToStudentForm objform = (ExamOptionalSubjectAssignmentToStudentForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		saveErrors(request, errors);

		if (errors.isEmpty()) {
			ArrayList<ExamOptAssSubTypeTO> val = objform.getList();
			objform.setList(null);
			handler.add(val);

			messages.add("message", new ActionMessage(
					"knowledgepro.exam.icmr.addsuccess"));
			saveMessages(request, messages);
		}

		return initExamOptionalSubjectAssignmentToStudent(mapping, objform,
				request, response);
	}
}
