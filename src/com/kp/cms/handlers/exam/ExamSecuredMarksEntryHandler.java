package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSecuredMarksEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamSecuredMarksEntryHelper;
import com.kp.cms.to.exam.ExamSecuredMarksEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamSecuredMarksEntryImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamSecuredMarksEntryHandler extends ExamGenHandler {

	private static ExamSecuredMarksEntryHandler handler = null;

	public static ExamSecuredMarksEntryHandler getInstance() {

		if (handler == null)
			return new ExamSecuredMarksEntryHandler();
		else
			return handler;

	}

	ExamSecuredMarksEntryHelper helper = new ExamSecuredMarksEntryHelper();
	ExamSecuredMarksEntryImpl impl = new ExamSecuredMarksEntryImpl();

	// To FETCH the subjects list
	public ArrayList<KeyValueTO> getSubjectList() {
		ArrayList<SubjectUtilBO> listBO = impl.get_subjectList();
		return helper.convertBOToTO_Subject_KeyVal(listBO);
	}

	public String getSubjectCode(int subjectId) {
		return ((SubjectUtilBO) impl.select_Unique(subjectId,
				SubjectUtilBO.class)).getCode();
	}

	public ArrayList<ExamSecuredMarksEntryTO> getSingleStudentMarks() {

		ArrayList<ExamSecuredMarksEntryTO> list = new ArrayList<ExamSecuredMarksEntryTO>();
		for (int i = 1; i <= 10/*20*/; i++) {

			list.add(new ExamSecuredMarksEntryTO(i));
		}
		return list;
	}

	// To get secured mark type from EXAM - SETTINGS
	public boolean getSecured_marks_Entry_By_Settings() {

		return impl.getsecured_marks_type();
	}

	// To get max marks for a subject based on exam type from SUBJECT - RULE
	// SETTINGS
	public BigDecimal getMaxMarks(String examType, Integer subjectId,
			Integer answerScriptId, String subjectType, String rollReg,
			Integer examId, String isPreviousExam, String supSchemeNo) {

		return impl.getMaxMarksForSubject(examType, subjectId, answerScriptId,
				subjectType, get_Student_id_rollReg(rollReg), examId, isPreviousExam, supSchemeNo);
	
	}

	// DUPLICATE CHECK
	public boolean checkForDuplicateEntry(Integer examId, Integer subjectId,
			String subjectTypeId, Integer answerScriptId,
			Integer evaluatorTypeId, String registerNo) {
		return impl.checkForDuplicateEntry(examId, subjectId, subjectTypeId,
				answerScriptId, evaluatorTypeId, registerNo,
				getSecured_marks_Entry_By_Settings());
	}

	// DUPLICATE CHECK
	public boolean checkForAbscentEntry(Integer examId, Integer subjectId,
			String subjectTypeId, Integer answerScriptId,
			Integer evaluatorTypeId, String registerNo, String abscentCode,
			String notProcessCode) {

		return impl.checkForAbscentEntry(examId, subjectId, subjectTypeId,
				answerScriptId, evaluatorTypeId, registerNo, abscentCode,
				notProcessCode, getSecured_marks_Entry_By_Settings());
	}

	public int addChanges(ExamSecuredMarksEntryForm objform)
			throws BusinessException {
		int flag = 0;
		ArrayList<ExamSecuredMarksEntryTO> listOfStudentsTo = objform
				.getListSingleStudents();
		Integer examId = null, subjectId = null, answerScriptId = null, evaluatorTypeId = null;
		String subjectTypeId = null;
		if (CommonUtil.checkForEmpty(objform.getEvaluatorType())) {
			evaluatorTypeId = Integer.parseInt(objform.getEvaluatorType());
		}
		if (CommonUtil.checkForEmpty(objform.getAnswerScriptType())) {
			answerScriptId = Integer.parseInt(objform.getAnswerScriptType());
		}
		examId = Integer.parseInt(objform.getExamId());
		subjectId = Integer.parseInt(objform.getSubject());
		subjectTypeId = objform.getSubjectType();
		if (CommonUtil.checkForEmpty(objform.getEvaluatorTypeId())) {
			evaluatorTypeId = Integer.parseInt(objform.getEvaluatorTypeId());
		}
		if (CommonUtil.checkForEmpty(objform.getAnswerScriptTypeId())) {
			answerScriptId = Integer.parseInt(objform.getAnswerScriptTypeId());
		}
		boolean abscentEntry = false;
		String marks = null;
		
		
		
		int slNo = 0;
		List<String> regNoList = new ArrayList<String>();
		List<String> slNoList = new ArrayList<String>();
 		for (ExamSecuredMarksEntryTO duplTo : listOfStudentsTo) {
 			if(duplTo.getRegisterNo() == null || duplTo.getRegisterNo().trim().isEmpty()){
 				continue;
 			}
			if(!regNoList.contains(duplTo.getRegisterNo().trim())){
				regNoList.add(duplTo.getRegisterNo().trim());	
			}
			else
			{
				slNoList.add(Integer.toString(duplTo.getSerialNumber()));
			}
			
 		}
		StringBuffer slNos = new StringBuffer();
		if(slNoList!= null && slNoList.size() > 0){
			Iterator<String> itr = slNoList.iterator();
			while (itr.hasNext()) {
				String slNoString = (String) itr.next();
				slNos.append(slNoString + ",");
				
			}
		}
		String slNumbers = slNos.toString();
		if(slNumbers!= null && slNumbers.length() > 0){
			if(slNumbers.endsWith(",")){
				slNumbers = StringUtils.chop(slNumbers);
			}
			throw new DuplicateException(slNumbers);
		}
		
		for (ExamSecuredMarksEntryTO to : listOfStudentsTo) {
			if(to.getRegisterNo() ==  null || to.getRegisterNo().trim().isEmpty()){
				continue;
			}
			marks = null;
			abscentEntry = checkForAbscentEntry(examId, subjectId,
					subjectTypeId, answerScriptId, evaluatorTypeId,
					getDecryptRegNo(to.getRegisterNo()), impl
							.getCodeForAbsentEntry(), impl
							.getCodeForNotprocessEntry());
			if (!abscentEntry) {
				if (checkForDuplicateEntry(examId, subjectId, subjectTypeId,
						answerScriptId, evaluatorTypeId, getDecryptRegNo(to
								.getRegisterNo()))) {
					throw new DuplicateException(Integer.toString(to
							.getSerialNumber()));
				}
			}

			if (objform.getSubjectType().equalsIgnoreCase("Theory"))
				marks = to.getTheoryMarks();
			else if (objform.getSubjectType().equalsIgnoreCase("Practical")) {
				marks = to.getPracticalMarks();
			}
			flag = add(to, evaluatorTypeId, subjectId, examId, subjectTypeId,
					abscentEntry, answerScriptId);
		}
		return flag;
	}

	// On - VIEW
	public int addViewChanges(ExamSecuredMarksEntryForm objform) {
		ArrayList<ExamSecuredMarksEntryTO> listOfStudentsTo = objform
				.getListSingleStudentsView();
		int flag = 0;
		for (ExamSecuredMarksEntryTO to : listOfStudentsTo) {
			if (to.getDetailId() != null
					&& to.getDetailId().trim().length() > 0)
				flag = impl.update_mistake(Integer.parseInt(to.getDetailId()),
						(to.getMistake()) ? 1 : 0);
		}
		return flag;
	}

	// On - SUBMIT - ADD & UPDATE
	private int add(ExamSecuredMarksEntryTO eTO, Integer evaluatorTypeId,
			Integer subjectId, Integer examId, String subjectType,
			boolean abscentEntry, Integer answerScriptId) {

		int flag = 0;
		Integer marksEntryId = null;
		Integer detailId = null;
		ExamMarksEntryDetailsBO detBO;
		ExamMarksEntryBO masterBO;
		boolean theory_Practical = true;
		String marks = null;
		int mistake = 0, retest = 0;
		if (eTO.getMistake())
			mistake = 1;
		if (eTO.getRetest())
			retest = 1;
		ExamSecuredMarksEntryImpl impl = new ExamSecuredMarksEntryImpl();
		List<Object[]> markEntryList = impl.get_secured_mark_details(examId, subjectId, evaluatorTypeId,
				answerScriptId, get_Student_id_rollReg(getDecryptRegNo(eTO
						.getRegisterNo())), (retest ==1 ? true:false));
		if(markEntryList.size() > 0){
			Object[] obj = markEntryList.get(0);
			if(obj[7]!= null){
				eTO.setMarksEntryId(obj[7].toString());
			}
		}
		
		if (subjectType.equalsIgnoreCase("Theory"))
			marks = eTO.getTheoryMarks();
		else if (subjectType.equalsIgnoreCase("Practical")) {
			marks = eTO.getPracticalMarks();
		}
		if (null != marks && marks.trim().length() > 0) {
			if (!("null".equals(eTO.getMarksEntryId())
					|| "".equals(eTO.getMarksEntryId())
					|| "undefined".equals(eTO.getMarksEntryId()) || null == eTO
					.getMarksEntryId())) {
				marksEntryId = Integer.parseInt(eTO.getMarksEntryId());
			}

			if (!("null".equals(eTO.getDetailId())
					|| "".equals(eTO.getDetailId())
					|| "undefined".equals(eTO.getDetailId()) || null == eTO
					.getDetailId())) {
				detailId = Integer.parseInt(eTO.getDetailId());
			}
			if (eTO.getDetailId() != null
					&& !eTO.getDetailId().equalsIgnoreCase("null")
					&& !eTO.getDetailId().trim().equals("")
					&& !eTO.getDetailId().equalsIgnoreCase("undefined")) {
				detailId = Integer.parseInt(eTO.getDetailId());
			}
			boolean newMarksId = false;
			if (marksEntryId == null || marksEntryId < 1) {
				newMarksId = true;
				masterBO = new ExamMarksEntryBO();
				if (evaluatorTypeId != null && evaluatorTypeId == 0) {
					evaluatorTypeId = null;
				}
				masterBO.setEvaluatorTypeId(evaluatorTypeId);
				masterBO.setAnswerScriptTypeId(answerScriptId);
				masterBO
						.setStudentId(get_Student_id_rollReg(getDecryptRegNo(eTO
								.getRegisterNo())));
				masterBO.setExamId(examId);
				masterBO.setIsSecured(1);
				masterBO.setCreatedDate(new Date());
				masterBO.setLastModifiedDate(new Date());

				if (!eTO.getRetest()) {
					marksEntryId = impl.insert_returnId(masterBO);
				}

			}
			
			detBO = new ExamMarksEntryDetailsBO(marksEntryId, subjectId,
					mistake, retest);

			if (subjectType.equalsIgnoreCase("theory")) {
				detBO.setTheoryMarks(eTO.getTheoryMarks());
				detBO.setPreviousEvaluatorTheoryMarks(eTO.getTheoryMarks());
				detBO.setCreatedDate(new Date());
				detBO.setLastModifiedDate(new Date());
			}

			else {
				detBO.setPracticalMarks(eTO.getPracticalMarks());
				detBO.setPreviousEvaluatorPracticalMarks(eTO
						.getPracticalMarks());
				detBO.setCreatedDate(new Date());
				detBO.setLastModifiedDate(new Date());
				theory_Practical = false;
			}

			if (detailId != null && detailId > 0 && eTO.getRetest()) {
				impl.update_details(detailId, marks, theory_Practical, retest);
				flag = 1;

			} else {
				impl.insert(detBO);
				flag = 1;

			}
			/*
			 * if (newMarksId || detailId == null || detailId < 1) {
			 * impl.insert(detBO); flag = 1; } else {
			 * impl.update_details(detailId, marks, theory_Practical, retest);
			 * flag = 1;
			 * 
			 * }
			 */
		}
		return flag;
	}

	public ExamSecuredMarksEntryTO get_securedMarkDetails(Integer intExamId,
			Integer intSubjectId, Integer intEvaluatorId,
			Integer intAnswerScriptId, String rollReg, String subjectType, String isPreviousExam, String examType, String schemeNo)
			throws Exception {
		if (getSubj_for_stud(intSubjectId, rollReg, isPreviousExam, subjectType, examType, intExamId, schemeNo)) 
		{
			if (checkForAbscentEntry(intExamId, intSubjectId, subjectType,intAnswerScriptId, intEvaluatorId, rollReg, impl.getCodeForAbsentEntry(), impl.getCodeForNotprocessEntry())) 
			{
				return get_view_details(intExamId, intSubjectId,intEvaluatorId, intAnswerScriptId, rollReg, subjectType);

			}
			else 
			{
				return new ExamSecuredMarksEntryTO();
			}
		}
		return null;
	}

	// To validate students' subjects
	public boolean getSubj_for_stud(int subjectId, String rollRegNo, String isPreviousExam,
			String subjectType, String examType, int intExamId, String schemeNo) throws Exception {

		boolean rollOrReg = getsecured_marks_entry_by_settings();

		return impl.get_subjectsList(subjectId, rollRegNo, rollOrReg, isPreviousExam, subjectType, examType, intExamId, schemeNo);
	}

	// To get the rollNo/regNo from exam settings
	public boolean getsecured_marks_entry_by_settings() {

		return impl.getsecured_marks_type();
	}

	public ExamSecuredMarksEntryTO get_securedMarkDifference(String examType,
			Integer intExamId, Integer intSubjectId, Integer intEvaluatorId,
			Integer intAnswerScriptId, String rollReg, String subjectType,
			String marks, String isPreviousExam, String supSchemeNo) {

		ExamSecuredMarksEntryTO to = new ExamSecuredMarksEntryTO();
		boolean abscentEntry = checkForAbscentEntry(intExamId, intSubjectId,
				subjectType, intAnswerScriptId, intEvaluatorId, rollReg, impl
						.getCodeForAbsentEntry(), impl
						.getCodeForNotprocessEntry());

		if (abscentEntry)
			to.setRetest(true);
		else {
			to.setRetest(false);
		}

		if (!abscentEntry) {
			if (checkForDuplicateEntry(intExamId, intSubjectId, subjectType,
					intAnswerScriptId, intEvaluatorId, rollReg)) {
				to.setErrorType("Duplicate Entry");
				return to;
			}

		}
		BigDecimal maxMk = getMaxMarks(examType, intSubjectId,
				intAnswerScriptId, subjectType, rollReg, intExamId, isPreviousExam, supSchemeNo);
		if(maxMk!=null && maxMk.intValue()==0)
		{
			to.setErrorType("No Definition");
		}
		String splChar1 = null, splChar2 = null;
		int maxMark = 0;
		if (maxMk != null)
			maxMark = maxMk.intValue();

		if (marks != null && marks.trim().length() > 0) {
			splChar1 = impl.getCodeForAbsentEntry();
			splChar2 = impl.getCodeForNotprocessEntry();
			if (!(marks.equals(splChar1) || marks.equals(splChar2))) {

				if (splCharValidation(marks, splChar1)
						|| splCharValidation(marks, splChar2)) {
					to.setErrorType("Special Character");
					return to;
				} else if (maxMark != 0 && Integer.parseInt(marks) > maxMark) {
					to.setErrorType("Max Marks Not allowed");
					to.setMaxMarks(maxMark);
					return to;

				}
			}
		}

		ExamSecuredMarksEntryTO previousEvaluatorMarksTo = getMaxDiffereneceOfMarks(
				intExamId, intSubjectId, subjectType, intAnswerScriptId,
				intEvaluatorId, rollReg, marks);
		Double d = null, maxval = null;
		if (get_max_allowed_percentage() != null) {
			maxval = get_max_allowed_percentage().doubleValue();
		}

		String value = previousEvaluatorMarksTo.getPercentageDifference();
		if (value != null) {
			d = Double.valueOf(value);
			if (d >= maxval)
				to.setPreviousEvaluatorMarks(previousEvaluatorMarksTo
						.getPreviousEvaluatorMarks());
		}
		ExamSecuredMarksEntryTO newTo = get_securedMarkDetails(intExamId,
				intSubjectId, intEvaluatorId, intAnswerScriptId, rollReg, to.getRetest());
		if (newTo != null) {
			if (newTo.getMarksEntryId() != null) {
				to.setMarksEntryId(newTo.getMarksEntryId());
			} else {
				to.setMarksEntryId(null);
			}
			if (newTo.getDetailId() != null) {
				to.setDetailId(newTo.getDetailId());
			} else {
				to.setDetailId(null);
			}
		}

		return to;
	}

	// To get max allowed percentage from - Exam Settings
	public BigDecimal get_max_allowed_percentage() {

		return impl.get_max_allowed_percentage();
	}

	// To get studentId by roll/register no depending on exam - settings
	public Integer get_Student_id_rollReg(String rollRegNo) {

		boolean rollOrReg = getsecured_marks_entry_by_settings();

		return impl.select_Student_id_rollReg(rollRegNo, rollOrReg);
	}

	// Get percentage difference by comparing against EXAM - SETTINGS' max
	// allowed percentage
	private ExamSecuredMarksEntryTO getMaxDiffereneceOfMarks(Integer intExamId,
			Integer intSubjectId, String subjectType,
			Integer intAnswerScriptId, Integer intEvaluatorId,
			String rollRegNo, String marksEntered) {

		return helper.convertBoToForEvaluatorCalculation(impl
				.getPerecentage_Difference(intExamId, intSubjectId,
						subjectType, get_Student_id_rollReg(rollRegNo),
						marksEntered, intAnswerScriptId, intEvaluatorId));
	}

	// To FETCH Evaluator List
	public HashMap<Integer, String> getEvaluatorType(int subjectId,
			int subjectTypeId, Integer examId) {
		return helper.convertBOToTO_EvaluatorType(impl.getEvaluatorType(
				subjectId, subjectTypeId, examId));
	}

	// To FETCH Answer Script Type
	public HashMap<Integer, String> get_answerScript_type(int subjectId,
			int subjectTypeId, Integer examId) {
		return helper.convertBOToTO_EvaluatorType(impl.get_answerScript_type(
				subjectId, subjectTypeId, examId));
	}

	// TO GET THE GRID
	public ExamSecuredMarksEntryTO get_securedMarkDetails(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			String rollReg, boolean isRetest) {

		if (evaluatorId != null && evaluatorId == 0) {
			evaluatorId = null;
		}

		if (ansScriptId != null && ansScriptId == 0) {
			ansScriptId = null;
		}
		return helper.convertBOToTO_get_securedMarksDetails(impl
				.get_secured_mark_details(examId, subjectId, evaluatorId,
						ansScriptId, get_Student_id_rollReg(rollReg), isRetest));
	}

	// ON - VIEW - To Fetch Data
	public ExamSecuredMarksEntryTO get_view_details(int examId, int subjectId,
			Integer evaluatorId, Integer ansScriptId, String rollReg,
			String subjectType) throws Exception {

		if (ansScriptId != null && ansScriptId == 0) {
			ansScriptId = null;
		}
		if (evaluatorId != null && evaluatorId == 0) {
			evaluatorId = null;
		}

		return helper.convertBOToTO_get_view_details(impl.get_view_details(
				examId, subjectId, evaluatorId, ansScriptId,
				get_Student_id_rollReg(rollReg)), subjectType);
	}

	private boolean splCharValidation(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	// Retain values on error
	public ExamSecuredMarksEntryForm retainAllValues(
			ExamSecuredMarksEntryForm objform) {
		CommonAjaxHandler commHandler = new CommonAjaxHandler();
		objform.setSubjectList(getSubjectList());
		objform.setEvaluatorType(objform.getEvaluatorType());
		objform.setAnswerScriptType(objform.getAnswerScriptType());
		objform.setSubject(objform.getSubject());
		if (CommonUtil.checkForEmpty(objform.getExamType()))
			objform.setExamNameList(commHandler.getExamNameByExamType(objform
					.getExamType()));
		if (CommonUtil.checkForEmpty(objform.getSubjectType())) {
			String value = objform.getSubjectType();
			HashMap<String, String> subjectMap = new HashMap<String, String>();
			if (value.equalsIgnoreCase("T")) {
				subjectMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectMap.put("T", "Theory");
				subjectMap.put("P", "Practical");
			}
			objform.setSubjectTypeList(subjectMap);
		}
		ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
		if (CommonUtil.checkForEmpty(objform.getSubjectType())) {
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase(
					"t") ? 1 : 0;
			if (CommonUtil.checkForEmpty(objform.getSubject()) && CommonUtil.checkForEmpty(objform.getExamId())) {
				objform.setListEvaluatorType(entryHandler
						.getEvaluatorType(Integer
								.parseInt(objform.getSubject()),
								intSubjectTypeId, Integer.parseInt(objform
										.getExamId())));
				objform.setListAnswerScriptType(entryHandler
						.get_answerScript_type(Integer.parseInt(objform
								.getSubject()), intSubjectTypeId, Integer
								.parseInt(objform.getExamId())));
			}
		}
		objform.setExamName(objform.getExamName());
		return objform;
	}

	public ArrayList<KeyValueTO> getSubjectCodeName(String sCodeName, int examId) {
		ArrayList<SubjectUtilBO> listBO = impl
				.get_subjectListbyNameCode(sCodeName, examId);
		return helper.convertBOToTO_SubjectMap(listBO, sCodeName);
	}
	/**
	 * 
	 * @param examId
	 * @param subjectId
	 * @param subjectTypeId
	 * @param evaluatorId
	 * @param scriptId
	 * @return
	 */
	public int getRegisterNoCount(int examId, int subjectId, int subjectTypeId, int evaluatorId, int scriptId, boolean isTheory) {
		return  impl.getRegisterNoCount(examId, subjectId, subjectTypeId, evaluatorId, scriptId, isTheory);
	}
}
