package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.forms.attendance.CocurricularLeaveApproveForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.attendance.CocurricularLeaveApproveHelper;
import com.kp.cms.to.attendance.ApproveCocurricularLeaveTO;
import com.kp.cms.transactionsimpl.attendance.CocurricularLeaveApproveTransactionImpl;
import com.kp.cms.transactions.attandance.ICocurricularLeaveApproveTransaction;
import com.kp.cms.utilities.CommonUtil;

public class CocurricularLeaveApproveHandler {
	private static Log log = LogFactory.getLog(CocurricularLeaveApproveHandler.class);
	private static volatile CocurricularLeaveApproveHandler cocurricularLeaveApproveHandler  =  null;
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static CocurricularLeaveApproveHandler getInstance()
	{
		if(cocurricularLeaveApproveHandler== null)
		{
			cocurricularLeaveApproveHandler = new CocurricularLeaveApproveHandler();
		}
		return cocurricularLeaveApproveHandler;
	}
	
	public Map<Integer, String> getAssignedActivityMap(String userId) throws Exception{
		log.debug("call of getAssignedActivityMap method in CocurricularLeaveApproveHandler.class");
		ICocurricularLeaveApproveTransaction transaction = new CocurricularLeaveApproveTransactionImpl().getInstance();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap = transaction.getAssignedActivityMap(userId);
		log.debug("end of getAssignedActivityMap method in CocurricularLeaveApproveHandler.class");
		return activityMap;
	}
	public List<ApproveCocurricularLeaveTO> getCocurricularApplications(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception{
		log.debug("call of getCocurricularApplications method in CocurricularLeaveApproveHandler.class");
		List<ApproveCocurricularLeaveTO> cocurricularLeaveTOList = new ArrayList<ApproveCocurricularLeaveTO>();
		ICocurricularLeaveApproveTransaction transaction = new CocurricularLeaveApproveTransactionImpl().getInstance();
		Map<Integer, String> userMap = AssignCocurricularSubjectTeacherHandler.getInstance().getUsers();
		String activityIdForQuery = "";
		String activityNames ="";
		String[] activityIds = cocurricularLeaveApproveForm.getActivityIds();
		java.sql.Date fromDate = CommonUtil.ConvertStringToSQLDate(cocurricularLeaveApproveForm.getFromDate());
		java.sql.Date  toDate = CommonUtil.ConvertStringToSQLDate(cocurricularLeaveApproveForm.getToDate());
		int count =0;
		for(int i = 0; i<activityIds.length;i++)
		{
			if(i==activityIds.length-1)
			{
				activityIdForQuery = activityIdForQuery+activityIds[i];
				activityNames= activityNames+cocurricularLeaveApproveForm.getActivityMap().get(Integer.parseInt(activityIds[i]));
			}
			else
			{
				activityIdForQuery = activityIdForQuery+activityIds[i]+",";
				activityNames= activityNames+cocurricularLeaveApproveForm.getActivityMap().get(Integer.parseInt(activityIds[i]))+",";
			}
		}
		cocurricularLeaveApproveForm.setActivityNames(activityNames);
		List<StuCocurrLeave> cocurrLeavesBo = transaction.getCocurricularApplications(activityIdForQuery,fromDate,toDate);
		cocurricularLeaveTOList = CocurricularLeaveApproveHelper.getInstance().convertBOListToTOList(cocurrLeavesBo);
		log.debug("end of getCocurricularApplications method in CocurricularLeaveApproveHandler.class");
		return cocurricularLeaveTOList;
	}
	
	public boolean approveCocurricularApplicationsByTeacher(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception {
		log.debug("call of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHandler.class");
		boolean isAdded = false;
		ICocurricularLeaveApproveTransaction transaction = new CocurricularLeaveApproveTransactionImpl().getInstance();
		List<StuCocurrLeave> cocurrLeavesBoList = new ArrayList<StuCocurrLeave>();
		cocurrLeavesBoList = CocurricularLeaveApproveHelper.getInstance().approveCocurricularApplicationsByTeacher(cocurricularLeaveApproveForm);
		isAdded = transaction.approveCocurricularApplicationsByTeacher(cocurrLeavesBoList);
		log.debug("end of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHandler.class");
		return isAdded;
	}
	public List<ApproveCocurricularLeaveTO> searchCocurricularApplications(CocurricularLeaveApproveForm cocurricularLeaveApproveForm,String searchType) throws Exception
	{
		log.debug("call of searchCocurricularApplications method in CocurricularLeaveApproveHandler.class");
		List<ApproveCocurricularLeaveTO> cocurricularLeaveTOList = new ArrayList<ApproveCocurricularLeaveTO>();
		ICocurricularLeaveApproveTransaction transaction = new CocurricularLeaveApproveTransactionImpl().getInstance();
		String activityIdForQuery = "";
		String activityNames ="";
		String[] activityIds = cocurricularLeaveApproveForm.getActivityIds();
		java.sql.Date fromDate = CommonUtil.ConvertStringToSQLDate(cocurricularLeaveApproveForm.getFromDate());
		java.sql.Date  toDate = CommonUtil.ConvertStringToSQLDate(cocurricularLeaveApproveForm.getToDate());
		int count =0;
		for(int i = 0; i<activityIds.length;i++)
		{
			if(i==activityIds.length-1)
			{
				activityIdForQuery = activityIdForQuery+activityIds[i];
				activityNames= activityNames+cocurricularLeaveApproveForm.getActivityMap().get(Integer.parseInt(activityIds[i]));
			}
			else
			{
				activityIdForQuery = activityIdForQuery+activityIds[i]+",";
				activityNames= activityNames+cocurricularLeaveApproveForm.getActivityMap().get(Integer.parseInt(activityIds[i]))+",";
			}
		}
		cocurricularLeaveApproveForm.setActivityNames(activityNames);
		List<StuCocurrLeave> cocurrLeavesBo = transaction.searchCocurricularApplications(activityIdForQuery,fromDate,toDate,searchType,cocurricularLeaveApproveForm.getUserId());
		cocurricularLeaveTOList = CocurricularLeaveApproveHelper.getInstance().convertBOListToTOList(cocurrLeavesBo);
		log.debug("end of searchCocurricularApplications method in CocurricularLeaveApproveHandler.class");
		return cocurricularLeaveTOList;
	}
	public boolean cancelCocurricularApplicationsByTeacher(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception {
		log.debug("call of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHandler.class");
		boolean isCanceled = false;
		ICocurricularLeaveApproveTransaction transaction = new CocurricularLeaveApproveTransactionImpl().getInstance();
		List<StuCocurrLeave> cocurrLeavesBoList = new ArrayList<StuCocurrLeave>();
		cocurrLeavesBoList = CocurricularLeaveApproveHelper.getInstance().cancelCocurricularApplicationsByTeacher(cocurricularLeaveApproveForm);
		isCanceled = transaction.cancelCocurricularApplicationsByTeacher(cocurrLeavesBoList);
		log.debug("end of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHandler.class");
		return isCanceled;
	}
}
