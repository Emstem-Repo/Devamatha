package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.helpers.exam.NewStudentMarksCorrectionHelper;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewStudentMarksCorrectionTransactionImpl;

public class NewStudentMarksCorrectionHandler {
	/**
	 * Singleton object of NewStudentMarksCorrectionHandler
	 */
	private static volatile NewStudentMarksCorrectionHandler newStudentMarksCorrectionHandler = null;
	private static final Log log = LogFactory.getLog(NewStudentMarksCorrectionHandler.class);
	private NewStudentMarksCorrectionHandler() {
		
	}
	/**
	 * return singleton object of NewStudentMarksCorrectionHandler.
	 * @return
	 */
	public static NewStudentMarksCorrectionHandler getInstance() {
		if (newStudentMarksCorrectionHandler == null) {
			newStudentMarksCorrectionHandler = new NewStudentMarksCorrectionHandler();
		}
		return newStudentMarksCorrectionHandler;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkMarksEnteredForExam(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		String query=NewStudentMarksCorrectionHelper.getInstance().getMarksEnteredForExamQuery(newStudentMarksCorrectionForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		boolean isEntered=false;
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			isEntered=true;
		}
		return isEntered;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 */
	public boolean checkStudentIsValid(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm)  throws Exception{
		boolean isEntered=false;
		INewStudentMarksCorrectionTransaction transaction1=NewStudentMarksCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(newStudentMarksCorrectionForm.getRegNo(),newStudentMarksCorrectionForm.getSchemeNo(),newStudentMarksCorrectionForm.getRollNo());
		if(studentId!=null && studentId>0){
			newStudentMarksCorrectionForm.setStudentId(studentId);
			String query=NewStudentMarksCorrectionHelper.getInstance().checkStudentIsValidQuery(newStudentMarksCorrectionForm);
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			List list=transaction.getDataForQuery(query);
			if(list!=null && !list.isEmpty()){
				isEntered=true;
			}
		}
		return isEntered;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public List<NewStudentMarkCorrectionTo> getSubjectListOfStudent(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		String query=NewStudentMarksCorrectionHelper.getInstance().checkStudentIsValidQuery(newStudentMarksCorrectionForm);
		String oldMarksQuery=NewStudentMarksCorrectionHelper.getInstance().getOldMarksQuery(newStudentMarksCorrectionForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Integer> oldMarksList=transaction.getDataForQuery(oldMarksQuery);
		List marksList=transaction.getDataForQuery(query);
		List<NewStudentMarkCorrectionTo> mainList=null;
		if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
			mainList=NewStudentMarksCorrectionHelper.getInstance().convertMarksEntryBotoTO(marksList,oldMarksList,newStudentMarksCorrectionForm);
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Internal overAll")){
			mainList=NewStudentMarksCorrectionHelper.getInstance().convertInternalOverAllBotoTO(marksList,oldMarksList,newStudentMarksCorrectionForm);
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Regular overAll")){
			mainList=NewStudentMarksCorrectionHelper.getInstance().convertRegularOverAllBotoTO(marksList,oldMarksList,newStudentMarksCorrectionForm);
		}
		return mainList;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveChangedMarks(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		List<MarksEntryCorrectionDetails> boList=NewStudentMarksCorrectionHelper.getInstance().convertTotoBoList(newStudentMarksCorrectionForm);
		INewStudentMarksCorrectionTransaction transaction=NewStudentMarksCorrectionTransactionImpl.getInstance();
		transaction.updateModifiedMarks(newStudentMarksCorrectionForm.getMarksList(),newStudentMarksCorrectionForm.getUserId(),newStudentMarksCorrectionForm.getMarksCardNo());
		return transaction.saveMarksEntryCorrection(boList);
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarkDetailsTO> getStudentOldMarks(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		String query=NewStudentMarksCorrectionHelper.getInstance().getStudentOldMarksBySubjectId(newStudentMarksCorrectionForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		return NewStudentMarksCorrectionHelper.getInstance().convertBOtoTo(list);
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 */
	public Map<Integer, SubjectMarksTO> getMaxMarksMap(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		String query=NewStudentMarksCorrectionHelper.getInstance().getMaxMarksQuery(newStudentMarksCorrectionForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		return NewStudentMarksCorrectionHelper.getInstance().getMaxMarksMap(list,newStudentMarksCorrectionForm);
	}
}
