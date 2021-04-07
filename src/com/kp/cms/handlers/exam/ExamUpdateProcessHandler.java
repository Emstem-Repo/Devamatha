package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamStudentAssignmenteMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentAttendanceMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentDetentionDetailsBO;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentInternalFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentPassFail;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.helpers.exam.ExamUpdateProcessHelper;
import com.kp.cms.to.exam.BatchClassTO;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamUpdateProcessMapTO;
import com.kp.cms.to.exam.ExamUpdateProcessTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.SubInfoTO;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unused")
public class ExamUpdateProcessHandler extends ExamGenHandler {

	ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();

	ExamUpdateProcessHelper helper = new ExamUpdateProcessHelper();
	

	private static final int PROCESS_CALC_REGULAR_MARKS = 1;
	private static final int PROCESS_CALC_INTERNAL_MARKS = 2;
	private static final int PROCESS_SUPPLEMENTARY_DATA_CREATION = 3;
	private static final int PROCESS_UPDATE_DETENTION = 4;
	private static final int PROCESS_PROMOTION = 5;

	public void update(int processId, ArrayList<BatchClassTO> listBatchClass,
			Integer examId, Integer academicYear,
			boolean checkPromotionCriteria, boolean checkExamFeePaid,
			String isPreviousExam, ActionErrors err) throws Exception {
		switch (processId) {
		case PROCESS_CALC_REGULAR_MARKS:
			process_CalcRegularMarks(listBatchClass, examId, isPreviousExam, err);
			process_PassOrFail(listBatchClass, examId, isPreviousExam, err);
			break;

		case PROCESS_CALC_INTERNAL_MARKS:
			process_CalcInternalMarks(listBatchClass, examId, isPreviousExam);
			break;
		case PROCESS_SUPPLEMENTARY_DATA_CREATION:
			//process_SupplementaryDataCreation(listBatchClass, examId);
			process_SupplementaryDataCreationNew(listBatchClass, examId, isPreviousExam);
			break;
		case PROCESS_UPDATE_DETENTION:
			process_UpdateDetention(listBatchClass, academicYear);
			break;
		case PROCESS_PROMOTION:
			process_Promotion(listBatchClass, academicYear,
					checkPromotionCriteria, checkExamFeePaid);
			break;

		}
	}

	public String getProcessName(int id) {
		String processName = null;
		switch (id) {
		case 1:
			processName = "Calculate Overall regular Exam marks";

			break;
		case 2:
			processName = "Calculate Overall Internal marks";

			break;

		case 3:
			processName = "Supplementary data creation";

			break;

		case 4:
			processName = "Update Detention";

			break;

		case 5:
			processName = "Promotion";

			break;

		}
		return processName;
	}

	// To FETCH the joining batch & classes based on the process selected
	public ArrayList<BatchClassTO> getClasses(Integer examId,
			Integer academicYear, String type) {
		if (type.equalsIgnoreCase("Supplementary data creation")) {
			return getClassJoiningBatch(examId);
		}

		return helper
				.convertBOToTO(impl.get_classDetails(examId, academicYear));

	}

	// To FETCH students for a class
	public ArrayList<Integer> getStudentsForClass(Integer classId) {

		return impl.getStudentsForClass(classId);
	}

	// To FETCH students for a class
	public ArrayList<Integer> getStudentsForClassWithPrevExam(Integer classId,
			String isPreviousExam) {

		return impl.getStudentsForClassWithPrevExam(classId, isPreviousExam);
	}

	// ==========================================================================
	// =========================================================================
	// ======================= PROCESS - OVERALL - REGULAR ====================

	// To FETCH subjects for a given class which are not regular
	public ArrayList<Integer> getSubjectsForClass(Integer classId) {

		return impl.getSubjectsForClass(classId);
	}

	// To fetch student's subjects
	private ArrayList<Integer> getSubjectsForStudent(Integer stuId) {

		return impl.getSubjectsForStudent(stuId);
	}

	private ArrayList<Integer> getSubjectsForStudentWithPrevExam(Integer stuId,
			String isPreviousExam,int schemeNo) {

		return impl.getSubjectsForStudentWithPrevExam(stuId, isPreviousExam,schemeNo);
	}

	// To get evaluation type from SUBJECT - RULE - SETTINGS
	public List<Object[]> get_type_of_evaluation_theory(Integer subjectId,
			List<Object[]> courseIdschemeNoRows, String subjectType, String academicYear) {
		if (courseIdschemeNoRows != null && courseIdschemeNoRows.size() > 0) {
			Object[] courseIdschemeNo = courseIdschemeNoRows.get(0);
			return impl.get_type_of_evaluation_theory(subjectId,
					(Integer) courseIdschemeNo[0],
					(Integer) courseIdschemeNo[1], subjectType, academicYear);
		} else {
			return new ArrayList<Object[]>();
		}

	}

	public List<Object[]> get_type_of_evaluation_practical(Integer subjectId,
			List<Object[]> courseIdschemeNoRows, String subjectType, String academicYear) {
		if (courseIdschemeNoRows != null && courseIdschemeNoRows.size() > 0) {
			Object[] courseIdschemeNo = courseIdschemeNoRows.get(0);
			return impl.get_type_of_evaluation_practical(subjectId,
					(Integer) courseIdschemeNo[0],
					(Integer) courseIdschemeNo[1], subjectType, academicYear);
		} else {
			return new ArrayList<Object[]>();
		}

	}

	// To get students' final marks
	public List<Object[]> get_student_mark_details(Integer classId,
			Integer examId, ArrayList<Integer> subjectList, Integer studentId,
			Integer courseId) {

		return impl.get_student_mark_details(examId, subjectList, studentId,
				courseId);

	}

	// To get course & scheme for a subject & class
	public List<Object[]> get_course_scheme_of_subject(Integer subjectId,
			Integer classId) {

		return impl.get_course_scheme_of_subject(subjectId, classId);

	}

