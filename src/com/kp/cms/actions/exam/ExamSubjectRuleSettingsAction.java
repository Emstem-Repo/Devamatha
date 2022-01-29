package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamSubjectRuleSettingsForm;
import com.kp.cms.handlers.exam.ExamSubjectRuleSettingsFormTOHandler;
import com.kp.cms.handlers.exam.ExamSubjectRuleSettingsHandler;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsEditTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamSubjectRuleSettingsAction extends BaseDispatchAction {

	private static Log log = LogFactory
			.getLog(ExamSubjectRuleSettingsAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamSubjectRuleSettingsHandler handler = new ExamSubjectRuleSettingsHandler();
	ExamSubjectRuleSettingsFormTOHandler sesHandler = new ExamSubjectRuleSettingsFormTOHandler();
	HttpSession session = null;

	public ActionForward initSubjectRuleSet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initSubjectRuleSet..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		cleanUpPageData(objform);
		session = request.getSession(false);
		objform.resetSessionValues(session);
		objform.setProgramTypeList(handler.getProgramTypeList());
		log.info("exit initSubjectRuleSet..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered Add..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String[] str = request.getParameterValues("selectedCourse");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		
		if (errors.isEmpty()) {
			try {

				if (objform.getBack() != null && objform.getBack().equalsIgnoreCase("Back")) {
					if (session != null) {
						objform.setProgramName(objform.getProgramName());
						// setting TheoryInternalTO to session
						// sesHandler.setToTheoryInternalSession(objform,
						// session, "add");
						Object object = session.getAttribute("TheoryInternal");
						ExamSubjectRuleSettingsTheoryInternalTO theoryTO = (object == null ? new ExamSubjectRuleSettingsTheoryInternalTO()
								: (ExamSubjectRuleSettingsTheoryInternalTO) object);
						objform.setAttTO(theoryTO.getListAttendanceTO());
						objform.setSubInternalList(theoryTO
								.getSubInternalList());
						objform.setAssignmentList(theoryTO.getAssignmentList());
						objform.setTheoryIntTO(theoryTO);

						if (objform.getTheoryESETO() != null)
							objform
									.getTheoryESETO()
									.setMultipleAnswerScriptList(
											handler
													.getMultipleAnswerScriptsDetails());
					}
					objform.setBack(null);

				} else {
					setRequest(objform, request);
					int programTypeId = (objform.getSelectedProgramType() != null
							&& objform.getSelectedProgramType().trim().length() > 0 ? Integer
							.parseInt(objform.getSelectedProgramType())
							: 0);
					objform.setProgramName(handler
							.getProgramNameByProgramTypeId(programTypeId));
					if (!handler.chkNewSubjects(objform.getAcademicYear(),
							objform.getSchemeName(), objform.getListCourses())) {
						handler.isDuplicated(objform.getAcademicYear(), objform
								.getSchemeName(), objform.getListCourses());
					}
				}
			} catch (DuplicateException duplicateException) {
				objform.setAcademicYear(objform.getAcademicYear());
				ExamSubjectRuleSettingsTO duplecateTO = new ExamSubjectRuleSettingsTO();
				BeanUtils.copyProperty(duplecateTO, "academicYear", objform
						.getAcademicYear());
				duplecateTO.setAcademicYear(objform.getAcademicYear());
				duplecateTO.setCourseIdList(objform.getListCourses());
				duplecateTO.setSchemeType(objform.getSchemeName());
				duplecateTO.setProgramName(objform.getProgramName());
				duplecateTO.setProgramType(objform.getSelectedProgramType());
				session.setAttribute("duplecateTO", duplecateTO);
				errors.add("error", new ActionError(
						"knowledgepro.exam.subjectRuleSettings.exists"));
				saveErrors(request, errors);
				objform.setProgramTypeList(handler.getProgramTypeList());
				objform.setMapCourse(handler.getCoursesByProgramTypes(objform
						.getSelectedProgramType()));
				return mapping
						.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
			} catch (ReActivateException reActivateException) {
				ExamSubjectRuleSettingsTO reactivationTO = new ExamSubjectRuleSettingsTO();
				reactivationTO.setAcademicYear(objform.getAcademicYear());
				reactivationTO.setCourseIdList(objform.getListCourses());
				reactivationTO.setSchemeType(objform.getSchemeName());
				reactivationTO.setProgramName(objform.getProgramName());
				reactivationTO.setProgramType(objform.getSelectedProgramType());
				session.setAttribute("reactivationTO", reactivationTO);
				errors.add("error", new ActionError(
						"knowledgepro.exam.subjectRuleSettings.reactivate",
						reActivateException.getID()));
				saveErrors(request, errors);
				if (objform.getSelectedProgramType() != null
						&& objform.getSelectedProgramType().trim().length() > 0) {
					objform.setMapCourse(handler
							.getCoursesByProgramTypes(objform
									.getSelectedProgramType()));
				}
				objform.setProgramTypeList(handler.getProgramTypeList());
				return mapping
						.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
			}

			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
		} else {
			saveErrors(request, errors);
			// Multi select values are not clearing the data thats y we have to get values from request scope and set into form
			// Below Line Written By Balaji
			objform.setSelectedCourse(str);
			if (objform.getSelectedProgramType() != null
					&& objform.getSelectedProgramType().trim().length() > 0) {
				objform.setMapCourse(handler.getCoursesByProgramTypes(objform
						.getSelectedProgramType()));
			}
		}

		objform.setProgramTypeList(handler.getProgramTypeList());
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	private void setRequest(ExamSubjectRuleSettingsForm objform,
			HttpServletRequest request) throws Exception {
		if (objform != null) {
			String[] str = request.getParameterValues("selectedCourse");
			ArrayList<Integer> listCourses = new ArrayList<Integer>();
			StringBuilder courseIds=new StringBuilder();
			if (str != null && str.length != 0) {
				for (int x = 0; x < str.length; x++) {

					listCourses.add(Integer.parseInt(str[x]));
					courseIds.append(str[x]).append(",");

				}
				courseIds.setCharAt(courseIds.length()-1, ' ');
				objform.setCourseIds(courseIds.toString().trim());
				session.setAttribute("listCourses", listCourses);

				objform.setListCourses(listCourses);
				objform.setListCourseName(handler
						.getListExamCourse(listCourses));
			}
			if (CommonUtil.checkForEmpty(objform.getSelectSchemeType())) {

				if (objform.getSelectSchemeType().equalsIgnoreCase("1")) {
					objform.setSchemeName("odd");
					handler.getSchemeType(listCourses, objform.getSchemeName());

				} else if (objform.getSelectSchemeType().equalsIgnoreCase("2")) {
					objform.setSchemeName("even");
					handler.getSchemeType(listCourses, objform.getSchemeName());

				} else {
					objform.setSchemeName("both");
					handler.getSchemeType(listCourses, objform.getSchemeName());

				}

			}
			// ----------setting default object to All
			// tabs(..ExamSubjectRuleSettingsTheoryInternalTO)

			objform
					.setTheoryIntTO(new ExamSubjectRuleSettingsTheoryInternalTO());
			objform.setTheoryESETO(new ExamSubjectRuleSettingsTheoryESETO());
			objform
					.setPracticalESETO(new ExamSubjectRuleSettingsPracticalESETO());
			objform
					.setPracticalInternalTO(new ExamSubjectRuleSettingsPracticalInternalTO());
			objform.setListCourses(listCourses);

			// setting attendance type for Theory

			ExamSubjectRuleSettingsAttendanceTO toTheory = new ExamSubjectRuleSettingsAttendanceTO();
			ExamSubjectRuleSettingsAttendanceTO toPract = new ExamSubjectRuleSettingsAttendanceTO();
			toTheory.setAttendanceTypeList(handler.getAttendanceListHashMap());
			toPract.setAttendanceTypeList(handler.getAttendanceListHashMap());
			objform.setAttTO(toTheory);
			objform.setPracticalTO(toPract);
			// setting attendance type for Practical

			objform.setAcademicYearName(handler.getacademicYear(Integer
					.parseInt(objform.getAcademicYear())));
			objform.setProgramName(handler.getProgramNameByProgramId(Integer
					.parseInt(objform.getSelectedProgramType())));
			objform.setSubInternalList(handler.getSubInternalDetails());
			objform.setSubTO(new ExamSubjectRuleSettingsSubInternalTO());
			objform
					.setSubTOPractical(new ExamSubjectRuleSettingsSubInternalTO());
			objform
					.setSubInternalListPractical(handler
							.getSubInternalDetails());
			objform.setAssignmentList(handler.getExamAssignmentDetails());
			objform.setAssignmentListPractical(handler
					.getExamAssignmentDetails());

		}
	}

	public ActionForward Copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		String[] str = request.getParameterValues("selectedCourse");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {

			setRequest(objform, request);
			objform.setCopyAcademicYear(null);
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_COPY);

		}
		if (objform.getSelectedProgramType() != null
				&& objform.getSelectedProgramType().trim().length() > 0) {
			objform.setMapCourse(handler.getCoursesByProgramTypes(objform
					.getSelectedProgramType()));
		}
		objform.setProgramTypeList(handler.getProgramTypeList());

		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	public ActionForward submittedSubjectRuleSettingsCopy(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		if (errors.isEmpty()) {

			String isCopied = handler.copy(objform);
			if (isCopied.equals("Copied")) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.subjectrulesettings.copy");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else if (isCopied.equals("DataPresent")) {

				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.subjectRuleSettings.exists.copy.academicYear"));
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_COPY);
			} else if (isCopied.equals("NoRecords")) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.subjectrulesettings.copy.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else if (isCopied.equals("Fail")) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.subjectRuleSettings.exists.copy.fail"));
				saveErrors(request, errors);
			}
			return initSubjectRuleSet(mapping, form, request, response);

		}

		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_COPY);
	}

	public ActionForward theoryESE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		if(isCancelled(request))
		{
			objform.setProgramTypeList(handler.getProgramTypeList());
			cleanUpPageData(objform);
			log.info("exit reActivateSubjectRuleMaster..");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS); 
		}
		if (objform.getBack() != null
				&& objform.getBack().equalsIgnoreCase("Back")) {
			if (session != null) {
				objform.setProgramName(objform.getProgramName());
				Object object = session.getAttribute("TheoryESE");
				ExamSubjectRuleSettingsTheoryESETO theoryESETO = (object == null ? new ExamSubjectRuleSettingsTheoryESETO()
						: (ExamSubjectRuleSettingsTheoryESETO) object);

				objform.setTheoryESETO(theoryESETO);
			}
			objform.setBack(null);

		} else {

			if (session != null && session.getAttribute("edit") == null) {
				String checkedLeave = request
						.getParameter("attTO.leaveAttendance");
				String checkedCocurr = request
						.getParameter("attTO.coCurricularAttendance");
				String checkedsubInternal = request
						.getParameter("theoryIntTO.subInternalChecked");
				String checkedAttendance = request
						.getParameter("theoryIntTO.attendanceChecked");
				String checkedAssignment = request
						.getParameter("theoryIntTO.assignmentChecked");
				objform.setProgramName(objform.getProgramName());
				if (objform.getAttTO() != null) {
					objform.getAttTO().setCheckedActiveDummyLeave(
							(checkedLeave == null ? false : true));
					objform.getAttTO().setCheckedActiveDummyCoCurr(
							(checkedCocurr == null ? false : true));

				}
				if (objform.getTheoryIntTO() != null) {
					objform.getTheoryIntTO().setCheckedActiveDummySubInt(
							(checkedsubInternal == null ? false : true));
					objform.getTheoryIntTO().setCheckedActiveDummyAttendance(
							(checkedAttendance == null ? false : true));
					objform.getTheoryIntTO().setCheckedActiveDummyAssignment(
							(checkedAssignment == null ? false : true));
				}
				// setting TheoryInternalTO to session
				sesHandler.setToTheoryInternalSession(objform, session, "add");

				if (objform.getTheoryESETO() != null)
					objform.getTheoryESETO().setMultipleAnswerScriptList(
							handler.getMultipleAnswerScriptsDetails());

			}

			if (session.getAttribute("edit") != null) {
				// --------------------------------
				String checkedLeave = request
						.getParameter("attTO.leaveAttendance");
				String checkedCocurr = request
						.getParameter("attTO.coCurricularAttendance");
				String checkedsubInternal = request
						.getParameter("theoryIntTO.subInternalChecked");
				String checkedAttendance = request
						.getParameter("theoryIntTO.attendanceChecked");
				String checkedAssignment = request
						.getParameter("theoryIntTO.assignmentChecked");
				objform.setProgramName(objform.getProgramName());
				if (objform.getAttTO() != null) {
					objform.getAttTO().setCheckedActiveDummyLeave(
							(checkedLeave == null ? false : true));
					objform.getAttTO().setCheckedActiveDummyCoCurr(
							(checkedCocurr == null ? false : true));

				}
				if (objform.getTheoryIntTO() != null) {
					objform.getTheoryIntTO().setCheckedActiveDummySubInt(
							(checkedsubInternal == null ? false : true));
					objform.getTheoryIntTO().setCheckedActiveDummyAttendance(
							(checkedAttendance == null ? false : true));
					objform.getTheoryIntTO().setCheckedActiveDummyAssignment(
							(checkedAssignment == null ? false : true));
				}
				// -----------------------------
				objform.setProgramName(objform.getProgramName());
				objform
						.setTheoryESETO(new ExamSubjectRuleSettingsTheoryESETO());
				objform = getTheoryESEBasedOnSubjectRuleId(objform, session);

				sesHandler.setToTheoryInternalSession(objform, session,
						"update");

			}
		}

		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
	}

	private ExamSubjectRuleSettingsForm getTheoryESEBasedOnSubjectRuleId(
			ExamSubjectRuleSettingsForm objform, HttpSession session) throws Exception {

		int subRuleId = 0;
		if (session.getAttribute("TheoryESE") != null) {
			session.removeAttribute("TheoryESE");
		}

		if (CommonUtil.checkForEmpty(objform.getId())) {
			subRuleId = Integer.parseInt(objform.getId());
		}

		objform.setTheoryESETO(null);
		ExamSubjectRuleSettingsTheoryESETO theoryESETO = (objform
				.getTheoryESETO() == null ? new ExamSubjectRuleSettingsTheoryESETO()
				: objform.getTheoryESETO());

		theoryESETO = handler.setTheoryESEMultipleAnswerScript(subRuleId,
				theoryESETO);

		theoryESETO = handler.setTheoryESEMultipleEvaluator(subRuleId,
				theoryESETO);
		objform.setTheoryESETO(theoryESETO);

		return objform;
	}

	public ActionForward practicalESE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		objform.setListCourses(objform.getListCourses());
		if (objform.getBack() != null
				&& objform.getBack().equalsIgnoreCase("Back")) {
			if (session != null) {
				objform.setProgramName(objform.getProgramName());
				Object object = session.getAttribute("PracticalESE");
				ExamSubjectRuleSettingsPracticalESETO practicalESETO = (object == null ? new ExamSubjectRuleSettingsPracticalESETO()
						: (ExamSubjectRuleSettingsPracticalESETO) object);
				objform.setPracticalESETO(practicalESETO);

				// ----------set check box value for subject final-----------//
				String checkedSubjectFinalTheory = request
						.getParameter("subjectFinalTheoryExamChecked");
				String checkedSubjectFinalPractical = request
						.getParameter("subjectFinalPracticalExamChecked");
				String checkedSubjectFinalInternal = request
						.getParameter("subjectFinalInternalExamChecked");
				String checkedSubjectFinalAttendance = request
						.getParameter("subjectFinalAttendanceChecked");

				if (objform.getSubjectFinalTO() != null) {
					objform
							.getSubjectFinalTO()
							.setIsSubjectFinalAttendanceChecked(
									checkedSubjectFinalAttendance == null ? false
											: true);
					objform.getSubjectFinalTO()
							.setIsSubjectFinalInternalExamChecked(
									checkedSubjectFinalInternal == null ? false
											: true);
					objform
							.getSubjectFinalTO()
							.setIsSubjectFinalPracticalExamChecked(
									checkedSubjectFinalPractical == null ? false
											: true);
					objform.getSubjectFinalTO()
							.setIsSubjectFinalTheoryExamChecked(
									checkedSubjectFinalTheory == null ? false
											: true);
				}

				// ----------------------------------------//
			}
			objform.setBack(null);

		} else {
			if (session != null && session.getAttribute("edit") == null) {
				String checkedLeave = request
						.getParameter("practicalInternalTO.leaveAttendance");
				String checkedCocurr = request
						.getParameter("practicalInternalTO.coCurricularAttendance");
				String checkedsubInternal = request
						.getParameter("practicalInternalTO.subInternalChecked");
				String checkedAttendance = request
						.getParameter("practicalInternalTO.attendanceChecked");
				String checkedAssignment = request
						.getParameter("practicalInternalTO.assignmentChecked");
				objform.setProgramName(objform.getProgramName());
				if (objform.getPracticalTO() != null) {
					objform.getPracticalTO().setCheckedActiveDummyLeave(
							(checkedLeave == null ? false : true));
					objform.getPracticalTO().setCheckedActiveDummyCoCurr(
							(checkedCocurr == null ? false : true));

				}
				if (objform.getPracticalInternalTO() != null) {
					objform
							.getPracticalInternalTO()
							.setCheckedActiveDummySubInt(
									(checkedsubInternal == null ? false : true));
					objform.getPracticalInternalTO()
							.setCheckedActiveDummyAttendance(
									(checkedAttendance == null ? false : true));
					objform.getPracticalInternalTO()
							.setCheckedActiveDummyAssignment(
									(checkedAssignment == null ? false : true));
				}
				sesHandler.setToPracticalInternalSession(objform, session,
						"add");
				objform.setMultipleAnswerScriptListPractical(handler
						.getMultipleAnswerScriptsDetails());

				objform.getPracticalESETO().setMultipleAnswerScriptList(
						handler.getMultipleAnswerScriptsDetails());

			}

			// -------------edit-------practicalESE--------------
			if (session.getAttribute("edit") != null) {
				objform
						.setPracticalESETO(new ExamSubjectRuleSettingsPracticalESETO());
				String checkedLeave = request
						.getParameter("practicalInternalTO.leaveAttendance");
				String checkedCocurr = request
						.getParameter("practicalInternalTO.coCurricularAttendance");
				String checkedsubInternal = request
						.getParameter("practicalInternalTO.subInternalChecked");
				String checkedAttendance = request
						.getParameter("practicalInternalTO.attendanceChecked");
				String checkedAssignment = request
						.getParameter("practicalInternalTO.assignmentChecked");
				objform.setProgramName(objform.getProgramName());
				if (objform.getPracticalTO() != null) {
					objform.getPracticalTO().setCheckedActiveDummyLeave(
							(checkedLeave == null ? false : true));
					objform.getPracticalTO().setCheckedActiveDummyCoCurr(
							(checkedCocurr == null ? false : true));

				}
				if (objform.getPracticalInternalTO() != null) {
					objform
							.getPracticalInternalTO()
							.setCheckedActiveDummySubInt(
									(checkedsubInternal == null ? false : true));
					objform.getPracticalInternalTO()
							.setCheckedActiveDummyAttendance(
									(checkedAttendance == null ? false : true));
					objform.getPracticalInternalTO()
							.setCheckedActiveDummyAssignment(
									(checkedAssignment == null ? false : true));
				}
				objform = getPracticalESEBasedOnSubjectRuleId(objform, session);
				sesHandler.setToPracticalInternalSession(objform, session,
						"update");
				objform.setProgramName(objform.getProgramName());
			}
		}

		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);
	}

	private ExamSubjectRuleSettingsForm getPracticalESEBasedOnSubjectRuleId(
			ExamSubjectRuleSettingsForm objform, HttpSession session) throws Exception {

		int subRuleId = 0;
		if (session.getAttribute("PracticalESE") != null) {
			session.removeAttribute("PracticalESE");
		}

		if (CommonUtil.checkForEmpty(objform.getId())) {
			subRuleId = Integer.parseInt(objform.getId());
		}

		ExamSubjectRuleSettingsPracticalESETO practicalESETO = objform
				.getPracticalESETO();

		practicalESETO = handler.setPracticalESEMultipleAnswerScript(subRuleId,
				practicalESETO);

		practicalESETO = handler.setPracticalESEMultipleEvaluator(subRuleId,
				practicalESETO);

		// ---set-theoryESETO-------- to FORM--------

		objform.setPracticalESETO(practicalESETO);
		return objform;
	}

	public ActionForward practicalInternal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		errors.clear();
		messages.clear();
		if (objform.getBack() != null
				&& objform.getBack().equalsIgnoreCase("Back")) {
			if (session != null) {

				objform.setProgramName(objform.getProgramName());
				Object object = session.getAttribute("PracticalInternal");
				ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO = (object == null ? new ExamSubjectRuleSettingsPracticalInternalTO()
						: (ExamSubjectRuleSettingsPracticalInternalTO) object);
				objform.setPracticalTO(practicalIntTO.getListAttendanceTO());
				objform.setSubInternalListPractical(practicalIntTO
						.getSubInternalList());
				objform.setAssignmentListPractical(practicalIntTO
						.getAssignmentList());
				objform.setPracticalInternalTO(practicalIntTO);
				if (objform.getPracticalESETO() != null)
					objform.getPracticalESETO().setMultipleAnswerScriptList(
							handler.getMultipleAnswerScriptsDetails());
			}
			objform.setBack(null);

		} else {
			if (session != null && session.getAttribute("edit") == null) {
				String checkedregularTheoryESE = request
						.getParameter("theoryESETO.regularTheoryESEChecked");
				String checkedMultipleAnswerScripts = request
						.getParameter("theoryESETO.multipleAnswerScriptsChecked");
				String checkedMultipleEvaluator = request
						.getParameter("theoryESETO.multipleEvaluatorChecked");

				objform.setProgramName(objform.getProgramName());
				if (objform.getTheoryESETO() != null) {
					objform.getTheoryESETO().setMultipleAnswerScriptsChecked(
							checkedMultipleAnswerScripts);
					objform.getTheoryESETO().setMultipleEvaluatorChecked(
							checkedMultipleEvaluator);
					objform.getTheoryESETO().setRegularTheoryESEChecked(
							checkedregularTheoryESE);

					objform
							.getTheoryESETO()
							.setIsMultipleAnswerScriptsChecked(
									checkedMultipleAnswerScripts == null ? false
											: true);
					objform.getTheoryESETO().setIsMultipleEvaluatorChecked(
							checkedMultipleEvaluator == null ? false : true);
					objform.getTheoryESETO().setIsRegularTheoryESEChecked(
							checkedregularTheoryESE == null ? false : true);
					if (objform.getTheoryESETO()
							.getMultipleAnswerScriptsChecked() != null) {

						List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = objform
								.getTheoryESETO().getMultipleAnswerScriptList();
						int flag = 0;
						for (ExamSubjectRuleSettingsMultipleAnswerScriptTO ansScriptTO : multipleAnswerScriptList) {

							if (ansScriptTO.getMultipleAnswerScriptTheoryESE() != null
									&& ansScriptTO
											.getMultipleAnswerScriptTheoryESE()
											.trim().length() > 0) {
								flag = 1;
								break;
							}
						}
						if (flag == 0) {
							errors
									.add(
											"error",
											new ActionError(
													"knowledgepro.exam.subjectrulesettings.multipleAnswerScript"));
							saveErrors(request, errors);

							return mapping
									.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);

						}

					}

					if (objform.getTheoryESETO().getMultipleEvaluatorChecked() != null) {

						boolean isChecked = checkMultipleEvaluator(objform
								.getTheoryESETO());
						if (isChecked) {
							errors
									.add(
											"error",
											new ActionError(
													"knowledgepro.exam.subjectrulesettings.multipleEvaluator"));
							saveErrors(request, errors);
							return mapping
									.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
						}
					}
				}
				sesHandler.setToTheoryESESession(objform, session, "add");

			}

			if (session.getAttribute("edit") != null) {
				String checkedregularTheoryESE = request
						.getParameter("theoryESETO.regularTheoryESEChecked");
				String checkedMultipleAnswerScripts = request
						.getParameter("theoryESETO.multipleAnswerScriptsChecked");
				String checkedMultipleEvaluator = request
						.getParameter("theoryESETO.multipleEvaluatorChecked");
				if (objform.getTheoryESETO() != null) {
					objform.getTheoryESETO().setMultipleAnswerScriptsChecked(
							checkedMultipleAnswerScripts);
					objform.getTheoryESETO().setMultipleEvaluatorChecked(
							checkedMultipleEvaluator);
					objform.getTheoryESETO().setRegularTheoryESEChecked(
							checkedregularTheoryESE);
					objform
							.getTheoryESETO()
							.setIsMultipleAnswerScriptsChecked(
									checkedMultipleAnswerScripts == null ? false
											: true);
					objform.getTheoryESETO().setIsMultipleEvaluatorChecked(
							checkedMultipleEvaluator == null ? false : true);
					objform.getTheoryESETO().setIsRegularTheoryESEChecked(
							checkedregularTheoryESE == null ? false : true);

					if (objform.getTheoryESETO()
							.getMultipleAnswerScriptsChecked() != null) {
						List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScript_List = objform
								.getTheoryESETO().getMultipleAnswerScriptList();
						int flag = 0;
						for (ExamSubjectRuleSettingsMultipleAnswerScriptTO ansScriptTO : multipleAnswerScript_List) {
							if (ansScriptTO.getMultipleAnswerScriptTheoryESE() != null
									&& ansScriptTO
											.getMultipleAnswerScriptTheoryESE()
											.trim().length() > 0) {
								flag = 1;
								break;

							}
						}
						if (flag == 0) {
							errors
									.add(
											"error",
											new ActionError(
													"knowledgepro.exam.subjectrulesettings.multipleAnswerScript"));
							saveErrors(request, errors);
							return mapping
									.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);

						}

					}
					if (objform.getTheoryESETO().getMultipleEvaluatorChecked() != null) {

						boolean isChecked = checkMultipleEvaluator(objform
								.getTheoryESETO());
						if (isChecked) {
							errors
									.add(
											"error",
											new ActionError(
													"knowledgepro.exam.subjectrulesettings.multipleEvaluator"));
							saveErrors(request, errors);
							return mapping
									.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
						}
					}
					sesHandler
							.setToTheoryESESession(objform, session, "update");
				}
				objform.setProgramName(objform.getProgramName());
				objform
						.setPracticalInternalTO(new ExamSubjectRuleSettingsPracticalInternalTO());
				objform = getPracticalInternalBasedOnSubjectRuleId(objform,
						session);
			}
		}
		return mapping
				.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
	}

	private ExamSubjectRuleSettingsForm getPracticalInternalBasedOnSubjectRuleId(
			ExamSubjectRuleSettingsForm objform, HttpSession session) throws Exception {

		int subRuleId = 0;
		if (CommonUtil.checkForEmpty(objform.getId())) {
			subRuleId = Integer.parseInt(objform.getId());
		}
		// -------------------For TheoryInternal-------------------
		if (session.getAttribute("PracticalInternal") != null) {
			session.removeAttribute("PracticalInternal");
		}
		objform.setPracticalInternalTO(null);
		ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO = (objform
				.getPracticalInternalTO() == null ? new ExamSubjectRuleSettingsPracticalInternalTO()
				: objform.getPracticalInternalTO());

		practicalIntTO = handler
				.setPracticalInternal(subRuleId, practicalIntTO);
		practicalIntTO = handler.setPracticalInternalAttendance(subRuleId,
				practicalIntTO);

		practicalIntTO = handler.setPracticalInternalAssignment(subRuleId,
				practicalIntTO);

		// ----set to form---------------------------
		objform
				.setSubInternalListPractical(practicalIntTO
						.getSubInternalList());

		objform.setAssignmentListPractical(practicalIntTO.getAssignmentList());

		objform.setPracticalInternalTO(handler.getFinalInternalMarksPractical(
				objform.getId(), practicalIntTO));

		objform.setPracticalTO(practicalIntTO.getListAttendanceTO());

		ExamSubjectRuleSettingsSubInternalTO subPractTO = new ExamSubjectRuleSettingsSubInternalTO();

		subPractTO.setTotalentryMaximumMarks(practicalIntTO
				.getTotalentryMaximumMarks());
		subPractTO.setTotalMaximumMarks(practicalIntTO.getTotalMaximumMarks());
		subPractTO.setTotalMinimummumMarks(practicalIntTO
				.getTotalMinimummumMarks());
		subPractTO.setSelectTheBest(practicalIntTO.getSelectTheBest());
		objform.setSubTOPractical(subPractTO);

		return objform;
	}

	public ActionForward subjectFinal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		errors.clear();
		messages.clear();

		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		if (objform.getSubjectFinalTO() != null) {
			objform.getSubjectFinalTO().setIsSubjectFinalAttendanceChecked(
					false);
			objform.getSubjectFinalTO().setIsSubjectFinalInternalExamChecked(
					false);
			objform.getSubjectFinalTO().setIsSubjectFinalTheoryExamChecked(
					false);
			objform.getSubjectFinalTO().setIsSubjectFinalPracticalExamChecked(
					false);

		}

		if (session != null && session.getAttribute("edit") == null) {
			String checkedregularTheoryESE = request
					.getParameter("practicalESETO.regularPracticalESEChecked");
			String checkedMultipleAnswerScripts = request
					.getParameter("practicalESETO.multipleAnswerScriptsChecked");
			String checkedMultipleEvaluator = request
					.getParameter("practicalESETO.multipleEvaluatorChecked");

			objform.setProgramName(objform.getProgramName());
			if (objform.getPracticalESETO() != null) {
				objform.getPracticalESETO().setMultipleAnswerScriptsChecked(
						checkedMultipleAnswerScripts);
				objform.getPracticalESETO().setMultipleEvaluatorChecked(
						checkedMultipleEvaluator);
				objform.getPracticalESETO().setRegularPracticalESEChecked(
						checkedregularTheoryESE);

				objform.getPracticalESETO().setIsMultipleAnswerScriptsChecked(
						checkedMultipleAnswerScripts == null ? false : true);
				objform.getPracticalESETO().setIsMultipleEvaluatorChecked(
						checkedMultipleEvaluator == null ? false : true);
				objform.getPracticalESETO().setIsRegularTheoryESEChecked(
						checkedregularTheoryESE == null ? false : true);
				int programTypeId = (objform.getSelectedProgramType() != null
						&& objform.getSelectedProgramType().trim().length() > 0 ? Integer
						.parseInt(objform.getSelectedProgramType())
						: 0);
				objform.setProgramName(handler
						.getProgramNameByProgramTypeId(programTypeId));
				if (objform.getPracticalESETO()
						.getMultipleAnswerScriptsChecked() != null) {

					List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = objform
							.getPracticalESETO().getMultipleAnswerScriptList();
					int flag = 0;
					for (ExamSubjectRuleSettingsMultipleAnswerScriptTO ansScriptTO : multipleAnswerScriptList) {

						if (ansScriptTO.getMultipleAnswerScriptTheoryESE() != null
								&& ansScriptTO
										.getMultipleAnswerScriptTheoryESE()
										.trim().length() > 0) {
							flag = 1;
							break;
						}
					}
					if (flag == 0) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.subjectrulesettings.multipleAnswerScript"));
						saveErrors(request, errors);

						return mapping
								.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);

					}

				}

				if (objform.getPracticalESETO().getMultipleEvaluatorChecked() != null
						&& objform.getPracticalESETO()
								.getMultipleEvaluatorChecked()
								.equalsIgnoreCase("on")) {

					boolean isChecked = checkMultipleEvaluator(objform
							.getPracticalESETO());
					if (isChecked) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.subjectrulesettings.multipleEvaluator"));
						saveErrors(request, errors);

						return mapping
								.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);

					}
				}
			}
			sesHandler.setToPracticalESESession(objform, session, "add");

		}
		// ------------edit subjectFinal------------------

		if (session.getAttribute("edit") != null) {

			String checkedregularTheoryESE = request
					.getParameter("practicalESETO.regularPracticalESEChecked");
			String checkedMultipleAnswerScripts = request
					.getParameter("practicalESETO.multipleAnswerScriptsChecked");
			String checkedMultipleEvaluator = request
					.getParameter("practicalESETO.multipleEvaluatorChecked");
			if (objform.getPracticalESETO() != null) {
				objform.getPracticalESETO().setMultipleAnswerScriptsChecked(
						checkedMultipleAnswerScripts);
				objform.getPracticalESETO().setMultipleEvaluatorChecked(
						checkedMultipleEvaluator);
				objform.getPracticalESETO().setRegularPracticalESEChecked(
						checkedregularTheoryESE);
				objform.getPracticalESETO().setIsMultipleAnswerScriptsChecked(
						checkedMultipleAnswerScripts == null ? false : true);
				objform.getPracticalESETO().setIsMultipleEvaluatorChecked(
						checkedMultipleEvaluator == null ? false : true);
				objform.getPracticalESETO().setIsRegularTheoryESEChecked(
						checkedregularTheoryESE == null ? false : true);

				if (objform.getPracticalESETO()
						.getMultipleAnswerScriptsChecked() != null) {
					List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScript_List = objform
							.getPracticalESETO().getMultipleAnswerScriptList();
					int flag = 0;
					for (ExamSubjectRuleSettingsMultipleAnswerScriptTO ansScriptTO : multipleAnswerScript_List) {
						if (ansScriptTO.getMultipleAnswerScriptTheoryESE() != null
								&& ansScriptTO
										.getMultipleAnswerScriptTheoryESE()
										.trim().length() > 0) {
							flag = 1;
							break;

						}
					}
					if (flag == 0) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.subjectrulesettings.multipleAnswerScript"));
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);

					}
				}
				if (objform.getPracticalESETO().getMultipleEvaluatorChecked() != null) {

					boolean isChecked = checkMultipleEvaluator(objform
							.getPracticalESETO());
					if (isChecked) {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.subjectrulesettings.multipleEvaluator"));
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);
					}
				}

			}
			// -----------------------------
			sesHandler.setToPracticalESESession(objform, session, "update");
			objform = getSubjectFinalDetails(objform);
			objform.setProgramName(objform.getProgramName());

		}
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SUBJECT_FINAL);
	}

	private ExamSubjectRuleSettingsForm getSubjectFinalDetails(
			ExamSubjectRuleSettingsForm objform) throws Exception {

		int subRuleId = 0;
		if (CommonUtil.checkForEmpty(objform.getId())) {
			subRuleId = Integer.parseInt(objform.getId());
		}

		objform.setSubjectFinalAttendanceChecked(null);
		objform.setSubjectFinalInternalExamChecked(null);
		objform.setSubjectFinalPracticalExamChecked(null);
		objform.setSubjectFinalTheoryExamChecked(null);
		ExamSubjectRuleSettingsTO subjectFinalTO = handler
				.getSubjectFinalDetails(subRuleId);

		objform.setMaximumSubjectFinal(subjectFinalTO.getMaximumSubjectFinal());
		objform.setMinimumSubjectFinal(subjectFinalTO.getMinimumSubjectFinal());
		objform.setValuatedSubjectFinal(subjectFinalTO
				.getValuatedSubjectFinal());
		objform.setSubjectFinalAttendanceChecked(subjectFinalTO
				.getSubjectFinalAttendanceChecked());
		objform.setSubjectFinalInternalExamChecked(subjectFinalTO
				.getSubjectFinalInternalExamChecked());
		objform.setSubjectFinalPracticalExamChecked(subjectFinalTO
				.getSubjectFinalPracticalExamChecked());
		objform.setSubjectFinalTheoryExamChecked(subjectFinalTO
				.getSubjectFinalTheoryExamChecked());
		objform.setSubjectFinalTO(null);
		objform.setSubjectFinalTO(subjectFinalTO);
		return objform;
	}

	public ActionForward submittedSubjectFinal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		// inserting all tab values

		if (session != null && session.getAttribute("edit") == null) {
			// setting practicalESE to session

			sesHandler.setToSubjectFinalSession(objform, session, "add");
			List<Integer> listIds = insertAllDetails(objform);
			if (listIds != null && listIds.size() > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.subjectrulesettings.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.subjectrulesettings.noschemes_subjects"));
				saveErrors(request, errors);
			}
		}

		if (session.getAttribute("edit") != null) {
			sesHandler.setToSubjectFinalSession(objform, session, "update");
			updateAllDetails(objform, session);
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.subjectrulesettings.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			if (session.getAttribute("editTO") != null) {

				ExamSubjectRuleSettingsTO subRuleTO = (ExamSubjectRuleSettingsTO) session
						.getAttribute("editTO");

				if (subRuleTO != null) {
					ArrayList<Integer> listCourses = (ArrayList<Integer>) subRuleTO
							.getCourseIdList();
					objform.setListCourses(listCourses);
					objform.setListCourseName(handler
							.getListExamCourse(listCourses));
					objform.setAcademicYear(subRuleTO.getAcademicYear());
					// objform.setProgramName(subRuleTO.getProgramType());

				}
				return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
			}

		}
		return initSubjectRuleSet(mapping, form, request, response);
	}

	private boolean updateAllDetails(ExamSubjectRuleSettingsForm objform,
			HttpSession session) throws Exception {
		boolean isUpdated = false;
		int subjectRuleId = 0;
		if (CommonUtil.checkForEmpty(objform.getId())) {
			subjectRuleId = Integer.parseInt(objform.getId());
		}
		isUpdated = handler.updateSubjectRuleSettings(objform, session);
		if (objform.getAssignmentList() != null

		&& objform.getAssignmentList().size() != 0) {
			List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = objform
					.getAssignmentList();
			handler.updateAssignmentDetails(listAssignmentTO, subjectRuleId);
		}

		if (objform.getAssignmentListPractical() != null
				&& objform.getAssignmentListPractical().size() != 0) {
			List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentPracticalTO = objform
					.getAssignmentListPractical();
			handler.updateAssignmentDetails(listAssignmentPracticalTO,
					subjectRuleId);
		}

		if (objform.getTheoryESETO().getMultipleAnswerScriptList() != null
				&& objform.getTheoryESETO().getMultipleAnswerScriptList()
						.size() != 0) {
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = objform
					.getTheoryESETO().getMultipleAnswerScriptList();
			handler.updateMultipleAnswerScriptDetails(listTO, subjectRuleId);
		}

		if (objform.getPracticalESETO().getMultipleAnswerScriptList() != null
				&& objform.getPracticalESETO().getMultipleAnswerScriptList()
						.size() != 0) {
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = objform
					.getPracticalESETO().getMultipleAnswerScriptList();
			handler.updateMultipleAnswerScriptDetails(listTO, subjectRuleId);
		}

		if (objform.getSubInternalList() != null
				&& objform.getSubInternalList().size() != 0) {
			List<ExamSubjectRuleSettingsSubInternalTO> subTOList = objform
					.getSubInternalList();

			handler.updateSubInternalDetails(subTOList, subjectRuleId);
		}

		if (objform.getSubInternalListPractical() != null
				&& objform.getSubInternalListPractical().size() != 0) {
			List<ExamSubjectRuleSettingsSubInternalTO> subTOPracticalList = objform
					.getSubInternalListPractical();
			handler.updateSubInternalDetails(subTOPracticalList, subjectRuleId);
		}

		if (objform.getAttTO() != null) {
			ExamSubjectRuleSettingsAttendanceTO theoryAttTO = objform
					.getAttTO();
			handler.updateAttendanceDetails(theoryAttTO, subjectRuleId);
		}
		if (objform.getPracticalTO() != null) {
			ExamSubjectRuleSettingsAttendanceTO practicalAttTO = objform
					.getPracticalTO();
			handler.updateAttendanceDetails(practicalAttTO, subjectRuleId);
		}

		if (objform.getTheoryESETO() != null) {
			ExamSubjectRuleSettingsTheoryESETO theoryESETO = objform
					.getTheoryESETO();
			handler.updateMultipleEvaluatorDetails(theoryESETO, subjectRuleId);
		}
		if (objform.getPracticalESETO() != null) {
			ExamSubjectRuleSettingsPracticalESETO practicalESETO = objform
					.getPracticalESETO();
			handler.updateMultipleEvaluatorDetails(practicalESETO,
					subjectRuleId);
		}
		return isUpdated;

	}

	// Edit from first page

	@SuppressWarnings("unchecked")
	private List<Integer> insertAllDetails(ExamSubjectRuleSettingsForm objform)
			throws Exception {

		ExamSubjectRuleSettingsSubInternalTO to = (objform.getSubTO() == null ? new ExamSubjectRuleSettingsSubInternalTO()
				: objform.getSubTO());
		ExamSubjectRuleSettingsSubInternalTO toPractical = (objform
				.getSubTOPractical() == null ? new ExamSubjectRuleSettingsSubInternalTO()
				: objform.getSubTOPractical());

		if (session.getAttribute("listCourses") != null) {
			objform.setListCourses((ArrayList<Integer>) session
					.getAttribute("listCourses"));
		}
		List<Integer> subRuleSettingsIDList = handler
				.insertSubjectRuleSettingsDetails(objform.getAcademicYear(),
						objform.getListCourses(), objform.getUserId(), objform
								.getSchemeName(), to, toPractical, session);

		if (subRuleSettingsIDList != null && subRuleSettingsIDList.size() > 0) {
			for (Integer id : subRuleSettingsIDList)

			{
				insertAllListDetails(objform, id);
			}
		}

		return subRuleSettingsIDList;

	}

	private void insertAllListDetails(ExamSubjectRuleSettingsForm objform,
			Integer subRuleSettingsID) throws Exception {

		List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = null;
		List<ExamSubjectRuleSettingsSubInternalTO> subTOList = null;
		ExamSubjectRuleSettingsAttendanceTO attTO = null;
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = null;
		int type = 0;
		if (objform.getAssignmentList() != null
				&& objform.getAssignmentList().size() > 0) {
			listAssignmentTO = objform.getAssignmentList();
			handler.addAssignmentDetails(listAssignmentTO, subRuleSettingsID
					.intValue());
		} else {
			type = 1; // for getting theory assignment list from session
			listAssignmentTO = getValuesFromSession(session, type);
			handler.addAssignmentDetails(listAssignmentTO, subRuleSettingsID
					.intValue());
			listAssignmentTO = null;

		}

		if (objform.getAssignmentListPractical() != null
				&& objform.getAssignmentListPractical().size() > 0) {
			listAssignmentTO = objform.getAssignmentListPractical();
			handler.addAssignmentDetails(listAssignmentTO, subRuleSettingsID
					.intValue());
		} else {
			type = 8; // for getting practical assignment list from session
			listAssignmentTO = getValuesFromSession(session, type);
			handler.addAssignmentDetails(listAssignmentTO, subRuleSettingsID
					.intValue());
			listAssignmentTO = null;

		}

		if (objform.getTheoryESETO() != null
				&& objform.getTheoryESETO().getMultipleAnswerScriptList() != null
				&& objform.getTheoryESETO().getMultipleAnswerScriptList()
						.size() > 0) {
			listTO = objform.getTheoryESETO().getMultipleAnswerScriptList();
			handler.addMultipleAnswerScriptDetails(listTO, subRuleSettingsID
					.intValue());
		} else {
			type = 4; // for getting theory multipleAnswerScript list from
			// session
			listTO = getValuesFromSession(session, type);
			handler.addMultipleAnswerScriptDetails(listTO, subRuleSettingsID
					.intValue());
			listTO = null;
		}

		if (objform.getPracticalESETO() != null
				&& objform.getPracticalESETO().getMultipleAnswerScriptList() != null
				&& objform.getPracticalESETO().getMultipleAnswerScriptList()
						.size() > 0) {
			listTO = objform.getPracticalESETO().getMultipleAnswerScriptList();
			handler.addMultipleAnswerScriptDetails(listTO, subRuleSettingsID
					.intValue());
		} else {
			type = 6; // for getting practical multipleAnswerScript list from
			// session
			listTO = getValuesFromSession(session, type);
			handler.addMultipleAnswerScriptDetails(listTO, subRuleSettingsID
					.intValue());
			listTO = null;
		}

		if (objform.getSubInternalList() != null
				&& objform.getSubInternalList().size() > 0) {
			subTOList = null;
			subTOList = objform.getSubInternalList();

			handler.addSubInternalDetails(subTOList, subRuleSettingsID
					.intValue());
		} else {
			type = 2;// for getting theory subInternal list from session
			subTOList = getValuesFromSession(session, type);
			handler.addSubInternalDetails(subTOList, subRuleSettingsID
					.intValue());
			subTOList = null;

		}

		if (objform.getSubInternalListPractical() != null
				&& objform.getSubInternalListPractical().size() > 0) {
			List<ExamSubjectRuleSettingsSubInternalTO> subTOPracticalList = objform
					.getSubInternalListPractical();
			handler.addSubInternalDetails(subTOPracticalList, subRuleSettingsID
					.intValue());
		} else {
			type = 9;// for getting practical subInternal list from session
			subTOList = getValuesFromSession(session, type);
			handler.addSubInternalDetails(subTOList, subRuleSettingsID
					.intValue());
			subTOList = null;
		}

		if (objform.getAttTO() != null) {
			attTO = objform.getAttTO();
			attTO.setIsTheoryPractical("t");
			handler.addAttendanceDetails(attTO, subRuleSettingsID.intValue());
		}

		else {
			type = 3;// for getting theory attendance from session
			attTO = (ExamSubjectRuleSettingsAttendanceTO) getValuesFromSession(
					session, type).get(0);
			attTO.setIsTheoryPractical("t");
			handler.addAttendanceDetails(attTO, subRuleSettingsID.intValue());
			attTO = null;

		}
		if (objform.getPracticalTO() != null) {
			attTO = objform.getPracticalTO();
			attTO.setIsTheoryPractical("p");
			handler.addAttendanceDetails(attTO, subRuleSettingsID.intValue());
		} else {
			type = 10;// for getting theory attendance from session
			attTO = (ExamSubjectRuleSettingsAttendanceTO) getValuesFromSession(
					session, type).get(0);
			attTO.setIsTheoryPractical("p");
			handler.addAttendanceDetails(attTO, subRuleSettingsID.intValue());
			attTO = null;
		}

		if (objform.getTheoryESETO() != null) {
			ExamSubjectRuleSettingsTheoryESETO theoryESETO = objform
					.getTheoryESETO();
			handler.addMultipleEvaluatorDetails(theoryESETO, subRuleSettingsID
					.intValue());
		} else {
			type = 5; // for getting theory multipleAnswerScript list from
			// session
			ExamSubjectRuleSettingsTheoryESETO theoryESETO = (ExamSubjectRuleSettingsTheoryESETO) getValuesFromSession(
					session, type).get(0);
			handler.addMultipleEvaluatorDetails(theoryESETO, subRuleSettingsID
					.intValue());
			listTO = null;
		}
		if (objform.getPracticalESETO() != null) {
			ExamSubjectRuleSettingsPracticalESETO practicalESETO = objform
					.getPracticalESETO();
			handler.addMultipleEvaluatorDetails(practicalESETO,
					subRuleSettingsID.intValue());
		} else {
			type = 7; // for getting practical multipleEvaluator list from
			// session
			ExamSubjectRuleSettingsPracticalESETO practicalESETO = (ExamSubjectRuleSettingsPracticalESETO) getValuesFromSession(
					session, type).get(0);
			handler.addMultipleEvaluatorDetails(practicalESETO,
					subRuleSettingsID.intValue());
		}

	}

	private List getValuesFromSession(HttpSession session, int type) throws Exception {
		List list = null;
		if (session != null && session.getAttribute("TheoryInternal") != null) {
			ExamSubjectRuleSettingsTheoryInternalTO theoryTO = (ExamSubjectRuleSettingsTheoryInternalTO) session
					.getAttribute("TheoryInternal");

			switch (type) {
			case 1:
				List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = (theoryTO
						.getAssignmentList() == null ? new ArrayList<ExamSubjectRuleSettingsAssignmentTO>()
						: theoryTO.getAssignmentList());
				return listAssignmentTO;
			case 2:
				List<ExamSubjectRuleSettingsSubInternalTO> subTOList = (theoryTO
						.getSubInternalList() == null ? new ArrayList<ExamSubjectRuleSettingsSubInternalTO>()
						: theoryTO.getSubInternalList());
				return subTOList;

			case 3:
				list = new ArrayList();
				list
						.add((theoryTO.getListAttendanceTO() == null ? new ExamSubjectRuleSettingsAttendanceTO()
								: theoryTO.getListAttendanceTO()));
				return list;

			}

		}

		else if (session != null
				&& session.getAttribute("PracticalInternal") != null) {
			list = null;
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO = (ExamSubjectRuleSettingsPracticalInternalTO) session
					.getAttribute("PracticalInternal");

			switch (type) {
			case 8:
				List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = (practicalIntTO
						.getAssignmentList() == null ? new ArrayList<ExamSubjectRuleSettingsAssignmentTO>()
						: practicalIntTO.getAssignmentList());
				return listAssignmentTO;
			case 9:
				List<ExamSubjectRuleSettingsSubInternalTO> subTOList = (practicalIntTO
						.getSubInternalList() == null ? new ArrayList<ExamSubjectRuleSettingsSubInternalTO>()
						: practicalIntTO.getSubInternalList());
				return subTOList;

			case 10:
				list = new ArrayList();
				list
						.add((practicalIntTO.getListAttendanceTO() == null ? new ExamSubjectRuleSettingsAttendanceTO()
								: practicalIntTO.getListAttendanceTO()));
				return list;
			}

		} else if (session != null && session.getAttribute("TheoryESE") != null) {
			list = null;
			ExamSubjectRuleSettingsTheoryESETO theoryESETO = (ExamSubjectRuleSettingsTheoryESETO) session
					.getAttribute("TheoryESE");
			switch (type) {
			case 4:
				List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = (theoryESETO
						.getMultipleAnswerScriptList() == null ? new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>()
						: theoryESETO.getMultipleAnswerScriptList());
				return listTO;
			case 5:
				list = new ArrayList();
				list
						.add((theoryESETO == null ? new ExamSubjectRuleSettingsTheoryESETO()
								: theoryESETO));
				return list;

			}
		} else if (session != null
				&& session.getAttribute("PracticalESE") != null) {
			list = null;
			ExamSubjectRuleSettingsPracticalESETO practicalESETO = (ExamSubjectRuleSettingsPracticalESETO) session
					.getAttribute("PracticalESE");
			switch (type) {
			case 6:
				List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = (practicalESETO
						.getMultipleAnswerScriptList() == null ? new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>()
						: practicalESETO.getMultipleAnswerScriptList());
				return listTO;
			case 7:
				list = new ArrayList();
				list
						.add((practicalESETO == null ? new ExamSubjectRuleSettingsPracticalESETO()
								: practicalESETO));
				return list;

			}
		}

		return new ArrayList();
	}

	public ActionForward Edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered Edit..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		
		setUserId(request, objform);
		String[] str = request.getParameterValues("selectedCourse");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			setRequest(objform, request);
			ExamSubjectRuleSettingsTO ruleTO = null;
			if (session.getAttribute("reactivationTO") != null) {
				ruleTO = (ExamSubjectRuleSettingsTO) session
						.getAttribute("reactivationTO");
			} else if (session.getAttribute("duplecateTO") != null) {
				ruleTO = (ExamSubjectRuleSettingsTO) session
						.getAttribute("duplecateTO");
			} else {
				ruleTO = new ExamSubjectRuleSettingsTO(objform
						.getAcademicYear(), objform.getSchemeName(), objform
						.getSelectedProgramType(), objform.getProgramName(),
						objform.getListCourses());
			}

			List<ExamSubjectRuleSettingsEditTO> editTO = handler
					.getSubjectDetails(ruleTO);
			if (editTO != null && editTO.size() > 0) {

				int programTypeId = (objform.getSelectedProgramType() != null
						&& objform.getSelectedProgramType().trim().length() > 0 ? Integer
						.parseInt(objform.getSelectedProgramType())
						: 0);
				objform.setProgramName(handler
						.getProgramNameByProgramTypeId(programTypeId));
				ruleTO.setEditTO(editTO);
				objform.setSubjectFinalAttendanceChecked(null);
				objform.setSubjectFinalInternalExamChecked(null);
				objform.setSubjectFinalPracticalExamChecked(null);
				objform.setSubjectFinalTheoryExamChecked(null);
				if (objform.getSubjectFinalTO() != null) {
					objform.getSubjectFinalTO()
							.setIsSubjectFinalAttendanceChecked(false);
					objform.getSubjectFinalTO()
							.setIsSubjectFinalTheoryExamChecked(false);
					objform.getSubjectFinalTO()
							.setIsSubjectFinalInternalExamChecked(false);
					objform.getSubjectFinalTO()
							.setIsSubjectFinalPracticalExamChecked(false);
				}

				objform.setEdittedSubRuleList(editTO);
				if (session.getAttribute("editTO") == null) {
					session.setAttribute("editTO", ruleTO);
				}

				return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
			}

			else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				if (objform.getSelectedProgramType() != null
						&& objform.getSelectedProgramType().trim().length() > 0) {
					objform.setMapCourse(handler
							.getCoursesByProgramTypes(objform
									.getSelectedProgramType()));
				}
				objform.setProgramTypeList(handler.getProgramTypeList());
				return mapping
						.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);

			}

		}
		if (objform.getSelectedProgramType() != null
				&& objform.getSelectedProgramType().trim().length() > 0) {
			objform.setMapCourse(handler.getCoursesByProgramTypes(objform
					.getSelectedProgramType()));
		}

		objform.setProgramTypeList(handler.getProgramTypeList());
		log.info("exiting Edit..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);

	}

	public ActionForward setEdittedSubRuleDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered setEdittedSubRuleDetails..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String progeramName = (objform.getProgramName() == null ? "" : objform
				.getProgramName());
		retainValues(objform);
		objform.setTheoryIntTO(null);
		objform.setTheoryIntTO(new ExamSubjectRuleSettingsTheoryInternalTO());
		objform = getTheoryInternalBasedOnSubjectRuleId(objform, session);
		objform.setProgramName(null);
		objform.setProgramName(progeramName);
		log.info("exiting setEdittedSubRuleDetails..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
	}

	private ExamSubjectRuleSettingsForm getTheoryInternalBasedOnSubjectRuleId(
			ExamSubjectRuleSettingsForm objform, HttpSession session) throws Exception {
		int subRuleId = 0;
		if (CommonUtil.checkForEmpty(objform.getId())) {
			subRuleId = Integer.parseInt(objform.getId());
		}
		// -------------------For TheoryInternal-------------------
		if (session.getAttribute("TheoryInternal") != null) {
			session.removeAttribute("TheoryInternal");
		}
		objform.setTheoryIntTO(null);
		ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO = (objform
				.getTheoryIntTO() == null ? new ExamSubjectRuleSettingsTheoryInternalTO()
				: objform.getTheoryIntTO());
		theoryIntTO = handler.setTheoryInternal(subRuleId, theoryIntTO);
		theoryIntTO = handler.setTheoryInternalAttendance(subRuleId,
				theoryIntTO);
		theoryIntTO = handler.setTheoryInternalAssignment(subRuleId,
				theoryIntTO);
		session.removeAttribute("TheoryInternal");
		session.setAttribute("TheoryInternal", theoryIntTO);
		// ----set to form---------------------------
		objform.setSubInternalList(theoryIntTO.getSubInternalList());
		objform.setAttTO(theoryIntTO.getListAttendanceTO());

		objform.setAssignmentList(theoryIntTO.getAssignmentList());
		objform.setTheoryIntTO(handler.getFinalInternalMarks(objform.getId(),
				theoryIntTO));
		ExamSubjectRuleSettingsSubInternalTO subTO = new ExamSubjectRuleSettingsSubInternalTO();
		subTO
				.setTotalentryMaximumMarks(theoryIntTO
						.getTotalentryMaximumMarks());
		subTO.setTotalMaximumMarks(theoryIntTO.getTotalMaximumMarks());
		subTO.setTotalMinimummumMarks(theoryIntTO.getTotalMinimummumMarks());
		subTO.setSelectTheBest(theoryIntTO.getSelectTheBest());
		objform.setSubTO(subTO);
		// --------------------For Theory ESE-------------------------
		session.setAttribute("edit", objform.getId());
		return objform;
	}

	private void retainValues(ExamSubjectRuleSettingsForm objform) throws Exception {
		objform.setListCourseName(objform.getListCourseName());
		objform.setAcademicYearName(handler.getacademicYear(Integer
				.parseInt(objform.getAcademicYear())));
		objform.setProgramName(handler.getProgramNameByProgramId(Integer
				.parseInt(objform.getSelectedProgramType())));
		objform.setSubInternalList(null);
		objform.setSubTO(null);
		objform.setSubTOPractical(null);
		objform.setSubInternalListPractical(null);
		objform.setAssignmentList(null);
		objform.setAssignmentListPractical(null);
		objform.setSubInternalList(handler.getSubInternalDetails());
		objform.setSubTO(new ExamSubjectRuleSettingsSubInternalTO());
		objform.setSubTOPractical(new ExamSubjectRuleSettingsSubInternalTO());
		objform.setSubInternalListPractical(handler.getSubInternalDetails());
		objform.setAssignmentList(handler.getExamAssignmentDetails());
		objform.setAssignmentListPractical(handler.getExamAssignmentDetails());
	}

	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered Delete..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		String[] str = request.getParameterValues("selectedCourse");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			setRequest(objform, request);
			String deleteMessage = null;
			boolean isDeleted = handler.deleteSubjectRuleSettingsDetails(objform);
			if (isDeleted) {
				deleteMessage = "knowledgepro.exam.subjectrulesettings.deleted";
			} else {
				deleteMessage = "knowledgepro.norecords";
				if (objform.getSelectedProgramType() != null
						&& objform.getSelectedProgramType().trim().length() > 0) {
					objform.setMapCourse(handler
							.getCoursesByProgramTypes(objform
									.getSelectedProgramType()));
				}
			}
			ActionMessage message = new ActionMessage(deleteMessage);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		objform.setProgramTypeList(handler.getProgramTypeList());
		log.info("exit Delete..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	public ActionForward Reactivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered reActivateSubjectRuleMaster ..");
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		setRequest(objform, request);
		ExamSubjectRuleSettingsTO reactivationTO = (ExamSubjectRuleSettingsTO) session
				.getAttribute("reactivationTO");
		boolean isReactivated = handler.reactivate(reactivationTO);
		if (isReactivated) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.subjectRuleSettings.reactivated");
			messages.add("messages", message);
			saveMessages(request, messages);
			session.removeAttribute("reactivationTO");
		} else {
			errors.add("error", new ActionError(
					"knowledgepro.exam.subjectRuleSettings.reactivation.fail"));
			saveErrors(request, errors);
			session.removeAttribute("reactivationTO");

		}
		objform.setProgramTypeList(handler.getProgramTypeList());
		cleanUpPageData(objform);
		log.info("exit reActivateSubjectRuleMaster..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	private boolean checkMultipleEvaluator(Object obj) throws Exception {
		List<String> evalIds = new ArrayList<String>();
		boolean isChecked = true;
		if (obj instanceof ExamSubjectRuleSettingsTheoryESETO) {
			ExamSubjectRuleSettingsTheoryESETO theoryTO = (ExamSubjectRuleSettingsTheoryESETO) obj;
			evalIds.add(theoryTO.getEvalId1());
			evalIds.add(theoryTO.getEvalId2());
			evalIds.add(theoryTO.getEvalId3());
			evalIds.add(theoryTO.getEvalId4());
			evalIds.add(theoryTO.getEvalId5());
			for (String evalId : evalIds) {
				if (evalId != null && evalId.trim().length() > 0) {
					isChecked = false;
					break;
				}
			}

		} else if (obj instanceof ExamSubjectRuleSettingsPracticalESETO) {
			ExamSubjectRuleSettingsPracticalESETO practicalTO = (ExamSubjectRuleSettingsPracticalESETO) obj;
			evalIds.add(practicalTO.getEvalId1());
			evalIds.add(practicalTO.getEvalId2());
			evalIds.add(practicalTO.getEvalId3());
			evalIds.add(practicalTO.getEvalId4());
			evalIds.add(practicalTO.getEvalId5());
			for (String evalId : evalIds) {
				if (evalId != null && evalId.trim().length() > 0) {
					isChecked = false;
					break;
				}
			}
		}
		return isChecked;

	}

	private void cleanUpPageData(ExamSubjectRuleSettingsForm objform) throws Exception {
		log.info("enter cleanUpPageData..");
		if (objform != null) {
			objform.setSelectSchemeType(null);
			objform.setSelectedProgramType(null);
			objform.setSelectedCourse(null);
			objform.setAcademicYear(null);
			objform.setTheoryIntTO(null);
			objform.setPracticalESETO(null);
			objform.setPracticalInternalTO(null);
			objform.setTheoryESETO(null);
			objform.setSubTO(null);
			objform.setSubTOPractical(null);
			objform.setAttTO(null);
			objform.setMaximumSubjectFinal(null);
			objform.setMinimumSubjectFinal(null);
			objform.setValuatedSubjectFinal(null);
			objform.setSubjectFinalTheoryExamChecked(null);
			objform.setSubjectFinalPracticalExamChecked(null);
			objform.setSubjectFinalInternalExamChecked(null);
			objform.setSubjectFinalAttendanceChecked(null);
			// ---subject final---
			objform.setSubjectFinalTO(null);
			objform.setSubjectFinalAttendanceChecked(null);
			objform.setSubjectFinalInternalExamChecked(null);
			objform.setSubjectFinalPracticalExamChecked(null);
			objform.setSubjectFinalTheoryExamChecked(null);
			// ------end-----------
			objform.setMapCourse(null);
			objform.setAcademicYearName(null);
			objform.setCourseIds(null);
			objform.setAssignmentList(null);
			objform.setAssignmentListPractical(null);
			objform.setCourseName(null);
			objform.setListCourses(null);
			objform.setEdittedSubRuleList(null);
			objform.setListCourseName(null);
			objform.setId(null);
			objform.setPracticalTO(null);
			objform.setProgramName(null);
			objform.setSchemeName(null);
			objform.setSubInternalList(null);
			objform.setSubInternalListPractical(null);
			objform.setBack(null);
			objform.setMultipleAnswerScriptList(null);
			objform.setMultipleAnswerScriptListPractical(null);
			objform.setSubjectName(null);
		}
		log.info("exit cleanUpPageData..");
	}

	public ActionForward deleteSubRuleDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ExamSubjectRuleSettingsForm objform = (ExamSubjectRuleSettingsForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		setRequest(objform, request);
		boolean isDeleted=ExamSubjectRuleSettingsHandler.getInstance().deleteSubjectRuleSettingSubjectWise(objform.getId());
		if (isDeleted) 
		{
			ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.deleted");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.exam.subjectrulesettings.deleted.failed"));
			saveErrors(request, errors);
		}
		objform.setProgramTypeList(handler.getProgramTypeList());
		cleanUpPageData(objform);
		log.info("exit reActivateSubjectRuleMaster..");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}
}
