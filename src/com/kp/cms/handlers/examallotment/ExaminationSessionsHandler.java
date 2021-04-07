package com.kp.cms.handlers.examallotment;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExaminationSessionsForm;
import com.kp.cms.helpers.examallotment.ExaminationSessionsHelper;
import com.kp.cms.to.examallotment.ExaminationSessionsTo;
import com.kp.cms.transactions.examallotment.IExaminationSessionsTrans;
import com.kp.cms.transactionsimpl.examallotment.ExaminationSessionsTransImpl;

public class ExaminationSessionsHandler {
	IExaminationSessionsTrans transaction=ExaminationSessionsTransImpl.getInstance();
	ExaminationSessionsHelper examinationSessionsHelper=ExaminationSessionsHelper.getInstance();
	public static volatile ExaminationSessionsHandler examinationSessionsHandler=null;
	//private constructor
	private ExaminationSessionsHandler(){
		
	}
	//singleton object
	public static ExaminationSessionsHandler getInstance(){
		if(examinationSessionsHandler==null){
			examinationSessionsHandler=new ExaminationSessionsHandler();
			return examinationSessionsHandler;
		}
		return examinationSessionsHandler;
	}
	/*
	 * checking duplicate record
	 */
	public boolean checkDuplicate(ExaminationSessionsForm examinationSessionsForm,String string) throws Exception{
		boolean flag=transaction.checkDuplicate(examinationSessionsForm,string);
		return flag;
	}
	public boolean add(ExaminationSessionsForm examinationSessionsForm) throws Exception{
		//convert form to bo
		ExaminationSessions examinationSessions=examinationSessionsHelper.convertFormToBo(examinationSessionsForm);
		boolean flag=transaction.add(examinationSessions);
		return flag;
	}
	public void getListOfExaminationSessions(
			ExaminationSessionsForm examinationSessionsForm) throws Exception{
		List<ExaminationSessions> list=transaction.getListOfExaminationSessions();
		if(list!=null && !list.isEmpty()){
			List<ExaminationSessionsTo> listOfTos=examinationSessionsHelper.convertBosToTos(list);
			examinationSessionsForm.setList(listOfTos);
		}
	}
	public boolean delete(int id, String string) throws Exception{
		boolean flag=transaction.delete(id,string);
		return flag;
	}
	public void edit(ExaminationSessionsForm examinationSessionsForm) throws Exception{
		ExaminationSessions examinationSessions=transaction.getExaminationSessionById(examinationSessionsForm.getId());
		//convert bo to form
		examinationSessionsForm.setSession(examinationSessions.getSession());
		examinationSessionsForm.setSessionDesc(examinationSessions.getSessionDescription());
		examinationSessionsForm.setTimings(examinationSessions.getTimings());
		examinationSessionsForm.setOrderNo(String.valueOf(examinationSessions.getOrderNO()));
	}
	public boolean update(ExaminationSessionsForm examinationSessionsForm) throws Exception{
		ExaminationSessions examinationSessions=transaction.getExaminationSessionById(examinationSessionsForm.getId());
		examinationSessions.setSession(examinationSessionsForm.getSession());
		examinationSessions.setSessionDescription(examinationSessionsForm.getSessionDesc());
		examinationSessions.setOrderNO(Integer.parseInt(examinationSessionsForm.getOrderNo()));
		examinationSessions.setTimings(examinationSessionsForm.getTimings());
		examinationSessions.setModifiedBy(examinationSessionsForm.getUserId());
		examinationSessions.setLastModifiedDate(new Date());
		boolean flag=transaction.update(examinationSessions);
		return flag;
	}
}