	private void process_CalcRegularMarks (
			ArrayList<BatchClassTO> listBatchClass, Integer examId,
			String isPreviousExam, ActionErrors err) throws Exception {

		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		for (BatchClassTO bTo : listBatchClass) {
			Integer clsId = null;

			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}
			Integer schemeNo = bTo.getSemester();

			// Delete previous entries for a class & then insert

			studentList = getStudentsForClassWithPrevExam(clsId, isPreviousExam);

			HashMap<Integer, ArrayList<SubInfoTO>> evaluationDetailsT = new HashMap<Integer, ArrayList<SubInfoTO>>();
			HashMap<Integer, ArrayList<SubInfoTO>> evaluationDetailsP = new HashMap<Integer, ArrayList<SubInfoTO>>();
			ArrayList<ExamStudentFinalMarkDetailsBO> listToInsert = new ArrayList<ExamStudentFinalMarkDetailsBO>();

			// To find the type of evaluation for each subject
			List<Object[]> listT = null;
			List<Object[]> listP = null;
			String Theory_Preferred = "evaluate";
			String Practical_Preferred = "evaluate";
			// below list will only contain single subject
			Integer courseId = impl.getCourseOfStudent(clsId,true);
			for (Integer stuId : studentList) {
				ArrayList<StudentMarkDetailsTO> stuMarkDetails = new ArrayList<StudentMarkDetailsTO>();
				stuMarkDetails.clear();
				subjectsList = impl.getSubjectsForStudentWithPrevSchemeNo(stuId,isPreviousExam, schemeNo);
					/*getSubjectsForStudentWithPrevExam(stuId,
						isPreviousExam);*/
//				Integer courseId = impl.getCourseOfStudent(stuId);
				if(subjectsList == null || subjectsList.size() <= 0){
					err.add(CMSConstants.ERRORS, new ActionError("knowledgepro.exam.update.process.subject.not.found", bTo.getClassName(), stuId));
					throw new DataNotFoundException();
				}
				if (subjectsList != null) {

					for (Integer subId : subjectsList) {
						listT = new ArrayList<Object[]>();
						listP = new ArrayList<Object[]>();
						ArrayList<Integer> tempSubList = new ArrayList<Integer>();
						tempSubList.add(subId);
						String subjectType = getSubjectsTypeBySubjectId(subId);
						/*Integer schemeNo = impl.getSchemeNoOfStudent(stuId);
						if(isPreviousExam.equalsIgnoreCase("true")){
							schemeNo = schemeNo - 1;
						}*/
						DisplayValueTO regularCheck = impl.getRegularCheckForSubject(subId,
								bTo.getJoiningBatch(), subjectType, courseId,
								schemeNo);

						DisplayValueTO multipleEvalCheck = impl
								.getmultipleEvalCheckForSubject(subId, bTo
										.getJoiningBatch(), subjectType,
										courseId, schemeNo);
						DisplayValueTO ansScriptCheck = impl
								.getMultipleAnsScriptCheckForSubject(subId, bTo
										.getJoiningBatch(), subjectType,
										courseId, schemeNo);

						// If subject is theory
						if (subjectType.equalsIgnoreCase("t")
								|| subjectType.equalsIgnoreCase("b")) {

							if ((regularCheck.getDisplay() != null && regularCheck
									.getDisplay().equals("1"))
									|| (ansScriptCheck.getDisplay() != null && ansScriptCheck
											.getDisplay().equals("1"))) {

								// Calculate REGULAR theory marks
								evaluationDetailsT
										.put(
												subId,
												helper
														.convertToTO_SubInfoTO(get_type_of_evaluation_theory(
																subId,
																get_course_scheme_of_subject(
																		subId,
																		clsId),
																"t" ,bTo
																.getJoiningBatch())));

								listT = getStudentMarkTheoryReg(clsId, examId,
										tempSubList, stuId);

								Theory_Preferred = "Highest";

							} else if (multipleEvalCheck.getDisplay() != null
									&& multipleEvalCheck.getDisplay().equals(
											"1")) {

								Theory_Preferred = "evaluate";
								// Calculate MULTIPLE EVALUATOR/ANSSCRIPT theory
								// marks
								evaluationDetailsT
										.put(
												subId,
												helper
														.convertToTO_SubInfoTO(get_type_of_evaluation_theory(
																subId,
																get_course_scheme_of_subject(
																		subId,
																		clsId),
																"t",bTo
																.getJoiningBatch())));
								listT = getStudentMarkTheoryMulEval(clsId,
										examId, tempSubList, stuId, courseId, schemeNo);

							}
						}

						// If subject is practical
						if (subjectType.equalsIgnoreCase("p")
								|| subjectType.equalsIgnoreCase("b")) {

							// Calculate REGULAR practical marks
							if ((regularCheck.getValue() != null && regularCheck
									.getValue().equals("1"))
									|| (ansScriptCheck.getValue() != null && ansScriptCheck
											.getValue().equals("1"))) {

								evaluationDetailsP
										.put(
												subId,
												helper
														.convertToTO_SubInfoTO(get_type_of_evaluation_practical(
																subId,
																get_course_scheme_of_subject(
																		subId,
																		clsId),
																"p",bTo
																.getJoiningBatch())));
								listP = getStudentMarkPracReg(clsId, examId,
										tempSubList, stuId);
								Practical_Preferred = "Highest";

							} else if (multipleEvalCheck.getValue() != null
									&& multipleEvalCheck.getValue().equals("1")) {

								Practical_Preferred = "evaluate";
								// Calculate MULTIPLE EVALUATOR/ANSSCRIPT
								// practical marks
								evaluationDetailsP
										.put(
												subId,
												helper
														.convertToTO_SubInfoTO(get_type_of_evaluation_practical(
																subId,
																get_course_scheme_of_subject(
																		subId,
																		clsId),
																"p",bTo
																.getJoiningBatch())));
								listP = getStudentMarkPracMulEval(clsId,
										examId, tempSubList, stuId, courseId, schemeNo);

							}
						}
						 ArrayList<StudentMarkDetailsTO> list = helper.convertBOToTO_mark_details(listT, listP,
								Theory_Preferred, Practical_Preferred);
						stuMarkDetails.addAll(list);

					}

				}
				listToInsert
						.addAll(helper.set_details(clsId, examId, stuId,
								stuMarkDetails, evaluationDetailsT,
								evaluationDetailsP));
			}

			impl.insert_ListFinalMarks(listToInsert);

		}

	}

	private List<Object[]> getStudentMarkPracMulEval(Integer clsId,
			Integer examId, ArrayList<Integer> subjectsList, Integer stuId,
			Integer courseId, int schemeNo) {

		return impl.getStudentMarkPracMulEval(examId, subjectsList, stuId,
				courseId, schemeNo);
	}

	private List<Object[]> getStudentMarkPracReg(Integer clsId, Integer examId,
			ArrayList<Integer> subjectsList, Integer stuId) {

		return impl.getStudentMarkPracReg(examId, subjectsList, stuId);

	}

	private List<Object[]> getStudentMarkTheoryMulEval(Integer clsId,
			Integer examId, ArrayList<Integer> subjectsList, Integer stuId,
			Integer courseId, int schemeNo) {

		return impl.getStudentMarkTheoryMulEval(examId, subjectsList, stuId,
				courseId, schemeNo);

	}

	private List<Object[]> getStudentMarkTheoryReg(Integer clsId,
			Integer examId, ArrayList<Integer> subjectsList, Integer stuId) {

		return impl.getStudentMarkTheoryReg(examId, subjectsList, stuId);
	}

	// ==========================================================================
	// =========================================================================
	// ================== PROCESS - UPDATE DETENTION =====================

	// Get count of students' failed subjects
	public Integer get_count_failed_subjects(Integer classId,
			Integer studentId, ArrayList<Integer> subjectId) {

		return impl.get_count_failed_subjects(classId, studentId, subjectId);

	}

	// Get course & schemeNo for a class
	public List<Object[]> get_course_schemeNo_forClass(Integer classId) {

		return impl.get_course_schemeNo_forClass(classId);

	}

	public boolean get_numberLog_percentage(Integer courseId, Integer schemeNo) {
		return impl.get_numberLog_percentage(courseId, schemeNo);
	}

	// To get maxBackLogNumber OR maxBackLogPercentage
	public String get_maxPecent_OrMaxLog(Integer courseId, Integer schemeNo) {

		return impl.get_maxPecent_OrMaxLog(courseId, schemeNo,
				get_numberLog_percentage(courseId, schemeNo));

	}

	// To add updateDetention
	private void process_UpdateDetention(
			ArrayList<BatchClassTO> listBatchClass, int academicYear) {
		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		for (BatchClassTO bTo : listBatchClass) {
			Integer clsId = null;

			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}

			studentList = getStudentsForClass(clsId);

			ArrayList<ExamStudentDetentionDetailsBO> listToInsert = new ArrayList<ExamStudentDetentionDetailsBO>();
			for (Integer stuId : studentList) {

				subjectsList = getSubjectsForStudent(stuId);

				int failedSubjects = get_count_failed_subjects(clsId, stuId,
						subjectsList);

				List<Object[]> courseScheme = get_course_schemeNo_forClass(clsId);
				Integer schemeNo = 0, courseId = 0;
				if (courseScheme != null && courseScheme.size() > 0) {
					Object[] courseId_SchemeNo = courseScheme.get(0);
					courseId = (Integer) courseId_SchemeNo[0];
					schemeNo = (Integer) courseId_SchemeNo[1];
				}
				String maxBackOrMaxPercentage = get_maxPecent_OrMaxLog(
						courseId, schemeNo);
				if (maxBackOrMaxPercentage != null) {
					String value[] = maxBackOrMaxPercentage.split("_");
					if (value != null) {
						ExamStudentDetentionDetailsBO bo = helper
								.getExamStudentDetentionDetailsBO(value,
										failedSubjects, stuId, subjectsList,
										schemeNo);
						if (bo != null)
							listToInsert.add(bo);
					}
				}

			}

			impl.insert_List(listToInsert);
		}
	}

	// ==========================================================================
	// =========================================================================
	// ====================== PROCESS - PROMOTION ============================

	// To get non - detained students for a class
	public ArrayList<Integer> getNonDetainedStudentsForClass(Integer classId) {

		return impl.getNonDetainedStudentsForClass(classId);
	}

	// To get student's academic year for next scheme no
	public Integer getacademicYearForNextScheme(Integer studentId) {

		Integer currSchId = impl.getCurrSchemeOfStudent(studentId);
		Integer schemeNo = impl.getSchemeNoOfStudent(studentId);
		return impl.getacademicYearForNextScheme(currSchId, schemeNo);
	}

	private String get_class_section_name(Integer clsId) {

		return impl.get_class_section_name(clsId);
	}

	// To get student's next classSchemewise considering section for promotion
	public Integer getStudentNextClassSchemwiseForSection(Integer courseId,
			Integer schemeNo, String sectionName, Integer studentId) {

		Integer academicYear = getacademicYearForNextScheme(studentId);
		return impl.getStudentNextClassSchemwiseForSection(courseId, schemeNo,
				sectionName, academicYear);
	}

	// To get student's next class
	public Integer getStudentNextCla(Integer courseId, Integer schemeNo,
			Integer studentId, String sectionName) {

		Integer academicYear = getacademicYearForNextScheme(studentId);
		return impl.getStudentNextCla(courseId, schemeNo, sectionName,
				academicYear);
	}

	// To fetch the student's previous class' second language Id
	public Integer getSecondLanguageId(Integer stuId) {

		String a = impl.getSecondLanguageHistory(stuId);

		return impl.getSecondLanguageId(a);

	}

	// To fetch the student's next class' second language
	public HashMap<Integer, ArrayList<Integer>> getSecondLang_forSubGrp(
			Integer classId) {

		ArrayList<Integer> subGrpIds = impl.getNextSubjGrpForClass(classId);

		return getSecondLangAgainstSubGrp(subGrpIds);
	}

	// To get the second language against the subject group for new class
	public HashMap<Integer, ArrayList<Integer>> getSecondLangAgainstSubGrp(
			ArrayList<Integer> subGrpIds) {
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		map.put(1, subGrpIds);
		map.put(2, impl.getSecondLangAgainstSubGrp(subGrpIds));
		return map;
	}

	// To fetch the previous class' subject groups of a student
	public List<Object[]> getPrevSubjGrpForStudent(Integer studentId) {

		return impl.getPrevSubjGrpForStudent(studentId);
	}

	// To get subject groups for new class
	public ArrayList<Integer> getNextSubjGrpForClass(Integer classId) {

		return impl.getNextSubjGrpForClass(classId);
	}

	// To get the subject groups for the new class of student
	public List<Object[]> getNextSubjGrpForStudent(Integer studentId,
			Integer classId, Integer nextClassSchemewise) {

		return impl.getNextSubjGrpForStudent(studentId, classId,
				nextClassSchemewise);
	}

	// To get student's admission application id
	public Integer getStudentAdmnApplnId(Integer studentId) {

		return impl.getStudentAdmnApplnId(studentId);
	}

	// To fetch common subject groups
	public ArrayList<Integer> getCommonSubjectGroup(ArrayList<Integer> ids) {

		return impl.getCommonSubjectGroup(ids);
	}

	// To promote a student to next class & update his respective subject
	// groups,
	// common subject groups & classSchemewise

	@SuppressWarnings("unchecked")
	private void process_Promotion(ArrayList<BatchClassTO> listBatchClass,
			int academicYear, boolean checkPromotionCriteria,
			boolean checkExamFeePaid) {

		for (Iterator iterator = listBatchClass.iterator(); iterator.hasNext();) {
			BatchClassTO listTo = (BatchClassTO) iterator.next();
			Integer clsId = null;

			if (CommonUtil.checkForEmpty(listTo.getClassId())) {
				clsId = Integer.parseInt(listTo.getClassId());
			}
			List<Object[]> courseScheme = get_course_schemeNo_forClass(clsId);

			String sectionName = get_class_section_name(clsId);

			Integer schemeNo = 0, courseId = 0;
			if (courseScheme != null && courseScheme.size() > 0) {
				Object[] courseId_SchemeNo = courseScheme.get(0);
				courseId = (Integer) courseId_SchemeNo[0];
				schemeNo = (Integer) courseId_SchemeNo[1];
			}
			if (checkPromotionCriteria) {
				process_UpdateDetention(listBatchClass, academicYear);
			}
			ArrayList<Integer> stuList = getNonDetainedStudentsForClass(clsId);
			if (checkExamFeePaid) {
				for (Integer stuId : stuList) {
					ExamStudentDetentionDetailsBO bo = helper.getDetentionBo(
							stuId, schemeNo, impl.checkExamFeeStatus(stuId));
					if (bo != null) {
						impl.insert(bo);
					}
				}
			}
			ArrayList<Integer> stuList1 = getNonDetainedStudentsForClass(clsId);

			for (Integer stuId : stuList1) {

				Integer secondLanguage = getSecondLanguageId(stuId);
				Integer academicYearStu = getacademicYearForNextScheme(stuId);

				if (academicYearStu != null) {

					impl
							.update_subjectGroup(
									clsId,

									helper
											.convert_prev_subjGrp(getPrevSubjGrpForStudent(getStudentAdmnApplnId(stuId))),
									getStudentNextCla(courseId, schemeNo,
											stuId, sectionName),
									getStudentNextClassSchemwiseForSection(
											courseId, schemeNo, sectionName,
											stuId),
									stuId,
									getStudentAdmnApplnId(stuId),
									helper
											.convert_next_subjGrp(
													getNextSubjGrpForStudent(
															stuId,
															getStudentNextCla(
																	courseId,
																	schemeNo,
																	stuId,
																	sectionName),
															getStudentNextClassSchemwiseForSection(
																	courseId,
																	schemeNo,
																	sectionName,
																	stuId)),
													secondLanguage));

				}
			}
		}

	}

	// ==========================================================================
	// =========================================================================
	// ====================== PROCESS - INTERNAL OVERALL ===============

	// To get the internal examId for an exam
	public ArrayList<ExamUpdateProcessTO> get_internalExamId_forExam(
			Integer examId) {

		return helper.convertListToArrayList(impl
				.get_internalExamId_forExam(examId));
	}

	// To calculate & add INTERNAL MARKS
	/**
	 * @param listBatchClass
	 * @param examId
	 */

	// To get the value of 'best of' theory internal
	private Integer getTheoryLimit(Integer subId, Integer examId,
			Integer courseId, int schemeNo) {

		return impl.getBestOfTheoryInternal(subId, examId, courseId, schemeNo);
	}

	// To get the value of 'best of' practical internal
	private Integer getPracticalLimit(Integer subId, Integer examId,
			Integer courseId, int schemeNo) {

		return impl.getBestOfPracticalInternal(subId, examId, courseId, schemeNo);
	}

	// Check Sub Internal option in subject rule settings for theory
	private boolean subInternalTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.subInternalTheoryCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// Check Sub Internal option in subject rule settings for practical
	private boolean subInternalPracticalCheckSubjRuleSettings(
			Integer subjectId, Integer examId, Integer courseId, int schemeNo) {

		return impl.subInternalPracticalCheckSubjRuleSettings(subjectId,
				examId, courseId, schemeNo);

	}

	// Check Leave option in subject rule settings for theory
	private boolean leaveCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.leaveCheckSubjRuleSettings(subjectId, examId, courseId, schemeNo);

	}

	// Check CoCurricular option in subject rule settings attendance
	private boolean coCurricularCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.coCurricularCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// Check Attendance option in subject rule settings for theory
	private boolean attendanceTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.attendanceTheoryCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// Check Attendance option in subject rule settings for practical
	private boolean attendancePracticalCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.attendancePracticalCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// Check Assignment option in subject rule settings for theory
	private boolean assignmentTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.assignmentTheoryCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// Check Assignment option in subject rule settings for practical
	private boolean assignmentPracticalCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.assignmentPracticalCheckSubjRuleSettings(subjectId, examId,
				courseId, schemeNo);

	}

	// To fetch the minimum, maximum, entered total theory internal marks for a
	// subject
	public Integer getTotalMinMaxEnteredIntTheoryMarks(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.getTotalMinMaxEnteredIntTheoryMarks(subjectId, examId,
				courseId, schemeNo);

	}

	// To fetch the minimum, maximum, entered total practical internal marks for
	// a subject
	public Integer getTotalMinMaxEnteredIntPracticalMarks(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {

		return impl.getTotalMinMaxEnteredIntPracticalMarks(subjectId, examId,
				courseId, schemeNo);

	}

	private int theoryTotalAttendenceMarks(ExamUpdateProcessMapTO to,
			Integer subId, Integer examId, Integer courseId, int schemeNo) {
		 if (attendanceTheoryCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
			ExamUpdateProcessMapTO attendenceTo = to.getSubjectTheoryTo(subId);
			if (attendenceTo != null) {
				int attendenceCalculation = 0;
				float totalCalculation = 0;
				float totalCalculation1 = 0;
				String val = "";
				if (leaveCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
					attendenceCalculation += attendenceTo
							.getSumOfStudentOnLeave();
				}
				if (coCurricularCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
					attendenceCalculation += attendenceTo
							.getSumOfStudentOnCoCurricularLeave();
				}
				attendenceCalculation += attendenceTo.getSumOfStudentsPresent();
				if (attendenceTo.getTotalClassHourHeld() != 0) {
					/*totalCalculation = (float) (Math
							.floor((double) (attendenceCalculation * 100)
									/ (double) (attendenceTo
											.getTotalClassHourHeld()) + 0.5f));*/
					totalCalculation =attendenceCalculation * 100;
					totalCalculation1 = totalCalculation/(attendenceTo.getTotalClassHourHeld());
					totalCalculation = CommonUtil.roundToDecimal(totalCalculation1, 2);
				}
				return impl.getSubjectDefinitionMarks(totalCalculation, subId,
						courseId);
			} else
				return 0;
		 } else {
		 	return 0;
		 }

	}

	private int practicalTotalAttendenceMarks(ExamUpdateProcessMapTO to,
			Integer subId, Integer examId, Integer courseId, int schemeNo) {
		 if (attendancePracticalCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
			ExamUpdateProcessMapTO attendenceTo = to
					.getSubjectPracticalTo(subId);
			if (attendenceTo != null) {
				int attendenceCalculation = 0;
				float totalCalculation = 0;
				if (leaveCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
					attendenceCalculation += attendenceTo
							.getSumOfStudentOnLeave();
				}
				if (coCurricularCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
					attendenceCalculation += attendenceTo
							.getSumOfStudentOnCoCurricularLeave();
				}
				attendenceCalculation += attendenceTo.getSumOfStudentsPresent();
				if (attendenceTo.getTotalClassHourHeld() != 0) {

					/*totalCalculation = (float) (Math
							.floor((double) (attendenceCalculation * 100)
									/ (double) (attendenceTo
											.getTotalClassHourHeld()) + 0.5f));*/
					totalCalculation = attendenceCalculation * 100;
					float totalCalculation1 = totalCalculation/(attendenceTo.getTotalClassHourHeld());
					totalCalculation = CommonUtil.roundToDecimal(totalCalculation1, 2);
				}
				return impl.getSubjectDefinitionMarks(totalCalculation, subId,
						courseId);
			} else {
				return 0;
			}
		 }
		 return 0;

	}

	// Fetch total theory assignment marks against exam & subject for a student
	private String theoryTotalAssignmentMarks(Integer stuId, Integer subId) {

		return impl.theoryTotalAssignmentMarks(stuId, subId);
	}

	// Fetch total practical assignment marks against exam & subject for a
	// student
	private String practicalTotalAssignmentMarks(Integer stuId, Integer subId) {

		return impl.practicalTotalAssignmentMarks(stuId, subId);
	}

	// Student's internal marks
	public List<Object[]> getInternalMarksOfStudent(Integer internalExamId,
			Integer stuId, ArrayList<Integer> subjectsList) {

		return impl.getInternalMarksOfStudent(internalExamId, stuId,
				subjectsList);

	}

	// Calculate & add all the details into the respective tables
	@SuppressWarnings("unchecked")
	private void process_CalcInternalMarks(
			ArrayList<BatchClassTO> listBatchClass, Integer examId,
			String isPreviousExam) throws Exception {

		ArrayList<ExamUpdateProcessTO> internalExamList = get_internalExamId_forExam(examId);
		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		if (internalExamList != null && internalExamList.size() > 0) {
			for (Iterator iterator = internalExamList.iterator(); iterator
					.hasNext();) {
				ExamUpdateProcessTO examUpdateProcessTO = (ExamUpdateProcessTO) iterator
						.next();

				for (BatchClassTO bTo : listBatchClass) {
					Integer clsId = null;
					if (CommonUtil.checkForEmpty(bTo.getClassId())) {
						clsId = Integer.parseInt(bTo.getClassId());
					}
					// studentList = getStudentsForClass(clsId);
					studentList = getStudentsForClassWithPrevExam(clsId,
							isPreviousExam);

					ArrayList<ExamStudentInternalFinalMarkDetailsBO> listToInsert = new ArrayList<ExamStudentInternalFinalMarkDetailsBO>();
					for (Integer stuId : studentList) {
							subjectsList = getSubjectsForStudentWithPrevExam(stuId,isPreviousExam,bTo.getSemester());
							listToInsert.addAll(helper.set_details_Internal(clsId,stuId,getInternalMarksOfStudent(examUpdateProcessTO.getInternalExamId(),stuId,subjectsList),examUpdateProcessTO.getInternalExamTypeId(),examUpdateProcessTO.getInternalExamId()));

					}

					impl.insert_List_finalInternalMarks(listToInsert);

				}
			}
		}
		for (BatchClassTO bTo : listBatchClass) {
			Integer clsId = null;
			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}
			ArrayList<Integer> examList = new ArrayList<Integer>();
			ArrayList insert_List_finalInternalMarksOverall = new ArrayList();
			ArrayList insertList_assignmentMarks = new ArrayList();
			ArrayList insertList_attendanceMarks = new ArrayList();
			ExamStudentOverallInternalMarkDetailsBO bo = null;
			ExamStudentAssignmenteMarkDetailsBO assignBo = null;
			ExamStudentAttendanceMarkDetailsBO attendenceBo = null;
			for (ExamUpdateProcessTO to : internalExamList) {
				examList.add(to.getInternalExamId());
			}
			// studentList = getStudentsForClass(clsId);
			studentList = getStudentsForClassWithPrevExam(clsId, isPreviousExam);

			ExamUpdateProcessMapTO to = null;
			HashMap<Integer, ExamUpdateProcessMapTO> attendenceTheoryMap = null;
			HashMap<Integer, ExamUpdateProcessMapTO> attendencePracticalMap = null;
			int schemeNo =bTo.getSemester();
			for (Integer stuId : studentList) 
			{
				// subjectsList = getSubjectsForStudent(stuId);
					subjectsList = getSubjectsForStudentWithPrevExam(stuId,isPreviousExam,bTo.getSemester());
					to = new ExamUpdateProcessMapTO();
//					boolean isSchemeAssigned = false;
					Integer courseId = null;
					courseId = impl.getCourseOfStudent(stuId,false);
					if (subjectsList != null && subjectsList.size() > 0) 
					{
						for (Integer subId : subjectsList) 
						{
//							if(!isSchemeAssigned)
//							{
//								schemeNo = impl.getSchemeNo(clsId);
//								isSchemeAssigned = true;
//							}
							Integer attendanceTypeId = impl.getTheoryAttendenceTypeId(subId, "t", examId,courseId, schemeNo);
							attendenceTheoryMap = helper.getAttendenceListForStudent(impl.getSubjectTotalHoursHeld(attendanceTypeId,subId, stuId, examId, courseId, isPreviousExam, true,schemeNo), impl.getStudentAttendance(attendanceTypeId,stuId, subId, examId, courseId,isPreviousExam, true,schemeNo));
							attendanceTypeId = impl.getTheoryAttendenceTypeId(subId, "p", examId, courseId, schemeNo);
							attendencePracticalMap = helper.getAttendenceListForStudent(impl.getSubjectTotalHoursHeld(attendanceTypeId, subId, stuId, examId, courseId,isPreviousExam, false,schemeNo), impl.getStudentAttendance(attendanceTypeId,stuId, subId, examId, courseId,isPreviousExam, false,schemeNo));
							if (attendenceTheoryMap != null) 
							{
								to.setAttendenceTheoryMap(attendenceTheoryMap);
							}
							if (attendencePracticalMap != null) 
							{
								to.setAttendencePracticalMap(attendencePracticalMap);
							}
							bo = new ExamStudentOverallInternalMarkDetailsBO();
							assignBo = new ExamStudentAssignmenteMarkDetailsBO();
							attendenceBo = new ExamStudentAttendanceMarkDetailsBO();
							Double assignTotalTheoryMarks = 0.0;
							Double assignTotalPracticalMarks = 0.0;
							Integer attendenceTotalTheoryMarks = 0;
							Integer attendenceTotalPracticalMarks = 0;
							bo.setClassId(clsId);
							bo.setStudentId(stuId);
							bo.setExamId(examId);
							bo.setSubjectId(subId);
							Double subInternalPracticalMarks = 0.0;
							Double subInternalTheoryMarks = 0.0;
							StringBuffer subInternalPracticalPercentage = null;
							StringBuffer subInternalTheoryPercentage = null;
							if (subInternalPracticalCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								subInternalPracticalPercentage = new StringBuffer();
								subInternalPracticalMarks = impl.get_student_practical_marks(stuId, subId,examList, getPracticalLimit(subId,examId, courseId, schemeNo),subInternalPracticalPercentage, schemeNo);
								Integer checkMarks = impl.getSubInternalMarks(subInternalPracticalPercentage, impl.getCourseOfStudent(stuId,false));
								if (checkMarks != null) 
								{
									subInternalPracticalMarks = Double.valueOf(checkMarks);
								}
							}
							if (subInternalTheoryCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								subInternalTheoryPercentage = new StringBuffer();
								Integer thLimit = getTheoryLimit(subId, examId,courseId, schemeNo);
								if (thLimit == null) 
								{
									thLimit = 0;
								}
								subInternalTheoryMarks = impl.get_student_theory_marks(stuId, subId,examList, thLimit,subInternalTheoryPercentage, schemeNo);
								Integer checkMarks = impl.getSubInternalMarks(subInternalTheoryPercentage, impl.getCourseOfStudent(stuId,false));
								if (checkMarks != null) 
								{
									subInternalTheoryMarks = Double.valueOf(checkMarks);
								}	
							}
							if (assignmentTheoryCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								String theoryMarks = theoryTotalAssignmentMarks(stuId, subId);
								if (theoryMarks != null) 
								{
									bo.setTheoryTotalAssignmentMarks(theoryMarks);
									assignTotalTheoryMarks += Double.parseDouble(theoryMarks);
								}
							}	
							if (assignmentPracticalCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								String pracMarks = practicalTotalAssignmentMarks(stuId, subId);
								if (pracMarks != null) 
								{
									bo.setPracticalTotalAssignmentMarks(pracMarks);
									assignTotalPracticalMarks += Double.parseDouble(pracMarks);
								}
							}
							assignBo.setStudentId(stuId);
							assignBo.setClassId(clsId);
							attendenceBo.setStudentId(stuId);
							attendenceBo.setClassId(clsId);
							attendenceBo.setAttendanceTypeId(attendanceTypeId);
							Integer maxMarks = getTotalMinMaxEnteredIntPracticalMarks(subId, examId, courseId, schemeNo);
							Integer minMarks = getTotalMinMaxEnteredIntTheoryMarks(subId, examId, courseId, schemeNo);
							assignBo.setAssignmentMinMark(((maxMarks != null) ? maxMarks: 0)+ ((minMarks != null) ? minMarks : 0));
							Double studentAssignmentMarks = assignTotalPracticalMarks+ assignTotalTheoryMarks;
							if (studentAssignmentMarks != null) 
							{
								assignBo.setStudentassignmentMark(Integer.parseInt(Integer.toString(studentAssignmentMarks.intValue())));
							}
							if (attendancePracticalCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								Integer pracMarks = practicalTotalAttendenceMarks(to, subId, examId, courseId, schemeNo);
								if (pracMarks != null) 
								{
									bo.setPracticalTotalAttendenceMarks(pracMarks.toString());
									attendenceTotalPracticalMarks += pracMarks;
								}
							}
							if (attendanceTheoryCheckSubjRuleSettings(subId,examId, courseId, schemeNo)) 
							{
								Integer theoryMarks = theoryTotalAttendenceMarks(to, subId, examId, courseId, schemeNo);
								if (theoryMarks != null) 
								{
									bo.setTheoryTotalAttendenceMarks(theoryMarks.toString());
									attendenceTotalTheoryMarks += theoryMarks;
								}
							}
							attendenceBo.setStudentAttendanceMark(attendenceTotalTheoryMarks+ attendenceTotalPracticalMarks); // Integer
							if (subInternalTheoryMarks != null) 
							{
								bo.setTheoryTotalSubInternalMarks(subInternalTheoryMarks.toString());
								if ((subInternalTheoryMarks+ attendenceTotalTheoryMarks + assignTotalTheoryMarks) < getTotalMinMaxEnteredIntTheoryMarks(subId, examId, courseId, schemeNo)) 
								{
									bo.setPassOrFail("fail");
								}
								else 
								{
									bo.setPassOrFail("pass");
								}
							}
							if (subInternalPracticalMarks != null) 
							{
								bo.setPracticalTotalSubInternalMarks(subInternalPracticalMarks.toString());
								if ((subInternalPracticalMarks+ attendenceTotalPracticalMarks + assignTotalPracticalMarks) < getTotalMinMaxEnteredIntPracticalMarks(subId, examId, courseId, schemeNo)) 
								{
									bo.setPassOrFail("fail");
								}
								else 
								{
									bo.setPassOrFail("pass");
								}
							}
							helper.convertInternalSettings(impl.getInternalSettings(subId, examId, courseId, schemeNo),bo);
							insert_List_finalInternalMarksOverall.add(bo);
							insertList_assignmentMarks.add(assignBo);
							insertList_attendanceMarks.add(attendenceBo);
						}	
					}
				
			}
			impl.insert_List_finalInternalMarksOverall(insert_List_finalInternalMarksOverall);
			impl.insertList_assignmentMarks(insertList_assignmentMarks);
			impl.insertList_attendanceMarks(insertList_attendanceMarks);

		}

	}

	// ==========================================================================
	// =========================================================================
	// ================ PROCESS - SUPPLEMENTARY DATA CREATION ==============

	// To get the classes & joining batch for a particular exam
	public ArrayList<BatchClassTO> getClassJoiningBatch(Integer examId) {

		return helper.convertClassJoiningBatch(impl
				.getClassJoiningBatch(examId));

	}

	// To fetch the students for supplementary data creation process (B.R 2)
	public ArrayList<Integer> getStudentList(Integer classId,
			Integer joiningBatch, String isPreviousExam) {

		return impl.getStudentList(classId, joiningBatch, isPreviousExam);

	}

	// To get subjects for a given class
	public ArrayList<Integer> get_subjects_forClass(Integer classId) {

		return impl.get_subjects_forClass(classId);

	}

	// To get the chance of the student for a particular subject
	public List<Object[]> getChanceOfStudent(Integer stuId,
			ArrayList<Integer> subjectsList) {

		return impl.getChanceOfStudent(stuId, subjectsList);

	}

	// To get max allowed failed subjects for special supplementary exam
	public Integer getMaxAllowedFailedSubject(Integer examId) {

		return impl.getMaxAllowedFailedSubject(examId);

	}

	// To check whether the exam type is special supplementary
	public boolean getExamType(Integer examId) {

		return impl.getExamType(examId);

	}

	// To get the Aggregate Pass Percentage from Exam Course Settings
	public BigDecimal getAggregatePassPercentage(Integer stuId) {

		Integer courseId = impl.getCourseOfStudent(stuId,false);

		return impl.getAggregatePassPercentage(courseId);

	}

	// To get the pass/fail status of theory/practical of a subject
	public List<Object[]> getFailStatusTheoryPrac(
			ArrayList<Integer> subjectIds, Integer studentId) {

		return impl.getFailStatusTheoryPrac(subjectIds, studentId);

	}
	
	
	/**
	 * 
	 * @param listBatchClass
	 * @param examId
	 * @param isPreviousExam
	 */
	private void process_SupplementaryDataCreationNew(ArrayList<BatchClassTO> listBatchClass, Integer examId, String isPreviousExam) throws Exception {
		
		for (BatchClassTO bTo : listBatchClass) {
			Classes classes = new Classes();

			Integer clsId = null;

			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}
			
			impl.deleleAlreadyExistedRecords(clsId, examId);
			
			List<Object[]> courseScheme = new ExamUpdateProcessHandler().get_course_schemeNo_forClass(clsId);
			Integer schemeNo = 0, courseId = 0;
			if (courseScheme != null && courseScheme.size() > 0) {
				Object[] courseId_SchemeNo = courseScheme.get(0);
				courseId = (Integer) courseId_SchemeNo[0];
				schemeNo = (Integer) courseId_SchemeNo[1];

			}
			ArrayList<Integer> studentList = getStudentList(clsId, split(bTo.getJoiningBatch()), isPreviousExam);
			Integer academicYear = split(bTo.getJoiningBatch());
			Map<Integer, Integer> supStudentMap = new HashMap<Integer, Integer>();
			List<Integer> excludedList =  impl.getExcludedFromResultSubjects(courseId, schemeNo, academicYear);
			for (Integer stuId : studentList) {
				//int courseId = impl.getCourseOfStudent(stuId);
				ArrayList<Integer> subjectsList = getSubjectsForStudentWithPrevScheme(stuId, isPreviousExam, schemeNo);
				if(subjectsList!=null && !subjectsList.isEmpty()){
				HashMap<Integer, Integer> chanceList = helper.convertListToHashMap(impl.getChanceOfStudent(stuId, subjectsList));
				for (Integer subId : subjectsList) {
					int studentRecCount = impl.getStudentRecCount(stuId, subId);
					List<Object[]> marksList =  getStudentSuppMarkDetails(stuId, subId, courseId, academicYear, studentRecCount, schemeNo);
					List<StudentMarkDetailsTO> markTOList = helper.getSupMarkDetailsList(marksList);
					ArrayList supBoList = helper.createSupplimentaryBO(markTOList, chanceList, stuId, clsId, academicYear,  examId, schemeNo);
					if(supBoList!= null && supBoList.size() > 0){
						supStudentMap.put(stuId, stuId);
					}
					if(supBoList!= null && supBoList.size() > 0){
						impl.insert_List(supBoList);
					}
				}
				}
			}
			for (Integer stuId : studentList) {
				
				ArrayList improvementList = new ArrayList<ExamSupplementaryImprovementApplicationBO>();
				if(supStudentMap.containsKey(stuId)){
					continue;
				}
				//int courseId = impl.getCourseOfStudent(stuId);
				String hihestOrLatest = impl.getHighestorLatest(courseId);
				boolean isHeighest = true;
				if(hihestOrLatest.equalsIgnoreCase("Latest Marks")){
					isHeighest = false;
				}
				BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(courseId);
				ArrayList<Integer> subjectsList = getSubjectsForStudentWithPrevScheme(stuId, isPreviousExam, schemeNo);
				if(subjectsList!=null && !subjectsList.isEmpty()){
				
				ArrayList<Integer> newSubjectsList = new ArrayList<Integer>();
				Iterator<Integer> itr = subjectsList.iterator();
				while (itr.hasNext()) {
					Integer subjectId = (Integer) itr.next();
					if(!excludedList.contains(subjectId)){
						newSubjectsList.add(subjectId);
					}
					
				}
				HashMap<Integer, Integer> chanceList = helper.convertListToHashMap(impl.getChanceOfStudent(stuId, newSubjectsList));
				Integer studentFinalMarks = helper.getStudentTotalMarkForImprovement(clsId, academicYear, stuId, courseId, newSubjectsList, isHeighest);
				Double subjectTotalMarks = impl.getAggregateOfSubjects(newSubjectsList, academicYear, courseId, schemeNo);
				for (Integer subId : newSubjectsList) {
					if (studentFinalMarks != null && subjectTotalMarks != null && studentFinalMarks > 0 && subjectTotalMarks > 0) {
						Double studentPercentage = Double.valueOf((studentFinalMarks * 100) / subjectTotalMarks);
						if (requiredAggrePercentage != null	&& studentPercentage != null) {
							if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
								ExamSupplementaryImprovementApplicationBO examSupplementaryImprovementApplicationBO = new ExamSupplementaryImprovementApplicationBO();
								if (chanceList.containsKey(subId)) {
									Integer chanceValue = chanceList.get(subId);
									if (chanceValue != null) {
										chanceValue = chanceValue + 1;
										examSupplementaryImprovementApplicationBO.setChance(chanceValue);
									}
								} else {
									examSupplementaryImprovementApplicationBO.setChance(1);
								}
								examSupplementaryImprovementApplicationBO.setStudentId(stuId);
								examSupplementaryImprovementApplicationBO.setExamId(examId);
								examSupplementaryImprovementApplicationBO.setSubjectId(subId);
								examSupplementaryImprovementApplicationBO.setIsImprovement(1);
								examSupplementaryImprovementApplicationBO.setSchemeNo(schemeNo);
								classes.setId(clsId);
								examSupplementaryImprovementApplicationBO.setClasses(classes);
								improvementList.add(examSupplementaryImprovementApplicationBO);
								
							}
						}
					}
				}
				if(improvementList!= null && improvementList.size() > 0){
					impl.insert_List(improvementList);
				}
				
				
			}
		}
		}
		
	}

