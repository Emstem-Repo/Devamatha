package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.exam.AttendanceTypeUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamSubjecRuleSettingsMultipleEvaluatorBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAssignmentBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAttendanceBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsMultipleAnsScriptBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamSubjectRuleSettingsForm;
import com.kp.cms.helpers.exam.ExamSubjectRuleSettingsHelper;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsEditTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubjectFinalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubjectTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamSubjectRuleSettingsImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamSubjectRuleSettingsHandler extends ExamGenHandler {
	private static final Log log = LogFactory
			.getLog(ExamSubjectRuleSettingsHandler.class);
	ExamSubjectRuleSettingsHelper helper = new ExamSubjectRuleSettingsHelper();
	ExamSubjectRuleSettingsImpl impl = new ExamSubjectRuleSettingsImpl();
	private static volatile ExamSubjectRuleSettingsHandler handler = null;

	public static ExamSubjectRuleSettingsHandler getInstance() {
		if (handler == null) {
			handler = new ExamSubjectRuleSettingsHandler();
			return handler;
		}
		return handler;
	}

	public Map<Integer, String> getSchemeIDSchemeNOByCourse(String courseId) throws Exception {
		List<CurriculumSchemeUtilBO> listBO = new ArrayList(impl
				.select_Scheme_By_Course(Integer.parseInt(courseId)));
		return helper.convertBOToTO_SchemeNo_Map(listBO);

	}

	public ArrayList<KeyValueTO> getSchemeIDSchemeNOByCourse(String courseId,
			int toAcademicYear) throws Exception {
		return impl.select_SchemeByCourse(Integer.parseInt(courseId),
				toAcademicYear);

	}

	public List<KeyValueTO> getProgramTypeList() throws Exception {
		ArrayList<ExamProgramTypeUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamProgramTypeUtilBO.class));
		return helper.convertBOToTO_ProgramType(listBO);
	}

	public HashMap<Integer, String> getAttendanceListHashMap() throws Exception {
		ArrayList<AttendanceTypeUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(AttendanceTypeUtilBO.class));
		return helper.convertBOToTO_AttendanceType_HashMap(listBO);
	}

	public List<ExamSubjectRuleSettingsSubInternalTO> getSubInternalDetails() throws Exception {
		ArrayList<ExamInternalExamTypeBO> listBO = new ArrayList(impl
				.select_ActiveOnlySubinternal(ExamInternalExamTypeBO.class));
		return helper.convertBOToTO_SubInternal(listBO);
	}

	public List<ExamSubjectRuleSettingsAssignmentTO> getExamAssignmentDetails() throws Exception {
		ArrayList<ExamAssignmentTypeMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamAssignmentTypeMasterBO.class));
		return helper.convertBOToTO_Assignment(listBO);
	}

	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> getMultipleAnswerScriptsDetails() throws Exception {
		ArrayList<ExamMultipleAnswerScriptMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamMultipleAnswerScriptMasterBO.class));
		return helper.convertBOToTO_MultipleAnswerScript(listBO);
	}

	public String getacademicYear(int academicYear) throws Exception {

		if (academicYear != 0) {
			return Integer.toString(academicYear).concat("-")
					+ Integer.toString(academicYear + 1);
		}
		return "";
	}

	public ArrayList<ExamSubjectRuleSettingsSubjectTO> getSubjects(
			int courseId, int schemeId, int schemeNo, int academicYear) throws Exception {
		return helper.convertBOToTO_SubjectsList(impl.select_Subjects(courseId,
				schemeId, schemeNo, academicYear));
	}
	
	public ArrayList<ExamSubjectRuleSettingsSubjectTO> getNewSubjects(
			int courseId, int schemeId, int schemeNo, int academicYear) throws Exception {
		return helper.convertBOToTO_SubjectsList(impl.getSubjects(courseId,
				schemeId, schemeNo, academicYear));
	}


	public Map<Integer, String> getSchemeType(ArrayList<Integer> courseID,
			String schemeName) throws Exception {
		Map<Integer, String> schemeMap = null;
		for (Integer i : courseID) {
			schemeMap = getSchemeIDSchemeNOByCourse(Integer.toString(i));

		}

		return schemeMap;
	}

	public List<Integer> insertSubjectRuleSettingsDetails(String academicYear,
			ArrayList<Integer> courseIds, String userId, String schemeType,
			ExamSubjectRuleSettingsSubInternalTO subTO,
			ExamSubjectRuleSettingsSubInternalTO toPractical,
			HttpSession session) throws Exception {
		int courseId = 0;
		int subjectID = 0;
		int schemeNo = 0;
		int academic_Year = 0;
		String mode = "add";
		if (CommonUtil.checkForEmpty(academicYear)) {
			academic_Year = Integer.parseInt(academicYear);
		}
		List<Integer> ids = null;
		ExamSubjectRuleSettingsBO subjectRulesBO = getAllTOObjects(Integer
				.parseInt(academicYear), session, userId, courseId, subjectID,
				schemeNo, subTO, toPractical, mode, 0);

		ArrayList<ExamSubjectRuleSettingsBO> subRuleBO = new ArrayList<ExamSubjectRuleSettingsBO>();

		Map<Integer, String> map = null;
		if (courseIds != null && courseIds.size() > 0) {
			for (Integer courseID : courseIds) {
				Iterator it = null;

				map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
				if (map != null && map.size() > 0) {
					it = map.keySet().iterator();

					Integer key = (Integer) it.next();

					int value = Integer.parseInt(map.get(key));

					for (int i = 1; i <= value; i++) {

						if (i % 2 == 0) {

							try {

								ArrayList<ExamSubjectRuleSettingsSubjectTO> subjectTO = getNewSubjects(
										courseID, key, i, academic_Year);
								if (subjectTO != null && subjectTO.size() > 0) {

									ArrayList<Integer> lisstOfSubject = new ArrayList<Integer>();
									for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {

										if (schemeType != null
												&& schemeType
														.equalsIgnoreCase("even")
												|| schemeType
														.equalsIgnoreCase("both")) {

											subjectRulesBO.setSubjectId(to
													.getSubid());
											subjectRulesBO
													.setCourseId(courseID);
											subjectRulesBO.setSchemeNo(i);

											if (!lisstOfSubject.contains(to
													.getSubid())) {
												subRuleBO
														.add(new ExamSubjectRuleSettingsBO(
																subjectRulesBO));
												lisstOfSubject.add(to
														.getSubid());
											}

										}
									}
								} else {
									log
											.warn("No subjects available for the course:\t"
													+ courseID
													+ "\t schemeID:\t"
													+ key
													+ "\t schemeNo:\t"
													+ i
													+ "\t academicYear:\t"
													+ academic_Year);
								}

							} catch (Exception e) {

								log.error(e.getMessage());
							}

						}

						else {

							try {

								ArrayList<ExamSubjectRuleSettingsSubjectTO> subjectTO = getNewSubjects(
										courseID, key, i, academic_Year);
								if (subjectTO != null && subjectTO.size() > 0) {
									for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {

										if (schemeType != null
												&& schemeType
														.equalsIgnoreCase("odd")
												|| schemeType
														.equalsIgnoreCase("both")) {

											subjectRulesBO.setSubjectId(to
													.getSubid());
											subjectRulesBO
													.setCourseId(courseID);
											subjectRulesBO.setSchemeNo(i);
											subRuleBO
													.add(new ExamSubjectRuleSettingsBO(
															subjectRulesBO));

										}
									}
								} else {
									log
											.warn("No subjects available for the course:\t"
													+ courseId
													+ "\t schemeID:\t"
													+ key
													+ "\t schemeNo:\t"
													+ i
													+ "\t academicYear:\t"
													+ academic_Year);
								}

							} catch (Exception e) {

								log.error(e.getMessage());
								e.printStackTrace();
							}

						}
					}
				} else {
					log.warn("No schemes available for the course:\t"
							+ courseId);
				}

			}

		} else {
			log
					.warn("No courses have been selected to add subject rule settings:\t"
							+ courseIds);
		}

		if (subRuleBO != null && subRuleBO.size() > 0)
			ids = impl.insert_SubjectRuleSettingsList(subRuleBO);
		return ids;

	}

	private ExamSubjectRuleSettingsBO getAllTOObjects(int academicYear,
			HttpSession session, String userId, int courseID, int subjectID,
			int schemeNo, ExamSubjectRuleSettingsSubInternalTO subTO,
			ExamSubjectRuleSettingsSubInternalTO toPractical, String mode,
			int id) throws Exception {

		ExamSubjectRuleSettingsBO subjectRulesBO = null;

		ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO = (ExamSubjectRuleSettingsTheoryInternalTO) session
				.getAttribute("TheoryInternal");

		ExamSubjectRuleSettingsTheoryESETO theoryESETO = (ExamSubjectRuleSettingsTheoryESETO) session
				.getAttribute("TheoryESE");

		ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO = (ExamSubjectRuleSettingsPracticalInternalTO) session
				.getAttribute("PracticalInternal");

		ExamSubjectRuleSettingsPracticalESETO practicalESETO = (ExamSubjectRuleSettingsPracticalESETO) session
				.getAttribute("PracticalESE");

		ExamSubjectRuleSettingsSubjectFinalTO subjectFinalTO = (ExamSubjectRuleSettingsSubjectFinalTO) session
				.getAttribute("SubjectRule");

		BigDecimal totalMinimummumMarks = null;
		BigDecimal totalMaximumMarks = null;
		BigDecimal totalentryMaximumMarks = null;

		BigDecimal totalMinimumMarksPractical = null;
		BigDecimal totalMaximumMarksPractical = null;
		BigDecimal totalentryMaximumMarksPractical = null;

		int finalTheoryInternalIsAssignment = 0;
		int finalTheoryInternalIsSubInternal = 0;
		int finalTheoryInternalIsAttendance = 0;
		Integer selectBestOfTheoryInternal = null;
		BigDecimal finalTheoryInternalMaximumMark = null;
		BigDecimal finalTheoryInternalEnteredMaxMark = null;
		BigDecimal finalTheoryInternalMinimumMark = null;

		if (CommonUtil.checkForEmpty(subTO.getTotalentryMaximumMarks())
				&& (subTO.getTotalentryMaximumMarks().trim().length() > 0)) {
			totalentryMaximumMarks = new BigDecimal(subTO
					.getTotalentryMaximumMarks());

		}

		if (CommonUtil.checkForEmpty(subTO.getTotalMaximumMarks())
				&& (subTO.getTotalMaximumMarks().trim().length() > 0)) {
			totalMaximumMarks = new BigDecimal(subTO.getTotalMaximumMarks());

		}

		if (CommonUtil.checkForEmpty(subTO.getTotalMinimummumMarks())
				&& (subTO.getTotalMinimummumMarks().trim().length() > 0)) {
			totalMinimummumMarks = new BigDecimal(subTO
					.getTotalMinimummumMarks());

		}

		if (CommonUtil.checkForEmpty(toPractical.getTotalentryMaximumMarks())
				&& (toPractical.getTotalentryMaximumMarks().trim().length() > 0)) {
			totalentryMaximumMarksPractical = new BigDecimal(toPractical
					.getTotalentryMaximumMarks());

		}

		if (CommonUtil.checkForEmpty(toPractical.getTotalMaximumMarks())
				&& (toPractical.getTotalMaximumMarks().trim().length() > 0)) {
			totalMaximumMarksPractical = new BigDecimal(toPractical
					.getTotalMaximumMarks());

		}

		if (CommonUtil.checkForEmpty(toPractical.getTotalMinimummumMarks())
				&& (toPractical.getTotalMinimummumMarks().trim().length() > 0)) {
			totalMinimumMarksPractical = new BigDecimal(toPractical
					.getTotalMinimummumMarks());

		}

		if ((theoryIntTO.getFinalEntryMaximumMarks().trim().length() > 0)
				&& (theoryIntTO.getFinalEntryMaximumMarks() != null)) {
			finalTheoryInternalEnteredMaxMark = new BigDecimal(theoryIntTO
					.getFinalEntryMaximumMarks());
		}

		if (theoryIntTO.getFinalInternalMaximumMarks() != null
				&& theoryIntTO.getFinalInternalMaximumMarks().trim().length() > 0) {
			finalTheoryInternalMaximumMark = new BigDecimal(theoryIntTO
					.getFinalInternalMaximumMarks());
		}

		if (theoryIntTO.getFinalInternalMinimumMarks() != null
				&& theoryIntTO.getFinalInternalMinimumMarks().trim().length() > 0) {
			finalTheoryInternalMinimumMark = new BigDecimal(theoryIntTO
					.getFinalInternalMinimumMarks());
		}

		if (theoryIntTO.getAssignmentChecked() != null
				&& theoryIntTO.getAssignmentChecked().trim().length() > 0) {
			finalTheoryInternalIsAssignment = 1;
		}

		if (theoryIntTO.getSubInternalChecked() != null
				&& theoryIntTO.getSubInternalChecked().trim().length() > 0) {
			finalTheoryInternalIsSubInternal = 1;
		}

		if (theoryIntTO.getAttendanceChecked() != null
				&& theoryIntTO.getAttendanceChecked().trim().length() > 0) {
			finalTheoryInternalIsAttendance = 1;
		}

		if (subTO.getSelectTheBest() != null
				&& subTO.getSelectTheBest().trim().length() > 0) {

			selectBestOfTheoryInternal = Integer.parseInt(subTO
					.getSelectTheBest());
		}

		// -------------------end of
		// ExamSubjectRuleSettingsTheoryInternalTO-----------//

		// -------------PracticalInternal------------------------//

		int finalPracticalInternalIsAssignment = 0;
		int finalPracticalInternalIsSubInternal = 0;
		int finalPracticalInternalIsAttendance = 0;
		Integer selectBestOfPracticalInternal = null;
		BigDecimal finalPracticalInternalEnteredMaxMark = null;
		BigDecimal finalPracticalInternalMaximumMark = null;
		BigDecimal finalPracticalInternalMinimum_mark = null;

		if (practicalIntTO != null) {

			if (practicalIntTO.getFinalEntryMaximumMarks() != null
					&& practicalIntTO.getFinalEntryMaximumMarks().trim()
							.length() > 0) {
				finalPracticalInternalEnteredMaxMark = new BigDecimal(
						practicalIntTO.getFinalEntryMaximumMarks());
			}

			if (practicalIntTO.getFinalInternalMaximumMarks() != null
					&& practicalIntTO.getFinalInternalMaximumMarks().trim()
							.length() > 0) {
				finalPracticalInternalMaximumMark = new BigDecimal(
						practicalIntTO.getFinalInternalMaximumMarks());
			}

			if (practicalIntTO.getFinalInternalMinimumMarks() != null
					&& practicalIntTO.getFinalInternalMinimumMarks().trim()
							.length() > 0) {
				finalPracticalInternalMinimum_mark = new BigDecimal(
						practicalIntTO.getFinalInternalMinimumMarks());
			}

			if (practicalIntTO.getAssignmentChecked() != null
					&& practicalIntTO.getAssignmentChecked().trim().length() > 0) {
				finalPracticalInternalIsAssignment = 1;
			}

			if (practicalIntTO.getSubInternalChecked() != null
					&& practicalIntTO.getSubInternalChecked().trim().length() > 0) {
				finalPracticalInternalIsSubInternal = 1;
			}

			if (practicalIntTO.getAttendanceChecked() != null
					&& practicalIntTO.getAttendanceChecked().trim().length() > 0) {
				finalPracticalInternalIsAttendance = 1;
			}
		}
		if (toPractical != null && toPractical.getSelectTheBest() != null
				&& toPractical.getSelectTheBest().trim().length() > 0) {

			selectBestOfPracticalInternal = Integer.parseInt(toPractical
					.getSelectTheBest());

		}
		// -------------------------------END------------------------//

		// ------------------practicalESETO----------/

		BigDecimal practicalEseEnteredMaxMark = null;
		BigDecimal practicalEseMaximumMark = null;
		BigDecimal practicalEseMinimumMark = null;
		BigDecimal practicalEseTheoryFinalMaximumMark = null;
		BigDecimal practicalEseTheoryFinalMinimumMark = null;
		int practicalEseIsMultipleAnswerScript = 0;
		int practicalEseIsMultipleEvaluator = 0;
		int practicalEseIsRegular = 0;

		if (CommonUtil.checkForEmpty(practicalESETO
				.getMaximumEntryMarksPracticalESE())
				&& practicalESETO.getMaximumEntryMarksPracticalESE().trim()
						.length() > 0) {
			practicalEseEnteredMaxMark = new BigDecimal(practicalESETO
					.getMaximumEntryMarksPracticalESE());

		}
		if (CommonUtil.checkForEmpty(practicalESETO
				.getMaximumMarksPracticalESE())
				&& practicalESETO.getMaximumMarksPracticalESE().trim().length() > 0) {
			practicalEseMaximumMark = new BigDecimal(practicalESETO
					.getMaximumMarksPracticalESE());

		}
		if (CommonUtil.checkForEmpty(practicalESETO
				.getMinimumMarksPracticalESE())
				&& practicalESETO.getMinimumMarksPracticalESE().trim().length() > 0) {
			practicalEseMinimumMark = new BigDecimal(practicalESETO
					.getMinimumMarksPracticalESE());

		}
		if (CommonUtil.checkForEmpty(practicalESETO
				.getMaximumTheoryFinalMarksPracticalESE())
				&& practicalESETO.getMaximumTheoryFinalMarksPracticalESE()
						.trim().length() > 0) {
			practicalEseTheoryFinalMaximumMark = new BigDecimal(practicalESETO
					.getMaximumTheoryFinalMarksPracticalESE());

		}
		if (CommonUtil.checkForEmpty(practicalESETO
				.getMinimumTheoryFinalMarksPracticalESE())
				&& practicalESETO.getMinimumTheoryFinalMarksPracticalESE()
						.trim().length() > 0) {
			practicalEseTheoryFinalMinimumMark = new BigDecimal(practicalESETO
					.getMinimumTheoryFinalMarksPracticalESE());

		}

		if (practicalESETO.getMultipleAnswerScriptsChecked() != null
				&& practicalESETO.getMultipleAnswerScriptsChecked().trim()
						.length() > 0) {
			practicalEseIsMultipleAnswerScript = 1;
		}
		if (practicalESETO.getMultipleEvaluatorChecked() != null
				&& practicalESETO.getMultipleEvaluatorChecked().trim().length() > 0) {
			practicalEseIsMultipleEvaluator = 1;
		}

		if (practicalESETO.getRegularPracticalESEChecked() != null
				&& practicalESETO.getRegularPracticalESEChecked().trim()
						.length() > 0) {
			practicalEseIsRegular = 1;
		}

		// ----------------------end----------------------------------------------

		BigDecimal theoryEseEnteredMaxMark = null;
		BigDecimal theoryEseMaximumMark = null;
		BigDecimal theoryEseMinimumMark = null;
		BigDecimal theoryEseTheoryFinalMaximumMark = null;
		BigDecimal theoryEseTheoryFinalMinimumMark = null;
		int theoryEseIsMultipleAnswerScript = 0;
		int theoryEseIsMultipleEvaluator = 0;
		int theoryEseIsRegular = 0;

		if (CommonUtil.checkForEmpty(theoryESETO
				.getMaximumEntryMarksTheoryESE())
				&& theoryESETO.getMaximumEntryMarksTheoryESE().trim().length() > 0) {
			theoryEseEnteredMaxMark = new BigDecimal(theoryESETO
					.getMaximumEntryMarksTheoryESE());

		}
		if (CommonUtil.checkForEmpty(theoryESETO.getMaximumMarksTheoryESE())
				&& theoryESETO.getMaximumMarksTheoryESE().trim().length() > 0) {
			theoryEseMaximumMark = new BigDecimal(theoryESETO
					.getMaximumMarksTheoryESE());

		}
		if (CommonUtil.checkForEmpty(theoryESETO.getMinimumMarksTheoryESE())
				&& theoryESETO.getMinimumMarksTheoryESE().trim().length() > 0) {
			theoryEseMinimumMark = new BigDecimal(theoryESETO
					.getMinimumMarksTheoryESE());

		}
		if (CommonUtil.checkForEmpty(theoryESETO
				.getMaximumTheoryFinalMarksTheoryESE())
				&& theoryESETO.getMaximumTheoryFinalMarksTheoryESE().trim()
						.length() > 0) {
			theoryEseTheoryFinalMaximumMark = new BigDecimal(theoryESETO
					.getMaximumTheoryFinalMarksTheoryESE());

		}
		if (CommonUtil.checkForEmpty(theoryESETO
				.getMinimumTheoryFinalMarksTheoryESE())
				&& theoryESETO.getMinimumTheoryFinalMarksTheoryESE().trim()
						.length() > 0) {
			theoryEseTheoryFinalMinimumMark = new BigDecimal(theoryESETO
					.getMinimumTheoryFinalMarksTheoryESE());

		}

		if (theoryESETO.getMultipleAnswerScriptsChecked() != null
				&& theoryESETO.getMultipleAnswerScriptsChecked().trim()
						.length() > 0) {
			theoryEseIsMultipleAnswerScript = 1;
		}
		if (theoryESETO.getMultipleEvaluatorChecked() != null
				&& theoryESETO.getMultipleEvaluatorChecked().trim().length() > 0) {
			theoryEseIsMultipleEvaluator = 1;
		}

		if (theoryESETO.getRegularTheoryESEChecked() != null
				&& theoryESETO.getRegularTheoryESEChecked().trim().length() > 0) {
			theoryEseIsRegular = 1;
		}

		// ----------------------end----------------------------------------------

		// -----------------------Subject final------------//
		int subjectFinalIsAttendance = 0;
		int subjectFinalIsInternalExam = 0;
		int subjectFinalIsPracticalExam = 0;
		int subjectFinalIsTheoryExam = 0;

		BigDecimal subjectFinalMaximum = null;

		BigDecimal subjectFinalMinimum = null;

		BigDecimal subjectFinalValuated = null;

		if (CommonUtil.checkForEmpty(subjectFinalTO.getMaximumSubjectFinal())
				&& subjectFinalTO.getMaximumSubjectFinal().trim().length() > 0) {
			subjectFinalMaximum = new BigDecimal(subjectFinalTO
					.getMaximumSubjectFinal());

		}
		if (CommonUtil.checkForEmpty(subjectFinalTO.getMinimumSubjectFinal())
				&& subjectFinalTO.getMinimumSubjectFinal().trim().length() > 0) {
			subjectFinalMinimum = new BigDecimal(subjectFinalTO
					.getMinimumSubjectFinal());

		}
		if (CommonUtil.checkForEmpty(subjectFinalTO.getValuatedSubjectFinal())
				&& subjectFinalTO.getValuatedSubjectFinal().trim().length() > 0) {
			subjectFinalValuated = new BigDecimal(subjectFinalTO
					.getValuatedSubjectFinal());

		}

		if (subjectFinalTO.getSubjectFinalAttendanceChecked() != null
				&& subjectFinalTO.getSubjectFinalAttendanceChecked().trim()
						.length() > 0) {
			subjectFinalIsAttendance = 1;
		}
		if (subjectFinalTO.getSubjectFinalInternalExamChecked() != null
				&& subjectFinalTO.getSubjectFinalInternalExamChecked().trim()
						.length() > 0) {
			subjectFinalIsInternalExam = 1;
		}
		if (subjectFinalTO.getSubjectFinalPracticalExamChecked() != null
				&& subjectFinalTO.getSubjectFinalPracticalExamChecked().trim()
						.length() > 0) {
			subjectFinalIsPracticalExam = 1;
		}
		if (subjectFinalTO.getSubjectFinalTheoryExamChecked() != null
				&& subjectFinalTO.getSubjectFinalTheoryExamChecked().trim()
						.length() > 0) {
			subjectFinalIsTheoryExam = 1;
		}

		if (mode.equalsIgnoreCase("add")) {

			subjectRulesBO = new ExamSubjectRuleSettingsBO(academicYear,
					courseID, finalPracticalInternalEnteredMaxMark,
					finalPracticalInternalIsAssignment,
					finalPracticalInternalIsAttendance,
					finalPracticalInternalIsSubInternal,
					finalPracticalInternalMaximumMark,
					finalPracticalInternalMinimum_mark,
					finalTheoryInternalEnteredMaxMark,
					finalTheoryInternalIsAssignment,
					finalTheoryInternalIsAttendance,
					finalTheoryInternalIsSubInternal,
					finalTheoryInternalMaximumMark,
					finalTheoryInternalMinimumMark, practicalEseEnteredMaxMark,
					practicalEseIsMultipleAnswerScript,
					practicalEseIsMultipleEvaluator, practicalEseIsRegular,
					practicalEseMaximumMark, practicalEseMinimumMark,
					practicalEseTheoryFinalMaximumMark,
					practicalEseTheoryFinalMinimumMark, schemeNo,
					selectBestOfPracticalInternal, selectBestOfTheoryInternal,
					subjectFinalIsAttendance, subjectFinalIsInternalExam,
					subjectFinalIsPracticalExam, subjectFinalIsTheoryExam,
					subjectFinalMaximum, subjectFinalMinimum,
					subjectFinalValuated, subjectID, theoryEseEnteredMaxMark,
					theoryEseIsMultipleAnswerScript,
					theoryEseIsMultipleEvaluator, theoryEseIsRegular,
					theoryEseMaximumMark, theoryEseMinimumMark,
					theoryEseTheoryFinalMaximumMark,
					theoryEseTheoryFinalMinimumMark, userId, new Date(),
					new Date(), userId, true, totalMinimummumMarks,
					totalMaximumMarks, totalentryMaximumMarks,
					totalMinimumMarksPractical, totalMaximumMarksPractical,
					totalentryMaximumMarksPractical);

		} else

		{

			subjectRulesBO = new ExamSubjectRuleSettingsBO(id, academicYear,
					finalPracticalInternalEnteredMaxMark,
					finalPracticalInternalIsAssignment,
					finalPracticalInternalIsAttendance,
					finalPracticalInternalIsSubInternal,
					finalPracticalInternalMaximumMark,
					finalPracticalInternalMinimum_mark,
					finalTheoryInternalEnteredMaxMark,

					finalTheoryInternalIsAssignment,
					finalTheoryInternalIsAttendance,

					finalTheoryInternalIsSubInternal,

					finalTheoryInternalMaximumMark,
					finalTheoryInternalMinimumMark, practicalEseEnteredMaxMark,
					practicalEseIsMultipleAnswerScript,
					practicalEseIsMultipleEvaluator, practicalEseIsRegular,
					practicalEseMaximumMark, practicalEseMinimumMark,
					practicalEseTheoryFinalMaximumMark,
					practicalEseTheoryFinalMinimumMark,
					totalentryMaximumMarksPractical,
					totalMaximumMarksPractical, totalMinimumMarksPractical,
					selectBestOfPracticalInternal, selectBestOfTheoryInternal,
					subjectFinalIsAttendance, subjectFinalIsInternalExam,
					subjectFinalIsPracticalExam, subjectFinalIsTheoryExam,
					subjectFinalMaximum, subjectFinalMinimum,
					subjectFinalValuated, theoryEseEnteredMaxMark,
					theoryEseIsMultipleAnswerScript,
					theoryEseIsMultipleEvaluator, theoryEseIsRegular,
					theoryEseMaximumMark, theoryEseMinimumMark,
					theoryEseTheoryFinalMaximumMark,
					theoryEseTheoryFinalMinimumMark, totalentryMaximumMarks,
					totalMaximumMarks, totalMinimummumMarks);

		}

		return subjectRulesBO;
	}

	public void addAssignmentDetails(
			List<ExamSubjectRuleSettingsAssignmentTO> listTO,
			int subRuleSettingsID) throws Exception {

		ExamSubjectRuleSettingsAssignmentBO assignmentBO = null;
		BigDecimal maximumMark = null;
		BigDecimal minimumMark = null;
		String isTheoryPractical = null;
		if (listTO != null) {
			for (ExamSubjectRuleSettingsAssignmentTO to : listTO) {

				maximumMark = null;
				minimumMark = null;
				isTheoryPractical = to.getIsTheoryPractical();

				if (to.getMaximumAssignMarks() != null
						&& to.getMaximumAssignMarks().trim().length() > 0) {
					maximumMark = new BigDecimal(to.getMaximumAssignMarks());
				}

				if (to.getMinimumAssignMarks() != null
						&& to.getMinimumAssignMarks().trim().length() > 0) {
					minimumMark = new BigDecimal(to.getMinimumAssignMarks());
				}
				int id = to.getAssignmentTypeId();
				assignmentBO = new ExamSubjectRuleSettingsAssignmentBO(
						subRuleSettingsID, id, minimumMark, maximumMark,
						isTheoryPractical);
				assignmentBO.setIsActive(true);
				impl.insert(assignmentBO);

			}
		}

	}

	public void addMultipleAnswerScriptDetails(
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO,
			int subRuleSettingsID) throws Exception {

		BigDecimal value = null;
		ExamSubjectRuleSettingsMultipleAnsScriptBO bo = null;

		for (ExamSubjectRuleSettingsMultipleAnswerScriptTO to : listTO) {
			value = null;

			if (to.getMultipleAnswerScriptTheoryESE() != null
					&& to.getMultipleAnswerScriptTheoryESE().trim().length() > 0) {
				value = new BigDecimal(to.getMultipleAnswerScriptTheoryESE());
			}

			bo = new ExamSubjectRuleSettingsMultipleAnsScriptBO(
					subRuleSettingsID, to.getMultipleAnswerScriptId(), value,
					to.getIsTheoryPractical());

			bo.setIsActive(true);
			impl.insert(bo);
		}

	}

	public void addSubInternalDetails(
			List<ExamSubjectRuleSettingsSubInternalTO> subTOList,
			int subRuleSettingsID) throws Exception {

		ExamSubjectRuleSettingsSubInternalBO bo = null;

		String isTheoryPractical = null;
		for (ExamSubjectRuleSettingsSubInternalTO to : subTOList) {
			BigDecimal minimumMark = null;
			BigDecimal enteredMaxMark = null;
			BigDecimal maximumMark = null;
			if (to.getMinimumMarks() != null
					&& to.getMinimumMarks().trim().length() > 0) {
				minimumMark = new BigDecimal(to.getMinimumMarks());
			}
			if (to.getMaximumMarks() != null
					&& to.getMaximumMarks().trim().length() > 0) {
				maximumMark = new BigDecimal(to.getMaximumMarks());
			}
			if (to.getEntryMaximumMarks() != null
					&& to.getEntryMaximumMarks().trim().length() > 0) {
				enteredMaxMark = new BigDecimal(to.getEntryMaximumMarks());
			}
			isTheoryPractical = to.getIsTheoryPractical();
			Integer internalExamTypeId = (to.getInternalExamTypeId() != null
					&& to.getInternalExamTypeId().trim().length() > 0 ? new Integer(
					to.getInternalExamTypeId())
					: null);
			bo = new ExamSubjectRuleSettingsSubInternalBO(subRuleSettingsID,
					internalExamTypeId, minimumMark, enteredMaxMark,
					maximumMark, isTheoryPractical);
			bo.setIsActive(true);
			impl.insert(bo);
		}

	}

	public void addAttendanceDetails(ExamSubjectRuleSettingsAttendanceTO to,
			int subRuleSettingsID) throws Exception {
		ExamSubjectRuleSettingsAttendanceBO attendanceBO = null;
		int leaveAttendance = 0;
		int cocurricularAttendance = 0;
		Integer attendanceTypeId = null;

		if (to.getLeaveAttendance() != null
				&& to.getLeaveAttendance().trim().length() > 0) {
			leaveAttendance = 1;
		}

		if (to.getCoCurricularAttendance() != null
				&& to.getCoCurricularAttendance().trim().length() > 0) {
			cocurricularAttendance = 1;
		}

		if (CommonUtil.checkForEmpty(to.getAttendanceType())) {
			attendanceTypeId = Integer.parseInt(to.getAttendanceType());
		}

		attendanceBO = new ExamSubjectRuleSettingsAttendanceBO(
				subRuleSettingsID, attendanceTypeId, leaveAttendance,
				cocurricularAttendance, to.getIsTheoryPractical());
		attendanceBO.setIsActive(true);
		impl.insert(attendanceBO);

	}

	public void addMultipleEvaluatorDetails(
			ExamSubjectRuleSettingsTheoryESETO theoryESETO,
			int subRuleSettingsID) throws Exception {

		ExamSubjecRuleSettingsMultipleEvaluatorBO multpleEvalBO = null;

		int evalId1 = 0;
		int evalId2 = 0;
		int evalId3 = 0;
		int evalId4 = 0;
		int evalId5 = 0;
		int noOFEvaluation = 0;
		String typeOFEvaluations = null;
		List<Integer> evalIds = new ArrayList<Integer>();
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> listBO = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();

		if (theoryESETO.getMultipleEvaluatorChecked() != null
				&& theoryESETO.getMultipleEvaluatorChecked().trim().length() > 0) {

			if (CommonUtil.checkForEmpty(theoryESETO.getNoOfEvaluations())) {
				noOFEvaluation = Integer.parseInt(theoryESETO
						.getNoOfEvaluations());
			}

			if (CommonUtil.checkForEmpty(theoryESETO.getTypeOfEvaluation())
					&& theoryESETO.getTypeOfEvaluation().length() > 0) {
				if (theoryESETO.getTypeOfEvaluation().equalsIgnoreCase("1"))
					typeOFEvaluations = "Average";
				else if (theoryESETO.getTypeOfEvaluation()
						.equalsIgnoreCase("2"))
					typeOFEvaluations = "Highest";
				else
					typeOFEvaluations = "Lowest";

			}

			if (CommonUtil.checkForEmpty(theoryESETO.getEvalId1())) {
				evalId1 = Integer.parseInt(theoryESETO.getEvalId1());
				evalIds.add(evalId1);
			}

			if (CommonUtil.checkForEmpty(theoryESETO.getEvalId2())) {
				evalId2 = Integer.parseInt(theoryESETO.getEvalId2());
				evalIds.add(evalId2);
			}
			if (CommonUtil.checkForEmpty(theoryESETO.getEvalId3())) {
				evalId3 = Integer.parseInt(theoryESETO.getEvalId3());
				evalIds.add(evalId3);
			}
			if (CommonUtil.checkForEmpty(theoryESETO.getEvalId4())) {
				evalId4 = Integer.parseInt(theoryESETO.getEvalId4());
				evalIds.add(evalId4);
			}
			if (CommonUtil.checkForEmpty(theoryESETO.getEvalId5())) {
				evalId5 = Integer.parseInt(theoryESETO.getEvalId5());
				evalIds.add(evalId5);
			}

			if (evalIds.size() > 0) {
				for (Integer evalId : evalIds) {
					multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO(
							subRuleSettingsID, evalId, noOFEvaluation,
							typeOFEvaluations, "t");
					multpleEvalBO.setIsActive(true);
					listBO.add(multpleEvalBO);

				}

			} else {
				multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO(
						subRuleSettingsID, 0, noOFEvaluation,
						typeOFEvaluations, "t");
				multpleEvalBO.setIsActive(true);
				listBO.add(multpleEvalBO);
				multpleEvalBO.setIsActive(true);
				listBO.add(multpleEvalBO);
			}

		}
		impl.insert_List(listBO);

	}

	public void addMultipleEvaluatorDetails(
			ExamSubjectRuleSettingsPracticalESETO practicalESETO,
			int subRuleSettingsID) throws Exception {

		ExamSubjecRuleSettingsMultipleEvaluatorBO multpleEvalBO = null;
		int evalId1 = 0;
		int evalId2 = 0;
		int evalId3 = 0;
		int evalId4 = 0;
		int evalId5 = 0;
		int noOFEvaluation = 0;
		String typeOFEvaluations = null;
		List<Integer> evalIds = new ArrayList<Integer>();
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> listBO = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();

		if (practicalESETO.getMultipleEvaluatorChecked() != null
				&& practicalESETO.getMultipleEvaluatorChecked().trim().length() > 0) {
			if (CommonUtil.checkForEmpty(practicalESETO.getNoOfEvaluations())) {
				noOFEvaluation = Integer.parseInt(practicalESETO
						.getNoOfEvaluations());
			}
			// ---checking evaluator id-if null-------
			if (CommonUtil.checkForEmpty(practicalESETO.getEvalId1())) {
				evalId1 = Integer.parseInt(practicalESETO.getEvalId1());
				evalIds.add(evalId1);
			}

			if (CommonUtil.checkForEmpty(practicalESETO.getEvalId2())) {
				evalId2 = Integer.parseInt(practicalESETO.getEvalId2());
				evalIds.add(evalId2);
			}
			if (CommonUtil.checkForEmpty(practicalESETO.getEvalId3())) {
				evalId3 = Integer.parseInt(practicalESETO.getEvalId3());
				evalIds.add(evalId3);
			}
			if (CommonUtil.checkForEmpty(practicalESETO.getEvalId4())) {
				evalId4 = Integer.parseInt(practicalESETO.getEvalId4());
				evalIds.add(evalId4);
			}
			if (CommonUtil.checkForEmpty(practicalESETO.getEvalId5())) {
				evalId5 = Integer.parseInt(practicalESETO.getEvalId5());
				evalIds.add(evalId5);
			}

			if (CommonUtil.checkForEmpty(practicalESETO.getTypeOfEvaluation())
					&& practicalESETO.getTypeOfEvaluation().length() > 0) {
				if (practicalESETO.getTypeOfEvaluation().equalsIgnoreCase("1"))
					typeOFEvaluations = "Average";
				else if (practicalESETO.getTypeOfEvaluation().equalsIgnoreCase(
						"2"))
					typeOFEvaluations = "Highest";
				else
					typeOFEvaluations = "Lowest";

			}

			if (evalIds.size() > 0) {
				for (Integer evalId : evalIds) {
					multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO(
							subRuleSettingsID, evalId, noOFEvaluation,
							typeOFEvaluations, practicalESETO
									.getIsTheoryPractical());
					multpleEvalBO.setIsActive(true);
					listBO.add(multpleEvalBO);

				}

			} else {
				multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO(
						subRuleSettingsID, 0, noOFEvaluation,
						typeOFEvaluations, practicalESETO
								.getIsTheoryPractical());
				multpleEvalBO.setIsActive(true);
				listBO.add(multpleEvalBO);
			}

		}

		impl.insert_List(listBO);

	}

	public boolean deleteSubjectRuleSettingsDetails(
			ExamSubjectRuleSettingsForm objform) throws Exception {

		List<Integer> listCourseIds = (objform.getListCourses() == null ? new ArrayList<Integer>()
				: objform.getListCourses());
		Map<Integer, String> map = null;
		String schemeType = (objform.getSchemeName() == null ? "" : objform
				.getSchemeName());
		int academicYear = 0;
		boolean isPresent = false;
		if (CommonUtil.checkForEmpty(objform.getAcademicYear())) {
			academicYear = Integer.parseInt(objform.getAcademicYear());
		}
		if (listCourseIds.size() > 0) {
			for (Integer courseID : listCourseIds) {
				Iterator it = null;

				map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
				if (map != null && map.size() > 0) {

					it = map.keySet().iterator();

					Integer key = (Integer) it.next();

					int value = Integer.parseInt(map.get(key));

					for (int i = 1; i <= value; i++) {

						if (i % 2 == 0) {

							try {

								if (schemeType.equalsIgnoreCase("even")
										|| schemeType.equalsIgnoreCase("both")) {

									if (checkIfDataPresentInTableToDelete(
											academicYear, i, courseID
													.intValue()) > 0) {
										impl.delete_SubjectRuleSettings(
												academicYear, courseID, i,
												false);
										isPresent = true;
									}

								}

							} catch (Exception e) {

								log.error(e.getMessage());
							}

						}

						else {

							try {
								if (schemeType.equalsIgnoreCase("odd")
										|| schemeType.equalsIgnoreCase("both")) {

									if (checkIfDataPresentInTableToDelete(
											academicYear, i, courseID
													.intValue()) > 0) {
										impl.delete_SubjectRuleSettings(
												academicYear, courseID, i,
												false);
										isPresent = true;
									}
								}

							} catch (Exception e) {

								log.error(e.getMessage());
							}

						}
					}
				}

			}
		}

		return isPresent;

	}

	private int checkIfDataPresentInTableToDelete(int academicYear,
			int schemeNo, int courseId) throws Exception {

		return impl.checkIfDataIsPresent(academicYear, schemeNo, courseId);
	}

	public List<ExamSubjectRuleSettingsEditTO> getSubjectDetails(
			ExamSubjectRuleSettingsTO ruleTO) throws Exception {
		List<Object[]> listOfSubjects = null;
		List<Integer> courseIdsList = ruleTO.getCourseIdList();
		List<Integer> schemeNoList = null;
		Map<Integer, String> map = null;
		String schemeType = ruleTO.getSchemeType();
		int academicYear = 0;

		if (CommonUtil.checkForEmpty(ruleTO.getAcademicYear())) {
			academicYear = Integer.parseInt(ruleTO.getAcademicYear());
		}
		// HashMap subjectsMap = new HashMap();
		for (Integer courseId : courseIdsList)

		{
			schemeNoList = new ArrayList<Integer>();
			Iterator it = null;
			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseId));

			it = map.keySet().iterator();
			if (map != null && map.size() > 0) {
				Integer key = (Integer) it.next();

				int value = Integer.parseInt(map.get(key));
				int count = 0;
				for (int i = 1; i <= value; i++) {

					if (i % 2 == 0) {

						try {
							count = count + 1;

							if (schemeType != null
									&& schemeType.equalsIgnoreCase("even")
									|| schemeType.equalsIgnoreCase("both")) {

								schemeNoList.add(i);

							}

						} catch (Exception e) {

							log.error(e.getMessage());
						}

					}

					else {

						try {
							count = count + 1;
							if (schemeType != null
									&& schemeType.equalsIgnoreCase("odd")
									|| schemeType.equalsIgnoreCase("both")) {
								schemeNoList.add(i);

							}

						} catch (Exception e) {

							log.error(e.getMessage());
						}

					}

				}

				listOfSubjects = impl.selectAllDetailsForEdit(academicYear,
						courseId, key, schemeNoList);

				// subjectsMap.put(count, listOfSubjects);

			}
		}
		// return new ArrayList<ExamSubjectRuleSettingsEditTO>();

		return helper.convertBOToSubjectRuleSettingsEditTO(listOfSubjects);

	}

	public ExamSubjectRuleSettingsTheoryInternalTO setTheoryInternal(
			int subRuleId, ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) throws Exception {

		String thORPractical = "theory";

		List<ExamSubjectRuleSettingsSubInternalBO> listSubBO = new ArrayList(
				impl.select_All(subRuleId, thORPractical,
						ExamSubjectRuleSettingsSubInternalBO.class));

		List<ExamSubjectRuleSettingsSubInternalTO> listSubTO = helper
				.convertSubInternalBOToSubInternalTO(listSubBO, thORPractical);

		if (listSubTO != null && listSubTO.size() == 0) {
			listSubTO = getSubInternalDetails();
		}

		theoryIntTO.setSubInternalList(listSubTO);

		return theoryIntTO;
	}

	public ExamSubjectRuleSettingsTheoryInternalTO setTheoryInternalAttendance(
			int subRuleId, ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) throws Exception {
		String thORPractical = "theory";
		List<ExamSubjectRuleSettingsAttendanceBO> listAttendanceBO = new ArrayList(
				impl.select_All(subRuleId, thORPractical,
						ExamSubjectRuleSettingsAttendanceBO.class));

		ExamSubjectRuleSettingsAttendanceTO attendanceTO = helper
				.convertSubInternalBOToAttendanceTO(listAttendanceBO,
						thORPractical);

		if (attendanceTO != null) {
			theoryIntTO.setListAttendanceTO(attendanceTO);
		}

		return theoryIntTO;
	}

	public ExamSubjectRuleSettingsTheoryInternalTO setTheoryInternalAssignment(
			int subRuleId, ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) throws Exception {
		String thORPractical = "theory";
		List<ExamSubjectRuleSettingsAssignmentBO> listAssignmentBO = new ArrayList(
				impl.select_All(subRuleId,
						ExamSubjectRuleSettingsAssignmentBO.class));

		List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = helper
				.convertAssignmentBOToAssignmentTO(listAssignmentBO,
						thORPractical);
		if (listAssignmentTO != null && listAssignmentTO.size() == 0) {
			listAssignmentTO = getExamAssignmentDetails();
		}
		theoryIntTO.setAssignmentList(listAssignmentTO);
		return theoryIntTO;
	}

	public ExamSubjectRuleSettingsTheoryInternalTO getFinalInternalMarks(
			String id, ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) throws Exception {

		int subjectRuleId = (id != null && id.trim().length() > 0 ? Integer
				.parseInt(id) : 0);

		Object object = impl.select_Unique_Active(subjectRuleId,
				ExamSubjectRuleSettingsBO.class);
		ExamSubjectRuleSettingsBO bo = (object != null ? (ExamSubjectRuleSettingsBO) object
				: new ExamSubjectRuleSettingsBO());

		if (bo != null) {
			return helper
					.convertBOtoTo_ExamSubjectRuleSettingsTheoryInternalTO(bo,
							theoryIntTO);
		} else {
			return theoryIntTO;
		}

	}

	public ExamSubjectRuleSettingsTheoryESETO setTheoryESEMultipleAnswerScript(
			int subRuleId, ExamSubjectRuleSettingsTheoryESETO theoryESETO) throws Exception {

		List<ExamSubjectRuleSettingsMultipleAnsScriptBO> listBO = new ArrayList(
				impl.select_All(subRuleId,
						ExamSubjectRuleSettingsMultipleAnsScriptBO.class));

		Object object = impl.select_Unique_Active(subRuleId,
				ExamSubjectRuleSettingsBO.class);
		ExamSubjectRuleSettingsBO bo = (object == null ? new ExamSubjectRuleSettingsBO()
				: (ExamSubjectRuleSettingsBO) object);

		return helper.convertBOtoTo_ExamSubjectRuleSettingsTheoryESETO(listBO,
				theoryESETO, bo);
	}

	public ExamSubjectRuleSettingsTheoryESETO setTheoryESEMultipleEvaluator(
			int subRuleId, ExamSubjectRuleSettingsTheoryESETO theoryESETO) throws Exception {

		List<ExamSubjecRuleSettingsMultipleEvaluatorBO> mulEvalBOList = new ArrayList(
				impl.select_All(subRuleId, "theory",
						ExamSubjecRuleSettingsMultipleEvaluatorBO.class));

		theoryESETO = helper
				.convertBOtoTo_ExamSubjectRuleSettingsTheoryESEmulEvalTO(
						mulEvalBOList, theoryESETO);

		return theoryESETO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO setPracticalInternal(
			int subRuleId,
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {
		String thORPractical = "practical";
		List<ExamSubjectRuleSettingsSubInternalBO> listSubBO = new ArrayList(
				impl.select_All(subRuleId,
						ExamSubjectRuleSettingsSubInternalBO.class));

		List<ExamSubjectRuleSettingsSubInternalTO> listSubTO = helper
				.convertSubInternalBOToSubInternalTO(listSubBO, thORPractical);
		if (listSubTO != null && listSubTO.size() == 0) {
			listSubTO = getSubInternalDetails();
		}
		practicalIntTO.setSubInternalList(listSubTO);

		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO setPracticalInternalAttendance(
			int subRuleId,
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {
		String thORPractical = "practical";
		List<ExamSubjectRuleSettingsAttendanceBO> listAttendanceBO = new ArrayList(
				impl.select_All(subRuleId, thORPractical,
						ExamSubjectRuleSettingsAttendanceBO.class));

		ExamSubjectRuleSettingsAttendanceTO attendanceTO = helper
				.convertSubInternalBOToAttendanceTO(listAttendanceBO,
						thORPractical);

		practicalIntTO.setListAttendanceTO(attendanceTO);

		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO setPracticalInternalAssignment(
			int subRuleId,
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {

		String thORPractical = "practical";
		List<ExamSubjectRuleSettingsAssignmentBO> listAssignmentBO = new ArrayList(
				impl.select_All(subRuleId,
						ExamSubjectRuleSettingsAssignmentBO.class));
		List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO = helper
				.convertAssignmentBOToAssignmentTO(listAssignmentBO,
						thORPractical);
		if (listAssignmentTO != null && listAssignmentTO.size() == 0) {
			listAssignmentTO = getExamAssignmentDetails();
		}
		practicalIntTO.setAssignmentList(listAssignmentTO);
		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO setTheoryInternal(
			int subRuleId,
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {
		String thORPractical = "practical";

		List<ExamSubjectRuleSettingsSubInternalBO> listSubBO = new ArrayList(
				impl.select_All(subRuleId,
						ExamSubjectRuleSettingsSubInternalBO.class));

		List<ExamSubjectRuleSettingsSubInternalTO> listSubTO = helper
				.convertSubInternalBOToSubInternalTO(listSubBO, thORPractical);

		practicalIntTO.setSubInternalList(listSubTO);
		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO getFinalInternalMarksPractical(
			String id, ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {
		int subjectRuleId = 0;
		if (id != null && id.length() != 0) {
			subjectRuleId = Integer.parseInt(id);
		}
		ExamSubjectRuleSettingsBO bo = (ExamSubjectRuleSettingsBO) impl
				.select_Unique(subjectRuleId, ExamSubjectRuleSettingsBO.class);

		return helper.convertBOtoTo_ExamSubjectRuleSettingsPracticalInternalTO(
				bo, practicalIntTO);

	}

	public ExamSubjectRuleSettingsPracticalESETO setPracticalESEMultipleAnswerScript(
			int subRuleId, ExamSubjectRuleSettingsPracticalESETO practicalESETO) throws Exception {

		List<ExamSubjectRuleSettingsMultipleAnsScriptBO> listBO = new ArrayList(
				impl.select_All(subRuleId, "practical",
						ExamSubjectRuleSettingsMultipleAnsScriptBO.class));

		ExamSubjectRuleSettingsBO bo = (ExamSubjectRuleSettingsBO) impl
				.select_Unique(subRuleId, ExamSubjectRuleSettingsBO.class);

		return helper.convertBOtoTo_ExamSubjectRuleSettingsPracticalESETO(
				listBO, practicalESETO, bo);
	}

	public ExamSubjectRuleSettingsPracticalESETO setPracticalESEMultipleEvaluator(
			int subRuleId, ExamSubjectRuleSettingsPracticalESETO practicalESETO) throws Exception {
		List<ExamSubjecRuleSettingsMultipleEvaluatorBO> mulEvalBOList = new ArrayList(
				impl.select_All(subRuleId, "practical",
						ExamSubjecRuleSettingsMultipleEvaluatorBO.class));

		practicalESETO = helper
				.convertBOtoTo_ExamSubjectRuleSettingsPracticalESEmulEvalTO(
						mulEvalBOList, practicalESETO);

		return practicalESETO;
	}

	public ExamSubjectRuleSettingsTO getSubjectFinalDetails(int subRuleId) throws Exception {

		ExamSubjectRuleSettingsBO bo = (ExamSubjectRuleSettingsBO) impl
				.select_Unique(subRuleId, ExamSubjectRuleSettingsBO.class);

		return helper.converBOTosubjectFinalTO(bo);
	}

	public boolean updateSubjectRuleSettings(
			ExamSubjectRuleSettingsForm objform, HttpSession session) throws Exception {

		int id = 0;
		int academicYear = 0;

		if (CommonUtil.checkForEmpty(objform.getId())) {
			id = Integer.parseInt(objform.getId());
		}
		if (CommonUtil.checkForEmpty(objform.getAcademicYear()))

		{
			academicYear = Integer.parseInt(objform.getAcademicYear());
		}

		ExamSubjectRuleSettingsBO subjectRuleBO = getAllTOObjects(academicYear,
				session, objform.getUserId(), 0, 0, 0, objform.getSubTO(),
				objform.getSubTOPractical(), "update", id);

		ExamSubjectRuleSettingsBO tempBO = (ExamSubjectRuleSettingsBO) impl
				.select_Unique_Active(id, ExamSubjectRuleSettingsBO.class);

		subjectRuleBO.setIsActive(true);
		subjectRuleBO.setCourseId(tempBO.getCourseId());
		subjectRuleBO.setSubjectId(tempBO.getSubjectId());
		subjectRuleBO.setSchemeNo(tempBO.getSchemeNo());
		subjectRuleBO.setCreatedBy(tempBO.getCreatedBy());
		subjectRuleBO.setLastModifiedDate((new Date()));
		subjectRuleBO.setCreatedDate(tempBO.getCreatedDate());
		subjectRuleBO.setModifiedBy(tempBO.getCreatedBy());
		return impl.updateSubjectRuleSettings(subjectRuleBO);

	}

	public void updateAssignmentDetails(
			List<ExamSubjectRuleSettingsAssignmentTO> listAssignmentTO,
			int subjectRuleId) throws Exception {

		ExamSubjectRuleSettingsAssignmentBO assignmentBO = null;

		for (ExamSubjectRuleSettingsAssignmentTO to : listAssignmentTO) {
			BigDecimal maximumMark = null;
			BigDecimal minimumMark = null;
			int id = (to.getId() != null && to.getId().trim().length() > 0 ? Integer
					.parseInt(to.getId())
					: 0);
			maximumMark = null;
			minimumMark = null;

			if (to.getMaximumAssignMarks() != null
					&& to.getMaximumAssignMarks().trim().length() > 0) {
				maximumMark = new BigDecimal(to.getMaximumAssignMarks());
			}

			if (to.getMinimumAssignMarks() != null
					&& to.getMinimumAssignMarks().trim().length() > 0) {
				minimumMark = new BigDecimal(to.getMinimumAssignMarks());
			}

			assignmentBO = new ExamSubjectRuleSettingsAssignmentBO();
			assignmentBO.setId(id);
			assignmentBO.setSubjectRuleSettingsId(subjectRuleId);
			assignmentBO.setMaximumMark(maximumMark);
			assignmentBO.setMinimumMark(minimumMark);
			assignmentBO.setIsTheoryPractical(to.getIsTheoryPractical());
			assignmentBO.setAssignmentTypeId(to.getAssignmentTypeId());
			assignmentBO.setIsActive(true);

			Object object = impl.select_Unique(id,
					ExamSubjectRuleSettingsAssignmentBO.class);
			ExamSubjectRuleSettingsAssignmentBO bo_sub = (object == null ? null
					: (ExamSubjectRuleSettingsAssignmentBO) object);
			if (bo_sub != null) {
				impl.update(assignmentBO);
			} else {
				impl.insert(assignmentBO);

			}

		}

	}

	public void updateMultipleAnswerScriptDetails(
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO,
			int subjectRuleId) throws Exception {

		BigDecimal value = null;
		ExamSubjectRuleSettingsMultipleAnsScriptBO bo = null;

		for (ExamSubjectRuleSettingsMultipleAnswerScriptTO to : listTO) {
			value = null;
			int id = (to.getId() != null && to.getId().trim().length() > 0 ? Integer
					.parseInt(to.getId())
					: 0);
			if (to.getMultipleAnswerScriptTheoryESE() != null
					&& to.getMultipleAnswerScriptTheoryESE().trim().length() > 0) {
				value = new BigDecimal(to.getMultipleAnswerScriptTheoryESE());
			}

			bo = new ExamSubjectRuleSettingsMultipleAnsScriptBO();
			bo.setId(id);
			bo.setSubjectRuleSettingsId(subjectRuleId);
			bo.setIsTheoryPractical(to.getIsTheoryPractical());
			bo.setMultipleAnswerScriptId(to.getMultipleAnswerScriptId());
			bo.setValue(value);
			bo.setIsActive(true);

			Object object = impl.select_Unique(id,
					ExamSubjectRuleSettingsMultipleAnsScriptBO.class);
			ExamSubjectRuleSettingsMultipleAnsScriptBO bo_sub = (object == null ? null
					: (ExamSubjectRuleSettingsMultipleAnsScriptBO) object);
			if (bo_sub != null) {
				bo_sub.setIsTheoryPractical(to.getIsTheoryPractical());
				bo_sub.setValue(value);
				bo_sub.setIsActive(true);
				impl.update(bo_sub);
			} else {
				impl.insert(bo);

			}
		}

	}

	public void updateSubInternalDetails(
			List<ExamSubjectRuleSettingsSubInternalTO> subTOList,
			int subjectRuleId) throws Exception {
		ExamSubjectRuleSettingsSubInternalBO bo = null;
		String isTheoryPractical = null;

		for (ExamSubjectRuleSettingsSubInternalTO to : subTOList) {
			BigDecimal minimumMark = null;
			BigDecimal enteredMaxMark = null;
			BigDecimal maximumMark = null;
			int id = (to.getId() != null && to.getId().trim().length() > 0 ? Integer
					.parseInt(to.getId())
					: 0);
			if (to.getMinimumMarks() != null
					&& to.getMinimumMarks().trim().length() > 0) {
				minimumMark = new BigDecimal(to.getMinimumMarks());
			}
			if (to.getMaximumMarks() != null
					&& to.getMaximumMarks().trim().length() > 0) {
				maximumMark = new BigDecimal(to.getMaximumMarks());
			}
			if (to.getEntryMaximumMarks() != null
					&& to.getEntryMaximumMarks().trim().length() > 0) {
				enteredMaxMark = new BigDecimal(to.getEntryMaximumMarks());
			}
			isTheoryPractical = to.getIsTheoryPractical();
			bo = new ExamSubjectRuleSettingsSubInternalBO();
			bo.setId(id);
			bo.setSubjectRuleSettingsId(subjectRuleId);
			bo.setMinimumMark(minimumMark);
			bo.setMaximumMark(maximumMark);
			bo.setEnteredMaxMark(enteredMaxMark);
			bo.setInternalExamTypeId((to.getInternalExamTypeId() != null
					&& to.getInternalExamTypeId().trim().length() > 0 ? Integer
					.parseInt(to.getInternalExamTypeId()) : 0));
			bo.setIsActive(true);
			bo.setIsTheoryPractical(isTheoryPractical);
			bo.setCreatedDate(new Date());
			Object object = impl.select_Unique(id,
					ExamSubjectRuleSettingsSubInternalBO.class);
			ExamSubjectRuleSettingsSubInternalBO bo_sub = (object == null ? null
					: (ExamSubjectRuleSettingsSubInternalBO) object);
			if (bo_sub != null) {
				impl.update(bo);
			} else {
				impl.insert(bo);

			}

		}

	}

	public void updateAttendanceDetails(
			ExamSubjectRuleSettingsAttendanceTO attTO, int subjectRuleId) throws Exception {

		ExamSubjectRuleSettingsAttendanceBO attendanceBO = null;
		int leaveAttendance = (attTO.getLeaveAttendance() != null
				&& attTO.getLeaveAttendance().equals("on") ? 1 : 0);
		int cocurricularAttendance = (attTO.getCoCurricularAttendance() != null
				&& attTO.getCoCurricularAttendance().equals("on") ? 1 : 0);
		Integer attendanceTypeId = null;
		// int id = 0;

		if (CommonUtil.checkForEmpty(attTO.getAttendanceType())) {
			attendanceTypeId = Integer.parseInt(attTO.getAttendanceType());
		}

		attendanceBO = new ExamSubjectRuleSettingsAttendanceBO();
		// if (attTO.getId() != null && attTO.getId().trim().length() > 0) {
		// id = Integer.parseInt(attTO.getId());
		// }

		ExamSubjectRuleSettingsAttendanceBO bo = impl.select_Unique_forAtt(
				subjectRuleId, attTO.getIsTheoryPractical());

		if (bo != null) {
			// bo.setId(id);
			bo.setAttendanceTypeId(attendanceTypeId);
			bo.setIsLeave(leaveAttendance);
			bo.setIsCoCurricular(cocurricularAttendance);
			bo.setIsTheoryPractical(attTO.getIsTheoryPractical());

			impl.update(bo);
		} else {

			attendanceBO.setSubjectRuleSettingsId(subjectRuleId);
			attendanceBO.setAttendanceTypeId(attendanceTypeId);
			attendanceBO.setIsLeave(leaveAttendance);
			attendanceBO.setIsCoCurricular(cocurricularAttendance);
			attendanceBO.setIsTheoryPractical(attTO.getIsTheoryPractical());
			attendanceBO.setIsActive(true);
			impl.insert(attendanceBO);

		}

	}

	public void updateMultipleEvaluatorDetails(
			ExamSubjectRuleSettingsTheoryESETO theoryESETO, int subjectRuleId) throws Exception {
		ExamSubjecRuleSettingsMultipleEvaluatorBO multpleEvalBO = null;

		Integer id = null;
		Integer noOFEvaluation = null;
		String typeOFEvaluations = null;
		List<KeyValueTO> evalIds = new ArrayList<KeyValueTO>();
		List<Object> listOfMultEvalBO = null;
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> listBO = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> insertList = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();

		if (CommonUtil.checkForEmpty(theoryESETO.getNoOfEvaluations())) {
			noOFEvaluation = Integer.parseInt(theoryESETO.getNoOfEvaluations());
		}

		if (CommonUtil.checkForEmpty(theoryESETO.getEvalId1())
				|| CommonUtil.checkForEmpty(theoryESETO.getId1())) {
			id = (theoryESETO.getId1() != null
					&& theoryESETO.getId1().trim().length() > 0 ? new Integer(
					theoryESETO.getId1()) : null);
			evalIds.add(new KeyValueTO(id, theoryESETO.getEvalId1()));
			id = null;
		}

		if (CommonUtil.checkForEmpty(theoryESETO.getEvalId2())
				|| CommonUtil.checkForEmpty(theoryESETO.getId2())) {
			id = (theoryESETO.getId2() != null
					&& theoryESETO.getId2().trim().length() > 0 ? new Integer(
					theoryESETO.getId2()) : null);
			evalIds.add(new KeyValueTO(id, theoryESETO.getEvalId2()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(theoryESETO.getEvalId3())
				|| CommonUtil.checkForEmpty(theoryESETO.getId3())) {
			id = (theoryESETO.getId3() != null
					&& theoryESETO.getId3().trim().length() > 0 ? new Integer(
					theoryESETO.getId3()) : null);
			evalIds.add(new KeyValueTO(id, theoryESETO.getEvalId3()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(theoryESETO.getEvalId4())
				|| CommonUtil.checkForEmpty(theoryESETO.getId4())) {
			id = (theoryESETO.getId4() != null
					&& theoryESETO.getId4().trim().length() > 0 ? new Integer(
					theoryESETO.getId4()) : null);
			evalIds.add(new KeyValueTO(id, theoryESETO.getEvalId4()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(theoryESETO.getEvalId5())
				|| CommonUtil.checkForEmpty(theoryESETO.getId5())) {
			id = (theoryESETO.getId5() != null
					&& theoryESETO.getId5().trim().length() > 0 ? new Integer(
					theoryESETO.getId5()) : null);
			evalIds.add(new KeyValueTO(id, theoryESETO.getEvalId5()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(theoryESETO.getTypeOfEvaluation())
				&& theoryESETO.getTypeOfEvaluation().length() > 0) {
			if (theoryESETO.getTypeOfEvaluation().equalsIgnoreCase("1"))
				typeOFEvaluations = "Average";
			else if (theoryESETO.getTypeOfEvaluation().equalsIgnoreCase("2"))
				typeOFEvaluations = "Highest";
			else if (theoryESETO.getTypeOfEvaluation().equalsIgnoreCase("3"))
				typeOFEvaluations = "Lowest";
			else
				typeOFEvaluations = null;

		}
		listOfMultEvalBO = impl.select_All(subjectRuleId, "theory",
				ExamSubjecRuleSettingsMultipleEvaluatorBO.class);
		if (evalIds.size() > 0) {
			for (KeyValueTO evalId : evalIds) {
				multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO();
				multpleEvalBO.setSubjectRuleSettingsId(subjectRuleId);
				multpleEvalBO
						.setEvaluatorId((evalId.getDisplay() != null
								&& evalId.getDisplay().trim().length() > 0 ? new Integer(
								evalId.getDisplay())
								: null));
				multpleEvalBO.setTypeOfEvaluation(typeOFEvaluations);
				multpleEvalBO.setNoOfEvaluations(noOFEvaluation);
				multpleEvalBO.setIsTheoryPractical("t");
				multpleEvalBO.setIsActive(true);
				if (evalId.getId() == null) {
					insertList.add(multpleEvalBO);
				} else {
					multpleEvalBO.setId(evalId.getId());
					listBO.add(multpleEvalBO);
				}
			}
		} else if (listOfMultEvalBO != null && listOfMultEvalBO.size() > 0) {
			for (Iterator<Object> it = listOfMultEvalBO.iterator(); it
					.hasNext();) {
				ExamSubjecRuleSettingsMultipleEvaluatorBO bo = (ExamSubjecRuleSettingsMultipleEvaluatorBO) it
						.next();
				bo.setEvaluatorId(null);
				bo.setTypeOfEvaluation(typeOFEvaluations);
				bo.setNoOfEvaluations(noOFEvaluation);
				bo.setIsActive(true);
				listBO.add(bo);

			}

		}

		if (listOfMultEvalBO != null && listOfMultEvalBO.size() > 0) {
			impl.update_List(listBO);
		}
		if (insertList != null && insertList.size() > 0) {
			impl.insert_List(insertList);

		}

	}

	public void updateMultipleEvaluatorDetails(
			ExamSubjectRuleSettingsPracticalESETO practicalESETO,
			int subjectRuleId) throws Exception {
		ExamSubjecRuleSettingsMultipleEvaluatorBO multpleEvalBO = null;
		Integer id = null;
		int noOFEvaluation = 0;
		String typeOFEvaluations = null;
		List<Object> listOfMultEvalBO = null;
		List<KeyValueTO> evalIds = new ArrayList<KeyValueTO>();
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> insertList = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();
		ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> listBO = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();

		if (CommonUtil.checkForEmpty(practicalESETO.getNoOfEvaluations())) {
			noOFEvaluation = Integer.parseInt(practicalESETO
					.getNoOfEvaluations().trim());
		}

		if (CommonUtil.checkForEmpty(practicalESETO.getEvalId1())
				|| CommonUtil.checkForEmpty(practicalESETO.getId1())) {
			id = (practicalESETO.getId1() != null
					&& practicalESETO.getId1().trim().length() > 0 ? new Integer(
					practicalESETO.getId1())
					: null);
			evalIds.add(new KeyValueTO(id, practicalESETO.getEvalId1()));
			id = null;
		}

		if (CommonUtil.checkForEmpty(practicalESETO.getEvalId2())
				|| CommonUtil.checkForEmpty(practicalESETO.getId2())) {
			id = (practicalESETO.getId2() != null
					&& practicalESETO.getId2().trim().length() > 0 ? new Integer(
					practicalESETO.getId2())
					: null);
			evalIds.add(new KeyValueTO(id, practicalESETO.getEvalId2()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(practicalESETO.getEvalId3())
				|| CommonUtil.checkForEmpty(practicalESETO.getId3())) {
			id = (practicalESETO.getId3() != null
					&& practicalESETO.getId3().trim().length() > 0 ? new Integer(
					practicalESETO.getId3())
					: null);
			evalIds.add(new KeyValueTO(id, practicalESETO.getEvalId3()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(practicalESETO.getEvalId4())
				|| CommonUtil.checkForEmpty(practicalESETO.getId4())) {
			id = (practicalESETO.getId4() != null
					&& practicalESETO.getId4().trim().length() > 0 ? new Integer(
					practicalESETO.getId4())
					: null);
			evalIds.add(new KeyValueTO(id, practicalESETO.getEvalId4()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(practicalESETO.getEvalId5())
				|| CommonUtil.checkForEmpty(practicalESETO.getId5())) {
			id = (practicalESETO.getId5() != null
					&& practicalESETO.getId5().trim().length() > 0 ? new Integer(
					practicalESETO.getId5())
					: null);
			evalIds.add(new KeyValueTO(id, practicalESETO.getEvalId5()));
			id = null;
		}
		if (CommonUtil.checkForEmpty(practicalESETO.getTypeOfEvaluation())
				&& practicalESETO.getTypeOfEvaluation().length() > 0) {
			if (practicalESETO.getTypeOfEvaluation().equalsIgnoreCase("1"))
				typeOFEvaluations = "Average";
			else if (practicalESETO.getTypeOfEvaluation().equalsIgnoreCase("2"))
				typeOFEvaluations = "Highest";
			else if (practicalESETO.getTypeOfEvaluation().equalsIgnoreCase("3"))
				typeOFEvaluations = "Lowest";

			else

				typeOFEvaluations = null;

		}
		listOfMultEvalBO = impl.select_All(subjectRuleId, "practical",
				ExamSubjecRuleSettingsMultipleEvaluatorBO.class);
		if (evalIds.size() > 0) {
			for (KeyValueTO evalId : evalIds) {
				multpleEvalBO = new ExamSubjecRuleSettingsMultipleEvaluatorBO();
				multpleEvalBO.setSubjectRuleSettingsId(subjectRuleId);
				multpleEvalBO
						.setEvaluatorId((evalId.getDisplay() == null ? null
								: new Integer(evalId.getDisplay())));
				multpleEvalBO.setTypeOfEvaluation(typeOFEvaluations);
				multpleEvalBO.setNoOfEvaluations(noOFEvaluation);
				multpleEvalBO.setIsTheoryPractical("p");
				multpleEvalBO.setIsActive(true);
				if (evalId.getId() == null) {
					insertList.add(multpleEvalBO);
				} else {
					multpleEvalBO.setId(evalId.getId());
					listBO.add(multpleEvalBO);
				}
			}
		}

		else if (listOfMultEvalBO != null && listOfMultEvalBO.size() > 0) {
			for (Iterator<Object> it = listOfMultEvalBO.iterator(); it
					.hasNext();) {
				ExamSubjecRuleSettingsMultipleEvaluatorBO bo = (ExamSubjecRuleSettingsMultipleEvaluatorBO) it
						.next();
				bo.setEvaluatorId(null);
				bo.setTypeOfEvaluation(typeOFEvaluations);
				bo.setNoOfEvaluations(noOFEvaluation);
				bo.setIsActive(true);
				listBO.add(bo);
			}

		}

		if (listOfMultEvalBO != null && listOfMultEvalBO.size() > 0) {
			impl.update_List(listBO);
		}
		if (insertList != null && insertList.size() > 0) {
			impl.insert_List(insertList);

		}

	}

	public void isDuplicated(String academic_Year, String schemeType,
			ArrayList<Integer> listCourses) throws Exception

	{
		Map<Integer, String> map = null;
		int academicYear = 0;
		if (CommonUtil.checkForEmpty(academic_Year)) {
			academicYear = Integer.parseInt(academic_Year);
		}

		for (Integer courseID : listCourses) {
			Iterator it = null;

			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
			if (map != null && map.size() > 0) {
				it = map.keySet().iterator();

				Integer key = (Integer) it.next();

				int value = Integer.parseInt(map.get(key));

				for (int i = 1; i <= value; i++) {

					if (i % 2 == 0) {

						if (schemeType.equalsIgnoreCase("even")
								|| schemeType.equalsIgnoreCase("both")) {

							ExamSubjectRuleSettingsBO objbo = impl.isDuplicated(0, courseID, i, academicYear);
							if (objbo!= null && objbo.getIsActive() == true) {
								
								throw new DuplicateException();
							} else
							if(objbo!=null &&objbo.getIsActive()==false){
	
								throw new ReActivateException(objbo.getId());
							}
						}

					} else {

						if (schemeType.equalsIgnoreCase("odd")
								|| schemeType.equalsIgnoreCase("both")) {

							ExamSubjectRuleSettingsBO objbo = impl.isDuplicated(0, courseID, i, academicYear);
							if(objbo!=null){
							if (objbo.getIsActive() == true) {
	
								throw new DuplicateException();
							} else {
	
								throw new ReActivateException(objbo.getId());
							}
							}
						}

					}
				}

			}
		}
	}

	public boolean reactivate(ExamSubjectRuleSettingsTO reactivationTO)
			throws Exception {
		List<Integer> listCourseIds = reactivationTO.getCourseIdList();
		Map<Integer, String> map = null;
		String schemeType = reactivationTO.getSchemeType();
		int academicYear = 0;
		if (CommonUtil.checkForEmpty(reactivationTO.getAcademicYear())) {
			academicYear = Integer.parseInt(reactivationTO.getAcademicYear());
		}
		for (Integer courseID : listCourseIds) {
			Iterator it = null;
			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
			if (map != null && map.size() > 0) {
				it = map.keySet().iterator();
				Integer key = (Integer) it.next();
				int value = Integer.parseInt(map.get(key));
				for (int i = 1; i <= value; i++) {
					if (i % 2 == 0) {
						try {
							if (schemeType.equalsIgnoreCase("even")
									|| schemeType.equalsIgnoreCase("both")) {

								impl.delete_SubjectRuleSettings(academicYear,
										courseID, i, true);
							}
						} catch (Exception e) {

							log.error(e.getMessage());
						}
					} else {
						try {
							if (schemeType.equalsIgnoreCase("odd")
									|| schemeType.equalsIgnoreCase("both")) {

								impl.delete_SubjectRuleSettings(academicYear,
										courseID, i, true);
							}

						} catch (Exception e) {

							log.error(e.getMessage());
						}
					}
				}
			}

		}
		return true;
	}

	// public void addNewlyAddedSubject(int curriculumschemeId, int courseId,
	// int academicYear, int noScheme) throws Exception {
	//
	// HashMap<Integer, ArrayList<Integer>> map_schemeNo_SubList = impl
	// .getScheme_sub(curriculumschemeId, courseId, academicYear,
	// noScheme);
	//
	// ArrayList<Integer> subList = new ArrayList<Integer>();
	// int schemeNo = 0;
	// List<Integer> oldIdList = new ArrayList<Integer>();
	// List<Integer> ids = null;
	// ArrayList<ExamSubjectRuleSettingsBO> subRuleBO = new
	// ArrayList<ExamSubjectRuleSettingsBO>();
	//
	// ExamSubjectRuleSettingsBO subjectRulesBO = impl
	// .getSubRuleSettingsOfFromAcaYear(courseId, schemeNo,
	// academicYear);
	// if (subjectRulesBO != null) {
	// oldIdList.add(subjectRulesBO.getId());
	// }
	// if (subList != null && subList.size() > 0) {
	//
	// for (Integer subId : subList) {
	//
	// subjectRulesBO.setSubjectId(subId);
	// subjectRulesBO.setCourseId(courseId);
	// subjectRulesBO.setSchemeNo(schemeNo);
	// subjectRulesBO.setAcademicYear(academicYear);
	//
	// subRuleBO.add(new ExamSubjectRuleSettingsBO(subjectRulesBO));
	//
	// }
	// } else {
	// log
	// .warn("No subjects available for the course:\t" + courseId
	//
	// + "\t schemeNo:\t" + schemeNo + "\t academicYear:\t"
	// + academicYear);
	// }
	//
	// if (subRuleBO != null && subRuleBO.size() > 0) {
	// ids = impl.insert_SubjectRuleSettingsList(subRuleBO);
	//
	// // insert into ExamSubjectRuleSettingsSubInternal table
	// impl.copyToRelatedTable("ExamSubjectRuleSettingsSubInternalBO",
	// ids, oldIdList);
	//
	// // insert into ExamSubjectRuleSettingsAssignment table
	// impl.copyToRelatedTable("ExamSubjectRuleSettingsAssignmentBO", ids,
	// oldIdList);
	//
	// // insert into ExamSubjectRuleSettingsAttendance table
	// impl.copyToRelatedTable("ExamSubjectRuleSettingsAttendanceBO", ids,
	// oldIdList);
	//
	// // insert into ExamSubjectRuleSettingsMultipleAnsScript
	// // table
	// impl.copyToRelatedTable(
	// "ExamSubjectRuleSettingsMultipleAnsScriptBO", ids,
	// oldIdList);
	//
	// // insert into ExamSubjecRuleSettingsMultipleEvaluator
	// // table
	// impl
	// .copyToRelatedTable(
	// "ExamSubjecRuleSettingsMultipleEvaluatorBO", ids,
	// oldIdList);
	// }
	//
	// }

	public String copy(ExamSubjectRuleSettingsForm objform) throws Exception {
		String message = null;

		int fromAcademicYear = 0;

		int toAcademicYear = 0;
		if (CommonUtil.checkForEmpty(objform.getAcademicYear())
				&& objform.getAcademicYear().trim().length() > 0) {
			fromAcademicYear = Integer.parseInt(objform.getAcademicYear());
		}

		if (CommonUtil.checkForEmpty(objform.getCopyAcademicYear())
				&& objform.getCopyAcademicYear().trim().length() > 0) {
			toAcademicYear = Integer.parseInt(objform.getCopyAcademicYear());
		}
		String schemeType = objform.getSchemeName();
		ArrayList<Integer> courseIds = objform.getListCourses();
		List<Integer> ids = null;
		ArrayList<ExamSubjectRuleSettingsBO> subRuleBO = new ArrayList<ExamSubjectRuleSettingsBO>();
		if (courseIds != null && courseIds.size() > 0) {
			for (Integer courseID : courseIds) {

				ArrayList<KeyValueTO> list = getSchemeIDSchemeNOByCourse(
						Integer.toString(courseID), toAcademicYear);
				if (list != null && list.size() > 0) {

					for (KeyValueTO kvto : list) {
						Integer key = kvto.getId();

						int i = Integer.parseInt(kvto.getDisplay());

						List<Integer> oldIdList = new ArrayList<Integer>();
						ExamSubjectRuleSettingsBO subjectRulesBO = impl
								.getSubRuleSettingsOfFromAcaYear(courseID, i,

								fromAcademicYear);
						if (subjectRulesBO != null) {
							oldIdList.add(subjectRulesBO.getId());

							ArrayList<ExamSubjectRuleSettingsSubjectTO> subjectTO = getSubjects(
									courseID, key, i, toAcademicYear);
							ArrayList<Integer> lisstOfSubject;
							if (schemeType != null
									&& schemeType.equalsIgnoreCase("even")
									|| schemeType.equalsIgnoreCase("both")) {

								try {

									if (subjectTO != null
											&& subjectTO.size() > 0) {

										lisstOfSubject = new ArrayList<Integer>();
										for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {

											subjectRulesBO.setSubjectId(to
													.getSubid());
											subjectRulesBO
													.setCourseId(courseID);
											subjectRulesBO.setSchemeNo(i);
											subjectRulesBO
													.setAcademicYear(toAcademicYear);

											if (!lisstOfSubject.contains(to
													.getSubid())) {
												subRuleBO
														.add(new ExamSubjectRuleSettingsBO(
																subjectRulesBO));
												lisstOfSubject.add(to
														.getSubid());
											}

										}
									} else {
										log
												.warn("No subjects available for the course:\t"
														+ courseID
														+ "\t schemeID:\t"
														+ key
														+ "\t schemeNo:\t"
														+ i
														+ "\t academicYear:\t"
														+ toAcademicYear);
									}

								} catch (Exception e) {

									log.error(e.getMessage());
								}

							} else {

								try {

									if (subjectTO != null
											&& subjectTO.size() > 0) {
										lisstOfSubject = new ArrayList<Integer>();
										for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {

											// if (schemeType != null
											// && schemeType
											// .equalsIgnoreCase("odd")
											// || schemeType
											// .equalsIgnoreCase("both")) {

											subjectRulesBO.setSubjectId(to
													.getSubid());
											subjectRulesBO
													.setCourseId(courseID);
											subjectRulesBO
													.setAcademicYear(toAcademicYear);
											subjectRulesBO.setSchemeNo(i);
											if (!lisstOfSubject.contains(to
													.getSubid())) {
												subRuleBO
														.add(new ExamSubjectRuleSettingsBO(
																subjectRulesBO));
												lisstOfSubject.add(to
														.getSubid());
											}

											// subRuleBO
											// .add(new
											// ExamSubjectRuleSettingsBO(
											// subjectRulesBO));

										}
										// }
									} else {
										log
												.warn("No subjects available for the course:\t"
														+ courseID
														+ "\t schemeID:\t"
														+ key
														+ "\t schemeNo:\t"
														+ i
														+ "\t academicYear:\t"
														+ toAcademicYear);
									}

								} catch (Exception e) {

									log.error(e.getMessage());
									e.printStackTrace();
								}

							}

							if (subRuleBO != null && subRuleBO.size() > 0) {
								ids = impl
										.insert_SubjectRuleSettingsList(subRuleBO);

								// insert into
								// ExamSubjectRuleSettingsSubInternal
								// table
								impl.copyToRelatedTable(
										"ExamSubjectRuleSettingsSubInternalBO",
										ids, oldIdList);

								// insert into ExamSubjectRuleSettingsAssignment
								// table
								impl.copyToRelatedTable(
										"ExamSubjectRuleSettingsAssignmentBO",
										ids, oldIdList);

								// insert into ExamSubjectRuleSettingsAttendance
								// table
								impl.copyToRelatedTable(
										"ExamSubjectRuleSettingsAttendanceBO",
										ids, oldIdList);

								// insert into
								// ExamSubjectRuleSettingsMultipleAnsScript
								// table
								impl
										.copyToRelatedTable(
												"ExamSubjectRuleSettingsMultipleAnsScriptBO",
												ids, oldIdList);

								// insert into
								// ExamSubjecRuleSettingsMultipleEvaluator
								// table
								impl
										.copyToRelatedTable(
												"ExamSubjecRuleSettingsMultipleEvaluatorBO",
												ids, oldIdList);
								subRuleBO = new ArrayList<ExamSubjectRuleSettingsBO>();
							}
						}
					}
				} else {
					log.warn("No schemes available for the course:\t"
							+ courseID);
				}
				if (ids.size() > 0) {
					message = "Copied";
				} else {
					message = "Fail";
				}
			}

		} else {
			log
					.warn("No courses have been selected to add subject rule settings:\t"
							+ courseIds);
		}

		return message;
		// return ids;
	}

	public String insertSourceAcademicYearToDestAcademic(
			ExamSubjectRuleSettingsForm objform) throws Exception

	{
		String message = null;
		// insert with updated values into main table-subject rule setting
		int fromAcademicYear = 0;
		int toAcademicYear = 0;
		if (CommonUtil.checkForEmpty(objform.getAcademicYear())
				&& objform.getAcademicYear().trim().length() > 0) {
			fromAcademicYear = Integer.parseInt(objform.getAcademicYear());
		}

		if (CommonUtil.checkForEmpty(objform.getCopyAcademicYear())
				&& objform.getCopyAcademicYear().trim().length() > 0) {
			toAcademicYear = Integer.parseInt(objform.getCopyAcademicYear());
		}

		ArrayList<ExamSubjectRuleSettingsBO> fromAcademicYearListBO = getList(
				fromAcademicYear, toAcademicYear, objform.getListCourses(),
				objform.getSchemeName());

		if (fromAcademicYearListBO.size() == 0) {
			return "NoRecords";
		} else if (fromAcademicYearListBO.size() > 0
				&& fromAcademicYearListBO.get(0).getIsActiveInt() == 0) {
			message = "DataPresent";
		}

		else {
			// insert into table
			List<Integer> oldIdList = new ArrayList<Integer>();
			ArrayList<ExamSubjectRuleSettingsBO> insertToAcademicYearListBO = new ArrayList<ExamSubjectRuleSettingsBO>();

			for (Iterator<ExamSubjectRuleSettingsBO> itr = fromAcademicYearListBO
					.iterator(); itr.hasNext();) {

				ExamSubjectRuleSettingsBO bo = itr.next();

				oldIdList.add(bo.getId());

				bo.setAcademicYear(toAcademicYear);
				bo.setCreatedDate(new Date());

				insertToAcademicYearListBO.add(bo);

			}
			List<Integer> newSubjectRuleIds = impl
					.insert_SubjectRuleSettingsList(insertToAcademicYearListBO);

			// insert into ExamSubjectRuleSettingsSubInternal table
			impl.copyToRelatedTable("ExamSubjectRuleSettingsSubInternalBO",
					newSubjectRuleIds, oldIdList);

			// insert into ExamSubjectRuleSettingsAssignment table
			impl.copyToRelatedTable("ExamSubjectRuleSettingsAssignmentBO",
					newSubjectRuleIds, oldIdList);

			// insert into ExamSubjectRuleSettingsAttendance table
			impl.copyToRelatedTable("ExamSubjectRuleSettingsAttendanceBO",
					newSubjectRuleIds, oldIdList);

			// insert into ExamSubjectRuleSettingsMultipleAnsScript table
			impl.copyToRelatedTable(
					"ExamSubjectRuleSettingsMultipleAnsScriptBO",
					newSubjectRuleIds, oldIdList);

			// insert into ExamSubjecRuleSettingsMultipleEvaluator table
			impl.copyToRelatedTable(
					"ExamSubjecRuleSettingsMultipleEvaluatorBO",
					newSubjectRuleIds, oldIdList);

			if (newSubjectRuleIds.size() > 0) {
				message = "Copied";
			} else {
				message = "Fail";
			}

		}
		return message;
	}

	// Prepare the subject rule settings list based on the courseId,Academic
	// year and scheme no

	private ArrayList<ExamSubjectRuleSettingsBO> getList(int academicYear,
			int toAcademicYear, List<Integer> courseIds, String schemeType)
			throws Exception {
		Map<Integer, String> map = null;
		List<Integer> schemeList = new ArrayList<Integer>();
		List<Integer> dataList = new ArrayList<Integer>();
		for (Integer courseID : courseIds) {
			Iterator it = null;
			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
			if (map != null && map.size() > 0) {
				it = map.keySet().iterator();
				Integer key = (Integer) it.next();
				int value = Integer.parseInt(map.get(key));
				for (int i = 1; i <= value; i++) {
					if (i % 2 == 0) {
						try {
							if (schemeType.equalsIgnoreCase("even")
									|| schemeType.equalsIgnoreCase("both")) {
								int present = impl.checkIfDataIsPresent(
										toAcademicYear, i, courseID);
								if (present > 0) {
									dataList.add(present);
								}
								schemeList.add(i);
							}
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					} else {
						try {
							if (schemeType.equalsIgnoreCase("odd")
									|| schemeType.equalsIgnoreCase("both")) {

								int present = impl.checkIfDataIsPresent(
										toAcademicYear, i, courseID);
								if (present > 0) {
									dataList.add(present);
								}
								schemeList.add(i);
							}

						} catch (Exception e) {
							log.error(e.getMessage());
						}
					}
				}
			}
		}
		if (dataList.size() > 0) {
			ExamSubjectRuleSettingsBO bo = new ExamSubjectRuleSettingsBO();
			bo.setIsActiveInt(0);
			ArrayList<ExamSubjectRuleSettingsBO> list = new ArrayList<ExamSubjectRuleSettingsBO>();
			list.add(0, bo);
			return list;
		} else {
			return impl.getList(courseIds, schemeList, academicYear);
		}
	}

	public boolean chkNewSubjects(String academic_Year, String schemeType,
			ArrayList<Integer> listCourses) throws Exception

	{
		boolean newSubPresent=false;
		Map<Integer, String> map = null;
		int academicYear = 0;
		if (CommonUtil.checkForEmpty(academic_Year)) {
			academicYear = Integer.parseInt(academic_Year);
		}

		for (Integer courseID : listCourses) {
			Iterator it = null;

			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));
			if (map != null && map.size() > 0) {
				it = map.keySet().iterator();

				Integer key = (Integer) it.next();

				int value = Integer.parseInt(map.get(key));

				for (int i = 1; i <= value; i++) {

					if (i % 2 == 0) {

						if (schemeType.equalsIgnoreCase("even")
								|| schemeType.equalsIgnoreCase("both")) {

							if(impl.newSubjects(key, courseID, i, academicYear)){
								newSubPresent=true;
								break;
							}
						}

					} else {

						if (schemeType.equalsIgnoreCase("odd")
								|| schemeType.equalsIgnoreCase("both")) {

							if(impl.newSubjects(key, courseID, i, academicYear)){
								newSubPresent=true;
								break;
							}

						}

					}
				}

			}
		}
		return newSubPresent;
	}

	public boolean deleteSubjectRuleSettingSubjectWise(String id)throws Exception 
	{
		return impl.deleteSubjectRuleSettingSubjectWise(id);
	}

}
