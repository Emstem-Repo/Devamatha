package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.helpers.exam.PublishSupplementaryImpApplicationHelper;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IPublishSupplementaryImpApplicationTxn;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamPublishHallTicketImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.PublishSupplementaryImpApplicationTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class PublishSupplementaryImpApplicationHandler {
	/**
	 * Singleton object of PublishSupplementaryImpApplicationHandler
	 */
	private static volatile PublishSupplementaryImpApplicationHandler publishSupplementaryImpApplicationHandler = null;
	private static final Log log = LogFactory.getLog(PublishSupplementaryImpApplicationHandler.class);
	private PublishSupplementaryImpApplicationHandler() {
		
	}
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHandler.
	 * @return
	 */
	public static PublishSupplementaryImpApplicationHandler getInstance() {
		if (publishSupplementaryImpApplicationHandler == null) {
			publishSupplementaryImpApplicationHandler = new PublishSupplementaryImpApplicationHandler();
		}
		return publishSupplementaryImpApplicationHandler;
	}
	/**
	 * @param publishSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public boolean addOrUpdate(PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm,ActionErrors errors) throws Exception {
		log.info("Entered into addOrUpdate");
		boolean isAddOrUpdate=false;
		if(!publishSupplementaryImpApplicationForm.getStayClass()[0].isEmpty() && publishSupplementaryImpApplicationForm.getStayClass()[0]!=null
				&& publishSupplementaryImpApplicationForm.getStayClass().toString().length()>0)
	{
			
			StringBuffer classNames=new StringBuffer();
			String className=null;
			List<PublishSupplementaryImpApplication> list=new ArrayList<PublishSupplementaryImpApplication>();
			//ExamPublishHallTicketImpl impl=new ExamPublishHallTicketImpl();
			for (String i : publishSupplementaryImpApplicationForm.getStayClass()) {
				StringTokenizer tokens = new StringTokenizer(i, ",");
				while (tokens.hasMoreElements()) {
				PublishSupplementaryImpApplication bo=new PublishSupplementaryImpApplication();
				int val = Integer.parseInt(tokens.nextElement().toString());
				if(publishSupplementaryImpApplicationForm.getMode().equalsIgnoreCase("update")){
					bo.setId(publishSupplementaryImpApplicationForm.getId());
				}else{
					bo.setCreatedBy(publishSupplementaryImpApplicationForm.getUserId());
 					bo.setCreatedDate(new Date());
				}
				bo.setModifiedBy(publishSupplementaryImpApplicationForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setIsActive(true);
				ExamDefinition exam=new ExamDefinition();
				exam.setId(Integer.parseInt(publishSupplementaryImpApplicationForm.getExamId()));
				bo.setExam(exam);
				if(publishSupplementaryImpApplicationForm.getStartDate()!=null)
				bo.setStartDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getStartDate()));
				if(publishSupplementaryImpApplicationForm.getEndDate()!=null)
				bo.setEndDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getEndDate()));
				
				if(publishSupplementaryImpApplicationForm.getExtendedDate()!=null)
					bo.setExtendedDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getExtendedDate()));
				if(publishSupplementaryImpApplicationForm.getFineStartDate()!=null)
					bo.setExtendedFineStartDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getFineStartDate()));
				if(publishSupplementaryImpApplicationForm.getFineEndDate()!=null)
					bo.setExtendedFineDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getFineEndDate()));
				if(publishSupplementaryImpApplicationForm.getSuperFIneStartDate()!=null)
					bo.setExtendedSuperFineStartDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getSuperFIneStartDate()));
				if(publishSupplementaryImpApplicationForm.getSuperFineEndDate()!=null)
					bo.setExtendedSuperFineDate(CommonUtil.ConvertStringToSQLDate(publishSupplementaryImpApplicationForm.getSuperFineEndDate()));
				if(publishSupplementaryImpApplicationForm.getFineFee()!=null)
					bo.setFineAmount(publishSupplementaryImpApplicationForm.getFineFee());
				if(publishSupplementaryImpApplicationForm.getSuperFineFee()!=null)
					bo.setSuperFineAmount(publishSupplementaryImpApplicationForm.getSuperFineFee());
				Classes classId=new Classes();
				classId.setId(val);
				bo.setClassCode(classId);
				int examId=Integer.parseInt(publishSupplementaryImpApplicationForm.getExamId());
				IPublishSupplementaryImpApplicationTxn txnImpl=PublishSupplementaryImpApplicationTxnImpl.getInstance();
				className=txnImpl.isDuplicate(publishSupplementaryImpApplicationForm.getId(), examId, val);
				if(!className.isEmpty()){
					if(classNames.length()>0){
						classNames.append(","+className);
						} else
							classNames.append(className);
				}
				
				list.add(bo);
				
			}
			}
		   if(classNames.length()>0){
				throw new DuplicateException(classNames.toString());
			}
			Iterator iterator=list.iterator();
			while (iterator.hasNext()) {
				PublishSupplementaryImpApplication impApplication = (PublishSupplementaryImpApplication) iterator.next();
				if(publishSupplementaryImpApplicationForm.getMode().equalsIgnoreCase("update")){
					isAddOrUpdate=PropertyUtil.getInstance().update(impApplication);
				}else
					isAddOrUpdate= PropertyUtil.getInstance().save(impApplication);
			}
			
	}
		log.info("Exit from addOrUpdate");
		return isAddOrUpdate;
		
	}
	/**
	 * @param id
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOrReactivate(int id, String mode,String userId) throws Exception {
		log.info("Entered into deleteOrReactivate");
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		PublishSupplementaryImpApplication bo=(PublishSupplementaryImpApplication) transaction.getMasterEntryDataById(PublishSupplementaryImpApplication.class,id);
		if(mode.equalsIgnoreCase("delete"))
			bo.setIsActive(false);
		else
			bo.setIsActive(true);
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		log.info("Exit from deleteOrReactivate");
		return PropertyUtil.getInstance().update(bo);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<PublishSupplementaryImpApplicationTo> getActiveData() throws Exception {
		String query="from PublishSupplementaryImpApplication p where p.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<PublishSupplementaryImpApplication> boList=transaction.getDataForQuery(query);
		return PublishSupplementaryImpApplicationHelper.getInstance().convertBotoToList(boList);
	}
	/**
	 * @param publishSupplementaryImpApplicationForm
	 */
	public void getDetailsById( PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm) throws Exception{
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		PublishSupplementaryImpApplication bo=(PublishSupplementaryImpApplication) transaction.getMasterEntryDataById(PublishSupplementaryImpApplication.class,publishSupplementaryImpApplicationForm.getId());
		publishSupplementaryImpApplicationForm.setExamId(String.valueOf(bo.getExam().getId()));
		publishSupplementaryImpApplicationForm.setYear(String.valueOf(bo.getExam().getAcademicYear()));
		if(bo.getStartDate()!=null)
		publishSupplementaryImpApplicationForm.setStartDate(CommonUtil.formatSqlDate(bo.getStartDate().toString()));
		if(bo.getEndDate()!=null)
		publishSupplementaryImpApplicationForm.setEndDate(CommonUtil.formatSqlDate(bo.getEndDate().toString()));
		if(bo.getExtendedDate()!=null)
			publishSupplementaryImpApplicationForm.setExtendedDate(CommonUtil.formatSqlDate(bo.getExtendedDate().toString()));
		if(bo.getExtendedFineStartDate()!=null)
			publishSupplementaryImpApplicationForm.setFineStartDate(CommonUtil.formatSqlDate(bo.getExtendedFineStartDate().toString()));
		if(bo.getExtendedFineDate()!=null)
			publishSupplementaryImpApplicationForm.setFineEndDate(CommonUtil.formatSqlDate(bo.getExtendedFineDate().toString()));
		if(bo.getExtendedSuperFineStartDate()!=null)
			publishSupplementaryImpApplicationForm.setSuperFIneStartDate(CommonUtil.formatSqlDate(bo.getExtendedSuperFineStartDate().toString()));
		if(bo.getExtendedSuperFineDate()!=null)
			publishSupplementaryImpApplicationForm.setSuperFineEndDate(CommonUtil.formatSqlDate(bo.getExtendedSuperFineDate().toString()));
		if(bo.getFineAmount()!=null)
			publishSupplementaryImpApplicationForm.setFineFee(bo.getFineAmount());
		if(bo.getSuperFineAmount()!=null)
			publishSupplementaryImpApplicationForm.setSuperFineFee(bo.getSuperFineAmount());
		Map<Integer, String> classMap=loadClassByExamNameAndYear(publishSupplementaryImpApplicationForm);
		if(classMap!=null &&!classMap.isEmpty())
		{
			classMap=CommonUtil.sortMapByValueForAlphaNumeric(classMap);
			publishSupplementaryImpApplicationForm.setMapClass(classMap);
		}
		if(bo.getClassCode()!=null)
		{
			Map<Integer, String> mapClass=new HashMap<Integer, String>();
			mapClass.put(bo.getClassCode().getId(), bo.getClassCode().getName());
			mapClass=CommonUtil.sortMapByValueForAlphaNumeric(mapClass);
			publishSupplementaryImpApplicationForm.setMapSelectedClass(mapClass);
			String[] classIds={Integer.toString(bo.getClassCode().getId())};
			publishSupplementaryImpApplicationForm.setClassCodeIdsTo(classIds);
		}
	}
	
	public Map<Integer, String> loadClassByExamNameAndYear(PublishSupplementaryImpApplicationForm actionForm) throws Exception
	{
		IPublishSupplementaryImpApplicationTxn applicationTxn=PublishSupplementaryImpApplicationTxnImpl.getInstance();
		List applicationBOList =applicationTxn.loadClassByExamNameAndYear(actionForm);
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		if(applicationBOList!=null )
		{
		Iterator iterator=applicationBOList.iterator();
		while(iterator.hasNext())
		{
		 	Object[] objects=(Object[]) iterator.next();
		 	if(objects[0]!=null && objects[1]!=null && objects[2]!=null )
		 	{
			classMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString()+"("+objects[2]+")");
		 	}
		}
		}
		return classMap;
		
	}
	public List<PublishSupplementaryImpApplicationTo> getListOnYearChange(String year, String examId) throws Exception{
		String query="from PublishSupplementaryImpApplication p where p.isActive=1 and p.exam.academicYear="+year+"and p.exam.id="+examId;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<PublishSupplementaryImpApplication> boList=transaction.getDataForQuery(query);
		return PublishSupplementaryImpApplicationHelper.getInstance().convertBotoToList(boList);
	}
}
