package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;

public class HostelUnitsForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String hostelId;
	private String hostelName;
	private String blockId;
	private String blockName;
	private String noOfFloors;
	private String primaryContactName;
	private String primaryContactDesignation;
	private String primaryContactPhone;
	private String primaryContactMobile;
	private String primaryContactEmail;
	private String secContactName;
	private String secContactDesignation;
	private String secContactPhone;
	private String secContactMobile;
	private String secContactEmail;
	private List<HostelUnitsTO> unitsList;
	private List<HostelTO> hostelList = new ArrayList<HostelTO>();
	private Map<Integer, String> blockMap;
	private String onlineLeave;
	private String applyBeforeHours;
	private String applyBeforeMin;
	private String leaveBeforeNoOfDays;
	private String applyBeforeNextDayHours;
	private String applyBeforeNextDayMin;
	private String smsForParents;
	private String intervalMails;
	private String smsForPrimaryCon;
	private String smsForSecondCon;
	private String smsOnEvening;
	private String smsOnMorning;
	private String punchExepSundaySession;
	
	public String getSmsForParents() {
		return smsForParents;
	}

	public void setSmsForParents(String smsForParents) {
		this.smsForParents = smsForParents;
	}

	public String getIntervalMails() {
		return intervalMails;
	}

	public void setIntervalMails(String intervalMails) {
		this.intervalMails = intervalMails;
	}

	public String getSmsForPrimaryCon() {
		return smsForPrimaryCon;
	}

	public void setSmsForPrimaryCon(String smsForPrimaryCon) {
		this.smsForPrimaryCon = smsForPrimaryCon;
	}

	public String getSmsForSecondCon() {
		return smsForSecondCon;
	}

	public void setSmsForSecondCon(String smsForSecondCon) {
		this.smsForSecondCon = smsForSecondCon;
	}

	public String getSmsOnEvening() {
		return smsOnEvening;
	}

	public void setSmsOnEvening(String smsOnEvening) {
		this.smsOnEvening = smsOnEvening;
	}

	public String getSmsOnMorning() {
		return smsOnMorning;
	}

	public void setSmsOnMorning(String smsOnMorning) {
		this.smsOnMorning = smsOnMorning;
	}

	public String getApplyBeforeHours() {
		return applyBeforeHours;
	}

	public void setApplyBeforeHours(String applyBeforeHours) {
		this.applyBeforeHours = applyBeforeHours;
	}

	public String getApplyBeforeMin() {
		return applyBeforeMin;
	}

	public void setApplyBeforeMin(String applyBeforeMin) {
		this.applyBeforeMin = applyBeforeMin;
	}

	public String getApplyBeforeNextDayHours() {
		return applyBeforeNextDayHours;
	}

	public void setApplyBeforeNextDayHours(String applyBeforeNextDayHours) {
		this.applyBeforeNextDayHours = applyBeforeNextDayHours;
	}

	public String getApplyBeforeNextDayMin() {
		return applyBeforeNextDayMin;
	}

	public void setApplyBeforeNextDayMin(String applyBeforeNextDayMin) {
		this.applyBeforeNextDayMin = applyBeforeNextDayMin;
	}

	public String getOnlineLeave() {
		return onlineLeave;
	}

	public void setOnlineLeave(String onlineLeave) {
		this.onlineLeave = onlineLeave;
	}

	public String getLeaveBeforeNoOfDays() {
		return leaveBeforeNoOfDays;
	}

	public void setLeaveBeforeNoOfDays(String leaveBeforeNoOfDays) {
		this.leaveBeforeNoOfDays = leaveBeforeNoOfDays;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNoOfFloors() {
		return noOfFloors;
	}

	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public List<HostelTO> getHostelList() {
		return hostelList;
	}

	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}

	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}

	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public List<HostelUnitsTO> getUnitsList() {
		return unitsList;
	}

	public void setUnitsList(List<HostelUnitsTO> unitsList) {
		this.unitsList = unitsList;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactDesignation() {
		return primaryContactDesignation;
	}

	public void setPrimaryContactDesignation(String primaryContactDesignation) {
		this.primaryContactDesignation = primaryContactDesignation;
	}

	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}

	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}

	public String getPrimaryContactMobile() {
		return primaryContactMobile;
	}

	public void setPrimaryContactMobile(String primaryContactMobile) {
		this.primaryContactMobile = primaryContactMobile;
	}

	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getSecContactName() {
		return secContactName;
	}

	public void setSecContactName(String secContactName) {
		this.secContactName = secContactName;
	}

	public String getSecContactDesignation() {
		return secContactDesignation;
	}

	public void setSecContactDesignation(String secContactDesignation) {
		this.secContactDesignation = secContactDesignation;
	}

	public String getSecContactPhone() {
		return secContactPhone;
	}

	public void setSecContactPhone(String secContactPhone) {
		this.secContactPhone = secContactPhone;
	}

	public String getSecContactMobile() {
		return secContactMobile;
	}

	public void setSecContactMobile(String secContactMobile) {
		this.secContactMobile = secContactMobile;
	}

	public String getSecContactEmail() {
		return secContactEmail;
	}

	public void setSecContactEmail(String secContactEmail) {
		this.secContactEmail = secContactEmail;
	}
	
	public void clearAll()
	{
		this.name = null;
		this.noOfFloors = null;
		this.setHostelId(null);
		this.setBlockId(null);
		this.blockMap = null;
		this.primaryContactDesignation = null;
		this.primaryContactEmail = null;
		this.primaryContactMobile = null;
		this.primaryContactName = null;
		this.primaryContactPhone = null;
		this.secContactDesignation = null;
		this.secContactEmail = null;
		this.secContactMobile = null;
		this.secContactName = null;
		this.secContactPhone = null;
		this.applyBeforeHours=null;
		this.applyBeforeMin=null;
		this.applyBeforeNextDayHours=null;
		this.applyBeforeNextDayMin=null;
		this.onlineLeave=null;
		this.smsForParents=null;
		this.smsForPrimaryCon=null;
		this.smsForSecondCon=null;
		this.smsOnEvening=null;
		this.smsOnMorning=null;
		this.leaveBeforeNoOfDays=null;
		this.intervalMails=null;
		this.punchExepSundaySession=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getPunchExepSundaySession() {
		return punchExepSundaySession;
	}

	public void setPunchExepSundaySession(String punchExepSundaySession) {
		this.punchExepSundaySession = punchExepSundaySession;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
