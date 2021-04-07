package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamMarksEntryUtilBO;
import com.kp.cms.forms.exam.UploadMarksEntryForm;
import com.kp.cms.helpers.exam.UploadMarksEntryHelper;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.UploadMarksEntryTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IUploadMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UploadMarksEntryTransactionImpl;

public class UploadMarksEntryHandler 
{
	IUploadMarksEntryTransaction transaction=new UploadMarksEntryTransactionImpl();
	private static volatile UploadMarksEntryHandler uploadMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(UploadMarksEntryHandler.class);
	private UploadMarksEntryHandler() {
		
	}
	
	public static UploadMarksEntryHandler getInstance() {
		if (uploadMarksEntryHandler == null) {
			uploadMarksEntryHandler = new UploadMarksEntryHandler();
		}
		return uploadMarksEntryHandler;
	}
	
	/**
	 * @param programId
	 * @param courseId
	 * @param semister
	 * @param subjectId
	 * @param isPrevious
	 * @param examId
	 * @param examType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getStudentMap(Integer programId,Integer courseId,Integer semister,Integer subjectId,String isPrevious,int examId,String examType,String subjectType) throws Exception {
		StringBuffer query=new StringBuffer(" select s.registerNo,s.id ");
		return transaction.getStudentMap(programId,courseId,semister,subjectId,isPrevious,examId,examType,subjectType,query);
	}

	public boolean addUploadedData(String userId,List<UploadMarksEntryTO> evaluator1List,List<UploadMarksEntryTO> evaluator2List,Integer subjectId,String subjectType)throws Exception 
	{
		
		List<ExamMarksEntryUtilBO> evaluator1Marks=UploadMarksEntryHelper.getInstance().convertTOToBO(userId,evaluator1List);
		List<ExamMarksEntryUtilBO> evaluator2Marks=UploadMarksEntryHelper.getInstance().convertTOToBO(userId,evaluator2List);

		return transaction.addMarks(evaluator1Marks,evaluator2Marks,subjectId,subjectType);
	}

	public List<Integer> getEvaluatorIds(int subjectId,int courseId,int schemeNo,int academicYear) throws Exception
	{
		return transaction.getEvaluatorIds(subjectId,courseId,schemeNo,academicYear);
	}

	public ExamMarksEntryUtilBO getMarkEntryDetails(String examId,String studentId, Integer evaluatorId)throws Exception 
	{
		return transaction.getMarkEntryDetails(examId,studentId,evaluatorId);
		
	}

	public boolean checkStudentCourse(String regNo, String courseId)throws Exception 
	{
		return transaction.checkStudentCourse(regNo,courseId);
	}

	public boolean isDuplicateForMarkDetails(Integer marksId, Integer subjectId) throws Exception
	{
		return transaction.isDuplicateForMarkDetails(marksId,subjectId);
	}
	
	
	/**
	 * @param userId
	 * @param evaluator1List
	 * @param evaluator2List
	 * @param subjectId
	 * @param subjectType
	 * @return
	 * @throws Exception
	 */
	public boolean saveUploadedData(List<UploadMarksEntryTO> evaluator1List,List<UploadMarksEntryTO> evaluator2List,String subjectType,Map<String,Integer> classIdMap,UploadMarksEntryForm marksEntryForm)throws Exception 
	{
		
		List<StudentMarksTO> finalList=new ArrayList<StudentMarksTO>();
		if(evaluator1List!=null && !evaluator1List.isEmpty())
			finalList=UploadMarksEntryHelper.getInstance().convertTotoFianlData(evaluator1List,classIdMap,subjectType);
		if(evaluator2List!=null && !evaluator2List.isEmpty())
			finalList.addAll(UploadMarksEntryHelper.getInstance().convertTotoFianlData(evaluator2List,classIdMap,subjectType));

		return transaction.saveMarks(finalList,marksEntryForm);
	}

