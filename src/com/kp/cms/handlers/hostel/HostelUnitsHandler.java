package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.HostelUnitsForm;
import com.kp.cms.helpers.hostel.HostelUnitsHelper;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelUnitsTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelUnitsTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelUnitsTransactionImpl;

public class HostelUnitsHandler {
	private static final Log log = LogFactory.getLog(HostelUnitsHandler.class);
	private static volatile HostelUnitsHandler hostelUnitsHandler = null;
	
	private HostelUnitsHandler() {
	}
	
	public static HostelUnitsHandler getInstance() {
		if (hostelUnitsHandler == null) {
			hostelUnitsHandler = new HostelUnitsHandler();
		}
		return hostelUnitsHandler;
	}
	
	IHostelUnitsTransaction transaction=new HostelUnitsTransactionImpl();
	
	public List<HostelUnitsTO> getHostelUnitsDetails()throws Exception
	{
		log.info("Inside of getHostelUnitsDetails of HostelUnitsHandler");
		List<HlUnits> unitsList =transaction.getHostelUnitsDetails();
		if(unitsList!=null && !unitsList.isEmpty()){
			return HostelUnitsHelper.getInstance().pupulateHlUnitsBOtoTO(unitsList);
		}
		log.info("Leaving from getHostelUnitsDetails of HostelUnitsHandler");
		return new ArrayList<HostelUnitsTO>();
	}
	
	public boolean addHostelUnits(HostelUnitsForm hostelUnitsForm)throws Exception
	{
		log.info("Inside addHostelUnits of HostelUnitsHandler");
		HostelUnitsTO hostelUnitsTO=new HostelUnitsTO();
		if(hostelUnitsForm!=null){
			hostelUnitsTO.setName(hostelUnitsForm.getName());
			hostelUnitsTO.setNoOfFloors(Integer.parseInt(hostelUnitsForm.getNoOfFloors()));
			HostelTO hostelTO=new HostelTO();
			hostelTO.setId(Integer.parseInt(hostelUnitsForm.getHostelId()));
			hostelUnitsTO.setHostelTO(hostelTO);
			
			HostelBlocksTO hostelBlocksTO = new HostelBlocksTO();
			hostelBlocksTO.setId(Integer.parseInt(hostelUnitsForm.getBlockId()));
			hostelUnitsTO.setHostelBlocksTO(hostelBlocksTO);
			hostelUnitsTO.setPrimaryContactDesignation(hostelUnitsForm.getPrimaryContactDesignation());
			hostelUnitsTO.setPrimaryContactEmail(hostelUnitsForm.getPrimaryContactEmail());
			hostelUnitsTO.setPrimaryContactMobile(hostelUnitsForm.getPrimaryContactMobile());
			hostelUnitsTO.setPrimaryContactName(hostelUnitsForm.getPrimaryContactName());
			hostelUnitsTO.setPrimaryContactPhone(hostelUnitsForm.getPrimaryContactPhone());
			hostelUnitsTO.setSecContactDesignation(hostelUnitsForm.getSecContactDesignation());
			hostelUnitsTO.setSecContactEmail(hostelUnitsForm.getSecContactEmail());
			hostelUnitsTO.setSecContactMobile(hostelUnitsForm.getSecContactMobile());
			hostelUnitsTO.setSecContactName(hostelUnitsForm.getSecContactName());
			hostelUnitsTO.setSecContactPhone(hostelUnitsForm.getSecContactPhone());
			//leave details
			hostelUnitsTO.setLeaveBeforeNoOfDays(hostelUnitsForm.getLeaveBeforeNoOfDays());
			hostelUnitsTO.setOnlineLeave(hostelUnitsForm.getOnlineLeave());
			if(hostelUnitsForm.getApplyBeforeHours()!=null && !hostelUnitsForm.getApplyBeforeHours().isEmpty()){
				hostelUnitsTO.setApplyBeforeHours(hostelUnitsForm.getApplyBeforeHours());
			}else{
				hostelUnitsTO.setApplyBeforeHours(null);
			}
			if(hostelUnitsForm.getApplyBeforeMin()!=null && !hostelUnitsForm.getApplyBeforeMin().isEmpty()){
				hostelUnitsTO.setApplyBeforeMin(hostelUnitsForm.getApplyBeforeMin());
			}else{
				hostelUnitsTO.setApplyBeforeMin(null);
			}
			if(hostelUnitsForm.getApplyBeforeNextDayHours()!=null && !hostelUnitsForm.getApplyBeforeNextDayHours().isEmpty()){
				hostelUnitsTO.setApplyBeforeNextDayHours(hostelUnitsForm.getApplyBeforeNextDayHours());
			}else{
				hostelUnitsTO.setApplyBeforeNextDayHours(null);
			}
			if(hostelUnitsForm.getApplyBeforeNextDayMin()!=null && !hostelUnitsForm.getApplyBeforeNextDayMin().isEmpty()){
				hostelUnitsTO.setApplyBeforeNextDayMin(hostelUnitsForm.getApplyBeforeNextDayMin());
			}else{
				hostelUnitsTO.setApplyBeforeNextDayMin(null);
			}
			hostelUnitsTO.setSmsForParents(hostelUnitsForm.getSmsForParents());
			hostelUnitsTO.setIntervalMails(hostelUnitsForm.getIntervalMails());
			hostelUnitsTO.setSmsForPrimaryCon(hostelUnitsForm.getSmsForPrimaryCon());
			hostelUnitsTO.setSmsForSecondCon(hostelUnitsForm.getSmsForSecondCon());
			hostelUnitsTO.setSmsOnMorning(hostelUnitsForm.getSmsOnMorning());
			hostelUnitsTO.setSmsOnEvening(hostelUnitsForm.getSmsOnEvening());
			hostelUnitsTO.setPunchExepSundaySession(hostelUnitsForm.getPunchExepSundaySession());
			
			
			hostelUnitsTO.setCreatedBy(hostelUnitsForm.getUserId());
			hostelUnitsTO.setCreatedDate(new Date());
			hostelUnitsTO.setModifiedBy(hostelUnitsForm.getUserId());
			hostelUnitsTO.setLastModifiedDate(new Date());
		}
		HlUnits hlUnits=HostelUnitsHelper.getInstance().populateTOtoBO(hostelUnitsTO);
		if(hlUnits!=null){
			return transaction.addHostelUnits(hlUnits);
		}
		log.info("Leaving of addHostelUnits of HostelUnitsHandler");
		return false;
	}
	
