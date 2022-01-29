package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.GradesForm;
import com.kp.cms.helpers.admin.GradesHelper;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.transactions.admin.IGradesTransaction;
import com.kp.cms.transactionsimpl.admin.GradesTransactionImpl;

/**
 * 
 * @author
 *
 */
public class GradesHandler {
	private static final Log log = LogFactory.getLog(GradesHandler.class);
	public static volatile GradesHandler gradesHandler = null;

	public static GradesHandler getInstance() {
		if (gradesHandler == null) {
			gradesHandler = new GradesHandler();
			return gradesHandler;
		}
		return gradesHandler;
	}
	/**
	 * 
	 * @return list of gradeTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<GradeTO> getGrades() throws Exception {
		IGradesTransaction iGradesTransaction = GradesTransactionImpl.getInstance();
		List<Grade> gradeList = iGradesTransaction.geGrades();
		List<GradeTO> gradeTOList = GradesHelper.getInstance().copyGradeBosToTos(gradeList);
		log.debug("leaving getGrades");
		return gradeTOList;
	}
	  
	/**
	 * 
	 * @param gradesForm
	 * @return
	 * @throws Exception
	 */
	public boolean addGrades(GradesForm gradesForm)	throws Exception {
		IGradesTransaction iGradesTransaction = GradesTransactionImpl.getInstance();
		boolean isAdded = false;
		
		Grade duplGrade = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm); 

		duplGrade = iGradesTransaction.isGradeDuplcated(duplGrade);  //duplication checking
		if (duplGrade != null) {
			throw new DuplicateException();
		}
		
		Grade duplMark = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm); 
		duplMark = iGradesTransaction.isMarkDuplcated(duplMark);
		
		if (duplMark != null) {
			throw new DuplicateException();
		}
				
		Grade grade = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm);
		isAdded = iGradesTransaction.addGrades(grade); 
		log.debug("leaving addGrades");
		return isAdded;
	}
	
	/**
	 * 
	 * @param 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean updateGrades(GradesForm gradesForm) throws Exception {
		IGradesTransaction iGradesTransaction = GradesTransactionImpl.getInstance();
		boolean isUpdate = false;
		//originalchanged variables are used for edit duplication checking.. in edit no need to check duplication for the loaded record
		
		Boolean originalGradeNotChanged = false;
		Boolean originalMarkNotChanged = false;

		String curGrade = gradesForm.getGrade();
		String origGrade = gradesForm.getOrigGrade();
		int curMark = 0;
		int origmark = 0;
		
		if(gradesForm.getMark() != null && !gradesForm.getMark().isEmpty()){
			curMark = Integer.parseInt(gradesForm.getMark());
		}
		if(gradesForm.getOrigMark() != null && !gradesForm.getOrigMark().isEmpty()){
			origmark = Integer.parseInt(gradesForm.getOrigMark());
		}
		
		if (curGrade.equals(origGrade)) {
			originalGradeNotChanged = true;
		}
		if(curMark == origmark)
		{
			originalMarkNotChanged = true;
		}
		
		if(!originalGradeNotChanged){
			Grade duplGrade = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm); 

			duplGrade = iGradesTransaction.isGradeDuplcated(duplGrade);
			if (duplGrade != null) 
			{
				throw new DuplicateException();
			}
		}
		if(!originalMarkNotChanged){
			Grade duplMark = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm); 
			duplMark = iGradesTransaction.isMarkDuplcated(duplMark);
			if (duplMark != null) 
			{
				throw new DuplicateException();
			}		
		}
		Grade grade = GradesHelper.getInstance().copyDataFromFormToBO(gradesForm); 
		isUpdate = iGradesTransaction .updateGrades(grade);
		log.debug("leaving updateGrades");
		return isUpdate;
	}

	/**
	 * 
	 * @param 
	 * @return boolean true / false based on result.
	 * @throws Exception 
	 */
	public boolean deleteGrades(int id,Boolean activate,GradesForm gradesForm)throws Exception {
		IGradesTransaction iGradesTransaction = GradesTransactionImpl.getInstance();
//		Grade grade = new Grade();
//		grade.setId(id);
		boolean result = iGradesTransaction.deleteGrade(id,activate,gradesForm);
		log.debug("leaving deleteGrades");
		return result;
	}
	
	
}