	/**
	 * @param programId
	 * @param courseId
	 * @param semister
	 * @param subjectId
	 * @param isPrevious
	 * @param examId
	 * @param examType
	 * @param subjectType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getClassIdForStudents(Integer programId,Integer courseId,Integer semister,Integer subjectId,String isPrevious,int examId,String examType,String subjectType) throws Exception  {
		StringBuffer query=new StringBuffer();
		if (isPrevious.equalsIgnoreCase("yes")) {
			query=query.append(" select s.id,classHis.classes.id ");
		}else{
			query=query.append(" select s.id,s.classSchemewise.classes.id ");
		}
		return transaction.getStudentMap(programId,courseId,semister,subjectId,isPrevious,examId,examType,subjectType,query);
	}

	/**
	 * @param studentIdList
	 * @param marksEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkMarksAlreadyExists(List<Integer> studentIdList, UploadMarksEntryForm marksEntryForm) throws Exception {
		return transaction.checkMarksAlreadyExist(studentIdList,marksEntryForm);
	}
	public Double getMaxMarkOfSubject(UploadMarksEntryForm uploadMarksEntryForm) throws Exception {
		return transaction.getMaxMarkOfSubject(uploadMarksEntryForm);
	}
	
	public Set<StudentMarksTO> getStudentForInput(UploadMarksEntryForm uploadMarksEntryForm) throws Exception {
		String marksQuery=UploadMarksEntryHelper.getInstance().getQueryForAlreadyEnteredMarks(uploadMarksEntryForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		List marksList=transaction.getDataForQuery(marksQuery);// calling the method for checking data is there for the marksQuery
		Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
		if(marksList!=null && !marksList.isEmpty()){
			existsMarks=UploadMarksEntryHelper.getInstance().convertBoDataToMarksMap(marksList);// converting the database data to Required Map
		}
		
		Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
		if(!uploadMarksEntryForm.getExamType().equalsIgnoreCase("Supplementary")){
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			String currentStudentQuery=UploadMarksEntryHelper.getInstance().getQueryForCurrentClassStudents(uploadMarksEntryForm);
			List currentStudentList=transaction.getDataForQuery(currentStudentQuery);// calling the method for getting current class students
			if(currentStudentList!=null && !currentStudentList.isEmpty()){
				studentList=UploadMarksEntryHelper.getInstance().convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,uploadMarksEntryForm);
			}
			
			String previousStudentQuery=UploadMarksEntryHelper.getInstance().getQueryForPreviousClassStudents(uploadMarksEntryForm);
			List previousStudentList=transaction.getDataForQuery(previousStudentQuery);// calling the method for getting Previous class students
			if(previousStudentList!=null && !previousStudentList.isEmpty()){
				studentList=UploadMarksEntryHelper.getInstance().convertBotoTOListForPreviousClassStudents(studentList,previousStudentList,existsMarks,listOfDetainedStudents,uploadMarksEntryForm);
			}
		}else{// For Supplementary
			Map<Integer, String> oldRegMap=getOldRegisterNo(uploadMarksEntryForm.getSchemeId());
			String currentStudentQuery=UploadMarksEntryHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(uploadMarksEntryForm);
			List currentStudentList=transaction.getDataForQuery(currentStudentQuery);// calling the method for getting Supplementary current class students
			if(currentStudentList!=null && !currentStudentList.isEmpty()){
				studentList=UploadMarksEntryHelper.getInstance().convertBotoTOListForSupplementaryCurrentStudents(studentList,currentStudentList,existsMarks,uploadMarksEntryForm,oldRegMap);
			}
			
			String previousStudentQuery=UploadMarksEntryHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(uploadMarksEntryForm);
			List previousStudentList=transaction.getDataForQuery(previousStudentQuery);// calling the method for getting Supplementary Previous class students
			if(previousStudentList!=null && !previousStudentList.isEmpty()){
				studentList=UploadMarksEntryHelper.getInstance().convertBotoTOListForSupplementaryPreviousClassStudents(studentList,previousStudentList,existsMarks,uploadMarksEntryForm,oldRegMap);
			}
		}
		return studentList;
	}
	
	/**
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOldRegisterNo(String schemeNo) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String oldRegQuery=UploadMarksEntryHelper.getInstance().getQueryForOldRegisterNos(schemeNo);
		List oldRegList=transaction.getDataForQuery(oldRegQuery);
		return UploadMarksEntryHelper.getInstance().getOldRegMap(oldRegList);
	}
}
