package com.kp.cms.handlers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm;
import com.kp.cms.helpers.admin.PeersEvaluationOpenSessionHelper;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationOpenSessionTo;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;
import com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction;
import com.kp.cms.transactionsimpl.admin.PeersEvaluationOpenSessionTransactionImpl;

public class PeersEvaluationOpenSessionHandler {
	public static volatile PeersEvaluationOpenSessionHandler handler = null;
	public static PeersEvaluationOpenSessionHandler getInstance(){
		if(handler == null){
			handler = new PeersEvaluationOpenSessionHandler();
			return handler;
		}
		return handler;
	}
	IPeersEvaluationOpenSessionTransaction transction = PeersEvaluationOpenSessionTransactionImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public  List<DepartmentEntryTO> getDepartmentList() throws Exception{
		List<Department> department = transction.getDepartmentList();
		List<DepartmentEntryTO> departmentEntryTOs = PeersEvaluationOpenSessionHelper.getInstance().copyDataFromBoTOTo(department);
		return departmentEntryTOs;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 * @throws Exception
	 */
	public List<PeersEvaluationOpenSessionTo> getOpenConnectionList( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		List<PeersEvaluationOpenSession> openSessions = transction.getOpenConnectionList();
		List<PeersEvaluationOpenSessionTo> sessionTos = PeersEvaluationOpenSessionHelper.getInstance().convertBoTOTo(openSessions);
		return sessionTos;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	
	public Map<Integer, String> getSessionByYear(int currentYear) throws Exception {
		Map<Integer, String> sessionMap = transction.getSessionsByYear(currentYear);
		return sessionMap;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @param errors 
	 * @param request 
	 * @return
	 */
	public boolean duplicateCheck( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception {
		boolean isDuplicate = false;
		if(peersEvaluationOpenSessionForm.getDepartmentIds()!=null && peersEvaluationOpenSessionForm.getDepartmentIds().length!=0){
			for(int i=0;i<peersEvaluationOpenSessionForm.getDepartmentIds().length;i++){
				int deptId = Integer.parseInt(peersEvaluationOpenSessionForm.getDepartmentIds()[i]);
				isDuplicate = transction.checkDuplicate(deptId,peersEvaluationOpenSessionForm);
				if(!isDuplicate){
					return isDuplicate;
				}
			}
		}
		return isDuplicate;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean submitOpenConnectionDetails( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		boolean isAdded= false;
		List<PeersEvaluationOpenSession> openSessions = PeersEvaluationOpenSessionHelper.getInstance().convertDataFromFormTOBo(peersEvaluationOpenSessionForm);
		isAdded = transction.submitOpenSession(openSessions);
		return isAdded;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updatePeersEvaluationOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		boolean isUpdated = transction.updatePeersOpenSession(peersEvaluationOpenSessionForm);
		return isUpdated;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 */
	public boolean deleteOpenConnection( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		boolean isDeleted = transction.deletePeersOpenSession(peersEvaluationOpenSessionForm);
		return isDeleted;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @throws Exceptionb
	 */
	public void editPeersEvaluationOpenSession(PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception {
		PeersEvaluationOpenSession openSession = transction.editPeersEvaluationOpenSession(peersEvaluationOpenSessionForm);
		peersEvaluationOpenSessionForm = PeersEvaluationOpenSessionHelper.getInstance().populateBoTOTo(openSession,peersEvaluationOpenSessionForm);
	}
}
