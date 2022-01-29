package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalRedoMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.helpers.exam.NewUpdateProccessHelper;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMarksEntryImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

@SuppressWarnings("unchecked")
public class NewUpdateProccessHandler {
	
	private final int PROCESS_CALC_REGULAR_MARKS = 1;
	private final int PROCESS_CALC_INTERNAL_MARKS = 2;
	private final int PROCESS_SUPPLEMENTARY_DATA_CREATION = 3;
	private final int PROCESS_UPDATE_PASS_FAIL = 4;
	private final int PROCESS_REVALUATION_MODERATION = 5;
	private final int PROCESS_INTERNAL_GRACE = 6;
	private final int PROCESS_REGULAR_GRACE = 7;	
	private final int PROCESS_REVALUATION = 8;
	private final int PROCESS_INTERNAL_REDO_DATA_CREATION = 9;
	private final int PROCESS_INTERNAL_REDO_MARKS_UPDATION = 10;
	
	/**
	 * Singleton object of NewUpdateProccessHandler
	 */
	private static volatile NewUpdateProccessHandler newUpdateProccessHandler = null;
	/**
	 * return singleton object of NewUpdateProccessHandler.
	 * @return
	 */
	public static NewUpdateProccessHandler getInstance() {
		if (newUpdateProccessHandler == null) {
			newUpdateProccessHandler = new NewUpdateProccessHandler();
		}
		return newUpdateProccessHandler;
	}
		
	public Map<Integer, String> getBatchYear() throws Exception{
		String query=NewUpdateProccessHelper.getInstance().getQueryForBatchYear();
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		List<Integer> list=transaction.getDataByQuery(query);
		return NewUpdateProccessHelper.getInstance().convertBatchBoListToTOList(list);
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> getClassesByExamAndExamType(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		String query=NewUpdateProccessHelper.getInstance().getqueryForSuppliementaryDataCreation(newUpdateProccessForm);
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		List list=transaction.getDataByQuery(query);
		return NewUpdateProccessHelper.getInstance().convertBoListToTOList(list);
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateProcess(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		boolean isUpdated=false;
		switch (Integer.parseInt(newUpdateProccessForm.getProcess())) {
			case PROCESS_CALC_REGULAR_MARKS:
				isUpdated= calculateRegularOverAllMarks(newUpdateProccessForm);
				if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty()){
					if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())
						passOrFail(newUpdateProccessForm);
				}
					
				break;
	
			case PROCESS_CALC_INTERNAL_MARKS:
				isUpdated = calculateInternalOverAllMarks(newUpdateProccessForm);
				if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty())
					passOrFail(newUpdateProccessForm);
				break;
				
				
			case PROCESS_SUPPLEMENTARY_DATA_CREATION:
				isUpdated = supplementaryDataCreation(newUpdateProccessForm);
				break;
			case PROCESS_UPDATE_PASS_FAIL:
				 passOrFail(newUpdateProccessForm);
				 isUpdated=true;
				 break;
				 
		    // vibin for SH
			case PROCESS_REVALUATION_MODERATION:
				//isUpdated= calculateRevaluationModeration(newUpdateProccessForm);
				 isUpdated=true;
				 if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty()){
					if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())
						passOrFail(newUpdateProccessForm);
				 }
				 break;
				 
			case PROCESS_REGULAR_GRACE:
				isUpdated= calculateRegularGrace(newUpdateProccessForm);
				 isUpdated=true;
				 if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty()){
						if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())
							passOrFail(newUpdateProccessForm);
					}
				 break;
	
			case PROCESS_INTERNAL_GRACE:
				isUpdated = calculateInternalGrace(newUpdateProccessForm);
				if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty())
					passOrFail(newUpdateProccessForm);
				break;
				
			case PROCESS_REVALUATION:
				isUpdated = calculateRevaluationScrutinyAndSaveData(newUpdateProccessForm);
				if(newUpdateProccessForm.getErrorList()==null || newUpdateProccessForm.getErrorList().isEmpty())
					passOrFail(newUpdateProccessForm);
				break;
				
			case PROCESS_INTERNAL_REDO_DATA_CREATION:
				
				isUpdated = internalRedoDataCreation(newUpdateProccessForm);
				break;
				
			case PROCESS_INTERNAL_REDO_MARKS_UPDATION:
				
				isUpdated = internalRedoMarksUpdation(newUpdateProccessForm);
				break;
		}
		
		return isUpdated;
	}
	
	private boolean calculateInternalGrace(NewUpdateProccessForm newUpdateProccessForm) throws ApplicationException {
		return NewUpdateProccessHelper.getInstance().calculateInternalGraceAndSaveData(newUpdateProccessForm);
	}
	private boolean calculateRegularGrace(NewUpdateProccessForm newUpdateProccessForm) throws ApplicationException {
		return NewUpdateProccessHelper.getInstance().calculateRegularGraceAndSaveData(newUpdateProccessForm);
	}
