package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamSubDefinitionCourseWiseHandler;
import com.kp.cms.handlers.exam.SubjectRuleSettingsHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseUpdateTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("deprecation")
public class ExamSubjectDefinitionCourseWiseAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamSubDefinitionCourseWiseHandler handler = new ExamSubDefinitionCourseWiseHandler();

	public ActionForward initExamSubDefCourseWise(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		resetForm(objform,request);
		return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
	}

	private void resetForm(ExamSubjectDefCourseForm objform,
			HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("EditTO");
		setUserId(request, objform);
		setProgramtypelist(objform); 
		//objform.setCourseNameList(handler.getCourseListHashMap());
		//setprogramMapToRequest(objform);
		objform.setCourse(null);
		objform.setProgramTypeId(null);
		objform.setProgramId(null);
		objform.setScheme(null);
	}

	private ExamSubjectDefCourseForm retainValues(
			ExamSubjectDefCourseForm objform,HttpServletRequest request) {
		objform = handler.retainAllValues(objform,request);
		return objform;
	}

	public ActionForward setExamUnvSubCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);

		saveErrors(request, errors);
		setUserId(request, objform);
		HttpSession session = request.getSession(false);
		int schemeNo = 0;
		int schemeId = 0;
		int academicYear = 0;
		int courseId = 0;
		if (errors.isEmpty()) {

			academicYear = (objform.getAcademicYear() != null
					&& objform.getAcademicYear().trim().length() > 0 ? Integer
					.parseInt(objform.getAcademicYear()) : 0);
			courseId = (objform.getCourse() != null
					&& objform.getCourse().trim().length() > 0 ? Integer
					.parseInt(objform.getCourse()) : 0);

			String[] schemeStr = (objform.getScheme() != null
					&& objform.getScheme().trim().length() > 0 ? objform
					.getScheme().split("_") : new String[] { "0" });
			if (schemeStr != null && schemeStr.length > 1) {
				schemeId = Integer.parseInt(schemeStr[0]);
				schemeNo = Integer.parseInt(schemeStr[1]);
			}

			if (session.getAttribute("EditTO") == null) {
				ExamSubDefinitionCourseWiseTO subdefTO = new ExamSubDefinitionCourseWiseTO();
				subdefTO.setAcademicYear(academicYear);
				subdefTO.setSchemeId(schemeId);
				subdefTO.setSchemeNo(schemeNo);
				subdefTO.setCourseId(courseId);
				session.setAttribute("EditTO", subdefTO);
			}

			objform.setListSubjects(handler.getSubj_course_scheme_year(
					courseId, schemeId, schemeNo, academicYear));
			objform.setAcademicYear(objform.getAcademicYear());
			objform
					.setAcademicYear_value(handler
							.getacademicYear(academicYear));
			objform.setCourseName(handler.getCourseName(courseId));
			objform.setSchemeName(Integer.toString(schemeNo));
			objform.setSchemeId(Integer.toString(schemeId));
			objform.setSchemeNo(Integer.toString(schemeNo));
			objform.setCourseId(objform.getCourse());

			if (objform.getListSubjects().size() == 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
//				setprogramMapToRequest(objform, request);
//				retainValues(objform,request);
				resetForm(objform, request);
				return mapping
						.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
			}

			return mapping
					.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_RESULT);

		} else {

			objform = retainValues(objform,request);
			objform.setAcademicYear(objform.getAcademicYear());
			setprogramMapToRequest(objform, request);
			return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
		}

	}

	// -----------#editted data-------------------------------------//
	public ActionForward editSubjectInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		setUserId(request, objform);
		ExamSubDefinitionCourseWiseTO to = handler.createFormObjcet(objform);
		objform.setSubjectName(to.getSubjectName());
		objform.setSubjectCode(to.getSubjectCode());
		objform.setSubId(Integer.toString(to.getSubjectId()));
		String isTheoryOrPractical = handler.checkIfTheoryOrPractical(to
				.getSubjectId());

		if (isTheoryOrPractical != null && isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("t")) {
			objform.setIsTheoryOrPractical("t");

		} else if (isTheoryOrPractical != null
				&& isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("p")) {
			objform.setIsTheoryOrPractical("p");
		} else {
			objform.setIsTheoryOrPractical("b");
		}

		objform.setCourseId(objform.getCourseId());
		objform.setListSubjectSection(handler.getSubjectSectioneList());
		handler.getSubjectDetailsFromSubjectId(Integer.parseInt(objform
				.getSubId()), objform);
		if(objform.getSubjectCode()==null){
			objform.setSubjectCode(to.getSubjectCode());
		}
		return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_EDIT);
	}

	public ActionForward submitEdittedData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String subjectOrder = null;
		try {

			if (errors.isEmpty()) {
				HttpSession session = request.getSession(false);
				subjectOrder = objform.getSubjectOrder();
				int tempSchemeNo = 0;
				if (session.getAttribute("EditTO") != null) {
					ExamSubDefinitionCourseWiseTO subdefTOFromSession = (ExamSubDefinitionCourseWiseTO) session
							.getAttribute("EditTO");
					tempSchemeNo = subdefTOFromSession.getSchemeNo();
				}
				
				handler.addSubDefCourseDetails(objform, tempSchemeNo);

				ActionMessage message = new ActionMessage("knowledgepro.exam.subjectDefinitionCourseWise.success");
				errors.add("messages", message);
				saveMessages(request, errors);
				objform.resetFields();
				
				if (session.getAttribute("EditTO") != null) {
					ExamSubDefinitionCourseWiseTO subdefTOFromSession = (ExamSubDefinitionCourseWiseTO) session
							.getAttribute("EditTO");
					int academicYear = subdefTOFromSession.getAcademicYear();
					int schemeNo = subdefTOFromSession.getSchemeNo();
					int schemeId = subdefTOFromSession.getSchemeId();
					int courseId = subdefTOFromSession.getCourseId();
					objform.setAcademicYear_value(handler
							.getacademicYear(academicYear));
					objform.setCourseName(handler.getCourseName(courseId));
					objform.setSchemeName(Integer.toString(schemeNo));
					objform.setListSubjects(handler.getSubj_course_scheme_year(
							courseId, schemeId, schemeNo, academicYear));
					objform.setCourseId(Integer.toString(courseId));

				}

				return mapping
						.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_RESULT);

			}

		}

		catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.subject.exists"));
			saveErrors(request, errors);
		}
		ExamSubDefinitionCourseWiseTO to = handler.createFormObjcet(objform);
		objform.setSubjectName(to.getSubjectName());
		objform.setSubjectCode(to.getSubjectCode());
		if (subjectOrder != null)
			objform.setSubjectOrder(subjectOrder);
		objform.setListSubjectSection(handler.getSubjectSectioneList());
		String isTheoryOrPractical = handler.checkIfTheoryOrPractical(to
				.getSubjectId());

		if (isTheoryOrPractical != null && isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("t")) {
			objform.setIsTheoryOrPractical("t");

		} else if (isTheoryOrPractical != null
				&& isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("p")) {
			objform.setIsTheoryOrPractical("p");
		} else {
			objform.setIsTheoryOrPractical("b");
		}

		return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_EDIT);

	}

	// ---------------end of editted data----------------------------#

	// ----------------define attendance marks--------------------------------#
	public ActionForward defineAttendanceMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		setUserId(request, objform);
		objform.clearPage();
		objform.setListAttendanceDetails(handler.displayAttendanceDetails(
				objform.getSubId(), objform.getCourseId()));

		objform.setCourseId(objform.getCourseId());
		return mapping
				.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
	}

	public ActionForward editAttendenceDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String mode = "Edit";
		objform = handler.getAttendanceUpdatableForm(objform, mode);
		setRequestToList(objform, request);
		objform.setCourseId(objform.getCourseId());
		request.setAttribute("examAttOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping
				.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
	}

	private ExamSubjectDefCourseForm setRequestToList(
			ExamSubjectDefCourseForm objform, HttpServletRequest request)
			throws Exception {
		objform.setListAttendanceDetails(handler.displayAttendanceDetails(
				objform.getSubId(), objform.getCourseId()));

		return objform;
	}

	// -------------------update attendance-------------//

	public ActionForward updateAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		try {
			if (errors.isEmpty()) {

				if (objform.getStartPercentage().trim().length() == 0
						|| objform.getEndPercentage().trim().length() == 0
						|| objform.getEndPercentage().trim().length() == 0) {
					if (objform.getStartPercentage().trim().length() == 0) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.attendanceMarks.from.empty"));
						saveErrors(request, errors);
					} else {
						errors.add("error", new ActionError(
								"knowledgepro.exam.attendanceMarks.to.empty"));
						saveErrors(request, errors);
					}

					request.setAttribute("examAttOperation", "edit");
					request.setAttribute("Update", "Update");
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
				}

				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue()) > Double.valueOf(100)
						|| (Double.valueOf(objform.getEndPercentage().trim())
								.doubleValue() > Double.valueOf(100))
						|| (Double.valueOf(objform.getStartPercentage().trim())
								.doubleValue()) <= Double.valueOf(0)
						|| (Double.valueOf(objform.getEndPercentage().trim())
								.doubleValue() <= Double.valueOf(0))) {
					objform.setCourseId(objform.getCourseId());
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.attendanceMarks.percInvalid.GreatrThanHundred"));
					saveErrors(request, errors);
					request.setAttribute("examAttOperation", "edit");
					request.setAttribute("Update", "Update");
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}
				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {
					objform.setCourseId(objform.getCourseId());

					errors.add("error", new ActionError(
							"knowledgepro.exam.attendanceMarks.percInvalid"));
					saveErrors(request, errors);
					request.setAttribute("examAttOperation", "edit");
					request.setAttribute("Update", "Update");
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}
				if ((Double.valueOf(objform.getMarks().trim()).doubleValue()) > Double
						.valueOf(100)
						|| (Double.valueOf(objform.getMarks().trim())
								.doubleValue()) <= Double.valueOf(0)) {
					objform.setCourseId(objform.getCourseId());
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.attendanceMarks.GreatrThanHundred"));
					saveErrors(request, errors);
					request.setAttribute("examAttOperation", "edit");
					request.setAttribute("Update", "Update");
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}

				else {
					boolean update = false;
					if (objform.getOrgStartPercentage().equals(
							objform.getStartPercentage())
							&& objform.getOrgEndPercentage().equals(
									objform.getEndPercentage())) {
						update = true;

					}

					handler.updateAttendance(Integer.parseInt(objform.getId()),
							Integer.parseInt(objform.getSubId()), objform
									.getStartPercentage().trim(), objform
									.getEndPercentage().trim(), objform
									.getMarks().trim(), update, objform
									.getCourseId());
					objform.setCourseId(objform.getCourseId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					objform.setCourseId(objform.getCourseId());
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}
			}

		} catch (DuplicateException e1) {
			objform.setCourseId(objform.getCourseId());
			errors.add("error", new ActionError(
					"knowledgepro.exam.attendanceMarks.exists"));
			saveErrors(request, errors);
			setRequestToList(objform, request);
			request.setAttribute("examAttOperation", "edit");
			request.setAttribute("Update", "Update");
			return mapping
					.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.attendanceMarks.reactivate", e1
									.getID()));
			saveErrors(request, errors);
		}
		request.setAttribute("examAttOperation", "edit");
		request.setAttribute("Update", "Update");
		objform.setCourseId(objform.getCourseId());
		objform.setListAttendanceDetails(handler.displayAttendanceDetails(
				objform.getSubId(), objform.getCourseId()));
		return mapping
				.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
	}

	public ActionForward deleteAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();

		setUserId(request, objform);
		handler.deleteAttendanceMarks(Integer.parseInt(objform.getId()),
				objform.getUserId());
		objform.setCourseId(objform.getCourseId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.attendanceMarks.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.clearPage();
		objform.setListAttendanceDetails(handler.displayAttendanceDetails(
				objform.getSubId(), objform.getCourseId()));
		return mapping
				.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
	}

	public ActionForward addAtttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		errors = validateData(objform);
		saveErrors(request, errors);

		try {

			if (errors.isEmpty()) {
				String marks = objform.getMarks();
				if (marks != null && marks.trim().length() > 8) {

					if (marks.trim().charAt(8) != '.') {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.subjectDefinitionCourseWise.rangeValue"));
						saveErrors(request, errors);
						objform.setListAttendanceDetails(handler
								.displayAttendanceDetails(objform.getSubId(),
										objform.getCourseId()));
						return mapping
								.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
					}

				}

				if (objform.getMarks().length() <= 0
						|| objform.getStartPercentage().length() <= 0
						|| objform.getEndPercentage().length() <= 0) {

					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.subjectDefinitionCourseWise.empty"));
					saveErrors(request, errors);
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
				}

				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue()) > Double.valueOf(100)
						|| (Double.valueOf(objform.getEndPercentage().trim())
								.doubleValue() > Double.valueOf(100))
						|| (Double.valueOf(objform.getStartPercentage().trim())
								.doubleValue()) <= Double.valueOf(0)
						|| (Double.valueOf(objform.getEndPercentage().trim())
								.doubleValue() <= Double.valueOf(0))) {
					objform.setCourseId(objform.getCourseId());
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.attendanceMarks.percInvalid.GreatrThanHundred"));
					saveErrors(request, errors);

					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}
				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" From %", " To %");
					errors.add("messages", message);
					saveMessages(request, errors);

					objform.setCourseId(objform.getCourseId());
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				}
				if ((Double.valueOf(objform.getMarks().trim()).doubleValue()) > Double
						.valueOf(100)
						|| (Double.valueOf(objform.getMarks().trim())
								.doubleValue()) <= Double.valueOf(0)) {
					objform.setCourseId(objform.getCourseId());
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.attendanceMarks.GreatrThanHundred"));
					saveErrors(request, errors);
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);

				} else {
					String mode = "add";
					List<Integer> subIds = new ArrayList<Integer>();
					subIds.add(Integer.parseInt(objform.getSubId()));
					objform.setCourseId(objform.getCourseId());
					handler.addAttendance(subIds, objform.getStartPercentage()
							.trim(), objform.getEndPercentage().trim(), objform
							.getMarks(), objform.getUserId(), mode, objform
							.getCourseId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					objform.setListAttendanceDetails(handler
							.displayAttendanceDetails(objform.getSubId(),
									objform.getCourseId()));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
				}
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.attendanceMarks.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.attendanceMarks.reactivate", e1
									.getID()));
			saveErrors(request, errors);
		} finally {

		}
		objform.setCourseId(objform.getCourseId());
		objform.setListAttendanceDetails(handler.displayAttendanceDetails(
				objform.getSubId(), objform.getCourseId()));
		return mapping
				.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE_ATTENDANCE_MARKS);
	}

	private ActionErrors validateData(ExamSubjectDefCourseForm objform) {
		String pMarks = objform.getStartPercentage();
		String tMarks = objform.getEndPercentage();
		String attMarks = objform.getAttendanceMarks();
		boolean isError = false;

		if (!(tMarks == null && tMarks.length() == 0)) {

			if (splCharValidation(tMarks)) {

				isError = true;

			}

		}

		if (!(pMarks == null && pMarks.length() == 0)) {
			if (splCharValidation(pMarks)) {

				isError = true;

			}

		}

		if (!(attMarks == null || attMarks.length() == 0 || attMarks.equals(""))) {
			if (splCharValidation(pMarks)) {

				isError = true;

			}

		}
		if (isError) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
		}

		return errors;
	}

	public ActionForward submittedSubjectAttendanceMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();

		return mapping
				.findForward(CMSConstants.EXAM_SUBDEF_COURSEWISE_SUBMITTED_ATTENDANCE_MARKS);
	}

	// ----------------end--------------------------------#
	// ----------------Grade/Class Definition--------------------------------#
	public ActionForward gradeClassDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		objform.clearPage();
		
		objform.setSubjectCode(objform.getSubjectCode());
		objform.setSubId(objform.getSubId());
		objform.setCourseId(objform.getCourseId());
		objform.setSubjectName(objform.getSubjectName());
		objform.setListGradeDefinition(handler.displayGradeDefinition(objform
				.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
		return mapping
				.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
	}

	public ActionForward addGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		errors = validateData(objform);
		saveErrors(request, errors);
		ArrayList<Integer> listSubjectId = new ArrayList<Integer>();

		try {
			if (errors.isEmpty()) {

				if (objform.getStartPercentage().length() < 0
						|| objform.getEndPercentage().length() <= 0) {

					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.subjectDefinitionCourseWise.empty"));
					saveErrors(request, errors);
					objform.setCourseId(objform.getCourseId());
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);

				}

				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" Start %", " End %");
					errors.add("messages", message);
					saveMessages(request, errors);
					objform.setCourseId(objform.getCourseId());
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
				} else {
					String mode = "add";
					int subjectId = Integer.parseInt(objform.getSubId());
					listSubjectId.add(subjectId);
					handler.addGDAdd(listSubjectId, objform
							.getStartPercentage().trim(), objform
							.getEndPercentage().trim(), objform.getGrade()
							.trim(), objform.getInterpretation().trim(),
							objform.getResultClass().trim(), objform
									.getGradePoint().trim(), objform
									.getUserId(), mode, objform.getCourseId(),Integer.parseInt(objform.getAcademicYear()));
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinition.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					objform.setCourseId(objform.getCourseId());
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));

					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);

				}
			} else {

				objform.setSubjectCode(objform.getSubjectCode());
				objform.setSubId(objform.getSubId());
				objform.setCourseId(objform.getCourseId());
				objform.setSubjectName(objform.getSubjectName());
				objform.setListGradeDefinition(handler.displayGradeDefinition(objform
						.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));

				return mapping
						.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinition.exists"));
			saveErrors(request, errors);
			objform.setCourseId(objform.getCourseId());
			objform.setSubjectCode(objform.getSubjectCode());
			objform.setSubId(objform.getSubId());
			objform.setSubjectName(objform.getSubjectName());
			objform.setListGradeDefinition(handler.displayGradeDefinition(objform
					.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
			return mapping
					.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);

		}
		objform.setSubjectCode(objform.getSubjectCode());
		objform.setSubId(objform.getSubId());
		objform.setSubjectName(objform.getSubjectName());
		objform.setListGradeDefinition(handler.displayGradeDefinition(objform
				.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));

		return mapping
				.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
	}

	public ActionForward editGCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		String mode = "Edit";
		objform = handler.getUpdatableForm(objform, mode);
		objform.setCourseId(objform.getCourseId());
		setRequestToList(objform, request, objform.getOrgSubid());
		request.setAttribute("examGDOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping
				.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
	}

	private void setRequestToList(ExamSubjectDefCourseForm objform,
			HttpServletRequest request, int subID) throws Exception {

		objform.setListGradeDefinition(handler.displayGradeDefinition(objform
				.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
		ArrayList<Integer> listSubIDId = new ArrayList<Integer>();
		listSubIDId.add(subID);
		objform.setSubjectCode(objform.getSubjectCode());
		objform.setSubId(objform.getSubId());
		objform.setSubjectName(objform.getSubjectName());
		objform.setId(objform.getId());

	}

	public ActionForward updateGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		try {
			if (errors.isEmpty()) {

				if (objform.getStartPercentage().length() <= 0
						|| objform.getEndPercentage().length() <= 0) {

					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.subjectDefinitionCourseWise.empty"));
					saveErrors(request, errors);
					ArrayList<Integer> listSubId = new ArrayList<Integer>();
					listSubId.add(Integer.parseInt(objform.getSubId()));
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
					request.setAttribute("examGDOperation", "edit");
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setCourseId(objform.getCourseId());
					objform.setSubjectName(objform.getSubjectName());
					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
				}

				if ((Double.valueOf(objform.getStartPercentage().trim())
						.doubleValue() > Double.valueOf(
						objform.getEndPercentage().trim()).doubleValue())) {

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.percInvalid",
							" Start %", " End %");
					errors.add("messages", message);
					saveMessages(request, errors);
					ArrayList<Integer> listSubId = new ArrayList<Integer>();
					listSubId.add(Integer.parseInt(objform.getSubId()));
					objform.setCourseId(objform.getCourseId());
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
					request.setAttribute("examGDOperation", "edit");
					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);

				} else {

					boolean update = false;
					ExamSubDefinitionCourseUpdateTO updateTO = new ExamSubDefinitionCourseUpdateTO();
					updateTO = setToUpdateTO(updateTO, objform);

					String mode = "update";
					handler.update(updateTO, mode, update,Integer.parseInt(objform.getAcademicYear()));
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.gradeDefinition.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
					ArrayList<Integer> listSubId = new ArrayList<Integer>();
					listSubId.add(Integer.parseInt(objform.getSubId()));
					objform.setListGradeDefinition(handler.displayGradeDefinition(objform
							.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));

					objform.setSubjectCode(objform.getSubjectCode());
					objform.setSubId(objform.getSubId());
					objform.setSubjectName(objform.getSubjectName());
					objform.setCourseId(objform.getCourseId());
					return mapping
							.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
				}
			} else {
				ArrayList<Integer> listSubId = new ArrayList<Integer>();
				listSubId.add(Integer.parseInt(objform.getSubId()));
				objform.setCourseId(objform.getCourseId());
				objform.setListGradeDefinition(handler.displayGradeDefinition(objform
						.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
				request.setAttribute("examGDOperation", "edit");
				return mapping
						.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.gradeDefinition.exists"));
			saveErrors(request, errors);
			ArrayList<Integer> listSubId = new ArrayList<Integer>();
			listSubId.add(Integer.parseInt(objform.getSubId()));
			objform.setCourseId(objform.getCourseId());
			objform.setListGradeDefinition(handler.displayGradeDefinition(objform
					.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
			request.setAttribute("examGDOperation", "edit");
			return mapping
					.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
		} catch (ReActivateException e1) {
			errors
					.add("error", new ActionError(
							"knowledgepro.exam.gradeDefinition.reactivate", e1
									.getID()));
			saveErrors(request, errors);
			ArrayList<Integer> listSubId = new ArrayList<Integer>();
			listSubId.add(Integer.parseInt(objform.getSubId()));
			objform.setListGradeDefinition(handler.displayGradeDefinition(objform
					.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
			return mapping
					.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
		}

	}

	private ExamSubDefinitionCourseUpdateTO setToUpdateTO(
			ExamSubDefinitionCourseUpdateTO updateTO,
			ExamSubjectDefCourseForm objform) {
		int courseId = 0;
		updateTO.setStartPercentage(objform.getStartPercentage());
		updateTO.setEndPercentage(objform.getEndPercentage());
		updateTO.setOrgStartPercentage(objform.getOrgStartPercentage());
		updateTO.setOrgEndPercentage(objform.getOrgEndPercentage());
		updateTO.setId(Integer.parseInt(objform.getId()));
		updateTO.setSubjectId(Integer.parseInt(objform.getSubId()));
		updateTO.setInterpretation(objform.getInterpretation().trim());
		updateTO.setResultClass(objform.getResultClass());
		updateTO.setGradePoint(objform.getGradePoint().trim());
		updateTO.setGrade(objform.getGrade());
		updateTO.setUserId(objform.getUserId());
		updateTO.setOrgGrade(objform.getOrgGrade());

		if (!(objform.getCourseId().trim().length() == 0 && objform
				.getCourseId() == null)) {

			courseId = Integer.parseInt(objform.getCourseId());

		}

		updateTO.setCourseId(courseId);

		return updateTO;
	}

	public ActionForward deleteGDAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		handler.delete_SubjectCourseWiseGradeDef(Integer.parseInt(objform
				.getId()));
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.gradeDefinition.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setSubjectCode(objform.getSubjectCode());
		objform.setSubId(objform.getSubId());
		objform.setCourseId(objform.getCourseId());
		objform.setSubjectName(objform.getSubjectName());
		objform.setListGradeDefinition(handler.displayGradeDefinition(objform
				.getSubId(), objform.getCourseId(),Integer.parseInt(objform.getAcademicYear())));
		return mapping.findForward(CMSConstants.EXAM_SUB_COURSEWISE_CLASS_DEFINITION);
	}

	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]+\\.");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
	/**
	 * 
	 * @param examSubjectDefCourseForm
	 */
	public void setprogramMapToRequest(ExamSubjectDefCourseForm examSubjectDefCourseForm, HttpServletRequest request) {
		if (examSubjectDefCourseForm.getProgramTypeId() != null && (!examSubjectDefCourseForm.getProgramTypeId().isEmpty())) {
			 Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(examSubjectDefCourseForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
			//examSubjectDefCourseForm.setProgramList(programMap);
		}
	}
	/**
	 * 
	 * @param examSubjectDefCourseForm
	 * @throws Exception
	 */
	public void setProgramtypelist(ExamSubjectDefCourseForm examSubjectDefCourseForm) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if (programTypeList != null) {
			examSubjectDefCourseForm.setProgramTypeList(programTypeList);
		}
	}
	
	// vinodha - Copy SubjectDefinitionCoursewise to other AcademicYears
	public ActionForward copySubjectDefinitionCoursewise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		ActionErrors errors = objform.validate(mapping, request);
		String st = request.getParameter("courseIds");
		String str[]=st.split(",");
		if(str==null || str.length==0 || st.isEmpty()){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				objform.setCourseIds(str);
				List<String> notExist=handler.checkExisted(objform);
				if(notExist!=null && !notExist.isEmpty()){
					Iterator<String> itr=notExist.iterator();
					StringBuilder courseName=new StringBuilder();
					while (itr.hasNext()) {
						String name = (String) itr.next();
						courseName.append(" ").append(name);
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectdefinitioncoursewise.notExisted",courseName));
				}
				if(errors.isEmpty()){
					return mapping.findForward(CMSConstants.EXAM_SUBJECT_DEFINITION_COURSEWISE_COPY);
				}else {
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
		}
	}
	
	public ActionForward copySubjectDefinitionCoursewiseByYear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = objform.validate(mapping, request);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			try {
				List<String> notExist=handler.checkExistedCopy(objform);
				if(notExist!=null && !notExist.isEmpty()){
					Iterator<String> itr=notExist.iterator();
					StringBuilder courseName = new StringBuilder();
					while (itr.hasNext()) {
						String name = (String) itr.next();
						courseName.append(" ").append(name);
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectdefinitioncoursewise.Existed",courseName));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_SUBJECT_DEFINITION_COURSEWISE_COPY);
				}
				else{
					List<String> list=handler.getSubjectsForCopy(objform);
					if(list==null || list.isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectdefinitioncoursewisesubjectgroup.notDefined",objform.getToYear()));
					}
					if(errors.isEmpty()){
						boolean isCopy=handler.copySubjectDefinitionCourseWise(list,objform);
						if(isCopy){
							objform.resetFields();
							ActionMessage message = new ActionMessage("knowledgepro.exam.subjectdefinitioncoursewisesubjectgroup.copy");
							messages.add("messages", message);
							saveMessages(request, messages);
						}else{
							errors.add("error", new ActionError("knowledgepro.exam.subjectdefinitioncoursewisesubjectgroup.copy.failed"));
							saveErrors(request, errors);
						}
					}else {
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.EXAM_SUBJECT_DEFINITION_COURSEWISE_COPY);
					}
				}
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
		}
		return mapping.findForward(CMSConstants.EXAM_SUB_DEF_COURSE_WISE);
	}
	
}
