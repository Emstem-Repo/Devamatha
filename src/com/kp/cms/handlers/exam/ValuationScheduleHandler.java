package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ValuationScheduleForm;
import com.kp.cms.helpers.exam.ValuationScheduleHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.ValuationScheduleTO;
import com.kp.cms.transactions.exam.IValuationScheduleTransaction;
import com.kp.cms.transactionsimpl.exam.ValuationScheduleTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ValuationScheduleHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private volatile static ValuationScheduleHandler newSecuredMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(ValuationScheduleHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	private ValuationScheduleHandler() {
	
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationScheduleHandler getInstance() {
		if (newSecuredMarksEntryHandler == null) {
			newSecuredMarksEntryHandler = new ValuationScheduleHandler();
		}
		return newSecuredMarksEntryHandler;
	}
	
	/**
	 * @param examType
	 * @param year 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamNameByExamType(String examType, String year) throws Exception {
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		ArrayList<KeyValueTO> examByExamType = transaction.getExamByExamType(examType,year);
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByExamType) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	/**
	 * @param academicYear
	 * @param displaySubType
	 * @param examId
	 * @param examType 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getSubjectCodeName(String academicYear,String displaySubType, int examId, String examType) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		return transaction.getSubjectCodeName(academicYear,displaySubType,examId, examType);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployeeList() throws Exception {
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getEmployeeMap();
		return map;
	}
	/**
	 * @param subjectId 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOtherEmployeeList() throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getOtherEmployeeMap();
		return map;
	}
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveValuationSchedule(ValuationScheduleForm valuationScheduleForm) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		List<ExamValuationScheduleDetails>  scheduleDetails = ValuationScheduleHelper.getInstance().convertFormTOBO(valuationScheduleForm); 
		boolean save = transaction.saveDetails(scheduleDetails);
		return save;
	}
	/**
	 * @param subjectName
	 * @param sCode
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<KeyValueTO> getSortedSubjectList(String subjectName, String sCode, int examId) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		ArrayList<SubjectUtilBO> listBO =transaction.getSubjectNames(sCode,subjectName,examId);
		ArrayList<KeyValueTO> subjectMap = ValuationScheduleHelper.getInstance().convertBOToTOSubjectMap(listBO, sCode);
		Collections.sort(subjectMap,new KeyValueTOComparator());
		return subjectMap;
	}
	
	/**
	 * @param currentExam
	 * @param year 
	 * @return
	 * 
	 * @throws Exception
	 */
	public List<ValuationScheduleTO> getValuationScheduleDetails(String currentExam, String year) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		List<ExamValuationScheduleDetails> boList = transaction.getValuationSchedule(currentExam,year);
		List<ValuationScheduleTO> tos = ValuationScheduleHelper.getInstance().convertBOToTO(boList);
		return tos;
	}
	
	/**
	 * @param valuationScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteValuationSchedule(ValuationScheduleForm scheduleForm) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		boolean delete = transaction.deleteDetails(scheduleForm.getId());
		return delete;
	}
	
	/**
	 * Gets a row based on the Id
	 */
	public void getDetailsonId(ValuationScheduleForm scheduleForm) throws Exception{
		log.info("Inside into getDetailsonId of ValuationScheduleHandler");
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		ExamValuationScheduleDetails scheduleDetails=transaction.getDetailsonId(scheduleForm.getId());
		ValuationScheduleHelper.getInstance().setBotoForm(scheduleForm, scheduleDetails);
	}
	
	public boolean updateValuationSchedule(ValuationScheduleForm valuationScheduleForm) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		List<ExamValuationScheduleDetails>  scheduleDetails = ValuationScheduleHelper.getInstance().convertFormTOBOUpdate(valuationScheduleForm); 
		boolean update = transaction.updateDetails(scheduleDetails);
		return update;
	}
	
	
	public ExamValuationScheduleDetails checkForDuplicate(ValuationScheduleForm scheduleForm)throws Exception
	{
		log.info("Inside into checkForDuplicate of ValuationScheduleHandler");
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		return transaction.checkForDuplicate(scheduleForm);
		
	}
	
	public List<ValuationScheduleTO> getExamNameListSchedule(String currentExam, String year) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		List<ExamValuationScheduleDetails> boList = transaction.getValuationScheduleList(currentExam,year);
		List<ValuationScheduleTO> tos = ValuationScheduleHelper.getInstance().convertBOToTO(boList);
		return tos;
	}
	/**
	 * @param subjectId 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOtherEmployeeList(String subjectId) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getOtherEmployeeMap(subjectId);
		return map;
	}
	/**
	 * @param subjectId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getValuatorNameListBySubjectDept(
			String subjectId) throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getValuatorNameListBySubjectDept(subjectId);
		return map;
	}
	/**
	 * @param subjectId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getValuatorsAllList() throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getValuatorsAllList();
		return map;
	}
	public Map<Integer, String> getOtherEmployeeAllList() throws Exception{
		IValuationScheduleTransaction transaction = ValuationScheduleTransactionImpl.getInstance();
		Map<Integer, String> map =  transaction.getOtherEmployeeAllMap();
		return map;
	}

}
