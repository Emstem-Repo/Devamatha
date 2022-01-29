package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.helpers.exam.NewExamMarksEntryHelper;
import com.kp.cms.helpers.exam.NewSecuredMarksEntryHelper;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewSecuredMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewSecuredMarksEntryTransactionImpl;

public class NewSecuredMarksEntryHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private static volatile NewSecuredMarksEntryHandler newSecuredMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksEntryHandler.class);
	private NewSecuredMarksEntryHandler() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static NewSecuredMarksEntryHandler getInstance() {
		if (newSecuredMarksEntryHandler == null) {
			newSecuredMarksEntryHandler = new NewSecuredMarksEntryHandler();
		}
		return newSecuredMarksEntryHandler;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkMarksEnteredThroughMarksEntry(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query=NewSecuredMarksEntryHelper.getInstance().getQueryForCheckMarksEnteredThroughSecured(newSecuredMarksEntryForm);// getting the query from Helper class
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		boolean isEntered=false;// By default false value 
		List existList=transaction.getDataForQuery(query); // calling the method for checking data is there for the query
		if(existList!=null && !existList.isEmpty()){
			isEntered=true;// if existList is not empty that means marks entered through secured marks Entry screen
		}
		return isEntered;
	}
	public List<StudentMarksTO> getStudentForInput(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String marksQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForAlreadyEnteredMarks(newSecuredMarksEntryForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		List marksList=transaction.getDataForQuery(marksQuery);// calling the method for checking data is there for the marksQuery
		Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
		if(marksList!=null && !marksList.isEmpty()){
			existsMarks=NewSecuredMarksEntryHelper.getInstance().convertBoDataToMarksMap(marksList);// converting the database data to Required Map
		}
		newSecuredMarksEntryForm.setRegCount(existsMarks.size());
		if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
			String evalMarksQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForEvaluatorEnteredMarks(newSecuredMarksEntryForm);
			List evalMarksList=transaction.getDataForQuery(evalMarksQuery);// calling the method for checking data is there for the marksQuery
			Map<Integer,Map<Integer,String>> evaMap=NewSecuredMarksEntryHelper.getInstance().convertBotoEvaMap(evalMarksList,newSecuredMarksEntryForm.getSubjectType());
			newSecuredMarksEntryForm.setEvaMap(evaMap);
		}
		List<StudentMarksTO> studentList=new ArrayList<StudentMarksTO>();
		if(!newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Supplementary")){
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			if(newSecuredMarksEntryForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForCurrentClassStudents(newSecuredMarksEntryForm);
				List currentStudentList=transaction.getDataForQuery(currentStudentQuery);// calling the method for getting current class students
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					studentList=NewSecuredMarksEntryHelper.getInstance().convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,newSecuredMarksEntryForm);
				}
			}else{
				String previousStudentQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForPreviousClassStudents(newSecuredMarksEntryForm);
				List previousStudentList=transaction.getDataForQuery(previousStudentQuery);// calling the method for getting Previous class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					studentList=NewSecuredMarksEntryHelper.getInstance().convertBotoTOListForPreviousClassStudents(studentList,previousStudentList,existsMarks,listOfDetainedStudents,newSecuredMarksEntryForm);
				}
			}
			//code added by mehaboob
			if(newSecuredMarksEntryForm.getSubjectId()!=null && !newSecuredMarksEntryForm.getSubjectId().isEmpty()
					&& newSecuredMarksEntryForm.getExamId()!=null && !newSecuredMarksEntryForm.getExamId().isEmpty()){
			       List<Integer> midsemExemptionsList=transaction.getExemptionStudentsListBySubjectId(newSecuredMarksEntryForm.getSubjectId(),newSecuredMarksEntryForm.getExamId());
			       if(midsemExemptionsList!=null && !midsemExemptionsList.isEmpty()){
                    newSecuredMarksEntryForm.setStudentIdList(midsemExemptionsList);			    	   
			       }
			}
			//end code
		}else{// For Supplementary
			String oldRegQuery=NewExamMarksEntryHelper.getInstance().getQueryForOldRegisterNos(newSecuredMarksEntryForm.getSchemeNo());
			List oldRegList=transaction.getDataForQuery(oldRegQuery);
			Map<Integer, String> oldRegMap=NewExamMarksEntryHelper.getInstance().getOldRegMap(oldRegList);
			String currentStudentQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(newSecuredMarksEntryForm);
			List currentStudentList=transaction.getDataForQuery(currentStudentQuery);// calling the method for getting Supplementary current class students
			if(currentStudentList!=null && !currentStudentList.isEmpty()){
				studentList=NewSecuredMarksEntryHelper.getInstance().convertBotoTOListForSupplementaryCurrentStudents(studentList,currentStudentList,existsMarks,newSecuredMarksEntryForm,oldRegMap);
			}
			
			String previousStudentQuery=NewSecuredMarksEntryHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(newSecuredMarksEntryForm);
			List previousStudentList=transaction.getDataForQuery(previousStudentQuery);// calling the method for getting Supplementary Previous class students
			if(previousStudentList!=null && !previousStudentList.isEmpty()){
				studentList=NewSecuredMarksEntryHelper.getInstance().convertBotoTOListForSupplementaryPreviousClassStudents(studentList,previousStudentList,existsMarks,newSecuredMarksEntryForm,oldRegMap);
			}
		}
		return studentList;
	}
	/**
	 * @returnOr
	 * @throws Exception
	 */
	public List<String> getExamAbscentCode()throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		return transaction.getExamAbscentCode();
	}
	/**
	 * @param subjectId
	 * @param examId
	 * @param examType
	 * @return
	 */
	public Double getMaxMarkOfSubject(NewSecuredMarksEntryForm newSecuredMarksEntryForm)throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		return transaction.getMaxMarkOfSubject(newSecuredMarksEntryForm);
	}
	public String getPropertyValue(int id, String boName, boolean isActive,String property) throws Exception {
		INewSecuredMarksEntryTransaction transaction=NewSecuredMarksEntryTransactionImpl.getInstance();
		return transaction.getPropertyData(id, boName, isActive, property);
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveMarks(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		INewSecuredMarksEntryTransaction transaction=NewSecuredMarksEntryTransactionImpl.getInstance();
		return transaction.saveMarks(newSecuredMarksEntryForm);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getExamDiffPercentage() throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		return transaction.getExamDiffPercentage();
	}
	/**
	 * @param examId
	 * @param subCode
	 * @param examType
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getSubjects(String examId, String subCode,
			String examType, String year) throws Exception{
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		return transaction.getSubjects(examId,subCode,examType,year);
	}
}
