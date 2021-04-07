package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.forms.exam.ExtendSupplyImprovApplDateForm;
import com.kp.cms.helpers.exam.ExtendSupplyImprApplDateHelper;
import com.kp.cms.to.exam.ExtendSupplyImprApplDateTo;
import com.kp.cms.transactions.exam.IExtendSupplyImprApplDateTransaction;
import com.kp.cms.transactionsimpl.exam.ExtendSupplyImprApplDateTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExtendSupplyImprApplDateHandler {
	IExtendSupplyImprApplDateTransaction transaction=ExtendSupplyImprApplDateTransImpl.getInstance();
	ExtendSupplyImprApplDateHelper helper=ExtendSupplyImprApplDateHelper.getInstance();
	private static volatile ExtendSupplyImprApplDateHandler extendSupplyImprApplHandler = null;
	private static final Log log = LogFactory.getLog(ExtendSupplyImprApplDateHandler.class);
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHandler.
	 * @return
	 */
	public static ExtendSupplyImprApplDateHandler getInstance() {
		if (extendSupplyImprApplHandler == null) {
			extendSupplyImprApplHandler = new ExtendSupplyImprApplDateHandler();
		}
		return extendSupplyImprApplHandler;
	}
	public List<ExtendSupplyImprApplDateTo> getExamsToExtend(
			ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm) throws Exception{
		List<ExtendSupplyImprApplDateTo> extendSupplyImprApplDateTos=null;
		if(extendSupplyImprovApplDateForm.getExamType().equals("Regular")) {
			List<ExamPublishHallTicketMarksCardBO> list=transaction.getExamsToExtendRegular(Integer.parseInt(extendSupplyImprovApplDateForm.getExamId()));
			extendSupplyImprApplDateTos=helper.convertBoToTosRegular(list);
		}
		else if(extendSupplyImprovApplDateForm.getExamType().equals("Supplementary")) {
			List<PublishSupplementaryImpApplication> list=transaction.getExamsToExtend(Integer.parseInt(extendSupplyImprovApplDateForm.getExamId()));
			extendSupplyImprApplDateTos=helper.convertBoToTos(list);
		}
		else if(extendSupplyImprovApplDateForm.getExamType().equals("5")) {		//	Internal Retest exam type id
			List<ExamPublishHallTicketMarksCardBO> list=transaction.getExamsToExtendInternalRedo(Integer.parseInt(extendSupplyImprovApplDateForm.getExamId()));
			extendSupplyImprApplDateTos=helper.convertBoToTosRegular(list);
		}
		return extendSupplyImprApplDateTos;
	}
	public boolean updatetheExtendedDate(
			ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm,
			HttpServletRequest request) throws Exception{
		boolean flag=false;
		//which are checked
		List<Integer> ids=new ArrayList<Integer>();
		//disabling the tos which are checked
		List<ExtendSupplyImprApplDateTo> extendSupplyImprApplDateTos=extendSupplyImprovApplDateForm.getToList();
		Iterator<ExtendSupplyImprApplDateTo> iterator=extendSupplyImprApplDateTos.iterator();
		//to check whether the extended end date is greater than or not
		while (iterator.hasNext()) {
			ExtendSupplyImprApplDateTo extendSupplyImprApplDateTo = (ExtendSupplyImprApplDateTo) iterator.next();
			if(extendSupplyImprApplDateTo.getChecked()!=null && !extendSupplyImprApplDateTo.getChecked().isEmpty() && extendSupplyImprApplDateTo.getChecked().equalsIgnoreCase("on")){
				if(CommonUtil.ConvertStringToSQLDate(extendSupplyImprApplDateTo.getEndDate()).compareTo(CommonUtil.ConvertStringToSQLDate(extendSupplyImprovApplDateForm.getExtendedEndDate()))>0){
					request.setAttribute("keys","key");
					throw new Exception();
				}
			}
		}
		//to update the selected records
		Iterator<ExtendSupplyImprApplDateTo> iterator1=extendSupplyImprApplDateTos.iterator();
		while (iterator1.hasNext()) {
			ExtendSupplyImprApplDateTo extendSupplyImprApplDateTo = (ExtendSupplyImprApplDateTo) iterator1.next();
			if(extendSupplyImprApplDateTo.getChecked()!=null && !extendSupplyImprApplDateTo.getChecked().isEmpty() && extendSupplyImprApplDateTo.getChecked().equalsIgnoreCase("on")){
				ids.add(extendSupplyImprApplDateTo.getId());
				extendSupplyImprApplDateTo.setChecked(null);
			}
		}
		if(extendSupplyImprovApplDateForm.getExamType().equals("Regular"))
			return flag=updateTheRecordsRegular(extendSupplyImprovApplDateForm,ids);
		else if(extendSupplyImprovApplDateForm.getExamType().equals("Supplementary"))
			return flag=updateTheRecords(extendSupplyImprovApplDateForm,ids);
		else if(extendSupplyImprovApplDateForm.getExamType().equals("5"))
			return flag=updateTheRecordsInternalRedo(extendSupplyImprovApplDateForm,ids);
		else
			return flag;
	}
	private boolean updateTheRecords( ExtendSupplyImprovApplDateForm form, List<Integer> ids) throws Exception{
		boolean flag=false;
		List<PublishSupplementaryImpApplication> pbApplications=new ArrayList<PublishSupplementaryImpApplication>();
		if(ids!=null && !ids.isEmpty()){
			List<PublishSupplementaryImpApplication> list=transaction.getBosToUpdate(ids);
				if(list!=null && !list.isEmpty()){
					Iterator<PublishSupplementaryImpApplication> iterator=list.iterator();
					while (iterator.hasNext()) {
						PublishSupplementaryImpApplication publishSupplementaryImpApplication = (PublishSupplementaryImpApplication) iterator.next();
						publishSupplementaryImpApplication.setExtendedDate(CommonUtil.ConvertStringToDate(form.getExtendedEndDate()));
						publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
						if(!form.getFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setFineAmount(form.getFineAmount());
						if(!form.getSuperFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setSuperFineAmount(form.getSuperFineAmount());
						pbApplications.add(publishSupplementaryImpApplication);
						if(!form.getExtendedSuperFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineStartDate()));
						if(!form.getExtendedSuperFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineEndDate()));
						if(!form.getExtendedFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedFineStartDate()));
						if(!form.getExtendedFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
					}
				}
		}
		if(pbApplications!=null){
			flag=transaction.updateTheData(pbApplications);
		}
		return flag;
	}
	
	private boolean updateTheRecordsRegular( ExtendSupplyImprovApplDateForm form, List<Integer> ids) throws Exception{
		boolean flag=false;
		List<ExamPublishHallTicketMarksCardBO> pbApplications=new ArrayList<ExamPublishHallTicketMarksCardBO>();
		if(ids!=null && !ids.isEmpty()){
			List<ExamPublishHallTicketMarksCardBO> list=transaction.getBosToUpdateRegular(ids);
				if(list!=null && !list.isEmpty()){
					Iterator<ExamPublishHallTicketMarksCardBO> iterator=list.iterator();
					while (iterator.hasNext()) {
						ExamPublishHallTicketMarksCardBO publishSupplementaryImpApplication = (ExamPublishHallTicketMarksCardBO) iterator.next();
						publishSupplementaryImpApplication.setExtendedDate(CommonUtil.ConvertStringToDate(form.getExtendedEndDate()));
						publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
						if(!form.getFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setFineAmount(form.getFineAmount());
						if(!form.getSuperFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setSuperFineAmount(form.getSuperFineAmount());
						pbApplications.add(publishSupplementaryImpApplication);
						if(!form.getExtendedSuperFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineStartDate()));
						if(!form.getExtendedSuperFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineEndDate()));
						if(!form.getExtendedFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedFineStartDate()));
						if(!form.getExtendedFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
					}
				}
		}
		if(pbApplications!=null){
			flag=transaction.updateTheDataRegular(pbApplications);
		}
		return flag;
	}
	
	private boolean updateTheRecordsInternalRedo( ExtendSupplyImprovApplDateForm form, List<Integer> ids) throws Exception{
		boolean flag=false;
		List<ExamPublishHallTicketMarksCardBO> pbApplications=new ArrayList<ExamPublishHallTicketMarksCardBO>();
		if(ids!=null && !ids.isEmpty()){
			List<ExamPublishHallTicketMarksCardBO> list=transaction.getBosToUpdateInternalRedo(ids);
				if(list!=null && !list.isEmpty()){
					Iterator<ExamPublishHallTicketMarksCardBO> iterator=list.iterator();
					while (iterator.hasNext()) {
						ExamPublishHallTicketMarksCardBO publishSupplementaryImpApplication = (ExamPublishHallTicketMarksCardBO) iterator.next();
						publishSupplementaryImpApplication.setExtendedDate(CommonUtil.ConvertStringToDate(form.getExtendedEndDate()));
						publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
						if(!form.getFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setFineAmount(form.getFineAmount());
						if(!form.getSuperFineAmount().equalsIgnoreCase(""))
						publishSupplementaryImpApplication.setSuperFineAmount(form.getSuperFineAmount());
						pbApplications.add(publishSupplementaryImpApplication);
						if(!form.getExtendedSuperFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineStartDate()));
						if(!form.getExtendedSuperFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedSuperFineDate(CommonUtil.ConvertStringToDate(form.getExtendedSuperFineEndDate()));
						if(!form.getExtendedFineStartDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineStartDate(CommonUtil.ConvertStringToDate(form.getExtendedFineStartDate()));
						if(!form.getExtendedFineEndDate().equalsIgnoreCase(""))
							publishSupplementaryImpApplication.setExtendedFineDate(CommonUtil.ConvertStringToDate(form.getExtendedFineEndDate()));
					}
				}
		}
		if(pbApplications!=null){
			flag=transaction.updateTheDataRegular(pbApplications);
		}
		return flag;
	}
}
