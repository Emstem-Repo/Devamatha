package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationBO;
import com.kp.cms.bo.exam.ExamRevaluationDetailsBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamRevaluationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamRevaluationHelper;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamRevaluationDetailsTO;
import com.kp.cms.to.exam.ExamRevaluationEntryTO;
import com.kp.cms.to.exam.ExamRevaluationStudentTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamRevaluationHandler extends ExamGenHandler {

	ExamRevaluationHelper helper = new ExamRevaluationHelper();
	ExamRevaluationImpl impl = new ExamRevaluationImpl();

	// To get Re-valuation Type List
	public ArrayList<KeyValueTO> getListRevaluationType() {
		List<ExamRevaluationTypeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamRevaluationTypeBO.class));
		return helper.convertBOToTO_RevaluationType(listBO);
	}

	// To FETCH SS - AS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ExamRevaluationEntryTO getAllSubjFor_OneStudent(int courseId,
			String rollNo, String regNo, int examId, int schemeNo,
			int revaluationId) throws BusinessException {
		boolean rollNoPresent = false;
		boolean regNoPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNoPresent = true;
		}
		int id = impl.getStudentIdRollORReg(rollNo, regNo, rollNoPresent);
		if(id==0){
			throw new BusinessException("Inconsistent Roll and Reg No");	
		}

		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent Roll and Reg No");
			}
		}

		ArrayList<Integer> listSubjectIds;
		List listMarksDetails;
		StudentUtilBO sUBO;
		String classCode = "";

		listSubjectIds = impl.select_subjectIdForRevaluation(courseId, rollNo,
				regNo, rollNoPresent, schemeNo, examId, revaluationId);
		if (listSubjectIds == null) {
			listSubjectIds = new ArrayList<Integer>();
		}

		sUBO = impl.select_student(rollNo, regNo, rollNoPresent);
		listMarksDetails = impl.selectMarksDetail_AllSubForOneStudent(sUBO
				.getId(), listSubjectIds, examId, revaluationId);

		return helper.convertBOToTO_MarksDetails(listMarksDetails, sUBO,
				classCode, schemeNo, courseId);

	}

	// To INSERT & UPDATE SS - AS
	public void updateAllSubjFor_OneStudent(Integer examMarksEntryId,
			int examId, int studentId, int revaluationTypeId,
			String marksCardNo,
			ArrayList<ExamRevaluationDetailsTO> listDetailsTO, String userId,
			String marksCardDate) throws BusinessException {

		impl.insert_MarksEntry(helper.convertTOToBO_MarksMaster(
				examMarksEntryId, examId, studentId, revaluationTypeId,
				marksCardNo, userId, marksCardDate), helper
				.convertTOToBO_MarksDetails(listDetailsTO, userId));

	}

	// To FETCH AS - SS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ArrayList<ExamRevaluationStudentTO> getSingleSubjFor_AllStudents(
			int courseId, int subjectId, int subjectTypeId,
			boolean orderRollNo, int examId, int revaluationId, int schemeNo)
			throws Exception {
		return helper.convertBOToTO_getStudentMarksDetails(impl
				.select_allStudentsForOneSubject(courseId, subjectId,
						subjectTypeId, orderRollNo, examId, revaluationId,
						schemeNo), getSubjectsTypeBySubjectId(subjectId),
				schemeNo, courseId, subjectId);

	}

	// To INSERT & UPDATE AS - SS
	public void updateSingleSubj_AllStudents(ExamRevaluationForm objform) {
		Integer revaluationTypeId = null;
		if (objform.getRevaluationType() != null
				&& !objform.getRevaluationType().equals("")) {
			revaluationTypeId = Integer.parseInt(objform.getRevaluationType());
		}
		Integer subjectId = Integer.parseInt(objform.getSubjectId());
		Integer examId = objform.getExamNameId();
		String userId = objform.getUserId();
		Integer examRevaluationId = null;
		Integer detailId = null;
		ExamRevaluationDetailsBO detBO;
		ExamRevaluationBO masterBO;
		boolean theory_Practical = true;
		boolean theory = false;
		ArrayList<ExamRevaluationStudentTO> listTO = objform
				.getExamRevaluationStudentTO();
		for (ExamRevaluationStudentTO eTO : listTO) {

			String theoryMarks = eTO.getCurrentTheoryMarks();
			String practicalMarks = eTO.getCurrentPracticalMarks();
			boolean flag = true;
			BigDecimal tMarks = null;
			BigDecimal pMarks = null;
			if (theoryMarks != null && theoryMarks.trim().length() > 0) {
				tMarks = new BigDecimal(eTO.getCurrentTheoryMarks());
			}
			if (practicalMarks != null && practicalMarks.trim().length() > 0) {
				pMarks = new BigDecimal(eTO.getCurrentPracticalMarks());
			}
			if ((theoryMarks != null && theoryMarks.trim().length() > 0)
					|| (practicalMarks != null && practicalMarks.trim()
							.length() > 0)) {

				examRevaluationId = eTO.getMarksEntryId();
				detailId = eTO.getDetailId();
				boolean newMarksId = false;
				if (examRevaluationId == null || examRevaluationId < 1) {
					newMarksId = true;
					masterBO = new ExamRevaluationBO();
					masterBO.setRevaluationTypeId(revaluationTypeId);
					masterBO.setStudentId(eTO.getStudentId());
					masterBO.setExamId(examId);
					examRevaluationId = impl.insert_returnId(masterBO);
				}
				detBO = new ExamRevaluationDetailsBO(examRevaluationId,
						subjectId, userId);

				if (objform.getSubjectTypeId() == 1) {
					detBO.setPreviousTheoryMarks(tMarks);
					theory = true;

				} else if (objform.getSubjectTypeId() == 0) {
					detBO.setPreviousPracticalMarks(pMarks);
					theory_Practical = false;
				} else {
					detBO.setPreviousTheoryMarks(tMarks);
					detBO.setPreviousPracticalMarks(pMarks);
				}

				if (newMarksId || detailId == null || detailId < 1) {
					impl.insert(detBO);
					flag = false;
				}
			}
			if (flag) {

				impl.update_details(eTO.getDetailId(), tMarks, pMarks,
						theory_Practical, theory);
			}
		}
	}

	// To get the first grid for single student
	public ExamRevaluationForm getStudentDetails(ExamRevaluationForm objform) {
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

	// To get Updatable Form
	public ExamRevaluationForm getUpdatableForm(ExamRevaluationForm objform) {
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
			int courseId) {
		return helper.convertBOtoTO_SchemeNoBy_ExamId_CourseId(impl
				.select_SchemeNoBy_ExamId_CourseId(examId, courseId));
	}

	// To get subjectType by subjectID
	public String getSubjectsTypeBySubjectId(int subjectId) {
		return impl.selectSubjectsTypeBySubjectId(subjectId);
	}

	// Retain values on error
	public ExamRevaluationForm retainAllValues(ExamRevaluationForm objform) {
		CommonAjaxHandler commonHandler = new CommonAjaxHandler();
		if (CommonUtil.checkForEmpty(objform.getExamName())) {
			objform.setCourseNameList(commonHandler.getCourseByExamName(objform
					.getExamName()));

		}
		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getExamName())) {
			objform.setSchemeNameList(commonHandler
					.getSchemeNoByExamIdCourseId(Integer.parseInt(objform
							.getExamName()), Integer.parseInt(objform
							.getCourse())));

		}
		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getScheme())) {
			String schemeSplit[] = objform.getScheme().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			objform.setSubjectNameList(commonHandler.getSubjectsByCourse(
					Integer.parseInt(objform.getCourse()), schemeId, schemeNo));

		}

		return objform;
	}

	// To FETCH subject type & evaluation type from SUBJECT - RULE - SETTINGS
	public ArrayList<DisplayValueTO> get_type_of_evaluation(int subjectId,
			int courseId, int schemeNo) {
		return impl.get_type_of_evaluation(subjectId, courseId, schemeNo);
	}

}
