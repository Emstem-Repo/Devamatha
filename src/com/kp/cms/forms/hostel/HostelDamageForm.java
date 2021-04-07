package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HostelDamageTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelDamageForm extends BaseActionForm {
	private String hlDamageId;
	private String applicationNo;
	private String registerNo;
	private String staffId;
	private String rollNo;
	private String date;
	private String timeHours;
	private String timeMins; 
	private String description;
	private String amount;
	private HostelDamageTO hostelDamageTO;
	private List<HostelTO> hostelTOList;
	private List<HlDamageTO> hlDamageTOList;
	

	public String getHlDamageId() {
		return hlDamageId;
	}

	public void setHlDamageId(String hlDamageId) {
		this.hlDamageId = hlDamageId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
    
	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
    
	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}	
	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}

	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}

	public HostelDamageTO getHostelDamageTO() {
		return hostelDamageTO;
	}
	
	public void setHostelDamageTO(HostelDamageTO hostelDamageTO) {
		this.hostelDamageTO = hostelDamageTO;
	}
	
	public String getTimeHours() {
		return timeHours;
	}

	public void setTimeHours(String timeHours) {
		this.timeHours = timeHours;
	}	

	public String getTimeMins() {
		return timeMins;
	}

	public void setTimeMins(String timeMins) {
		this.timeMins = timeMins;
	}
    
	
	public List<HlDamageTO> getHlDamageTOList() {
		return hlDamageTOList;
	}

	public void setHlDamageTOList(List<HlDamageTO> hlDamageTOList) {
		this.hlDamageTOList = hlDamageTOList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields1() 
	{
		super.setHostelId(null);
		applicationNo =null;
		registerNo =null;
		staffId =null;
		rollNo =null;
		
	}
	// here two reset functions 1 and 2 are written because values in 
	//resetFields1 is needed in the next page so cannot clear them from session
	
	public void resetFields2()
	{
		date=null;
		timeHours=null;
		timeMins=null; 
		description=null;
		amount=null;
	}

}
