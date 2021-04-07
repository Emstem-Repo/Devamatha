package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.ExamStudentMarksCorrectionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamStudentMarksCorrectionHelper;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionAllstudentsTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionSingleStudentTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionTO;
import com.kp.cms.transactionsimpl.exam.ExamStudentMarksCorrectionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamStudentMarksCorrectionHandler extends ExamGenHandler {

	ExamStudentMarksCorrectionHelper helper = new ExamStudentMarksCorrectionHelper();
	ExamStudentMarksCorrectionImpl impl = new ExamStudentMarksCorrectionImpl();

	// To update single student's all subject's marks
	public void updateAllSubjFor_OneStudent(Integer studentsMarksCorrectionId,
			int examId, int studentId, Integer evaluatorType,
			Integer answerScriptTypeId, String marksType, String marksCardNo,
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO,
			String userId,
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> originalList, boolean secured)
			throws BusinessException {

		impl.insert_MarksCorrection(helper.convertTOToBO_MarksMaster(
				studentsMarksCorrectionId, examId, studentId, evaluatorType,
				answerScriptTypeId, marksType, userId, marksCardNo), helper
				.convertTOToBO_MarksDetails(listDetailsTO, userId), studentId, evaluatorType, examId, secured, answerScriptTypeId);

	}

	// To update single subject marks for all students
	public void updateSingleSubj_AllStudents(
			ExamStudentMarksCorrectionForm objform) {

		Integer subjectId = Integer.parseInt(objform.getSubjectId());
		Integer marksEntryId = null;

		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> listTO = objform
				.getSingleSubjFor_AllStudents();

		for (ExamStudentMarksCorrectionAllstudentsTO eTO : listTO) {

			int mistake = 0;
			int retest = 0;

			if (eTO.getMistake() == false) {
				mistake = 0;
			} else {
				mistake = 1;
			}
			if (eTO.getRetest() == false) {
				retest = 0;
			}

			marksEntryId = eTO.getMarksEntryId();
			String theory1 = null;
			String practical1 = null;
			if (eTO.getTheoryMarks() != null
					&& eTO.getTheoryMarks().trim().length() > 0) {
				theory1 = eTO.getTheoryMarks();
			}
			if (eTO.getPracticalMarks() != null
					&& eTO.getPracticalMarks().trim().length() > 0) {
				practical1 = eTO.getPracticalMarks();
			}

			if (impl.chkIfCorrectionDone(theory1, practical1, marksEntryId,
					subjectId)) {
				ExamMarksEntryDetailsBO detBO = new ExamMarksEntryDetailsBO(marksEntryId, subjectId,
						retest, mistake, theory1, practical1,
						eTO.getComments(), objform.getUserId(),
						eTO.getPreviousEvaluatorTheoryMarks(),
						eTO.getPreviousEvaluatorPracticalMarks(), eTO.getModifiedBy(), eTO.getLastModifiedDate(),
						eTO.getCreatedBy(), eTO.getCreatedDate());
				
				List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = new ArrayList<ExamMarksEntryDetailsBO>();
				examMarksEntryDetailsBOList.add(detBO);
				impl.updateentryCorrectionDetailsBO(examMarksEntryDetailsBOList);
				//impl.saveHistoryToTable(detBO);
				impl.deleteExamMarkEntryDetailsBOForAllStudents(detBO);
				detBO.setComments(null);
				impl.insert(detBO);
			}

		}
	}

	// To update single subject marks for all students
	public void updateSingleSubj_AllStudentsOverall(
			ExamStudentMarksCorrectionForm objform) {
		Integer subjectId = Integer.parseInt(objform.getSubjectId());
		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> listTO = objform
				.getSingleSubjFor_AllStudents();
		for (ExamStudentMarksCorrectionAllstudentsTO eTO : listTO) {
			if (objform.getMarkType().equals("Internal overAll")) {
				impl.chkNInsertInternalOverall(eTO.getTheoryMarks(), eTO
						.getPracticalMarks(), eTO.getMarksEntryId(), subjectId,
						eTO.getComments());
			} else if (objform.getMarkType().equals("Regular overAll")) {
				impl.chkNInsertRegularOverall(eTO.getTheoryMarks(), eTO
						.getPracticalMarks(), eTO.getMarksEntryId(), subjectId,
						eTO.getComments());
			}
		}
	}

	public String getSubjectsTypeBySubjectId(int subjectId) {
		String value = impl.selectSubjectsTypeBySubjectId(subjectId);
		return value.toUpperCase();
	}

	// To get all students for a single subject

	public ArrayList<ExamStudentMarksCorrectionAllstudentsTO> getSingleSubjFor_AllStudents(
			int courseId, int subjectId, int examId, Integer ansScriptId,
			Integer evaluatorId, int secured, String markType, int schemeNo)
			throws Exception {
		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> list = new ArrayList<ExamStudentMarksCorrectionAllstudentsTO>();
		Map<Integer, List<Integer>> markSubjectList = null;
		if (markType != null && markType.equals("")) {
			markSubjectList = impl.getoldMarkPresent();
			list = helper.convertBOToTO_getStudentMarksDetails(
					getSubjectsTypeBySubjectId(subjectId), impl
							.select_allStudentsForOneSubject(courseId,
									subjectId, examId, ansScriptId,
									evaluatorId, secured, markType),  markSubjectList);
		}
		if (markType != null && markType.equals("Internal overAll")) {
			markSubjectList = impl.getoldMarkPresentForInternalOverAll();
			// DisplayValueTO dto = helper.convertInternalSettings(impl
			// .selectInternalSettings(subjectId, examId, courseId,
			// schemeNo));
			list = helper.convertBOToTO_getStudentInternalOverall(
					getSubjectsTypeBySubjectId(subjectId), impl
							.select_allStudentsForOneSubjectForInternalOverall(
									courseId, subjectId, examId), markSubjectList);
		}
		if (markType != null && markType.equals("Regular overAll")) {
			markSubjectList = impl.getoldMarkPresentForRegularOverAll(); 
			list = helper.convertBOToTO_getStudentInternalOverall(
					getSubjectsTypeBySubjectId(subjectId), impl
							.select_allStudentsForOneSubjectForRegularOverAll(
									courseId, subjectId, examId), markSubjectList);
		}

		return list;
	}

	public ExamStudentMarksCorrectionForm getStudentDetails(
			ExamStudentMarksCorrectionForm objform) {
		objform.setListStudentDetailsSize(0);
		objform.setExamNameId(Integer.parseInt(objform.getExamName()));
		objform.setExamName(getExamNameByExamId(Integer.parseInt(objform
				.getExamName())));
		objform.setCourseId(objform.getCourse());
		if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
			objform.setCourse(getCourseName(Integer.parseInt(objform.getCourse())));
			
			if (objform.getScheme() != null
					&& objform.getScheme().trim().length() > 0) {
				String schemes[] = objform.getScheme().split("_");
				objform.setScheme(schemes[1]);
				objform.setSchemeId(schemes[0]);
			}			
		}
		
		objform.setExamType(objform.getExamType());
		return objform;
	}

	public ExamStudentMarksCorrectionForm getUpdatableForm(
			ExamStudentMarksCorrectionForm objform) {
		objform.setExamNameId(Integer.parseInt(objform.getExamName()));
		objform.setExamName(getExamNameByExamId(Integer.parseInt(objform
				.getExamName())));
		objform.setCourseId(objform.getCourse());
		objform.setCourse(getCourseName(Integer.parseInt(objform.getCourse())));
		if (objform.getScheme() != null
				&& objform.getScheme().trim().length() > 0) {
			String schemes[] = objform.getScheme().split("_");
			objform.setScheme(schemes[1]);
			objform.setSchemeId(schemes[0]);
		}
		objform.setSubjectId(objform.getSubject());
		objform.setSubject(getSubjectName(Integer
				.parseInt(objform.getSubject())));

		return objform;
	}

	// To get all subjects for a single student
	public ExamStudentMarksCorrectionTO getAllSubjFor_OneStudent(int courseId,
			String rollNo, String regNo, int examId, int schemeNo,
			Integer ansScriptId, Integer evaluatorId, int secured,
			String markType, boolean isPreviousExam) throws BusinessException {
		ExamStudentMarksCorrectionTO returnTO = new ExamStudentMarksCorrectionTO();
		boolean rollNoPresent = false;
		boolean regNoPresent = false;
		ExamRegDecrypt de = new ExamRegDecrypt();
		if (rollNo != null && rollNo.length() > 0) {
			if (secured == 1) {
				rollNo = de.decrypt(rollNo);
			}
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			if (secured == 1) {
				regNo = de.decrypt(regNo);
			}
			regNoPresent = true;
		}
		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent Roll and Reg No");
			}
		}
		ArrayList<Integer> listSubjectIds = null;
		List listMarksDetails = new ArrayList<Object[]>();
		
		StudentUtilBO sUBO = null;
		sUBO = impl.select_student(rollNo, regNo, rollNoPresent);
		courseId = sUBO.getAdmApplnUtilBO().getSelectedCourseId();
		listSubjectIds = getSubjectIdList(courseId, rollNo, regNo,
				rollNoPresent, schemeNo, sUBO.getId(), isPreviousExam);
		if (listSubjectIds != null && listSubjectIds.size() > 0) {

			//sUBO = impl.select_student(rollNo, regNo, rollNoPresent);
			Map<Integer, List<Integer>> markIdList;
			if (markType != null && markType.equals("")) {
				markIdList = impl.getoldMarkPresent();
				listMarksDetails = impl.selectMarksDetail_AllSubForOneStudent(
						sUBO.getId(), listSubjectIds, examId, ansScriptId,
						evaluatorId, secured);

				returnTO = helper.convertBOToTO_MarksDetails(listMarksDetails,
						sUBO, markIdList);
			} else if (markType != null && markType.equals("Internal overAll")) {
				markIdList = impl.getoldMarkPresentForInternalOverAll();
				for (Integer subId : listSubjectIds) {

					
					listMarksDetails
							.addAll(impl
									.selectMarksDetail_AllSubForOneStudentInternalOverall(
											sUBO.getId(), subId, examId));
				}

				returnTO = helper.convertBOToTO_MarksDetailsInternalOverall(
						listMarksDetails, sUBO, markIdList);

			} else if (markType != null && markType.equals("Regular overAll")) {
				markIdList = impl.getoldMarkPresentForRegularOverAll();
				listMarksDetails = impl
						.selectMarksDetail_AllSubForOneStudentRegularOverall(
								sUBO.getId(), listSubjectIds, examId);
				returnTO = helper.convertBOToTO_MarksDetailsInternalOverall(
						listMarksDetails, sUBO, markIdList);
			}
			if(listMarksDetails == null || listMarksDetails.size() <= 0){
				throw new DataNotFoundException();
			}
			return returnTO;
		} else {
			throw new DataNotFoundException();
		}
		

	}

	public ExamStudentMarksCorrectionForm retainAllValues(
			ExamStudentMarksCorrectionForm objform) throws Exception {
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
		int schemeNo = 0, schemeId = 0;
		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getScheme())) {
			String schemeSplit[] = objform.getScheme().split("_");
			schemeNo = Integer.parseInt(schemeSplit[1]);
			schemeId = Integer.parseInt(schemeSplit[0]);
			objform.setSubjectNameList(commonHandler
					.getSubjectsByCourseSchemeExamId(Integer.parseInt(objform
							.getCourse()), schemeId, schemeNo, Integer
							.parseInt(objform.getExamName())));
			objform.setSubject(objform.getSubject());
		}
		if (CommonUtil.checkForEmpty(objform.getExamType())) {
			HashMap<String, String> markTypeMap = new HashMap<String, String>();
			if (objform.getExamType().equalsIgnoreCase("Internal")) {
				markTypeMap = new ExamGenHandler().getInternalExamType();
			} else {
				markTypeMap.put("Internal overAll", "Internal overAll");
				markTypeMap.put("Regular overAll", "Regular overAll");
			}
			objform.setMarkTypeList(markTypeMap);
			objform.setMarkType(objform.getMarkType());
		}

		if (/*CommonUtil.checkForEmpty(objform.getCourse())
				&& */CommonUtil.checkForEmpty(objform.getScheme())) {
			Integer courseId = null;
			String schemeSplit[] = objform.getScheme().split("_");
			
			if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
				courseId = Integer.parseInt(objform.getCourse());
				schemeId = Integer.parseInt(schemeSplit[0]);
				schemeNo = Integer.parseInt(schemeSplit[1]);
			}
			else{
				if(schemeSplit[0]!= null && !schemeSplit[0].trim().isEmpty()){
					schemeNo = Integer.parseInt(schemeSplit[0]);
				}
			}
			ExamMarksEntryHandler h = new ExamMarksEntryHandler();
			if (CommonUtil.checkForEmpty(objform.getSubject())
					&& CommonUtil.checkForEmpty(objform.getSubjectType())) {

				int subject = Integer.parseInt(objform.getSubject());
				int subType = Integer.parseInt(objform.getSubjectType());
				Integer examName = Integer.parseInt(objform.getExamName());
				objform.setSubject(objform.getSubject());

				objform.setEvaluatorMap(h.get_evaluatorList_ruleSettings(
						courseId, schemeNo, subject, subType, examName));

				objform.setAnswerScriptTypeMap(h.getListanswerScriptType(
						courseId, schemeNo, subject, subType, examName));
			} else {
				if(objform.getExamName()!= null && !objform.getExamName().trim().isEmpty()){
					Integer examName = Integer.parseInt(objform.getExamName());
					objform.setEvaluatorMap(h.get_evaluatorList_ruleSettings(
							courseId, schemeNo, null, null, examName));
	
					objform.setAnswerScriptTypeMap(h.getListanswerScriptType(
							courseId, schemeNo, null, null, examName));
				}
			}
		}
		// ---------------
		return objform;
	}

	// Fetch evaluator list
	public HashMap<Integer, String> get_evaluatorList_fromRuleSettings(
			Integer courseId, Integer schemeNo, Integer subjectId) {
		return helper.convert_evaluatorList_fromRuleSettings(impl
				.getEvaluator_list(courseId, schemeNo, subjectId));
	}

	// Mandatory check for answer script type
	public Integer getanswerScriptType(Integer courseId, Integer schemeNo,
			Integer subjectId) {

		return impl.getanswerScriptId_fromRuleSettings(courseId, schemeNo,
				subjectId);
	}

	// Fetch answer script type
	public HashMap<Integer, String> getListanswerScriptType() {

		List<ExamMultipleAnswerScriptMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamMultipleAnswerScriptMasterBO.class));
		return helper.convertBOToTO_getListanswerScriptType(listBO);
	}

	// To get student's old marks
	public void viewOldMarksForSingleStudent(
			ExamStudentMarksCorrectionForm objform) {
		//int secured = Integer.parseInt(objform.getSecured());

/*		objform.setSingleStuFor_AllSubjects1(helper.convertBOToTO_OldMarks(impl
				.getPrevMarks(objform.getExamNameId(), objform.getStudentId(),
						objform.getEvaluatorTypeId(), objform
								.getAnswerScriptTypeId(), Integer
								.parseInt(objform.getSubjectId()), secured,
						objform.getMarkType())));
*/		
		objform.setSingleStuFor_AllSubjects1(helper.convertBOToTO_OldMarks(impl.getoldMarkDetails(Integer.parseInt(objform.getExamMasterId()),
				objform.getSubjectId())));

	}

	public void viewOldMarksOverall(ExamStudentMarksCorrectionForm objform)
			throws NumberFormatException, DataNotFoundException {
		String markType = objform.getMarkType();
		
		if (markType != null && markType.equals("Internal overAll")) {
			/*objform.setSingleStuFor_AllSubjects1(helper
					.convertBOToTO_OldMarks(impl.getPrevMarksInternal(Integer
							.parseInt(objform.getCourseId()), Integer
							.parseInt(objform.getSubjectId()), objform
							.getExamNameId(), objform.getStudentId())));*/
			objform.setSingleStuFor_AllSubjects1(helper
			.convertBOToTO_OldMarks(impl.getoldMarkDetailsInternal(Integer.parseInt(objform.getExamMasterId()))));
			

			/*objform.setSingleStuFor_AllSubjects1(helper
			.convertBOToTO_OldMarks(impl.getoldMarkDetailsInternal());
*/
			
		}

		
		
		if (markType != null && markType.equals("Regular overAll")) {
			/*objform.setSingleStuFor_AllSubjects(helper
					.convertBOToTO_OldMarks(impl.getPrevMarksRegular(Integer
							.parseInt(objform.getCourseId()), Integer
							.parseInt(objform.getSubjectId()), objform
							.getExamNameId(), objform.getStudentId())));*/
			objform.setSingleStuFor_AllSubjects1(helper
			.convertBOToTO_OldMarks(impl.getoldMarkDetailsInternalRegular(Integer.parseInt(objform.getExamMasterId()))));
		}

	}

	public void updateAllSubjFor_OneStudentOverall(Integer id, Integer examId,
			Integer studentId, String markType,
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO,
			String userId, int courseId, int schemeId)
			throws DataNotFoundException {
		
		if (markType != null && markType.equals("Internal overAll")) {

			helper.convertTOToBO_SingleStuInternalOverall(id, examId,
					studentId, listDetailsTO, userId, courseId, schemeId);
		} else if (markType != null && markType.equals("Regular overAll")) {
			helper.convertTOToBO_SingleStuRegularOverall(id, examId, studentId,
					listDetailsTO, userId, courseId, schemeId);

		}
	}
}
