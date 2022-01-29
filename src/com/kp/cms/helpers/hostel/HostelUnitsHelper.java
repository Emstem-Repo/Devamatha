package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelUnitsHelper {
	private static final Log log = LogFactory.getLog(HostelUnitsHelper.class);
	private static volatile HostelUnitsHelper hostelUnitsHelper = null;
	
	private HostelUnitsHelper() {
	}
	
	public static HostelUnitsHelper getInstance() {

		if (hostelUnitsHelper == null) {
			hostelUnitsHelper = new HostelUnitsHelper();
		}
		return hostelUnitsHelper;
	}
	
	public HlUnits populateTOtoBO(HostelUnitsTO hostelUnitsTO) throws Exception
	{
		log.info("Inside populateTOtoBO of HostelUnitsHelper");
		HlUnits hlUnits = null;
		
		if (hostelUnitsTO != null) {
			hlUnits = new HlUnits();
			if(hostelUnitsTO.getId()>0){
				hlUnits.setId(hostelUnitsTO.getId());
			}
			if(hostelUnitsTO.getName()!=null){
				hlUnits.setName(hostelUnitsTO.getName());
			}
			if (hostelUnitsTO.getHostelBlocksTO()!= null) {
				HlBlocks hlBlocks = new HlBlocks();
				hlBlocks.setId(hostelUnitsTO.getHostelBlocksTO().getId());
				hlUnits.setBlocks(hlBlocks);
			}
			hlUnits.setPrimaryContactDesignation(hostelUnitsTO.getPrimaryContactDesignation());
			hlUnits.setPrimaryContactEmail(hostelUnitsTO.getPrimaryContactEmail());
			hlUnits.setPrimaryContactMobile(hostelUnitsTO.getPrimaryContactMobile());
			hlUnits.setPrimaryContactName(hostelUnitsTO.getPrimaryContactName());
			hlUnits.setPrimaryContactPhone(hostelUnitsTO.getPrimaryContactPhone());
			hlUnits.setSecondaryContactDesignation(hostelUnitsTO.getSecContactDesignation());
			hlUnits.setSecondaryContactEmail(hostelUnitsTO.getSecContactEmail());
			hlUnits.setSecondaryContactMobile(hostelUnitsTO.getSecContactMobile());
			hlUnits.setSecondaryContactName(hostelUnitsTO.getSecContactName());
			hlUnits.setSecondaryContactPhone(hostelUnitsTO.getSecContactPhone());
			hlUnits.setFloorNo(hostelUnitsTO.getNoOfFloors());
			//leave details
			if(hostelUnitsTO.getOnlineLeave().equalsIgnoreCase("yes")){
				hlUnits.setOnlineLeave(true);
			}else{
				hlUnits.setOnlineLeave(false);
			}
			hlUnits.setApplyBeforeTime(HostelUnitsHelper.getTime(hostelUnitsTO.getApplyBeforeHours(),hostelUnitsTO.getApplyBeforeMin()));
			hlUnits.setApplyBeforeNextDayTime(HostelUnitsHelper.getTime(hostelUnitsTO.getApplyBeforeNextDayHours(),hostelUnitsTO.getApplyBeforeNextDayMin()));
			if(hostelUnitsTO.getLeaveBeforeNoOfDays()!=null && !hostelUnitsTO.getLeaveBeforeNoOfDays().isEmpty()){
				hlUnits.setLeaveBeforeNoOfDays(hostelUnitsTO.getLeaveBeforeNoOfDays());
			}
			if(hostelUnitsTO.getSmsForParents().equalsIgnoreCase("yes")){
				hlUnits.setSmsForParents(true);
			}else{
				hlUnits.setSmsForParents(false);
			}
			if(hostelUnitsTO.getIntervalMails()!=null && !hostelUnitsTO.getIntervalMails().isEmpty()){
				hlUnits.setIntervalMails(hostelUnitsTO.getIntervalMails());
			}
			if(hostelUnitsTO.getSmsForPrimaryCon().equalsIgnoreCase("yes")){
				hlUnits.setSmsForPrimaryCon(true);
			}else{
				hlUnits.setSmsForPrimaryCon(false);
			}
			if(hostelUnitsTO.getSmsForSecondCon().equalsIgnoreCase("yes")){
				hlUnits.setSmsForSecondCon(true);
			}else{
				hlUnits.setSmsForSecondCon(false);
			}
			if(hostelUnitsTO.getSmsOnMorning().equalsIgnoreCase("yes")){
				hlUnits.setSmsOnMorning(true);
			}else{
				hlUnits.setSmsOnMorning(false);
			}
			if(hostelUnitsTO.getSmsOnEvening().equalsIgnoreCase("yes")){
				hlUnits.setSmsOnEvening(true);
			}else{
				hlUnits.setSmsOnEvening(false);
			}
			if(hostelUnitsTO.getPunchExepSundaySession().equalsIgnoreCase("yes")){
				hlUnits.setPunchExepSundaySession(true);
			}else{
				hlUnits.setPunchExepSundaySession(false);
			}
			hlUnits.setCreatedBy(hostelUnitsTO.getCreatedBy());
			hlUnits.setModifiedBy(hostelUnitsTO.getModifiedBy());
			hlUnits.setCreatedDate(new Date());
			hlUnits.setLastModifiedDate(new Date());
			hlUnits.setIsActive(true);
		}
		log.info("Leaving from populateTOtoBO of HostelUnitsHelper");
		return hlUnits;
	}
	
	public List<HostelUnitsTO> pupulateHlUnitsBOtoTO(List<HlUnits> unitsList)throws Exception
	{
		log.info("Entering into pupulateHlUnitsBOtoTO of HostelUnitsHelper");
		HostelUnitsTO hostelUnitsTO = null;
		//HostelTO hostelTO = null;

		List<HostelUnitsTO> newUnitsList = new ArrayList<HostelUnitsTO>();
		if (unitsList != null && !unitsList.isEmpty()) {
			Iterator<HlUnits> iterator = unitsList.iterator();
			while (iterator.hasNext()) {
				HlUnits hlUnits = iterator.next();
				hostelUnitsTO = new HostelUnitsTO();
				//hostelTO = new HostelTO();
				if (hlUnits.getId() != 0 && hlUnits.getName() != null) {
					hostelUnitsTO.setId(hlUnits.getId());
					hostelUnitsTO.setName(hlUnits.getName());
					if (hlUnits.getBlocks()!= null) {
						hostelUnitsTO.setBlockName(hlUnits.getBlocks().getName());
					}
					hostelUnitsTO.setNoOfFloors(hlUnits.getFloorNo());
					hostelUnitsTO.setHostelName(hlUnits.getBlocks().getHlHostel().getName());
					newUnitsList.add(hostelUnitsTO);
				}
			}
		}
		log.info("Leaving from pupulateHlUnitsBOtoTO of HostelUnitsHelper");
		return newUnitsList;
	}
	
	public HlUnits populateTotoBoUpdate(HostelUnitsTO byTO) throws Exception
	{
		log.info("Entering into populateTotoBoUpdate of HostelUnitsHelper");
		HlBlocks hlBlocks = null;
		if (byTO != null) {
			HlUnits hlUnits = new HlUnits();
			hlUnits.setId(byTO.getId());
			hlUnits.setName(byTO.getName());
			hlUnits.setFloorNo(byTO.getNoOfFloors());
			hlUnits.setPrimaryContactDesignation(byTO.getPrimaryContactDesignation());
			hlUnits.setPrimaryContactEmail(byTO.getPrimaryContactEmail());
			hlUnits.setPrimaryContactMobile(byTO.getPrimaryContactMobile());
			hlUnits.setPrimaryContactName(byTO.getPrimaryContactName());
			hlUnits.setPrimaryContactPhone(byTO.getPrimaryContactPhone());
			hlUnits.setSecondaryContactDesignation(byTO.getSecContactDesignation());
			hlUnits.setSecondaryContactEmail(byTO.getSecContactEmail());
			hlUnits.setSecondaryContactMobile(byTO.getSecContactMobile());
			hlUnits.setSecondaryContactName(byTO.getSecContactName());
			hlUnits.setSecondaryContactPhone(byTO.getSecContactPhone());
			//leave details
			if(byTO.getOnlineLeave().equalsIgnoreCase("yes")){
				hlUnits.setOnlineLeave(true);
			}else{
				hlUnits.setOnlineLeave(false);
			}
			hlUnits.setApplyBeforeTime(HostelUnitsHelper.getTime(byTO.getApplyBeforeHours(),byTO.getApplyBeforeMin()));
			hlUnits.setApplyBeforeNextDayTime(HostelUnitsHelper.getTime(byTO.getApplyBeforeNextDayHours(),byTO.getApplyBeforeNextDayMin()));
			if(byTO.getLeaveBeforeNoOfDays()!=null && !byTO.getLeaveBeforeNoOfDays().isEmpty()){
				hlUnits.setLeaveBeforeNoOfDays(byTO.getLeaveBeforeNoOfDays());
			}
			if(byTO.getSmsForParents().equalsIgnoreCase("yes")){
				hlUnits.setSmsForParents(true);
			}else{
				hlUnits.setSmsForParents(false);
			}
			if(byTO.getIntervalMails()!=null && !byTO.getIntervalMails().isEmpty()){
				hlUnits.setIntervalMails(byTO.getIntervalMails());
			}
			if(byTO.getSmsForPrimaryCon().equalsIgnoreCase("yes")){
				hlUnits.setSmsForPrimaryCon(true);
			}else{
				hlUnits.setSmsForPrimaryCon(false);
			}
			if(byTO.getSmsForSecondCon().equalsIgnoreCase("yes")){
				hlUnits.setSmsForSecondCon(true);
			}else{
				hlUnits.setSmsForSecondCon(false);
			}
			if(byTO.getSmsOnMorning().equalsIgnoreCase("yes")){
				hlUnits.setSmsOnMorning(true);
			}else{
				hlUnits.setSmsOnMorning(false);
			}
			if(byTO.getSmsOnEvening().equalsIgnoreCase("yes")){
				hlUnits.setSmsOnEvening(true);
			}else{
				hlUnits.setSmsOnEvening(false);
			}
			if(byTO.getPunchExepSundaySession().equalsIgnoreCase("yes")){
				hlUnits.setPunchExepSundaySession(true);
			}else{
				hlUnits.setPunchExepSundaySession(false);
			}
			if (byTO.getHostelBlocksTO()!=null){
				hlBlocks = new HlBlocks();
				hlBlocks.setId(byTO.getHostelBlocksTO().getId());
				hlUnits.setBlocks(hlBlocks);
			}
			hlUnits.setIsActive(true);
			hlUnits.setLastModifiedDate(new Date());
			hlUnits.setModifiedBy(byTO.getModifiedBy());
			if (hlUnits != null) {
				return hlUnits;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of HostelUnitsHelper");
		return null;
	}
	
	public HostelUnitsTO populateBotoToEdit(HlUnits hlUnits)throws Exception 
	{
		log.info("Entering into populateBotoToEdit of HostelUnitsHelper");
		HostelUnitsTO hostelUnitsTO = new HostelUnitsTO();
		if (hlUnits != null) {
			hostelUnitsTO.setId(hlUnits.getId());
			if (hlUnits.getName() != null && !hlUnits.getName().isEmpty()) {
				hostelUnitsTO.setName(hlUnits.getName());
			}
			if (hlUnits.getFloorNo() != null){
				hostelUnitsTO.setNoOfFloors(hlUnits.getFloorNo());
			}
			if (hlUnits.getPrimaryContactDesignation() != null && !hlUnits.getPrimaryContactDesignation().isEmpty()){
				hostelUnitsTO.setPrimaryContactDesignation(hlUnits.getPrimaryContactDesignation());
			}
			if (hlUnits.getPrimaryContactEmail() != null && !hlUnits.getPrimaryContactEmail().isEmpty()){
				hostelUnitsTO.setPrimaryContactEmail(hlUnits.getPrimaryContactEmail());
			}
			if (hlUnits.getPrimaryContactMobile() != null && !hlUnits.getPrimaryContactMobile().isEmpty()){
				hostelUnitsTO.setPrimaryContactMobile(hlUnits.getPrimaryContactMobile());
			}
			if (hlUnits.getPrimaryContactName() != null && !hlUnits.getPrimaryContactName().isEmpty()){
				hostelUnitsTO.setPrimaryContactName(hlUnits.getPrimaryContactName());
			}
			if (hlUnits.getPrimaryContactPhone() != null && !hlUnits.getPrimaryContactPhone().isEmpty()){
				hostelUnitsTO.setPrimaryContactPhone(hlUnits.getPrimaryContactPhone());
			}
			if (hlUnits.getSecondaryContactDesignation() != null && !hlUnits.getSecondaryContactDesignation().isEmpty()){
				hostelUnitsTO.setSecContactDesignation(hlUnits.getSecondaryContactDesignation());
			}
			if (hlUnits.getSecondaryContactEmail() != null && !hlUnits.getSecondaryContactEmail().isEmpty()){
				hostelUnitsTO.setSecContactEmail(hlUnits.getSecondaryContactEmail());
			}
			if (hlUnits.getSecondaryContactMobile() != null && !hlUnits.getSecondaryContactMobile().isEmpty()){
				hostelUnitsTO.setSecContactMobile(hlUnits.getSecondaryContactMobile());
			}
			if (hlUnits.getSecondaryContactName() != null && !hlUnits.getSecondaryContactName().isEmpty()){
				hostelUnitsTO.setSecContactName(hlUnits.getSecondaryContactName());
			}
			if (hlUnits.getSecondaryContactPhone() != null && !hlUnits.getSecondaryContactPhone().isEmpty()){
				hostelUnitsTO.setSecContactPhone(hlUnits.getSecondaryContactPhone());
			}
			HostelTO hostelTO = new HostelTO();
			if(hlUnits.getBlocks()!=null){
				if(hlUnits.getBlocks().getHlHostel().getId()>0){
					hostelTO.setName(hlUnits.getBlocks().getHlHostel().getName());
					hostelTO.setId(hlUnits.getBlocks().getHlHostel().getId());
					hostelUnitsTO.setHostelTO(hostelTO);
				}
			}
			HostelBlocksTO hostelBlocksTO = new HostelBlocksTO();
			if(hlUnits.getBlocks()!=null){
				if(hlUnits.getBlocks().getId()>0){
				hostelBlocksTO.setName(hlUnits.getBlocks().getName());
				hostelBlocksTO.setId(hlUnits.getBlocks().getId());
				hostelUnitsTO.setHostelBlocksTO(hostelBlocksTO);
			}
			}
			if(hlUnits.getOnlineLeave()!=null && hlUnits.getOnlineLeave()){
				hostelUnitsTO.setOnlineLeave("yes");
			}else{
				hostelUnitsTO.setOnlineLeave("no");
			}
			if(hlUnits.getApplyBeforeTime()!=null && !hlUnits.getApplyBeforeTime().isEmpty()){
				if(hlUnits.getApplyBeforeTime().substring(0,2)!=null && !hlUnits.getApplyBeforeTime().substring(0,2).isEmpty())
					hostelUnitsTO.setApplyBeforeHours(hlUnits.getApplyBeforeTime().substring(0,2));
				else
					hostelUnitsTO.setApplyBeforeHours(null);
				if(hlUnits.getApplyBeforeTime().substring(3,5)!=null && !hlUnits.getApplyBeforeTime().substring(3,5).isEmpty())
					hostelUnitsTO.setApplyBeforeMin(hlUnits.getApplyBeforeTime().substring(3,5));
				else
					hostelUnitsTO.setApplyBeforeMin(null);
			}
			else{
				hostelUnitsTO.setApplyBeforeHours(null);
				hostelUnitsTO.setApplyBeforeMin(null);
			}
			if(hlUnits.getLeaveBeforeNoOfDays()!=null && !hlUnits.getLeaveBeforeNoOfDays().isEmpty()){
				hostelUnitsTO.setLeaveBeforeNoOfDays(hlUnits.getLeaveBeforeNoOfDays());
			}
			if(hlUnits.getIntervalMails()!=null && !hlUnits.getIntervalMails().isEmpty()){
				hostelUnitsTO.setIntervalMails(hlUnits.getIntervalMails());
			}
			if(hlUnits.getApplyBeforeNextDayTime()!=null && !hlUnits.getApplyBeforeNextDayTime().isEmpty()){
				hostelUnitsTO.setApplyBeforeNextDayHours(hlUnits.getApplyBeforeNextDayTime().substring(0,2));
				hostelUnitsTO.setApplyBeforeNextDayMin(hlUnits.getApplyBeforeNextDayTime().substring(3,5));
			}
			if(hlUnits.getSmsForParents()!=null && hlUnits.getSmsForParents()){
				hostelUnitsTO.setSmsForParents("yes");
			}else{
				hostelUnitsTO.setSmsForParents("no");
			}
			if(hlUnits.getSmsForParents()!=null && hlUnits.getSmsForParents()){
				hostelUnitsTO.setSmsForParents("yes");
			}else{
				hostelUnitsTO.setSmsForParents("no");
			}
			if(hlUnits.getSmsForPrimaryCon()!=null && hlUnits.getSmsForPrimaryCon()){
				hostelUnitsTO.setSmsForPrimaryCon("yes");
			}else{
				hostelUnitsTO.setSmsForPrimaryCon("no");
			}
			if(hlUnits.getSmsForSecondCon()!=null && hlUnits.getSmsForSecondCon()){
				hostelUnitsTO.setSmsForSecondCon("yes");
			}else{
				hostelUnitsTO.setSmsForSecondCon("no");
			}
			if(hlUnits.getSmsOnEvening()!=null && hlUnits.getSmsOnEvening()){
				hostelUnitsTO.setSmsOnEvening("yes");
			}else{
				hostelUnitsTO.setSmsOnEvening("no");
			}
			if(hlUnits.getSmsOnMorning()!=null && hlUnits.getSmsOnMorning()){
				hostelUnitsTO.setSmsOnMorning("yes");
			}else{
				hostelUnitsTO.setSmsOnMorning("no");
			}
			if(hlUnits.getPunchExepSundaySession()!=null && hlUnits.getPunchExepSundaySession()){
				hostelUnitsTO.setPunchExepSundaySession("yes");
			}else{
				hostelUnitsTO.setPunchExepSundaySession("no");
			}
		}
		if (hostelUnitsTO != null) {
			return hostelUnitsTO;
		}
		log.info("Leaving from populateBotoToEdit of HostelUnitsHelper");
		return null;
	}
	//added by Dilip
	public static String getTime(String one, String two) {
		String time = null;
		if((one!=null && !one.isEmpty()) && (two!=null && !two.isEmpty())){
			time = one + ":" + two;
			return time;
		}
		else if((one!=null && !one.isEmpty()) && (two==null || two.isEmpty())){
			time = one + ":" + "00";
			return time;
		}
		else if((one==null || one.isEmpty())&& (two!=null && !two.isEmpty())){
			time = "00" + ":" + two;
			return time;
		}
		else
			return time;
		
	}

}
