package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamSecuredMarkVerificationBO;
import com.kp.cms.bo.exam.ExamSecuredMarksVerificationDetailsBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamSecuredMarksVerificationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamSecuredMarksVerificationHelper;
import com.kp.cms.helpers.exam.NewSecuredMarksEntryHelper;
import com.kp.cms.to.exam.ExamSecuredMarksVerificationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.transactionsimpl.exam.ExamSecuredMarksVerificationImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewStudentMarksCorrectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamSecuredMarksVerificationHandler extends ExamGenHandler {

	ExamSecuredMarksVerificationImpl impl = new ExamSecuredMarksVerificationImpl();
	ExamSecuredMarksVerificationHelper helper = new ExamSecuredMarksVerificationHelper();

	public ArrayList<KeyValueTO> getSubjectList() {
		ArrayList<SubjectUtilBO> listBO = impl.get_subjectList();
		return helper.convertBOToTO_Subject_KeyVal(listBO);
	}

	public String getSubjectCode(int subjectId) {
		return ((SubjectUtilBO) impl.select_Unique(subjectId,
				SubjectUtilBO.class)).getCode();
	}

	public String getDecryptRegNo(String encryptedRegNo) {
		try {
			return ExamRegDecrypt.decrypt(encryptedRegNo);
		} catch (Exception e) {
			return "";
		}
	}

	// To get subject type by subjectId
	public String getSubjectsTypeBySubjectId(Integer subjectId) {
		String value = impl.selectSubjectsTypeBySubjectId(subjectId);
		return value.toLowerCase();
	}

	// To get the rollNo/regNo from exam settings
	public boolean getsecured_marks_entry_by_settings() {

		return impl.getsecured_marks_type();
	}

	public ExamSecuredMarksVerificationForm retainAllValues(
			ExamSecuredMarksVerificationForm objform) throws Exception{
		CommonAjaxHandler commHandler = new CommonAjaxHandler();
		if (objform.getCheckBox() != null) {
			objform.setDummyCheckBox(true);
			objform.setCheckBox(null);
		} else {
			objform.setDummyCheckBox(false);
		}
		if(objform.getExamId()!=null && !objform.getExamId().isEmpty()){
		objform.setSubjectList(commHandler.getSubjectCodeName(objform.getsCodeName(),Integer.parseInt(objform.getExamId())));
		objform.setEvaluatorType(objform.getEvaluatorType());
		objform.setAnswerScriptType(objform.getAnswerScriptType());
		objform.setSubject(objform.getSubject());
		// changed as academic year parameter newly added -Smitha
		if (CommonUtil.checkForEmpty(objform.getExamType()) && CommonUtil.checkForEmpty(objform.getYear()))
			objform.setExamNameList(commHandler.getExamNameByExamTypeAndYear(objform.getExamType(),Integer.parseInt(objform.getYear())));
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
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase("t") ? 1 : 0;
			if (CommonUtil.checkForEmpty(objform.getSubject())) 
			{
				objform.setListEvaluatorType(entryHandler.getEvaluatorType(Integer.parseInt(objform.getSubject()),intSubjectTypeId, Integer.parseInt(objform.getExamId())));
				objform.setListAnswerScriptType(entryHandler.get_answerScript_type(Integer.parseInt(objform.getSubject()), intSubjectTypeId, Integer.parseInt(objform.getExamId())));
			}
		}
		objform.setExamName(/*objform.getExamName()*/objform.getExamId());
		}
		return objform;
	}

	// To validate students' subjects
	public boolean getSubj_for_stud(int subjectId, String rollRegNo) {

		boolean rollOrReg = getsecured_marks_entry_by_settings();

		return impl.get_subjectsList(subjectId, rollRegNo, rollOrReg);
	}

	public ArrayList<ExamSecuredMarksVerificationTO> getSingleStudentMarks() {

		ArrayList<ExamSecuredMarksVerificationTO> list = new ArrayList<ExamSecuredMarksVerificationTO>();
		for (int i = 1; i <= 10; i++) {

			list.add(new ExamSecuredMarksVerificationTO());
		}
		return list;
	}

	// On SUBMIT - To Add
	public int addChanges(ExamSecuredMarksVerificationForm objform) {
		ArrayList<ExamSecuredMarksVerificationTO> singleStudentMarks = objform
				.getListSingleStudents();
		String secure = objform.getBoxCheck();
		int flag = 0;
		for (ExamSecuredMarksVerificationTO to : singleStudentMarks) {
			if (secure != null && secure.equalsIgnoreCase("true")) {
				to.setRegNo(getDecryptRegNo(to.getRegNo()));
				to.setSecured(true);
			}
			if (to.getMarksEntryId() == 0) {
				to.setMarksEntryId(null);
			}
			if (to.getDetailId() == 0) {
				to.setDetailId(null);
			}

			if (objform.getSubject() != null
					&& objform.getSubject().trim().length() > 0)
				to.setSubjectId(Integer.parseInt(objform.getSubject()));
			if (objform.getExamId() != null
					&& objform.getExamId().trim().length() > 0)
				to.setExamId(Integer.parseInt(objform.getExamId()));
			if (objform.getEvaluatorType() != null && objform.getEvaluatorType().trim().length() > 0)
				to.setEvaluatorType(Integer.parseInt(objform.getEvaluatorType()));
			if (objform.getAnswerScriptType() != null && objform.getAnswerScriptType().trim().length() > 0)
				to.setAnswerScriptId(Integer.parseInt(objform.getAnswerScriptType()));
			flag = add(to);
		}
		return flag;
	}

	// To get the mark details(GRID)
	public ExamSecuredMarksVerificationTO get_securedMarkDetails(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			String rollReg) {
		return helper.convertBOToTO_get_securedMarksDetails(impl
				.select_secured_markdet_stu(examId, subjectId, evaluatorId,
						ansScriptId, get_Student_id_rollReg(rollReg)),
				getSubjectsTypeBySubjectId(subjectId));
	}

	// On SUBMIT - To Add
	public int add(ExamSecuredMarksVerificationTO to) {

		ExamSecuredMarkVerificationBO objBO;
		int addedFlag = 0;
		boolean subject_validate_for_student = getSubj_for_stud(to
				.getSubjectId(), to.getRegNo());
		if (subject_validate_for_student == true) {
			int securedId = impl.isDuplicated_examSecuredMarksVerification(to
					.getExamId(), to.getSubjectId(), to.getEvaluatorType(), to
					.getAnswerScriptId());
			if (to.getRegNo() != null) {

				int mistake = 0;
				int retest = 0;
				int secured = 0;
				String packetNo=null;

				if (to.getMistake() == false) {
					mistake = 0;
				} else {
					mistake = 1;
				}
				if (to.getRetest() == false) {
					retest = 0;
				} else {
					retest = 1;
				}
				if (to.getSecured() == false) {
					secured = 0;
				} else {
					secured = 1;
				}
				if(to.getPacketNo()!=null && !to.getPacketNo().isEmpty()){
					packetNo=to.getPacketNo();
				}

				String theory = null;
				String practical = null;

				if (to.getTheoryMarks() != null
						&& to.getTheoryMarks().trim().length() > 0) {
					theory = (String) to.getTheoryMarks();
				}
				if (to.getPracticalMarks() != null
						&& to.getPracticalMarks().trim().length() > 0) {
					practical = (String) to.getPracticalMarks();
				}

				// entry id = null & detail id = null
				if (to.getMarksEntryId() == null && to.getDetailId() == null) {
					int flag = 0;

					objBO = new ExamSecuredMarkVerificationBO(to.getExamId(),
							to.getSubjectId(), to.getEvaluatorType(), to
									.getAnswerScriptId(), secured);

					if (securedId > 0) {
						flag = 1;
					}
					if (flag != 1) {
						securedId = impl.insert_returnId(objBO);
					}

					Integer stuid = get_Student_id_rollReg(to.getRegNo());

					if (!impl.isDuplicated_examSecuredMarksVerificationDetails(
							to.getMarksEntryId(), get_Student_id_rollReg(to
									.getRegNo()))) {
						// Integer stuid=get_Student_id_rollReg(to.getRegNo());

						impl.insert(new ExamSecuredMarksVerificationDetailsBO(
								securedId, stuid, theory, practical, mistake,
								retest));
					}

				}

				// entry id != null & detail id = null
				else if (to.getMarksEntryId() != null
						&& to.getDetailId() == null) {

					if (!impl.isDuplicated_examSecuredMarksVerificationDetails(
							to.getMarksEntryId(), get_Student_id_rollReg(to
									.getRegNo()))) {

						objBO = new ExamSecuredMarkVerificationBO(to
								.getExamId(), to.getSubjectId(), to
								.getEvaluatorType(), to.getAnswerScriptId(),
								secured);

						if (securedId < 0) {

							addedFlag = impl
									.insert(new ExamSecuredMarksVerificationDetailsBO(
											securedId,
											get_Student_id_rollReg(to
													.getRegNo()), theory,
											practical, mistake, retest));

						} else {

							Integer smeid = null;

							smeid = impl.get_smeid(to.getExamId(), to
									.getSubjectId(), to.getEvaluatorType(), to
									.getAnswerScriptId());

							addedFlag = impl
									.insert(new ExamSecuredMarksVerificationDetailsBO(
											smeid, get_Student_id_rollReg(to
													.getRegNo()), theory,
											practical, mistake, retest));

						}
					}

				}
				// entry id & detail id != null
				else if (to.getMarksEntryId() != null
						&& to.getDetailId() != null) {

					addedFlag = impl.update_mistake_retest(to.getDetailId(),
							mistake, retest);

				}
			}
		}
		return addedFlag;
	}

	// To get studentId by roll/register no depending on exam - settings
	public Integer get_Student_id_rollReg(String rollRegNo) {

		boolean rollOrReg = getsecured_marks_entry_by_settings();

		return impl.select_Student_id_rollReg(rollRegNo, rollOrReg);
	}

	public boolean getEvaluatorType_Status(String subjectId,
			String subjectTypeId, String examId) {
		ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
		int intSubjectTypeId = subjectTypeId.equalsIgnoreCase("t") ? 1 : 0;
		HashMap<Integer, String> evalList = null;
		if (CommonUtil.checkForEmpty(subjectId)) {
			evalList = entryHandler.getEvaluatorType(Integer
					.parseInt(subjectId), intSubjectTypeId, Integer
					.parseInt(examId));
		}
		if (evalList != null && evalList.size() > 0)
			return true;
		return false;

	}

	// To FETCH answer script list
	public HashMap<Integer, String> getListanswerScriptType(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId) {

		List<Object> listBO = null;

		Integer validate = get_answerScriptFrom_ruleSettings(courseId,
				schemeNo, subjectId, subjectTypeId);

		if (validate != null && validate > 0) {

			listBO = impl
					.select_ActiveOnly(ExamMultipleAnswerScriptMasterBO.class);

		} else {
			return new HashMap<Integer, String>();
		}
		return helper.convertBOToTO_Mul_Ans_Script_KeyVal(listBO);
	}

	// Validation for multiple answer scripts
	public Integer get_answerScriptFrom_ruleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId) {

		return impl.select_answerScriptFrom_ruleSettings(courseId, schemeNo,
				subjectId, subjectTypeId);
	}

	public boolean getAnswerScriptType_Status(String subjectId,
			String subjectTypeId, String examId) {

		ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
		int intSubjectTypeId = subjectTypeId.equalsIgnoreCase("t") ? 1 : 0;
		HashMap<Integer, String> answerScriptList = null;
		if (CommonUtil.checkForEmpty(subjectId)) {
			answerScriptList = entryHandler.get_answerScript_type(Integer
					.parseInt(subjectId), intSubjectTypeId, Integer
					.parseInt(examId));
		}
		if (answerScriptList != null && answerScriptList.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public String addChangesByPrinter(ExamSecuredMarksVerificationForm objform) throws Exception {
		ArrayList<ExamSecuredMarksVerificationTO> singleStudentMarks = objform
				.getListSingleStudents();
		String flag = "";
		int schemeNo=0;
		if(objform.getSchemeNo()!=null && !objform.getSchemeNo().trim().isEmpty() && objform.getSchemeNo().trim().length()>0 && StringUtils.isNumeric(objform.getSchemeNo()))
			schemeNo=Integer.parseInt(objform.getSchemeNo());
		Integer vmarksEntryId = null;
		ExamMarksVerificationEntryBO marksVerificationEntryBO = new ExamMarksVerificationEntryBO();
		
		
		marksVerificationEntryBO.setCreatedDate(new Date());
		if(objform.getExamId() != null){
			marksVerificationEntryBO.setExamId(Integer.parseInt(objform.getExamId()));
		}
		if(objform.getSubject() != null){
			marksVerificationEntryBO.setSubjectId(Integer.parseInt(objform.getSubject()));
		}
		if(objform.getUserId() != null && objform.getUserId().trim().length() > 0){
			marksVerificationEntryBO.setUserId(Integer.parseInt(objform.getUserId().trim()));
			
		}
		
		if(objform.getEvaluatorType() != null && objform.getEvaluatorType().trim().length() > 0){
			marksVerificationEntryBO.setEvaluatorTypeId(Integer.parseInt(objform.getEvaluatorType()));
		}
		if(objform.getAnswerScriptType() != null && objform.getAnswerScriptType().trim().length() > 0) {
			marksVerificationEntryBO.setAnswerScriptTypeId(Integer.parseInt(objform.getAnswerScriptType()));
		}
		if(objform.getPacketNo() != null && !objform.getPacketNo().isEmpty()) {
			marksVerificationEntryBO.setPacketNo(objform.getPacketNo());
		}
		for (ExamSecuredMarksVerificationTO to : singleStudentMarks) {
			if((to.getTheoryMarks()) != null && to.getTheoryMarks().trim().length() > 0) {
				marksVerificationEntryBO.setStudentId(getStudentIdByReg(to.getRegNo(),schemeNo));
				marksVerificationEntryBO.setVmarks(to.getTheoryMarks());
				vmarksEntryId = impl.insert_returnId1(marksVerificationEntryBO);
				if(vmarksEntryId == 0){
					if(flag.isEmpty()){
						flag = to.getRegNo();
					}else{
						flag = flag + ", "+to.getRegNo();
					}
				}
			}
		}
		return flag;
	}


	/**
	 * @param regNo
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	private Integer getStudentIdByReg(String regNo, int schemeNo) throws Exception {
		if(schemeNo>0){
			INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
			Integer studentId = transaction1.getStudentId(regNo, String.valueOf(schemeNo) ,null);
			if(studentId==null){
				studentId=0;
			}
			return studentId;
		}else
		return get_Student_id_rollReg(regNo);
	}

	// To get the mark details(GRID)
	public ExamSecuredMarksVerificationTO get_securedMarkDetails1(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			String rollReg ,String examType,int schemeNo, String subjectType) throws Exception {
		if(schemeNo==0){
		return helper.convertBOToTO_get_securedMarksDetails1(impl
				.select_secured_markdet_stu1(examId, subjectId, evaluatorId,
						ansScriptId, get_Student_id_rollReg(rollReg),subjectType),
				getSubjectsTypeBySubjectId(subjectId));
		}else{
			INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
			Integer studentId = transaction1.getStudentId(rollReg, String.valueOf(schemeNo) ,null);
			if(studentId==null){
				studentId=0;
			}
			return helper.convertBOToTO_get_securedMarksDetails1(impl
					.select_secured_markdet_stu1(examId, subjectId, evaluatorId,
							ansScriptId,studentId,subjectType),
							getSubjectsTypeBySubjectId(subjectId));
			
		}
	}
	public ExamSecuredMarksVerificationTO get_securedMarkDetails3(String rollReg,int schemeNo) throws Exception {
		if(schemeNo>0)
			return helper.convertBOToTO_get_securedMarksDetails3(impl .select_secured_markdet_stu3(getStudentIdByReg(rollReg, schemeNo)));
		else
			return helper.convertBOToTO_get_securedMarksDetails3(impl .select_secured_markdet_stu3(get_Student_id_rollReg(rollReg)));
	}
	/**
	 * @param examId
	 * @param subjectId
	 * @param evaluatorId
	 * @param ansScriptId
	 * @param rollReg
	 * @return
	 */
	public ExamSecuredMarksVerificationTO get_securedMarkDetails2(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			String rollReg) {
		return helper.convertBOToTO_get_securedMarksDetails2(
				impl.checkStudentId(examId, subjectId, evaluatorId,
						ansScriptId, get_Student_id_rollReg(rollReg)));
	}
	/**
	 * @param examId
	 * @param subjectId
	 * @param evaluatorId
	 * @param ansScriptId
	 * @param rollReg
	 * @param examType
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public ExamSecuredMarksVerificationTO getSecuredMarkDetails(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			String rollReg,String examType,int schemeNo) throws Exception {
		if(schemeNo>0){
		return helper.convertBOToTO_get_securedMarksDetails2(
				impl.checkStudentId(examId, subjectId, evaluatorId,
						ansScriptId, get_Student_id_rollReg(rollReg)));
		}else{
			INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
			Integer studentId = transaction1.getStudentId(rollReg, String.valueOf(schemeNo) ,null);
			if(studentId==null){
				studentId=0;
			}
			return helper.convertBOToTO_get_securedMarksDetails2(
					impl.checkStudentId(examId, subjectId, evaluatorId,
							ansScriptId, studentId));
			
		}
	}

	/**
	 * @param objform
	 * @param regNo 
	 * @return
	 * @throws Exception
	 */
	public Double getMaxMarkOfSubject(ExamSecuredMarksVerificationForm objform, String regNo) throws Exception{
		return impl.getMaxMarkOfSubject(objform,regNo);
	}

	/**
	 * @param objform
	 * @throws Exception
	 */
	public void getEvaMap(ExamSecuredMarksVerificationForm objform) throws Exception{
		
			String evalMarksQuery=helper.getQueryForEvaluatorEnteredMarks(objform);
			List evalMarksList=NewExamMarksEntryTransactionImpl.getInstance().getDataForQuery(evalMarksQuery);// calling the method for checking data is there for the marksQuery
			Map<Integer,Map<Integer,String>> evaMap=helper.convertBotoEvaMap(evalMarksList,objform.getSubjectType());
			objform.setEvaMap(evaMap);
	}
	
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public String getclassNameByClassId(int classId)throws Exception{
		return impl.getClassNameByClassId(classId);
	}

}