	public HlUnits checkForDuplicateonName(String blockId, String name)throws Exception
	{
		log.info("Inside into checkForDuplicateonName of HostelUnitsHandler");
		return transaction.checkForDuplicateonUnitsName(blockId, name);
	}
	
	public boolean deleteHostelUnits(int hostelUnitsId, String userId)throws Exception
	{
		log.info("Inside of deleteHostelBlocks of HostelBlocksHandler");
		if(transaction!=null){
			return transaction.deleteHostelUnits(hostelUnitsId, userId);
		}
		log.info("Leaving of deleteHostelUnits of HostelUnitsHandler");
		return false;
	}
	
	public boolean updateHostelUnits(HostelUnitsForm byForm)throws Exception
	{

		log.info("Inside of updateHostelUnits of HostelUnitsHandler");
		HostelUnitsTO byTO=new HostelUnitsTO();
		String name="";
		boolean orgName=false;
		name=byForm.getName();
		orgName = transaction.checkForDuplicateonNameSameId(name, byForm);
		if (orgName){
			throw new DuplicateException();
		}
		else{
		if(byForm!=null){
			byTO.setId(byForm.getId());
			byTO.setName(byForm.getName());
			byTO.setNoOfFloors(Integer.parseInt(byForm.getNoOfFloors()));
			byTO.setPrimaryContactDesignation(byForm.getPrimaryContactDesignation());
			byTO.setPrimaryContactEmail(byForm.getPrimaryContactEmail());
			byTO.setPrimaryContactMobile(byForm.getPrimaryContactMobile());
			byTO.setPrimaryContactName(byForm.getPrimaryContactName());
			byTO.setPrimaryContactPhone(byForm.getPrimaryContactPhone());
			byTO.setSecContactDesignation(byForm.getSecContactDesignation());
			byTO.setSecContactEmail(byForm.getSecContactEmail());
			byTO.setSecContactMobile(byForm.getSecContactMobile());
			byTO.setSecContactName(byForm.getSecContactName());
			byTO.setSecContactPhone(byForm.getSecContactPhone());
			byTO.setModifiedBy(byForm.getUserId());
			byTO.setLastModifiedDate(new Date());
			//leave details
			byTO.setLeaveBeforeNoOfDays(byForm.getLeaveBeforeNoOfDays());
			byTO.setOnlineLeave(byForm.getOnlineLeave());
			if(byForm.getApplyBeforeHours()!=null && !byForm.getApplyBeforeHours().isEmpty())
				byTO.setApplyBeforeHours(byForm.getApplyBeforeHours());
			else
				byTO.setApplyBeforeHours(null);
			if(byForm.getApplyBeforeMin()!=null && !byForm.getApplyBeforeMin().isEmpty())
				byTO.setApplyBeforeMin(byForm.getApplyBeforeMin());
			else
				byTO.setApplyBeforeMin(null);
			if(byForm.getApplyBeforeNextDayHours()!=null && !byForm.getApplyBeforeNextDayHours().isEmpty())
				byTO.setApplyBeforeNextDayHours(byForm.getApplyBeforeNextDayHours());
			else
				byTO.setApplyBeforeNextDayHours(null);
			if(byForm.getApplyBeforeNextDayMin()!=null && !byForm.getApplyBeforeNextDayMin().isEmpty())
				byTO.setApplyBeforeNextDayMin(byForm.getApplyBeforeNextDayMin());
			else
				byTO.setApplyBeforeNextDayMin(null);
			byTO.setSmsForParents(byForm.getSmsForParents());
			byTO.setIntervalMails(byForm.getIntervalMails());
			byTO.setSmsForPrimaryCon(byForm.getSmsForPrimaryCon());
			byTO.setSmsForSecondCon(byForm.getSmsForSecondCon());
			byTO.setSmsOnMorning(byForm.getSmsOnMorning());
			byTO.setSmsOnEvening(byForm.getSmsOnEvening());
			byTO.setPunchExepSundaySession(byForm.getPunchExepSundaySession());
			
			if(byForm.getHostelId()!=null && !byForm.getHostelId().isEmpty()){
				HostelTO hostelTO=new HostelTO();
				hostelTO.setId(Integer.parseInt(byForm.getHostelId()));
				byTO.setHostelTO(hostelTO);
			}
			if(byForm.getBlockId()!=null && ! byForm.getBlockId().isEmpty()){
				HostelBlocksTO hostelBlocksTO = new HostelBlocksTO();
				hostelBlocksTO.setId(Integer.parseInt(byForm.getBlockId()));
				byTO.setHostelBlocksTO(hostelBlocksTO);
			}
		}
		if(byTO!=null){
			HlUnits hlUnits=HostelUnitsHelper.getInstance().populateTotoBoUpdate(byTO);
			if(transaction!=null){
				return transaction.updateHostelUnits(hlUnits);				
			}
		}
		log.info("Leaving of updateHostelUnits of HostelUnitsHandler");
		return false;
		}
	}
	
	public boolean reActivateHostelUnits(String name, String userId)throws Exception
	{
	log.info("Inside into reActivateHostelUnits of HostelUnitsHandler");
	if(transaction!=null){
		return transaction.reActivateHostelUnits(name, userId);
	}
	log.info("Leaving of reActivateHostelUnits of HostelUnitsHandler");
	return false;
	}
	
	public HostelUnitsTO getDetailsonId(int untId) throws Exception{
		log.info("Inside into getDetailsonId of HostelUnitsHandler");
		HlUnits hlUnits=transaction.getUnitsDetailsonId(untId);
		log.info("End of getDetailsonId of HostelUnitsHandler");
		return HostelUnitsHelper.getInstance().populateBotoToEdit(hlUnits);
	}
}
