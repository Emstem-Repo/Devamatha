package com.kp.cms.forms.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.HolidayDetailsTO;

public class HolidayDetailsForm extends BaseActionForm{
private int id;
private String startDate;
private String endDate;
private List<HolidayDetailsTO> holidaysTO;
private String description;
private String origStartDate;
private String origEndDate;
private String origDescription;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public void reset(){
	this.startDate=null;
	this.endDate=null;
	this.description=null;
	this.id=0;
}
public List<HolidayDetailsTO> getHolidaysTO() {
	return holidaysTO;
}
public void setHolidaysTO(List<HolidayDetailsTO> holidaysTO) {
	this.holidaysTO = holidaysTO;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getOrigStartDate() {
	return origStartDate;
}
public void setOrigStartDate(String origStartDate) {
	this.origStartDate = origStartDate;
}
public String getOrigEndDate() {
	return origEndDate;
}
public void setOrigEndDate(String origEndDate) {
	this.origEndDate = origEndDate;
}
public String getOrigDescription() {
	return origDescription;
}
public void setOrigDescription(String origDescription) {
	this.origDescription = origDescription;
}
}
