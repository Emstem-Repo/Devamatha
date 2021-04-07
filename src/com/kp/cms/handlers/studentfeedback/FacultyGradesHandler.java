package com.kp.cms.handlers.studentfeedback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.studentfeedback.FacultyGrades;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.studentfeedback.FacultyGradesForm;
import com.kp.cms.helpers.studentfeedback.FacultyGradesHelper;
import com.kp.cms.to.studentfeedback.FacultyGradesTO;
import com.kp.cms.transactions.studentfeedback.IFacultyGradesTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.FacultyGradesTransactionImpl;

public class FacultyGradesHandler {
	private static final Log log = LogFactory.getLog(FacultyGradesHandler.class);
	private static volatile FacultyGradesHandler facultyGradesHandler = null;
	
	private FacultyGradesHandler() {
	}
	
	public static FacultyGradesHandler getInstance() {
		if (facultyGradesHandler == null) {
			facultyGradesHandler = new FacultyGradesHandler();
		}
		return facultyGradesHandler;
	}
	IFacultyGradesTransaction transaction=new FacultyGradesTransactionImpl();
	
	public boolean addFacultyGrades(FacultyGradesForm facultyGradesForm)throws Exception
	{
		log.info("Inside addExternalEvaluator of ExternalEvaluatorHandler");
		//Capture the formbean properties. and set those to externalEvaluatorTO
		
		FacultyGradesTO facultyGradesTO=new FacultyGradesTO();
		if(facultyGradesForm!=null){
			facultyGradesTO.setGrade(facultyGradesForm.getGrade());
			
			facultyGradesTO.setScaleFrom(new BigDecimal(facultyGradesForm.getScaleFrom()));
			facultyGradesTO.setScaleTo(new BigDecimal(facultyGradesForm.getScaleTo()));
			facultyGradesTO.setCreatedBy(facultyGradesForm.getUserId());
			facultyGradesTO.setModifiedBy(facultyGradesForm.getUserId());

		}
		FacultyGrades facultyGrades=FacultyGradesHelper.getInstance().populateTOtoBO(facultyGradesTO);
		if(facultyGrades!=null){
			return transaction.addFacultyGrades(facultyGrades);
		}
		log.info("Leaving of addFacultyGrades of FacultyGradesHandler");
		return false;
	}
	
	public List<FacultyGradesTO> getFacultyGradesDetails()throws Exception
	{
		log.info("Inside of getFacultyGradesDetails of FacultyGradesHandler");
		List<FacultyGrades> facultyList =transaction.getFacultyGradesDetails();
		if(facultyList!=null && !facultyList.isEmpty()){
			return FacultyGradesHelper.getInstance().pupulateFacultyGradesBOtoTO(facultyList);
		}
		log.info("Leaving from getFacultyGradesDetails of FacultyGradesHandler");
		return new ArrayList<FacultyGradesTO>();
	}
	
	public boolean deleteFacultyGrades(int facultyGradesId, String userId)throws Exception{
		log.info("Inside of deleteFacultyGrades of FacultyGradesHandler");
		if(transaction!=null){
			return transaction.deleteFacultyGrades(facultyGradesId, userId);
		}
		log.info("Leaving of deleteFacultyGrades of FacultyGradesHandler");
		return false;
	}
	
	public boolean updateFacultyGrades(FacultyGradesForm byForm, ActionErrors errors)throws Exception
	{
		log.info("Inside of updateFacultyGrades of FacultyGradesHandler");
		FacultyGradesTO byTO=new FacultyGradesTO();
		String grade="";
		boolean orgGrade=false;
		grade = byForm.getGrade();
		orgGrade = transaction.checkForDuplicateonGrade1(grade, errors, byForm);
		if (orgGrade){
			throw new DuplicateException();
		}
		
		
		else{
		if(byForm!=null){
			byTO.setId(byForm.getId());
			byTO.setGrade(byForm.getGrade());
//			BigDecimal scaleFrom=new BigDecimal(byForm.getScaleFrom());
			byTO.setScaleFrom(new BigDecimal(byForm.getScaleFrom()));
			byTO.setModifiedBy(byForm.getUserId());
			byTO.setScaleTo(new BigDecimal(byForm.getScaleTo()));
		}
		if(byTO!=null){
			FacultyGrades facultyGrades=FacultyGradesHelper.getInstance().populateTotoBoUpdate(byTO);
			if(transaction!=null){
				return transaction.updateFacultyGrades(facultyGrades);				
			}
		}
		log.info("Leaving of updateFacultyGrades of FacultyGradesHandler");
		return false;
		}

	}
	
	public boolean reActivateFacultyGrades(String grade, String userId)throws Exception
	{
	log.info("Inside into reActivateFacultyGrades of FacultyGradesHandler");
	if(transaction!=null){
		return transaction.reActivateFacultyGrades(grade, userId);
	}
	log.info("Leaving of reActivateFacultyGrades of FacultyGradesHandler");
	return false;
	}
	
	public FacultyGradesTO getDetailsonId(int extId) throws Exception{
		log.info("Inside into getDetailsonId of FacultyGradesHandler");
		FacultyGrades facultyGrades=transaction.getDetailsonId(extId);
		log.info("End of getDetailsonId of FacultyGradesHandler");
		return FacultyGradesHelper.getInstance().populateBotoToEdit(facultyGrades);
	}
	
	public FacultyGrades checkForDuplicateonGrade(String grade)throws Exception
	{
		log.info("Inside into checkForDuplicateonGrade of FacultyGradesHandler");
		return transaction.checkForDuplicateonGrade(grade);
	}

}
