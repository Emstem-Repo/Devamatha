package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamMarksEntryForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamMarksEntryHelper;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.exam.ExamMarksEntryStudentTO;
import com.kp.cms.to.exam.ExamMarksEntryTO;
import com.kp.cms.transactionsimpl.exam.ExamMarksEntryImpl;
import com.kp.cms.transactionsimpl.exam.ExamSecuredMarksEntryImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamMarksEntryHandler extends ExamGenHandler {

	ExamMarksEntryHelper helper = new ExamMarksEntryHelper();
	ExamMarksEntryImpl impl = new ExamMarksEntryImpl();

	private static volatile ExamMarksEntryHandler examMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(ExamMarksEntryHandler.class);
	/**
	 * return singleton object of examMarksEntryHandler.
	 * @return
	 */
	public static ExamMarksEntryHandler getInstance() {
		if (examMarksEntryHandler == null) {
			examMarksEntryHandler = new ExamMarksEntryHandler();
		}
		return examMarksEntryHandler;
	}
	// To FETCH SS - AS
	public ExamMarksEntryTO getAllSubjFor_OneStudent(int courseId,
			String rollNo, String regNo, int examId, int schemeNo,
			Integer ansScriptId, Integer evaluatorId, String examType)
			throws BusinessException, Exception {
		boolean rollNoPresent = false;
		boolean regNoPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNoPresent = true;
		}
		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent");
			}
		}

		ArrayList<Integer> listSubjectIds = new ArrayList<Integer>();
		List listMarksDetails;
		StudentUtilBO sUBO;
		String classCode = "";
		int currentScheme = 0;
		if (rollNoPresent) {

			currentScheme = impl.getCurrentSchemeByRollNoRegNo(rollNo, true);
			// if current scheme is selected, then use applsubgrp table else
			// history table
			if (currentScheme == schemeNo) {
				if (validateCourseScheme(courseId, schemeNo, rollNo, true,
						examId)) {
					Integer admAppId = impl.getadmIdByRollNo(rollNo);
					ArrayList<Integer> subjGrpList = impl
							.getSubGrpList(admAppId);
					listSubjectIds = impl
							.getSubjectIdListForStudent(subjGrpList);
				} else {
					throw new BusinessException("rollNo");
				}
			} else {
				listSubjectIds = impl.getSubGrpHistory(rollNo, schemeNo, true);
			}
			if (listSubjectIds == null) {
				listSubjectIds = new ArrayList<Integer>();
			}
			classCode = impl.getClassCodeforRegRoll(rollNo, regNo, true);
			sUBO = impl.select_student(rollNo, regNo, true);
			listMarksDetails = impl.selectMarksDetail_AllSubForOneStudent(sUBO
					.getId(), listSubjectIds, examId, ansScriptId, evaluatorId,
					examType);

		} else {

			currentScheme = impl.getCurrentSchemeByRollNoRegNo(regNo, false);

			if (currentScheme == schemeNo) {
				if (validateCourseScheme(courseId, schemeNo, regNo, false,
						examId)) {
					Integer admAppId = impl.getadmIdByRegNo(regNo);
					ArrayList<Integer> subjGrpList = impl
							.getSubGrpList(admAppId);
					listSubjectIds = impl
							.getSubjectIdListForStudent(subjGrpList);
				} else {
					throw new BusinessException("regNo");
				}
			} else {
				listSubjectIds = impl.getSubGrpHistory(regNo, schemeNo, false);
			}

			if (listSubjectIds == null) {
				listSubjectIds = new ArrayList<Integer>();
			}
			classCode = impl.getClassCodeforRegRoll(rollNo, regNo, false);

			sUBO = impl.select_student(rollNo, regNo, false);
			listMarksDetails = impl.selectMarksDetail_AllSubForOneStudent(sUBO
					.getId(), listSubjectIds, examId, ansScriptId, evaluatorId,
					examType);

		}
		return helper.convertBOToTO_MarksDetails(listMarksDetails, sUBO,
				classCode);

	}

	private boolean validateCourseScheme(int courseId, int schemeNo,
			String rollNo, boolean useRollNo, int examId) throws Exception {
		boolean flag = false;
		Iterator<Object[]> list = impl.validateCourseScheme(rollNo, useRollNo,
				examId).iterator();
		while (list.hasNext()) {
			Object[] row = (Object[]) list.next();
			Integer cId = null, sNo = null;
			if (row[0] != null) {
				cId = (Integer) row[0];
			}
			if (row[1] != null) {
				sNo = (Integer) row[1];
			}
			if (cId == courseId && sNo == schemeNo) {
				flag = true;
			}
		}
		return flag;

	}

	// To INSERT & UPDATE SS - AS
	public void updateAllSubjFor_OneStudent(Integer examMarksEntryId,
			int examId, int studentId, Integer evaluatorTypeId,
			String marksCardNo,
			ArrayList<ExamMarksEntryDetailsTO> listDetailsTO, String userId,
			String marksCardDate, Integer answerScriptTypeId)
			throws BusinessException, Exception {
		impl.insert_MarksEntry(helper.convertTOToBO_MarksMaster(
				examMarksEntryId, examId, studentId, evaluatorTypeId,
				marksCardNo, userId, marksCardDate, answerScriptTypeId), helper
				.convertTOToBO_MarksDetails(listDetailsTO, userId));

	}

	// To fetch all students for a single subject (AS - SS)
	public ArrayList<ExamMarksEntryStudentTO> getSingleSubjFor_AllStudents(
			int courseId, int subjectId, int subjectTypeId, String orderRollNo,
			int examId, Integer ansScriptId, Integer evaluatorId, int schemeNo,
			String examType, int classId) throws Exception {
		String subjectType = getSubjectsTypeBySubjectId(subjectId);
		List<Object[]> allStudentsForOneSubject=impl.select_allStudentsForOneSubject(courseId, subjectId,subjectTypeId, orderRollNo, examId, ansScriptId,evaluatorId, schemeNo, subjectType, examType, classId);
		List<Integer>listOfDetainedStudents = new ArrayList<Integer>(); 
		if(!examType.equalsIgnoreCase("Supplementary")){
			listOfDetainedStudents=getDetainedOrDiscontinuedStudents();
		}
		
		return helper.convertBOToTO_getStudentMarksDetails(allStudentsForOneSubject,subjectType,listOfDetainedStudents);
	}

	// To INSERT & UPDATE AS - SS
	public void updateSingleSubj_AllStudents(ExamMarksEntryForm objform) throws Exception {
		Integer evaluatorTypeId = null;
		Integer answerScriptId = null;
		if (objform.getEvaluatorType() != null
				&& !objform.getEvaluatorType().equals("")) {
			evaluatorTypeId = Integer.parseInt(objform.getEvaluatorType());
		}
		if (objform.getAnswerScriptType() != null
				&& !objform.getAnswerScriptType().equals("")) {
			answerScriptId = Integer.parseInt(objform.getAnswerScriptType());
		}

		Integer subjectId = Integer.parseInt(objform.getSubjectId());
		Integer examId = objform.getExamNameId();
		Integer marksEntryId = null;
		Integer detailId = null;
		String userId = objform.getUserId();

		ExamMarksEntryDetailsBO detBO;
		ExamMarksEntryBO masterBO;
		boolean theory_Practical = true;
		boolean theory = false;
		ArrayList<ExamMarksEntryStudentTO> listTO = objform
				.getExamMarksEntryStudentTO();
		for (ExamMarksEntryStudentTO eTO : listTO) {

			String theoryMarks = eTO.getTheoryMarks();
			String practicalMarks = eTO.getPracticalMarks();
			boolean flag = true;
			if (theoryMarks != null && theoryMarks.trim().length() > 0) {
				theoryMarks = eTO.getTheoryMarks();
			}
			if (practicalMarks != null && practicalMarks.trim().length() > 0) {
				practicalMarks = eTO.getPracticalMarks();
			}
			if ((theoryMarks != null && theoryMarks.trim().length() > 0)
					|| (practicalMarks != null && practicalMarks.trim()
							.length() > 0)) {

				marksEntryId = eTO.getMarksEntryId();
				detailId = eTO.getDetailId();
				boolean newMarksId = false;
				if (marksEntryId == null || marksEntryId < 1) {
					newMarksId = true;
					masterBO = new ExamMarksEntryBO();
					masterBO.setEvaluatorTypeId(evaluatorTypeId);
					masterBO.setAnswerScriptTypeId(answerScriptId);
					masterBO.setStudentId(eTO.getStudentId());
					masterBO.setLastModifiedDate(new Date());
					masterBO.setCreatedDate(new Date());
					masterBO.setExamId(examId);
					masterBO.setModifiedBy(userId);
					masterBO.setCreatedBy(userId);
					marksEntryId = impl.insert_returnId(masterBO);

				}
				detBO = new ExamMarksEntryDetailsBO(marksEntryId, subjectId);

				if (objform.getSubjectTypeId() == 1) {
					detBO.setTheoryMarks(theoryMarks);
					theory = true;

				} else if (objform.getSubjectTypeId() == 0) {
					detBO.setPracticalMarks(practicalMarks);
					theory_Practical = false;
				} else {
					detBO.setTheoryMarks(theoryMarks);
					detBO.setPracticalMarks(practicalMarks);
				}

				detBO.setCreatedBy(userId);
				detBO.setCreatedDate(new Date());
				detBO.setLastModifiedDate(new Date());
				detBO.setModifiedBy(userId);
				if (newMarksId || detailId == null || detailId < 1) {
					impl.insert(detBO);
					flag = false;
				}
			}
			if (flag) {

				impl.update_details(eTO.getDetailId(), theoryMarks,
						practicalMarks, theory_Practical, theory);
			}
		}
	}

	// To FETCH answer script list
	public HashMap<Integer, String> getListanswerScriptType(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId,
			Integer examId) throws Exception {

		List<Object> listBO = null;

		Integer validate = get_answerScriptFrom_ruleSettings(courseId,
				schemeNo, subjectId, subjectTypeId, examId);

		if (validate != null && validate > 0) {

			listBO = impl
					.select_ActiveOnly(ExamMultipleAnswerScriptMasterBO.class);

		} else {
			return new HashMap<Integer, String>();
		}
		return helper.convertBOToTO_Mul_Ans_Script_KeyVal(listBO);
	}

	public ExamMarksEntryForm getStudentDetails(ExamMarksEntryForm objform) throws Exception {
		objform.setListStudentDetailsSize(0);
		objform.setExamNameId(Integer.parseInt(objform.getExamName()));
		objform.setExamName(getExamNameByExamId(Integer.parseInt(objform
				.getExamName())));
		objform.setCourseId(objform.getCourse());
		objform.setCourse(getCourseName(Integer.parseInt(objform.getCourse())));
		String splitScheme[] = objform.getScheme().split("_");
		if (splitScheme[1] != null)
			objform.setSchemeId(splitScheme[1]);
		objform.setScheme(splitScheme[1]);
		if (objform.getSubjectType() != null
				&& objform.getSubjectType().trim().length() > 0) {
			objform
					.setSubjectTypeId(Integer
							.parseInt(objform.getSubjectType()));
		}
		String subjectType = objform.getSubjectType();
		if (subjectType.equals("1"))
			subjectType = "Theory";
		else if (subjectType.equals("0"))
			subjectType = "Practical";
		else
			subjectType = "Theroy/Practical";

		objform.setSubjectType(subjectType);

		return objform;
	}

	// Updatable - form
	public ExamMarksEntryForm getUpdatableForm(ExamMarksEntryForm objform) throws Exception {
		objform.setExamNameId(Integer.parseInt(objform.getExamName()));
		objform.setExamName(getExamNameByExamId(Integer.parseInt(objform
				.getExamName())));
		objform.setCourseId(objform.getCourse());
		objform.setCourse(getCourseName(Integer.parseInt(objform.getCourse())));
		String splitScheme[] = objform.getScheme().split("_");

		if (splitScheme[1] != null)
			objform.setSchemeId(splitScheme[1]);
		objform.setScheme(splitScheme[1]);
		objform.setSubjectId(objform.getSubject());
		objform.setSubject(getSubjectName(Integer
				.parseInt(objform.getSubject())));
		if (objform.getSubjectType() != null
				&& objform.getSubjectType().trim().length() > 0) {
			objform
					.setSubjectTypeId(Integer
							.parseInt(objform.getSubjectType()));
		}
		String subjectType = objform.getSubjectType();
		if (subjectType.equals("1"))
			subjectType = "Theory";
		else if (subjectType.equals("0"))
			subjectType = "Practical";
		else
			subjectType = "Theroy/Practical";

		objform.setSubjectType(subjectType);

		return objform;
	}

	// To get schemeNo for a course & exam
	public HashMap<String, String> getSchemeNoBy_ExamId_CourseId(int examId,
			int courseId) throws Exception {
		return helper.convertBOtoTO_SchemeNoBy_ExamId_CourseId(impl
				.select_SchemeNoBy_ExamId_CourseId(examId, courseId));
	}

	public ExamMarksEntryForm retainAllValues(ExamMarksEntryForm objform) throws Exception {
		CommonAjaxHandler commonHandler = new CommonAjaxHandler();
		if (CommonUtil.checkForEmpty(objform.getExamType())) {
			objform.setExamNameList(commonHandler.getExamNameByExamType(objform
					.getExamType()));
			objform.setExamType(objform.getExamType());
		}

		if (CommonUtil.checkForEmpty(objform.getExamName())) {
			objform.setCourseNameList(commonHandler.getCourseByExamName(objform
					.getExamName()));
			objform.setExamName(objform.getExamName());
		}
		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getExamName())) {
			objform.setSchemeNameList(commonHandler
					.getSchemeNoByExamIdCourseId(Integer.parseInt(objform
							.getExamName()), Integer.parseInt(objform
							.getCourse())));

			objform.setScheme(objform.getScheme());
		}
		int schemeNo = 0;
		int courseId = 0;
		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getScheme())) {
			String schemeSplit[] = objform.getScheme().split("_");
			schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			courseId = Integer.parseInt(objform.getCourse());
			Integer examName = Integer.parseInt(objform.getExamName());
			objform.setSubjectNameList(commonHandler
					.getSubjectsByCourseSchemeExamId(Integer.parseInt(objform
							.getCourse()), schemeId, schemeNo, examName));

			if (CommonUtil.checkForEmpty(objform.getSubject())
					&& CommonUtil.checkForEmpty(objform.getSubjectType())) {

				int subject = Integer.parseInt(objform.getSubject());
				int subType = Integer.parseInt(objform.getSubjectType());
				objform.setSubject(objform.getSubject());

				objform.setEvaluatorMap(get_evaluatorList_ruleSettings(
						courseId, schemeNo, subject, subType, examName));

				objform.setAnswerScriptTypeMap(getListanswerScriptType(
						courseId, schemeNo, subject, subType, examName));
			} else {
				objform.setEvaluatorMap(get_evaluatorList_ruleSettings(
						courseId, schemeNo, null, null, examName));

				objform.setAnswerScriptTypeMap(getListanswerScriptType(
						courseId, schemeNo, null, null, examName));
			}
		}
		return objform;
	}

	// Validation for multiple answer scripts
	public Integer get_answerScriptFrom_ruleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId,
			Integer examId) throws Exception {

		return impl.select_answerScriptFrom_ruleSettings(courseId, schemeNo,
				subjectId, subjectTypeId, examId);
	}

	// To FETCH evaluator list
	public HashMap<Integer, String> get_evaluatorList_ruleSettings(
			Integer courseId, Integer schemeNo, Integer subjectId,
			Integer subjectTypeId, Integer examId) throws Exception {

		return helper.convertBOToTO_Mul_EvaluatorType(impl
				.select_evaluatorList_ruleSettings(courseId, schemeNo,
						subjectId, subjectTypeId, examId));
	}

	// Get current exam name for a exam type
	public String getCurrentExamName(String examType) throws Exception {

		return impl.getCurrentExamName(examType);
	}

	// Get Absent code from Exam Settings
	public String getCodeForAbsentEntry() throws Exception {

		return impl.getCodeForAbsentEntry();
	}

	// Get Not Process code from Exam Settings
	public String getCodeForNotprocessEntry() throws Exception {

		return impl.getCodeForNotprocessEntry();
	}

	// Marks Validation for theory
	public BigDecimal getTheoryMaxMarkForSubject(String examType,
			Integer courseId, Integer schemeNo, Integer subjectId,
			Integer subjectTypeId, Integer examId, Integer ansScriptId,
			Integer multipleEvaluator, Integer academicYear) throws Exception {

		return impl.getTheoryMaxMarkForSubject(examType, courseId, schemeNo,
				subjectId, subjectTypeId, examId, ansScriptId,
				multipleEvaluator, academicYear);
	}

	// Marks Validation for practical
	public BigDecimal getPracticalMaxMarkForSubject(String examType,
			Integer courseId, Integer schemeNo, Integer subjectId,
			Integer subjectTypeId, Integer examId, Integer ansScriptId,
			Integer multipleEvaluator, Integer academicYear) throws Exception {

		return impl.getPracticalMaxMarkForSubject(examType, courseId, schemeNo,
				subjectId, subjectTypeId, examId, ansScriptId,
				multipleEvaluator, academicYear);
	}
	
	public List<Integer>getDetainedOrDiscontinuedStudents()throws Exception
	{
		return impl.getDetainedOrDiscontinuedStudents();
	}
	public String getDetainedDiscontinuedStudent(int studentId, int classId)throws Exception
	{
		
		return impl.getDetainedDiscontinuedStudent(studentId, classId);
	}
	public List<Integer>getStudentsHallTicketBlocked(int studentId, int classId, int examId)throws Exception
	{
		return impl.getStudentsHallTicketBlocked(studentId, classId, examId);
	}
	
	/**
	 * 
	 * @param studentId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public Integer getAcademicYearFromPreviousClassDetails(int studentId, String schemeNo)throws Exception{
		ExamSecuredMarksEntryImpl securedImpl = new ExamSecuredMarksEntryImpl();
		return securedImpl.getAcademicYearFromPreviousClassDetails(studentId, schemeNo);
	}
	public List<Integer>getStudentsHallTicketBlockedForPromotion(int studentId)throws Exception
	{
		return impl.getStudentsHallTicketBlockedForPromotion(studentId);
	}
	
}
