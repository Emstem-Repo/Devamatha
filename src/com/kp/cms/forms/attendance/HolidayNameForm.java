package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.HolidayTO;

public class HolidayNameForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private int holidayId;
	private String orgHolidayName;
	private String orgHolidayDate;
	private String holidayDate;
	private String holidayName;
	private List<HolidayTO> holidayList;
	private int reactiveId;
	
	public HolidayNameForm(){
		super();
	}
	
	public int getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getOrgHolidayName() {
		return orgHolidayName;
	}
	public void setOrgHolidayName(String orgHolidayName) {
		this.orgHolidayName = orgHolidayName;
	}
	public String getOrgHolidayDate() {
		return orgHolidayDate;
	}
	public void setOrgHolidayDate(String orgHolidayDate) {
		this.orgHolidayDate = orgHolidayDate;
	}
	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<HolidayTO> getHolidayList() {
		return holidayList;
	}
	public void setHolidayList(List<HolidayTO> holidayList) {
		this.holidayList = holidayList;
	}
	public int getReactiveId() {
		return reactiveId;
	}
	public void setReactiveId(int reactiveId) {
		this.reactiveId = reactiveId;
	}
    
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.holidayDate = null;
		this.holidayName=null;
	
	}
	
	

}
