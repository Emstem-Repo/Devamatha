package com.kp.cms.handlers.exam;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamEndDate;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamEndDateForm;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.helpers.exam.ExamEndDateHelper;
import com.kp.cms.to.exam.ExamEndDateTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.IExamEndDates;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamEndDatesImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;



public class ExamEndDatesHandlers {
	private static volatile ExamEndDatesHandlers examEndDatesHandlers = null;
	private static final Log log = LogFactory.getLog(ExamEndDatesHandlers.class);
	private ExamEndDatesHandlers() {
		
	}
	
	/**
	 * @return
	 */
	public static ExamEndDatesHandlers getInstance() {
		if (examEndDatesHandlers == null) {
			examEndDatesHandlers = new ExamEndDatesHandlers();
		}
		return examEndDatesHandlers;
	}
	public List<ExamEndDateTo>getActiveData() throws Exception {
		String query="from ExamEndDate e where e.isActive=1";
		 IExamEndDates transaction=ExamEndDatesImpl.getInstance();
		List<ExamEndDate> boList=transaction.getDataForQuery(query);
		return ExamEndDateHelper.getInstance().convertBotoToList(boList);
	}
	public boolean addOrUpdate( ExamEndDateForm examEndDateFormForm) throws Exception {
		log.info("Entered into addOrUpdate");
		String query="from ExamEndDate p where p.exam.id="+examEndDateFormForm.getExamId();
		ExamEndDate  exists=(ExamEndDate) PropertyUtil.getDataForUniqueObject(query);
		if(exists!=null){
			if(exists.getId()!=examEndDateFormForm.getId()){
				if(exists.getIsActive())
					throw new DuplicateException();
				else{
					examEndDateFormForm.setId(exists.getId());
					throw new ReActivateException();
				}
			}
		}
		ExamEndDate bo=new ExamEndDate();
		if(examEndDateFormForm.getMode().equalsIgnoreCase("update")){
			bo.setId(examEndDateFormForm.getId());
		}else{
			bo.setCreatedBy(examEndDateFormForm.getUserId());
			bo.setCreatedDate(new Date());
		}
		bo.setModifiedBy(examEndDateFormForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		ExamDefinition exam=new ExamDefinition();
		exam.setId(Integer.parseInt(examEndDateFormForm.getExamId()));
		bo.setExam(exam);
		if(examEndDateFormForm.getEndDate()!=null)
		bo.setEndDate(CommonUtil.ConvertStringToSQLDate(examEndDateFormForm.getEndDate()));	
		log.info("Exit from addOrUpdate");
		if(examEndDateFormForm.getMode().equalsIgnoreCase("update")){
			return PropertyUtil.getInstance().update(bo);
		}else
			return PropertyUtil.getInstance().save(bo);
	}
	
	/**
	 * @param examEndDateFormForm
	 * @throws Exception
	 */
	public void getDetailsById( ExamEndDateForm examEndDateFormForm) throws Exception{
		IExamEndDates transaction=ExamEndDatesImpl.getInstance();
		String query="from ExamEndDate e where e.id="+examEndDateFormForm.getId();
		List<ExamEndDate> boList=transaction.getDataFromTable(query);
		for(int i=0;i<boList.size();i++)
		{
			ExamEndDate bo=(ExamEndDate)boList.get(i);
		examEndDateFormForm.setExamId(String.valueOf(bo.getExam().getId()));
		examEndDateFormForm.setYear(String.valueOf(bo.getExam().getAcademicYear()));
			if(bo.getEndDate()!=null)
			examEndDateFormForm.setEndDate(CommonUtil.formatSqlDate(bo.getEndDate().toString()));
		}
	}
	public boolean deleteOrReactivate(int id, String mode,String userId) throws Exception {
		log.info("Entered into deleteOrReactivate");
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		ExamEndDate bo=(ExamEndDate) transaction.getMasterEntryDataById(ExamEndDate.class,id);
		if(mode.equalsIgnoreCase("delete"))
			bo.setIsActive(false);
		else
			bo.setIsActive(true);
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		log.info("Exit from deleteOrReactivate");
		return PropertyUtil.getInstance().update(bo);
	}
}

