package com.kp.cms.transactions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ValuationScheduleForm;
import com.kp.cms.to.exam.KeyValueTO;

public interface IValuationScheduleTransaction {
	
	public ArrayList<KeyValueTO> getExamByExamType(String examTypeName, String year) throws Exception;
	
	Map<Integer, String> getSubjectCodeName(String academicYear,String displaySubType, int examId, String examType) throws Exception;
	
	Map<Integer, String> getEmployeeMap() throws Exception;
	
	Map<Integer, String> getOtherEmployeeMap() throws Exception;
	
	boolean saveDetails(List<ExamValuationScheduleDetails> scheduleDetails) throws Exception;
	
	ArrayList<SubjectUtilBO> getSubjectNames(String sCode, String subjectName, int examId) throws Exception;
	
	ExamValuationScheduleDetails getDetailsForEdit(int id) throws Exception;
	
	boolean deleteDetails(int id) throws Exception;
	
	Map<Integer, String> getValuatorNameList(String valuatorName, String subjectId)throws Exception;

	public List<ExamValuationScheduleDetails> getValuationSchedule(String currentExam, String year);
	
	public ExamValuationScheduleDetails getDetailsonId(int id)throws Exception ;
	
	boolean updateDetails(List<ExamValuationScheduleDetails> scheduleDetails) throws Exception;
	
	public ExamValuationScheduleDetails checkForDuplicate(ValuationScheduleForm valuationScheduleForm)throws Exception;

	List<ExamValuationScheduleDetails> getValuationScheduleList(String currentExam, String year) throws Exception;

	public Map<Integer, String> getOtherEmployeeMap(String subjectId) throws Exception;

	public Map<Integer, String> getValuatorNameListBySubjectDept(String subjectId) throws Exception;

	public Map<Integer, String> getValuatorsAllList() throws Exception;

	public Map<Integer, String> getOtherEmployeeAllMap() throws Exception;
	
}
