package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.to.exam.ExtendSupplyImprApplDateTo;
import com.kp.cms.utilities.CommonUtil;

public class ExtendSupplyImprApplDateHelper {
	private static volatile ExtendSupplyImprApplDateHelper extendSupplyImprApplHelper = null;
	private static final Log log = LogFactory.getLog(ExtendSupplyImprApplDateHelper.class);
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHandler.
	 * @return
	 */
	public static ExtendSupplyImprApplDateHelper getInstance() {
		if (extendSupplyImprApplHelper == null) {
			extendSupplyImprApplHelper = new ExtendSupplyImprApplDateHelper();
		}
		return extendSupplyImprApplHelper;
	}
	public List<ExtendSupplyImprApplDateTo> convertBoToTos(
			List<PublishSupplementaryImpApplication> list) throws Exception{
		List<ExtendSupplyImprApplDateTo> extendSupplyImprApplDateTos=new ArrayList<ExtendSupplyImprApplDateTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<PublishSupplementaryImpApplication> iterator=list.iterator();
			ExtendSupplyImprApplDateTo extendSupplyImprApplDateTo=null;
			while (iterator.hasNext()) {
				PublishSupplementaryImpApplication publishSupplementaryImpApplication = (PublishSupplementaryImpApplication) iterator.next();
				extendSupplyImprApplDateTo=new ExtendSupplyImprApplDateTo();
				extendSupplyImprApplDateTo.setId(publishSupplementaryImpApplication.getId());
				if(publishSupplementaryImpApplication.getClassCode()!=null){
					extendSupplyImprApplDateTo.setClassName(publishSupplementaryImpApplication.getClassCode().getName());
				}
				if(publishSupplementaryImpApplication.getStartDate()!=null){
					extendSupplyImprApplDateTo.setStartDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getStartDate()));
				}
				if(publishSupplementaryImpApplication.getEndDate()!=null){
					extendSupplyImprApplDateTo.setEndDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getEndDate()));
				}
				if(publishSupplementaryImpApplication.getExtendedDate()!=null){
					extendSupplyImprApplDateTo.setExtendedDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getExtendedDate()));
				}
				if(publishSupplementaryImpApplication.getExtendedFineDate()!=null){
					extendSupplyImprApplDateTo.setExtendedFineDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getExtendedFineDate()));
				}
				if(publishSupplementaryImpApplication.getExtendedSuperFineDate()!=null){
					extendSupplyImprApplDateTo.setExtendedSuperFineDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getExtendedSuperFineDate()));

				}
				if(publishSupplementaryImpApplication.getFineAmount()!=null){
					extendSupplyImprApplDateTo.setFineAmount(publishSupplementaryImpApplication.getFineAmount());
				}
				if(publishSupplementaryImpApplication.getSuperFineAmount()!=null){
					extendSupplyImprApplDateTo.setSuperFineAmount(publishSupplementaryImpApplication.getSuperFineAmount());
				}
				
				if(publishSupplementaryImpApplication.getExtendedFineStartDate()!=null){
					extendSupplyImprApplDateTo.setExtendedFineStartDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getExtendedFineStartDate()));
				}
				if(publishSupplementaryImpApplication.getExtendedSuperFineStartDate()!=null){
					extendSupplyImprApplDateTo.setExtendedSuperFineStartDate(CommonUtil.formatDates(publishSupplementaryImpApplication.getExtendedSuperFineStartDate()));

				}
				extendSupplyImprApplDateTos.add(extendSupplyImprApplDateTo);
			}
		}
		return extendSupplyImprApplDateTos;
	}
	
	public List<ExtendSupplyImprApplDateTo> convertBoToTosRegular(
			List<ExamPublishHallTicketMarksCardBO> list) throws Exception{
		List<ExtendSupplyImprApplDateTo> extendSupplyImprApplDateTos=new ArrayList<ExtendSupplyImprApplDateTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamPublishHallTicketMarksCardBO> iterator=list.iterator();
			ExtendSupplyImprApplDateTo extendSupplyImprApplDateTo=null;
			while (iterator.hasNext()) {
				ExamPublishHallTicketMarksCardBO publishRegular = (ExamPublishHallTicketMarksCardBO) iterator.next();
				extendSupplyImprApplDateTo=new ExtendSupplyImprApplDateTo();
				extendSupplyImprApplDateTo.setId(publishRegular.getId());
				if(publishRegular.getClasses()!=null){
					extendSupplyImprApplDateTo.setClassName(publishRegular.getClasses().getName());
				}
				if(publishRegular.getDownloadStartDate()!=null){
					extendSupplyImprApplDateTo.setStartDate(CommonUtil.formatDates(publishRegular.getDownloadStartDate()));
				}
				if(publishRegular.getDownloadEndDate()!=null){
					extendSupplyImprApplDateTo.setEndDate(CommonUtil.formatDates(publishRegular.getDownloadEndDate()));
				}
				if(publishRegular.getExtendedDate()!=null){
					extendSupplyImprApplDateTo.setExtendedDate(CommonUtil.formatDates(publishRegular.getExtendedDate()));
				}
				if(publishRegular.getExtendedFineDate()!=null){
					extendSupplyImprApplDateTo.setExtendedFineDate(CommonUtil.formatDates(publishRegular.getExtendedFineDate()));
				}
				if(publishRegular.getExtendedSuperFineDate()!=null){
					extendSupplyImprApplDateTo.setExtendedSuperFineDate(CommonUtil.formatDates(publishRegular.getExtendedSuperFineDate()));

				}
				if(publishRegular.getFineAmount()!=null){
					extendSupplyImprApplDateTo.setFineAmount(publishRegular.getFineAmount());
				}
				if(publishRegular.getSuperFineAmount()!=null){
					extendSupplyImprApplDateTo.setSuperFineAmount(publishRegular.getSuperFineAmount());
				}
				
				if(publishRegular.getExtendedFineStartDate()!=null){
					extendSupplyImprApplDateTo.setExtendedFineStartDate(CommonUtil.formatDates(publishRegular.getExtendedFineStartDate()));
				}
				if(publishRegular.getExtendedSuperFineStartDate()!=null){
					extendSupplyImprApplDateTo.setExtendedSuperFineStartDate(CommonUtil.formatDates(publishRegular.getExtendedSuperFineStartDate()));

				}
				extendSupplyImprApplDateTos.add(extendSupplyImprApplDateTo);
			}
		}
		return extendSupplyImprApplDateTos;
	}
}
