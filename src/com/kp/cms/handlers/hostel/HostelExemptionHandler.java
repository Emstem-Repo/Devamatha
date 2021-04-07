package com.kp.cms.handlers.hostel;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.hostel.HostelExemptionBo;
import com.kp.cms.bo.hostel.HostelExemptionDetailsBo;
import com.kp.cms.forms.hostel.HostelExemptionForm;
import com.kp.cms.helpers.hostel.HostelExemptionhelper;
import com.kp.cms.to.hostel.HostelExemptionTo;
import com.kp.cms.transactions.hostel.IHostelExemptionTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelExemptionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class HostelExemptionHandler {
	private static final Log log = LogFactory.getLog(HostelExemptionHandler.class);
	public static volatile HostelExemptionHandler hostelExemptionHandler=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static HostelExemptionHandler getInstance(){
		if(hostelExemptionHandler==null){
			hostelExemptionHandler= new HostelExemptionHandler();
			}
		return hostelExemptionHandler;
	}
	
	public List<HostelExemptionTo> getHostelStudentDataForExemption(HostelExemptionForm hostelExemptionForm)throws Exception{
		IHostelExemptionTransaction transaction=HostelExemptionTransactionImpl.getInstance();
		List<HlAdmissionBo> boList=transaction.getHostelStudentData(hostelExemptionForm);
		Map<Integer,Integer> alreadySavedData=transaction.getHostelExemptionData(hostelExemptionForm);
		if(!alreadySavedData.isEmpty())
		hostelExemptionForm.setAlreadySavedData(alreadySavedData);
		List<HostelExemptionTo> toList=HostelExemptionhelper.getInstance().convertBotoTo(boList,alreadySavedData,hostelExemptionForm);
		return toList;
	}
	public boolean saveHostelExemptionDetails(List<HostelExemptionTo> hostelExemptionList,HostelExemptionForm hostelExemptionForm ) throws Exception{
		IHostelExemptionTransaction transaction=HostelExemptionTransactionImpl.getInstance();
		HostelExemptionBo bo=new HostelExemptionBo();
		Map<Integer,Integer> savedDataMap=hostelExemptionForm.getAlreadySavedData();
		Map<Integer,Integer> hlExpIdMap=hostelExemptionForm.getHlExpIdMap();
		if(hostelExemptionList != null && !hostelExemptionList.isEmpty()){
			Set<HostelExemptionDetailsBo> exemptionDetails=new HashSet<HostelExemptionDetailsBo>();
			Iterator<HostelExemptionTo> iterator = hostelExemptionList.iterator();
			while (iterator.hasNext()) {
				
				HostelExemptionTo to = (HostelExemptionTo) iterator.next();
				if(to.getChecked1() != null && to.getChecked1().equalsIgnoreCase("on")){
					// /* again we have to select the already existed record then we have to update the record 
					if(hlExpIdMap!=null && !hlExpIdMap.isEmpty()){
						if(hlExpIdMap.containsKey(to.getHlAdmitionId()))
						bo.setId(hlExpIdMap.get(to.getHlAdmitionId()));
					}
				if(savedDataMap !=null && !savedDataMap.isEmpty())	{
					if(savedDataMap.containsKey(to.getHlAdmitionId())){
						
						HostelExemptionDetailsBo exemptionBo=new HostelExemptionDetailsBo();
						HlAdmissionBo admBo=new HlAdmissionBo();
						admBo.setId(to.getHlAdmitionId());
						exemptionBo.setId(savedDataMap.get(to.getHlAdmitionId()));
						exemptionBo.setHlAdmissionBo(admBo);
						exemptionBo.setModifiedBy(hostelExemptionForm.getUserId());
						exemptionBo.setLastModifiedDate(new Date());
						exemptionBo.setIsActive(true);
						exemptionDetails.add(exemptionBo);
						
					}// */  
					// /* inseret the new record
					else{
						HostelExemptionDetailsBo exemptionBo=new HostelExemptionDetailsBo();
						HlAdmissionBo admBo=new HlAdmissionBo();
						admBo.setId(to.getHlAdmitionId());
						exemptionBo.setHlAdmissionBo(admBo);
						exemptionBo.setCreatedBy(hostelExemptionForm.getUserId());
						exemptionBo.setCreatedDate(new Date());
						exemptionBo.setModifiedBy(hostelExemptionForm.getUserId());
						exemptionBo.setLastModifiedDate(new Date());
						exemptionBo.setIsActive(true);
						exemptionDetails.add(exemptionBo);
					}// */
				}else{
					HostelExemptionDetailsBo exemptionBo=new HostelExemptionDetailsBo();
					HlAdmissionBo admBo=new HlAdmissionBo();
					admBo.setId(to.getHlAdmitionId());
					exemptionBo.setHlAdmissionBo(admBo);
					exemptionBo.setCreatedBy(hostelExemptionForm.getUserId());
					exemptionBo.setCreatedDate(new Date());
					exemptionBo.setModifiedBy(hostelExemptionForm.getUserId());
					exemptionBo.setLastModifiedDate(new Date());
					exemptionBo.setIsActive(true);
					exemptionDetails.add(exemptionBo);
				}
				}else{
					// /* if we de select the already existed record then delete that record
				if(savedDataMap!=null && !savedDataMap.isEmpty()){
					if(savedDataMap.containsKey(to.getHlAdmitionId())){
						if(hlExpIdMap!=null && !hlExpIdMap.isEmpty()){
							if(hlExpIdMap.containsKey(to.getHlAdmitionId()))
							bo.setId(hlExpIdMap.get(to.getHlAdmitionId()));
						}
						HostelExemptionDetailsBo exemptionBo=new HostelExemptionDetailsBo();
						HlAdmissionBo admBo=new HlAdmissionBo();
						admBo.setId(to.getHlAdmitionId());
						exemptionBo.setHlAdmissionBo(admBo);
						exemptionBo.setId(savedDataMap.get(to.getHlAdmitionId()));
						exemptionBo.setModifiedBy(hostelExemptionForm.getUserId());
						exemptionBo.setLastModifiedDate(new Date());
						exemptionBo.setIsActive(false);
						exemptionDetails.add(exemptionBo);
						}
					}
				}
			}
			Map<Integer,Integer> inActivehlExpIdMap=hostelExemptionForm.getInActveHlExpIdMap();
			if(inActivehlExpIdMap!=null){
				 for (Integer key : inActivehlExpIdMap.keySet()) {
					 HostelExemptionDetailsBo exemptionBo=transaction.getInActiveHlExData(key);
							if(exemptionBo!=null){
								exemptionDetails.add(exemptionBo);
							}
						}
			 		}
			bo.setHostelExemptionDetailsBo(exemptionDetails);
			int hostelId=Integer.parseInt(hostelExemptionForm.getHostelId());
			HlHostel hostel=new HlHostel();
			hostel.setId(hostelId);
			bo.setHostelId(hostel);
			bo.setFromDate(CommonUtil.ConvertStringToSQLDate(hostelExemptionForm.getHolidaysFrom()));
			bo.setToDate(CommonUtil.ConvertStringToSQLDate(hostelExemptionForm.getHolidaysTo()));
			bo.setFromSession(hostelExemptionForm.getHolidaysFromSession());
			bo.setToSession(hostelExemptionForm.getHolidaysToSession());
			bo.setReason(hostelExemptionForm.getReason());
			bo.setCreatedBy(hostelExemptionForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(hostelExemptionForm.getUserId());
			bo.setLastModifiedDate(new Date());
			bo.setIsActive(true);
		}
		boolean isSaved=transaction.saveHostelExemptionDetails(bo);
		return isSaved;
	}
}