//	private boolean calculateRevaluationModeration(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
//		return NewUpdateProccessHelper.getInstance().calculateRevaluationModerationAndSaveData(newUpdateProccessForm);
//	}
	/**
	 * @param newUpdateProccessForm
	 * @throws Exception
	 */
	private void passOrFail(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		NewUpdateProccessHelper.getInstance().updatePassOrFail(newUpdateProccessForm);
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	private boolean calculateRegularOverAllMarks(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		return NewUpdateProccessHelper.getInstance().calculateRegularOverAllAndSaveData(newUpdateProccessForm);
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	private boolean calculateInternalOverAllMarks(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		return NewUpdateProccessHelper.getInstance().calculateInternalOverAllAndSaveData(newUpdateProccessForm);
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public boolean supplementaryDataCreation(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		List<StudentSupplementaryImprovementApplication> boList=NewUpdateProccessHelper.getInstance().getBoListFromToList(newUpdateProccessForm);
		return transaction.saveUpdateProcess(boList);
	}
	/**
	 * @param classIds
	 * @param newUpdateProccessForm
	 * @return
	 */
	public List<String> getAlreadyModifiedStudents(String classIds, NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		String query="select s.student.registerNo from StudentSupplementaryImprovementApplication s" +
				" where s.examDefinition.id= " +newUpdateProccessForm.getExamId()+
				" and s.classes.id in ("+classIds+") and (s.isAppearedPractical=1 or s.isAppearedTheory=1) group by s.student.registerNo";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	
	public boolean deleteOldInternalOverAllMarks(int classId, int examId) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.deleteOldInternalOverAllMarks(classId, examId);
	}
	
	private boolean calculateRevaluationScrutinyAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		return NewUpdateProccessHelper.getInstance().calculateRevaluationScrutinyAndSaveData(newUpdateProccessForm);
	}
	
	/**
	 * 
	 * @param newUpdateProccessForm
	 * @return boolean
	 * @throws Exception
	 * @author Arun Sudhakaran
	 */
	public boolean internalRedoDataCreation(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		List<StudentSupplementaryImprovementApplication> boList=NewUpdateProccessHelper.getInstance().getBoListFromToListForInternalRedo(newUpdateProccessForm);
		return transaction.saveUpdateProcess(boList);
	}
	
	public boolean internalRedoMarksUpdation(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		return NewUpdateProccessHelper.getInstance().updateInternalRedoMarks(newUpdateProccessForm);
	}
	
	@SuppressWarnings("unused")
	private Map<String, ExamMarksEntryDetailsBO> getMarksMap(int examId, int classId) throws Exception {
		
		ExamMarksEntryImpl markEntryTx = new ExamMarksEntryImpl();
		List<ExamMarksEntryDetailsBO> marksEntryData = markEntryTx.getMarksEntryByExamAndClass(examId, classId);
		Map<String, ExamMarksEntryDetailsBO> marksMap = new HashMap<String, ExamMarksEntryDetailsBO>();
		Iterator<ExamMarksEntryDetailsBO> marksItr = marksEntryData.iterator();
		while(marksItr.hasNext()) {
			
			ExamMarksEntryDetailsBO bo = marksItr.next();
			String key = bo.getExamMarksEntryBO().getStudentUtilBO().getId() + "_" +
						 bo.getExamMarksEntryBO().getClasses().getId() + "_" +
						 bo.getSubjectUtilBO().getId();
			marksMap.put(key, bo);
		}
		return marksMap;
	}
	
	private List<MarksEntryDetails> getStudentMarksForSubjectNew(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,INewUpdateProccessTransaction transaction,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception {

		List<MarksEntryDetails> bo= transaction.getStudentMarksForSubjectNewForRedo(subId,studentId,intExamId,to,subType,limit,isInd,examId,newUpdateProccessForm);
		return bo;
	}
	private double getStudentMarksForSubject(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,INewUpdateProccessTransaction transaction,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception {

		return transaction.getStudentMarksForSubjectForRedo(subId,studentId,intExamId,to,subType,limit,isInd,examId,newUpdateProccessForm);
	}
		
}