/*

	// To calculate & add Supplementary Data Creation
	@SuppressWarnings("unchecked")
	private void process_SupplementaryDataCreation(
			ArrayList<BatchClassTO> listBatchClass, Integer examId) {

		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		ArrayList<Integer> examList = null;
		Integer studentRecCount = null;
		Integer courseId = null;
		//Integer latestExamId = null;
		String hihestOrLatest = impl.getHighestorLatest(courseId);

		for (BatchClassTO bTo : listBatchClass) {

			Integer clsId = null;

			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}

			studentList = getStudentList(clsId, split(bTo.getJoiningBatch()));
			Integer academicYear = split(bTo.getJoiningBatch());
			for (Integer stuId : studentList) {
				courseId = impl.getCourseOfStudent(stuId);
				//latestExamId = impl.getStudLatestExam(stuId);
				ArrayList finalListSupplData = new ArrayList();
				subjectsList = impl.getSubjectsForStudentForSupp(stuId);
				HashMap<Integer, Integer> chanceList = helper.convertListToHashMap(impl.getChanceOfStudent(stuId, subjectsList));
				Integer subjectTotalMarks = impl.getAggregateOfSubjects(subjectsList, academicYear, courseId);
				if (subjectsList != null && subjectsList.size() > 0) {
					for (Integer subId : subjectsList) {
						String subjectType = getSubjectsTypeBySubjectId(subId);
						finalListSupplData = new ArrayList();

						// Get the student record count from the regular overall
						// table

						studentRecCount = impl.getStudentRecCount(stuId, subId);

						if (studentRecCount != null && studentRecCount == 1) {

							// when he has only 1 record
							ArrayList<StudentMarkDetailsTO> studentSuppDetails = helper.convertBOToTOStuMarkSuppDetails(
											getStudentSuppMarkDetails(stuId, subId, courseId, academicYear), subjectType);

							finalListSupplData = helper.setSuppDetails(
									studentSuppDetails, chanceList, stuId,
									clsId, academicYear, subjectTotalMarks,
									examId);

							impl.insert_List(finalListSupplData);

						} else if (studentRecCount > 1){

							// when he has more than 1 record
							
							
							
							if(hihestOrLatest == null){
								continue;
							}
							if(hihestOrLatest.equalsIgnoreCase("Latest Marks")){
								ArrayList<StudentMarkDetailsTO> studentSuppDetails = helper
										.convertBOToTOStuMarkSuppDetails(impl.get_student_supp_mark_details_multiRec(
																stuId, subId,courseId,academicYear), subjectType);
								
								finalListSupplData = helper.setSuppDetails(	studentSuppDetails, chanceList, stuId,
										clsId, academicYear, subjectTotalMarks,	examId);
								impl.insert_List(finalListSupplData);
							}
							else{
								
								//sup
								ArrayList<StudentMarkDetailsTO> studentSuppDetails = helper.convertBOToTOStuMarkSuppDetails(
												impl.get_student_supp_mark_details_multiRec(stuId, subId,
														courseId,academicYear),	subjectType);
								
								finalListSupplData = helper.setSuppDetailsforHighest(
										studentSuppDetails, chanceList, stuId,
										clsId, academicYear, subjectTotalMarks,
										examId);
								
								Iterator<ExamSupplementaryImprovementApplicationBO> itr = finalListSupplData.iterator();
								Map<Integer, Integer> subMap = new HashMap<Integer, Integer>();
								while (itr.hasNext()) {
									ExamSupplementaryImprovementApplicationBO examSupplementaryImprovementApplicationBO = (ExamSupplementaryImprovementApplicationBO) itr
											.next();
									subMap.put(examSupplementaryImprovementApplicationBO.getSubjectId(), examSupplementaryImprovementApplicationBO.getSubjectId());
								}
		
								impl.insert_List(finalListSupplData);
								
								
								studentSuppDetails = helper.convertBOToTOStuMarkSuppDetails(impl
										.get_student_supp_mark_details_highest_marks(stuId, subId, courseId,academicYear),	subjectType);
				
								finalListSupplData = helper.setSuppDetailsForHighestImprovement(
													studentSuppDetails, chanceList, stuId,	clsId, academicYear, subjectTotalMarks,
													examId, subMap);
								impl.insert_List(finalListSupplData);
							}
							
							
							
						}
					}
				}
			}
		}
	}*/

	private List<Object[]> getSubjectRuleDetails(Integer courseId,
			Integer subId, Integer academicYear) {

		return impl.getSubjectRuleDetails(courseId, subId, academicYear);
	} 

	public List<Object[]> getStudentSuppMarkDetails(Integer stuId,	Integer subId, Integer courseId,
													Integer academicYear, int studentRecCount, int schemeNo) {

		return impl.get_student_supp_mark_details(stuId, subId, courseId, academicYear, studentRecCount, schemeNo);
	}

	private int split(String joiningBatch) {

		String joiningBatchInt = "";
		if (joiningBatch != null && joiningBatch.trim().length() > 0) {
			joiningBatchInt = joiningBatch.substring(0, 4);
			return Integer.parseInt(joiningBatchInt);
		} else {
			return 0;
		}

	}

	private boolean getMaxFailElgibleOrNot(Integer stuId,
			ArrayList<Integer> subjectsList, Integer examId,
			Integer academicYear, Integer courseId) {

		Integer maxAllowedFailForExam = impl.getMaxAllowedFailedSubject(examId);
		Integer studentTotalMarks = 0;
		boolean theoryCheck = true;
		boolean practicalCheck = true;
		boolean internalCheck = true;
		boolean flag = true;
		boolean theoryFail = true;
		boolean practicalFail = true;
		boolean internalFail = true;
		Integer count = 0;

		for (Integer subId : subjectsList) {

			theoryCheck = impl.getFinalTheoryCheck(subId, academicYear,
					courseId);

			practicalCheck = impl.getFinalPracticalCheck(subId, academicYear,
					courseId);

			internalCheck = impl.getFinalInternalCheck(subId, academicYear,
					courseId);

			if (theoryCheck) {

				theoryFail = helper.convertRegularTheoryMarks(impl
						.getStudentsRegularTheoryMarksForSubj(subId, stuId));
				if (theoryFail)
					count++;

			}
			if (!theoryFail && practicalCheck) {

				practicalFail = helper.convertRegularTheoryMarks(impl
						.getStudentsRegularTheoryMarksForSubj(subId, stuId));
				if (practicalFail)
					count++;
			}
			if (internalCheck) {

				internalFail = impl.getInternalFailStatus(subId, stuId);
				if (internalFail)
					count++;
			}

		}

		if (maxAllowedFailForExam != null) {
			if (count > maxAllowedFailForExam) {

				return true;
			}
		}

		return false;
	}

	private void addAllSubjectsToSupplementaryDataCreation(
			ArrayList<Integer> subjectsList, Integer examId, Integer clsId,
			Integer stuId) {
		ExamSupplementaryImprovementApplicationBO supplyBo = null;

		if (subjectsList != null) {
			for (Integer subId : subjectsList) {
				supplyBo = new ExamSupplementaryImprovementApplicationBO();
				supplyBo.setExamId(examId);
				supplyBo.setSubjectId(subId);
				supplyBo.setStudentId(stuId);

				impl.insert(supplyBo);
			}
		}

	}
	/*
	private boolean calculateTotalAggregateSatisfied(Integer examId,
			Integer clsId, ArrayList<Integer> subjectsList, Integer stuId,
			Integer academicYear) {

		Integer schemeNo = 0, courseId = 0;
		List<Object[]> courseScheme = get_course_schemeNo_forClass(clsId);
		if (courseScheme != null && courseScheme.size() > 0) {
			Object[] courseId_SchemeNo = courseScheme.get(0);
			courseId = (Integer) courseId_SchemeNo[0];
			schemeNo = (Integer) courseId_SchemeNo[1];
		}
		Integer subjectTotalMarks = impl.getAggregateOfSubjects(subjectsList,
				academicYear, courseId);

		BigDecimal requiredAggrePercentage = impl
				.getAggregatePassPercentage(courseId);
		Integer studentTotalMarks = 0;
		boolean theoryCheck = true;
		boolean practicalCheck = true;
		boolean internalCheck = true;
		Integer studentFinalMarks = 0;
		Integer marks = 0;
		if (subjectsList != null) {
			for (Integer subId : subjectsList) {

				theoryCheck = impl.getFinalTheoryCheck(subId, academicYear,
						courseId);

				practicalCheck = impl.getFinalPracticalCheck(subId,
						academicYear, courseId);

				internalCheck = impl.getFinalInternalCheck(subId, academicYear,
						courseId);

				if (theoryCheck) {

					Integer theoryMarks = impl.getStudentsTheoryMarkForSubj(
							subId, stuId);
					if (theoryMarks != null) {
						marks = marks + theoryMarks;
					}
				}
				if (practicalCheck) {
					Integer practicalMarks = impl
							.getStudentsPracticalMarkForSubj(subId, stuId);
					if (practicalMarks != null) {
						marks = marks + practicalMarks;

					}
				}
				if (internalCheck) {
					marks = marks
							+ helper.convertIntenalMarks(impl
									.getStudentsInternalMarkForSubj(subId,
											stuId));

				}
				studentFinalMarks = studentFinalMarks + marks;
				marks = 0;

			}
		}
		if (studentFinalMarks != null && subjectTotalMarks != null) {
			Double studentPercentage = new Double((studentFinalMarks * 100)
					/ subjectTotalMarks);

			if (requiredAggrePercentage != null && studentPercentage != null) {
				if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(
						requiredAggrePercentage.toString()).intValue())) {

					return true;
				}
			}
		}
		return false;
	}
	 */
	private ArrayList<Integer> getSubjectsForStudentWithPrevScheme(Integer stuId,
			String isPreviousExam, int schemeNo) {

		return impl.getSubjectsForStudentWithPreviousScheme(stuId, isPreviousExam, schemeNo);
	}	
	
	
	private void process_PassOrFail(
			ArrayList<BatchClassTO> listBatchClass, Integer examId,
			String isPreviousExam, ActionErrors err) throws Exception {

		IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		ExamStudentPassFail examStudentPassFail;
		Student student;
		Classes classes;
		List<ExamStudentPassFail> examStudentPassFailList = new ArrayList<ExamStudentPassFail>();
		for (BatchClassTO bTo : listBatchClass) {
			Integer clsId = null;

			if (CommonUtil.checkForEmpty(bTo.getClassId())) {
				clsId = Integer.parseInt(bTo.getClassId());
			}
			List<Object[]> courseScheme = new ExamUpdateProcessHandler().get_course_schemeNo_forClass(clsId);
			Integer schemeNo = 0, courseId = 0;
			if (courseScheme != null && courseScheme.size() > 0) {
				Object[] courseId_SchemeNo = courseScheme.get(0);
				courseId = (Integer) courseId_SchemeNo[0];
				schemeNo = (Integer) courseId_SchemeNo[1];

				
			}
			//impl.deleleAlreadyExistedRecordsForPassFail(clsId, schemeNo);
			Integer academicYear = split(bTo.getJoiningBatch());
			List<Integer> excludedList =  impl.getExcludedFromResultSubjects(courseId, schemeNo, academicYear);
			studentList = getStudentsForClassWithPrevExam(clsId, isPreviousExam);
			BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(courseId);
			List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(courseId, schemeNo, academicYear);

			
			for (Integer stuId : studentList) {
				ArrayList<StudentMarkDetailsTO> stuMarkDetails = new ArrayList<StudentMarkDetailsTO>();
				stuMarkDetails.clear();
				subjectsList = getSubjectsForStudentWithPrevScheme(stuId, isPreviousExam, schemeNo); 
				
				ArrayList<Integer> certSubjectsList = new ArrayList<Integer>();
				Iterator<Integer> subItr = subjectsList.iterator();
				while (subItr.hasNext()) {
					Integer subjectId = (Integer) subItr.next();
					if(excludedList.contains(subjectId)){
						certSubjectsList.add(subjectId);
					}
					
				}
				Integer studentFinalMarks = helper.getStudentTotalMarkForImprovement(clsId, academicYear, stuId, courseId, subjectsList, true);
				Double subjectTotalMarks = impl.getAggregateOfSubjects(subjectsList, academicYear, courseId, schemeNo);
				if(subjectTotalMarks == null || subjectTotalMarks <= 0){
					err.add(CMSConstants.ERRORS, new ActionError("knowledgepro.exam.update.process.subject.definition.not.found", bTo.getClassName()));
					throw new DataNotFoundException();	
				}
				Integer certCourseMarks = 0;
				Double certCourseSubTotalMarks = 0.0;
				if(certSubjectsList.size() > 0){
					certCourseMarks = helper.getStudentTotalMarkForImprovement(clsId, academicYear, stuId, courseId, certSubjectsList, true);
					certCourseSubTotalMarks = impl.getAggregateOfSubjects(certSubjectsList, academicYear, courseId, schemeNo);
				}
				
				boolean failed = false;
				
				Iterator<Integer> itr1 = subjectsList.iterator();
				while (itr1.hasNext()) {
					Integer subjectId = (Integer) itr1.next();
					List<Object[]> marksList = impl.getMarkDetails(stuId, subjectId, courseId, academicYear, schemeNo);
					List<StudentMarkDetailsTO> markTOList = helper.getSupMarkDetailsList(marksList);
					if(markTOList!= null && markTOList.size() > 0){
						failed = true;
						if(failureExcludeList!= null && failureExcludeList.size() > 0){
							if(failureExcludeList.contains(subjectId)){
								failed = false;
							}
						}
						if(failed){
							break;
						}
					}
					
				}

				Double studentPercentage = 0.0;
				if (studentFinalMarks != null && subjectTotalMarks != null && studentFinalMarks > 0 && subjectTotalMarks > 0) {
					//studentPercentage = new Double((studentFinalMarks * 100) / subjectTotalMarks);
					if(certCourseMarks!= null && certCourseSubTotalMarks!= null  && certCourseSubTotalMarks > 0){
						studentPercentage =  Double.valueOf(((studentFinalMarks - certCourseMarks) * 100) / (subjectTotalMarks - certCourseSubTotalMarks));
					}
					else{
						studentPercentage = Double.valueOf((studentFinalMarks * 100) / subjectTotalMarks);	
					}
					if (requiredAggrePercentage != null	&& studentPercentage != null) {
						if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
							failed = true;	
						}
					}
				}
				/*if(certCourseMarks!= null && certCourseSubTotalMarks!= null  && certCourseSubTotalMarks > 0){
					studentPercentage =  new Double(((studentFinalMarks - certCourseMarks) * 100) / (subjectTotalMarks - certCourseSubTotalMarks));
				}*/
				
				student = new Student();
				examStudentPassFail = new ExamStudentPassFail();
				classes = new Classes();
				student.setId(stuId);
				examStudentPassFail.setStudent(student);
				classes.setId(clsId);
				examStudentPassFail.setClasses(classes);
				examStudentPassFail.setSchemeNo(schemeNo);
				if(studentPercentage!= null){
					float stuPercentage = CommonUtil.roundToDecimal((studentPercentage.floatValue()), 2);
					examStudentPassFail.setPercentage(new BigDecimal(stuPercentage));
					String result = iUpdateStudentSGPATxn.getResultClass(courseId, Double.valueOf(stuPercentage),academicYear,stuId);
					examStudentPassFail.setResult(result);
				}

				if(failed){
					examStudentPassFail.setResult("Failed");
				}
				
				if(failed){
					examStudentPassFail.setPassFail('F');
				}
				else{
					examStudentPassFail.setPassFail('P');	
				}
				examStudentPassFailList.add(examStudentPassFail);
			}
		}
		impl.updatePassFail(examStudentPassFailList);
	}
	
	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @param academicYear
	 * @param isPreviousExam
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassorFail(int courseId, int schemeNo, Integer academicYear, String isPreviousExam) throws Exception {
		IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();

		
		ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();
		ArrayList<Integer> studentList = null;
		ArrayList<Integer> subjectsList = null;
		ExamStudentPassFail examStudentPassFail;
		Student student;
		Classes classes;
		List<ExamStudentPassFail> examStudentPassFailList = new ArrayList<ExamStudentPassFail>();
		ArrayList<Integer> classIdList = iUpdateStudentSGPATxn.getClassIdsByCourseAndScheme(courseId, schemeNo, academicYear);
		BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(courseId);
		List<Integer> excludedList =  impl.getExcludedFromResultSubjects(courseId, schemeNo, academicYear);
		List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(courseId, schemeNo, academicYear);
		
		Iterator<Integer> itr = classIdList.iterator();
		while (itr.hasNext()) {
			Integer clsId = (Integer) itr.next();

			//impl.deleleAlreadyExistedRecordsForPassFail(clsId, schemeNo);
			
			studentList = getStudentsForClassWithPrevExam(clsId, isPreviousExam);
			
			for (Integer stuId : studentList) {
				
				ArrayList<StudentMarkDetailsTO> stuMarkDetails = new ArrayList<StudentMarkDetailsTO>();
				stuMarkDetails.clear();
				subjectsList = getSubjectsForStudentWithPrevScheme(stuId, isPreviousExam, schemeNo); 
				if(subjectsList!=null && !subjectsList.isEmpty()){
				ArrayList<Integer> certSubjectsList = new ArrayList<Integer>();
				Iterator<Integer> subItr = subjectsList.iterator();
				while (subItr.hasNext()) {
					Integer subjectId = (Integer) subItr.next();
					if(excludedList.contains(subjectId)){
						certSubjectsList.add(subjectId);
					}
					
				}
				
				Integer studentFinalMarks = helper.getStudentTotalMarkForImprovement(clsId, academicYear, stuId, courseId, subjectsList, true);
				Double subjectTotalMarks = impl.getAggregateOfSubjects(subjectsList, academicYear, courseId, schemeNo);
				Integer certCourseMarks = 0;
				Double certCourseSubTotalMarks = 0.0;
				if(certSubjectsList.size() > 0){
					certCourseMarks = helper.getStudentTotalMarkForImprovement(clsId, academicYear, stuId, courseId, certSubjectsList, true);
					certCourseSubTotalMarks = impl.getAggregateOfSubjects(certSubjectsList, academicYear, courseId, schemeNo);
				}
				boolean failed = false;
				
				Iterator<Integer> itr1 = subjectsList.iterator();
				while (itr1.hasNext()) {
					Integer subjectId = (Integer) itr1.next();
					List<Object[]> marksList = impl.getMarkDetails(stuId, subjectId, courseId, academicYear, schemeNo);
					List<StudentMarkDetailsTO> markTOList = helper.getSupMarkDetailsList(marksList);
					if(markTOList!= null && markTOList.size() > 0){
						failed = true;
						if(failureExcludeList!= null && failureExcludeList.size() > 0){
							if(failureExcludeList.contains(subjectId)){
								failed = false;
							}
						}
						if(failed){
							break;
						}
					}
					
				}
				Double studentPercentage = 0.0;
				if (studentFinalMarks != null && subjectTotalMarks != null && studentFinalMarks > 0 && subjectTotalMarks > 0) {
					//studentPercentage = new Double((studentFinalMarks * 100) / subjectTotalMarks);
					if(certCourseMarks!= null && certCourseSubTotalMarks!= null && certCourseSubTotalMarks > 0 ){
						studentPercentage =  Double.valueOf(((studentFinalMarks - certCourseMarks) * 100) / (subjectTotalMarks - certCourseSubTotalMarks));
					}else{
						studentPercentage = Double.valueOf((studentFinalMarks * 100) / subjectTotalMarks);
					}
					
					if (requiredAggrePercentage != null	&& studentPercentage != null) {
						if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
							failed = true;	
						}
					}
				}
				/*if(certCourseMarks!= null && certCourseSubTotalMarks!= null && certCourseSubTotalMarks > 0 ){
					studentPercentage =  new Double(((studentFinalMarks - certCourseMarks) * 100) / (subjectTotalMarks - certCourseSubTotalMarks));
				}*/
				
				student = new Student();
				examStudentPassFail = new ExamStudentPassFail();
				classes = new Classes();
				student.setId(stuId);
				examStudentPassFail.setStudent(student);
				classes.setId(clsId);
				examStudentPassFail.setClasses(classes);
				examStudentPassFail.setSchemeNo(schemeNo);
				if(failed){
					examStudentPassFail.setPassFail('F');
				}
				else{
					examStudentPassFail.setPassFail('P');	
				}
				if(studentPercentage!= null){
					float stuPercentage = CommonUtil.roundToDecimal((studentPercentage.floatValue()), 2);
					examStudentPassFail.setPercentage(new BigDecimal(stuPercentage));
					String result = iUpdateStudentSGPATxn.getResultClass(courseId, Double.valueOf(stuPercentage),academicYear,stuId);
					examStudentPassFail.setResult(result);
				}
				if(failed){
					examStudentPassFail.setResult("Failed");
				}

				examStudentPassFailList.add(examStudentPassFail);
			}
			}
		}
		return impl.updatePassFail(examStudentPassFailList);
    }

	public boolean updatePassorFailForCjc(int courseId, int academicYear,int clsId)throws Exception 
	{
		ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();
		ArrayList<Integer> studentList = null;
		List<Subject>part1SubjectList=null;
		List<Subject>part2SubjectList=null;
		ExamStudentPassFail examStudentPassFail;
		Student student;
		Classes classes;
		List<ExamStudentPassFail> examStudentPassFailList = new ArrayList<ExamStudentPassFail>();
		classes=impl.getClasess(clsId);
		Integer schemeNo=classes.getTermNumber();
		impl.deleleAlreadyExistedRecordsForPassFail(clsId, schemeNo);
		studentList = getStudentsForClassWithPrevExam(clsId, "false");
		for (Integer stuId : studentList) 
		{
			ExamStudentPassFail passFail=new ExamStudentPassFail();
			
			student=new Student();
			student.setId(stuId);
			passFail.setStudent(student);
			
			classes=new Classes();
			classes.setId(clsId);
			passFail.setClasses(classes);
			passFail.setSchemeNo(schemeNo);
			List<Object[]>passDetails=impl.getPassOrFail(stuId);
			if(passDetails.size()!=0)
			{	
				
				Iterator<Object[]>passItr=passDetails.iterator();
				Double totalObtain=0.0;
				Double minTotal=0.0;
				Double maxTotal=0.0;
				Double percentage=0.0;
				Double pof=0.0;
				while(passItr.hasNext())
				{
					Object[]obj=passItr.next();
					totalObtain+=Double.parseDouble(obj[0].toString());
					minTotal+=Double.parseDouble(obj[1].toString());
					maxTotal+=Double.parseDouble(obj[2].toString());
					percentage+=Double.parseDouble(obj[3].toString());
					pof+=Double.parseDouble(obj[4].toString());
					
				}
				percentage=(totalObtain/maxTotal*100);
				passFail.setPercentage(new BigDecimal(percentage));
				if(pof==0)
					passFail.setPassFail('P');
				else
					passFail.setPassFail('F');
				
				examStudentPassFailList.add(passFail);
			}
		}	
		return impl.updatePassFail(examStudentPassFailList);
			
	}

	private SubjectRuleSettings getMarksToPass(int subjectId, int courseId) 
	{
		return impl.getMarksToPass(subjectId,courseId);
	}

	private void getMarksObtainedByStudent(Integer stuId, int subjectId,String subjectType,String theoryMarks,String practicalMarks, List<Integer> examIdList, String examType) throws Exception
	{
		impl.getMarksObtainedByStudent(stuId,subjectId,subjectType,theoryMarks,practicalMarks,examIdList,examType);
	}

	private List<Subject> getSubjectsForStudent(Integer stuId,String subjectPart) throws Exception
	{
		return impl.getSubjectsForStudent(stuId,subjectPart);
	}

	public void validatePromotionProcess(int processId,
			ArrayList<BatchClassTO> listBatchClass, Integer examId,
			Integer academicYear, boolean checkPromotionCriteria,
			boolean checkExamFeePaid, String isPreviousExam, ActionErrors errors) throws Exception {
		if (processId==5) {
			List<String> classNames=impl.checkPromotionProcessNewClass(listBatchClass);
			if(classNames!=null && !classNames.isEmpty()){
				String classes ="";
				Iterator<String> itr = classNames.iterator();
				while (itr.hasNext()) {
					String s = (String) itr.next();
					if(!s.isEmpty()){
						classes = classes+","+s;
					}else{
						classes = s;
					}
				}
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.updateprocess.checkclass",classes));
			}
		}
		
	}
}
