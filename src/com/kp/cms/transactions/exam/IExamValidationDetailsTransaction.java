package com.kp.cms.transactions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.to.exam.KeyValueTO;


public interface IExamValidationDetailsTransaction {

	boolean saveDetails(ExamValidationDetails examValidationDetails) throws Exception;

	ArrayList<SubjectUtilBO> getSubjectNames(String sCode, String subjectName, int examId) throws Exception;

	List<ExamValidationDetails> getExamValidationList(String currentExam, String year) throws Exception;

	ExamValidationDetails getDetailsForEdit(int id) throws Exception;

	boolean deleteDetails(int id) throws Exception;

	boolean updateDetails(ExamValidationDetails examValidationDetails) throws Exception;

	Map<Integer, String> getEmployeeMap() throws Exception;
	
	public ArrayList<KeyValueTO> getExamByExamType(String examTypeName, String year) throws Exception;
 
	//code changed by mahi
	ExamValuationAnswerScript getAnswerScriptDetails(ExamValidationDetailsForm examValidationDetailsForm) throws Exception;

	Map<Integer, String> getValuatorNameList(String valuatorName, String subjectId)throws Exception;

	Map<Integer, String> getOtherEmployeeMap(String subjectId) throws Exception;

	Map<Integer, String> getSubjectCodeName(String academicYear,String displaySubType, int examId, String examType) throws Exception;

	Map<Integer, String> getValuatorNameListBySubjectDept(String subjectId) throws Exception;
	
	List<ExamValuationAnswerScript> getExamValidationListBySubject(String currentExam,String subjectId) throws Exception;
	
	public Long getAbsentStudentIds(String currentExam,String subjectId,String examType)throws Exception;
	
	public Long getStudentForPreviousClass(ExamValidationDetailsForm examValidationDetailsForm)throws Exception;
	
	public Long getNumberOfAlreadyIssuedScript(String currentExam,String subjectId)throws Exception;
	
	public Long getStudentForCurrentClass(ExamValidationDetailsForm examValidationDetailsForm)throws Exception;
	
	public boolean updateNumberOfScriptsAndValuator(ExamValidationDetailsForm examValidationDetailsForm)throws Exception;
	
	public Long getNumberOfAlreadyIssuedScriptForEvl2(String currentExam,String subjectId)throws Exception;
	
	Map<Integer, String> getValuatorListBySubjectDeptFromValuationScheduleDetails(String subjectId,int examId) throws Exception;
	
	Map<Integer, String> getOtherEmployeeMapFromValuationScheduleDetails(String subjectId,int examId) throws Exception;
}
