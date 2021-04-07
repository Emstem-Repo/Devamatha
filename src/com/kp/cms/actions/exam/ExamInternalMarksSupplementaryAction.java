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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamInternalMarksSupplementaryForm;
import com.kp.cms.handlers.exam.ExamInternalMarkSupplementaryHandler;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;

@SuppressWarnings("deprecation")
public class ExamInternalMarksSupplementaryAction extends BaseDispatchAction {
	ExamInternalMarkSupplementaryHandler handler = new ExamInternalMarkSupplementaryHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamInternalMarksSupplementary(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamInternalMarksSupplementaryForm objform = (ExamInternalMarksSupplementaryForm) form;
//		objform.setListExamName(handler.getSupplementaryExamName());
		objform.setExamNameId("");
		objform.setRollNo("");
		objform.setRegNo("");
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalMarksSupplementaryForm objform = (ExamInternalMarksSupplementaryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateData(objform, "search");
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			try {
				int courseId = 0;
				if (objform.getCourseId() != null) {
					courseId = Integer.parseInt(objform.getCourseId());
				}
				int examId = 0;
				if (objform.getExamNameId() != null) {
					examId = Integer.parseInt(objform.getExamNameId());
				}

				String rollNo = objform.getRollNo().trim();
				String regNo = objform.getRegNo().trim();
				String subjectType = objform.getSubjectTypeId();
				String[] scheme = objform.getSchemeId().split("_");
				String sc = "";
				for (int i = 0; i < scheme.length; i++) {
					sc = scheme[i];
				}
				List<ExamInternalMarksSupplementaryTO> list = handler
						.getSubjectInternalSupMarks(courseId, subjectType,
								rollNo, regNo, Integer.parseInt(sc), examId);
				objform.setSubjects(list);
				objform.setExamNameId(objform.getExamNameId());
				objform.setExamName(handler.getExamNameByExamId(Integer
						.parseInt(objform.getExamNameId())));
				objform.setCourseName(handler.getCourseName(Integer
						.parseInt(objform.getCourseId())));

				objform.setStudentId(handler.getStudentId(rollNo, regNo));

				objform.setSchemeId(sc);
				if (list.size() > 0) {
					return mapping
							.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY_SEARCH);

				} else {
				//	objform.setListExamName(handler.getSupplementaryExamName());
					objform.setExamNameId("");
					objform.setRollNo("");
					objform.setRegNo("");
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.ExamInternalMark.student", "");
					messages.add("messages", message);
					saveErrors(request, errors);
					saveMessages(request, messages);
					objform.setExamNameId("");
					objform.setRollNo("");
					objform.setRegNo("");
					return mapping
							.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY);
				}

			} catch (BusinessException ex) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				saveErrors(request, errors);
			//	objform.setListExamName(handler.getSupplementaryExamName());
				return mapping
						.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY);
			}
		} else {
			objform = retainValues(objform);
			request.setAttribute("retain", "yes");
			return mapping
					.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY);
		}
	}

	private ExamInternalMarksSupplementaryForm retainValues(
			ExamInternalMarksSupplementaryForm objform) throws Exception {
		objform = handler.retainAllValues(objform);
		return objform;
	}

	private ActionErrors validateData(
			ExamInternalMarksSupplementaryForm objform, String type) {
		if (type.equals("search")) {
			String regNo = objform.getRegNo();
			String rollNo = objform.getRollNo();
			if ((regNo == null || regNo.trim().equals(""))
					&& (rollNo == null || rollNo.trim().equals(""))) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamInternalMark.regNoOrrollNumber"));
			}
			if (splCharValidationSearch(regNo)
					|| splCharValidationSearch(rollNo)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
			}
		}
		if (type.equals("add")) {
			List<ExamInternalMarksSupplementaryTO> subjects = objform
					.getSubjects();
			for (ExamInternalMarksSupplementaryTO to : subjects) {
				if (to.getIsTheoryPrac().equalsIgnoreCase("T")) {
					if (to.getTheoryMarks() != null
							&& splCharValidation(to.getTheoryMarks())) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
						break;
					}
				}

				if (to.getIsTheoryPrac().equalsIgnoreCase("p")) {
					if (to.getPracticalMarks() != null
							&& splCharValidation(to.getPracticalMarks())) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
						break;
					}
				} else {
					if (to.getTheoryMarks() != null
							&& splCharValidation(to.getTheoryMarks())) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
						break;
					}
					if (to.getPracticalMarks() != null
							&& splCharValidation(to.getPracticalMarks())) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
						break;
					}

				}

			}
		}
		return errors;
	}

	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]\\.+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();

		return haveSplChar;

	}

	private boolean splCharValidationSearch(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9a-zA-Z]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();

		return haveSplChar;

	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalMarksSupplementaryForm objform = (ExamInternalMarksSupplementaryForm) form;
		errors.clear();
		messages.clear();
		errors = validateData(objform, "add");
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			handler.add(objform, objform.getStudentId());

			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamInternalMark.added", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			objform.setExamNameId("");
			objform.setRollNo("");
			objform.setRegNo("");
			return mapping
					.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY);
		} else {

			return mapping
					.findForward(CMSConstants.EXAM_INTERNAL_MARKS_SUPPLEMENTARY_SEARCH);
		}

	}

}
