package com.kp.cms.handlers.attendance;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucCetMarks;
import com.kp.cms.bo.admin.AttnPucDefineRange;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.forms.attendance.AttnPucSubjectForm;
import com.kp.cms.helpers.attendance.AttendanceDataMigrationReportHelper;
import com.kp.cms.helpers.attendance.AttnPucSubjectsHelper;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.AttnPucCetMarksTo;
import com.kp.cms.to.attendance.AttnPucDefineRangeTo;
import com.kp.cms.to.attendance.AttnPucSubjectsTo;
import com.kp.cms.transactions.attandance.IAttnPucSubjectsTransaction;
import com.kp.cms.transactionsimpl.attendance.AttnPucSubjectsTransactionImpl;

public class AttnPucSubjectsHandler {
	private static volatile AttnPucSubjectsHandler attnPucSubjectsHandler = null;
	public static AttnPucSubjectsHandler getInstance(){
		if(attnPucSubjectsHandler == null){
			attnPucSubjectsHandler = new AttnPucSubjectsHandler();
			return attnPucSubjectsHandler;
			}
		return attnPucSubjectsHandler;
		}
	/**
	 * @param attnPucSubjectsTos
	 * @return
	 * @throws Exception 
	 */
	public boolean uploadPucSubjects(List<AttnPucSubjectsTo> attnPucSubjectsTos) throws Exception {
		IAttnPucSubjectsTransaction transaction = AttnPucSubjectsTransactionImpl.getInstance();
		List<AttnPucSubjects> pucSubjects = AttnPucSubjectsHelper.getInstance(). convertTOToBO(attnPucSubjectsTos);
		return transaction.addPucSubjects(pucSubjects);
	}
	
	public boolean uploadPucCetMarks(List<AttnPucCetMarksTo> attnCetMarksTos,AttnPucSubjectForm attnPucSubjectForm) throws Exception {
		IAttnPucSubjectsTransaction transaction = AttnPucSubjectsTransactionImpl.getInstance();
		List<AttnPucCetMarks> cetMarksList = AttnPucSubjectsHelper.getInstance().convertCetTOToBO(attnCetMarksTos);
		return transaction.addPucCetMarks(cetMarksList,attnPucSubjectForm);
	}
	public List<AttnPucCetMarksTo> getAttnCetMarks(AttnPucSubjectForm attnPucSubjectForm,HttpSession session)throws Exception{
		IAttnPucSubjectsTransaction transaction = AttnPucSubjectsTransactionImpl.getInstance();
		AttnPucSubjectsHelper.getInstance().setSubjectNamesToSession(attnPucSubjectForm, session);
		List<AttnPucCetMarks> attnCetMarks=transaction.getAttnCetMarks(attnPucSubjectForm);
		List<AttnPucCetMarksTo> attnMarksToList=AttnPucSubjectsHelper.getInstance().convertAttnCetMarksBoToTO(attnCetMarks);
		return attnMarksToList;
	}
	public boolean uploadDefineRange(List<AttnPucDefineRangeTo> attnDefineRangeTo) throws Exception {
		IAttnPucSubjectsTransaction transaction = AttnPucSubjectsTransactionImpl.getInstance();
		List<AttnPucDefineRange> defineRange = AttnPucSubjectsHelper.getInstance().convertDefineRangeTOToBO(attnDefineRangeTo);
		return transaction.addAttDefineRange(defineRange);
	}
}
