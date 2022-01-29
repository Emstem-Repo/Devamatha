package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.helpers.exam.ExamValidationDetailsHelper;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamValidationDetailsTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValidationDetailsTxImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamValidationDetailsHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private static volatile ExamValidationDetailsHandler newSecuredMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(ExamValidationDetailsHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private ExamValidationDetailsHandler() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ExamValidationDetailsHandler getInstance() {
		if (newSecuredMarksEntryHandler == null) {
			newSecuredMarksEntryHandler = new ExamValidationDetailsHandler();
		}
		return newSecuredMarksEntryHandler;
	}
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveValidationDetails(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ExamValidationDetails  examValidationDetails = ExamValidationDetailsHelper.getInstance().convertFormTOBO(examValidationDetailsForm); 
		boolean save = transaction.saveDetails(examValidationDetails);
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
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ArrayList<SubjectUtilBO> listBO =transaction.getSubjectNames(sCode,subjectName,examId);
		ArrayList<KeyValueTO> subjectMap = ExamValidationDetailsHelper.getInstance().convertBOToTOSubjectMap(listBO, sCode);
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
	public List<ExamValidationDetailsTO> getExamValidationDetails(String currentExam, String year) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		List<ExamValidationDetails> boList = transaction.getExamValidationList(currentExam,year);
		List<ExamValidationDetailsTO> tos = ExamValidationDetailsHelper.getInstance().convertBOToTO(boList);
		return tos;
	}
	/**
	 * @param examValidationDetailsForm
	 * @throws Exception
	 */
	public void editValidationDetails(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ExamValidationDetails examValidationDetails = transaction.getDetailsForEdit(examValidationDetailsForm.getId());
		ExamValidationDetailsHelper.getInstance().convertBOToForm(examValidationDetailsForm,examValidationDetails);
	}
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteValidationDetails(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		boolean delete = transaction.deleteDetails(examValidationDetailsForm.getDeleteId());
		return delete;
	}
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateValidationDetails(	ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ExamValidationDetails examValidationDetails = ExamValidationDetailsHelper.getInstance().convertFormTOBO(examValidationDetailsForm);
		boolean update =  transaction.updateDetails(examValidationDetails);
		return update;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployeeList() throws Exception {
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getEmployeeMap();
		return map;
	}
	
	/**
	 * @param examType
	 * @param year 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamNameByExamType(String examType, String year) throws Exception {
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ArrayList<KeyValueTO> examByExamType = transaction.getExamByExamType(examType,year);
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByExamType) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 * code Changed by mehaboob
	 */
	public ExamValidationDetailsTO getAnswerScriptDetails(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		ExamValuationAnswerScript examValuationAnswerScript = transaction.getAnswerScriptDetails(examValidationDetailsForm);
		ExamValidationDetailsTO to =null;
		if(examValuationAnswerScript != null){
			    to=new ExamValidationDetailsTO();
				to.setAnswerScripts(String.valueOf(examValuationAnswerScript.getNumberOfAnswerScripts()));
			    examValidationDetailsForm.setId(examValuationAnswerScript.getId());
				if(examValuationAnswerScript.getIssueDate() != null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(examValuationAnswerScript.getIssueDate()), ExamValidationDetailsHandler.SQL_DATEFORMAT,	ExamValidationDetailsHandler.FROM_DATEFORMAT);
					to.setIssueDate(date);
					//examValidationDetailsForm.setIssueDate(date);
				}
				if(examValuationAnswerScript.getValidationDetailsId().getIsValuator()!=null &&!examValuationAnswerScript.getValidationDetailsId().getIsValuator().isEmpty()){
					to.setValuator(examValuationAnswerScript.getValidationDetailsId().getIsValuator());
				}
				if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null && examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee() != null && examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName() != null){
					examValidationDetailsForm.setValuatorName(examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName());
				}else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null && examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest() != null && !examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName().isEmpty()){
					examValidationDetailsForm.setValuatorName(examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName());
				}else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null && examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName() != null){
					examValidationDetailsForm.setValuatorName(examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName());
				}else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getOtherEmployee() != null){
					examValidationDetailsForm.setValuatorName(examValuationAnswerScript.getValidationDetailsId().getOtherEmployee());
				}
				if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getExamValuators() != null && examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName() != null){
					examValidationDetailsForm.setValuatorName(examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName());
				}
			    examValidationDetailsForm.setTotalAnswerScripts(String.valueOf(examValuationAnswerScript.getNumberOfAnswerScripts()));
		}
		return to;
	}
	/**
	 * @param subjectId 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getValuatorNameList(String valuatorName, String subjectId) throws Exception {
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getValuatorNameList(valuatorName,subjectId);
		return map;
	}
	/**
	 * @param subjectId 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOtherEmployeeList(String subjectId) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getOtherEmployeeMap(subjectId);
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
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		return transaction.getSubjectCodeName(academicYear,displaySubType,examId, examType);
	}
	/**
	 * @param subjectId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getValuatorNameListBySubjectDept(
			String subjectId) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getValuatorNameListBySubjectDept(subjectId);
		return map;
	}
	
	/**
	 * @param currentExam
	 * @param year
	 * @param subjectId
	 * @return
	 * @throws Exception
	 * code added by mehaboob to get examvalidtiondetails by subject
	 */
	public List<ExamValidationDetailsTO> getExamValidationDetailsByAjax(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		List<ExamValuationAnswerScript> boList = transaction.getExamValidationListBySubject(examValidationDetailsForm.getExamId(), examValidationDetailsForm.getSubjectId());
		return ExamValidationDetailsHelper.getInstance().convertBOToTOBySubject(boList,examValidationDetailsForm);
	}
	
	/**
	 * @param currentExam
	 * @param subjectId
	 * @return
	 * @throws Exception
	 * code added by mehaboob to get totalNumberof script and already issued scripts by subject
	 */
	public String numberOfAnswerScriptAndAlredyIssuedScript(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		  IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		  Long absent= transaction.getAbsentStudentIds(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getSubjectId(),examValidationDetailsForm.getExamType());
		  Long previoustClassStudent=0l;
		  if(!examValidationDetailsForm.getExamType().equalsIgnoreCase("Supplementary")){
		    previoustClassStudent= transaction.getStudentForPreviousClass(examValidationDetailsForm);
		  }
		  Long currentClassStudent= transaction.getStudentForCurrentClass(examValidationDetailsForm);
		  int totalNumberScript=0;
		  if(previoustClassStudent!=null && currentClassStudent!=null){
			  totalNumberScript=previoustClassStudent.intValue()+currentClassStudent.intValue();
		  }
		  totalNumberScript=totalNumberScript-absent.intValue();
		  Long alredyIssued=transaction.getNumberOfAlreadyIssuedScript(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getSubjectId());
		  String settingToResponse=null;
		  if(alredyIssued!=null){
			  settingToResponse=String.valueOf(totalNumberScript)+","+String.valueOf(alredyIssued.intValue());
			  if(examValidationDetailsForm.getUpdatedScripts()!=null && !examValidationDetailsForm.getUpdatedScripts().isEmpty()){
				  examValidationDetailsForm.setTotalAnswerScripts(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setIssuedAnswerScript(String.valueOf(alredyIssued.intValue()));
				  examValidationDetailsForm.setPendingAnswerScript(String.valueOf(totalNumberScript-alredyIssued.intValue()));
				  examValidationDetailsForm.setDisplayTotalScripts(true);
			  }
		  }else{
			  settingToResponse=String.valueOf(totalNumberScript)+","+"0";
			  if(examValidationDetailsForm.getUpdatedScripts()!=null && !examValidationDetailsForm.getUpdatedScripts().isEmpty()){
				  examValidationDetailsForm.setTotalAnswerScripts(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setIssuedAnswerScript("0");
				  examValidationDetailsForm.setPendingAnswerScript(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setDisplayTotalScripts(true);
			  }
		  }
		return settingToResponse;
		
	}
	public boolean updateTotalScriptSAndValuator(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		return transaction.updateNumberOfScriptsAndValuator(examValidationDetailsForm);
		 
	}
	
	
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public String numberOfAnswerScriptAndAlredyIssuedScriptForEvaluator2(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		  IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		  Long absent= transaction.getAbsentStudentIds(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getSubjectId(),examValidationDetailsForm.getExamType());
		  Long previoustClassStudent=0l;
		  if(!examValidationDetailsForm.getExamType().equalsIgnoreCase("Supplementary")){
		    previoustClassStudent= transaction.getStudentForPreviousClass(examValidationDetailsForm);
		  }
		  Long currentClassStudent= transaction.getStudentForCurrentClass(examValidationDetailsForm);
		  int totalNumberScript=0;
		  if(previoustClassStudent!=null && currentClassStudent!=null){
			  totalNumberScript=previoustClassStudent.intValue()+currentClassStudent.intValue();
		  }
		  totalNumberScript=totalNumberScript-absent.intValue();
		  Long alredyIssued=transaction.getNumberOfAlreadyIssuedScriptForEvl2(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getSubjectId());
		  String settingToResponse=null;
		  if(alredyIssued!=null){
			  settingToResponse=String.valueOf(totalNumberScript)+","+String.valueOf(alredyIssued.intValue());
			  if(examValidationDetailsForm.getUpdatedScripts()!=null && !examValidationDetailsForm.getUpdatedScripts().isEmpty()){
				  examValidationDetailsForm.setTotalAnswerScripts(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setIssuedAnswerScriptForEvl2(String.valueOf(alredyIssued.intValue()));
				  examValidationDetailsForm.setPendingAnswerScriptForEvl2(String.valueOf(totalNumberScript-alredyIssued.intValue()));
				  examValidationDetailsForm.setDisplayTotalScriptsForEvl2(true);
			  }
		  }else{
			  settingToResponse=String.valueOf(totalNumberScript)+","+"0";
			  if(examValidationDetailsForm.getUpdatedScripts()!=null && !examValidationDetailsForm.getUpdatedScripts().isEmpty()){
				  examValidationDetailsForm.setTotalAnswerScripts(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setIssuedAnswerScriptForEvl2("0");
				  examValidationDetailsForm.setPendingAnswerScriptForEvl2(String.valueOf(totalNumberScript));
				  examValidationDetailsForm.setDisplayTotalScriptsForEvl2(true);
			  }
		  }
		return settingToResponse;
		
	}
	
	
	/** method added  by chandra 
	 * @param subjectId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getValuatorNameListBySubjectDeptFromValuationScheduleDetails(
			String subjectId,int examId) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getValuatorListBySubjectDeptFromValuationScheduleDetails(subjectId,examId);
		return map;
	}
	
	/**method added by chandra
	 * @param subjectId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOtherEmployeeListFromValuationScheduleDetails(String subjectId,int examId) throws Exception{
		IExamValidationDetailsTransaction transaction = ExamValidationDetailsTxImpl.getInstance();
		Map<Integer, String> map =  transaction.getOtherEmployeeMapFromValuationScheduleDetails(subjectId,examId);
		return map;
	}


}
