package com.kp.cms.handlers.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.forms.sap.ExamScheduleForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.sap.ExamScheduleHelper;
import com.kp.cms.to.sap.ExamScheduleTo;
import com.kp.cms.transactions.sap.IExamScheduleTransaction;
import com.kp.cms.transactionsimpl.sap.ExamScheduleTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamScheduleHandler {

	private static volatile ExamScheduleHandler examScheduleHandler = null;
	private static final Log log = LogFactory.getLog(ExamScheduleHandler.class);
	
	/**
	 * return singleton object of TimeTableForClassHandler.
	 * @return
	 */
	public static ExamScheduleHandler getInstance() {
		if (examScheduleHandler == null) {
			examScheduleHandler = new ExamScheduleHandler();
		}
		return examScheduleHandler;
	}
	
	/**  get the workLocation
	 * @return
	 * @throws Exception
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getWorkLocation()throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		Map<Integer,String> workLocationMap=transaction.getWorkLocation();
		return workLocationMap;
	}
	
	/** 
	 * @param examschList
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveVenueDetails(List<ExamScheduleTo> examschList,ExamScheduleForm examScheduleForm,HttpServletRequest request)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		if((examScheduleForm.getSession()!=null && !examScheduleForm.getSession().isEmpty()) &&
				(examScheduleForm.getSessionOrder()!=null && !examScheduleForm.getSessionOrder().isEmpty())&&
				(examScheduleForm.getExamDate()!=null && !examScheduleForm.getExamDate().isEmpty())){
			String str = "select bo from ExamScheduleDate bo where bo.isActive = 1 and " +
			" bo.examDate='"+CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate())+"' and bo.session='"+examScheduleForm.getSession()+"'";
			ExamScheduleDate bo=transaction.validateSessionAndSessionorder(examScheduleForm,str);
			String sesion="sesion";
			if(bo!=null)
			ExamScheduleHelper.getInstance().checkingDuplicateVenueAndInvigilator(bo,request,sesion,examschList);
			String str1 = "select bo from ExamScheduleDate bo where bo.isActive = 1 and" +
			" bo.examDate='"+CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate())+"' and bo.sessionOrder='"+examScheduleForm.getSessionOrder()+"'";
			ExamScheduleDate bo1=transaction.validateSessionAndSessionorder(examScheduleForm,str1);
			String sesionorder="sesionorder";
			ExamScheduleHelper.getInstance().checkingDuplicateVenueAndInvigilator(bo1,request,sesionorder,examschList);
		}
		ExamScheduleDate bo2=ExamScheduleHelper.getInstance().convertTotoBo(examschList,examScheduleForm,request);
		boolean isSaved=transaction.saveVenueAndInvigilators(bo2);
	return isSaved;	
	}
	
	/**
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamScheduleTo> getVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		List<ExamScheduleDate> boList=transaction.getVenueAndInvigilatorsByMonthAndYear(examScheduleForm);
		List<ExamScheduleTo> ToList=ExamScheduleHelper.getInstance().convertBotoTo(boList);
		return ToList;
	}
	
	/**
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamScheduleTo> editVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		ExamScheduleDate bo=transaction.getVenueAndInvigilatorDetails(examScheduleForm);
		List<ExamScheduleTo> ToList=ExamScheduleHelper.getInstance().convertBotoToInEditMode(bo,examScheduleForm);
		return ToList;
	}
	
	/**
	 * @param examschList
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateVenueDetails(List<ExamScheduleTo> examschList,ExamScheduleForm examScheduleForm,HttpServletRequest request)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		if((examScheduleForm.getSession()!=null && !examScheduleForm.getSession().isEmpty()) &&
				(examScheduleForm.getSessionOrder()!=null && !examScheduleForm.getSessionOrder().isEmpty())&&
				(examScheduleForm.getExamDate()!=null && !examScheduleForm.getExamDate().isEmpty())){
			String str = "select bo from ExamScheduleDate bo where bo.isActive = 1 and " +
			" bo.examDate='"+CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate())+"' and bo.session='"+examScheduleForm.getSession()+"'";
			ExamScheduleDate bo=transaction.validateSessionAndSessionorder(examScheduleForm,str);
			if(bo!=null && (bo.getId()!=examScheduleForm.getExamScheduleDateId())){
				String sesion="sesion";
				ExamScheduleHelper.getInstance().checkingDuplicateVenueAndInvigilator(bo,request,sesion,examschList);
			}
			String str1 = "select bo from ExamScheduleDate bo where bo.isActive = 1 and" +
			" bo.examDate='"+CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate())+"' and bo.sessionOrder="+examScheduleForm.getSessionOrder();
			ExamScheduleDate bo1=transaction.validateSessionAndSessionorder(examScheduleForm,str1);
			if(bo1!=null && (bo1.getId()!=examScheduleForm.getExamScheduleDateId())){
				String sesionorder="sesionorder";
				ExamScheduleHelper.getInstance().checkingDuplicateVenueAndInvigilator(bo1,request,sesionorder,examschList);
			}
		}
		Set<ExamScheduleVenue> venueList=transaction.getVenueList(examScheduleForm);
		ExamScheduleDate examSchbo=ExamScheduleHelper.getInstance().convertTotoBoInUpdateMode(examschList,examScheduleForm,venueList,request);
		boolean isSaved=transaction.updateVenueAndInvigilators(examSchbo);
	return isSaved;	
	}
	
	/**
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		boolean isDeleted=transaction.deleteVenueAndInvigilatorDetails(examScheduleForm);
		return isDeleted;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteVenueData(int id)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		boolean isDeleted=transaction.deleteVenue(id);
		return isDeleted;
	}
	/**
	 * @param examschList
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamScheduleTo> removeSelectedToFromTolist(List<ExamScheduleTo> examschList,ExamScheduleForm examScheduleForm)throws Exception{
		List<ExamScheduleTo> newExamschList=new ArrayList<ExamScheduleTo>();
		ListIterator<ExamScheduleTo> itr=examschList.listIterator();
		Map<Integer,String> mainMap=examScheduleForm.getMainTeachersMap();
		int count=0;
		
		while (itr.hasNext()) {
			ExamScheduleTo newTo=new ExamScheduleTo();
				ExamScheduleTo to=itr.next();
				
				if(to.getDeleteCount()!=examScheduleForm.getDeleteCount()){	
					count=count+1;
					if(to.getId()!=0)
					newTo.setId(to.getId())	;
					
					if(to.getExamSchVenueId()!=0)
						newTo.setExamSchVenueId(to.getExamSchVenueId());
					if(to.getUsersBOIdMap()!=null && !to.getUsersBOIdMap().isEmpty())
						newTo.setUsersBOIdMap(to.getUsersBOIdMap());
					
					newTo.setWorkLocationMap(examScheduleForm.getWorkLocationMap());
					if(to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())
					newTo.setWorkLocation(to.getWorkLocation());
					
					if(to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())
						newTo.setVenueMap(CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation())));
					
					if(to.getVenue()!=null && !to.getVenue().isEmpty())
					newTo.setVenue(to.getVenue());
					
					if(to.getPriority()!=null && !to.getPriority().isEmpty())
						newTo.setPriority(to.getPriority());
					
					if(to.getTeachesFrom()!=null ){
					String[] str = to.getTeachesFrom();
					Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
					for (int x = 0; x < str.length; x++) {
						if (str[x] != null && str[x].length() > 0) {
							if(mainMap.containsKey(Integer.parseInt(str[x]))){
								String name=mainMap.get(Integer.parseInt(str[x]));
								fromTeachersMap.put(Integer.parseInt(str[x]),name);
							}
						}
					}
					fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
					newTo.setTeachersMap(fromTeachersMap);
				}else{
					newTo.setTeachersMap(mainMap);
				}
					
				if(to.getTeachesTo()!=null){	
					String[] str1 = to.getTeachesTo();
					Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
					for (int x = 0; x < str1.length; x++) {
						if (str1[x] != null && str1[x].length() > 0) {
							if(mainMap.containsKey(Integer.parseInt(str1[x]))){
								String name=mainMap.get(Integer.parseInt(str1[x]));
								
								toTeachersMap.put(Integer.parseInt(str1[x]),name);
							}
						}
					}
					toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
					newTo.setSelectedTeachersMap(toTeachersMap);
						}
					newTo.setIsActive(true);
					newTo.setDeleteCount(count);
					newExamschList.add(newTo);
					}else {
						if(to.getExamSchVenueId()!=0){
							boolean isDeleted=ExamScheduleHandler.getInstance().deleteVenueData(to.getExamSchVenueId());
						}
					}
				}	
		examScheduleForm.setExamSchToList(newExamschList);
		examScheduleForm.setExamScheduleToListSize(String.valueOf(newExamschList.size()));
		return newExamschList;
	}
	
	


}
