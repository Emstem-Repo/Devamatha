package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kp.cms.forms.exam.ExamSubjectRuleSettingsForm;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubjectFinalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamSubjectRuleSettingsFormTOHandler extends
		ExamSubjectRuleSettingsHandler {
	Logger log = Logger.getLogger(ExamSubjectRuleSettingsFormTOHandler.class);

	public ExamSubjectRuleSettingsTheoryInternalTO setToTheoryInternalSession(
			ExamSubjectRuleSettingsForm objform, HttpSession session,
			String mode) {
		log
				.info("----------inside setToTheoryInternalsessionsionsion method------------------");

		if (CommonUtil.checkForEmpty(mode) && mode.equalsIgnoreCase("add")) {

			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList = (objform
					.getSubInternalList() == null ? new ArrayList<ExamSubjectRuleSettingsSubInternalTO>()
					: objform.getSubInternalList());
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList = (objform
					.getAssignmentList() == null ? new ArrayList<ExamSubjectRuleSettingsAssignmentTO>()
					: objform.getAssignmentList());
			ExamSubjectRuleSettingsAttendanceTO attTO = (objform.getAttTO() == null ? new ExamSubjectRuleSettingsAttendanceTO()
					: objform.getAttTO());
			ExamSubjectRuleSettingsSubInternalTO subTO = (objform.getSubTO() == null ? new ExamSubjectRuleSettingsSubInternalTO()
					: objform.getSubTO());
			ExamSubjectRuleSettingsTheoryInternalTO thTO = (objform
					.getTheoryIntTO() == null ? new ExamSubjectRuleSettingsTheoryInternalTO()
					: objform.getTheoryIntTO());

			ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO = new ExamSubjectRuleSettingsTheoryInternalTO(
					thTO.getAssignmentChecked(), assignmentList, thTO
							.getAttendanceChecked(), attTO
							.getCoCurricularAttendance(), thTO
							.getFinalEntryMaximumMarks(), thTO
							.getFinalInternalMaximumMarks(), thTO
							.getFinalInternalMinimumMarks(), attTO
							.getLeaveAttendance(), thTO.getSelectTheBest(),
					attTO.getAttendanceType(), thTO.getSubInternalChecked(),
					subInternalList, subTO.getTotalMaximumMarks(), subTO
							.getTotalMaximumMarks(), subTO
							.getTotalentryMaximumMarks());
			theoryIntTO.setListAttendanceTO(attTO);
			theoryIntTO.setCheckedActiveDummyAssignment(thTO
					.getCheckedActiveDummyAssignment());
			theoryIntTO.setCheckedActiveDummyAttendance(thTO
					.getCheckedActiveDummyAttendance());
			theoryIntTO.setCheckedActiveDummySubInt(thTO
					.getCheckedActiveDummySubInt());
			if (session.getAttribute("count") != null) {
				session.removeAttribute("TheoryInternal");
				Integer count = (session.getAttribute("count") == null ? 0
						: (Integer) session.getAttribute("count"));
				count = count + 1;
				session.setAttribute("count", count);
				session.setAttribute("listCourses", objform.getListCourses());
				session.setAttribute("TheoryInternal", theoryIntTO);
			} else {
				Integer count = 1;
				session.setAttribute("count", count);
				session.removeAttribute("TheoryInternal");
				session.setAttribute("TheoryInternal", theoryIntTO);
			}
			log.info("exiting setToTheoryInternalsessionsionsion");
			return theoryIntTO;
		} else if (CommonUtil.checkForEmpty(mode)
				&& mode.equalsIgnoreCase("update")) {

			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList = (objform
					.getSubInternalList() != null ? objform
					.getSubInternalList()
					: new ArrayList<ExamSubjectRuleSettingsSubInternalTO>());

			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList = (objform
					.getAssignmentList() != null ? objform.getAssignmentList()
					: new ArrayList<ExamSubjectRuleSettingsAssignmentTO>());
			ExamSubjectRuleSettingsAttendanceTO attTO = (objform.getAttTO() == null ? new ExamSubjectRuleSettingsAttendanceTO()
					: objform.getAttTO());
			ExamSubjectRuleSettingsSubInternalTO subTO = (objform.getSubTO() == null ? new ExamSubjectRuleSettingsSubInternalTO()
					: objform.getSubTO());

			ExamSubjectRuleSettingsTheoryInternalTO thTO = (objform
					.getTheoryIntTO() == null ? new ExamSubjectRuleSettingsTheoryInternalTO()
					: objform.getTheoryIntTO());

			objform.setSubInternalList(subInternalList);

			ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO = new ExamSubjectRuleSettingsTheoryInternalTO(
					thTO.getAssignmentChecked(), assignmentList, thTO
							.getAttendanceChecked(), attTO
							.getCoCurricularAttendance(), thTO
							.getFinalEntryMaximumMarks(), thTO
							.getFinalInternalMaximumMarks(), thTO
							.getFinalInternalMinimumMarks(), attTO
							.getLeaveAttendance(), thTO.getSelectTheBest(),
					attTO.getAttendanceType(), thTO.getSubInternalChecked(),
					subInternalList, subTO.getTotalMaximumMarks(), subTO
							.getTotalMaximumMarks(), subTO
							.getTotalentryMaximumMarks());
			theoryIntTO.setListAttendanceTO(attTO);
			theoryIntTO.setCheckedActiveDummyAssignment(thTO
					.getCheckedActiveDummyAssignment());
			theoryIntTO.setCheckedActiveDummyAttendance(thTO
					.getCheckedActiveDummyAttendance());
			theoryIntTO.setCheckedActiveDummySubInt(thTO
					.getCheckedActiveDummySubInt());
			if (session.getAttribute("count") != null) {
				Integer count = (Integer) session.getAttribute("count");
				count = count + 1;
				session.setAttribute("count", count);
				session.removeAttribute("TheoryInternal");
				session.setAttribute("TheoryInternal", theoryIntTO);

			}

			else {
				Integer count = 1;
				session.setAttribute("count", count);
				session.removeAttribute("TheoryInternal");
				session.setAttribute("TheoryInternal", theoryIntTO);

			}
			thTO = null;
			log.info("exiting setToTheoryInternalsessionsionsion");
			return theoryIntTO;
		}

		return new ExamSubjectRuleSettingsTheoryInternalTO();
	}

	public ExamSubjectRuleSettingsTheoryESETO setToTheoryESESession(
			ExamSubjectRuleSettingsForm objform, HttpSession session,
			String mode) throws Exception {
		log
				.info("---------inside setToTheoryEsessionsionession method--------------");
		ExamSubjectRuleSettingsTheoryESETO theoryESETO = null;

		if (mode.equalsIgnoreCase("add")) {
			ExamSubjectRuleSettingsTheoryESETO theoryESETOFromForm = (objform
					.getTheoryESETO() != null ? objform.getTheoryESETO()
					: new ExamSubjectRuleSettingsTheoryESETO());

			ExamSubjectRuleSettingsHandler handler = ExamSubjectRuleSettingsHandler
					.getInstance();
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = null;

			// set multiple answer script list only if multipleAnswerScript is
			// checked

			multipleAnswerScriptList = (theoryESETOFromForm
					.getMultipleAnswerScriptList() == null ? handler
					.getMultipleAnswerScriptsDetails() : theoryESETOFromForm
					.getMultipleAnswerScriptList());
			theoryESETO = new ExamSubjectRuleSettingsTheoryESETO(
					theoryESETOFromForm.getMaximumEntryMarksTheoryESE(),
					theoryESETOFromForm.getMaximumMarksTheoryESE(),
					theoryESETOFromForm.getMaximumTheoryFinalMarksTheoryESE(),
					theoryESETOFromForm.getMinimumMarksTheoryESE(),
					theoryESETOFromForm.getMinimumTheoryFinalMarksTheoryESE(),
					multipleAnswerScriptList, theoryESETOFromForm
							.getMultipleAnswerScriptTheoryESE(),
					theoryESETOFromForm.getMultipleAnswerScriptsChecked(),

					theoryESETOFromForm.getMultipleEvaluatorChecked(),
					theoryESETOFromForm.getNoOfEvaluations(),

					theoryESETOFromForm.getRegularTheoryESEChecked(),
					theoryESETOFromForm.getTypeOfEvaluation());

			theoryESETO.setEvalId1(theoryESETOFromForm.getEvalId1());
			theoryESETO.setEvalId2(theoryESETOFromForm.getEvalId2());
			theoryESETO.setEvalId3(theoryESETOFromForm.getEvalId3());
			theoryESETO.setEvalId4(theoryESETOFromForm.getEvalId4());
			theoryESETO.setEvalId5(theoryESETOFromForm.getEvalId5());
			theoryESETO.setTypeOfEvaluation(theoryESETOFromForm
					.getTypeOfEvaluation());
			theoryESETO.setIsMultipleAnswerScriptsChecked(theoryESETOFromForm
					.getIsMultipleAnswerScriptsChecked());
			theoryESETO.setIsMultipleEvaluatorChecked(theoryESETOFromForm
					.getIsMultipleEvaluatorChecked());
			theoryESETO.setIsRegularTheoryESEChecked(theoryESETOFromForm
					.getIsRegularTheoryESEChecked());
			// session.removeAttribute("TheoryESE");
			objform.setTheoryESETO(null);
			objform.setTheoryESETO(theoryESETO);
			log.info("setting TheoryESE attribute to sessionsionsion");
			session.setAttribute("TheoryESE", theoryESETO);

		} else if (mode.equalsIgnoreCase("update")) {

			ExamSubjectRuleSettingsTheoryESETO theoryESETOFromForm = (objform
					.getTheoryESETO() != null ? objform.getTheoryESETO()
					: new ExamSubjectRuleSettingsTheoryESETO());

			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = theoryESETOFromForm
					.getMultipleAnswerScriptList();

			theoryESETO = new ExamSubjectRuleSettingsTheoryESETO(
					theoryESETOFromForm.getMaximumEntryMarksTheoryESE(),
					theoryESETOFromForm.getMaximumMarksTheoryESE(),
					theoryESETOFromForm.getMaximumTheoryFinalMarksTheoryESE(),
					theoryESETOFromForm.getMinimumMarksTheoryESE(),
					theoryESETOFromForm.getMinimumTheoryFinalMarksTheoryESE(),
					multipleAnswerScriptList, theoryESETOFromForm
							.getMultipleAnswerScriptTheoryESE(),
					theoryESETOFromForm.getMultipleAnswerScriptsChecked(),

					theoryESETOFromForm.getMultipleEvaluatorChecked(),
					theoryESETOFromForm.getNoOfEvaluations(),

					theoryESETOFromForm.getRegularTheoryESEChecked(),
					theoryESETOFromForm.getTypeOfEvaluation());

			theoryESETO.setEvalId1(theoryESETOFromForm.getEvalId1());
			theoryESETO.setEvalId2(theoryESETOFromForm.getEvalId2());
			theoryESETO.setEvalId3(theoryESETOFromForm.getEvalId3());
			theoryESETO.setEvalId4(theoryESETOFromForm.getEvalId4());
			theoryESETO.setEvalId5(theoryESETOFromForm.getEvalId5());
			theoryESETO.setId1(theoryESETOFromForm.getId1());
			theoryESETO.setId2(theoryESETOFromForm.getId2());
			theoryESETO.setId3(theoryESETOFromForm.getId3());
			theoryESETO.setId4(theoryESETOFromForm.getId4());
			theoryESETO.setId5(theoryESETOFromForm.getId5());
			theoryESETO.setTypeOfEvaluation(theoryESETOFromForm
					.getTypeOfEvaluation());
			theoryESETO.setIsMultipleAnswerScriptsChecked(theoryESETOFromForm
					.getIsMultipleAnswerScriptsChecked());
			theoryESETO.setIsMultipleEvaluatorChecked(theoryESETOFromForm
					.getIsMultipleEvaluatorChecked());
			theoryESETO.setIsRegularTheoryESEChecked(theoryESETOFromForm
					.getIsRegularTheoryESEChecked());
			objform.setTheoryESETO(null);
			objform.setTheoryESETO(theoryESETO);
			log.info("setting TheoryESE attribute to session");

			// -------------set the count value into session-------------

			if (session.getAttribute("countForTheoryESE") != null) {
				Integer count = (Integer) session
						.getAttribute("countForTheoryESE");
				count = 2;
				session.setAttribute("countForTheoryESE", count);

				session.setAttribute("listCourses", objform.getListCourses());
				session.setAttribute("TheoryESE", theoryESETO);

			}

			else {
				Integer count = 1;
				session.setAttribute("countForTheoryESE", count);
				session.removeAttribute("TheoryESE");
				session.setAttribute("TheoryESE", theoryESETO);
			}

		}
		return theoryESETO;

	}

	public ExamSubjectRuleSettingsPracticalInternalTO setToPracticalInternalSession(
			ExamSubjectRuleSettingsForm objform, HttpSession session,
			String mode) {
		log.info("Inside setToPracticalInternalsessionsionsion method");
		ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO = null;
		if (mode.equalsIgnoreCase("add"))

		{
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTOFromForm = (objform
					.getPracticalInternalTO() == null ? new ExamSubjectRuleSettingsPracticalInternalTO()
					: objform.getPracticalInternalTO());
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList = objform
					.getSubInternalListPractical();
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList = objform
					.getAssignmentListPractical();
			ExamSubjectRuleSettingsAttendanceTO attTO = (objform
					.getPracticalTO() == null ? new ExamSubjectRuleSettingsAttendanceTO()
					: objform.getPracticalTO());
			ExamSubjectRuleSettingsSubInternalTO subIntTO = (objform
					.getSubTOPractical() == null ? new ExamSubjectRuleSettingsSubInternalTO()
					: objform.getSubTOPractical());

			practicalIntTO = new ExamSubjectRuleSettingsPracticalInternalTO(
					practicalIntTOFromForm.getAssignmentChecked(),
					assignmentList, practicalIntTOFromForm
							.getAttendanceChecked(), attTO
							.getCoCurricularAttendance(),
					practicalIntTOFromForm.getFinalEntryMaximumMarks(),
					practicalIntTOFromForm.getFinalInternalMaximumMarks(),
					practicalIntTOFromForm.getFinalInternalMinimumMarks(),
					attTO.getLeaveAttendance(), subIntTO.getSelectTheBest(),
					practicalIntTOFromForm.getSelectedAttendanceType(),
					practicalIntTOFromForm.getSubInternalChecked(),
					subInternalList, subIntTO.getTotalMaximumMarks(), subIntTO
							.getTotalMaximumMarks(), subIntTO
							.getTotalentryMaximumMarks());
			practicalIntTO.setListAttendanceTO(attTO);
			practicalIntTO.setCheckedActiveDummyAssignment(practicalIntTOFromForm
					.getCheckedActiveDummyAssignment());
			practicalIntTO.setCheckedActiveDummyAttendance(practicalIntTOFromForm
					.getCheckedActiveDummyAttendance());
			practicalIntTO.setCheckedActiveDummySubInt(practicalIntTOFromForm
					.getCheckedActiveDummySubInt());
			if (session.getAttribute("PracticalInternal") != null) {
				log
						.info("setting PracticalInternal attribute to sessionsionsion ");

				session.setAttribute("PracticalInternal", practicalIntTO);
			} else {
				session.setAttribute("PracticalInternal", practicalIntTO);
			}
		} else if (mode.equalsIgnoreCase("update")) {
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTOFromForm = objform
					.getPracticalInternalTO();
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList = objform
					.getSubInternalListPractical();
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList = objform
					.getAssignmentListPractical();
			ExamSubjectRuleSettingsAttendanceTO attTO = objform
					.getPracticalTO();
			ExamSubjectRuleSettingsSubInternalTO subIntTO = objform
					.getSubTOPractical();

			practicalIntTO = new ExamSubjectRuleSettingsPracticalInternalTO(
					practicalIntTOFromForm.getAssignmentChecked(),
					assignmentList, practicalIntTOFromForm
							.getAttendanceChecked(), attTO
							.getCoCurricularAttendance(),
					practicalIntTOFromForm.getFinalEntryMaximumMarks(),
					practicalIntTOFromForm.getFinalInternalMaximumMarks(),
					practicalIntTOFromForm.getFinalInternalMinimumMarks(),
					attTO.getLeaveAttendance(), subIntTO.getSelectTheBest(),
					practicalIntTOFromForm.getSelectedAttendanceType(),
					practicalIntTOFromForm.getSubInternalChecked(),
					subInternalList, subIntTO.getTotalMaximumMarks(), subIntTO
							.getTotalMaximumMarks(), subIntTO
							.getTotalentryMaximumMarks());
			practicalIntTO.setListAttendanceTO(attTO);
			practicalIntTO.setCheckedActiveDummyAssignment(practicalIntTOFromForm
					.getCheckedActiveDummyAssignment());
			practicalIntTO.setCheckedActiveDummyAttendance(practicalIntTOFromForm
					.getCheckedActiveDummyAttendance());
			practicalIntTO.setCheckedActiveDummySubInt(practicalIntTOFromForm
					.getCheckedActiveDummySubInt());
			session.removeAttribute("PracticalInternal");

			log.info("setting PracticalInternal attribute to sessionsionsion ");

			session.setAttribute("PracticalInternal", practicalIntTO);

		}
		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalESETO setToPracticalESESession(
			ExamSubjectRuleSettingsForm objform, HttpSession session,
			String mode) throws Exception {
		ExamSubjectRuleSettingsPracticalESETO practicalESETO = null;
		ExamSubjectRuleSettingsHandler handler = ExamSubjectRuleSettingsHandler
		.getInstance();
		if (mode.equalsIgnoreCase("add")) {
			// set multiple answer script list only if multipleAnswerScript is
	// checked

	
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = null;
			
			ExamSubjectRuleSettingsPracticalESETO practicalESETOFromForm = (objform
					.getPracticalESETO() == null ? new ExamSubjectRuleSettingsPracticalESETO()
					: objform.getPracticalESETO());
			
			multipleAnswerScriptList = (practicalESETOFromForm
					.getMultipleAnswerScriptList() == null ? handler
					.getMultipleAnswerScriptsDetails() : practicalESETOFromForm
					.getMultipleAnswerScriptList());

			practicalESETO = new ExamSubjectRuleSettingsPracticalESETO(
					practicalESETOFromForm.getEvaluatorID(),
					practicalESETOFromForm.getMaximumEntryMarksPracticalESE(),
					practicalESETOFromForm.getMaximumMarksPracticalESE(),
					practicalESETOFromForm
							.getMaximumTheoryFinalMarksPracticalESE(),
					practicalESETOFromForm.getMinimumMarksPracticalESE(),
					practicalESETOFromForm
							.getMinimumTheoryFinalMarksPracticalESE(),
					multipleAnswerScriptList, practicalESETOFromForm
							.getMultipleAnswerScriptPracticalESE(),
					practicalESETOFromForm.getMultipleAnswerScriptsChecked(),

					practicalESETOFromForm.getMultipleEvaluatorChecked(),
					practicalESETOFromForm.getNoOfEvaluations(),

					practicalESETOFromForm.getRegularPracticalESEChecked(),
					practicalESETOFromForm.getTypeOfEvaluation());
			practicalESETO.setEvalId1(practicalESETOFromForm.getEvalId1());
			practicalESETO.setEvalId2(practicalESETOFromForm.getEvalId2());
			practicalESETO.setEvalId3(practicalESETOFromForm.getEvalId3());
			practicalESETO.setEvalId4(practicalESETOFromForm.getEvalId4());
			practicalESETO.setEvalId5(practicalESETOFromForm.getEvalId5());
			practicalESETO.setTypeOfEvaluation(practicalESETOFromForm
					.getTypeOfEvaluation());
			practicalESETO.setIsMultipleAnswerScriptsChecked(practicalESETOFromForm
					.getIsMultipleAnswerScriptsChecked());
			practicalESETO.setIsMultipleEvaluatorChecked(practicalESETOFromForm
					.getIsMultipleEvaluatorChecked());
			practicalESETO.setIsRegularTheoryESEChecked(practicalESETOFromForm
					.getIsRegularTheoryESEChecked());
			log.info("setting PracticalESE attribute to sessionsionsion");
			session.removeAttribute("PracticalESE");
			session.setAttribute("PracticalESE", practicalESETO);
		} else if (mode.equalsIgnoreCase("update")) {
					
			ExamSubjectRuleSettingsPracticalESETO practicalESETOFromForm = (objform
					.getPracticalESETO() == null ? new ExamSubjectRuleSettingsPracticalESETO()
					: objform.getPracticalESETO());
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = null;
			multipleAnswerScriptList = (practicalESETOFromForm
					.getMultipleAnswerScriptList() == null ? handler
					.getMultipleAnswerScriptsDetails() : practicalESETOFromForm
					.getMultipleAnswerScriptList());

			practicalESETO = new ExamSubjectRuleSettingsPracticalESETO(
					practicalESETOFromForm.getEvaluatorID(),
					practicalESETOFromForm.getMaximumEntryMarksPracticalESE(),
					practicalESETOFromForm.getMaximumMarksPracticalESE(),
					practicalESETOFromForm
							.getMaximumTheoryFinalMarksPracticalESE(),
					practicalESETOFromForm.getMinimumMarksPracticalESE(),
					practicalESETOFromForm
							.getMinimumTheoryFinalMarksPracticalESE(),
					multipleAnswerScriptList, practicalESETOFromForm
							.getMultipleAnswerScriptPracticalESE(),
					practicalESETOFromForm.getMultipleAnswerScriptsChecked(),

					practicalESETOFromForm.getMultipleEvaluatorChecked(),
					practicalESETOFromForm.getNoOfEvaluations(),

					practicalESETOFromForm.getRegularPracticalESEChecked(),
					practicalESETOFromForm.getTypeOfEvaluation());
			practicalESETO.setEvalId1(practicalESETOFromForm.getEvalId1());
			practicalESETO.setEvalId2(practicalESETOFromForm.getEvalId2());
			practicalESETO.setEvalId3(practicalESETOFromForm.getEvalId3());
			practicalESETO.setEvalId4(practicalESETOFromForm.getEvalId4());
			practicalESETO.setEvalId5(practicalESETOFromForm.getEvalId5());
			practicalESETO.setId1(practicalESETOFromForm.getId1());
			practicalESETO.setId2(practicalESETOFromForm.getId2());
			practicalESETO.setId3(practicalESETOFromForm.getId3());
			practicalESETO.setId4(practicalESETOFromForm.getId4());
			practicalESETO.setId5(practicalESETOFromForm.getId5());
			practicalESETO.setTypeOfEvaluation(practicalESETOFromForm
					.getTypeOfEvaluation());
			practicalESETO.setIsMultipleAnswerScriptsChecked(practicalESETOFromForm
					.getIsMultipleAnswerScriptsChecked());
			practicalESETO.setIsMultipleEvaluatorChecked(practicalESETOFromForm
					.getIsMultipleEvaluatorChecked());
			practicalESETO.setIsRegularTheoryESEChecked(practicalESETOFromForm
					.getIsRegularTheoryESEChecked());
			log.info("setting PracticalESE attribute to sessionsionsion");
			session.setAttribute("PracticalESE", practicalESETO);

		}
		return practicalESETO;
	}

	public ExamSubjectRuleSettingsSubjectFinalTO setToSubjectFinalSession(
			ExamSubjectRuleSettingsForm objform, HttpSession session,
			String mode) {
		ExamSubjectRuleSettingsSubjectFinalTO subjectFinalTO = null;

		if (mode.equalsIgnoreCase("add")) {
			subjectFinalTO = new ExamSubjectRuleSettingsSubjectFinalTO(objform
					.getMaximumSubjectFinal(),
					objform.getMinimumSubjectFinal(), objform
							.getSubjectFinalAttendanceChecked(), objform
							.getSubjectFinalInternalExamChecked(), objform
							.getSubjectFinalPracticalExamChecked(), objform
							.getSubjectFinalTheoryExamChecked(), objform
							.getValuatedSubjectFinal());

			log.info("setting SubjectRule attribute to sessionsionsion ");

			session.setAttribute("SubjectRule", subjectFinalTO);

			session.setAttribute("SubjectRule", subjectFinalTO);
		} else if (mode.equalsIgnoreCase("update")) {
			subjectFinalTO = new ExamSubjectRuleSettingsSubjectFinalTO(objform
					.getMaximumSubjectFinal(),
					objform.getMinimumSubjectFinal(), objform
							.getSubjectFinalAttendanceChecked(), objform
							.getSubjectFinalInternalExamChecked(), objform
							.getSubjectFinalPracticalExamChecked(), objform
							.getSubjectFinalTheoryExamChecked(), objform
							.getValuatedSubjectFinal());

			if (session.getAttribute("SubjectRule") != null) {
				session.removeAttribute("SubjectRule");
			}
			session.setAttribute("SubjectRule", subjectFinalTO);

		}
		return subjectFinalTO;
	}

}
