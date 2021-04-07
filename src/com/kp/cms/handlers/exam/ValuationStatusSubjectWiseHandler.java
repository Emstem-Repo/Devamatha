package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuationStatusSubjectWiseForm;
import com.kp.cms.helpers.exam.ValuationStatusSubjectWiseHelper;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ValuationStatusSubjectWiseTO;
import com.kp.cms.transactions.exam.IValuationStatusSubjectWiseTransaction;
import com.kp.cms.transactionsimpl.exam.ValuationStatusSubjectWiseImpl;

public class ValuationStatusSubjectWiseHandler {
	private static volatile ValuationStatusSubjectWiseHandler handler = null;
	private static final Log log = LogFactory.getLog(ValuationStatusSubjectWiseHandler.class);
	/**
	 * return singleton object of ValuationStatusSubjectWiseHandler.
	 * @return
	 */
	public static ValuationStatusSubjectWiseHandler getInstance(){
		if(handler == null){
			handler = new ValuationStatusSubjectWiseHandler();
			return handler;
		}
		return handler;
	}
	IValuationStatusSubjectWiseTransaction impl = ValuationStatusSubjectWiseImpl.getInstance();
	
	
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @throws Exception
	 */
	public void getValuationStatusSubjectWise( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm)throws Exception {
		String absentStudentQuery = ValuationStatusSubjectWiseHelper.getInstance().getQueryForAbsentStudents(valuationStatusSubjectWiseForm);
		List<Integer> absentStudentIds = impl.getAbsentStudentIds(absentStudentQuery);
		int totalAnswerScripts = 0;
		if(!valuationStatusSubjectWiseForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(valuationStatusSubjectWiseForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery = ValuationStatusSubjectWiseHelper.getInstance().getQueryForCurrentClassStudents(valuationStatusSubjectWiseForm);
				List<Integer> currentStudentIdsList=impl.getDataForQuery(currentStudentQuery);// calling the method for getting current class students
				if(currentStudentIdsList!=null && !currentStudentIdsList.isEmpty()){
					totalAnswerScripts = ValuationStatusSubjectWiseHelper.getInstance().getTotalAnswerScripts(absentStudentIds,currentStudentIdsList);
				}
			}else{
				String previousStudentQuery = ValuationStatusSubjectWiseHelper.getInstance().getQueryForPreviousClassStudents(valuationStatusSubjectWiseForm);
				List<Integer> previousStudentList=impl.getDataForQuery(previousStudentQuery);// calling the method for getting current class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					totalAnswerScripts = ValuationStatusSubjectWiseHelper.getInstance().getTotalAnswerScripts(absentStudentIds,previousStudentList);
				}
			}
		}else{
			if(valuationStatusSubjectWiseForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String supCurrentStudentQuery = ValuationStatusSubjectWiseHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(valuationStatusSubjectWiseForm);
				List<Integer> supCurrentStudentIds = impl.getDataForQuery(supCurrentStudentQuery);
				if(supCurrentStudentIds!=null && !supCurrentStudentIds.isEmpty()){
					totalAnswerScripts = ValuationStatusSubjectWiseHelper.getInstance().getTotalAnswerScripts(absentStudentIds,supCurrentStudentIds);
				}
			}else{
				String supPreviousStudentQuery = ValuationStatusSubjectWiseHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(valuationStatusSubjectWiseForm);
				List<Integer> supPreviousStudentIds = impl.getDataForQuery(supPreviousStudentQuery);
				if(supPreviousStudentIds!=null && !supPreviousStudentIds.isEmpty()){
					totalAnswerScripts = ValuationStatusSubjectWiseHelper.getInstance().getTotalAnswerScripts(absentStudentIds,supPreviousStudentIds);
				}
			}
		}
		String valuationDetailsQuery = ValuationStatusSubjectWiseHelper.getInstance().getValuationDetailsQuery(valuationStatusSubjectWiseForm);
		List<ExamValidationDetails> valuationDetailsList = impl.getvaluationDetailsData(valuationDetailsQuery);
		Map<String,List<ExamValidationDetailsTO>> valuationMap = ValuationStatusSubjectWiseHelper.getInstance().convertValuationDetailsTOMap(valuationDetailsList,totalAnswerScripts,valuationStatusSubjectWiseForm);
		/*if(valuationMap!=null && !valuationMap.isEmpty()){
			valuationStatusSubjectWiseForm.setValuationMap(valuationMap);
		}else{
			valuationStatusSubjectWiseForm.setTotalAnswerScripts(totalAnswerScripts);
			valuationStatusSubjectWiseForm.setValuationMap(valuationMap);
			throw new ApplicationException();
		}*/
		if(totalAnswerScripts!=0){
			valuationStatusSubjectWiseForm.setTotalAnswerScripts(totalAnswerScripts);
			valuationStatusSubjectWiseForm.setValuationMap(valuationMap);
		}else{
			valuationStatusSubjectWiseForm.setTotalAnswerScripts(totalAnswerScripts);
			valuationStatusSubjectWiseForm.setValuationMap(valuationMap);
			throw new ApplicationException();
		}
	}
}
