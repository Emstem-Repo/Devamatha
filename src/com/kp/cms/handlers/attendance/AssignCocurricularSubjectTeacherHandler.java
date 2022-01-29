package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.zefer.html.c.i;

import com.kp.cms.bo.admin.CoCurricularTeacherBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.AssignCocurricularSubjectTeacherForm;
import com.kp.cms.helpers.attendance.AssignCocurricularSubjectTeacherHelper;
import com.kp.cms.to.attendance.AssignCocurricularSubjectTeacherTO;
import com.kp.cms.transactions.attandance.IAssignCocurricularSubjectTeacherTransaction;
import com.kp.cms.transactionsimpl.attendance.AssignCocurricularSubjectTeacherTransactionImpl;

public class AssignCocurricularSubjectTeacherHandler {
	private static Log log = LogFactory.getLog(AssignCocurricularSubjectTeacherHandler.class);
	private static volatile AssignCocurricularSubjectTeacherHandler assignCocurricularSubjectTeacherHandler  =  null;
	public static AssignCocurricularSubjectTeacherHandler getInstance()
	{
		if(assignCocurricularSubjectTeacherHandler== null)
		{
			assignCocurricularSubjectTeacherHandler = new AssignCocurricularSubjectTeacherHandler();
		}
		return assignCocurricularSubjectTeacherHandler;
	}
	public Map<Integer, String> getUsers() throws Exception {
		log.debug("call of getUsers methos in AssignCocurricularSubjectTeacherHandler.class");
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		Map<Integer, String> teacherMap = new HashMap<Integer, String>();
		teacherMap = transaction.getUsers(); 
		log.debug("end of getUsers methos in AssignCocurricularSubjectTeacherHandler.class");
		return teacherMap;
	}
	
	public Map<Integer, String> getActivityMap() throws Exception{
		log.debug("call of getActivityMap methos in AssignCocurricularSubjectTeacherHandler.class");
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap = transaction.getActivityMap(); 
		log.debug("end of getActivityMap methos in AssignCocurricularSubjectTeacherHandler.class");
		return activityMap;
	}
	
	public boolean saveCocurricularSubjectTeacher(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm) throws Exception{
		log.debug("call of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		boolean isAdded = false;
		List<CoCurricularTeacherBO> duplicateCoCurricularTeacherBOs = new ArrayList<CoCurricularTeacherBO>();
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		CoCurricularTeacherBO  coCurricularTeacherBO = new CoCurricularTeacherBO();
		// check for duplicate
		duplicateCoCurricularTeacherBOs = transaction.checkDuplicate(assignCocurricularSubjectTeacherForm.getTeacherID());
		if(duplicateCoCurricularTeacherBOs!=null && duplicateCoCurricularTeacherBOs.size()>0)
		{
			Iterator<CoCurricularTeacherBO> iterator = duplicateCoCurricularTeacherBOs.iterator();
			while(iterator.hasNext())
			{
				CoCurricularTeacherBO  bo = iterator.next();
				if (bo != null && bo.getIsActive()) {
					throw new DuplicateException();
				} else if (bo != null && !bo.getIsActive()) {
					assignCocurricularSubjectTeacherForm.setDupliateId(Integer.toString(bo.getId()));
					throw new ReActivateException();
				}
			}
		}
		else
		{
			coCurricularTeacherBO = AssignCocurricularSubjectTeacherHelper.getInstance().convertFormToBO(assignCocurricularSubjectTeacherForm); 
			if(coCurricularTeacherBO!=null )
			{
				isAdded = transaction.saveCocurricularSubjectTeacher(coCurricularTeacherBO);
			}
		}
		
		
		log.debug("end of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		return isAdded;
	}
	public List<AssignCocurricularSubjectTeacherTO> getCocurricularList()throws Exception {
		log.debug("call of getCocurricularList method in AssignCocurricularSubjectTeacherHandler.class");
		List<AssignCocurricularSubjectTeacherTO> cocurricularList = new ArrayList<AssignCocurricularSubjectTeacherTO>();
		List<CoCurricularTeacherBO> bolist = new ArrayList<CoCurricularTeacherBO>();
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		bolist =  transaction.getCocurricularList();
		cocurricularList = AssignCocurricularSubjectTeacherHelper.getInstance().copyBOlisttoTOList(bolist);
		log.debug("end of getCocurricularList method in AssignCocurricularSubjectTeacherHandler.class");
		return cocurricularList;
	}
	public boolean deleteCocurricularTeacher(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm) throws Exception{
		log.debug("call of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		boolean isDeleted = false;
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		isDeleted = transaction.deleteCocurricularTeacher(assignCocurricularSubjectTeacherForm);
		log.debug("end of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		return isDeleted;
	}
	
	
	public boolean reactiveCocurricularSubjectTeacher(String dupliateId,String userId) throws Exception {
		log.debug("call reactiveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		boolean isReactive = false;
		isReactive = transaction.reactiveCocurricularSubjectTeacher(dupliateId,userId);
		log.debug("end reactiveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		return isReactive;
	}
	
	
	
	public void editCocurricularTeacher(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm)throws Exception {
		log.debug("call of editCocurricularTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		CoCurricularTeacherBO coCurricularTeacherBO = transaction.editCocurricularTeacher(assignCocurricularSubjectTeacherForm.getId());
		if(coCurricularTeacherBO!=null)
		{
			AssignCocurricularSubjectTeacherHelper.getInstance().populateDataToForm(coCurricularTeacherBO,assignCocurricularSubjectTeacherForm);
			
		}
		log.debug("end of editCocurricularTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		
	}
	
	public boolean updateCocurricularSubjectTeacher(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm)throws Exception {
		log.debug("call updateCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		IAssignCocurricularSubjectTeacherTransaction transaction = new AssignCocurricularSubjectTeacherTransactionImpl().getInstance();
		boolean isUpdate = false;
		CoCurricularTeacherBO coCurricularTeacherBO  = new CoCurricularTeacherBO();
		coCurricularTeacherBO = AssignCocurricularSubjectTeacherHelper.getInstance().convertFormToBO(assignCocurricularSubjectTeacherForm);
		coCurricularTeacherBO.setId(Integer.parseInt(assignCocurricularSubjectTeacherForm.getId()));
		isUpdate = transaction.updateCocurricularSubjectTeacher(coCurricularTeacherBO);
		log.debug("end updateCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherHandler.class");
		return isUpdate;
	}
	
	
	
}
