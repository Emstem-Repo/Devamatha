package com.kp.cms.actions.exam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamInternalRetestApplicationForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamInternalRetestApplicationHandler;
import com.kp.cms.to.exam.ExamInternalRetestApplicationSubjectsTO;
import com.kp.cms.to.exam.ExamInternalRetestApplicationTO;

@SuppressWarnings("deprecation")
public class ExamInternalRetestApplicationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentEditAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamInternalRetestApplicationHandler handler = new ExamInternalRetestApplicationHandler();

	public ActionForward initInternalRetestApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamInternalRetestApplicationForm objform = (ExamInternalRetestApplicationForm) form;
		setExamNamesToList(objform, request);
		objform.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION);
	}

	// To set ExamName Dropdown
	private void setExamNamesToList(ExamInternalRetestApplicationForm objform,
			HttpServletRequest request) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		objform
				.setListExamName(new ExamGenHandler()
						.getExamName_Internal(Integer.parseInt(dateFormat
								.format(date))));
		request.setAttribute("operation", "val");

	}

	// On SEARCH to get the student details
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalRetestApplicationForm objForm = (ExamInternalRetestApplicationForm) form;
		messages.clear();
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			try {
				objForm = getUpdatableForm(objForm);
				Integer classId = null;
				if (objForm.getClassId() != null
						&& objForm.getClassId().length() > 0)
					classId = Integer.parseInt(objForm.getClassId());
				objForm.setListClassDetails(handler.getClassDetails(Integer
						.parseInt(objForm.getExamNameId()), classId, objForm
						.getRollNo(), objForm.getRegNo()));
				if (objForm.getListClassDetails() != null
						&& objForm.getListClassDetails().size() > 0) {
					return mapping
							.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION_SEARCH);
				}
				messages.add("messages", new ActionMessage(
						"knowledgepro.cancelattendance.norecord"));
				saveMessages(request, messages);

			} catch (BusinessException e) {
				log.error("error in search method...", e);
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				saveErrors(request, errors);
			}
		}
		setExamNamesToList(objForm, request);
		objForm.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION);
	}

	// To get Updatable form
	private ExamInternalRetestApplicationForm getUpdatableForm(
			ExamInternalRetestApplicationForm objForm) throws Exception {
		int academicYear = Integer.parseInt(objForm.getAcademicYear()) + 1;
		objForm.setAcademicYear(objForm.getAcademicYear() + "-" + academicYear);
		objForm.setExamNameId(objForm.getExamNameId());
		objForm.setExamName(handler.getExamNameByExamId(Integer
				.parseInt(objForm.getExamNameId())));
		if (objForm.getClassId() != null
				&& objForm.getClassId().trim().length() > 0)
			objForm.setClassName(handler.getClassNameById(Integer
					.parseInt(objForm.getClassId())));
		else {
			objForm.setClassName("");
		}
		return objForm;
	}

	// ADD - to get the subjects for a particular student
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalRetestApplicationForm objForm = (ExamInternalRetestApplicationForm) form;

		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);

		errors = validateDetails(objForm);
		saveErrors(request, errors);

		if (errors.isEmpty()) {
			try {
				objForm = getUpdatableForm(objForm);
				Integer classId = null;
				if (objForm.getClassId() != null
						&& objForm.getClassId().length() > 0)
					classId = Integer.parseInt(objForm.getClassId());

				ExamInternalRetestApplicationTO to = handler
						.getSubjListForStudent(Integer.parseInt(objForm
								.getExamNameId()), classId,
								objForm.getRollNo(), objForm.getRegNo());
				to.setExamId(objForm.getExamNameId());
				to.setAcademicYear(objForm.getAcademicYear());
				to.setRegNumber(to.getRegNumber());
				to.setRollNumber(objForm.getRollNo());

				if (classId != null)
					to.setClassId(classId);
				if (objForm.getClassId() != null
						&& objForm.getClassId().length() > 0)
					to.setClassId(Integer.parseInt(objForm.getClassId()));
				objForm.setInternalRetestTo(to);
				if (to.getSubjectList() != null
						&& to.getSubjectList().size() > 0) {

					return mapping
							.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION_ADD);
				}

				messages.add("messages", new ActionMessage(
						"knowledgepro.exam.InternalRetest.noSubjects"));
				saveMessages(request, messages);

			} catch (BusinessException e) {

				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				saveErrors(request, errors);
			}
		}
		setExamNamesToList(objForm, request);
		objForm.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION);
	}

	// To validate RegisterNo and RollNumber
	private ActionErrors validateDetails(
			ExamInternalRetestApplicationForm objForm) {

		int flag = 0;
		if (objForm.getRegNo() == null && objForm.getRollNo() == null) {
			flag = 1;
		} else if ((objForm.getRegNo() != null && objForm.getRegNo().trim()
				.length() == 0)
				&& (objForm.getRollNo() != null && objForm.getRollNo().trim()
						.length() == 0)) {
			flag = 1;
		}
		if (flag == 1) {
			errors
					.add(
							"error",
							new ActionError(
									"knowledgepro.exam.ExamMarksEntry.Students.regNoOrrollNumber"));
		}
		return errors;
	}

	// On EDIT - to get student details
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalRetestApplicationForm objForm = (ExamInternalRetestApplicationForm) form;
		objForm.setInternalRetestTo(handler.getToForEditedStudent(objForm
				.getId()));
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION_ADD);

	}

	// To DELETE Student Entry
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalRetestApplicationForm objForm = (ExamInternalRetestApplicationForm) form;
		handler.deleteInternalRetestApplication(objForm.getId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.attendanceMarks.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objForm.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION);

	}

	// To add and update
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInternalRetestApplicationForm objForm = (ExamInternalRetestApplicationForm) form;

		errors.clear();
		errors = objForm.validate(mapping, request);
		errors = validateSubmitData(objForm);

		saveErrors(request, errors);
		if (errors.isEmpty()) {

			handler.addSubjectDetails(objForm.getInternalRetestTo().getId(),
					objForm.getInternalRetestTo());
			messages.add("messages", new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.addsuccess"));
			saveMessages(request, messages);
			setExamNamesToList(objForm, request);
			objForm.clearPage();
			return mapping
					.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION);

		}

		return mapping
				.findForward(CMSConstants.EXAM_INTERNAL_RETEST_APPLICATION_ADD);
	}

	// // To Validate The Submitted Data
	private ActionErrors validateSubmitData(
			ExamInternalRetestApplicationForm objForm) {
		int oneFeeEntryIsPresent = 0;
		int oneTheoryEntryIsPresent = 0;
		int onePracticalEntryIsPresent = 0;
		boolean flag = true;
		boolean checkBox = true;
		boolean splchar = true;
		ArrayList<ExamInternalRetestApplicationSubjectsTO> values = objForm
				.getInternalRetestTo().getSubjectList();
		flag = true;
		for (ExamInternalRetestApplicationSubjectsTO to : values) {
			oneTheoryEntryIsPresent = 0;
			onePracticalEntryIsPresent = 0;

			if (to.getFees() != null && to.getFees().trim().length() > 0) {
				oneFeeEntryIsPresent = 1;
				if (splchar && splCharValidation(to.getFees())) {

					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
					splchar = false;
				}
				if (to.getTheoryAppeared() != null
						&& to.getTheoryAppeared().equals("on")) {
					oneTheoryEntryIsPresent = 1;

				}
				if (to.getPracticalAppeared() != null
						&& to.getPracticalAppeared().equals("on")) {
					onePracticalEntryIsPresent = 1;

				}
				if (oneTheoryEntryIsPresent == 0
						&& onePracticalEntryIsPresent == 0) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.InternalRetest.feeEntry"));
					break;
				}
			} else {

				oneTheoryEntryIsPresent = 0;
				onePracticalEntryIsPresent = 0;
				if (to.getTheoryAppeared() != null) {
					oneTheoryEntryIsPresent = 1;
					to.setIsCheckedDummy(true);
					to.setTheoryAppeared(null);
					flag = false;
				} else {
					to.setIsCheckedDummy(false);
				}
				if (to.getPracticalAppeared() != null) {
					onePracticalEntryIsPresent = 1;
					to.setIsCheckedDummyPractical(true);
					to.setPracticalAppeared(null);
					flag = false;
				} else {
					to.setIsCheckedDummyPractical(false);
				}
				if ((oneTheoryEntryIsPresent == 1 || onePracticalEntryIsPresent == 1)
						&& checkBox) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.InternalRetest.emptyFee"));

					checkBox = false;
				}
			}

		}
		if (oneFeeEntryIsPresent == 0 && flag) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.InternalRetest.oneEntry"));

		}

		return errors;
	}

	// To validate special character
	private boolean splCharValidation(String name) {

		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
}
