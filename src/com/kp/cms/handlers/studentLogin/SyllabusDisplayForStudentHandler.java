package com.kp.cms.handlers.studentLogin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.helpers.studentLogin.SyllabusDisplayForStudentHelper;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;
import com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction;
import com.kp.cms.transactionsimpl.studentLogin.SyllabusDisplayForStudentTransactionImpl;


public class SyllabusDisplayForStudentHandler {
	private static final Log log = LogFactory.getLog(SyllabusDisplayForStudentHandler.class);	
	public static volatile SyllabusDisplayForStudentHandler syllabusDisplayForStudentHandler = null;
	
	/**
	 * @return
	 */
	public static SyllabusDisplayForStudentHandler getInstance() {
		if (syllabusDisplayForStudentHandler == null) {
			syllabusDisplayForStudentHandler = new SyllabusDisplayForStudentHandler();
		}
		return syllabusDisplayForStudentHandler;
	}
	
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidStudentRegNo(String regNo) throws Exception{
		ISyllabusDisplayForStudentTransaction transaction=SyllabusDisplayForStudentTransactionImpl.getInstance();
		Student studentBo=transaction.getStudentDetails(regNo);
		
		boolean isValidStudent=false;
		if(studentBo!=null){
		if(studentBo.getId()!=0 && studentBo.getId()>0){
			isValidStudent=true;
		}
		return isValidStudent;
		}else{
			return isValidStudent;
		}
	}
	
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<SyllabusDisplayForStudentTo> getStudentBacklocs(SyllabusDisplayForStudentForm form )throws Exception{
		ISyllabusDisplayForStudentTransaction transaction=SyllabusDisplayForStudentTransactionImpl.getInstance();
		List<Object[]> boList=transaction.getConsolidateMarksCardBo(form);
		Student studentBo=transaction.getStudentDetails(form.getRegistrNo());
		List<SyllabusDisplayForStudentTo> toList=SyllabusDisplayForStudentHelper.getInstance().convertBotoTo(boList,studentBo,form);
		return toList;
	}
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusDisplayForStudentForm form)throws Exception{
		ISyllabusDisplayForStudentTransaction transaction=SyllabusDisplayForStudentTransactionImpl.getInstance();
		List<Object[]> admYear=transaction.getCurrentStudyingBatchjoiningYear(form);
		return admYear;
	}
		
	/**
	 * @param csdId
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String getcurrentStudyingBatchSubjectName(int csdId,SyllabusDisplayForStudentForm form)throws Exception{
		ISyllabusDisplayForStudentTransaction transaction=SyllabusDisplayForStudentTransactionImpl.getInstance();
		String subjectName=transaction.getSubjectName(csdId,form);
		return subjectName;
	}
}
