package com.kp.cms.handlers.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm;
import com.kp.cms.helpers.exam.NewInternalMarksSupplementaryHelper;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.transactions.exam.INewInternalMarksSupplementaryTransaction;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.transactionsimpl.exam.NewInternalMarksSupplementaryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewStudentMarksCorrectionTransactionImpl;

public class NewInternalMarksSupplementaryHandler {
	/**
	 * Singleton object of NewInternalMarksSupplementaryHandler
	 */
	private static volatile NewInternalMarksSupplementaryHandler newInternalMarksSupplementaryHandler = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksSupplementaryHandler.class);
	private NewInternalMarksSupplementaryHandler() {
		
	}
	/**
	 * return singleton object of NewInternalMarksSupplementaryHandler.
	 * @return
	 */
	public static NewInternalMarksSupplementaryHandler getInstance() {
		if (newInternalMarksSupplementaryHandler == null) {
			newInternalMarksSupplementaryHandler = new NewInternalMarksSupplementaryHandler();
		}
		return newInternalMarksSupplementaryHandler;
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamInternalMarksSupplementaryTO> getStudentMarks(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		INewInternalMarksSupplementaryTransaction transaction=NewInternalMarksSupplementaryTransactionImpl.getInstance();
		//Getting Internal Marks for The Selected Exam
		String intQuery=NewInternalMarksSupplementaryHelper.getInstance().getInternalSupplementaryQuery(newInternalMarksSupplementaryForm);
		List intList=transaction.getDataForQuery(intQuery);
		Map<Integer,ExamInternalMarksSupplementaryTO> intMap=new HashMap<Integer, ExamInternalMarksSupplementaryTO>();
		NewInternalMarksSupplementaryHelper.getInstance().convertBOtoMap(intList,intMap);
		//Getting latest Internal Marks if the exam is not having some subjects 
		String latestInternalQuery=NewInternalMarksSupplementaryHelper.getInstance().getLatestInternalSupplementaryQuery(newInternalMarksSupplementaryForm);
		List latestIntList=transaction.getDataForQuery(latestInternalQuery); 
		NewInternalMarksSupplementaryHelper.getInstance().convertBOtoMap(latestIntList,intMap);
		//Getting Student Over all Marks
		String overAllQuery=NewInternalMarksSupplementaryHelper.getInstance().getOverAllQuery(newInternalMarksSupplementaryForm);
		List overList=transaction.getDataForQuery(overAllQuery);
		return NewInternalMarksSupplementaryHelper.getInstance().getFinalToList(intMap,overList);
	}
	
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidStudentRegNo(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		INewStudentMarksCorrectionTransaction transaction1=NewStudentMarksCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(newInternalMarksSupplementaryForm.getRegisterNo(),newInternalMarksSupplementaryForm.getSchemeNo(),newInternalMarksSupplementaryForm.getRollNo());
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
			newInternalMarksSupplementaryForm.setStudentId(studentId);
		}
		return isValidStudent;
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveChangedMarks(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		List<InternalMarkSupplementaryDetails> bos=NewInternalMarksSupplementaryHelper.getInstance().convertTOtoBOList(newInternalMarksSupplementaryForm);
		INewInternalMarksSupplementaryTransaction transaction=NewInternalMarksSupplementaryTransactionImpl.getInstance();
		return transaction.saveInternalMarkSupplementaryDetails(bos,newInternalMarksSupplementaryForm.getUserId());
	}
	
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamInternalMarksSupplementaryTO> getStudentMarksBySupplementaryApplication(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		INewInternalMarksSupplementaryTransaction transaction=NewInternalMarksSupplementaryTransactionImpl.getInstance();
		//Getting Supplementary Improvement Application Data
		String supQuery="from StudentSupplementaryImprovementApplication s where s.schemeNo="+newInternalMarksSupplementaryForm.getSchemeNo()+" and s.subject.isActive=1" +
				" and s.classes.course.id="+newInternalMarksSupplementaryForm.getCourseId()+" and s.examDefinition.id="+newInternalMarksSupplementaryForm.getExamId()+" and s.student.id="+newInternalMarksSupplementaryForm.getStudentId()+" and (s.isAppearedPractical=1 or s.isAppearedTheory=1)";
		List<StudentSupplementaryImprovementApplication> supList=transaction.getDataForQuery(supQuery);
		if(supList.isEmpty()){
			newInternalMarksSupplementaryForm.setMsg("There is No Supplementary Application For The Given RegisterNo");
		}
		//Getting Internal Marks for The Selected Exam
		String intQuery=NewInternalMarksSupplementaryHelper.getInstance().getInternalSupplementaryQuery(newInternalMarksSupplementaryForm);
		List intList=transaction.getDataForQuery(intQuery);
		Map<Integer,ExamInternalMarksSupplementaryTO> intMap=new HashMap<Integer, ExamInternalMarksSupplementaryTO>();
		NewInternalMarksSupplementaryHelper.getInstance().convertBOtoMap(intList,intMap);
		
		return NewInternalMarksSupplementaryHelper.getInstance().getFinalToListBySupplementaryApplication(intMap, supList);
	}
	/**
	 * @param to
	 * @param newInternalMarksSupplementaryForm 
	 * @param type 
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getMaxMarkOfSubject(ExamInternalMarksSupplementaryTO to, NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		INewInternalMarksSupplementaryTransaction transaction=NewInternalMarksSupplementaryTransactionImpl.getInstance();
		return transaction.getMaxMarkOfSubject(to,newInternalMarksSupplementaryForm);
	}
	
}